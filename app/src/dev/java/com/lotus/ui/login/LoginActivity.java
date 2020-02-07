package com.lotus.ui.login;

import android.content.Intent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.lotus.R;
import com.lotus.appBase.AppBaseActivity;
import com.lotus.appupdater.AppUpdateChecker;
import com.lotus.appupdater.AppUpdatemodal;
import com.lotus.appupdater.DialogAppUpdater;
import com.lotus.model.UserModel;
import com.lotus.model.request_model.LoginRequestModel;
import com.lotus.ui.MyApplication;
import com.lotus.ui.main.MainActivity;
import com.lotus.ui.main.dashboard.DashboardFragment;
import com.medy.retrofitwrapper.WebRequest;
import com.models.DeviceInfoModal;
import com.models.DeviceScreenModel;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppBaseActivity {

    private LinearLayout ll_logo;
    private EditText etUsername;
    private EditText etPassword;
    private ImageView captcha_view;
    private ImageView iv_refresh;
    private EditText etCaptcha;
    private TextView tvLogin;
    private FrameLayout fl_data;

    CheckBox cb_remember_me;


    @Override
    public int getLayoutResourceId() {
        return R.layout.activitiy_login;
    }

    private boolean checkUserLogin() {
        UserModel userModel = ((MyApplication) getApplication()).getUserModel();
        if (userModel != null) {
            sendActivityFinish(this, MainActivity.class);
            return true;
        }
        return false;
    }

    @Override
    public void initializeComponent() {
        super.initializeComponent();
        if (!checkUserLogin()) {
            cb_remember_me = findViewById(R.id.cb_remember_me);
            ll_logo = findViewById(R.id.ll_logo);
            etUsername = findViewById(R.id.etUsername);
            etPassword = findViewById(R.id.etPassword);
            captcha_view = findViewById(R.id.captcha_view);
            iv_refresh = findViewById(R.id.iv_refresh);
            etCaptcha = findViewById(R.id.etCaptcha);
            tvLogin = findViewById(R.id.tvLogin);
            fl_data = findViewById(R.id.fl_data);
            fl_data.post(new Runnable() {
                @Override
                public void run() {
                    int height = DeviceScreenModel.getInstance().getHeight(0.50f);
                    int height1 = fl_data.getHeight();
                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) fl_data.getLayoutParams();
                    layoutParams.topMargin = Math.round(height - (height1));
                    fl_data.setLayoutParams(layoutParams);

                    RelativeLayout.LayoutParams layoutParams1 = (RelativeLayout.LayoutParams) ll_logo.getLayoutParams();
                    layoutParams1.topMargin = DeviceScreenModel.getInstance().getHeight(0.10f);
                    ll_logo.setLayoutParams(layoutParams1);
                }
            });

//            etUsername.setText("a_manish1");
//            etPassword.setText("123456");
            iv_refresh.setOnClickListener(this);
            tvLogin.setOnClickListener(this);

            setupRememberedData();

            getWebRequestHelper().loadCaptcha(this);

            String app_update_data = null;
            if (getIntent().getExtras() != null) {
                app_update_data = getIntent().getExtras().getString(CONSTANT_APP_UPDATE);
            }
            if (isValidString(app_update_data)) {
                AppUpdatemodal appUpdatemodal = null;
                try {
                    appUpdatemodal = new Gson().fromJson(app_update_data, AppUpdatemodal.class);
                } catch (JsonSyntaxException ignore) {

                }
                if (appUpdatemodal != null) {
                    if (!isFinishing()) {
                        new DialogAppUpdater(LoginActivity.this, appUpdatemodal).show();
                    }
                    return;
                }

            }
            checkAppUpdate();
        }
    }

    private void checkAppUpdate() {
        AppUpdateChecker appUpdateChecker = new AppUpdateChecker(this, new AppUpdateChecker.OnAppUpdateAvaliable() {
            @Override
            public void appUpdateAvaliable(AppUpdatemodal appUpdatemodal) {
                if (appUpdatemodal == null) return;
                if (!isFinishing()) {
                    new DialogAppUpdater(LoginActivity.this, appUpdatemodal).show();
                }
            }
        });
        appUpdateChecker.checkAppUpdate();
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.iv_refresh:
                etCaptcha.setText("");
//                displayProgressBar(false, getString(R.string.please_wait));
                getWebRequestHelper().loadCaptcha(this);
                break;
            case R.id.tvLogin:
               // login();
                nextActivity();
                break;
        }
    }

    private void nextActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void setupRememberedData(){
        String rememberUserName = MyApplication.getInstance().getAppPrefs().getRememberUserName();
        String rememberPassword = MyApplication.getInstance().getAppPrefs().getRememberPassword();
        etUsername.setText(rememberUserName);
        etPassword.setText(rememberPassword);
    }

    private void updateRememberMe(){
        if(cb_remember_me.isChecked()){
            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            MyApplication.getInstance().getAppPrefs().updateRememberMe(username,password);
        }
    }

    private void login() {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String captcha = etCaptcha.getText().toString().trim();
        if (username.isEmpty()) {
            showErrorMessage("Please enter Username");
            return;
        }
        if (password.isEmpty()) {
            showErrorMessage("Please enter Password");
            return;
        }
//        if (captcha.isEmpty()) {
//            showErrorMessage("Please enter Captcha");
//            return;
//        }

        LoginRequestModel requestModel = new LoginRequestModel();
        requestModel.username1 = username;
        requestModel.password1 = password;
        requestModel.captcha = captcha;
        requestModel.device_info = new DeviceInfoModal(this).toString();

        displayProgressBar(false, getString(R.string.please_wait));
        getWebRequestHelper().login(requestModel, this);
    }

    @Override
    public void onWebRequestResponse(WebRequest webRequest) {
        dismissProgressBar();
        super.onWebRequestResponse(webRequest);
        if (webRequest.getWebRequestId() == ID_LOAD_CAPTCHA) {
            handleLoadCaptchaResponse(webRequest);
            return;
        }

        if (webRequest.getWebRequestId() == ID_LOGIN) {
            handleLoginResponse(webRequest);
            return;
        }
    }

    private void handleLoadCaptchaResponse(WebRequest webRequest) {
        String responseString = webRequest.getResponseString();
        try {
            JSONObject jsonObject = new JSONObject(responseString);
            String image = jsonObject.getString("filename");
            if (isValidString(image)) {
                Picasso.get().load(image).into(captcha_view);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            if (isValidString(responseString)) {
                showErrorMessage(responseString);
            }
        }

    }

    private void handleLoginResponse(WebRequest webRequest) {
        UserModel responsemodel = webRequest.getResponsePojo(UserModel.class);
        if (responsemodel == null) {
            String responseString = webRequest.getResponseString();
            if (isValidString(responseString)) {
                showErrorMessage(responseString);
            }
            return;
        }
        if (!responsemodel.isError()) {
            ((MyApplication) getApplication()).getUserPrefs().saveLoggedInUser(responsemodel);
            getMyApplication().updateCookies(webRequest.getCookies());
            String message = responsemodel.getMessage();
            if (isValidString(message)) {
                showCustomToast(message);
            }
            updateRememberMe();
            sendActivityFinish(this, MainActivity.class);
        } else {
            iv_refresh.performClick();
            String error = responsemodel.getMessage();
            if (isValidString(error)) {
                showErrorMessage(error);
            }
        }
    }
}
