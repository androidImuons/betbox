package com.lotus.appupdater;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.lotus.R;

import static com.lotus.rest.WebRequestConstants.URL_APK_DOWNLOAD;


/**
 * Created by ubuntu on 29/5/17.
 */

public class DialogAppUpdater extends Dialog implements View.OnClickListener

{
    Context context;
    TextView tv_update, tv_later;
    TextView tv_message_data;
    AppUpdatemodal appUpdatemodal;

    public DialogAppUpdater(Context context, AppUpdatemodal appUpdatemodal) {
        super(context);
        this.context = context;
        this.appUpdatemodal = appUpdatemodal;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        LayoutInflater inflate = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflate.inflate(R.layout.dialog_app_updater, null);
        setContentView(layout);

        WindowManager.LayoutParams wlmp = getWindow().getAttributes();
        wlmp.gravity = Gravity.CENTER;
        wlmp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wlmp.height = ViewGroup.LayoutParams.WRAP_CONTENT;

        setTitle(null);
        if (appUpdatemodal.isForcefully()) {
            setCancelable(false);
        } else {
            setCancelable(true);
        }
        setCanceledOnTouchOutside(false);
        setOnCancelListener(null);
        setOnShowListener(new OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                String msg = String.format(getContext().getString(R.string.text_app_update), appUpdatemodal.getVersion());
                if (!msg.trim().isEmpty()) {
                    tv_message_data.setText(Html.fromHtml(msg));
                }
            }
        });


        tv_update = (TextView) findViewById(R.id.tv_update);
        tv_later = (TextView) findViewById(R.id.tv_later);
        tv_message_data = (TextView) findViewById(R.id.tv_message_data);
        tv_update.setOnClickListener(this);
        tv_later.setOnClickListener(this);
        if (appUpdatemodal.isForcefully()) {
            tv_later.setVisibility(View.GONE);
        } else {
            tv_later.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tv_later) {
            this.dismiss();
        } else if (view.getId() == R.id.tv_update) {
            goToPlayStore();
        }
    }

    private void goToPlayStore() {
        String url = String.format(URL_APK_DOWNLOAD, appUpdatemodal.getApk_download_link());
        Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse(url));
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, "No application can handle this request, Please install a webbrowser."
                    , Toast.LENGTH_LONG).show();
        }
    }
}
