package com.lotus.ui.main.matchDetail.market;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.base.BaseFragment;
import com.lotus.R;
import com.lotus.appBase.AppBaseFragment;
import com.lotus.model.AvailableToBackModel;
import com.lotus.model.AvailableToLayModel;
import com.lotus.model.BetSlipModel;
import com.lotus.model.IndianSessionModel;
import com.lotus.model.MatchDetailModel;
import com.lotus.model.MatchMarketModel;
import com.lotus.model.RunnersModel;
import com.lotus.model.request_model.FavouriteRequestModel;
import com.lotus.model.web_response.FavouriteResponseModel;
import com.lotus.ui.main.MainActivity;
import com.lotus.ui.main.betSlip.BetSlipFragment;
import com.lotus.ui.main.dialog.PlaceBetSuccessDialog;
import com.lotus.ui.main.matchDetail.MatchDetailFragment;
import com.lotus.ui.main.matchDetail.live.LiveFragment;
import com.lotus.ui.main.matchDetail.liveTv.LiveTVFragment;
import com.lotus.ui.main.matchDetail.market.adapter.FancyAdapter;
import com.lotus.ui.main.matchDetail.market.adapter.MarketAdapter;
import com.lotus.utilites.DiffCallBackIndianSessionModel;
import com.lotus.utilites.DiffCallBackMatchMarketModel;
import com.lotus.utilites.MarketAdminSessionHelper;
import com.lotus.utilites.MarketBetFairSessionHelper;
import com.lotus.utilites.MarketIndianSessionHelper;
import com.lotus.utilites.MarketListHelper;
import com.lotus.utilites.MarketWinLossHelper;
import com.medy.retrofitwrapper.WebRequest;
import com.utilities.ItemClickSupport;

import java.util.List;

public class MarketFragment extends AppBaseFragment
        implements MarketWinLossHelper.MarketWinLossUpdaterListener,
        MarketBetFairSessionHelper.MarketBetFairSessionListener,
        MarketIndianSessionHelper.MarketIndianSessionListener,
        MarketAdminSessionHelper.MarketAdminSessionListener,
        MarketListHelper.MarketListHelperListener, BetSlipFragment.BetSlipDataListener {

    private RecyclerView rv_markets;
    private MarketAdapter adapter;
    private RecyclerView rv_fancy;
    private FancyAdapter fancyAdapter;
    private LinearLayout ll_fancy_data_lay;


    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public int getLayoutResourceId() {
        return R.layout.fragment_markets;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void initializeComponent() {
        super.initializeComponent();

        ll_fancy_data_lay = getView().findViewById(R.id.ll_fancy_data_lay);
        rv_markets = getView().findViewById(R.id.rv_markets);
        rv_fancy = getView().findViewById(R.id.rv_fancy);


        initializeOddsRecycler();
        initializeFancyRecycler();

        updateFancyDataView();
    }

    private void updateFancyDataView() {
        if (fancyAdapter != null) {
            if (fancyAdapter.getDataCount() == 0) {
                updateViewVisibility(ll_fancy_data_lay, View.INVISIBLE);
            } else {
                updateViewVisibility(ll_fancy_data_lay, View.VISIBLE);
            }
        } else {
            updateViewVisibility(ll_fancy_data_lay, View.INVISIBLE);
        }
    }


    @Override
    public void onClick(View v) {

    }

    private MatchDetailModel getMatchDetailModel() {
        if (isValidActivity()) {
            BaseFragment latestFragment = (BaseFragment) getParentFragment();
            if (latestFragment instanceof MatchDetailFragment) {
                return ((MatchDetailFragment) latestFragment).getMatchDetailModel();
            } else if (latestFragment instanceof LiveFragment) {
                return ((MatchDetailFragment) latestFragment.getParentFragment()).getMatchDetailModel();
            } else if (latestFragment instanceof LiveTVFragment) {
                return ((MatchDetailFragment) latestFragment.getParentFragment()).getMatchDetailModel();
            }
        }
        return null;
    }

    private void initializeOddsRecycler() {
        rv_markets.setLayoutManager(getVerticalLinearLayoutManager());
        final DiffCallBackMatchMarketModel diffCallback =
                new DiffCallBackMatchMarketModel(null,
                        getMatchDetailModel().getMarkets());
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);

        adapter = new MarketAdapter(getActivity()) {
            @Override
            public List<BetSlipModel> getPlacedBets(String marketId) {
                if (getActivity() != null) {
                    return ((MainActivity) getActivity()).getBetSlipFragment().getMatchOddsBets(marketId);
                }
                return super.getPlacedBets(marketId);
            }
        };
        adapter.update(getMatchDetailModel(), diffResult);
        rv_markets.setNestedScrollingEnabled(false);
        rv_markets.setAdapter(adapter);

        new ItemClickSupport(rv_markets) {
            @Override
            public void onChildItemClicked(RecyclerView recyclerView, int parentPosition, int childPosition, View v) {
                MatchMarketModel matchMarketModel = adapter.getItem(parentPosition);
                if (matchMarketModel == null) return;
                BetSlipModel betSlipModel = new BetSlipModel();
                if (v.getId() == R.id.ll_back) {
                    betSlipModel.setType("Back");
                    addBetData(getMatchDetailModel(), betSlipModel, parentPosition, childPosition);
                } else if (v.getId() == R.id.ll_lay) {
                    betSlipModel.setType("Lay");
                    addBetData(getMatchDetailModel(), betSlipModel, parentPosition, childPosition);
                }
            }
        }.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                MatchMarketModel matchMarketModel = adapter.getItem(position);
                if (matchMarketModel == null) return;
                if (v.getId() == R.id.iv_star_icon) {
                    favouriteMatch(getMatchDetailModel(), position);
                }
            }
        });
    }

    private void initializeFancyRecycler() {
        rv_fancy.setLayoutManager(getVerticalLinearLayoutManager());
        final DiffCallBackIndianSessionModel diffCallback =
                new DiffCallBackIndianSessionModel(null,
                        getMatchDetailModel().getSessions());
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);
        fancyAdapter = new FancyAdapter(getActivity());
        fancyAdapter.update(getMatchDetailModel(), diffResult);
        rv_fancy.setNestedScrollingEnabled(false);
        RecyclerView.ItemAnimator animator = rv_fancy.getItemAnimator();
        if (animator instanceof SimpleItemAnimator) {
            ((SimpleItemAnimator) animator).setSupportsChangeAnimations(false);
        }
        rv_fancy.setAdapter(fancyAdapter);

        new ItemClickSupport(rv_fancy).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                MatchDetailModel matchDetailModel = getMatchDetailModel();
                BetSlipModel betSlipModel = new BetSlipModel();
                betSlipModel.setSession(true);
                if (v.getId() == R.id.ll_back) {
                    betSlipModel.setType("Back");
                    addBetData(matchDetailModel, betSlipModel, 0, position);
                } else if (v.getId() == R.id.ll_lay) {
                    betSlipModel.setType("Lay");
                    addBetData(matchDetailModel, betSlipModel, 0, position);
                } else if (v.getId() == R.id.iv_max_exposure) {
                    fancyAdapter.setPosition(position);
                    fancyAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void addBetData(MatchDetailModel matchDetailModel, BetSlipModel betSlipModel, int marketPosition,
                            int runnerPosition) {
        try {

            betSlipModel.setMatchName(matchDetailModel.getMatchName());
            betSlipModel.setMatchid(matchDetailModel.getMatchid());
            betSlipModel.setSportId(matchDetailModel.getSportId());
            betSlipModel.setMatchdate(matchDetailModel.getServerMatchDate());
            MatchMarketModel matchMarketModel = null;
            IndianSessionModel indianSessionModel = null;
            if (betSlipModel.isSession()) {
                matchMarketModel = matchDetailModel.getMarkets().get(0);
                betSlipModel.setMarketid(matchMarketModel.getMarketid());
                indianSessionModel = matchDetailModel.getSessions().get(runnerPosition);
                betSlipModel.setSelectionId(indianSessionModel.getID());
                betSlipModel.setSelectionName(indianSessionModel.getHeadName());
                betSlipModel.setSessInptYes(indianSessionModel.getSessInptYes());
                betSlipModel.setSessInptNo(indianSessionModel.getSessInptNo());
                betSlipModel.setNoValume(indianSessionModel.getNoValume());
                betSlipModel.setYesValume(indianSessionModel.getYesValume());
                betSlipModel.setPointDiff(indianSessionModel.getPointDiff());
                betSlipModel.setIndian_fancy_selection_id(indianSessionModel.getInd_fancy_selection_id());
                betSlipModel.setFancyID(indianSessionModel.getID());
                betSlipModel.setFancyId(indianSessionModel.getMFancyID());
                if (betSlipModel.isBack()) {
                    betSlipModel.setPrice(indianSessionModel.getSessInptYes());
                    betSlipModel.setSize(indianSessionModel.getYesValume());
                } else {
                    betSlipModel.setPrice(indianSessionModel.getSessInptNo());
                    betSlipModel.setSize(indianSessionModel.getNoValume());
                }
                betSlipModel.setCurrentOdds(betSlipModel.getPrice());

            } else {
                matchMarketModel = matchDetailModel.getMarkets().get(marketPosition);
                betSlipModel.setMarketid(matchMarketModel.getMarketid());
                List<RunnersModel> runners = matchMarketModel.getRunners();
                if (runnerPosition >= runners.size()) return;
                RunnersModel runnersModel = matchMarketModel.getRunners().get(runnerPosition);
                betSlipModel.setSelectionId(runnersModel.getSelectionId());
                betSlipModel.setSelectionName(runnersModel.getRunnerName());
                if (betSlipModel.isBack()) {
                    AvailableToBackModel backModel = runnersModel.getBackOdds();
                    if (backModel == null) return;
                    betSlipModel.setPrice(backModel.getPriceText());
                    betSlipModel.setSize(backModel.getSizeText());
                } else {
                    AvailableToLayModel layModel = runnersModel.getLayOdds();
                    if (layModel == null) return;
                    betSlipModel.setPrice(layModel.getPriceText());
                    betSlipModel.setSize(layModel.getSizeText());
                }

                betSlipModel.setCurrentOdds(betSlipModel.getPrice());
            }

            addBetSlipFragment(matchMarketModel, indianSessionModel, betSlipModel);
        } catch (Exception ignore) {

        }

    }


    private void addBetSlipFragment(MatchMarketModel matchMarketModel, IndianSessionModel indianSessionModel, BetSlipModel betSlipModel) {
        try {
            updateViewVisibility(getMainActivity().getBottom_container(), View.VISIBLE);
            getMainActivity().getBetSlipFragment().updateBetData(matchMarketModel, indianSessionModel, betSlipModel);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
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


    @Override
    public void onWebRequestResponse(WebRequest webRequest) {
        dismissProgressBar();
        super.onWebRequestResponse(webRequest);
        if (webRequest.getWebRequestId() == ID_FAVOURITE) {
            handleFavouriteResponse(webRequest);
        } else if (webRequest.getWebRequestId() == ID_UNFAVOURITE) {
            handleUnFavouriteResponse(webRequest);
        }

    }

    private void handleFavouriteResponse(WebRequest webRequest) {
        FavouriteRequestModel favouriteRequestModel = webRequest.getExtraData(KEY_FAVOURITE);

        FavouriteResponseModel responseModel = webRequest.getResponsePojo(FavouriteResponseModel.class);
        if (responseModel == null || responseModel.isError()) return;
//        if (!MyApplication.getInstance().favouriteMarketIds.contains(favouriteRequestModel.market_id)) {
//            MyApplication.getInstance().favouriteMarketIds.add(favouriteRequestModel.market_id);
//        }
        List<MatchMarketModel> list = adapter.getList();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getMarketid().equals(favouriteRequestModel.market_id)) {
                list.get(i).setIs_favourite("Y");
                adapter.notifyItemFavourite(i);
                break;
            }
        }
        confirmBetPlaceDialog(responseModel.getMessage());
    }


    private void handleUnFavouriteResponse(WebRequest webRequest) {
        FavouriteRequestModel favouriteRequestModel = webRequest.getExtraData(KEY_UNFAVOURITE);

        FavouriteResponseModel responseModel = webRequest.getResponsePojo(FavouriteResponseModel.class);
        if (responseModel == null || responseModel.isError()) return;
//        if (MyApplication.getInstance().favouriteMarketIds.contains(favouriteRequestModel.market_id)) {
//            MyApplication.getInstance().favouriteMarketIds.remove(favouriteRequestModel.market_id);
//
//        }
        List<MatchMarketModel> list = adapter.getList();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getMarketid().equals(favouriteRequestModel.market_id)) {
                list.get(i).setIs_favourite("N");
                adapter.notifyItemFavourite(i);
                break;
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

    @Override
    public void onMarketBetFairSessionUpdate(List<MatchMarketModel> data) {
        if (getMatchDetailModel() != null) {
            adapter.updateRunners(data);
        }
    }


    @Override
    public void onBetDataChange(List<BetSlipModel> betSlipModelList, BetSlipModel betSlipModel) {
        MatchDetailModel matchDetailModel = getMatchDetailModel();
        if (matchDetailModel == null) return;
        if (betSlipModelList != null) {
            for (BetSlipModel slipModel : betSlipModelList) {
                for (int i = 0; i < matchDetailModel.getMarkets().size(); i++) {
                    MatchMarketModel matchMarketModel = matchDetailModel.getMarkets().get(i);
                    if (matchMarketModel.getMarketid().equals(slipModel.getMarketid())) {
                        adapter.notifyItemChanged(i);
                        break;
                    }
                }
            }
        }

        if (betSlipModel != null) {
            for (int i = 0; i < matchDetailModel.getMarkets().size(); i++) {
                MatchMarketModel matchMarketModel = matchDetailModel.getMarkets().get(i);
                if (matchMarketModel.getMarketid().equals(betSlipModel.getMarketid())) {
                    adapter.notifyItemChanged(i);
                    break;
                }
            }
        }
    }

    @Override
    public void onMarketBetFairFancyUpdate(List<IndianSessionModel> data, DiffUtil.DiffResult diffResult) {
        if (getMatchDetailModel() == null) return;
        getMatchDetailModel().setSessions(data);
        fancyAdapter.update(getMatchDetailModel(), diffResult);
        updateFancyDataView();
    }

    @Override
    public void onMarketIndianSessionUpdate(List<IndianSessionModel> responseModel, DiffUtil.DiffResult diffResult) {
        if (getMatchDetailModel() == null) return;
        getMatchDetailModel().setSessions(responseModel);
        fancyAdapter.update(getMatchDetailModel(), diffResult);
        updateFancyDataView();

    }


    @Override
    public void onMarketWinLossUpdate(List<MatchMarketModel> data) {
        if (getMatchDetailModel() != null) {
            adapter.updateRunners(data);
        }
    }

    @Override
    public void onMarketListUpdate(List<MatchMarketModel> newData, DiffUtil.DiffResult diffResult) {
        if (newData == null)
            return;
        if (getMatchDetailModel() != null) {
            getMatchDetailModel().setMarkets(newData);
            adapter.update(getMatchDetailModel(), diffResult);
        }
    }

    @Override
    public void onMarketAdminSessionUpdate(List<IndianSessionModel> data) {

    }

}
