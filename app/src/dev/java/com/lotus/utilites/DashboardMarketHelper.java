package com.lotus.utilites;

import android.os.Handler;
import android.os.HandlerThread;

import androidx.recyclerview.widget.DiffUtil;

import com.google.gson.reflect.TypeToken;
import com.lotus.model.MatchDetailModel;
import com.lotus.model.MatchMarketModel;
import com.lotus.model.OddsByMarketIdsModel;
import com.lotus.model.RunnersModel;
import com.lotus.rest.WebRequestHelper;
import com.lotus.ui.main.MainActivity;
import com.medy.retrofitwrapper.WebRequest;
import com.medy.retrofitwrapper.WebServiceException;
import com.medy.retrofitwrapper.WebServiceResponseListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Response;

/**
 * @author Sunil kumar Yadav
 * @Since 09/10/18
 */
public class DashboardMarketHelper {
    public static final String TAG = DashboardMarketHelper.class.getSimpleName();
    public static final int UPDATE_INTERVAL = 1000;
    public boolean isUpdaterStart = false;
    static DashboardMarketHelper matchMarketHelper = null;
    private MainActivity context;
    private String market_ids;
    public static String inplayMarket_ids = "";
    private DashboardMarketUpdaterListener dashboardMarketUpdaterListener;
    private WebRequestHelper webRequestHelper;
    private WebRequest previousRequest;
    private WebServiceResponseListener webServiceResponseListener = new WebServiceResponseListener() {
        @Override
        public void onWebRequestCall(WebRequest webRequest) {
            context.onWebRequestCall(webRequest);
        }

        @Override
        public void onWebRequestResponse(WebRequest webRequest) {
            handleMarketIdsResponse(webRequest);
        }
    };
    private HandlerThread mHandlerThread = null;
    private Handler handler = null;

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            getOddsByMarketIds();
        }
    };

    private DashboardMarketHelper(MainActivity context) {
        this.context = context;
        webRequestHelper = new WebRequestHelper(context);
    }

    public static DashboardMarketHelper getNewInstances(MainActivity context, String market_ids) {

        if (matchMarketHelper == null) {
            matchMarketHelper = new DashboardMarketHelper(context);
        }

        matchMarketHelper.market_ids = market_ids;
        matchMarketHelper.context=context;

        return matchMarketHelper;
    }


    public void setDashboardMarketUpdaterListener(DashboardMarketUpdaterListener dashboardMarketUpdaterListener) {
        this.dashboardMarketUpdaterListener = dashboardMarketUpdaterListener;
    }

    private void getOddsByMarketIds() {

        if (market_ids != null) {
            previousRequest = webRequestHelper.getOddsByMarketIds(market_ids, webServiceResponseListener);
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
            } catch (IOException | WebServiceException | IllegalStateException e) {
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


    private void handleMarketIdsResponse(WebRequest webRequest) {
        String response = webRequest.getResponseString();
        if (context.isValidString(response)) {
            try {
                final List<OddsByMarketIdsModel> responseModel = webRequest.
                        getResponsePojoSimpleList(new TypeToken<List<OddsByMarketIdsModel>>() {
                        });

                if (responseModel != null && context.getDashboardFragment() != null) {
                    final List<MatchDetailModel> newCricketList = new ArrayList<>();
                    final List<MatchDetailModel> newTennisList = new ArrayList<>();
                    final List<MatchDetailModel> newSoccerList = new ArrayList<>();

                    List<MatchDetailModel> oldCricketList = context.getDashboardFragment().adapter_cricket.getList();
                    List<MatchDetailModel> oldTennisList = context.getDashboardFragment().adapter_tennis.getList();
                    List<MatchDetailModel> oldSoccerList = context.getDashboardFragment().adapter_soccer.getList();

                    if (oldCricketList != null) {
                        for (MatchDetailModel matchMarketModel : oldCricketList) {
                            newCricketList.add(matchMarketModel.copyOf(matchMarketModel));
                        }

                    }

                    if (oldTennisList != null) {
                        for (MatchDetailModel matchMarketModel : oldTennisList) {
                            newTennisList.add(matchMarketModel.copyOf(matchMarketModel));
                        }

                    }


                    if (oldSoccerList != null) {
                        for (MatchDetailModel matchMarketModel : oldSoccerList) {
                            newSoccerList.add(matchMarketModel.copyOf(matchMarketModel));
                        }
                    }

                    StringBuilder builder = new StringBuilder();
                    for (OddsByMarketIdsModel oddsByMarketIdsModel : responseModel) {
//                        String eventTypeId = oddsByMarketIdsModel.getEventTypeId();
                        boolean marketFound = false;

                        for (MatchDetailModel matchDetailModel : newCricketList) {
                            boolean matched = false;
                            for (MatchMarketModel matchMarketModel : matchDetailModel.getMarkets()) {
                                if (matchMarketModel.getMarketid().equals(oddsByMarketIdsModel.getId())) {
                                    matchMarketModel.setInPlay(oddsByMarketIdsModel.isInPlay());
                                    matchMarketModel.setStatus(oddsByMarketIdsModel.getStatus());
                                    List<RunnersModel> runners = oddsByMarketIdsModel.getRunners();
                                    if (runners == null || runners.size() == 0) {
                                        matchMarketModel.setMatchDisable(true);
                                    } else {
                                        matchMarketModel.setMatchDisable(false);
                                    }

                                    if (!matchMarketModel.isMatchDisable()) {
                                        List<RunnersModel> oldData = new ArrayList<>(matchMarketModel.getRunners());
                                        oldData.removeAll(runners);
                                        for (RunnersModel oldDatum : oldData) {
                                            int index = matchMarketModel.getRunners().indexOf(oldDatum);
                                            if (index == -1) continue;
                                            matchMarketModel.getRunners().remove(index);
                                        }

                                        for (RunnersModel runnersModel : runners) {
                                            int index = matchMarketModel.getRunners().indexOf(runnersModel);
                                            if (index == -1) {
                                                matchMarketModel.getRunners().add(runnersModel);
                                            } else {
                                                //  matchMarketModel.getRunners().get(index).setRunnerName(runnersModel.getRunnerName());
                                                matchMarketModel.getRunners().get(index).setBack(runnersModel.getBack());
                                                matchMarketModel.getRunners().get(index).setLay(runnersModel.getLay());
                                            }
                                        }
                                    }
                                    matched = true;
                                    if (matchMarketModel.isInPlay()) {
                                        if (builder.length() > 0)
                                            builder.append(",");
                                        builder.append(matchMarketModel.getMarketid());
                                    }
                                    break;
                                }

                            }
                            if (matchDetailModel.getMarkets() != null && matchDetailModel.getMarkets().size() > 0) {
                                matchDetailModel.setInPlay(matchDetailModel.getMarkets().get(0).isInPlay());
                            } else {
                                matchDetailModel.setInPlay(false);
                            }
                            if (matched) {
                                marketFound = true;
                                break;
                            }
                        }


                        if (!marketFound) {

                            for (MatchDetailModel matchDetailModel : newTennisList) {
                                boolean matched = false;
                                for (MatchMarketModel matchMarketModel : matchDetailModel.getMarkets()) {
                                    if (matchMarketModel.getMarketid().equals(oddsByMarketIdsModel.getId())) {
                                        matchMarketModel.setInPlay(oddsByMarketIdsModel.isInPlay());
                                        matchMarketModel.setStatus(oddsByMarketIdsModel.getStatus());
                                        List<RunnersModel> runners = oddsByMarketIdsModel.getRunners();
                                        if (runners == null || runners.size() == 0) {
                                            matchMarketModel.setMatchDisable(true);
                                        } else {
                                            matchMarketModel.setMatchDisable(false);
                                        }

                                        if (!matchMarketModel.isMatchDisable()) {
                                            List<RunnersModel> oldData = new ArrayList<>(matchMarketModel.getRunners());
                                            oldData.removeAll(runners);
                                            for (RunnersModel oldDatum : oldData) {
                                                int index = matchMarketModel.getRunners().indexOf(oldDatum);
                                                if (index == -1) continue;
                                                matchMarketModel.getRunners().remove(index);
                                            }

                                            for (RunnersModel runnersModel : runners) {
                                                int index = matchMarketModel.getRunners().indexOf(runnersModel);
                                                if (index == -1) {
                                                    matchMarketModel.getRunners().add(runnersModel);
                                                } else {
                                                    // matchMarketModel.getRunners().get(index).setRunnerName(runnersModel.getRunnerName());
                                                    matchMarketModel.getRunners().get(index).setBack(runnersModel.getBack());
                                                    matchMarketModel.getRunners().get(index).setLay(runnersModel.getLay());
                                                }
                                            }
                                        }
                                        matched = true;
                                        if (matchMarketModel.isInPlay()) {
                                            if (builder.length() > 0)
                                                builder.append(",");
                                            builder.append(matchMarketModel.getMarketid());
                                        }
                                        break;
                                    }

                                }

                                if (matchDetailModel.getMarkets() != null && matchDetailModel.getMarkets().size() > 0) {
                                    matchDetailModel.setInPlay(matchDetailModel.getMarkets().get(0).isInPlay());
                                } else {
                                    matchDetailModel.setInPlay(false);
                                }
                                if (matched) {
                                    marketFound = true;
                                    break;
                                }
                            }


                        }
                        if (!marketFound) {

                            for (MatchDetailModel matchDetailModel : newSoccerList) {
                                boolean matched = false;
                                for (MatchMarketModel matchMarketModel : matchDetailModel.getMarkets()) {
                                    if (matchMarketModel.getMarketid().equals(oddsByMarketIdsModel.getId())) {
                                        matchMarketModel.setInPlay(oddsByMarketIdsModel.isInPlay());
                                        matchMarketModel.setStatus(oddsByMarketIdsModel.getStatus());
                                        List<RunnersModel> runners = oddsByMarketIdsModel.getRunners();
                                        if (runners == null || runners.size() == 0) {
                                            matchMarketModel.setMatchDisable(true);
                                        } else {
                                            matchMarketModel.setMatchDisable(false);
                                        }

                                        if (!matchMarketModel.isMatchDisable()) {
                                            List<RunnersModel> oldData = new ArrayList<>(matchMarketModel.getRunners());
                                            oldData.removeAll(runners);
                                            for (RunnersModel oldDatum : oldData) {
                                                int index = matchMarketModel.getRunners().indexOf(oldDatum);
                                                if (index == -1) continue;
                                                matchMarketModel.getRunners().remove(index);
                                            }

                                            for (RunnersModel runnersModel : runners) {
                                                int index = matchMarketModel.getRunners().indexOf(runnersModel);
                                                if (index == -1) {
                                                    matchMarketModel.getRunners().add(runnersModel);
                                                } else {
                                                    //  matchMarketModel.getRunners().get(index).setRunnerName(runnersModel.getRunnerName());
                                                    matchMarketModel.getRunners().get(index).setBack(runnersModel.getBack());
                                                    matchMarketModel.getRunners().get(index).setLay(runnersModel.getLay());
                                                }
                                            }
                                        }
                                        matched = true;
                                        if (matchMarketModel.isInPlay()) {
                                            if (builder.length() > 0)
                                                builder.append(",");
                                            builder.append(matchMarketModel.getMarketid());
                                        }
                                        break;
                                    }

                                }
                                if (matchDetailModel.getMarkets() != null && matchDetailModel.getMarkets().size() > 0) {
                                    matchDetailModel.setInPlay(matchDetailModel.getMarkets().get(0).isInPlay());
                                } else {
                                    matchDetailModel.setInPlay(false);
                                }

                                if (matched) {
                                    marketFound = true;
                                    break;
                                }
                            }

                        }

                    }

                    inplayMarket_ids = builder.toString();

                    Collections.sort(newCricketList, new Comparator<MatchDetailModel>() {
                        @Override
                        public int compare(MatchDetailModel o1, MatchDetailModel o2) {
                            return Boolean.compare(o2.isInPlay(), o1.isInPlay());
                        }
                    });
                    Collections.sort(newTennisList, new Comparator<MatchDetailModel>() {
                        @Override
                        public int compare(MatchDetailModel o1, MatchDetailModel o2) {
                            return Boolean.compare(o2.isInPlay(), o1.isInPlay());
                        }
                    });
                    Collections.sort(newSoccerList, new Comparator<MatchDetailModel>() {
                        @Override
                        public int compare(MatchDetailModel o1, MatchDetailModel o2) {
                            return Boolean.compare(o2.isInPlay(), o1.isInPlay());
                        }
                    });


                    for (MatchDetailModel matchDetailModel : newCricketList) {
                        int i = oldCricketList.indexOf(matchDetailModel);
                        if (i >= 0) {
                            MatchDetailModel oldMatchDetailModel = oldCricketList.get(i);
                            if (oldMatchDetailModel.getMarkets() != null) {
                                for (MatchMarketModel matchMarketModel : matchDetailModel.getMarkets()) {
                                    int i1 = oldMatchDetailModel.getMarkets().indexOf(matchMarketModel);
                                    if (i1 >= 0) {
                                        DiffCallBackRunnerModel3 diffCallback =
                                                new DiffCallBackRunnerModel3(oldMatchDetailModel.getMarkets().get(i1).getRunners(),
                                                        matchMarketModel.getRunners());
                                        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);
                                        matchMarketModel.setDiffResultRunners(diffResult);
                                    }
                                }
                            }


                        }
                    }

                    for (MatchDetailModel matchDetailModel : newTennisList) {
                        int i = oldTennisList.indexOf(matchDetailModel);
                        if (i >= 0) {
                            MatchDetailModel oldMatchDetailModel = oldTennisList.get(i);
                            if (oldMatchDetailModel.getMarkets() != null) {
                                for (MatchMarketModel matchMarketModel : matchDetailModel.getMarkets()) {
                                    int i1 = oldMatchDetailModel.getMarkets().indexOf(matchMarketModel);
                                    if (i1 >= 0) {
                                        DiffCallBackRunnerModel3 diffCallback =
                                                new DiffCallBackRunnerModel3(oldMatchDetailModel.getMarkets().get(i1).getRunners(),
                                                        matchMarketModel.getRunners());
                                        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);
                                        matchMarketModel.setDiffResultRunners(diffResult);
                                    }
                                }
                            }


                        }
                    }

                    for (MatchDetailModel matchDetailModel : newSoccerList) {
                        int i = oldSoccerList.indexOf(matchDetailModel);
                        if (i >= 0) {
                            MatchDetailModel oldMatchDetailModel = oldSoccerList.get(i);
                            if (oldMatchDetailModel.getMarkets() != null) {
                                for (MatchMarketModel matchMarketModel : matchDetailModel.getMarkets()) {
                                    int i1 = oldMatchDetailModel.getMarkets().indexOf(matchMarketModel);
                                    if (i1 >= 0) {
                                        DiffCallBackRunnerModel3 diffCallback =
                                                new DiffCallBackRunnerModel3(oldMatchDetailModel.getMarkets().get(i1).getRunners(),
                                                        matchMarketModel.getRunners());
                                        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);
                                        matchMarketModel.setDiffResultRunners(diffResult);
                                    }
                                }
                            }


                        }
                    }


                    /**
                     * cricket diff callback
                     */
                    DiffCallBackDashboardMatchDetailModel diffCallBackDashboardMatchDetailModel = new DiffCallBackDashboardMatchDetailModel(2, oldCricketList,
                            newCricketList);
                    final DiffUtil.DiffResult diffResultCricket = DiffUtil.calculateDiff(diffCallBackDashboardMatchDetailModel);


                    /**
                     * Tennis diff callback
                     */
                    diffCallBackDashboardMatchDetailModel = new DiffCallBackDashboardMatchDetailModel(2, oldTennisList,
                            newTennisList);
                    final DiffUtil.DiffResult diffResultTennis = DiffUtil.calculateDiff(diffCallBackDashboardMatchDetailModel);


                    /**
                     * Soccer diff callback
                     */
                    diffCallBackDashboardMatchDetailModel = new DiffCallBackDashboardMatchDetailModel(2, oldSoccerList,
                            newSoccerList);
                    final DiffUtil.DiffResult diffResultSoccer = DiffUtil.calculateDiff(diffCallBackDashboardMatchDetailModel);


                    if (dashboardMarketUpdaterListener != null) {
                        if (context != null && !context.isFinishing()) {
                            context.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (dashboardMarketUpdaterListener != null) {
                                        dashboardMarketUpdaterListener.onMarketUpdate(newCricketList,
                                                newTennisList,
                                                newSoccerList);

                                        dashboardMarketUpdaterListener.onMatchListUpdateFromMarket(newCricketList, diffResultCricket,
                                                newTennisList, diffResultTennis,
                                                newSoccerList, diffResultSoccer);
                                    }
                                    callNext(UPDATE_INTERVAL);
                                }
                            });
                        }
                    }


                }
            } catch (ConcurrentModificationException e) {
                callNext(UPDATE_INTERVAL);
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


    public void startDashboardMarketUpdater() {
        stopDashboardMarketUpdater(false);
        if (market_ids != null) {
            isUpdaterStart = true;
            mHandlerThread = new HandlerThread("HandlerThread_" + getClass().getSimpleName());
            mHandlerThread.start();
            handler = new Handler(mHandlerThread.getLooper());
            callNext(1);
        }

    }

    public void stopDashboardMarketUpdater(boolean needClean) {
        if (previousRequest != null) {
            previousRequest.cancel();
        }
        if (mHandlerThread != null) {
            mHandlerThread.quit();
        }
        isUpdaterStart = false;
    }

    public interface DashboardMarketUpdaterListener {
        void onMarketUpdate(List<MatchDetailModel> newCricketList,
                            List<MatchDetailModel> newTennisList,
                            List<MatchDetailModel> newSoccerList);

        void onMatchListUpdateFromMarket(List<MatchDetailModel> newCricketList, DiffUtil.DiffResult diffResultCricket,
                                         List<MatchDetailModel> newTennisList, DiffUtil.DiffResult diffResultTennis,
                                         List<MatchDetailModel> newSoccerList, DiffUtil.DiffResult diffResultSoccer);
    }

}
