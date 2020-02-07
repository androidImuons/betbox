package com.lotus.ui.main.matchDetail;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DiffUtil;
import androidx.viewpager.widget.ViewPager;

import com.base.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.lotus.R;
import com.lotus.appBase.AppBaseFragment;
import com.lotus.model.BetDataModel;
import com.lotus.model.BetSlipModel;
import com.lotus.model.IndianSessionModel;
import com.lotus.model.MatchDetailModel;
import com.lotus.model.MatchMarketModel;
import com.lotus.model.MatchScoreModel;
import com.lotus.model.MatchScoreModel2;
import com.lotus.model.RunnersModel;
import com.lotus.ui.main.betSlip.BetSlipFragment;
import com.lotus.ui.main.matchDetail.live.LiveFragment;
import com.lotus.ui.main.matchDetail.liveTv.LiveTVFragment;
import com.lotus.ui.main.matchDetail.market.MarketFragment;
import com.lotus.ui.main.matchDetail.openBets.OpenBetsFragment;
import com.lotus.utilites.GetBetDataHelper;
import com.lotus.utilites.MarketAdminSessionHelper;
import com.lotus.utilites.MarketBetFairSessionHelper;
import com.lotus.utilites.MarketIndianSessionHelper;
import com.lotus.utilites.MarketListHelper;
import com.lotus.utilites.MarketWinLossHelper;
import com.lotus.utilites.MatchScoreHelper;

import java.util.ArrayList;
import java.util.List;

public class MatchDetailFragment extends AppBaseFragment
        implements MarketWinLossHelper.MarketWinLossUpdaterListener,
        MarketBetFairSessionHelper.MarketBetFairSessionListener,
        MarketIndianSessionHelper.MarketIndianSessionListener,
        MarketAdminSessionHelper.MarketAdminSessionListener,
        MatchScoreHelper.MatchScoreHelperListener,
        GetBetDataHelper.BetDataHelperListener,
        MarketListHelper.MarketListHelperListener, BetSlipFragment.BetSlipDataListener {

    private TabLayout tab_home;
    private ViewPager view_pager;
    ViewPagerAdapter adapter;

    private MarketIndianSessionHelper marketIndianSessionHelper;
    private MarketWinLossHelper marketWinLossHelper;
    private MarketBetFairSessionHelper marketBetFairSessionHelper;
    private MarketAdminSessionHelper marketAdminSessionHelper;
    private MatchScoreHelper matchScoreHelper;
    private GetBetDataHelper getBetDataHelper;
    private MarketListHelper marketListHelper;


    private MatchDetailModel matchDetailModel;

    public MatchScoreModel matchScoreModel;
    public MatchScoreModel2 matchScoreModel2;

    public MatchScoreHelper getMatchScoreHelper() {
        return matchScoreHelper;
    }

    public MatchDetailModel getMatchDetailModel() {
        return matchDetailModel;
    }

    public void setMatchDetailModel(MatchDetailModel matchDetailModel) {
        this.matchDetailModel = matchDetailModel;
    }

    @Override
    public int getLayoutResourceId() {
        return R.layout.fragment_match_detail;
    }

    @Override
    public void initializeComponent() {
        super.initializeComponent();

        try {
            if (matchDetailModel != null) {
                if (matchDetailModel.getSessions() == null) {
                    matchDetailModel.setSessions(new ArrayList<IndianSessionModel>());
                }
                if (matchDetailModel.getMarkets() != null) {
                    for (MatchMarketModel matchMarketModel : matchDetailModel.getMarkets()) {
                        if (matchMarketModel.getRunners() == null) {
                            matchMarketModel.setRunners(new ArrayList<RunnersModel>());
                        }
                    }
                }

                marketIndianSessionHelper = MarketIndianSessionHelper.getNewInstances(getMainActivity(), matchDetailModel);
                marketWinLossHelper = MarketWinLossHelper.getNewInstances(getMainActivity(), matchDetailModel);
                marketBetFairSessionHelper = MarketBetFairSessionHelper.getNewInstances(getMainActivity(), matchDetailModel);
//                marketAdminSessionHelper = MarketAdminSessionHelper.getNewInstances(getMainActivity(), matchDetailModel);
                matchScoreHelper = MatchScoreHelper.getNewInstances(getMainActivity(), matchDetailModel);
                getBetDataHelper = GetBetDataHelper.getNewInstances(getMainActivity(), matchDetailModel);
                marketListHelper = MarketListHelper.getNewInstances(getMainActivity(), matchDetailModel);

                view_pager = getView().findViewById(R.id.view_pager);
                tab_home = getView().findViewById(R.id.tab_home);

                setupViewPager();
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void setupViewPager() {
        adapter = new ViewPagerAdapter(getChildFm());
        adapter.addFragment(new MarketFragment(), "Market");
        adapter.addFragment(new LiveFragment(), "Live");
        adapter.addFragment(new LiveTVFragment(), "Live TV");
        adapter.addFragment(new OpenBetsFragment(), "OpenBets");

        view_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 1) {
                    ((LiveFragment) adapter.getItem(position)).setupMatchData();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        view_pager.setAdapter(adapter);
        tab_home.setupWithViewPager(view_pager);
    }


    private void startHelpers() {
        if (marketWinLossHelper != null) {
            marketWinLossHelper.setMarketWinLossUpdaterListener(this);
            marketWinLossHelper.startMarketWinLossHelper();
        }
        if (marketIndianSessionHelper != null) {
            marketIndianSessionHelper.setMarketIndianSessionListener(this);
            marketIndianSessionHelper.startMarketIndianSessionHelper();
        }

        if (marketBetFairSessionHelper != null) {
            marketBetFairSessionHelper.setMarketBetFairSessionListener(this);
            marketBetFairSessionHelper.startMarketBetFairSessionHelper();
        }
        if (marketAdminSessionHelper != null) {
            marketAdminSessionHelper.setMarketAdminSessionListener(this);
            marketAdminSessionHelper.startMarketAdminSessionHelper();
        }
        if (matchScoreHelper != null) {
            matchScoreHelper.setMatchScoreHelperListener(this);
            matchScoreHelper.startMatchScoreUpdater();
        }
        if (getBetDataHelper != null) {
            getBetDataHelper.setBetDataHelperListener(this);
            getBetDataHelper.startBetDataUpdater();
        }
        if (marketListHelper != null) {
            marketListHelper.setMarketListHelperListener(this);
            marketListHelper.startMarketListUpdater();
        }


    }

    private void stopHelpers() {
        if (marketIndianSessionHelper != null) {
            marketIndianSessionHelper.stopMarketIndianSessionHelper(true);
        }
        if (marketWinLossHelper != null) {
            marketWinLossHelper.stopMarketWinLossHelper(true);
        }
        if (marketBetFairSessionHelper != null) {
            marketBetFairSessionHelper.stopMarketBetFairSessionHelper(true);
        }
        if (marketAdminSessionHelper != null) {
            marketAdminSessionHelper.stopMarketAdminSessionHelper(true);
        }
        if (matchScoreHelper != null) {
            matchScoreHelper.stopMatchScoreUpdater(true);
        }
        if (getBetDataHelper != null) {
            getBetDataHelper.stopBetDataUpdater(true);
        }
        if (marketListHelper != null) {
            marketListHelper.stopMarketListUpdater(true);
        }
    }

    @Override
    public void onResume() {
        startHelpers();
        super.onResume();

    }

    @Override
    public void onPause() {
        stopHelpers();
        super.onPause();
    }

    @Override
    public void onMarketAdminSessionUpdate(List<IndianSessionModel> data) {
        if (view_pager.getCurrentItem() == 0) {
            MarketFragment marketFragment = (MarketFragment) adapter.getItem(0);
            marketFragment.onMarketAdminSessionUpdate(data);
        } else if (view_pager.getCurrentItem() == 1) {
            Fragment item = adapter.getItem(1);
            if (item instanceof LiveFragment) {
                LiveFragment liveFragment = (LiveFragment) item;
                if (liveFragment.getMarketFragment() != null)
                    liveFragment.getMarketFragment().onMarketAdminSessionUpdate(data);
            }
        } else if (view_pager.getCurrentItem() == 2) {
            Fragment item = adapter.getItem(2);
            if (item instanceof LiveTVFragment) {
                LiveTVFragment liveFragment = (LiveTVFragment) item;
                if (liveFragment.getMarketFragment() != null)
                    liveFragment.getMarketFragment().onMarketAdminSessionUpdate(data);
            }
        }
    }

    @Override
    public void onMarketBetFairFancyUpdate(List<IndianSessionModel> data, DiffUtil.DiffResult diffResult) {
        if (view_pager.getCurrentItem() == 0 || view_pager.getCurrentItem() == 1 || view_pager.getCurrentItem() == 2) {
            MarketFragment marketFragment = (MarketFragment) adapter.getItem(0);
            marketFragment.onMarketBetFairFancyUpdate(data, diffResult);
            Fragment item = adapter.getItem(1);
            if (item instanceof LiveFragment) {
                LiveFragment liveFragment = (LiveFragment) item;
                if (liveFragment.getMarketFragment() != null)
                    liveFragment.getMarketFragment().onMarketBetFairFancyUpdate(data, diffResult);
            }
            Fragment item2 = adapter.getItem(2);
            if (item2 instanceof LiveTVFragment) {
                LiveTVFragment liveFragment = (LiveTVFragment) item2;
                if (liveFragment.getMarketFragment() != null)
                    liveFragment.getMarketFragment().onMarketBetFairFancyUpdate(data, diffResult);
            }
        }
//        if (view_pager.getCurrentItem() == 1) {
//            Fragment item = adapter.getItem(1);
//            if (item instanceof LiveFragment) {
//                LiveFragment liveFragment = (LiveFragment) item;
//                if (liveFragment.getMarketFragment() != null)
//                    liveFragment.getMarketFragment().onMarketBetFairFancyUpdate(data, diffResult);
//            }
//        }
    }

    @Override
    public void onMarketBetFairSessionUpdate(List<MatchMarketModel> data) {
        if (view_pager.getCurrentItem() == 0 || view_pager.getCurrentItem() == 1 || view_pager.getCurrentItem() == 2) {
            MarketFragment marketFragment = (MarketFragment) adapter.getItem(0);
            marketFragment.onMarketBetFairSessionUpdate(data);
            Fragment item = adapter.getItem(1);
            if (item instanceof LiveFragment) {
                LiveFragment liveFragment = (LiveFragment) item;
                if (liveFragment.getMarketFragment() != null)
                    liveFragment.getMarketFragment().onMarketBetFairSessionUpdate(data);
            }
            Fragment item2 = adapter.getItem(2);
            if (item2 instanceof LiveTVFragment) {
                LiveTVFragment liveFragment = (LiveTVFragment) item2;
                if (liveFragment.getMarketFragment() != null)
                    liveFragment.getMarketFragment().onMarketBetFairSessionUpdate(data);
            }
        }
//        else if (view_pager.getCurrentItem() == 1) {
//            Fragment item = adapter.getItem(1);
//            if (item instanceof LiveFragment) {
//                LiveFragment liveFragment = (LiveFragment) item;
//                if (liveFragment.getMarketFragment() != null)
//                    liveFragment.getMarketFragment().onMarketBetFairSessionUpdate(data);
//            }
//        }

    }

    @Override
    public void onMarketIndianSessionUpdate(List<IndianSessionModel> data, DiffUtil.DiffResult diffResult) {
        if (view_pager.getCurrentItem() == 0 || view_pager.getCurrentItem() == 1 || view_pager.getCurrentItem() == 2) {
            MarketFragment marketFragment = (MarketFragment) adapter.getItem(0);
            marketFragment.onMarketIndianSessionUpdate(data, diffResult);
            Fragment item = adapter.getItem(1);
            if (item instanceof LiveFragment) {
                LiveFragment liveFragment = (LiveFragment) item;
                if (liveFragment.getMarketFragment() != null)
                    liveFragment.getMarketFragment().onMarketIndianSessionUpdate(data, diffResult);
            }
            Fragment item2 = adapter.getItem(2);
            if (item2 instanceof LiveTVFragment) {
                LiveTVFragment liveFragment = (LiveTVFragment) item2;
                if (liveFragment.getMarketFragment() != null)
                    liveFragment.getMarketFragment().onMarketIndianSessionUpdate(data, diffResult);
            }
        }
//        else if (view_pager.getCurrentItem() == 1) {
//            Fragment item = adapter.getItem(1);
//            if (item instanceof LiveFragment) {
//                LiveFragment liveFragment = (LiveFragment) item;
//                if (liveFragment.getMarketFragment() != null)
//                    liveFragment.getMarketFragment().onMarketIndianSessionUpdate(data, diffResult);
//            }
//        }
    }

    @Override
    public void onMarketWinLossUpdate(List<MatchMarketModel> data) {
        if (view_pager.getCurrentItem() == 0 || view_pager.getCurrentItem() == 1|| view_pager.getCurrentItem() == 2) {
            MarketFragment marketFragment = (MarketFragment) adapter.getItem(0);
            marketFragment.onMarketWinLossUpdate(data);

            Fragment item = adapter.getItem(1);
            if (item instanceof LiveFragment) {
                LiveFragment liveFragment = (LiveFragment) item;
                if (liveFragment.getMarketFragment() != null)
                    liveFragment.getMarketFragment().onMarketWinLossUpdate(data);
            }

            Fragment item2 = adapter.getItem(2);
            if (item2 instanceof LiveTVFragment) {
                LiveTVFragment liveFragment = (LiveTVFragment) item2;
                if (liveFragment.getMarketFragment() != null)
                    liveFragment.getMarketFragment().onMarketWinLossUpdate(data);
            }

        }
//        else if (view_pager.getCurrentItem() == 1) {
//            Fragment item = adapter.getItem(1);
//            if (item instanceof LiveFragment) {
//                LiveFragment liveFragment = (LiveFragment) item;
//                if (liveFragment.getMarketFragment() != null)
//                    liveFragment.getMarketFragment().onMarketWinLossUpdate(data);
//            }
//        }
    }

    @Override
    public void onMarketListUpdate(List<MatchMarketModel> newData, DiffUtil.DiffResult diffResult) {
        if (view_pager.getCurrentItem() == 0 || view_pager.getCurrentItem() == 1|| view_pager.getCurrentItem() == 2) {
            MarketFragment marketFragment = (MarketFragment) adapter.getItem(0);
            marketFragment.onMarketListUpdate(newData, diffResult);

            Fragment item = adapter.getItem(1);
            if (item instanceof LiveFragment) {
                LiveFragment liveFragment = (LiveFragment) item;
                if (liveFragment.getMarketFragment() != null)
                    liveFragment.getMarketFragment().onMarketListUpdate(newData, diffResult);
            }

            Fragment item2 = adapter.getItem(2);
            if (item2 instanceof LiveTVFragment) {
                LiveTVFragment liveFragment = (LiveTVFragment) item2;
                if (liveFragment.getMarketFragment() != null)
                    liveFragment.getMarketFragment().onMarketListUpdate(newData, diffResult);
            }


        }
//        else if (view_pager.getCurrentItem() == 1) {
//            Fragment item = adapter.getItem(1);
//            if (item instanceof LiveFragment) {
//                LiveFragment liveFragment = (LiveFragment) item;
//                if (liveFragment.getMarketFragment() != null)
//                    liveFragment.getMarketFragment().onMarketListUpdate(newData, diffResult);
//            }
//        }


    }

    @Override
    public void onBetDataChange(List<BetSlipModel> betSlipModelList, BetSlipModel betSlipModel) {
        if (view_pager.getCurrentItem() == 0 || view_pager.getCurrentItem() == 1|| view_pager.getCurrentItem() == 2) {
            MarketFragment marketFragment = (MarketFragment) adapter.getItem(0);
            marketFragment.onBetDataChange(betSlipModelList, betSlipModel);

            Fragment item = adapter.getItem(1);
            if (item instanceof LiveFragment) {
                LiveFragment liveFragment = (LiveFragment) item;
                if (liveFragment.getMarketFragment() != null)
                    liveFragment.getMarketFragment().onBetDataChange(betSlipModelList, betSlipModel);
            }

            Fragment item2 = adapter.getItem(2);
            if (item2 instanceof LiveTVFragment) {
                LiveTVFragment liveFragment = (LiveTVFragment) item2;
                if (liveFragment.getMarketFragment() != null)
                    liveFragment.getMarketFragment().onBetDataChange(betSlipModelList, betSlipModel);
            }


        }
//        else if (view_pager.getCurrentItem() == 1) {
//            Fragment item = adapter.getItem(1);
//            if (item instanceof LiveFragment) {
//                LiveFragment liveFragment = (LiveFragment) item;
//                if (liveFragment.getMarketFragment() != null)
//                    liveFragment.getMarketFragment().onBetDataChange(betSlipModelList, betSlipModel);
//            }
//        }
    }


    @Override
    public void onMatchScoreUpdate(MatchScoreModel responseModel) {
        matchScoreModel = responseModel;
        if (view_pager.getCurrentItem() == 0 || view_pager.getCurrentItem() == 1) {
            Fragment item = adapter.getItem(1);
            if (item instanceof LiveFragment) {
                LiveFragment liveFragment = (LiveFragment) item;
                liveFragment.onMatchScoreUpdate(responseModel);
            }
        }
    }

    @Override
    public void onMatchScoreUpdate2(MatchScoreModel2 responseModel) {
        matchScoreModel2 = responseModel;
        if (view_pager.getCurrentItem() == 0 || view_pager.getCurrentItem() == 1) {
            Fragment item = adapter.getItem(1);
            if (item instanceof LiveFragment) {
                LiveFragment liveFragment = (LiveFragment) item;
                liveFragment.onMatchScoreUpdate2(responseModel);
            }
        }
    }

    @Override
    public void onBetDataUpdate(List<BetDataModel.BetUserData> matchedBet, DiffUtil.DiffResult matchedDiffResult,
                                List<BetDataModel.BetUserData> unMatchedBet, DiffUtil.DiffResult unMatchedDiffResult,
                                List<BetDataModel.BetUserData> fancyMatchedBet, DiffUtil.DiffResult fancyDiffResult) {
        getMatchDetailModel().setMatchedBet(matchedBet);
        getMatchDetailModel().setUnMatchedBet(unMatchedBet);
        getMatchDetailModel().setFancyMatchedBet(fancyMatchedBet);
        if (view_pager.getCurrentItem() == adapter.getCount() - 1) {
            OpenBetsFragment openBetsFragment = (OpenBetsFragment) adapter.getItem(adapter.getCount() - 1);
            openBetsFragment.onBetDataUpdate(matchedBet, matchedDiffResult,
                    unMatchedBet, unMatchedDiffResult,
                    fancyMatchedBet, fancyDiffResult);
        }
    }


}
