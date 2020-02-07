package com.lotus.utilites;

import android.os.Handler;
import android.os.HandlerThread;

import com.lotus.model.LoggedInUserModel;
import com.lotus.rest.WebRequestHelper;
import com.lotus.ui.main.MainActivity;
import com.medy.retrofitwrapper.WebRequest;
import com.medy.retrofitwrapper.WebServiceException;
import com.medy.retrofitwrapper.WebServiceResponseListener;

import java.io.IOException;
import java.util.HashSet;

import okhttp3.ResponseBody;
import retrofit2.Response;

/**
 * @author Sunil kumar Yadav
 * @Since 15/10/18
 */
public class CheckLoggedInUserHelper {
    public static final int UPDATE_INTERVAL = 1000;

    private static CheckLoggedInUserHelper instance;
    private boolean isUpdaterStart = false;
    private MainActivity context;

    private CheckLoggedInUserHelperListener checkLoggedInUserHelperListener;
    private WebRequestHelper webRequestHelper;
    private WebRequest previousRequest;
    private WebServiceResponseListener webServiceResponseListener = new WebServiceResponseListener() {
        @Override
        public void onWebRequestCall(WebRequest webRequest) {
            context.onWebRequestCall(webRequest);
        }

        @Override
        public void onWebRequestResponse(WebRequest webRequest) {
            handleLoggedInUserResponse(webRequest);
        }
    };
    private HandlerThread mHandlerThread = null;
    private Handler handler = null;

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            getLoggedInUserDetail();
        }
    };

    private CheckLoggedInUserHelper(MainActivity context) {
        this.context = context;
        webRequestHelper = new WebRequestHelper(context);
    }

    public static CheckLoggedInUserHelper getNewInstances(MainActivity context) {
        if (context == null) return null;
        if (instance == null) {
            instance = new CheckLoggedInUserHelper(context);
        }
        instance.context = context;
        return instance;
    }

    public void setCheckLoggedInUserHelperListener(CheckLoggedInUserHelperListener checkLoggedInUserHelperListener) {
        this.checkLoggedInUserHelperListener = checkLoggedInUserHelperListener;
    }


    private void getLoggedInUserDetail() {
        previousRequest = webRequestHelper.checkUserLoggedIn(webServiceResponseListener);
        webServiceResponseListener.onWebRequestCall(previousRequest);
        try {
            Response<ResponseBody> response = previousRequest.generateCall(context).execute();
            int responseCode = -1;
            HashSet<String> cookies = null;
            String webRequestResponse = null;
            if (response != null) {
                responseCode = response.code();
                okhttp3.Response raw = response.raw();
                if (!raw.headers("Set-Cookie").isEmpty()) {
                    cookies = new HashSet<>();
                    for (String header : raw.headers("Set-Cookie")) {
                        cookies.add(header);
                    }
                }
                try {
                    if (response.isSuccessful() && response.body() != null) {
                        webRequestResponse = response.body().string().trim();
                    } else if (response.errorBody() != null) {
                        webRequestResponse = response.errorBody().string().trim();
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            previousRequest.onRequestCompleted(null, responseCode, webRequestResponse, cookies);
            previousRequest.printResponseLog();
        } catch (IOException | WebServiceException e) {
            e.printStackTrace();
            previousRequest.onRequestCompleted(e, -1, null, null);
            previousRequest.printResponseLog();
        } catch (OutOfMemoryError e) {
            System.gc();
            String msg = e.getMessage() == null ? "Exception in call webRequest" : e.getMessage();
            WebServiceException e1 = new WebServiceException(msg,
                    WebServiceException.EXCEPTION_IN_CALL);

            previousRequest.onRequestCompleted(e1, -1, null, null);
            previousRequest.printResponseLog();
        }
        webServiceResponseListener.onWebRequestResponse(previousRequest);
    }

    private void handleLoggedInUserResponse(WebRequest webRequest) {
        final LoggedInUserModel responsePojoSimple = webRequest.getResponsePojoSimple(LoggedInUserModel.class);
        if (responsePojoSimple != null) {
            if (checkLoggedInUserHelperListener != null) {
                if (context != null && !context.isFinishing()) {
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (checkLoggedInUserHelperListener != null)
                                checkLoggedInUserHelperListener.onLoggedInUserUpdate(responsePojoSimple);
                        }
                    });
                }
            }
        }
        if (webRequest.getWebRequestException() != null && webRequest.getWebRequestException() instanceof IOException) {
            callNext(10000);
            return;
        }
        callNext(UPDATE_INTERVAL);


    }

    private void callNext(long time) {
        if (isUpdaterStart) {
            handler.removeCallbacks(runnable);
            handler.postDelayed(runnable, time);
        }
    }

    public void startCheckLoggedInUserHelper() {
        stopCheckLoggedInUserHelper();
        if (context.getMyApplication().getUserModel() != null) {
            isUpdaterStart = true;
            mHandlerThread = new HandlerThread("HandlerThread_" + getClass().getSimpleName());
            mHandlerThread.start();
            handler = new Handler(mHandlerThread.getLooper());
            callNext(1);
        }

    }

    public void stopCheckLoggedInUserHelper() {
        if (previousRequest != null) {
            previousRequest.cancel();
        }
        if (mHandlerThread != null) {
            mHandlerThread.quit();
        }
        isUpdaterStart = false;
    }

    public interface CheckLoggedInUserHelperListener {
        void onLoggedInUserUpdate(LoggedInUserModel loggedInUserModel);
    }
}
