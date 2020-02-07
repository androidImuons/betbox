package com.lotus.ui.main.matchDetail.openBets;

import android.app.Dialog;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.base.BaseFragment;
import com.lotus.R;
import com.lotus.appBase.AppBaseFragment;
import com.lotus.model.BetDataModel;
import com.lotus.model.MatchDetailModel;
import com.lotus.model.UserModel;
import com.lotus.model.web_response.EmptyResponseModel2;
import com.lotus.ui.main.dialog.ConfirmBetDialog;
import com.lotus.ui.main.dialog.PlaceBetSuccessDialog;
import com.lotus.ui.main.matchDetail.MatchDetailFragment;
import com.lotus.ui.main.matchDetail.openBets.adapter.MatchBetAdapter;
import com.lotus.ui.main.matchDetail.openBets.adapter.UnMatchBetAdapter;
import com.lotus.utilites.DiffCallBackMatchedBetModel;
import com.lotus.utilites.DiffCallBackUnMatchedBetModel;
import com.lotus.utilites.GetBetDataHelper;
import com.medy.retrofitwrapper.WebRequest;
import com.utilities.ItemClickSupport;

import java.util.List;

public class OpenBetsFragment extends AppBaseFragment
        implements GetBetDataHelper.BetDataHelperListener {

    private CheckBox cb_Show_bet_Info;
    private LinearLayout ll_market_bets_main;
    private TextView tv_match_name;
    private TextView tv_market_name;
    private LinearLayout ll_unmatched_betdata;
    private LinearLayout ll_unmatch_header;
    private TextView tv_unmatched_bets;
    private LinearLayout ll_unmatch_header1;
    private LinearLayout ll_layout_unmatched;
    private RecyclerView recycler_view1;
    private UnMatchBetAdapter unMatchBetAdapter;
    private TextView tv_no_bet_found1;
    private LinearLayout ll_match_header;
    private TextView tv_matched_bets;
    private LinearLayout ll_match_header1;
    private LinearLayout ll_layout_matched;
    private RecyclerView recycler_view;
    private MatchBetAdapter matchBetAdapter;
    private TextView tv_no_bet_found;

    private LinearLayout ll_fancy_header;
    private TextView tv_fancy_bets;
    private LinearLayout ll_match_header2;
    private LinearLayout ll_layout_fancy;
    private RecyclerView recycler_view2;
    private MatchBetAdapter fancyBetAdapter;
    private TextView tv_no_bet_found2;

    @Override
    public int getLayoutResourceId() {
        return R.layout.fragment_open_bets;
    }

    @Override
    public void initializeComponent() {
        super.initializeComponent();
        initView();
        initializeRecyclerView1();
        initializeRecyclerView();
        initializeRecyclerView2();
        updateCount();
    }

    private void initView() {
        cb_Show_bet_Info = getView().findViewById(R.id.cb_Show_bet_Info);
        cb_Show_bet_Info.setChecked(true);
        ll_market_bets_main = getView().findViewById(R.id.ll_market_bets_main);
        tv_match_name = getView().findViewById(R.id.tv_match_name);
        tv_market_name = getView().findViewById(R.id.tv_market_name);
        ll_unmatched_betdata = getView().findViewById(R.id.ll_unmatched_betdata);
        ll_unmatch_header = getView().findViewById(R.id.ll_unmatch_header);
        tv_unmatched_bets = getView().findViewById(R.id.tv_unmatched_bets);
        ll_unmatch_header1 = getView().findViewById(R.id.ll_unmatch_header1);
        ll_layout_unmatched = getView().findViewById(R.id.ll_layout_unmatched);
        updateViewVisibility(ll_layout_unmatched, View.GONE);
        recycler_view1 = getView().findViewById(R.id.recycler_view1);
        tv_no_bet_found1 = getView().findViewById(R.id.tv_no_bet_found1);
        ll_match_header1 = getView().findViewById(R.id.ll_match_header1);
        ll_match_header = getView().findViewById(R.id.ll_match_header);
        tv_matched_bets = getView().findViewById(R.id.tv_matched_bets);
        ll_layout_matched = getView().findViewById(R.id.ll_layout_matched);
        updateViewVisibility(ll_layout_matched, View.GONE);
        recycler_view = getView().findViewById(R.id.recycler_view);
        tv_no_bet_found = getView().findViewById(R.id.tv_no_bet_found);

        updateDataUi();

        ll_unmatch_header.setOnClickListener(this);
        ll_match_header.setOnClickListener(this);
        cb_Show_bet_Info.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                updateDataUi();
            }
        });


        ll_fancy_header = getView().findViewById(R.id.ll_fancy_header);
        tv_fancy_bets = getView().findViewById(R.id.tv_fancy_bets);
        ll_match_header2 = getView().findViewById(R.id.ll_match_header2);
        ll_layout_fancy = getView().findViewById(R.id.ll_layout_fancy);
        updateViewVisibility(ll_layout_fancy, View.GONE);
        recycler_view2 = getView().findViewById(R.id.recycler_view2);
        tv_no_bet_found2 = getView().findViewById(R.id.tv_no_bet_found2);


        ll_fancy_header.setOnClickListener(this);
        try {
            UserModel userModel = getMyApplication().getUserModel();
            if (userModel != null) {
                if (userModel.isUnmatched()) {
                    updateViewVisibility(ll_unmatched_betdata, View.VISIBLE);
                } else {
                    updateViewVisibility(ll_unmatched_betdata, View.GONE);
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void updateDataUi() {
        if (cb_Show_bet_Info.isChecked()) {
            updateViewVisibility(ll_unmatch_header1, View.VISIBLE);
            updateViewVisibility(ll_match_header1, View.VISIBLE);
            updateViewVisibility(ll_match_header2, View.VISIBLE);
            updateViewVisibility(recycler_view, View.VISIBLE);
            updateViewVisibility(recycler_view1, View.VISIBLE);
            updateViewVisibility(recycler_view2, View.VISIBLE);
        } else {
            updateViewVisibility(ll_unmatch_header1, View.INVISIBLE);
            updateViewVisibility(ll_match_header1, View.INVISIBLE);
            updateViewVisibility(ll_match_header2, View.INVISIBLE);
            updateViewVisibility(recycler_view, View.GONE);
            updateViewVisibility(recycler_view1, View.GONE);
            updateViewVisibility(recycler_view2, View.GONE);
        }
    }

    private void initializeRecyclerView1() {
        DiffCallBackUnMatchedBetModel diffCallback =
                new DiffCallBackUnMatchedBetModel(null,
                        getMatchDeatilModel().getUnMatchedBet());
        DiffUtil.DiffResult unMatchedDiffResult = DiffUtil.calculateDiff(diffCallback);
        recycler_view1.setLayoutManager(getVerticalLinearLayoutManager());
        unMatchBetAdapter = new UnMatchBetAdapter(getActivity());
        unMatchBetAdapter.updateData(getMatchDeatilModel().getUnMatchedBet(), unMatchedDiffResult);
        recycler_view1.setNestedScrollingEnabled(false);
        recycler_view1.setAdapter(unMatchBetAdapter);

        ItemClickSupport.addTo(recycler_view1).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                BetDataModel.BetUserData item = unMatchBetAdapter.getItem(position);
                if (item == null) return;
                switch (v.getId()) {
                    case R.id.iv_dlt:
                        addConfirmBetDialog(item, "Are you sure want to delete Unmatched Records");
                        break;
                }
            }
        });
    }

    private void updateCount() {
        tv_unmatched_bets.setText("UnMatched Bets (" + unMatchBetAdapter.getDataCount() + ")");
        tv_matched_bets.setText("Matched Bets (" + matchBetAdapter.getDataCount() + ")");
        tv_fancy_bets.setText("Fancy Bets (" + fancyBetAdapter.getDataCount() + ")");
    }

    private void initializeRecyclerView() {
        DiffCallBackMatchedBetModel diffCallback =
                new DiffCallBackMatchedBetModel(null,
                        getMatchDeatilModel().getMatchedBet());
        DiffUtil.DiffResult matchedDiffResult = DiffUtil.calculateDiff(diffCallback);
        recycler_view.setLayoutManager(getVerticalLinearLayoutManager());
        matchBetAdapter = new MatchBetAdapter(getActivity());
        matchBetAdapter.updateData(getMatchDeatilModel().getMatchedBet(), matchedDiffResult);
        recycler_view.setNestedScrollingEnabled(false);
        recycler_view.setAdapter(matchBetAdapter);
    }


    private void initializeRecyclerView2() {
        DiffCallBackMatchedBetModel diffCallback =
                new DiffCallBackMatchedBetModel(null,
                        getMatchDeatilModel().getFancyMatchedBet());
        DiffUtil.DiffResult matchedDiffResult = DiffUtil.calculateDiff(diffCallback);
        recycler_view2.setLayoutManager(getVerticalLinearLayoutManager());
        fancyBetAdapter = new MatchBetAdapter(getActivity());
        fancyBetAdapter.updateData(getMatchDeatilModel().getFancyMatchedBet(), matchedDiffResult);
        recycler_view2.setNestedScrollingEnabled(false);
        recycler_view2.setAdapter(fancyBetAdapter);
    }


    private MatchDetailModel getMatchDeatilModel() {
        if (isValidActivity()) {
            BaseFragment latestFragment = (BaseFragment) getParentFragment();
            if (latestFragment instanceof MatchDetailFragment) {
                return ((MatchDetailFragment) latestFragment).getMatchDetailModel();
            }
        }
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_unmatch_header:
                updateViewVisibility(ll_layout_matched, View.GONE);
                updateViewVisibility(ll_layout_fancy, View.GONE);
                if (ll_layout_unmatched.getVisibility() == View.VISIBLE) {
                    updateViewVisibility(ll_layout_unmatched, View.GONE);
                    updateViewVisibility(tv_no_bet_found1, View.GONE);
                } else {
                    updateViewVisibility(ll_layout_unmatched, View.VISIBLE);
//                    updateViewVisibility(tv_no_bet_found1, View.VISIBLE);
                }
                break;

            case R.id.ll_match_header:
                updateViewVisibility(ll_layout_unmatched, View.GONE);
                updateViewVisibility(ll_layout_fancy, View.GONE);
                if (ll_layout_matched.getVisibility() == View.VISIBLE) {
                    updateViewVisibility(ll_layout_matched, View.GONE);
                    updateViewVisibility(tv_no_bet_found, View.GONE);
                } else {
                    updateViewVisibility(ll_layout_matched, View.VISIBLE);
//                    updateViewVisibility(tv_no_bet_found, View.VISIBLE);
                }
                break;

            case R.id.ll_fancy_header:
                updateViewVisibility(ll_layout_matched, View.GONE);
                updateViewVisibility(ll_layout_unmatched, View.GONE);
                if (ll_layout_fancy.getVisibility() == View.VISIBLE) {
                    updateViewVisibility(ll_layout_fancy, View.GONE);
                    updateViewVisibility(tv_no_bet_found2, View.GONE);
                } else {
                    updateViewVisibility(ll_layout_fancy, View.VISIBLE);
//                    updateViewVisibility(tv_no_bet_found, View.VISIBLE);
                }
                break;
        }
    }

    @Override
    public void onBetDataUpdate(List<BetDataModel.BetUserData> matchedBet, DiffUtil.DiffResult matchedDiffResult,
                                List<BetDataModel.BetUserData> unMatchedBet, DiffUtil.DiffResult unMatchedDiffResult,
                                List<BetDataModel.BetUserData> fancyMatchedBet, DiffUtil.DiffResult fancyDiffResult) {
        if (matchBetAdapter != null)
            matchBetAdapter.updateData(matchedBet, matchedDiffResult);
        if (unMatchBetAdapter != null)
            unMatchBetAdapter.updateData(unMatchedBet, unMatchedDiffResult);
        if (fancyBetAdapter != null)
            fancyBetAdapter.updateData(fancyMatchedBet, fancyDiffResult);

        updateCount();
    }


    public void addDeleteSuccessDialog(String title) {
        PlaceBetSuccessDialog confirmationDialog = new PlaceBetSuccessDialog(getContext());
        confirmationDialog.setTextTitle(title);
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

    public void addConfirmBetDialog(final BetDataModel.BetUserData item, String title) {
        ConfirmBetDialog dialog = new ConfirmBetDialog(getContext());
        dialog.setTextTitle(title);
        dialog.setConfirmBetDialogListener(new ConfirmBetDialog.ConfirmBetDialogListener() {
            @Override
            public void onClickConfirm(Dialog dialog, View v) {
                switch (v.getId()) {
                    case R.id.tv_ok:
                        displayProgressBar(false, getResources().getString(R.string.please_wait));
                        getWebRequestHelper().deleteUnMatchedBet(item, OpenBetsFragment.this);
                        break;
                    case R.id.tv_cancel:
                        break;
                }
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    @Override
    public void onWebRequestResponse(WebRequest webRequest) {
        dismissProgressBar();
        super.onWebRequestResponse(webRequest);
        if (webRequest.getWebRequestId() == ID_DELETE_BETTING) {
            handleDeleteResponse(webRequest);
        }
    }

    private void handleDeleteResponse(WebRequest webRequest) {
        BetDataModel.BetUserData betUserData = webRequest.getExtraData(KEY_DELETE);
        EmptyResponseModel2 responseModel = webRequest.getResponsePojo(EmptyResponseModel2.class);
        if (responseModel != null && responseModel.getError() != 1) {
            String message = responseModel.getMessage();
            if (isValidString(message)) {
                addDeleteSuccessDialog(message);
                if (unMatchBetAdapter != null && betUserData != null) {
                    List<BetDataModel.BetUserData> list = unMatchBetAdapter.getList();
                    int index = list.indexOf(betUserData);
                    if (index >= 0) {
                        list.remove(index);
                        unMatchBetAdapter.notifyItemRemoved(index);
                        unMatchBetAdapter.notifyItemRangeRemoved(index, list.size());
                    }
                }
            }
        } else {
            String message = responseModel.getMessage();
            if (isValidString(message)) {
                showErrorMessage(message);
            }
        }
    }
}
