package com.lotus.appBase;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;

import com.base.BaseDialogFragment;
import com.lotus.R;
import com.lotus.Validate;
import com.lotus.rest.WebRequestConstants;
import com.lotus.rest.WebRequestHelper;
import com.lotus.ui.MyApplication;
import com.lotus.ui.main.MainActivity;
import com.medy.retrofitwrapper.WebRequest;
import com.medy.retrofitwrapper.WebServiceResponseListener;

/**
 * Created by ubuntu on 29/3/18.
 */

public abstract class AppBaseDialogFragment extends BaseDialogFragment
        implements WebServiceResponseListener, WebRequestConstants {

    private Dialog alertDialogProgressBar;

    @Override
    public void initializeComponent() {
    }

    public void displayProgressBar(boolean isCancellable) {
        dismissProgressBar();
        alertDialogProgressBar = new Dialog(getActivity(),
                R.style.CustomAlertDialogStyle);
        alertDialogProgressBar.setCancelable(false);
        alertDialogProgressBar
                .requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialogProgressBar.setContentView(R.layout.progress_dialog);

        alertDialogProgressBar.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.TRANSPARENT));

        alertDialogProgressBar.show();
    }

    public void dismissProgressBar() {
        if (alertDialogProgressBar != null) {
            alertDialogProgressBar.dismiss();
        }
    }

    public void showCustomToast(String message) {
        if (getActivity() == null)return ;
        ((AppBaseActivity)getActivity()).showCustomToast(message);
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
            ((AppBaseActivity) getActivity()).onWebRequestCall(webRequest);
        }
    }

    public WebRequestHelper getWebRequestHelper() {
        if (getActivity() != null) {
            return ((AppBaseActivity) getActivity()).getWebRequestHelper();
        }
        return null;
    }

    public MainActivity getMainActivity() throws IllegalAccessException {

        if (getActivity() == null)
            throw new IllegalAccessException("Activity is not found");
        return ((MainActivity) getActivity());
    }


    public MyApplication getMyApplication() throws IllegalAccessException {
        return ((MyApplication) getMainActivity().getApplication());
    }

    public boolean isValidObject(Object object) {
        return object != null;
    }


    public Validate getValidate() {
        if (getActivity() == null)return null;
        return ((AppBaseActivity)getActivity()).getValidate();
    }


    @Override
    public void onDestroyView() {
        dismissProgressBar();
        super.onDestroyView();
    }
}
