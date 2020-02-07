package com.lotus.ui.main.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.lotus.R;

/**
 * Created by ubuntu on 15/7/16.
 */
public class ExcelFileExportDialog extends Dialog implements
        View.OnClickListener {
    Context context;
    ExcelFileExportDialog dia;
    TextView message_txt;
    String msg = "";


    TextView tv_ok, tv_cancel;
    ConfirmBetDialogListener confirmBetDialogListener;


    public ExcelFileExportDialog(Context context) {
        super(context);
        this.context = context;
        dia = this;


    }

    public void setConfirmBetDialogListener(ConfirmBetDialogListener confirmBetDialogListener) {
        this.confirmBetDialogListener = confirmBetDialogListener;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawableResource(R.color.color_transprent);
        LayoutInflater inflate = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflate.inflate(R.layout.dialog_excel_file_export, null);
        setContentView(layout);

        WindowManager.LayoutParams wlmp = getWindow().getAttributes();
        wlmp.windowAnimations = R.style.BadeDialogStyle;
        wlmp.gravity = Gravity.CENTER;
        wlmp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        setTitle(null);
        setCancelable(true);
        setCanceledOnTouchOutside(true);
        setOnCancelListener(null);


        tv_ok = (TextView) layout.findViewById(R.id.tv_ok);
        message_txt = (TextView) layout.findViewById(R.id.message_txt);
        tv_cancel = (TextView) layout.findViewById(R.id.tv_cancel);

//        msg = "Bet placed successfully.";
        message_txt.setText(msg);

        tv_ok.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_ok:
                if (confirmBetDialogListener != null) {
                    confirmBetDialogListener.onClickConfirm(this, v);
                }
                break;
            case R.id.tv_cancel:
                if (confirmBetDialogListener != null) {
                    confirmBetDialogListener.onClickConfirm(this, v);
                }
                break;
        }
    }

    public void setFilePath(String filePath) {
        this.msg = filePath;
    }

    public interface ConfirmBetDialogListener {
        void onClickConfirm(Dialog dialog, View v);
    }
}
