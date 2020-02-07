package com.lotus.utilites;

import android.os.Handler;
import android.os.HandlerThread;

import androidx.recyclerview.widget.DiffUtil;

import com.lotus.model.MarketWinLossModel;
import com.lotus.model.MatchDetailModel;
import com.lotus.model.MatchMarketModel;
import com.lotus.model.RunnersModel;
import com.lotus.model.request_model.MarketWinLossRequestModel;
import com.lotus.model.web_response.MarketWinLossResponseModel;
import com.lotus.rest.WebRequestHelper;
import com.lotus.ui.main.MainActivity;
import com.medy.retrofitwrapper.WebRequest;
import com.medy.retrofitwrapper.WebServiceException;
import com.medy.retrofitwrapper.WebServiceResponseListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import okhttp3.ResponseBody;
import retrofit2.Response;

public class MarketWinLossHelper {

    public static final String TAG = MarketWinLossHelper.class.getSimpleName();
    public static final int UPDATE_INTERVAL = 2000;
    static final Map<String, MarketWinLossHelper> matchMarketMap = new ConcurrentHashMap<>();
    private boolean isUpdaterStart = false;
    private MainActivity context;
    private MatchDetailModel matchDetailModel;
    private MarketWinLossUpdaterListener marketWinLossUpdaterListener;
    private WebRequestHelper webRequestHelper;
    private WebRequest previousRequest;
    private WebServiceResponseListener webServiceResponseListener = new WebServiceResponseListener() {
        @Override
        public void onWebRequestCall(WebRequest webRequest) {
            context.onWebRequestCall(webRequest);
        }

        @Override
        public void onWebRequestResponse(WebRequest webRequest) {
            handleMarketWinnLossResponse(webRequest);
        }
    };
    private HandlerThread mHandlerThread = null;
    private Handler handler = null;

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            getMarketWinLoss();
        }
    };


    private MarketWinLossHelper(MainActivity context) {
        this.context = context;
        webRequestHelper = new WebRequestHelper(context);
    }

    public static MarketWinLossHelper getNewInstances(MainActivity context, MatchDetailModel
            matchDetailModel) {
        if (matchDetailModel == null) return null;
        MarketWinLossHelper matchMarketHelper = null;
        if (matchMarketMap.containsKey(matchDetailModel.getMatchid())) {
            matchMarketHelper = matchMarketMap.get(matchDetailModel.getMatchid());
            matchMarketHelper.matchDetailModel = matchDetailModel;
            matchMarketHelper.context=context;
        } else {
            matchMarketHelper = new MarketWinLossHelper(context);
            matchMarketHelper.matchDetailModel = matchDetailModel;
            matchMarketMap.put(matchDetailModel.getMatchid(), matchMarketHelper);
        }

        return matchMarketHelper;
    }

    public void setMarketWinLossUpdaterListener(MarketWinLossUpdaterListener marketWinLossUpdaterListener) {
        this.marketWinLossUpdaterListener = marketWinLossUpdaterListener;
    }


    private void getMarketWinLoss() {
        if (matchDetailModel != null) {
            MarketWinLossRequestModel requestModel = new MarketWinLossRequestModel();
            requestModel.matchId = matchDetailModel.getMatchid();
            requestModel.MarketId = matchDetailModel.getAllMarketIds();

            previousRequest = webRequestHelper.getMarketWinnLoss(requestModel, webServiceResponseListener);
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

    private void handleMarketWinnLossResponse(WebRequest webRequest) {
        final MarketWinLossResponseModel responseModel = webRequest.getResponsePojoSimple(MarketWinLossResponseModel.class);
        if (responseModel != null) {
            if (matchDetailModel == null) return;
            synchronized (matchDetailModel) {
                final List<MatchMarketModel> newDataList = new ArrayList<>();

                if (matchDetailModel.getMarkets() != null) {
                    for (MatchMarketModel matchMarketModel : matchDetailModel.getMarkets()) {
                        newDataList.add(matchMarketModel.copyOf(matchMarketModel));
                    }
                }

                if (newDataList.size() > 0) {
                    for (MarketWinLossModel datum : responseModel.getData()) {
                        for (MatchMarketModel matchMarketModel : newDataList) {
                            if (matchMarketModel.getMarketid().equals(datum.getMarketId())) {
                                for (MarketWinLossModel.RunnersBean runnersBean : datum.getRunners()) {
                                    for (RunnersModel runnersModel : matchMarketModel.getRunners()) {
                                        if (runnersModel.getSelectionId().equalsIgnoreCase(runnersBean.getSelectionId())) {
                                            runnersModel.setWinValue(runnersBean.getWinValue());
                                            runnersModel.setLossValue(runnersBean.getLossValue());
                                            break;
                                        }
                                    }
                                }
                                break;
                            }
                        }
                    }


                    for (MatchMarketModel matchMarketModel : newDataList) {
                        int i1 = matchDetailModel.getMarkets().indexOf(matchMarketModel);
                        if (i1 >= 0) {
                            DiffCallBackRunnerModel2 diffCallback =
                                    new DiffCallBackRunnerModel2(matchDetailModel.getMarkets().get(i1).getRunners(),
                                            matchMarketModel.getRunners());
                            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);
                            matchMarketModel.setDiffResultRunners(diffResult);
                        }
                    }

                    if (marketWinLossUpdaterListener != null) {
                        if (context != null && !context.isFinishing()) {
                            context.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (marketWinLossUpdaterListener != null)
                                        marketWinLossUpdaterListener.onMarketWinLossUpdate(newDataList);
                                }
                            });
                        }
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

    public void startMarketWinLossHelper() {
        stopMarketWinLossHelper(false);
        if (matchDetailModel != null) {
            isUpdaterStart = true;
            mHandlerThread = new HandlerThread("HandlerThread_" + getClass().getSimpleName());
            mHandlerThread.start();
            handler = new Handler(mHandlerThread.getLooper());
            callNext(1);
        }

    }

    public void stopMarketWinLossHelper(boolean needClean) {
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

    public interface MarketWinLossUpdaterListener {
        void onMarketWinLossUpdate(List<MatchMarketModel> data);
    }

}
