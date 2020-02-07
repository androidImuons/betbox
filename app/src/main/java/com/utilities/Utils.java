package com.utilities;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import com.base.BaseActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.lotus.BuildConfig;

import java.lang.reflect.Field;

/**
 * Created by Sunil kumar yadav on 21/4/18.
 */

public class Utils {

    public static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    public static boolean isDebugBuild (Context context) {
        if (context == null) return false;
        try {
            String packageName = context.getPackageName();

            Bundle bundle = context.getPackageManager().getApplicationInfo(
                    context.getPackageName(), PackageManager.GET_META_DATA).metaData;
            String manifest_pkg = null;
            if (bundle != null) {
                manifest_pkg = bundle.getString("manifest_pkg", null);
            }
            if (manifest_pkg != null) {
                packageName = manifest_pkg;
            }

            final Class<?> buildConfig = Class.forName(packageName + ".BuildConfig");
            final Field DEBUG = buildConfig.getField("DEBUG");
            DEBUG.setAccessible(true);
            return DEBUG.getBoolean(null);
        } catch (final Throwable t) {
            final String message = t.getMessage();
            if (message != null && message.contains("BuildConfig")) {
                // Proguard obfuscated build. Most likely a production build.
                return false;
            } else {
                return BuildConfig.DEBUG;
            }
        }
    }

    public static int getGooglePlayServicesAvailableStatus (Context context) {
        GoogleApiAvailability api = GoogleApiAvailability.getInstance();
        return api.isGooglePlayServicesAvailable(context);
    }

    public static boolean isValidPlayServices (Context context) {
        int resultCode = getGooglePlayServicesAvailableStatus(context);

        if (resultCode != ConnectionResult.SUCCESS) {
            GoogleApiAvailability instance = GoogleApiAvailability.getInstance();
            BaseActivity context1 = ((BaseActivity) context);
            if (instance.isUserResolvableError(resultCode))
                instance.getErrorDialog(context1,
                        resultCode, PLAY_SERVICES_RESOLUTION_REQUEST).show();
            else {
                context1.showCustomToast("This device is not supported.");
                (context1).finish();
            }
            return false;
        }
        return true;
    }


    public static void printLog (Context context, String TAG, String msg) {
        if (context != null && isDebugBuild(context) && TAG != null && msg != null) {
            Log.e(Utils.class.getSimpleName() + ": ", TAG + ": " + msg);
        }
    }

    public static void showOtp (Context context, String msg) {
        if (msg == null) return;
        //Toast.makeText(context, "OTP is: " + msg, Toast.LENGTH_LONG).show();
    }


}
