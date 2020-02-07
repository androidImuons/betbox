package com.lotus.appBase;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.base.BaseActivity;
import com.lotus.R;
import com.lotus.Validate;
import com.lotus.rest.WebRequestConstants;
import com.lotus.rest.WebRequestHelper;
import com.lotus.ui.MyApplication;
import com.medy.retrofitwrapper.WebRequest;
import com.medy.retrofitwrapper.WebRequestErrorDialog;
import com.medy.retrofitwrapper.WebServiceResponseListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * Created by ubuntu on 20/2/18.
 */

public abstract class AppBaseActivity extends BaseActivity
        implements WebServiceResponseListener, WebRequestConstants, View.OnClickListener {

    //    public String currency_symbol = "";
    private Window mWindow;
    private WebRequestErrorDialog errorMessageDialog;
    private Dialog alertDialogProgressBar;
    private WebRequestHelper webRequestHelper;
    private Validate validate;

    public static final String APP_DATE_FORMAT = "yyyy-MM-dd";


    @Override
    public void initializeComponent() {
//        currency_symbol = getResources().getString(R.string.rupee_symbol) + " ";
        webRequestHelper = new WebRequestHelper(this);
        validate = new Validate(this);
    }

    public void sendActivity(Context context, Class<?> className) {
        Intent intent = new Intent(context, className);
        context.startActivity(intent);
        overridePendingTransition(R.anim.right_in, R.anim.left_out);
    }

    public void sendActivity(Context context, Class<?> className, Bundle args) {
        Intent intent = new Intent(context, className);
        intent.putExtras(args);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        overridePendingTransition(R.anim.right_in, R.anim.left_out);
    }

    public void sendActivityFinish(Context context, Class<?> className) {
        Intent intent = new Intent(context, className);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        overridePendingTransition(R.anim.right_in, R.anim.left_out);
        finish();
    }

    public void sendActivityFinish(Context context, Class<?> className, Bundle args) {
        Intent intent = new Intent(context, className);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtras(args);
        context.startActivity(intent);
        overridePendingTransition(R.anim.right_in, R.anim.left_out);
        finish();
    }

    public void transparentStatusBar() {
        mWindow = getWindow();
        mWindow.getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }


   /* @Override
    public BaseFragment getLatestFragment () {
        if (getFragmentCount() > 1) {
            return super.getLatestFragment();
        } else {
            return null;
        }
    }*/

    @Override
    public void onWebRequestCall(WebRequest webRequest) {
        getMyApplication().onWebRequestCall(webRequest);
    }

    @Override
    public void onWebRequestResponse(WebRequest webRequest) {

    }

    public void dismissProgressBar() {
        if (alertDialogProgressBar != null) {
            alertDialogProgressBar.dismiss();
        }
    }

    public void displayProgressBar(boolean isCancellable) {
        displayProgressBar(isCancellable, "");
    }

    public void displayProgressBar(boolean isCancellable, String loaderMsg) {
        dismissProgressBar();
        if (isFinishing()) return;
        alertDialogProgressBar = new Dialog(this,
                R.style.CustomAlertDialogStyle);
        alertDialogProgressBar.setCancelable(isCancellable);
        alertDialogProgressBar
                .requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialogProgressBar.setContentView(R.layout.progress_dialog);
        TextView tv_loader_msg = alertDialogProgressBar.findViewById(R.id.tv_loader_msg);
        if (loaderMsg != null && !loaderMsg.trim().isEmpty()) {
            tv_loader_msg.setText(loaderMsg);
        } else {
            tv_loader_msg.setVisibility(View.GONE);
        }

        alertDialogProgressBar.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.TRANSPARENT));
        alertDialogProgressBar.show();
    }

    public void showErrorMessage(String msg) {
        if (!isFinishing()) {
            if (errorMessageDialog == null) {
                errorMessageDialog = new WebRequestErrorDialog(this, msg) {
                    @Override
                    public int getLayoutResourceId() {
                        return R.layout.errordialog;
                    }

                    @Override
                    public int getMessageTextViewId() {
                        return R.id.tv_message;
                    }

                    @Override
                    public int getDismissBtnTextViewId() {
                        return R.id.tv_ok;
                    }
                };
            } else if (errorMessageDialog.isShowing()) {
                errorMessageDialog.dismiss();
            }
            errorMessageDialog.setMsg(msg);
            errorMessageDialog.show();
        }

    }

    public void updateViewVisibility(View view, int visibility) {
        if (view.getVisibility() != visibility)
            view.setVisibility(visibility);
    }

    public WebRequestHelper getWebRequestHelper() {
        return webRequestHelper;
    }

    public Validate getValidate() {
        return validate;
    }


    public MyApplication getMyApplication() {
        return ((MyApplication) getApplication());
    }


    @Override
    protected void onDestroy() {
        dismissProgressBar();
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {

    }

    public String getValidDate(Calendar calendar) {
        Date date = calendar.getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(APP_DATE_FORMAT);
        return simpleDateFormat.format(date);

    }
    public void setBackgroundResDrawable(Context context, int resId, View view) {
        final int sdk = android.os.Build.VERSION.SDK_INT;
        if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackgroundDrawable(ContextCompat.getDrawable(context, resId));
        } else {
            view.setBackground(ContextCompat.getDrawable(context, resId));
        }
    }


}
