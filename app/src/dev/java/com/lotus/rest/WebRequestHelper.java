package com.lotus.rest;

import android.content.Context;

import com.lotus.model.BetDataModel;
import com.lotus.model.MatchDetailModel;
import com.lotus.model.ProfitLossModel;
import com.lotus.model.SeriesListModel;
import com.lotus.model.UserModel;
import com.lotus.model.request_model.BetHistoryPLRequestModel;
import com.lotus.model.request_model.BetPlaceRequestModel;
import com.lotus.model.request_model.ChangePasswordRequestModel;
import com.lotus.model.request_model.ConfirmBetRequestModel;
import com.lotus.model.request_model.FavouriteRequestModel;
import com.lotus.model.request_model.HistoryRequestModel;
import com.lotus.model.request_model.LoginRequestModel;
import com.lotus.model.request_model.MarketWinLossRequestModel;
import com.lotus.model.request_model.MatchStackRequestModel;
import com.lotus.model.request_model.P_LByMatchIdRequestModel;
import com.lotus.model.request_model.StackRequestModel;
import com.lotus.model.request_model.TransferRequestModel;
import com.medy.retrofitwrapper.WebRequest;
import com.medy.retrofitwrapper.WebServiceResponseListener;

/**
 * Created by ubuntu on 27/3/18.
 */

public class WebRequestHelper implements WebRequestConstants {

    private final long API_TIME_OUT = 1 * 60 * 1000;

    private static final String TAG = WebRequestHelper.class.getSimpleName();
    private Context context;

    public WebRequestHelper(Context context) {
        this.context = context;
    }

    public boolean isValidString(String data) {
        return data != null && !data.trim().isEmpty();
    }


    public void loadCaptcha(WebServiceResponseListener webServiceResponseListener) {
//        String URL = String.format(URL_LOAD_CAPTCHA, 0);
//        WebRequest webRequest = new WebRequest(ID_LOAD_CAPTCHA, URL, WebRequest.GET_REQ, API_TIME_OUT);
//        webRequest.send(context, webServiceResponseListener);
    }

    public void login(LoginRequestModel requestModel, WebServiceResponseListener webServiceResponseListener) {
        WebRequest webRequest = new WebRequest(ID_LOGIN, URL_LOGIN, WebRequest.POST_REQ, API_TIME_OUT);
        webRequest.setRequestModel(requestModel);
        webRequest.send(context, webServiceResponseListener);
    }


    public void changePassword(ChangePasswordRequestModel requestModel, WebServiceResponseListener webServiceResponseListener) {
        WebRequest webRequest = new WebRequest(ID_CHANGE_PASSWORD, URL_CHANGE_PASSWORD, WebRequest.POST_REQ, API_TIME_OUT);
        webRequest.setRequestModel(requestModel);
        webRequest.send(context, webServiceResponseListener);
    }


    public WebRequest getMatchAdminSession(MatchDetailModel matchDetailModel, WebServiceResponseListener webServiceResponseListener) {
        String url = String.format(URL_GET_MATCH_ADMIN_SESSION,
                matchDetailModel.getMatchid(), matchDetailModel.getAllMarketIds());
        WebRequest webRequest = new WebRequest(ID_MATCH_ADMIN_SESSION, url, WebRequest.GET_REQ, API_TIME_OUT);
        webRequest.send(context, webServiceResponseListener);
        return webRequest;
    }


    public void one_page_report(HistoryRequestModel requestModel, WebServiceResponseListener webServiceResponseListener) {
        WebRequest webRequest = new WebRequest(ID_ONE_PAGE_REPORT, URL_ONE_PAGE_REPORT, WebRequest.POST_REQ, API_TIME_OUT);
        webRequest.setRequestModel(requestModel);
        webRequest.send(context, webServiceResponseListener);
    }

    public void chip_leger(TransferRequestModel requestModel, WebServiceResponseListener webServiceResponseListener) {
        String url = String.format(URL_CHIP_LEGER, requestModel.user_id, requestModel.user_type,
                requestModel.type, requestModel.from_date, requestModel.to_date);
        WebRequest webRequest = new WebRequest(ID_CHIP_LEGER, url, WebRequest.GET_REQ, API_TIME_OUT);
        webRequest.send(context, webServiceResponseListener);
    }


    public void getInPlayMatchBySportId(int seriesType, UserModel userModel, WebServiceResponseListener webServiceResponseListener) {
        String url = String.format(URL_MATCH_BY_SPORT_ID, seriesType, userModel.getUser_id());
        WebRequest webRequest = new WebRequest(ID_MATCH_BY_SPORT_ID, url, WebRequest.GET_REQ, API_TIME_OUT);
        webRequest.send(context, webServiceResponseListener);
    }

    public void profitLossByMatchId(ProfitLossModel dataModel, P_LByMatchIdRequestModel requestModel, WebServiceResponseListener webServiceResponseListener) {
        WebRequest webRequest = new WebRequest(ID_PROFIT_LOSS_BY_MATCH, URL_PROFIT_LOSS_BY_MATCH, WebRequest.POST_REQ, API_TIME_OUT);
        webRequest.setRequestModel(requestModel);
        webRequest.addExtra(KEY_PROFIT_LOSS_BY_MATCH, dataModel);
        webRequest.send(context, webServiceResponseListener);
    }

    public void betHistoryPL(BetHistoryPLRequestModel requestModel, WebServiceResponseListener webServiceResponseListener) {
        String url = String.format(URL_BET_HISTORY_PL, requestModel.user_id, requestModel.matchId,
                requestModel.page_no, requestModel.fancyId);
        WebRequest webRequest = new WebRequest(ID_URL_BET_HISTORY_PL, url, WebRequest.GET_REQ, API_TIME_OUT);
        webRequest.send(context, webServiceResponseListener);
    }


    public WebRequest one_click_stake_setting(StackRequestModel requestModel, WebServiceResponseListener webServiceResponseListener) {
        WebRequest webRequest = new WebRequest(ID_ONE_CLICK_STAKE_SETTING, URL_ONE_CLICK_STAKE_SETTING, WebRequest.POST_REQ, API_TIME_OUT);
        webRequest.setRequestModel(requestModel);
        webRequest.addExtra(KEY_ONE_CLICK_STAKE_SETTING, requestModel);
        webRequest.send(context, webServiceResponseListener);
        return webRequest;
    }

    public WebRequest stake_setting(MatchStackRequestModel requestModel, WebServiceResponseListener webServiceResponseListener) {
        WebRequest webRequest = new WebRequest(ID_STAKE_SETTING, URL_STAKE_SETTING, WebRequest.POST_REQ, API_TIME_OUT);
        webRequest.setRequestModel(requestModel);
        webRequest.addExtra(KEY_STAKE_SETTING, requestModel);
        webRequest.send(context, webServiceResponseListener);
        return webRequest;
    }

    public void getSeriesWithMatchData(SeriesListModel seriesListModel, UserModel userModel, WebServiceResponseListener webServiceResponseListener) {
        String url = String.format(URL_SERIES_WITH_MATCH_DATA,
                seriesListModel.getSportID(),
                seriesListModel.getSeriesId(), userModel.getUser_id());
        WebRequest webRequest = new WebRequest(ID_SERIES_WITH_MATCH_DATA, url, WebRequest.GET_REQ, API_TIME_OUT);
        webRequest.addExtra(KEY_SERIES_WITH_MATCH_DATA, seriesListModel);
        webRequest.send(context, webServiceResponseListener);
    }

    public void save_multiple_bets(BetPlaceRequestModel requestModel, WebServiceResponseListener webServiceResponseListener) {
        WebRequest webRequest = new WebRequest(ID_SAVE_MULTIPLE_BETS, URL_SAVE_MULTIPLE_BETS, WebRequest.POST_REQ, API_TIME_OUT);
        webRequest.setRequestModel(requestModel);
        webRequest.send(context, webServiceResponseListener);
    }

    public void favourite(MatchDetailModel matchDetailModel, FavouriteRequestModel requestModel, WebServiceResponseListener webServiceResponseListener) {
        WebRequest webRequest = new WebRequest(ID_FAVOURITE, URL_FAVOURITE, WebRequest.POST_REQ, API_TIME_OUT);
        webRequest.setRequestModel(requestModel);
        webRequest.addExtra(KEY_MATCHDETAIL, matchDetailModel);
        webRequest.addExtra(KEY_FAVOURITE, requestModel);
        webRequest.send(context, webServiceResponseListener);
    }

    public void unfavourite(MatchDetailModel matchDetailModel, FavouriteRequestModel requestModel, WebServiceResponseListener webServiceResponseListener) {
        WebRequest webRequest = new WebRequest(ID_UNFAVOURITE, URL_UNFAVOURITE, WebRequest.POST_REQ, API_TIME_OUT);
        webRequest.setRequestModel(requestModel);
        webRequest.addExtra(KEY_MATCHDETAIL, matchDetailModel);
        webRequest.addExtra(KEY_UNFAVOURITE, requestModel);
        webRequest.send(context, webServiceResponseListener);
    }

    public void confirmBet(ConfirmBetRequestModel requestModel, WebServiceResponseListener webServiceResponseListener) {
        WebRequest webRequest = new WebRequest(ID_CONFIRM_BET, URL_CONFIRM_BET, WebRequest.POST_REQ, API_TIME_OUT);
        webRequest.setRequestModel(requestModel);
        webRequest.send(context, webServiceResponseListener);
    }

    public void deleteUnMatchedBet(BetDataModel.BetUserData betUserData, WebServiceResponseListener webServiceResponseListener) {
        String URL = String.format(URL_DELETE_BETTING,
                betUserData.getMstCode(), betUserData.getUserId());
        WebRequest webRequest = new WebRequest(ID_DELETE_BETTING, URL, WebRequest.GET_REQ, API_TIME_OUT);
        webRequest.addExtra(KEY_DELETE, betUserData);
        webRequest.send(context, webServiceResponseListener);
    }


//    ______________________________________________________

    public WebRequest getFavouriteMatchList(WebServiceResponseListener webServiceResponseListener) {
        String URL = String.format(URL_FAVOURITE_MATCH, 0);
        WebRequest webRequest = new WebRequest(ID_FAVOURITE_MATCH, URL, WebRequest.GET_REQ, API_TIME_OUT);
        return webRequest;
    }

    public WebRequest getUserMatchList(WebServiceResponseListener webServiceResponseListener) {
        String URL = String.format(URL_USER_MATCH_LIST, 0);
        WebRequest webRequest = new WebRequest(ID_USER_MATCH_LIST, URL, WebRequest.GET_REQ, API_TIME_OUT);
        return webRequest;
    }

    public WebRequest getOddsByMarketIds(String market_ids, WebServiceResponseListener webServiceResponseListener) {
        String url = String.format(URL_ODDS_BY_MARKET_IDS, market_ids);
        WebRequest webRequest = new WebRequest(ID_ODDS_BY_MARKET_IDS, url, WebRequest.GET_REQ, API_TIME_OUT);
        return webRequest;
    }

    public WebRequest checkUserLoggedIn(WebServiceResponseListener webServiceResponseListener) {
        WebRequest webRequest = new WebRequest(ID_LOGIN_CHECK, URL_LOGIN_CHECK, WebRequest.GET_REQ, API_TIME_OUT);
        return webRequest;
    }

    public WebRequest getBetData(UserModel userModel, MatchDetailModel matchDetailModel, WebServiceResponseListener webServiceResponseListener) {
        String url = String.format(URL_GET_BET_DATA, 0, userModel.getType(), userModel.getUser_id(),
                matchDetailModel.getMatchid());
        WebRequest webRequest = new WebRequest(ID_GET_BET_DATA, url, WebRequest.GET_REQ, API_TIME_OUT);
        return webRequest;
    }

    public WebRequest getMsgOnHeader(WebServiceResponseListener webServiceResponseListener) {
        WebRequest webRequest = new WebRequest(ID_DISPLAY_MSG_HEADER, URL_DISPLAY_MSG_HEADER, WebRequest.GET_REQ, API_TIME_OUT);
        return webRequest;
    }

    public WebRequest getChipDataById(UserModel userModel, WebServiceResponseListener webServiceResponseListener) {
        String url = String.format(URL_BALANCE, userModel.getUser_id());
        WebRequest webRequest = new WebRequest(ID_BALANCE, url, WebRequest.GET_REQ, API_TIME_OUT);
        return webRequest;
    }

    public WebRequest get_stake_setting(WebServiceResponseListener webServiceResponseListener) {
        WebRequest webRequest = new WebRequest(ID_GET_STAKE_SETTING, URL_GET_STAKE_SETTING, WebRequest.GET_REQ, API_TIME_OUT);
        return webRequest;
    }

    public WebRequest getMatchBetFairSession(String marketIds, WebServiceResponseListener webServiceResponseListener) {
        String url = String.format(URL_GET_MATCH_BETFAIR_SESSION, marketIds);
        WebRequest webRequest = new WebRequest(ID_MATCH_BETFAIR_SESSION, url, WebRequest.GET_REQ, API_TIME_OUT);
        return webRequest;
    }


    public WebRequest getMatchIndianSession(String matchId, String marketId, WebServiceResponseListener webServiceResponseListener) {
        String url = String.format(URL_GET_MATCH_INDIAN_SESSION, matchId, marketId);
        WebRequest webRequest = new WebRequest(ID_MATCH_INDIAN_SESSION, url, WebRequest.GET_REQ, API_TIME_OUT);
        return webRequest;
    }

    public WebRequest getMarketWinnLoss(MarketWinLossRequestModel requestModel, WebServiceResponseListener webServiceResponseListener) {
        WebRequest webRequest = new WebRequest(ID_MARKET_WIN_LOSS, URL_MARKET_WIN_LOSS, WebRequest.POST_REQ, API_TIME_OUT);
        webRequest.setRequestModel(requestModel);
        return webRequest;
    }

    public WebRequest getMatchScore(MatchDetailModel matchDetailModel, WebServiceResponseListener webServiceResponseListener) {
        String url = String.format(URL_GET_MATCH_SCORE, matchDetailModel.getSportId(), matchDetailModel.getMatchid());
        WebRequest webRequest = new WebRequest(ID_GET_MATCH_SCORE, url, WebRequest.GET_REQ, API_TIME_OUT);
        return webRequest;
    }

    public WebRequest getMatchScore2(MatchDetailModel matchDetailModel, WebServiceResponseListener webServiceResponseListener) {
        String url = String.format(URL_GET_MATCH_SCORE2, matchDetailModel.getMatchid());
        WebRequest webRequest = new WebRequest(ID_GET_MATCH_SCORE, url, WebRequest.GET_REQ, API_TIME_OUT);
        return webRequest;
    }

    public WebRequest getMarketListing(MatchDetailModel matchDetailModel, WebServiceResponseListener webServiceResponseListener) {
        String URL = String.format(URL_MARKET_LISTING, matchDetailModel.getMatchid());
        WebRequest webRequest = new WebRequest(ID_MARKET_LISTING, URL, WebRequest.GET_REQ, API_TIME_OUT);
        return webRequest;
    }

    public WebRequest getSelectionName(String marketIds_selectionids, WebServiceResponseListener webServiceResponseListener) {
        String URL = String.format(URL_GET_SELECTION_NAME, marketIds_selectionids);
        WebRequest webRequest = new WebRequest(ID_GET_SELECTION_NAME, URL, WebRequest.GET_REQ, API_TIME_OUT);
        return webRequest;
    }
}
