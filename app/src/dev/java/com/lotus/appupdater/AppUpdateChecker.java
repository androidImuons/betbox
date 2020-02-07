package com.lotus.appupdater;

import android.content.Context;

import com.lotus.ui.MyApplication;
import com.medy.retrofitwrapper.WebRequest;
import com.medy.retrofitwrapper.WebServiceResponseListener;

import org.json.JSONObject;

import static com.lotus.rest.WebRequestConstants.URL_CHECK_APP_VERSION;

/**
 * Created by ubuntu on 29/5/17.
 */

public class AppUpdateChecker implements WebServiceResponseListener {


    Context context;
    OnAppUpdateAvaliable onAppUpdateAvaliable;

    public void setOnAppUpdateAvaliable(OnAppUpdateAvaliable onAppUpdateAvaliable) {
        this.onAppUpdateAvaliable = onAppUpdateAvaliable;
    }

    public AppUpdateChecker(Context context, OnAppUpdateAvaliable onAppUpdateAvaliable) {
        this.context = context;
        this.onAppUpdateAvaliable = onAppUpdateAvaliable;

    }

    public void checkAppUpdate() {
        WebRequest webRequest = new WebRequest(1,
                URL_CHECK_APP_VERSION, WebRequest.GET_REQ);
        webRequest.send(context, this);
    }

    @Override
    public void onWebRequestCall(WebRequest webRequest) {
        MyApplication.getInstance().onWebRequestCall(webRequest);
    }

    @Override
    public void onWebRequestResponse(WebRequest webRequest) {
        handleAppUpdateResponse(webRequest.getResponseString());
    }


    private void handleAppUpdateResponse(String response) {
        if (response != null && !response.trim().isEmpty()) {
            AppUpdatemodal latestAppUpdatemodal = parseOurServerResponse(response);
            if (latestAppUpdatemodal == null) return;
            AppUpdateUtils.printLog(latestAppUpdatemodal.toString());
            if (isUpdateAvailable(latestAppUpdatemodal)) {
                if (onAppUpdateAvaliable != null) {
                    onAppUpdateAvaliable.appUpdateAvaliable(latestAppUpdatemodal);
                }
            }
        }
    }

    private AppUpdatemodal parseOurServerResponse(String response) {
        if (response != null && !response.trim().isEmpty()) {
            try {
                JSONObject resData = new JSONObject(response);
                int error = resData.getInt("error");
                if (error == 0) {
                    String version_name = resData.getString("version_name");
                    String version_code = resData.getString("version_code");
                    String version_desc = resData.getString("message");
                    String apk_download_link = resData.getString("apk_download_link");
                    try {
                        return new AppUpdatemodal(version_name, Integer.valueOf(version_code), true, version_desc,
                                apk_download_link);
                    } catch (NumberFormatException ignore) {

                    }
                }

            } catch (Exception e) {

            }
        }
        return null;
    }


    public interface OnAppUpdateAvaliable {
        void appUpdateAvaliable(AppUpdatemodal appUpdatemodal);
    }

    private Boolean isUpdateAvailable(AppUpdatemodal latestAppUpdatemodal) {
        AppUpdatemodal installedAppUpdateModal = new AppUpdatemodal();
        installedAppUpdateModal.setVersionCode(AppUpdateUtils.getAppInstalledVersionCode(context));
        installedAppUpdateModal.setVersion(AppUpdateUtils.getAppInstalledVersion(context));

        if (latestAppUpdatemodal.getVersionCode() != null && latestAppUpdatemodal.getVersionCode() > 0) {
            return latestAppUpdatemodal.getVersionCode() > installedAppUpdateModal.getVersionCode();
        }

        return false;
    }
}
