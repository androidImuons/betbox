package com.lotus.utilites;

import android.os.Handler;
import android.os.HandlerThread;

import androidx.recyclerview.widget.DiffUtil;

import com.lotus.model.IndianSessionModel;
import com.lotus.model.MatchDetailModel;
import com.lotus.model.web_response.IndianSessionResponseModel;
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

public class MarketIndianSessionHelper {

    public static final String TAG = MarketIndianSessionHelper.class.getSimpleName();
    public static final int UPDATE_INTERVAL = 2000;
    static final Map<String, MarketIndianSessionHelper> matchMarketMap = new ConcurrentHashMap<>();
    private boolean isUpdaterStart = false;
    private MainActivity context;
    private MatchDetailModel matchDetailModel;
    private MarketIndianSessionListener marketIndianSessionListener;
    private WebRequestHelper webRequestHelper;
    private WebRequest previousRequest;
    private WebServiceResponseListener webServiceResponseListener = new WebServiceResponseListener() {
        @Override
        public void onWebRequestCall(WebRequest webRequest) {
            context.onWebRequestCall(webRequest);
        }

        @Override
        public void onWebRequestResponse(WebRequest webRequest) {
            handleMarketIndianSessionResponse(webRequest);
        }
    };
    private HandlerThread mHandlerThread = null;
    private Handler handler = null;

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            getMarketIndianSession();
        }
    };

    private MarketIndianSessionHelper(MainActivity context) {
        this.context = context;
        webRequestHelper = new WebRequestHelper(context);
    }

    public static MarketIndianSessionHelper getNewInstances(MainActivity context, MatchDetailModel
            matchDetailModel) {

        if (matchDetailModel == null) return null;
        MarketIndianSessionHelper matchMarketHelper = null;
        if (matchMarketMap.containsKey(matchDetailModel.getMatchid())) {
            matchMarketHelper = matchMarketMap.get(matchDetailModel.getMatchid());
            matchMarketHelper.matchDetailModel = matchDetailModel;
            matchMarketHelper.context=context;
        } else {
            matchMarketHelper = new MarketIndianSessionHelper(context);
            matchMarketHelper.matchDetailModel = matchDetailModel;
            matchMarketMap.put(matchDetailModel.getMatchid(), matchMarketHelper);
        }
        return matchMarketHelper;
    }

    public void setMarketIndianSessionListener(MarketIndianSessionListener marketIndianSessionListener) {
        this.marketIndianSessionListener = marketIndianSessionListener;
    }


    private void getMarketIndianSession() {
        if (matchDetailModel != null) {
            if (matchDetailModel.getMarkets() == null || matchDetailModel.getMarkets().size() == 0) {
                callNext(3000);
                return;
            }
            previousRequest = webRequestHelper.getMatchIndianSession(matchDetailModel.getMatchid(),
                    matchDetailModel.getMarkets().get(0).getMarketid(), webServiceResponseListener);
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

    private void handleMarketIndianSessionResponse(WebRequest webRequest) {
        IndianSessionResponseModel responseModel = webRequest.getResponsePojoSimple(IndianSessionResponseModel.class);
        if (responseModel != null) {
            final List<IndianSessionModel> data = responseModel.getData();
            for (int i = 0; i < data.size(); i++) {
                if (data.get(i) == null) {
                    data.remove(i);
                    i--;
                }
            }
            if (matchDetailModel == null) return;
            synchronized (matchDetailModel) {
                final DiffCallBackIndianSessionModel diffCallback = new DiffCallBackIndianSessionModel(matchDetailModel.getSessions(),
                        data);
                final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);

                if (marketIndianSessionListener != null) {
                    if (context != null && !context.isFinishing()) {
                        context.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (marketIndianSessionListener != null)
                                    marketIndianSessionListener.onMarketIndianSessionUpdate(data, diffResult);
                            }
                        });
                    }
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


    public void startMarketIndianSessionHelper() {
        stopMarketIndianSessionHelper(false);
        if (matchDetailModel != null) {
            isUpdaterStart = true;
            mHandlerThread = new HandlerThread("HandlerThread_" + getClass().getSimpleName());
            mHandlerThread.start();
            handler = new Handler(mHandlerThread.getLooper());
            callNext(1);
        }

    }

    public void stopMarketIndianSessionHelper(boolean needClean) {
        if (previousRequest != null) {
            previousRequest.cancel();
        }
        if (mHandlerThread != null) {
            mHandlerThread.quit();
        }
        isUpdaterStart = false;
        if (needClean && matchDetailModel != null) {
            matchMarketMap.remove(matchDetailModel.getMatchid());
        }
    }

    public interface MarketIndianSessionListener {
        void onMarketIndianSessionUpdate(List<IndianSessionModel> data, DiffUtil.DiffResult diffResult);
    }

}
