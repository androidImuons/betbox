package com.lotus.appBase;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.base.BaseFragment;
import com.lotus.R;
import com.lotus.Validate;
import com.lotus.rest.WebRequestConstants;
import com.lotus.rest.WebRequestHelper;
import com.lotus.ui.MyApplication;
import com.lotus.ui.main.MainActivity;
import com.medy.retrofitwrapper.WebRequest;
import com.medy.retrofitwrapper.WebRequestErrorDialog;
import com.medy.retrofitwrapper.WebServiceResponseListener;

import java.util.Calendar;
import java.util.Locale;

/**
 * Created by ubuntu on 20/2/18.
 */

public abstract class AppBaseFragment extends BaseFragment
        implements WebServiceResponseListener, WebRequestConstants {


    //    public String currency_symbol = "";
    WebRequestErrorDialog errorMessageDialog;
    private Dialog alertDialogProgressBar;
    private Dialog alertSecondDialogProgressBar;

    @Override
    public void initializeComponent() {
//        currency_symbol = getResources().getString(R.string.rupee_symbol) + " ";
    }

    @Override
    public void reInitializeComponent() {
    }

    @Override
    public void onWebRequestCall(WebRequest webRequest) {
        if (getActivity() != null) {
            ((AppBaseActivity) getActivity()).onWebRequestCall(webRequest);
        }
    }

    @Override
    public void onWebRequestResponse(WebRequest webRequest) {
        if (getActivity() != null) {
            ((AppBaseActivity) getActivity()).onWebRequestResponse(webRequest);
        }
    }

    public void dismissProgressBar() {
        if (alertDialogProgressBar != null) {
            try {
                alertDialogProgressBar.dismiss();
            }catch (Exception e){

            }
        }
    }

    public void displayProgressBar(boolean isCancellable) {
        displayProgressBar(isCancellable, "");
    }

    public void displayProgressBar(boolean isCancellable, String loaderMsg) {
        dismissProgressBar();
        if (getActivity() == null) return;
        alertDialogProgressBar = new Dialog(getActivity(),
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
        try {
            alertDialogProgressBar.show();
        }catch (Exception e){

        }
    }

    public void dismissSecondProgressBar() {
        if (alertSecondDialogProgressBar != null) {
            try {
                alertSecondDialogProgressBar.dismiss();
            }catch (Exception e){

            }
        }
    }

    public void displaySecondProgressBar(boolean isCancellable) {
        displaySecondProgressBar(isCancellable, "");
    }

    public void displaySecondProgressBar(boolean isCancellable, String loaderMsg) {
        dismissProgressBar();
        if (getActivity() == null) return;
        alertSecondDialogProgressBar = new Dialog(getActivity(),
                R.style.CustomAlertDialogStyle);
        alertSecondDialogProgressBar.setCancelable(isCancellable);
        alertSecondDialogProgressBar
                .requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertSecondDialogProgressBar.setContentView(R.layout.progress_dialog);
        TextView tv_loader_msg = alertSecondDialogProgressBar.findViewById(R.id.tv_loader_msg);
        if (loaderMsg != null && !loaderMsg.trim().isEmpty()) {
            tv_loader_msg.setText(loaderMsg);
        } else {
            tv_loader_msg.setVisibility(View.GONE);
        }

        alertSecondDialogProgressBar.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.TRANSPARENT));
        try {
            alertSecondDialogProgressBar.show();
        } catch (Exception e) {

        }
    }

    public void showErrorMessage(String msg) {
        if (isValidActivity() && isVisible()) {
            if (errorMessageDialog == null) {
                errorMessageDialog = new WebRequestErrorDialog(getActivity(), msg) {
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
            try {
                errorMessageDialog.show();
            } catch (Exception e) {

            }
        }

    }

    public WebRequestHelper getWebRequestHelper() {
        if (getActivity() == null) return null;
        return ((AppBaseActivity) getActivity()).getWebRequestHelper();
    }


    public void showCustomToast(String message) {
        if (getActivity() == null) return;
        ((AppBaseActivity) getActivity()).showCustomToast(message);
    }

    public MainActivity getMainActivity() throws IllegalAccessException {
        if (getActivity() == null)
            throw new IllegalAccessException("Activity is not found");
        return ((MainActivity) getActivity());
    }

    public MyApplication getMyApplication() throws IllegalAccessException {
        return ((MyApplication) getMainActivity().getApplication());
    }


    public void updateViewVisibility(View view, int visibility) {
        if (view != null && view.getVisibility() != visibility)
            view.setVisibility(visibility);
    }

    @Override
    public void onDestroyView() {
        dismissProgressBar();
        super.onDestroyView();
    }

    public Validate getValidate() {
        Validate validate = null;
        try {
            validate = getMainActivity().getValidate();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return validate;
    }

    public String getValidDate(Calendar calendar) {
        if (isValidActivity()) {
            try {
                return getMainActivity().getValidDate(calendar);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public String getValidDecimalFormat(double value) {
        return String.format(Locale.ENGLISH, "%.2f", value);
    }
}
