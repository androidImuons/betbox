package com.lotus.utilites;

import android.os.Handler;
import android.os.HandlerThread;

import androidx.recyclerview.widget.DiffUtil;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.lotus.model.IndianSessionModel;
import com.lotus.model.MatchBetFairSessionModel;
import com.lotus.model.MatchDetailModel;
import com.lotus.model.MatchMarketModel;
import com.lotus.model.OddsByMarketIdsModel;
import com.lotus.model.RunnersModel;
import com.lotus.model.web_response.MarketSessionResponseModel;
import com.lotus.rest.WebRequestHelper;
import com.lotus.ui.main.MainActivity;
import com.medy.retrofitwrapper.WebRequest;
import com.medy.retrofitwrapper.WebServiceException;
import com.medy.retrofitwrapper.WebServiceResponseListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import okhttp3.ResponseBody;
import retrofit2.Response;

public class MarketBetFairSessionHelper {

    public static final String TAG = MarketBetFairSessionHelper.class.getSimpleName();
    public static final int UPDATE_INTERVAL = 1000;
    static final Map<String, MarketBetFairSessionHelper> matchMarketMap = new ConcurrentHashMap<>();
    private boolean isUpdaterStart = false;
    private MainActivity context;
    private MatchDetailModel matchDetailModel;
    private MarketBetFairSessionListener marketBetFairSessionListener;
    private WebRequestHelper webRequestHelper;
    private WebRequest previousRequest;
    private WebServiceResponseListener webServiceResponseListener = new WebServiceResponseListener() {
        @Override
        public void onWebRequestCall(WebRequest webRequest) {
            context.onWebRequestCall(webRequest);
        }

        @Override
        public void onWebRequestResponse(WebRequest webRequest) {
            handleMarketBetFairSessionResponse(webRequest);
        }
    };
    private HandlerThread mHandlerThread = null;
    private Handler handler = null;

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            getMarketBetFairSession();
        }
    };


    private MarketBetFairSessionHelper(MainActivity context) {
        this.context = context;
        webRequestHelper = new WebRequestHelper(context);
    }

    public static MarketBetFairSessionHelper getNewInstances(MainActivity context, MatchDetailModel
            matchDetailModel) {
        if (matchDetailModel == null) return null;
        MarketBetFairSessionHelper matchMarketHelper = null;
        if (matchMarketMap.containsKey(matchDetailModel.getMatchid())) {
            matchMarketHelper = matchMarketMap.get(matchDetailModel.getMatchid());
            matchMarketHelper.matchDetailModel = matchDetailModel;
            matchMarketHelper.context=context;
        } else {
            matchMarketHelper = new MarketBetFairSessionHelper(context);
            matchMarketHelper.matchDetailModel = matchDetailModel;
            matchMarketMap.put(matchDetailModel.getMatchid(), matchMarketHelper);
        }
        return matchMarketHelper;
    }

    public void setMarketBetFairSessionListener(MarketBetFairSessionListener marketWinLossUpdaterListener) {
        this.marketBetFairSessionListener = marketWinLossUpdaterListener;
    }


    private void getMarketBetFairSession() {
        if (matchDetailModel != null) {
            String allMarketIds = matchDetailModel.getAllMarketIds();
            if (!matchDetailModel.isValidString(allMarketIds)) {
                callNext(3000);
                return;
            }
            previousRequest = webRequestHelper.getMatchBetFairSession(allMarketIds, webServiceResponseListener);
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

    private void handleMarketBetFairSessionResponse(WebRequest webRequest) {
        String responseString = webRequest.getResponseString();
        if (context.isValidString(responseString)) {
            try {
                JSONObject jsonObject = new JSONObject(responseString);
                Iterator<String> keys = jsonObject.keys();
                List<MatchBetFairSessionModel> marketSessions = new ArrayList<>();
                List<OddsByMarketIdsModel> marketOdds = new ArrayList<>();
                while (keys.hasNext()) {
                    String key = keys.next();
                    if (key.equalsIgnoreCase("session")) {
                        try {
                            JSONArray jsonArray = jsonObject.getJSONArray(key);
                            marketSessions = new Gson().fromJson(jsonArray.toString().trim(),
                                    new TypeToken<List<MatchBetFairSessionModel>>() {
                                    }.getType());
                        } catch (JSONException | JsonSyntaxException e) {

                        }

                    } else {
                        try {
                            JSONArray jsonArray = jsonObject.getJSONArray(key);
                            marketOdds = new Gson().fromJson(jsonArray.toString().trim(),
                                    new TypeToken<List<OddsByMarketIdsModel>>() {
                                    }.getType());
                        } catch (JSONException | JsonSyntaxException e) {

                        }
                    }
                }

                final MarketSessionResponseModel responseModel = new MarketSessionResponseModel();
                responseModel.setData(marketOdds);
                responseModel.setSession(marketSessions);

                if (matchDetailModel == null) return;
                synchronized (matchDetailModel) {


                        List<OddsByMarketIdsModel> oddsByMarketIdsModel = responseModel.getData();
                        if (oddsByMarketIdsModel != null) {
                            final List<MatchMarketModel> oldMarketData = new ArrayList<>();
                            if (matchDetailModel.getMarkets() != null) {
                                for (MatchMarketModel matchMarketModel : matchDetailModel.getMarkets()) {
                                    oldMarketData.add(matchMarketModel.copyOf(matchMarketModel));
                                }

                            }
                            if (oldMarketData.size() > 0) {
                                Set<String> runningMarkets=new HashSet<>();
                                for (OddsByMarketIdsModel byMarketIdsModel : oddsByMarketIdsModel) {
                                    for (MatchMarketModel matchMarketModel : oldMarketData) {
                                        if (matchMarketModel.getMarketid().equals(byMarketIdsModel.getId())) {
                                            runningMarkets.add(matchMarketModel.getMarketid());
                                            matchMarketModel.setInPlay(byMarketIdsModel.isInPlay());
                                            matchMarketModel.setStatus(byMarketIdsModel.getStatus());
                                            List<RunnersModel> runners = byMarketIdsModel.getRunners();
                                            if (runners == null || runners.size() == 0) {
                                                matchMarketModel.setMatchDisable(true);
                                            } else {
                                                matchMarketModel.setMatchDisable(false);
                                            }

                                            List<RunnersModel> oldDataList = new ArrayList<>();
                                            if (matchMarketModel.getRunners() != null) {
                                                for (RunnersModel runnersModel : matchMarketModel.getRunners()) {
                                                    oldDataList.add(runnersModel.copyOf(runnersModel));
                                                }
                                            }

                                            if (!matchMarketModel.isMatchDisable()) {
                                                List<RunnersModel> oldData = new ArrayList<>(oldDataList);
                                                if (runners != null) {
                                                    oldData.removeAll(runners);
                                                }
                                                for (RunnersModel oldDatum : oldData) {
                                                    int index = oldDataList.indexOf(oldDatum);
                                                    if (index == -1) continue;
                                                    oldDataList.remove(index);
                                                }

                                                if (runners != null) {
                                                    for (RunnersModel runnersModel : runners) {
                                                        int index = oldDataList.indexOf(runnersModel);
                                                        if (index == -1) {
                                                            oldDataList.add(runnersModel);
                                                        } else {
                                                            //   oldDataList.get(index).setRunnerName(runnersModel.getRunnerName());
                                                            oldDataList.get(index).setBack(runnersModel.getBack());
                                                            oldDataList.get(index).setLay(runnersModel.getLay());
                                                        }
                                                    }
                                                }
                                            }

                                            DiffCallBackRunnerModel3 diffCallback = new DiffCallBackRunnerModel3(matchMarketModel.getRunners(),
                                                    oldDataList);
                                            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);
                                            matchMarketModel.setDiffResultRunners(diffResult);
                                            matchMarketModel.setRunners(oldDataList);
                                            break;
                                        }

                                    }
                                }

                                matchDetailModel.setInPlay(oldMarketData.get(0).isInPlay());

                                for (MatchMarketModel matchMarketModel : oldMarketData) {
                                    if(!runningMarkets.contains(matchMarketModel.getMarketid())){
                                        matchMarketModel.setStatus("SUSPENDED");
                                    }
                                }
                            }

                            if (marketBetFairSessionListener != null) {
                                if (context != null && !context.isFinishing()) {
                                    context.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (marketBetFairSessionListener != null)
                                                marketBetFairSessionListener.onMarketBetFairSessionUpdate(oldMarketData);
                                        }
                                    });
                                }
                            }
                        }





                    List<MatchBetFairSessionModel> session = responseModel.getSession();
                    if (matchDetailModel.getSessions() != null && matchDetailModel.getSessions().size() > 0) {

                        if (session != null && session.size() > 0) {
                            final List<IndianSessionModel> indianSessionModels = matchDetailModel.updateIndianSessions(session.get(0));
                            final DiffCallBackIndianSessionModel2 diffCallback = new DiffCallBackIndianSessionModel2(matchDetailModel.getSessions(),
                                    indianSessionModels);
                            final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);
                            if (marketBetFairSessionListener != null) {
                                if (context != null && !context.isFinishing()) {
                                    context.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (marketBetFairSessionListener != null)
                                                marketBetFairSessionListener.onMarketBetFairFancyUpdate(indianSessionModels,
                                                        diffResult);
                                        }
                                    });
                                }
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


    public void startMarketBetFairSessionHelper() {
        stopMarketBetFairSessionHelper(false);
        if (matchDetailModel != null) {
            isUpdaterStart = true;
            mHandlerThread = new HandlerThread("HandlerThread_" + getClass().getSimpleName());
            mHandlerThread.start();
            handler = new Handler(mHandlerThread.getLooper());
            callNext(1);
        }

    }

    public void stopMarketBetFairSessionHelper(boolean needClean) {
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

    public interface MarketBetFairSessionListener {

        void onMarketBetFairFancyUpdate(List<IndianSessionModel> data, DiffUtil.DiffResult diffResult);

        void onMarketBetFairSessionUpdate(List<MatchMarketModel> oldDataList);
    }


}
