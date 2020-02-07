package com.lotus.utilites;

import android.os.Handler;
import android.os.HandlerThread;

import androidx.recyclerview.widget.DiffUtil;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lotus.model.MatchDetailModel;
import com.lotus.model.MatchMarketModel;
import com.lotus.model.RunnersModel;
import com.lotus.model.SelectionNameModel;
import com.lotus.model.web_response.MarketListingResponseModel;
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

/**
 * @author Sunil kumar Yadav
 * @Since 22/10/18
 */
public class MarketListHelper {
    public static final int UPDATE_INTERVAL = 1000;
    static final Map<String, MarketListHelper> marketListHelperMap = new ConcurrentHashMap<>();
    private boolean isUpdaterStart = false;
    private MainActivity context;
    private MatchDetailModel matchDetailModel;
    private WebRequestHelper webRequestHelper;
    private WebRequest previousRequest;
    private MarketListHelperListener marketListHelperListener;
    private WebServiceResponseListener webServiceResponseListener = new WebServiceResponseListener() {
        @Override
        public void onWebRequestCall(WebRequest webRequest) {
            context.onWebRequestCall(webRequest);
        }

        @Override
        public void onWebRequestResponse(WebRequest webRequest) {
            handleMarketListResponse(webRequest);
        }
    };
    private HandlerThread mHandlerThread = null;
    private Handler handler = null;

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            getMarketList();
        }
    };

    private MarketListHelper(MainActivity context) {
        this.context = context;
        webRequestHelper = new WebRequestHelper(context);
    }

    public static MarketListHelper getNewInstances(MainActivity context, MatchDetailModel
            matchDetailModel) {
        if (matchDetailModel == null) return null;
        MarketListHelper marketListHelper = null;
        if (marketListHelperMap.containsKey(matchDetailModel.getMatchid())) {
            marketListHelper = marketListHelperMap.get(matchDetailModel.getMatchid());
            marketListHelper.matchDetailModel = matchDetailModel;
            marketListHelper.context = context;
        } else {
            marketListHelper = new MarketListHelper(context);
            marketListHelper.matchDetailModel = matchDetailModel;
            marketListHelperMap.put(matchDetailModel.getMatchid(), marketListHelper);
        }
        return marketListHelper;
    }

    public void setMarketListHelperListener(MarketListHelperListener marketListHelperListener) {
        this.marketListHelperListener = marketListHelperListener;
    }


    private WebRequest getPendingSelectionName(String marketIds_selectionids) {
        previousRequest = webRequestHelper.getSelectionName(marketIds_selectionids, null);
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
        return previousRequest;
    }

    private void getMarketList() {

        if (matchDetailModel != null) {
            previousRequest = webRequestHelper.getMarketListing(matchDetailModel, webServiceResponseListener);
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

    private void updatePendingSelectionName(List<MatchMarketModel> matchMarketModels) {


        if (matchMarketModels != null) {
            StringBuilder stringBuilder = new StringBuilder();

            for (MatchMarketModel matchMarketModel : matchMarketModels) {
                if (matchMarketModel.getRunners() != null && matchMarketModel.getRunners().size() > 0) {
                    for (RunnersModel runnersModel : matchMarketModel.getRunners()) {
                        if (!runnersModel.isValidString(runnersModel.getRunnerName())) {
                            if (stringBuilder.length() > 0) {
                                stringBuilder.append(",");
                            }
                            stringBuilder.append(matchMarketModel.getMarketid()).append("-").append(runnersModel.getSelectionId());
                        }
                    }

                }
            }

            String emptySelectionNames = stringBuilder.toString();
            if (emptySelectionNames.trim().length() > 0) {
                WebRequest pendingSelectionName = getPendingSelectionName(emptySelectionNames);
                String response = pendingSelectionName.getResponseString();
                if (response != null && !response.isEmpty()) {
                    Gson gson = new Gson();
                    List<SelectionNameModel> selectionNameModelList = gson.fromJson(response,
                            new TypeToken<List<SelectionNameModel>>() {
                            }.getType());
                    if (selectionNameModelList != null && selectionNameModelList.size() > 0) {

                        for (SelectionNameModel byMarketIdsModel : selectionNameModelList) {
                            boolean updateDone = false;
                            List<MatchMarketModel> markets = matchMarketModels;
                            for (MatchMarketModel market : markets) {
                                if (market.getMarketid().equals(byMarketIdsModel.getActualMarketId())) {
                                    for (RunnersModel runnersModel : market.getRunners()) {
                                        if (runnersModel.getSelectionId().equals(byMarketIdsModel.getActualSelectionId())) {
                                            runnersModel.setRunnerName(byMarketIdsModel.getRunnername());
                                            updateDone = true;
                                        }
                                        if (updateDone)
                                            break;
                                    }
                                }
                                if (updateDone)
                                    break;
                            }
                        }
                    }
                }
            }
        }


    }

    private void handleMarketListResponse(WebRequest webRequest) {
        final MarketListingResponseModel responseModel = webRequest.getResponsePojoSimple(MarketListingResponseModel.class);
        if (responseModel != null && responseModel.getData() != null) {
            final List<MatchMarketModel> oldMarketData = new ArrayList<>();
            if (matchDetailModel.getMarkets() != null) {
                for (MatchMarketModel matchMarketModel : matchDetailModel.getMarkets()) {
                    oldMarketData.add(matchMarketModel.copyOf(matchMarketModel));
                }
            }

            List<MatchMarketModel> newData = responseModel.getData();

            if (newData != null) {

                for (MatchMarketModel newDatum : newData) {
                    int i = oldMarketData.indexOf(newDatum);
                    if (i == -1) {
                        oldMarketData.add(newDatum);
                    } else {
                        if (newDatum.isValidString(newDatum.getMarket_name())) {
                            oldMarketData.get(i).setMarket_name(newDatum.getMarket_name());
                        }
                        oldMarketData.get(i).setIs_favourite(newDatum.getIs_favourite());
                        oldMarketData.get(i).setVisibility(newDatum.getVisibility());
                    }
                }

                updatePendingSelectionName(oldMarketData);

                final DiffCallBackMatchMarketModel diffCallBackMatchMarketModel = new DiffCallBackMatchMarketModel(matchDetailModel.getMarkets(),
                        oldMarketData);
                final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallBackMatchMarketModel);

                if (marketListHelperListener != null) {
                    if (context != null && !context.isFinishing()) {
                        context.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (marketListHelperListener != null)
                                    marketListHelperListener.onMarketListUpdate(oldMarketData, diffResult);
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

    public void startMarketListUpdater() {
        stopMarketListUpdater(false);
        if (matchDetailModel != null) {
            isUpdaterStart = true;
            mHandlerThread = new HandlerThread("HandlerThread_" + getClass().getSimpleName());
            mHandlerThread.start();
            handler = new Handler(mHandlerThread.getLooper());
            callNext(1);
        }

    }

    public void stopMarketListUpdater(boolean needClean) {
        if (previousRequest != null) {
            previousRequest.cancel();
        }
        if (mHandlerThread != null) {
            mHandlerThread.quit();
        }
        isUpdaterStart = false;
        if (needClean && matchDetailModel != null) {
            marketListHelperMap.remove(matchDetailModel.getMatchid());
        }
    }


    public interface MarketListHelperListener {

        void onMarketListUpdate(List<MatchMarketModel> newData, DiffUtil.DiffResult diffResult);
    }
}
