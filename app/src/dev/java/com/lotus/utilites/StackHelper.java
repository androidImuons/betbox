package com.lotus.utilites;

import android.os.Handler;
import android.os.HandlerThread;

import com.lotus.model.StackModel;
import com.lotus.model.UserModel;
import com.lotus.model.web_response.StackResponseModel;
import com.lotus.rest.WebRequestHelper;
import com.lotus.ui.main.MainActivity;
import com.medy.retrofitwrapper.WebRequest;
import com.medy.retrofitwrapper.WebServiceException;
import com.medy.retrofitwrapper.WebServiceResponseListener;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Response;

public class StackHelper {

    public static final int UPDATE_INTERVAL = 5000;
    private static StackHelper instance;
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
            if (context != null && !context.isFinishing()) {
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        context.dismissProgressBar();
                    }
                });
            }
            handleGetPageStacktResponse(webRequest);

        }
    };

    private HandlerThread mHandlerThread = null;
    private Handler handler = null;

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            getStakesFromServer();
        }
    };

    private StackHelper(MainActivity context) {
        this.context = context;
        webRequestHelper = new WebRequestHelper(context);
    }

    public static StackHelper getInstance(MainActivity context) {
        if (instance == null) {
            instance = new StackHelper(context);
        }
        instance.context = context;
        return instance;
    }

    public void getStakesFromServer() {
        UserModel userModel = context.getMyApplication().getUserModel();
        if (userModel != null) {
            if (context != null && !context.isFinishing()) {
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        context.displayProgressBar(false);
                    }
                });
            }
            previousRequest = webRequestHelper.get_stake_setting(webServiceResponseListener);
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
            stopStackUpdater();
        }
    }

    private void handleGetPageStacktResponse(WebRequest webRequest) {
        if (webRequest == null) return;
        StackResponseModel responseModel = webRequest.getResponsePojoSimple(StackResponseModel.class);
        if (responseModel != null) {
            final StackModel newStack = responseModel.getData();
            List<String> match_stake = newStack.getMatch_stake();
            if (match_stake != null && match_stake.size() > 5) {
                for (int i = 5; i < match_stake.size(); i++) {
                    match_stake.remove(i);
                    i--;
                }
            }
            if (context != null && !context.isFinishing()) {
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        context.getMyApplication().updateUserStack(newStack);
                    }
                });
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


    public void startStackUpdater() {
        stopStackUpdater();
        UserModel userModel = context.getMyApplication().getUserModel();
        if (userModel != null) {
            isUpdaterStart = true;
            mHandlerThread = new HandlerThread("HandlerThread_" + getClass().getSimpleName());
            mHandlerThread.start();
            handler = new Handler(mHandlerThread.getLooper());
            callNext(1);
        }
    }

    public void stopStackUpdater() {
        if (previousRequest != null) {
            previousRequest.cancel();
        }
        if (mHandlerThread != null) {
            mHandlerThread.quit();
        }
        isUpdaterStart = false;
    }


}
