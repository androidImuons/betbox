package com.lotus.ui.main.rightsidemenu.changepassword;


import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.lotus.R;
import com.lotus.appBase.AppBaseFragment;
import com.lotus.model.ChangePasswordModel;
import com.lotus.model.UserModel;
import com.lotus.model.request_model.ChangePasswordRequestModel;
import com.lotus.model.web_response.ChangePasswordResponseModel;
import com.medy.retrofitwrapper.WebRequest;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChangePasswordFragment extends AppBaseFragment {

    ChangePasswordListener changePasswordListener;
    public boolean autoOpen;

    public void setChangePasswordListener(ChangePasswordListener changePasswordListener) {
        this.changePasswordListener = changePasswordListener;
    }

    EditText etPassword;
    EditText etNewPassword;
    EditText etRNewPassword;
    TextView tvSave;

    @Override
    public int getLayoutResourceId() {
        return R.layout.fragment_change_password;
    }

    @Override
    public void initializeComponent() {
        super.initializeComponent();
        etPassword = getView().findViewById(R.id.etPassword);
        etNewPassword = getView().findViewById(R.id.etNewPassword);
        etRNewPassword = getView().findViewById(R.id.etRNewPassword);
        tvSave = getView().findViewById(R.id.tvSave);

        tvSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvSave:
                onSubmit();
                break;
        }
    }

    private void onSubmit() {
        if (isValid()) {
            try {
                ChangePasswordRequestModel requestModel = new ChangePasswordRequestModel();
                requestModel.old_password = etPassword.getText().toString().trim();
                requestModel.newpassword = etNewPassword.getText().toString().trim();
                requestModel.Renewpassword = etRNewPassword.getText().toString().trim();
                requestModel.user_id = getMyApplication().getUserModel().getUser_id();
                requestModel.user_type = getMyApplication().getUserModel().getType();
                displayProgressBar(false);
                getWebRequestHelper().changePassword(requestModel, this);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isValid() {
        String oldPassword = etPassword.getText().toString().trim();
        String newPassword = etNewPassword.getText().toString().trim();
        String confirmPassword = etRNewPassword.getText().toString().trim();
        if (TextUtils.isEmpty(oldPassword)) {
            showErrorMessage("Old login_password does not empty.");
            return false;
        } else if (TextUtils.isEmpty(newPassword)) {
            showErrorMessage("New login_password does not empty.");
            return false;
        } else if (TextUtils.isEmpty(confirmPassword)) {
            showErrorMessage("Renew login_password does not empty.");
            return false;
        } else if (!newPassword.equals(confirmPassword)) {
            showErrorMessage("New Password and Renew login_password does not match.");
            return false;
        }
        return true;
    }

    @Override
    public void onWebRequestResponse(WebRequest webRequest) {
        dismissProgressBar();
        super.onWebRequestResponse(webRequest);
        if (webRequest.getWebRequestId() == ID_CHANGE_PASSWORD) {
            handleChangePasswordResponse(webRequest);
            return;
        }


    }

    private void handleChangePasswordResponse(WebRequest webRequest) {
        try {
            String error = webRequest.getResponseString();
            ChangePasswordResponseModel responseModel = webRequest.getResponsePojo(ChangePasswordResponseModel.class);
            if (responseModel == null) {
                if (isValidString(error)) {
                    showErrorMessage(error);
                }
                return;
            }

            if (responseModel.getError() != 0) {
                error = responseModel.getMessage();
                if (isValidString(error)) {
                    showErrorMessage(error);
                }
                return;
            }
            ChangePasswordModel data = responseModel.getData();
            if (data == null) {
                if (isValidString(error)) {
                    showErrorMessage(error);
                }
                return;
            }
            UserModel userModel = getMyApplication().getUserModel();
            userModel.setMstrpassword(data.getMstrpassword());
            if (userModel.getChangePas() == 0) {
                userModel.setChangePas(1);
            }
            getMyApplication().updateUserProfile(userModel);
            showCustomToast(responseModel.getMessage());
            etPassword.setText("");
            etNewPassword.setText("");
            etRNewPassword.setText("");
            if (changePasswordListener != null) {
                changePasswordListener.passwordChangeSuccessfully();
            }
            getActivity().onBackPressed();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }


    public interface ChangePasswordListener {
        void passwordChangeSuccessfully();
    }

}
