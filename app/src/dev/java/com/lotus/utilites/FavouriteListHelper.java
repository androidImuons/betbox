package com.lotus.utilites;

import android.os.Handler;
import android.os.HandlerThread;

import androidx.recyclerview.widget.DiffUtil;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lotus.model.MatchDetailModel;
import com.lotus.model.MatchMarketModel;
import com.lotus.model.MatchVolumeModel;
import com.lotus.model.RunnersModel;
import com.lotus.model.SelectionModel;
import com.lotus.model.SelectionNameModel;
import com.lotus.model.SportModel;
import com.lotus.model.WinLossModel;
import com.lotus.model.web_response.FavouriteMatchListResponseModel;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Response;

import static com.lotus.rest.WebRequestConstants.HEADER_KEY_IN_PLAY;
import static com.lotus.utilites.FavouriteMarketHelper.inplayMarket_ids;

public class FavouriteListHelper {

    public static final String TAG = FavouriteListHelper.class.getSimpleName();
    public static final int UPDATE_INTERVAL = 1000;
    private boolean isUpdaterStart = false;
    private MainActivity context;
    private FavouriteMatchListListener favouriteMatchListListener;
    private WebRequestHelper webRequestHelper;
    private WebRequest previousRequest;
    private WebServiceResponseListener webServiceResponseListener = new WebServiceResponseListener() {
        @Override
        public void onWebRequestCall(WebRequest webRequest) {
            webRequest.addHeader(HEADER_KEY_IN_PLAY, inplayMarket_ids);
            context.onWebRequestCall(webRequest);
        }

        @Override
        public void onWebRequestResponse(WebRequest webRequest) {
            handleFavouriteMatchListResponse(webRequest);
        }
    };
    private HandlerThread mHandlerThread = null;
    private Handler handler = null;

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            getFavouriteMatchList();
        }
    };


    private FavouriteListHelper(MainActivity context) {
        this.context = context;
        webRequestHelper = new WebRequestHelper(context);
    }


    public static FavouriteListHelper getNewInstances(MainActivity context) {
        FavouriteListHelper matchMarketHelper = new FavouriteListHelper(context);

        return matchMarketHelper;
    }

    public void setFavouriteListListener(FavouriteMatchListListener favouriteMatchListListener) {
        this.favouriteMatchListListener = favouriteMatchListListener;
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


    private void getFavouriteMatchList() {
        previousRequest = webRequestHelper.getFavouriteMatchList(webServiceResponseListener);
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


    private FavouriteMatchListResponseModel parseResponse(WebRequest webRequest) {
        String response = webRequest.getResponseString();
        if (context.isValidString(response)) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.has("error") && !jsonObject.getBoolean("error")) {

                    Gson gson = new Gson();
                    List<String> match_stack = gson.fromJson(jsonObject.getJSONArray("match_stack").toString(),
                            new TypeToken<List<String>>() {
                            }.getType());
                    List<String> session_stack = gson.fromJson(jsonObject.getJSONArray("session_stack").toString(),
                            new TypeToken<List<String>>() {
                            }.getType());
                    List<String> one_click_stack = gson.fromJson(jsonObject.getJSONArray("match_stack").toString(),
                            new TypeToken<List<String>>() {
                            }.getType());

                    String all_market_ids = jsonObject.getString("market_ids");

                    FavouriteMatchListResponseModel favouriteMatchListResponseModel = new FavouriteMatchListResponseModel();
                    favouriteMatchListResponseModel.setMatch_stack(match_stack);
                    favouriteMatchListResponseModel.setSession_stack(session_stack);
                    favouriteMatchListResponseModel.setOne_click_stack(one_click_stack);
                    favouriteMatchListResponseModel.setMarket_ids(all_market_ids);

                    List<SportModel> sportModels = new ArrayList<>();
                    favouriteMatchListResponseModel.setData(sportModels);

                    JSONArray data = jsonObject.getJSONArray("data");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject sportData = data.getJSONObject(i);
                        JSONArray matchesDataList = sportData.getJSONArray("values");

                        SportModel sportModel = new SportModel();
                        sportModel.setSportId(sportData.getLong("SportId"));
                        sportModel.setSportname(sportData.getString("sportname"));

                        List<MatchDetailModel> matchDetailModels = new ArrayList<>();
                        sportModel.setValues(matchDetailModels);
                        sportModels.add(sportModel);

                        for (int j = 0; j < matchesDataList.length(); j++) {
                            JSONObject matchData = matchesDataList.getJSONObject(j);

                            WinLossModel winLossModel = gson.fromJson(matchData.getJSONObject("win_loss").toString(), WinLossModel.class);

                            List<RunnersModel> marketRunners = gson.fromJson(matchData.getJSONArray("runners").toString(),
                                    new TypeToken<List<RunnersModel>>() {
                                    }.getType());

                            List<MatchMarketModel> markets = new ArrayList<>();
                            MatchMarketModel matchMarketModel = new MatchMarketModel();
                            matchMarketModel.setRunners(marketRunners);
                            matchMarketModel.shiftWinLossValues(winLossModel);
                            matchMarketModel.setMarketid(matchData.getString("marketid"));
                            matchMarketModel.setMarket_name(matchData.optString("market_name"));
                            matchMarketModel.setIs_favourite(matchData.getString("is_favourite"));
                            matchMarketModel.setVisibility(matchData.getInt("visibility"));
                            markets.add(matchMarketModel);


                            List<MatchVolumeModel> volumeLimit = gson.fromJson(matchData.getJSONArray("volumeLimit").toString(),
                                    new TypeToken<List<MatchVolumeModel>>() {
                                    }.getType());
                            List<SelectionModel> selection = gson.fromJson(matchData.getJSONArray("selection").toString(),
                                    new TypeToken<List<SelectionModel>>() {
                                    }.getType());


                            MatchDetailModel matchDetailModel = new MatchDetailModel();
                            matchDetailModel.setSportId(sportModel.getSportId());
                            matchDetailModel.setSportname(sportModel.getSportname());
                            matchDetailModel.setMatchid(matchData.getString("matchid"));
                            matchDetailModel.setMatchName(matchData.getString("matchName"));
                            matchDetailModel.setSeries_name(matchData.getString("series_name"));
                            matchDetailModel.setMatchdate(matchData.getString("matchdate"));
                            matchDetailModel.setSport_image(matchData.getString("sport_image"));
                            matchDetailModel.setSocket_url(matchData.getString("socket_url"));
                            matchDetailModel.setResult(matchData.getString("result"));
                            matchDetailModel.setMarkets(markets);
                            matchDetailModel.setVolumeLimit(volumeLimit);
                            matchDetailModel.setSelection(selection);


                            matchDetailModels.add(matchDetailModel);
                        }


                    }
                    return favouriteMatchListResponseModel;

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private void updatePendingSelectionName(List<MatchDetailModel> newCricketList,
                                            List<MatchDetailModel> newTennisList,
                                            List<MatchDetailModel> newSoccerList) {


        StringBuilder stringBuilder = new StringBuilder();
        if (newCricketList != null) {
            for (MatchDetailModel matchDetailModel : newCricketList) {
                if (matchDetailModel.isValidString(matchDetailModel.getEmptySelectionsIds())) {
                    if (stringBuilder.length() > 0) {
                        stringBuilder.append(",");
                    }
                    stringBuilder.append(matchDetailModel.getEmptySelectionsIds());
                }
            }

        }
        if (newTennisList != null) {
            for (MatchDetailModel matchDetailModel : newTennisList) {
                if (matchDetailModel.isValidString(matchDetailModel.getEmptySelectionsIds())) {
                    if (stringBuilder.length() > 0) {
                        stringBuilder.append(",");
                    }
                    stringBuilder.append(matchDetailModel.getEmptySelectionsIds());
                }
            }

        }
        if (newSoccerList != null) {
            for (MatchDetailModel matchDetailModel : newSoccerList) {
                if (matchDetailModel.isValidString(matchDetailModel.getEmptySelectionsIds())) {
                    if (stringBuilder.length() > 0) {
                        stringBuilder.append(",");
                    }
                    stringBuilder.append(matchDetailModel.getEmptySelectionsIds());
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
                        if (newCricketList != null) {
                            for (MatchDetailModel matchMarketModel : newCricketList) {
                                List<MatchMarketModel> markets = matchMarketModel.getMarkets();
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
                                if (updateDone)
                                    break;
                            }
                        }

                        if (updateDone)
                            continue;


                        if (newTennisList != null) {
                            for (MatchDetailModel matchMarketModel : newTennisList) {
                                List<MatchMarketModel> markets = matchMarketModel.getMarkets();
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
                                if (updateDone)
                                    break;
                            }
                        }

                        if (updateDone)
                            continue;

                        if (newSoccerList != null) {
                            for (MatchDetailModel matchMarketModel : newSoccerList) {
                                List<MatchMarketModel> markets = matchMarketModel.getMarkets();
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
                                if (updateDone)
                                    break;
                            }
                        }

                    }

                }


            }
        }
    }


    private void handleFavouriteMatchListResponse(WebRequest webRequest) {
        final FavouriteMatchListResponseModel favouriteMatchListResponseModel = parseResponse(webRequest);
        if (favouriteMatchListResponseModel != null) {

            if (!context.isFinishing() && context.getFavouriteFragment() != null) {

                List<MatchDetailModel> oldCricketList = context.getFavouriteFragment().adapter_cricket.getList();
                List<MatchDetailModel> oldTennisList = context.getFavouriteFragment().adapter_tennis.getList();
                List<MatchDetailModel> oldSoccerList = context.getFavouriteFragment().adapter_soccer.getList();


                List<MatchDetailModel> newCricketList = null;
                List<MatchDetailModel> newTennisList = null;
                List<MatchDetailModel> newSoccerList = null;

                for (SportModel data : favouriteMatchListResponseModel.getData()) {
                    long sportId = data.getSportId();
                    if (sportId == 4) {
                        newCricketList = data.getValues();
                        if (oldCricketList != null) {
                            for (MatchDetailModel matchDetailModel : newCricketList) {
                                for (MatchDetailModel detailModel : oldCricketList) {
                                    if (detailModel.getUniqueIdForFav().equals(matchDetailModel.getUniqueIdForFav())) {
                                        matchDetailModel.getMarkets().get(0).setMatchDisable(detailModel.getMarkets().get(0).isMatchDisable());
                                        matchDetailModel.getMarkets().get(0).setStatus(detailModel.getMarkets().get(0).getStatus());
                                        matchDetailModel.getMarkets().get(0).setInPlay(detailModel.getMarkets().get(0).isInPlay());
                                        matchDetailModel.getMarkets().get(0).updateRunnerBackLay(detailModel.getMarkets().get(0).getRunners());
                                        matchDetailModel.getMarkets().get(0).updateRunnerName(detailModel.getMarkets().get(0).getRunners());

                                        matchDetailModel.setInPlay(detailModel.isInPlay());
                                        break;
                                    }
                                }
                            }
                        }

                    } else if (sportId == 2) {
                        newTennisList = data.getValues();
                        if (oldTennisList != null) {
                            for (MatchDetailModel matchDetailModel : newTennisList) {
                                for (MatchDetailModel detailModel : oldTennisList) {
                                    if (detailModel.getUniqueIdForFav().equals(matchDetailModel.getUniqueIdForFav())) {
                                        matchDetailModel.getMarkets().get(0).setMatchDisable(detailModel.getMarkets().get(0).isMatchDisable());
                                        matchDetailModel.getMarkets().get(0).setStatus(detailModel.getMarkets().get(0).getStatus());
                                        matchDetailModel.getMarkets().get(0).setInPlay(detailModel.getMarkets().get(0).isInPlay());
                                        matchDetailModel.getMarkets().get(0).updateRunnerBackLay(detailModel.getMarkets().get(0).getRunners());
                                        matchDetailModel.getMarkets().get(0).updateRunnerName(detailModel.getMarkets().get(0).getRunners());

                                        matchDetailModel.setInPlay(detailModel.isInPlay());
                                        break;
                                    }
                                }
                            }
                        }
                    } else if (sportId == 1) {
                        newSoccerList = data.getValues();
                        if (oldSoccerList != null) {
                            for (MatchDetailModel matchDetailModel : newSoccerList) {
                                for (MatchDetailModel detailModel : oldSoccerList) {
                                    if (detailModel.getUniqueIdForFav().equals(matchDetailModel.getUniqueIdForFav())) {
                                        matchDetailModel.getMarkets().get(0).setMatchDisable(detailModel.getMarkets().get(0).isMatchDisable());
                                        matchDetailModel.getMarkets().get(0).setStatus(detailModel.getMarkets().get(0).getStatus());
                                        matchDetailModel.getMarkets().get(0).setInPlay(detailModel.getMarkets().get(0).isInPlay());
                                        matchDetailModel.getMarkets().get(0).updateRunnerBackLay(detailModel.getMarkets().get(0).getRunners());
                                        matchDetailModel.getMarkets().get(0).updateRunnerName(detailModel.getMarkets().get(0).getRunners());

                                        matchDetailModel.setInPlay(detailModel.isInPlay());
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }

                updatePendingSelectionName(newCricketList, newTennisList, newSoccerList);

                if (newCricketList != null) {
                    Collections.sort(newCricketList, new Comparator<MatchDetailModel>() {
                        @Override
                        public int compare(MatchDetailModel o1, MatchDetailModel o2) {
                            return Boolean.compare(o2.isInPlay(), o1.isInPlay());
                        }
                    });
                }

                if (newTennisList != null) {
                    Collections.sort(newTennisList, new Comparator<MatchDetailModel>() {
                        @Override
                        public int compare(MatchDetailModel o1, MatchDetailModel o2) {
                            return Boolean.compare(o2.isInPlay(), o1.isInPlay());
                        }
                    });
                }
                if (newSoccerList != null) {
                    Collections.sort(newSoccerList, new Comparator<MatchDetailModel>() {
                        @Override
                        public int compare(MatchDetailModel o1, MatchDetailModel o2) {
                            return Boolean.compare(o2.isInPlay(), o1.isInPlay());
                        }
                    });
                }

                if (newCricketList != null) {
                    for (MatchDetailModel matchDetailModel : newCricketList) {
                        for (MatchDetailModel detailModel : oldCricketList) {
                            if (detailModel.getUniqueIdForFav().equals(matchDetailModel.getUniqueIdForFav())) {
                                if (matchDetailModel.getMarkets() != null && matchDetailModel.getMarkets().size() > 0) {
                                    DiffCallBackRunnerModel2 diffCallback = new DiffCallBackRunnerModel2(detailModel
                                            .getMarkets().get(0).getRunners(),
                                            matchDetailModel.getMarkets().get(0).getRunners());
                                    DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);
                                    matchDetailModel.getMarkets().get(0).setDiffResultRunners(diffResult);
                                }
                                break;
                            }
                        }
                    }
                }

                if (newTennisList != null) {
                    for (MatchDetailModel matchMarketModel : newTennisList) {
                        int i = oldTennisList.indexOf(matchMarketModel);
                        if (i >= 0 && matchMarketModel.getMarkets() != null && matchMarketModel.getMarkets().size() > 0) {
                            DiffCallBackRunnerModel2 diffCallback = new DiffCallBackRunnerModel2(oldTennisList.get(i)
                                    .getMarkets().get(0).getRunners(),
                                    matchMarketModel.getMarkets().get(0).getRunners());
                            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);
                            matchMarketModel.getMarkets().get(0).setDiffResultRunners(diffResult);
                        }
                    }
                }

                if (newSoccerList != null) {
                    for (MatchDetailModel matchMarketModel : newSoccerList) {
                        int i = oldSoccerList.indexOf(matchMarketModel);
                        if (i >= 0 && matchMarketModel.getMarkets() != null && matchMarketModel.getMarkets().size() > 0) {
                            DiffCallBackRunnerModel2 diffCallback = new DiffCallBackRunnerModel2(oldSoccerList.get(i)
                                    .getMarkets().get(0).getRunners(),
                                    matchMarketModel.getMarkets().get(0).getRunners());
                            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);
                            matchMarketModel.getMarkets().get(0).setDiffResultRunners(diffResult);
                        }
                    }
                }


                /**
                 * cricket diff callback
                 */
                DiffCallBackDashboardMatchDetailModel diffCallBackDashboardMatchDetailModel = new DiffCallBackDashboardMatchDetailModel(1, oldCricketList,
                        newCricketList);
                final DiffUtil.DiffResult diffResultCricket = DiffUtil.calculateDiff(diffCallBackDashboardMatchDetailModel);

                /**
                 * Tennis diff callback
                 */
                diffCallBackDashboardMatchDetailModel = new DiffCallBackDashboardMatchDetailModel(1, oldTennisList,
                        newTennisList);
                final DiffUtil.DiffResult diffResultTennis = DiffUtil.calculateDiff(diffCallBackDashboardMatchDetailModel);

                /**
                 * Soccer diff callback
                 */
                diffCallBackDashboardMatchDetailModel = new DiffCallBackDashboardMatchDetailModel(1, oldSoccerList,
                        newSoccerList);
                final DiffUtil.DiffResult diffResultSoccer = DiffUtil.calculateDiff(diffCallBackDashboardMatchDetailModel);

                if (favouriteMatchListListener != null) {
                    if (context != null && !context.isFinishing()) {
                        final List<MatchDetailModel> finalNewCricketList = newCricketList;
                        final List<MatchDetailModel> finalNewTennisList = newTennisList;
                        final List<MatchDetailModel> finalNewSoccerList = newSoccerList;
                        context.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (favouriteMatchListListener != null) {
                                    favouriteMatchListListener.onMatchListUpdate(favouriteMatchListResponseModel.getMarket_ids(),
                                            finalNewCricketList, diffResultCricket,
                                            finalNewTennisList, diffResultTennis,
                                            finalNewSoccerList, diffResultSoccer);

                                    favouriteMatchListListener.onMarketUpdateFromMatchList(finalNewCricketList,
                                            finalNewTennisList,
                                            finalNewSoccerList);
                                }

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


    public void startFavouriteMatchListHelper() {
        stopFavouriteMatchListHelper(false);
        isUpdaterStart = true;
        mHandlerThread = new HandlerThread("HandlerThread_" + getClass().getSimpleName());
        mHandlerThread.start();
        handler = new Handler(mHandlerThread.getLooper());
        callNext(1);
    }

    public void stopFavouriteMatchListHelper(boolean needClean) {
        if (previousRequest != null) {
            previousRequest.cancel();
        }
        if (mHandlerThread != null) {
            mHandlerThread.quit();
        }
        isUpdaterStart = false;

    }

    public interface FavouriteMatchListListener {

        void onMarketUpdateFromMatchList(List<MatchDetailModel> newCricketList,
                                         List<MatchDetailModel> newTennisList,
                                         List<MatchDetailModel> newSoccerList);

        void onMatchListUpdate(String allMarketIds,
                               List<MatchDetailModel> newCricketList, DiffUtil.DiffResult diffResultCricket,
                               List<MatchDetailModel> newTennisList, DiffUtil.DiffResult diffResultTennis,
                               List<MatchDetailModel> newSoccerList, DiffUtil.DiffResult diffResultSoccer);
    }

}
