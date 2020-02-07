package com.lotus.utilites;

import android.os.Handler;
import android.os.HandlerThread;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.lotus.model.UserBalanceModel;
import com.lotus.model.UserModel;
import com.lotus.rest.WebRequestHelper;
import com.lotus.ui.main.MainActivity;
import com.medy.retrofitwrapper.WebRequest;
import com.medy.retrofitwrapper.WebServiceException;
import com.medy.retrofitwrapper.WebServiceResponseListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashSet;

import okhttp3.ResponseBody;
import retrofit2.Response;

/**
 * @author Sunil kumar Yadav
 * @Since 12/10/18
 */
public class UserBalanceHelper {
    public static final int UPDATE_INTERVAL = 1000;

    private static UserBalanceHelper instance;
    private boolean isUpdaterStart = false;
    private MainActivity context;

    private WebRequestHelper webRequestHelper;
    private WebRequest previousRequest;
    private WebServiceResponseListener webServiceResponseListener = new WebServiceResponseListener() {
        @Override
        public void onWebRequestCall(WebRequest webRequest) {
            context.onWebRequestCall(webRequest);
        }

        @Override
        public void onWebRequestResponse(WebRequest webRequest) {
            handleBalanceResponse(webRequest);
        }
    };

    private HandlerThread mHandlerThread = null;
    private Handler handler = null;

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            getUserBalance();
        }
    };


    private UserBalanceHelper(MainActivity context) {
        this.context = context;
        webRequestHelper = new WebRequestHelper(context);
    }

    public static UserBalanceHelper getNewInstances(MainActivity context) {
        if (context == null) return null;
        if (instance == null) {
            instance = new UserBalanceHelper(context);
        }
        instance.context = context;
        return instance;
    }


    private void getUserBalance() {
        UserModel userModel = context.getMyApplication().getUserModel();
        if (userModel != null) {
            previousRequest = webRequestHelper.getChipDataById(userModel, webServiceResponseListener);
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
        } else {
            stopUserDataUpdater();
        }
    }

    private void handleBalanceResponse(WebRequest webRequest) {
        String responseString = webRequest.getResponseString();
        if (context.isValidString(responseString)) {
            try {
                JSONObject jsonObject = new JSONObject(responseString);
                JSONArray betLibility = jsonObject.getJSONArray("betLibility");
                if (betLibility.length() > 0) {
                    String data = betLibility.getJSONObject(0).toString();
                    if (context.isValidString(data)) {
                        final UserBalanceModel userBalanceModel = new Gson().fromJson(data, UserBalanceModel.class);
                        if (userBalanceModel != null) {
                            final UserBalanceModel oldBalanceModel = context.getMyApplication().getUserBalanceModel();
                            if (oldBalanceModel == null || oldBalanceModel.needUpdateBalance(userBalanceModel)) {
                                if (oldBalanceModel != null)
                                    userBalanceModel.setMarqueMsg(oldBalanceModel.getMarqueMsg());
                                if (context != null && !context.isFinishing()) {
                                    context.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            context.getMyApplication().updateUserBalance(userBalanceModel);
                                            callNext(UPDATE_INTERVAL);
                                        }
                                    });
                                }
                                return;
                            }
                        }

                    }
                }

            } catch (JSONException | JsonSyntaxException e) {
                e.printStackTrace();
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

    public void startUserDataHelperUpdater() {
        stopUserDataUpdater();
        if (context.getMyApplication().getUserModel() != null) {
            isUpdaterStart = true;
            mHandlerThread = new HandlerThread("HandlerThread_" + getClass().getSimpleName());
            mHandlerThread.start();
            handler = new Handler(mHandlerThread.getLooper());
            callNext(1);
        }

    }

    public void stopUserDataUpdater() {
        if (previousRequest != null) {
            previousRequest.cancel();
        }
        if (mHandlerThread != null) {
            mHandlerThread.quit();
        }
        isUpdaterStart = false;
    }
}
