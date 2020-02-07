package com.lotus.ui.main.betSlip;


import android.app.Dialog;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kyleduo.switchbutton.SwitchButton;
import com.lotus.BuildConfig;
import com.lotus.R;
import com.lotus.appBase.AppBaseFragment;
import com.lotus.model.AvailableToBackModel;
import com.lotus.model.AvailableToLayModel;
import com.lotus.model.BetPlaceModel;
import com.lotus.model.BetSlipModel;
import com.lotus.model.IndianSessionModel;
import com.lotus.model.MatchMarketModel;
import com.lotus.model.RunnersModel;
import com.lotus.model.StackModel;
import com.lotus.model.UserBalanceModel;
import com.lotus.model.UserModel;
import com.lotus.model.request_model.BetPlaceRequestModel;
import com.lotus.model.request_model.BetSlipRequestModel;
import com.lotus.model.request_model.ConfirmBetRequestModel;
import com.lotus.model.request_model.StackRequestModel;
import com.lotus.model.web_response.EmptyResponseModel;
import com.lotus.preferences.UserPrefs;
import com.lotus.ui.main.MainActivity;
import com.lotus.ui.main.dialog.ConfirmBetDialog;
import com.lotus.ui.main.dialog.ConfirmClearBetDialog;
import com.lotus.ui.main.dialog.PlaceBetSuccessDialog;
import com.medy.retrofitwrapper.WebRequest;
import com.medy.retrofitwrapper.WebServiceBaseResponseModel;
import com.utilities.ItemClickSupport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BetSlipFragment extends AppBaseFragment implements UserPrefs.UserStackListener {

    private BottomSheetBehavior bottomSheetBehavior;
    private RelativeLayout bottom_sheet;

    private HashMap<String, MatchMarketModel> matchMarketModelHashMap = new HashMap<>();
    private HashMap<String, IndianSessionModel> indianSessionModelHashMap = new HashMap<>();
    private List<BetSlipModel> list = new ArrayList<>();

    private SwitchButton sw_click_status;
    private LinearLayout ll_header_text;
    private TextView tv_edit_stacks;
    private LinearLayout ll_one_click_betting;
    private LinearLayout ll_edit_views;
    private LinearLayout ll_text_views;
    private RelativeLayout rl_view;
    private TextView tv_edit;
    private TextView tv_cancel;
    private TextView tv_save;
    private EditText et_1, et_2, et_3;
    private TextView tv_1, tv_2, tv_3;

    ImageView iv_full_screen;
    ImageView iv_close;
    RecyclerView recycler_view;
    BetSlipAdapter adapter;
    CardView card_no_record_found;
    LinearLayout ll_layout_recy_top;
    LinearLayout ll_layout_first;
    LinearLayout ll_layout;
    TextView tv_liability_total;
    TextView tv_remove_all_first;
    TextView tv_place_bets_first;
    TextView tv_remove_all;
    TextView tv_place_bets;
    private TextView selectedOneClickStack;
    //    private LinearLayout ll_edit_stacks;
    private CheckBox cb_confirm_batting_first;
    private CheckBox cb_confirm_batting;


    public BetSlipAdapter getAdapter() {
        return adapter;
    }

    public BottomSheetBehavior getBottomSheetBehavior() {
        return bottomSheetBehavior;
    }

    @Override
    public int getLayoutResourceId() {
        return R.layout.fragment_bet_slip;
    }

    @Override
    public void initializeComponent() {
        super.initializeComponent();
        bottom_sheet = getView().findViewById(R.id.bottom_sheet);


        sw_click_status = getView().findViewById(R.id.sw_click_status);
        ll_header_text = getView().findViewById(R.id.ll_header_text);
        tv_edit_stacks = getView().findViewById(R.id.tv_edit_stacks);
        ll_one_click_betting = getView().findViewById(R.id.ll_one_click_betting);
        ll_edit_views = getView().findViewById(R.id.ll_edit_views);
        rl_view = getView().findViewById(R.id.rl_view);
        tv_edit = getView().findViewById(R.id.tv_edit);
        tv_save = getView().findViewById(R.id.tv_save);
        tv_cancel = getView().findViewById(R.id.tv_cancel);
        et_1 = getView().findViewById(R.id.et_1);
        et_2 = getView().findViewById(R.id.et_2);
        et_3 = getView().findViewById(R.id.et_3);
//        ll_edit_stacks = getView().findViewById(R.id.ll_edit_stacks);
        tv_1 = getView().findViewById(R.id.tv_1);
        tv_2 = getView().findViewById(R.id.tv_2);
        tv_3 = getView().findViewById(R.id.tv_3);
        ll_text_views = getView().findViewById(R.id.ll_text_views);
        cb_confirm_batting_first = getView().findViewById(R.id.cb_confirm_batting_first);
        cb_confirm_batting = getView().findViewById(R.id.cb_confirm_batting);
        if (!sw_click_status.isChecked()) {
            updateViewVisibility(ll_one_click_betting, View.GONE);
        }

        sw_click_status.setOnCheckedChangeListener(onCheckedChangeListener);
        setSelectedToEditText(tv_1);
        tv_edit.setOnClickListener(this);
        tv_save.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);
        tv_edit_stacks.setOnClickListener(this);

        tv_1.setOnClickListener(this);
        tv_2.setOnClickListener(this);
        tv_3.setOnClickListener(this);

        iv_full_screen = getView().findViewById(R.id.iv_full_screen);
        iv_close = getView().findViewById(R.id.iv_close);
        recycler_view = getView().findViewById(R.id.recycler_view);
        card_no_record_found = getView().findViewById(R.id.card_no_record_found);
        updateViewVisibility(card_no_record_found, View.GONE);
        ll_layout_recy_top = getView().findViewById(R.id.ll_layout_recy_top);
        ll_layout_first = getView().findViewById(R.id.ll_layout_first);
        ll_layout = getView().findViewById(R.id.ll_layout);
        updateViewVisibility(ll_layout, View.GONE);
        updateViewVisibility(ll_layout_first, View.GONE);
        tv_liability_total = getView().findViewById(R.id.tv_liability_total);
        tv_remove_all_first = getView().findViewById(R.id.tv_remove_all_first);
        tv_place_bets_first = getView().findViewById(R.id.tv_place_bets_first);
        tv_remove_all = getView().findViewById(R.id.tv_remove_all);
        tv_place_bets = getView().findViewById(R.id.tv_place_bets);
        cb_confirm_batting_first.setOnClickListener(this);
        cb_confirm_batting.setOnClickListener(this);
        tv_remove_all_first.setOnClickListener(this);
        tv_place_bets_first.setOnClickListener(this);
        tv_remove_all.setOnClickListener(this);
        tv_place_bets.setOnClickListener(this);
        iv_full_screen.setOnClickListener(this);
        iv_close.setOnClickListener(this);
        initializeBottomSheet();
        initializeRecyclerView();
        hideEditViews();
        updateCheckBox();
    }

    private void updateCheckBox() {
        try {
            UserBalanceModel userModel = getMyApplication().getUserBalanceModel();
            if (userModel != null) {
                if (userModel.getIs_confirm_bet().equalsIgnoreCase("Y")) {
                    cb_confirm_batting_first.setChecked(true);
                    cb_confirm_batting.setChecked(true);
                } else {
                    cb_confirm_batting_first.setChecked(false);
                    cb_confirm_batting.setChecked(false);
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if (b) {
                int betCount = getBetCount();
                if (betCount > 0) {
                    confirmBetDialog("All bets in your betSlip will be cleared. Is this ok?");
                } else {
                    tv_edit_stacks.setTag(0);
                    try {
                        getMainActivity().startStackUpdater();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                tv_edit_stacks.setTag(null);
                hideEditViews();
            }
        }
    };

    private void confirmBetDialog(String title) {
        ConfirmClearBetDialog confirmationDialog = new ConfirmClearBetDialog(getContext());
        confirmationDialog.setText(title);
        confirmationDialog.setConfirmBetDialogListener(new ConfirmClearBetDialog.ConfirmBetDialogListener() {
            @Override
            public void onClickConfirm(Dialog dialog, View v) {
                switch (v.getId()) {
                    case R.id.tv_ok:
                        tv_edit_stacks.setTag(0);
                        try {
                            getMainActivity().startStackUpdater();
                            clearBets();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                        break;

                    case R.id.tv_cancel:
                        sw_click_status.setChecked(false);
                        break;
                }
                dialog.dismiss();
            }
        });
        confirmationDialog.show();
    }


    public void setupPeakHeightBottomSheet() {
        ll_layout_recy_top.post(new Runnable() {
            @Override
            public void run() {
                int defaultPeakHeight = ll_layout_recy_top.getHeight();
                if (list.size() > 0 && adapter.getView(0) != null) {
                    int item_height = adapter.getView(0).getHeight();
                    int bottom_data_height = ll_layout_first.getHeight();
                    defaultPeakHeight += item_height + bottom_data_height;
                }
                bottomSheetBehavior.setPeekHeight(defaultPeakHeight);
            }
        });

    }

    private void updateBottomSheetView() {
        if (list.size() > 0) {
            if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                updateViewVisibility(ll_layout_first, View.VISIBLE);
                updateViewVisibility(ll_layout, View.GONE);
            } else if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                updateViewVisibility(ll_layout_first, View.GONE);
                updateViewVisibility(ll_layout, View.VISIBLE);
            }
        } else {
            updateViewVisibility(ll_layout_first, View.GONE);
            updateViewVisibility(ll_layout, View.GONE);
        }
    }


    private void initializeBottomSheet() {
        // init the bottom sheet behavior
        bottomSheetBehavior = BottomSheetBehavior.from(bottom_sheet);
        // change the state of the bottom sheet
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        // set callback for changes
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

                updateBottomSheetView();
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
    }

    private void updateTotalLiability(List<BetSlipModel> betSlipModelList, BetSlipModel changedBetSlipModel) {
        if (list == null || list.size() == 0) {
            tv_liability_total.setText(getValidDecimalFormat(0.0));
            updatePlaceBetButton(0);
        } else {
            double totalLiability = 0.0d;
            for (BetSlipModel betSlipModel :
                    list) {
                String liability = betSlipModel.getLiability();
                try {
                    double v = Double.parseDouble(liability);
                    totalLiability += v;
                } catch (NumberFormatException ignore) {

                }
            }
            tv_liability_total.setText(getValidDecimalFormat(totalLiability));
            updatePlaceBetButton(totalLiability);
        }
        if (getActivity() != null) {
            ((MainActivity) getActivity()).onBetDataChange(betSlipModelList, changedBetSlipModel);
        }
    }

    private void updatePlaceBetButton(double totalLialility) {
        if (totalLialility > 0) {
            tv_place_bets.setEnabled(true);
            tv_place_bets_first.setEnabled(true);
        } else {
            tv_place_bets.setEnabled(false);
            tv_place_bets_first.setEnabled(false);
        }
    }

    private void initializeRecyclerView() {
        recycler_view.setLayoutManager(getVerticalLinearLayoutManager());
        adapter = new BetSlipAdapter(getActivity()) {
            @Override
            public void onDataChange(List<BetSlipModel> betSlipModelList, BetSlipModel betSlipModel) {
                updateTotalLiability(betSlipModelList, betSlipModel);
            }
        };
        adapter.update(list);
        recycler_view.setNestedScrollingEnabled(false);
        recycler_view.setAdapter(adapter);

        new ItemClickSupport(recycler_view) {
            @Override
            public void onChildItemClicked(RecyclerView recyclerView, int parentPosition, int childPosition, View v) {

            }
        }.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                try {
                    BetSlipModel betSlipModel = list.get(position);
                    switch (v.getId()) {
                        case R.id.iv_close:
                            list.remove(betSlipModel);
                            adapter.notifyDataSetChanged();
                            adapter.onDataChange(null, betSlipModel);
                            updateNoDataVIew();
                            break;
                    }
                } catch (IndexOutOfBoundsException e) {

                }
            }
        });

        recycler_view.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }

        });
    }

    @Override
    public void onClick(View v) {
        tv_edit_stacks.setTag(null);
        if (v.getId() == R.id.tv_edit) {
            showSaveCancelEtBtns();
            return;
        } else if (v.getId() == R.id.tv_cancel) {
            hideKeyboard();
            hideSaveCancelEtBtns();
            return;
        } else if (v.getId() == R.id.tv_save) {
            et_1.clearFocus();
            et_2.clearFocus();
            et_3.clearFocus();
            onSave();
            return;
        } else if (v.getId() == R.id.tv_edit_stacks) {
            try {
                tv_edit_stacks.setTag(1);
                getMainActivity().startStackUpdater();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            return;
        } else if (v.getId() == R.id.tv_1) {
            setSelectedToEditText(tv_1);
            return;

        } else if (v.getId() == R.id.tv_2) {
            setSelectedToEditText(tv_2);
            return;
        } else if (v.getId() == R.id.tv_3) {
            setSelectedToEditText(tv_3);
            return;

        }
        switch (v.getId()) {
            case R.id.iv_full_screen:
                if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                } else {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
                break;

            case R.id.iv_close:
                try {
                    updateDataOnList();
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    updateViewVisibility(getMainActivity().getBottom_container(), View.GONE);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                break;

            case R.id.tv_remove_all_first:
                tv_remove_all.performClick();
                break;

            case R.id.tv_remove_all:
                clearBets();
                break;

            case R.id.tv_place_bets_first:
                tv_place_bets.performClick();
                break;
            case R.id.tv_place_bets:
                checkIsConfirmBet();
                break;
            case R.id.cb_confirm_batting_first:
                confirmBet();
                break;
            case R.id.cb_confirm_batting:
                confirmBet();
                break;
        }

    }

    private void updateDataOnList() {
        for (BetSlipModel betSlipModel : list) {
            betSlipModel.setCurrentStack("0");
            String rate = betSlipModel.getCurrentOdds();
            String stackAmount = betSlipModel.getCurrentStack();

            if (rate.isEmpty()) {
                rate = "0";
            }
            if (stackAmount.isEmpty()) {
                stackAmount = "0";
            }
            try {
                double rateD = Double.parseDouble(rate);
                double stackAmountD = Double.parseDouble(stackAmount);
                double totalValue = rateD * stackAmountD;
                double amount = totalValue - stackAmountD;
                betSlipModel.setCurrentPL(amount > 0 ? getValidDecimalFormat(amount) : getValidDecimalFormat(0.0));
            } catch (NumberFormatException e) {
                betSlipModel.setCurrentPL(getValidDecimalFormat(0.0));
            }
            betSlipModel.setCurrentOdds(rate);
            betSlipModel.setCurrentStack(stackAmount);
        }
        if (adapter != null) {
            adapter.notifyDataSetChanged();
            adapter.onDataChange(list, null);
        }

    }

    private void checkIsConfirmBet() {
        try {
            UserBalanceModel userBalanceModel = getMyApplication().getUserBalanceModel();
            if (userBalanceModel == null) return;
            if (userBalanceModel.isConfirmBet()) {
                addConfirmBetDialog("Are you sure you want to place your bet?");
            } else {
                placeAllBets();
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void confirmBet() {
        try {
            ConfirmBetRequestModel requestModel = new ConfirmBetRequestModel();
            UserBalanceModel userBalanceModel = getMyApplication().getUserBalanceModel();
            if (userBalanceModel == null) return;
            requestModel.is_confirm_bet = userBalanceModel.isConfirmBet() ? "N" : "Y";
            displayProgressBar(false, getResources().getString(R.string.please_wait));
            getWebRequestHelper().confirmBet(requestModel, this);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    private void editStacksDialog() {
        try {
            StackModel userStack = getMyApplication().getUserStack();
            if (userStack != null) {
                List<String> match_stake = userStack.getMatch_stake();
                if (match_stake == null || match_stake.size() == 0) return;
                getMainActivity().showMatchStackDialog(match_stake);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void onSave() {
        StackRequestModel requestModel = new StackRequestModel();
        List<String> list = new ArrayList<>();
        list.add(et_1.getText().toString().trim());
        list.add(et_2.getText().toString().trim());
        list.add(et_3.getText().toString().trim());
        requestModel.one_click_stake = list;
        displayProgressBar(false);
        getWebRequestHelper().one_click_stake_setting(requestModel, this);
    }


    private void setSelectedToEditText(TextView textView) {
        selectedOneClickStack = textView;
        TextView[] tv_array = {tv_1, tv_2, tv_3};
        for (TextView tv : tv_array) {
            try {
                if (tv == textView) {
                    getMainActivity().setBackgroundResDrawable(getMainActivity(), R.drawable.bg_edit_text_selected, tv);
                } else {
                    getMainActivity().setBackgroundResDrawable(getMainActivity(), R.drawable.bg_edit_text_stroke, tv);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

    }

    public List<BetSlipModel> getMatchOddsBets(String marketId) {
        List<BetSlipModel> data = new ArrayList<>();
        if (list == null || list.size() == 0) return data;
        for (BetSlipModel betSlipModel : list) {
            if (!betSlipModel.isSession() && betSlipModel.getMarketid().equals(marketId)) {
                data.add(betSlipModel);
            }
        }
        return data;
    }

    private void placeAllBets() {
        if (list == null || list.size() == 0) return;
        try {
            UserModel userModel = getMyApplication().getUserModel();
            if (userModel == null) return;
            BetPlaceRequestModel betPlaceRequestModel = new BetPlaceRequestModel();
            for (BetSlipModel betSlipModel : list) {
                MatchMarketModel matchMarketModel = matchMarketModelHashMap.get(betSlipModel.getMarketid());
                if (matchMarketModel == null) return;
                BetSlipRequestModel betSlipRequestModel = betPlaceRequestModel.addBet(betSlipModel);
                betSlipRequestModel.userId = userModel.getUser_id();
                betSlipRequestModel.type = userModel.getType();
                betSlipRequestModel.inplay = matchMarketModel.isInPlay();
                if (betSlipModel.isSession()) {
                    IndianSessionModel indianSessionModel = indianSessionModelHashMap.get(betSlipRequestModel.FancyID);
                    if (indianSessionModel != null) {
                        betSlipRequestModel.TypeID = indianSessionModel.getTypeID();
                        betSlipRequestModel.SessInptNo = indianSessionModel.getSessInptNo();
                        betSlipRequestModel.SessInptYes = indianSessionModel.getSessInptYes();
                        betSlipRequestModel.NoValume = indianSessionModel.getNoValume();
                        betSlipRequestModel.YesValume = indianSessionModel.getYesValume();
                        betSlipRequestModel.pointDiff = indianSessionModel.getPointDiff();
                    }

                } else {
                    RunnersModel runnersModel = new RunnersModel();
                    runnersModel.setSelectionId(betSlipRequestModel.selectionId);
                    int i = matchMarketModel.getRunners().indexOf(runnersModel);
                    if (i >= 0) {
                        runnersModel = matchMarketModel.getRunners().get(i);
                        if (betSlipModel.isBack()) {
                            AvailableToBackModel availableToBackModel = runnersModel.getBackOdds();
                            betSlipRequestModel.isMatched =
                                    availableToBackModel.isMatched(betSlipModel.getFormattedOdds()) ? "1" : "0";
                        } else {
                            AvailableToLayModel availableToLayModel = runnersModel.getLayOdds();
                            betSlipRequestModel.isMatched =
                                    availableToLayModel.isMatched(betSlipModel.getFormattedOdds()) ? "1" : "0";
                        }
                    } else {
                        betSlipRequestModel.isMatched = "0";
                    }
                }

            }

            displayProgressBar(false, getString(R.string.please_wait));
            getWebRequestHelper().save_multiple_bets(betPlaceRequestModel, this);

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    public int getBetCount() {
        return adapter.getDataCount();
    }

    public void clearBets() {
        List<BetSlipModel> data = new ArrayList<>();
        data.addAll(list);
        list.clear();
        matchMarketModelHashMap.clear();
        indianSessionModelHashMap.clear();
        adapter.notifyDataSetChanged();
        adapter.onDataChange(data, null);
        updateNoDataVIew();
    }

    /**
     * @param matchMarketModel
     * @param betSlipModel
     */
    public void updateBetData(MatchMarketModel matchMarketModel,
                              IndianSessionModel indianSessionModel, BetSlipModel betSlipModel) {
        if(BuildConfig.SINGLE_BET_ACTIVE.equals("Y")){
            clearBets();
        }
        int index = list.indexOf(betSlipModel);
        if (index == -1) {
            matchMarketModelHashMap.put(betSlipModel.getMarketid(), matchMarketModel);
            if (indianSessionModel != null) {
                indianSessionModelHashMap.put(indianSessionModel.getID(), indianSessionModel);
            }
            list.add(0, betSlipModel);
            adapter.notifyDataSetChanged();
            adapter.onDataChange(null, null);
            updateNoDataVIew();
            if (sw_click_status.isChecked()) {
                betSlipModel.setCurrentStack(selectedOneClickStack.getText().toString().trim());
                adapter.onDataChange(null, null);
                checkIsConfirmBet();
            }
            return;
        }
        adapter.notifyDataSetChanged();
        adapter.onDataChange(null, null);
        updateNoDataVIew();
    }

    private void updateNoDataVIew() {
        updateBottomSheetView();
        setupPeakHeightBottomSheet();
    }

    @Override
    public void onWebRequestResponse(WebRequest webRequest) {
        dismissProgressBar();
        if (webRequest.getWebRequestId() == ID_SAVE_MULTIPLE_BETS) {
            handleBetPlaceResponse(webRequest);
        } else if (webRequest.getWebRequestId() == ID_ONE_CLICK_STAKE_SETTING) {
            handleOneClickStacktResponse(webRequest);
        } else if (webRequest.getWebRequestId() == ID_CONFIRM_BET) {
            handleBetConfirmResponse(webRequest);
        }
    }

    private void handleBetConfirmResponse(WebRequest webRequest) {
        EmptyResponseModel responseModel = webRequest.getResponsePojo(EmptyResponseModel.class);
        if (responseModel == null) return;
        if (!responseModel.isError()) {
            String message = responseModel.getMessage();
            if (isValidString(message)) {
                confirmBetPlaceDialog(message);
            }
        } else {
            String message = responseModel.getMessage();
            if (isValidString(message)) {
                showErrorMessage(message);
            }
        }
    }

    private void handleBetPlaceResponse(WebRequest webRequest) {
        String responseString = webRequest.getResponseString();
        List<BetPlaceModel> betPlaceModelList = null;
        try {
            betPlaceModelList = new Gson().fromJson(responseString, new TypeToken<List<BetPlaceModel>>() {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (betPlaceModelList == null) return;
        String message = "";
        for (BetPlaceModel betPlaceModel : betPlaceModelList) {
            for (BetSlipModel betSlipModel : list) {
                message = betPlaceModel.getResult().getMessage();
                if (betPlaceModel.getUnique_id().equals(betSlipModel.getUniqeId())) {
                    if (betPlaceModel.getResult().isError()) {
                        betSlipModel.setErrorMsg(betPlaceModel.getResult().getMessage());
                    } else {
                        list.remove(betSlipModel);
//                        adapter.onDataChange(null, betSlipModel);
                        confirmBetPlaceDialog(message);
                    }
                    break;
                }
            }
        }
        adapter.notifyDataSetChanged();
        updateNoDataVIew();

        if (list.size() == 0) {
            try {
                if (sw_click_status.isChecked()) {
//                    confirmBetPlaceDialog("Bet placed successfully.");
                    updateViewVisibility(getMainActivity().getBottom_container(), View.GONE);
                } else {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public void confirmBetPlaceDialog(String title) {
        try {
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
                    try {
                        dialog.dismiss();
                    } catch (Exception ignore) {

                    }
                }
            });
            confirmationDialog.show();
        } catch (Exception ignore) {

        }
    }

    public void addConfirmBetDialog(String title) {
        ConfirmBetDialog dialog = new ConfirmBetDialog(getContext());
        dialog.setTextTitle(title);
        dialog.setConfirmBetDialogListener(new ConfirmBetDialog.ConfirmBetDialogListener() {
            @Override
            public void onClickConfirm(Dialog dialog, View v) {
                switch (v.getId()) {
                    case R.id.tv_ok:
                        placeAllBets();
                        break;
                    case R.id.tv_cancel:
                        break;
                }
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    private void handleOneClickStacktResponse(WebRequest webRequest) {
        WebServiceBaseResponseModel baseResponsePojo = webRequest.getBaseResponsePojo();
        StackRequestModel requestModel = webRequest.getExtraData(KEY_ONE_CLICK_STAKE_SETTING);
        if (requestModel != null && baseResponsePojo != null) {
            if (!baseResponsePojo.isError()) {
                try {
                    StackModel userStack = getMyApplication().getUserStack();
                    if (userStack != null) {
                        userStack.setOne_click_stake(requestModel.one_click_stake);
                        getMyApplication().updateUserStack(userStack);
                        if (userStack.getOne_click_stake() != null && userStack.getOne_click_stake().size() > 0) {
                            tv_1.setText(userStack.getOne_click_stake().get(0));
                            tv_2.setText(userStack.getOne_click_stake().get(1));
                            tv_3.setText(userStack.getOne_click_stake().get(2));
                        }
                        if (adapter != null)
                            adapter.notifyDataSetChanged();

                        hideSaveCancelEtBtns();
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                showCustomToast(baseResponsePojo.getMessage());
//                getBetSlipFragment().hideSaveCancelEtBtns();
//                getRightSideMenuHelper().hideSaveCancelEtBtns();
            }

        }
    }

    @Override
    public void userLoggedInStackUpdate(StackModel stackModel) {
        try {
            getMainActivity().stopStackUpdater();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        if (stackModel == null) return;

        Integer tag = (Integer) tv_edit_stacks.getTag();
        if (tag == null) return;
        if (tag.intValue() == 0) {
            List<String> one_click_stake = stackModel.getOne_click_stake();
            if (one_click_stake == null || one_click_stake.size() == 0) return;
            EditText[] et_array = {et_1, et_2, et_3};
            TextView[] tv_array = {tv_1, tv_2, tv_3};
            et_1.setText("");
            et_2.setText("");
            et_3.setText("");
            for (int i = 0; i < one_click_stake.size(); i++) {
                if (et_array.length == i) {
                    break;
                }
                et_array[i].setText(stackModel.getValidString(one_click_stake.get(i)));
                tv_array[i].setText(stackModel.getValidString(one_click_stake.get(i)));
            }
            showEditViews();
        } else if (tag.intValue() == 1) {
            editStacksDialog();
        }

        tv_edit_stacks.setTag(null);
        if (adapter != null)
            adapter.notifyDataSetChanged();
    }

    private void showEditViews() {
        updateViewVisibility(ll_one_click_betting, View.VISIBLE);
//        updateViewVisibility(ll_header_text, View.GONE);
        updateViewVisibility(ll_edit_views, View.GONE);
        updateViewVisibility(rl_view, View.GONE);
        updateViewVisibility(ll_text_views, View.VISIBLE);
        setupPeakHeightBottomSheet();
    }

    private void showSaveCancelEtBtns() {
        updateViewVisibility(ll_one_click_betting, View.VISIBLE);
        updateViewVisibility(ll_text_views, View.GONE);
        updateViewVisibility(ll_edit_views, View.VISIBLE);
        updateViewVisibility(rl_view, View.VISIBLE);
        setupPeakHeightBottomSheet();
    }

    private void hideEditViews() {
        sw_click_status.setChecked(false);
        updateViewVisibility(ll_one_click_betting, View.GONE);
        setupPeakHeightBottomSheet();
//        updateViewVisibility(ll_header_text, View.VISIBLE);
    }

    public void hideSaveCancelEtBtns() {
        updateViewVisibility(ll_one_click_betting, View.VISIBLE);
        updateViewVisibility(ll_edit_views, View.GONE);
        updateViewVisibility(ll_text_views, View.VISIBLE);
        updateViewVisibility(rl_view, View.GONE);
        setupPeakHeightBottomSheet();
    }

    public interface BetSlipDataListener {
        void onBetDataChange(List<BetSlipModel> betSlipModelList, BetSlipModel betSlipModel);
    }
}