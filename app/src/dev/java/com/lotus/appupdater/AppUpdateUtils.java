package com.lotus.appupdater;

import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import com.lotus.ui.MyApplication;
import com.utilities.Utils;


/**
 * Created by ubuntu on 29/5/17.
 */

public class AppUpdateUtils {
    static final String PLAY_STORE_URL = "https://play.google.com/store/apps/details?id=%s";

    public static final String TAG = AppUpdateUtils.class.getSimpleName();

    public static void printLog(String msg) {
        if (msg == null) return;
        if (Utils.isDebugBuild(MyApplication.getInstance())) {
            Log.e(TAG, msg);
        }
    }

    static String getPlayStoreUrl(Context context) {
        return String.format(PLAY_STORE_URL, context.getPackageName());
    }


    public static String getAppInstalledVersion(Context context) {
        String version = "0.0.0.0";
        try {
            version = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return version;
    }

    public static Integer getAppInstalledVersionCode(Context context) {
        Integer versionCode = 0;

        try {
            versionCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return versionCode;
    }

}
