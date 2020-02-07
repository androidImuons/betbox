package com.lotus.utilites;

import android.os.Handler;
import android.os.HandlerThread;

import androidx.recyclerview.widget.DiffUtil;

import com.lotus.model.BetDataModel;
import com.lotus.model.MatchDetailModel;
import com.lotus.model.UserModel;
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
 * @Since 23/5/18
 */
public class GetBetDataHelper {
    public static final int UPDATE_INTERVAL = 1000;
    static final Map<String, GetBetDataHelper> matchScoreMap = new ConcurrentHashMap<>();
    private boolean isUpdaterStart = false;
    private MainActivity context;
    private MatchDetailModel matchDetailModel;
    private WebRequestHelper webRequestHelper;
    private WebRequest previousRequest;
    private BetDataHelperListener betDataHelperListener;
    private WebServiceResponseListener webServiceResponseListener = new WebServiceResponseListener() {
        @Override
        public void onWebRequestCall(WebRequest webRequest) {
            context.onWebRequestCall(webRequest);
        }

        @Override
        public void onWebRequestResponse(WebRequest webRequest) {
            handleBetDataResponse(webRequest);
        }
    };
    private HandlerThread mHandlerThread = null;
    private Handler handler = null;

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            getBetData();
        }
    };

    private GetBetDataHelper(MainActivity context) {
        this.context = context;
        webRequestHelper = new WebRequestHelper(context);
    }

    public static GetBetDataHelper getNewInstances(MainActivity context, MatchDetailModel
            matchDetailModel) {
        if (matchDetailModel == null) return null;
        GetBetDataHelper matchScoreHelper = null;
        if (matchScoreMap.containsKey(matchDetailModel.getMatchid())) {
            matchScoreHelper = matchScoreMap.get(matchDetailModel.getMatchid());
            matchScoreHelper.matchDetailModel = matchDetailModel;
            matchScoreHelper.context=context;
        } else {
            matchScoreHelper = new GetBetDataHelper(context);
            matchScoreHelper.matchDetailModel = matchDetailModel;
            matchScoreMap.put(matchDetailModel.getMatchid(), matchScoreHelper);
        }
        return matchScoreHelper;
    }

    public void setBetDataHelperListener(BetDataHelperListener betDataHelperListener) {
        this.betDataHelperListener = betDataHelperListener;
    }

    private void getBetData() {
        UserModel userModel = context.getMyApplication().getUserModel();
        if (userModel != null) {
            if (matchDetailModel != null) {
                previousRequest = webRequestHelper.getBetData(userModel, matchDetailModel, webServiceResponseListener);
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
        } else {
            stopBetDataUpdater(true);
        }

    }

    private void handleBetDataResponse(WebRequest webRequest) {
        BetDataModel responsePojoSimple = webRequest.getResponsePojoSimple(BetDataModel.class);
        if (responsePojoSimple != null) {
            final List<BetDataModel.BetUserData> matchedBet = new ArrayList<>();
            final List<BetDataModel.BetUserData> unMatchedBet = new ArrayList<>();
            final List<BetDataModel.BetUserData> fancyMatchedBet = new ArrayList<>();
            for (BetDataModel.BetUserData betUserData : responsePojoSimple.getBetUserData()) {
                if (betUserData.getIsMatched().equals("1")) {
                    matchedBet.add(betUserData);
                } else {
                    unMatchedBet.add(betUserData);
                }
            }
            fancyMatchedBet.addAll(responsePojoSimple.getFancyUserData());

            final DiffCallBackMatchedBetModel MatchDiffCallback = new DiffCallBackMatchedBetModel(matchDetailModel.getMatchedBet(),
                    matchedBet);
            final DiffUtil.DiffResult matchedDiffResult = DiffUtil.calculateDiff(MatchDiffCallback);

            final DiffCallBackUnMatchedBetModel UnMatchDiffCallback = new DiffCallBackUnMatchedBetModel(matchDetailModel.getUnMatchedBet(),
                    unMatchedBet);
            final DiffUtil.DiffResult unMatchedDiffResult = DiffUtil.calculateDiff(UnMatchDiffCallback);

            final DiffCallBackUnMatchedBetModel fancyDiffCallback = new DiffCallBackUnMatchedBetModel(matchDetailModel.getFancyMatchedBet(),
                    fancyMatchedBet);
            final DiffUtil.DiffResult fancyDiffResult = DiffUtil.calculateDiff(fancyDiffCallback);

            if (betDataHelperListener != null) {
                if (context != null && !context.isFinishing()) {
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (betDataHelperListener != null)
                                betDataHelperListener.onBetDataUpdate(matchedBet, matchedDiffResult, unMatchedBet, unMatchedDiffResult
                                        , fancyMatchedBet, fancyDiffResult);
                            callNext(UPDATE_INTERVAL);
                        }
                    });
                }
            }
        } else {
            if (webRequest.getWebRequestException() != null && webRequest.getWebRequestException() instanceof IOException) {
                callNext(10000);
                return;
            }
            callNext(UPDATE_INTERVAL);
        }
    }


    private void callNext(long time) {
        if (isUpdaterStart) {
            handler.removeCallbacks(runnable);
            handler.postDelayed(runnable, time);
        }
    }

    public void startBetDataUpdater() {
        stopBetDataUpdater(false);
        if (matchDetailModel != null) {
            isUpdaterStart = true;
            mHandlerThread = new HandlerThread("HandlerThread_" + getClass().getSimpleName());
            mHandlerThread.start();
            handler = new Handler(mHandlerThread.getLooper());
            callNext(1);
        }

    }

    public void stopBetDataUpdater(boolean needClean) {
        if (previousRequest != null) {
            previousRequest.cancel();
        }
        if (mHandlerThread != null) {
            mHandlerThread.quit();
        }
        isUpdaterStart = false;
        if (needClean && matchDetailModel != null) {
            matchScoreMap.remove(matchDetailModel.getMatchid());
        }
    }


    public interface BetDataHelperListener {
        void onBetDataUpdate(List<BetDataModel.BetUserData> matchedBet, DiffUtil.DiffResult matchedDiffResult,
                             List<BetDataModel.BetUserData> unMatchedBet, DiffUtil.DiffResult unMatchedDiffResult,
                             List<BetDataModel.BetUserData> fancyMatchedBet, DiffUtil.DiffResult fancyDiffResult);
    }
}
