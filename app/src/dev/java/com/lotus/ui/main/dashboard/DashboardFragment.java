package com.lotus.ui.main.dashboard;


import android.app.Dialog;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.lotus.R;
import com.lotus.appBase.AppBaseFragment;
import com.lotus.model.AvailableToBackModel;
import com.lotus.model.AvailableToLayModel;
import com.lotus.model.BetSlipModel;
import com.lotus.model.MatchDetailModel;
import com.lotus.model.MatchMarketModel;
import com.lotus.model.RunnersModel;
import com.lotus.model.request_model.FavouriteRequestModel;
import com.lotus.model.web_response.FavouriteResponseModel;
import com.lotus.ui.main.MainActivity;
import com.lotus.ui.main.betSlip.BetSlipFragment;
import com.lotus.ui.main.dashboard.adapter.CricketDashAdapter;
import com.lotus.ui.main.dashboard.adapter.SoccerDashAdapter;
import com.lotus.ui.main.dashboard.adapter.TennisDashAdapter;
import com.lotus.ui.main.dialog.PlaceBetSuccessDialog;
import com.lotus.ui.main.matchDetail.MatchDetailFragment;
import com.lotus.utilites.DashboardMarketHelper;
import com.lotus.utilites.DashboardMatchListHelper;
import com.medy.retrofitwrapper.WebRequest;
import com.utilities.ItemClickSupport;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends AppBaseFragment
        implements DashboardMarketHelper.DashboardMarketUpdaterListener,
        DashboardMatchListHelper.MatchListListener, BetSlipFragment.BetSlipDataListener {

    public static final String TAG = DashboardFragment.class.getSimpleName();
    private DashboardMarketHelper dashboardMarketHelper;

    private RecyclerView recycler_view_cricket;
    public CricketDashAdapter adapter_cricket;

    private RecyclerView recycler_view_soccer;
    public SoccerDashAdapter adapter_soccer;

    private RecyclerView recycler_view_tennis;
    public TennisDashAdapter adapter_tennis;

    private DashboardMatchListHelper dashboardMatchListHelper;

    TextView tv_no_record_found_cricket;
    TextView tv_no_record_found_soccer;
    TextView tv_no_record_found_tennis;

    @Override
    public int getLayoutResourceId() {
        return R.layout.fragment_dashboard;
    }

    @Override
    public void initializeComponent() {
        super.initializeComponent();


        tv_no_record_found_cricket = getView().findViewById(R.id.tv_no_record_found_cricket);
        tv_no_record_found_soccer = getView().findViewById(R.id.tv_no_record_found_soccer);
        tv_no_record_found_tennis = getView().findViewById(R.id.tv_no_record_found_tennis);


        initializeCricketRecyclerView();
        initializeSoccerRecyclerView();
        initializeTennisRecyclerView();
        try {
            dashboardMatchListHelper = DashboardMatchListHelper.getNewInstances(getMainActivity());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        displaySecondProgressBar(false, getString(R.string.please_wait));
    }

    private void initializeCricketRecyclerView() {
        recycler_view_cricket = getView().findViewById(R.id.recycler_view_cricket);
        adapter_cricket = new CricketDashAdapter(getActivity()) {
            @Override
            public List<BetSlipModel> getPlacedBets(String marketId) {
                if (getActivity() != null) {
                    return ((MainActivity) getActivity()).getBetSlipFragment().getMatchOddsBets(marketId);
                }
                return super.getPlacedBets(marketId);
            }
        };
        recycler_view_cricket.setLayoutManager(getVerticalLinearLayoutManager());
        recycler_view_cricket.setAdapter(adapter_cricket);
        new ItemClickSupport(recycler_view_cricket) {
            @Override
            public void onChildItemClicked(RecyclerView recyclerView, int parentPosition, int childPosition, View v) {
                MatchDetailModel matchDetailModel = adapter_cricket.getItem(parentPosition);
                if (matchDetailModel == null) return;
                BetSlipModel betSlipModel = new BetSlipModel();
                if (v.getId() == R.id.ll_back) {
                    betSlipModel.setType("Back");
                    addBetData(matchDetailModel, betSlipModel, 0, childPosition);
                } else if (v.getId() == R.id.ll_lay) {
                    betSlipModel.setType("Lay");
                    addBetData(matchDetailModel, betSlipModel, 0, childPosition);
                }
            }
        }.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                MatchDetailModel matchDetailModel = adapter_cricket.getItem(position);
                if (matchDetailModel == null) return;
                if (v.getId() == R.id.iv_star_icon) {
                    favouriteMatch(matchDetailModel, 0);
                } else {
                    addMatchDetailFragment(matchDetailModel);
                }
            }
        });
    }

    private void initializeSoccerRecyclerView() {
        recycler_view_soccer = getView().findViewById(R.id.recycler_view_soccer);
        adapter_soccer = new SoccerDashAdapter(getActivity()) {
            @Override
            public List<BetSlipModel> getPlacedBets(String marketId) {
                if (getActivity() != null) {
                    return ((MainActivity) getActivity()).getBetSlipFragment().getMatchOddsBets(marketId);
                }
                return super.getPlacedBets(marketId);
            }
        };
        recycler_view_soccer.setLayoutManager(getVerticalLinearLayoutManager());
        recycler_view_soccer.setAdapter(adapter_soccer);
        new ItemClickSupport(recycler_view_soccer) {
            @Override
            public void onChildItemClicked(RecyclerView recyclerView, int parentPosition, int childPosition, View v) {
                MatchDetailModel matchDetailModel = adapter_soccer.getItem(parentPosition);
                if (matchDetailModel == null) return;
                BetSlipModel betSlipModel = new BetSlipModel();
                if (v.getId() == R.id.ll_back) {
                    betSlipModel.setType("Back");
                    addBetData(matchDetailModel, betSlipModel, 0, childPosition);
                } else if (v.getId() == R.id.ll_lay) {
                    betSlipModel.setType("Lay");
                    addBetData(matchDetailModel, betSlipModel, 0, childPosition);
                }
            }
        }.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                MatchDetailModel matchDetailModel = adapter_soccer.getItem(position);
                if (matchDetailModel == null) return;
                if (v.getId() == R.id.iv_star_icon) {
                    favouriteMatch(matchDetailModel, 0);
                } else {
                    addMatchDetailFragment(matchDetailModel);
                }
            }
        });
    }

    private void initializeTennisRecyclerView() {
        recycler_view_tennis = getView().findViewById(R.id.recycler_view_tennis);
        adapter_tennis = new TennisDashAdapter(getActivity()) {
            @Override
            public List<BetSlipModel> getPlacedBets(String marketId) {
                if (getActivity() != null) {
                    return ((MainActivity) getActivity()).getBetSlipFragment().getMatchOddsBets(marketId);
                }
                return super.getPlacedBets(marketId);
            }
        };
        recycler_view_tennis.setLayoutManager(getVerticalLinearLayoutManager());
        recycler_view_tennis.setAdapter(adapter_tennis);
        new ItemClickSupport(recycler_view_tennis) {
            @Override
            public void onChildItemClicked(RecyclerView recyclerView, int parentPosition, int childPosition, View v) {
                MatchDetailModel matchDetailModel = adapter_tennis.getItem(parentPosition);
                if (matchDetailModel == null) return;
                BetSlipModel betSlipModel = new BetSlipModel();
                if (v.getId() == R.id.ll_back) {
                    betSlipModel.setType("Back");
                    addBetData(matchDetailModel, betSlipModel, 0, childPosition);
                } else if (v.getId() == R.id.ll_lay) {
                    betSlipModel.setType("Lay");
                    addBetData(matchDetailModel, betSlipModel, 0, childPosition);
                }
            }
        }.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                MatchDetailModel matchDetailModel = adapter_tennis.getItem(position);
                if (matchDetailModel == null) return;
                if (v.getId() == R.id.iv_star_icon) {
                    favouriteMatch(matchDetailModel, 0);
                } else {
                    addMatchDetailFragment(matchDetailModel);
                }
            }
        });
    }


    private void favouriteMatch(MatchDetailModel matchDetailModel, int position) {
        FavouriteRequestModel requestModel = new FavouriteRequestModel();
        requestModel.market_id = matchDetailModel.getMarkets().get(position).getMarketid();
        displayProgressBar(false, getString(R.string.please_wait));
        if (matchDetailModel.getMarkets().get(position).isFavourite())
            getWebRequestHelper().unfavourite(matchDetailModel, requestModel, this);
        else
            getWebRequestHelper().favourite(matchDetailModel, requestModel, this);
    }

    private void addBetData(MatchDetailModel matchDetailModel, BetSlipModel betSlipModel, int marketPosition,
                            int runnerPosition) {
        try {
            MatchMarketModel matchMarketModel = matchDetailModel.getMarkets().get(marketPosition);
            List<RunnersModel> runners = matchMarketModel.getRunners();
            if (runnerPosition >= runners.size()) return;
            RunnersModel runnersModel = matchMarketModel.getRunners().get(runnerPosition);
            betSlipModel.setSelectionId(runnersModel.getSelectionId());
            betSlipModel.setSelectionName(runnersModel.getRunnerName());
            if (betSlipModel.isBack()) {
                AvailableToBackModel backModel = runnersModel.getBackOdds();
                betSlipModel.setPrice(backModel.getPriceText());
                betSlipModel.setSize(backModel.getSizeText());
            } else {
                AvailableToLayModel layModel = runnersModel.getLayOdds();
                betSlipModel.setPrice(layModel.getPriceText());
                betSlipModel.setSize(layModel.getSizeText());
            }

            betSlipModel.setCurrentOdds(betSlipModel.getPrice());
            betSlipModel.setMarketid(matchMarketModel.getMarketid());

            betSlipModel.setMatchName(matchDetailModel.getMatchName());
            betSlipModel.setMatchid(matchDetailModel.getMatchid());
            betSlipModel.setSportId(matchDetailModel.getSportId());
            betSlipModel.setMatchdate(matchDetailModel.getServerMatchDate());

            addBetSlipFragment(matchMarketModel, betSlipModel);
        } catch (Exception ignore) {

        }

    }

    private void addBetSlipFragment(MatchMarketModel matchMarketModel, BetSlipModel betSlipModel) {
        try {
            updateViewVisibility(getMainActivity().getBottom_container(), View.VISIBLE);
            getMainActivity().getBetSlipFragment().updateBetData(matchMarketModel, null, betSlipModel);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void addMatchDetailFragment(MatchDetailModel matchDetailModel) {
        if (matchDetailModel == null) return;
        MatchDetailFragment fragment = new MatchDetailFragment();
        fragment.setMatchDetailModel(matchDetailModel);
        int enterAnimation = R.anim.right_in;
        int exitAnimation = 0;
        int enterAnimationBackStack = 0;
        int exitAnimationBackStack = R.anim.left_out;
        try {
            getMainActivity().changeFragment(fragment, true, false, 0,
                    enterAnimation, exitAnimation, enterAnimationBackStack, exitAnimationBackStack, false);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onWebRequestResponse(WebRequest webRequest) {
        dismissProgressBar();
        super.onWebRequestResponse(webRequest);
        if (webRequest.getWebRequestId() == ID_FAVOURITE) {
            handleFavouriteResponse(webRequest);
            return;
        } else if (webRequest.getWebRequestId() == ID_UNFAVOURITE) {
            handleUnFavouriteResponse(webRequest);
            return;
        }

    }

    private void handleFavouriteResponse(WebRequest webRequest) {
        MatchDetailModel matchDetailModel = webRequest.getExtraData(KEY_MATCHDETAIL);
        FavouriteRequestModel favouriteRequestModel = webRequest.getExtraData(KEY_FAVOURITE);

        FavouriteResponseModel responseModel = webRequest.getResponsePojo(FavouriteResponseModel.class);
        if (responseModel == null || responseModel.isError()) return;
        long sportId = matchDetailModel.getSportId();
        if (sportId == 4) {
            List<MatchDetailModel> list = adapter_cricket.getList();
            int index = list.indexOf(matchDetailModel);
            if (index >= 0) {
                startMarketUpdater();
                startMatchListUpdater();
//                if (!MyApplication.getInstance().favouriteMarketIds.contains(favouriteRequestModel.market_id)) {
//                    MyApplication.getInstance().favouriteMarketIds.add(favouriteRequestModel.market_id);
//                }
                list.get(index).getMarkets().get(0).setIs_favourite("Y");
                adapter_cricket.notifyItemChanged(index);
            }
        } else if (sportId == 2) {
            List<MatchDetailModel> list = adapter_tennis.getList();
            int index = list.indexOf(matchDetailModel);
            if (index >= 0) {
                startMarketUpdater();
                startMatchListUpdater();
//                if (!MyApplication.getInstance().favouriteMarketIds.contains(favouriteRequestModel.market_id)) {
//                    MyApplication.getInstance().favouriteMarketIds.add(favouriteRequestModel.market_id);
//
//                }
                list.get(index).getMarkets().get(0).setIs_favourite("Y");
                adapter_tennis.notifyItemChanged(index);

            }
        } else if (sportId == 1) {
            List<MatchDetailModel> list = adapter_soccer.getList();
            int index = list.indexOf(matchDetailModel);
            if (index >= 0) {
                startMarketUpdater();
                startMatchListUpdater();
//                if (!MyApplication.getInstance().favouriteMarketIds.contains(favouriteRequestModel.market_id)) {
//                    MyApplication.getInstance().favouriteMarketIds.add(favouriteRequestModel.market_id);
//
//                }
                list.get(index).getMarkets().get(0).setIs_favourite("Y");
                adapter_soccer.notifyItemChanged(index);

            }
        }
        confirmBetPlaceDialog(responseModel.getMessage());
    }

    private void handleUnFavouriteResponse(WebRequest webRequest) {
        MatchDetailModel matchDetailModel = webRequest.getExtraData(KEY_MATCHDETAIL);
        FavouriteRequestModel favouriteRequestModel = webRequest.getExtraData(KEY_UNFAVOURITE);

        FavouriteResponseModel responseModel = webRequest.getResponsePojo(FavouriteResponseModel.class);
        if (responseModel == null || responseModel.isError()) return;
        long sportId = matchDetailModel.getSportId();
        if (sportId == 4) {
            List<MatchDetailModel> list = adapter_cricket.getList();
            int index = list.indexOf(matchDetailModel);
            if (index >= 0) {
                startMarketUpdater();
                startMatchListUpdater();
//                if (MyApplication.getInstance().favouriteMarketIds.contains(favouriteRequestModel.market_id)) {
//                    MyApplication.getInstance().favouriteMarketIds.remove(favouriteRequestModel.market_id);
//
//                }
                list.get(index).getMarkets().get(0).setIs_favourite("N");
                adapter_cricket.notifyItemChanged(index);
            }
        } else if (sportId == 2) {
            List<MatchDetailModel> list = adapter_tennis.getList();
            int index = list.indexOf(matchDetailModel);
            if (index >= 0) {
                startMarketUpdater();
                startMatchListUpdater();
//                if (MyApplication.getInstance().favouriteMarketIds.contains(favouriteRequestModel.market_id)) {
//                    MyApplication.getInstance().favouriteMarketIds.remove(favouriteRequestModel.market_id);
//
//                }
                list.get(index).getMarkets().get(0).setIs_favourite("N");
                adapter_tennis.notifyItemChanged(index);
            }


        } else if (sportId == 1) {
            List<MatchDetailModel> list = adapter_soccer.getList();
            int index = list.indexOf(matchDetailModel);
            if (index >= 0) {
                startMarketUpdater();
                startMatchListUpdater();
//                if (MyApplication.getInstance().favouriteMarketIds.contains(favouriteRequestModel.market_id)) {
//                    MyApplication.getInstance().favouriteMarketIds.remove(favouriteRequestModel.market_id);
//
//                }
                list.get(index).getMarkets().get(0).setIs_favourite("N");
                adapter_soccer.notifyItemChanged(index);
            }
        }


        confirmBetPlaceDialog(responseModel.getMessage());
    }

    public void confirmBetPlaceDialog(String message) {
        PlaceBetSuccessDialog confirmationDialog = new PlaceBetSuccessDialog(getContext());
        confirmationDialog.setTextTitle(message);
        confirmationDialog.setConfirmBetDialogListener(new PlaceBetSuccessDialog.ConfirmBetDialogListener() {
            @Override
            public void onClickConfirm(Dialog dialog, View v) {
                switch (v.getId()) {
                    case R.id.tv_ok:
                        break;
                    case R.id.tv_cancel:
                        break;
                }
                dialog.dismiss();
            }
        });
        confirmationDialog.show();
    }

    private void startMarketUpdater() {
        if (isValidObject(dashboardMarketHelper)) {
            dashboardMarketHelper.setDashboardMarketUpdaterListener(this);
            dashboardMarketHelper.startDashboardMarketUpdater();
        }
    }

    private void startMatchListUpdater() {
        if (isValidObject(dashboardMatchListHelper)) {
            dashboardMatchListHelper.setMatchListListener(this);
            dashboardMatchListHelper.startMatchListHelper();
        }
    }

    private void stopUpdater() {
        if (isValidObject(dashboardMarketHelper)) {
            dashboardMarketHelper.stopDashboardMarketUpdater(true);
        }
        if (isValidObject(dashboardMatchListHelper)) {
            dashboardMatchListHelper.stopMatchListHelper(true);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getUserVisibleHint()) {
            startMarketUpdater();
            startMatchListUpdater();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        stopUpdater();
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser) {
            startMarketUpdater();
            startMatchListUpdater();
            if (adapter_cricket != null)
                adapter_cricket.notifyDataSetChanged();
            if (adapter_soccer != null)
                adapter_soccer.notifyDataSetChanged();
            if (adapter_tennis != null)
                adapter_tennis.notifyDataSetChanged();

        } else {
            stopUpdater();
        }

    }

    @Override
    public void onMatchListUpdate(String allMarketIds,
                                  List<MatchDetailModel> newCricketList, DiffUtil.DiffResult diffResultCricket,
                                  List<MatchDetailModel> newTennisList, DiffUtil.DiffResult diffResultTennis,
                                  List<MatchDetailModel> newSoccerList, DiffUtil.DiffResult diffResultSoccer) {
        dismissSecondProgressBar();
        adapter_cricket.update(newCricketList, diffResultCricket);
        adapter_tennis.update(newTennisList, diffResultTennis);
        adapter_soccer.update(newSoccerList, diffResultSoccer);

        updateNoDataView();

        try {
            dashboardMarketHelper = DashboardMarketHelper.getNewInstances(getMainActivity(), allMarketIds);
            startMarketUpdater();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onMatchListUpdateFromMarket(List<MatchDetailModel> newCricketList, DiffUtil.DiffResult diffResultCricket,
                                            List<MatchDetailModel> newTennisList, DiffUtil.DiffResult diffResultTennis,
                                            List<MatchDetailModel> newSoccerList, DiffUtil.DiffResult diffResultSoccer) {
        adapter_cricket.update(newCricketList, diffResultCricket);
        adapter_tennis.update(newTennisList, diffResultTennis);
        adapter_soccer.update(newSoccerList, diffResultSoccer);
    }

    @Override
    public void onMarketUpdateFromMatchList(List<MatchDetailModel> newCricketList,
                                            List<MatchDetailModel> newTennisList,
                                            List<MatchDetailModel> newSoccerList) {
        adapter_cricket.updateRunners(newCricketList);
        adapter_tennis.updateRunners(newTennisList);
        adapter_soccer.updateRunners(newSoccerList);
    }

    @Override
    public void onMarketUpdate(List<MatchDetailModel> newCricketList,
                               List<MatchDetailModel> newTennisList,
                               List<MatchDetailModel> newSoccerList) {
        adapter_cricket.updateRunners(newCricketList);
        adapter_tennis.updateRunners(newTennisList);
        adapter_soccer.updateRunners(newSoccerList);
    }


    private void updateNoDataView() {
        if (adapter_cricket != null) {
            updateViewVisibility(tv_no_record_found_cricket, adapter_cricket.getItemCount() == 0 ? View.VISIBLE : View.GONE);
        }
        if (adapter_tennis != null) {
            updateViewVisibility(tv_no_record_found_tennis, adapter_tennis.getItemCount() == 0 ? View.VISIBLE : View.GONE);
        }
        if (adapter_soccer != null) {
            updateViewVisibility(tv_no_record_found_soccer, adapter_soccer.getItemCount() == 0 ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    public void onBetDataChange(List<BetSlipModel> betSlipModelList, BetSlipModel betSlipModel) {

        if (betSlipModelList != null) {
            for (BetSlipModel slipModel : betSlipModelList) {
                if (slipModel.getSportId() == 4) {
                    if (adapter_cricket != null)
                        adapter_cricket.updateMatchRunner(slipModel);
                } else if (slipModel.getSportId() == 2) {
                    if (adapter_tennis != null)
                        adapter_tennis.updateMatchRunner(slipModel);
                } else if (slipModel.getSportId() == 1) {
                    if (adapter_soccer != null)
                        adapter_soccer.updateMatchRunner(slipModel);
                }
            }

        }

        if (betSlipModel != null) {
            if (betSlipModel.getSportId() == 4) {
                if (adapter_cricket != null)
                    adapter_cricket.updateMatchRunner(betSlipModel);
            } else if (betSlipModel.getSportId() == 2) {
                if (adapter_tennis != null)
                    adapter_tennis.updateMatchRunner(betSlipModel);
            } else if (betSlipModel.getSportId() == 1) {
                if (adapter_soccer != null)
                    adapter_soccer.updateMatchRunner(betSlipModel);
            }
        }


    }
}


