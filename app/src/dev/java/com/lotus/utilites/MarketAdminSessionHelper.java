package com.lotus.utilites;

import android.os.Handler;
import android.os.Message;

import com.lotus.model.IndianSessionModel;
import com.lotus.model.MatchDetailModel;
import com.lotus.model.web_response.IndianSessionResponseModel;
import com.lotus.rest.WebRequestHelper;
import com.lotus.ui.main.MainActivity;
import com.medy.retrofitwrapper.WebRequest;
import com.medy.retrofitwrapper.WebServiceResponseListener;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MarketAdminSessionHelper {

    public static final String TAG = MarketAdminSessionHelper.class.getSimpleName();
    public static final int UPDATE_INTERVAL = 2000;
    static final Map<String, MarketAdminSessionHelper> matchMarketMap = new ConcurrentHashMap<>();
    private boolean isUpdaterStart = false;
    private MainActivity context;
    private MatchDetailModel matchDetailModel;
    private MarketAdminSessionListener marketAdminSessionListener;
    private WebRequestHelper webRequestHelper;
    private WebRequest previousRequest;
    private WebServiceResponseListener webServiceResponseListener = new WebServiceResponseListener() {
        @Override
        public void onWebRequestCall(WebRequest webRequest) {
            context.onWebRequestCall(webRequest);
        }

        @Override
        public void onWebRequestResponse(WebRequest webRequest) {
            handleMarketAdminSessionResponse(webRequest);
        }
    };
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            getMarketIndianSession();
        }
    };

    public void setMarketAdminSessionListener(MarketAdminSessionListener marketAdminSessionListener) {
        this.marketAdminSessionListener = marketAdminSessionListener;
    }

    private void callNext() {
        if (isUpdaterStart)
            handler.sendEmptyMessageDelayed(1, UPDATE_INTERVAL);
    }

    private MarketAdminSessionHelper(MainActivity context) {
        this.context = context;
        webRequestHelper = new WebRequestHelper(context);
    }

    public static MarketAdminSessionHelper getNewInstances(MainActivity context, MatchDetailModel
            matchDetailModel) {
        if (matchDetailModel == null) return null;
        MarketAdminSessionHelper matchMarketHelper = new MarketAdminSessionHelper(context);
        matchMarketHelper.matchDetailModel = matchDetailModel;
        matchMarketMap.put(matchDetailModel.getMatchid(), matchMarketHelper);

        return matchMarketHelper;
    }


    private void getMarketIndianSession() {
        if (matchDetailModel != null) {
            previousRequest = webRequestHelper.
                    getMatchAdminSession(matchDetailModel, webServiceResponseListener);
        }

    }

    private void handleMarketAdminSessionResponse(WebRequest webRequest) {
        IndianSessionResponseModel responseModel = webRequest.getResponsePojo(IndianSessionResponseModel.class);
        if (responseModel != null) {

            List<IndianSessionModel> data = responseModel.getData();
            if (marketAdminSessionListener != null)
                marketAdminSessionListener.onMarketAdminSessionUpdate(data);
        }

        callNext();
    }

    public void startMarketAdminSessionHelper() {
        stopMarketAdminSessionHelper(false);
        if (matchDetailModel != null) {
            isUpdaterStart = true;
            getMarketIndianSession();
        }

    }

    public void stopMarketAdminSessionHelper(boolean needClean) {
        handler.removeMessages(1);
        isUpdaterStart = false;
        if (previousRequest != null) {
            previousRequest.cancel();
        }
        if (needClean && matchDetailModel != null) {
            matchMarketMap.remove(matchDetailModel.getMatchid());
        }
    }

    public interface MarketAdminSessionListener {
        void onMarketAdminSessionUpdate(List<IndianSessionModel> data);
    }

}
