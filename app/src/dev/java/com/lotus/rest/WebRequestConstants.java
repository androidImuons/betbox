package com.lotus.rest;


import com.rest.ServerConstant;

/**
 * Created by ubuntu on 27/3/18.
 */

public interface WebRequestConstants extends ServerConstant {

    String CONSTANT_APP_UPDATE = "APP_UPDATE";

    String HEADER_KEY_DEVICE_TYPE = "devicetype";
    String HEADER_KEY_DEVICE_INFO = "deviceinfo";
    String HEADER_KEY_APP_INFO = "appinfo";
    String HEADER_KEY_IN_PLAY = "inplay";
    String DEVICE_TYPE_ANDROID = "A";

    String Loginauthcontroller = BASE + "Loginauthcontroller/";
    String Apicontroller = BASE + "Apicontroller/";
    String Apiusercontroller = BASE + "Apiusercontroller/";
    String Apiadmincontroller = BASE + "Apiadmincontroller/";
    String Apimobilecontroller = BASE + "Apimobilecontroller/";
    String Betentrycntr = BASE + "Betentrycntr/";
    String Geteventcntr = BASE + "Geteventcntr/";
    String Createdealercontroller = BASE + "Createdealercontroller/";
    String Chipscntrl = BASE + "Chipscntrl/";


    String URL_CHECK_APP_VERSION = Apimobilecontroller + "chkAppVersion";
    String URL_APK_DOWNLOAD = BASE + "uploads/apk/%s";

    String URL_LOAD_CAPTCHA = Loginauthcontroller + "loadCaptcha";
    int ID_LOAD_CAPTCHA = 1;

    String URL_LOGIN = Loginauthcontroller + "chkLoginMobileUser";
    int ID_LOGIN = 2;

    String URL_LOGIN_CHECK = Loginauthcontroller + "is_logged_in_check";
    int ID_LOGIN_CHECK = 3;

    String URL_USER_MATCH_LIST = Apiusercontroller + "getUserFavouriteMatchLst/%s";
    int ID_USER_MATCH_LIST = 4;

    String URL_ODDS_BY_MARKET_IDS = "http://demo.com/get_odds_by_market_ids.php?market_id=%s"; // change here http://demo.com/ in your url
    int ID_ODDS_BY_MARKET_IDS = 5;


    String URL_MARKET_LISTING = Apicontroller + "getMarketListing/%s";//matchid
    int ID_MARKET_LISTING = 6;

    String URL_CHANGE_PASSWORD = Createdealercontroller + "changePassword";
    int ID_CHANGE_PASSWORD = 7;

    String URL_MARKET_WIN_LOSS = Apicontroller + "market_win_loss";
    int ID_MARKET_WIN_LOSS = 8;

    String URL_GET_MATCH_BETFAIR_SESSION = "http://demo.com/get_match_betfair_session.php?market_id=%s"; // change here http://demo.com/ in your url
    int ID_MATCH_BETFAIR_SESSION = 9;

    String URL_GET_MATCH_INDIAN_SESSION = Apicontroller + "matchLstIndianSession/%s/%s"; //matchId marketId
    int ID_MATCH_INDIAN_SESSION = 10;

    String URL_GET_MATCH_ADMIN_SESSION = Apicontroller + "matchLstAdminSession/%s/%s"; //matchId marketId
    int ID_MATCH_ADMIN_SESSION = 11;

    String URL_DISPLAY_MSG_HEADER = Betentrycntr + "DisplayMsgOnHeader";
    int ID_DISPLAY_MSG_HEADER = 12;

    String URL_BALANCE = Chipscntrl + "getChipDataById/%s";// userId
    int ID_BALANCE = 13;

    String URL_GET_MATCH_SCORE = "http://demo.com/api/match-center/stats/%s/%s";// sport_id, match_id   // change here http://demo.com/ in your url
    String URL_GET_MATCH_SCORE2 = Geteventcntr + "GetScoreApi/%s";// match_id
    int ID_GET_MATCH_SCORE = 14;

    String URL_GET_BET_DATA = Betentrycntr + "GatBetData/%s/%s/%s/%s";// :match_id
    int ID_GET_BET_DATA = 15;

    String URL_PLAY_STORE_APP_VERSION = BASE + "get_playstore_app_version/%s";

    String URL_ONE_PAGE_REPORT = Apiadmincontroller + "one_page_report";
    int ID_ONE_PAGE_REPORT = 16;

    String URL_CHIP_LEGER = Betentrycntr + "Chip_leger/%s/%s/%s/%s/%s";//user_id, user_type, type, from_date, to_date
    int ID_CHIP_LEGER = 17;

    String URL_PROFIT_LOSS_BY_MATCH = Betentrycntr + "profitLossByMatchId";
    String KEY_PROFIT_LOSS_BY_MATCH = "profitLossByMatchId";
    int ID_PROFIT_LOSS_BY_MATCH = 18;

    String URL_BET_HISTORY_PL = Betentrycntr + "BetHistoryPL/%s/%s/%s/%s";//user_id/matchId/page_no/fancyId
    int ID_URL_BET_HISTORY_PL = 19;

    String URL_GET_SERIES_LIST = Geteventcntr + "getSeriesLst/%s";// match type
    int ID_GET_SERIES_LIST = 20;

    String URL_MATCH_BY_SPORT_ID = Geteventcntr + "getInPlayMatchBySportId/%s/%s";// sport_id/user_id
    int ID_MATCH_BY_SPORT_ID = 21;

    String URL_GET_STAKE_SETTING = Apiusercontroller + "get_stake_setting";
    int ID_GET_STAKE_SETTING = 22;

    String URL_ONE_CLICK_STAKE_SETTING = Apiusercontroller + "one_click_stake_setting";
    String KEY_ONE_CLICK_STAKE_SETTING = "one_click_stake_setting";
    int ID_ONE_CLICK_STAKE_SETTING = 23;

    String URL_STAKE_SETTING = Apiusercontroller + "stake_setting";
    String KEY_STAKE_SETTING = "stake_setting";
    int ID_STAKE_SETTING = 24;

    String URL_SERIES_WITH_MATCH_DATA = Geteventcntr + "getSeriesWithMatchData/%s/%s/%s";// sport_id/seriesId/user_id
    int ID_SERIES_WITH_MATCH_DATA = 25;
    String KEY_SERIES_WITH_MATCH_DATA = "getSeriesWithMatchData";

    String URL_SAVE_MULTIPLE_BETS = Apiusercontroller + "save_multiple_bets";
    int ID_SAVE_MULTIPLE_BETS = 26;

    String URL_MATCH_AUTOCOMPLETE = Apiusercontroller + "match_autocomplete";
    int ID_MATCH_AUTOCOMPLETE = 27;

    String URL_FAVOURITE_MATCH = Apiusercontroller + "getFavouriteMatchLst/%s";
    int ID_FAVOURITE_MATCH = 28;

    String URL_FAVOURITE = Apiusercontroller + "favourite";
    String KEY_MATCHDETAIL = "match_detail";
    String KEY_FAVOURITE = "favourite";
    int ID_FAVOURITE = 29;

    String URL_UNFAVOURITE = Apiusercontroller + "unfavourite";
    String KEY_UNFAVOURITE = "unfavourite";
    int ID_UNFAVOURITE = 30;

    String URL_CONFIRM_BET = Apiusercontroller + "confirm_bet";
    int ID_CONFIRM_BET = 31;

    String URL_DELETE_BETTING = Betentrycntr + "deleteGetbetting/%s/%s";//MstCode, UserId
    String KEY_DELETE = "delete";
    int ID_DELETE_BETTING = 32;

    String URL_GET_SELECTION_NAME = "http://demo.com/get_selectionname_by_market_ids.php?market_sel_id=%s";//marketid-selectionid,marketid-selectionid   // change here http://demo.com/ in your url
    int ID_GET_SELECTION_NAME = 33;


    String TV1 = BASE+"tvnew/tv1.html";;
    String TV2 = BASE+"tvnew/tv2.html";

}
