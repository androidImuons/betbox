package com.lotus.utilites;

import android.os.Handler;
import android.os.HandlerThread;

import com.google.gson.reflect.TypeToken;
import com.lotus.model.MatchDetailModel;
import com.lotus.model.MatchScoreModel;
import com.lotus.model.MatchScoreModel2;
import com.lotus.rest.WebRequestHelper;
import com.lotus.ui.main.MainActivity;
import com.medy.retrofitwrapper.WebRequest;
import com.medy.retrofitwrapper.WebServiceException;
import com.medy.retrofitwrapper.WebServiceResponseListener;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import okhttp3.ResponseBody;
import retrofit2.Response;

/**
 * @author Sunil kumar Yadav
 * @Since 23/5/18
 */
public class MatchScoreHelper {
    public static final int UPDATE_INTERVAL = 1000;
    static final Map<String, MatchScoreHelper> matchScoreMap = new ConcurrentHashMap<>();
    private boolean isUpdaterStart = false;
    private MainActivity context;
    private MatchDetailModel matchDetailModel;
    private WebRequestHelper webRequestHelper;
    private WebRequest previousRequest;
    private WebRequest previousRequest2;
    private MatchScoreHelperListener matchScoreHelperListener;
    private WebServiceResponseListener webServiceResponseListener = new WebServiceResponseListener() {
        @Override
        public void onWebRequestCall(WebRequest webRequest) {
            context.onWebRequestCall(webRequest);
        }

        @Override
        public void onWebRequestResponse(WebRequest webRequest) {
            handleMatchScoreResponse(webRequest);
        }
    };
    private WebServiceResponseListener webServiceResponseListener2 = new WebServiceResponseListener() {
        @Override
        public void onWebRequestCall(WebRequest webRequest) {
            context.onWebRequestCall(webRequest);
        }

        @Override
        public void onWebRequestResponse(WebRequest webRequest) {
            handleMatchScoreResponse2(webRequest);
        }
    };
    private HandlerThread mHandlerThread = null;
    private Handler handler = null;

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
           // getMatchScore();
        }
    };

    private HandlerThread mHandlerThread2 = null;
    private Handler handler2 = null;

    Runnable runnable2 = new Runnable() {
        @Override
        public void run() {
            getMatchScore2();
        }
    };


    private MatchScoreHelper(MainActivity context) {
        this.context = context;
        webRequestHelper = new WebRequestHelper(context);
    }

    public static MatchScoreHelper getNewInstances(MainActivity context, MatchDetailModel
            matchDetailModel) {
        if (matchDetailModel == null) return null;
        MatchScoreHelper matchScoreHelper = null;
        if (matchScoreMap.containsKey(matchDetailModel.getMatchid())) {
            matchScoreHelper = matchScoreMap.get(matchDetailModel.getMatchid());
            matchScoreHelper.matchDetailModel = matchDetailModel;
            matchScoreHelper.context = context;
        } else {
            matchScoreHelper = new MatchScoreHelper(context);
            matchScoreHelper.matchDetailModel = matchDetailModel;
            matchScoreMap.put(matchDetailModel.getMatchid(), matchScoreHelper);
        }
        return matchScoreHelper;
    }

    public void setMatchScoreHelperListener(MatchScoreHelperListener matchScoreHelperListener) {
        this.matchScoreHelperListener = matchScoreHelperListener;
    }

    private void getMatchScore() {

        if (matchDetailModel != null) {
            previousRequest = webRequestHelper.getMatchScore(matchDetailModel, webServiceResponseListener);
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

    }

    private void getMatchScore2() {

        if (matchDetailModel != null) {
            previousRequest2 = webRequestHelper.getMatchScore2(matchDetailModel, webServiceResponseListener2);
            webServiceResponseListener2.onWebRequestCall(previousRequest2);
            try {
                Response<ResponseBody> response = previousRequest2.generateCall(context).execute();
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
                previousRequest2.onRequestCompleted(null, responseCode, webRequestResponse, cookies);
                previousRequest2.printResponseLog();
            } catch (IOException | WebServiceException e) {
                e.printStackTrace();
                previousRequest2.onRequestCompleted(e, -1, null, null);
                previousRequest2.printResponseLog();
            } catch (OutOfMemoryError e) {
                System.gc();
                String msg = e.getMessage() == null ? "Exception in call webRequest" : e.getMessage();
                WebServiceException e1 = new WebServiceException(msg,
                        WebServiceException.EXCEPTION_IN_CALL);

                previousRequest.onRequestCompleted(e1, -1, null, null);
                previousRequest.printResponseLog();
            }
            webServiceResponseListener2.onWebRequestResponse(previousRequest2);
        }

    }


    private void handleMatchScoreResponse(WebRequest webRequest) {
        String response = webRequest.getResponseString();
        if (context.isValidString(response)) {
            final MatchScoreModel matchScoreModel = webRequest.getResponsePojoSimple(MatchScoreModel.class);
            if (matchScoreHelperListener != null) {
                if (context != null && !context.isFinishing()) {
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (matchScoreHelperListener != null)
                                matchScoreHelperListener.onMatchScoreUpdate(matchScoreModel);
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

    private void handleMatchScoreResponse2(WebRequest webRequest) {
        String response = webRequest.getResponseString();
        if (context.isValidString(response)) {

            List<MatchScoreModel2> responseModel = webRequest.getResponsePojoSimpleList(new TypeToken<List<MatchScoreModel2>>() {
            });
            final MatchScoreModel2 matchScoreModel = (responseModel != null && responseModel.size() > 0) ?
                    responseModel.get(0) : null;

            if (matchScoreHelperListener != null) {
                if (context != null && !context.isFinishing()) {
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (matchScoreHelperListener != null)
                                matchScoreHelperListener.onMatchScoreUpdate2(matchScoreModel);
                        }
                    });
                }
            }

        }
        if (webRequest.getWebRequestException() != null && webRequest.getWebRequestException() instanceof IOException) {
            callNext2(10000);
            return;
        }
        callNext2(UPDATE_INTERVAL);
    }


    private void callNext(long time) {
        if (isUpdaterStart) {
            handler.removeCallbacks(runnable);
            handler.postDelayed(runnable, time);
        }
    }

    private void callNext2(long time) {
        if (isUpdaterStart) {
            handler2.removeCallbacks(runnable2);
            handler2.postDelayed(runnable2, time);
        }
    }

    public void startMatchScoreUpdater() {
        stopMatchScoreUpdater(false);
        if (matchDetailModel != null) {
            isUpdaterStart = true;
            mHandlerThread = new HandlerThread("HandlerThread_" + getClass().getSimpleName());
            mHandlerThread.start();
            handler = new Handler(mHandlerThread.getLooper());

            mHandlerThread2 = new HandlerThread("HandlerThread_" + getClass().getSimpleName());
            mHandlerThread2.start();
            handler2 = new Handler(mHandlerThread2.getLooper());

            callNext(1);
            callNext2(1);
        }

    }

    public void stopMatchScoreUpdater(boolean needClean) {
        if (previousRequest != null) {
            previousRequest.cancel();
        }
        if (previousRequest2 != null) {
            previousRequest2.cancel();
        }
        if (mHandlerThread != null) {
            mHandlerThread.quit();
        }
        if (mHandlerThread2 != null) {
            mHandlerThread2.quit();
        }
        isUpdaterStart = false;
        if (needClean && matchDetailModel != null) {
            matchScoreMap.remove(matchDetailModel.getMatchid());
        }
    }


    public interface MatchScoreHelperListener {
        void onMatchScoreUpdate(MatchScoreModel responseModel);

        void onMatchScoreUpdate2(MatchScoreModel2 responseModel);
    }
}
