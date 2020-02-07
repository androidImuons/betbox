package com.lotus.ui.main;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.base.BaseFragment;
import com.google.gson.Gson;
import com.lotus.R;
import com.lotus.appBase.AppBaseActivity;
import com.lotus.appupdater.AppUpdateChecker;
import com.lotus.appupdater.AppUpdatemodal;
import com.lotus.model.BetSlipModel;
import com.lotus.model.LoggedInUserModel;
import com.lotus.model.StackModel;
import com.lotus.model.UserBalanceModel;
import com.lotus.model.UserModel;
import com.lotus.model.request_model.StackRequestModel;
import com.lotus.preferences.UserPrefs;
import com.lotus.rest.WebRequestHelper;
import com.lotus.ui.helpers.LeftSideMenuHelper;
import com.lotus.ui.helpers.ToolBarHelper;
import com.lotus.ui.login.LoginActivity;
import com.lotus.ui.main.betSlip.BetSlipFragment;
import com.lotus.ui.main.dashboard.DashboardFragment;
import com.lotus.ui.main.dialog.stackdialog.StackDialog;
import com.lotus.ui.main.leftsidemenu.favourite.FavouriteFragment;
import com.lotus.ui.main.matchDetail.MatchDetailFragment;
import com.lotus.ui.main.rightsidemenu.changepassword.ChangePasswordFragment;
import com.lotus.utilites.CheckLoggedInUserHelper;
import com.lotus.utilites.HeadLineHelper;
import com.lotus.utilites.StackHelper;
import com.lotus.utilites.UserBalanceHelper;
import com.medy.retrofitwrapper.WebRequest;
import com.medy.retrofitwrapper.WebServiceBaseResponseModel;
import com.models.DeviceScreenModel;

import java.util.List;

import io.codetail.animation.ViewAnimationUtils;

public class MainActivity extends AppBaseActivity implements UserPrefs.UserPrefsListener,
        UserPrefs.UserBalanceListener, CheckLoggedInUserHelper.CheckLoggedInUserHelperListener,
        UserPrefs.UserStackListener, BetSlipFragment.BetSlipDataListener {

    public LeftSideMenuHelper leftSideMenuHelper;
    //    public RightSideMenuHelper rightSideMenuHelper;
    public ToolBarHelper toolBarHelper;
    private RelativeLayout rl_app_main_lay;
    private FrameLayout bottom_container;
    private ImageView iv_betSlip;
    View termConditionView;
    DashboardFragment dashboardFragment;
    UserBalanceHelper userDataHelper;
    CheckLoggedInUserHelper checkLoggedInUserHelper;
    HeadLineHelper headLineHelper;
    TextView tv_headline;
    String previousHeadlineText = "";
    private StackHelper stackHelper;
    private BetSlipFragment betSlipFragment;

    private FragmentManager.OnBackStackChangedListener onBackStackChangedListener = new FragmentManager.OnBackStackChangedListener() {
        @Override
        public void onBackStackChanged() {
            backStackHandler.removeMessages(1);
            backStackHandler.sendEmptyMessageDelayed(1, 100);
        }
    };
    private Handler backStackHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    BaseFragment latestFragment = getLatestFragment();
                    if (latestFragment != null) {
                        toolBarHelper.onCreateViewFragment(latestFragment);
                        latestFragment.viewCreateFromBackStack();
                    }

                    if (getFm().getBackStackEntryCount() > 0) {
                        if (dashboardFragment != null)
                            dashboardFragment.setUserVisibleHint(false);
                    } else {
                        if (dashboardFragment != null)
                            dashboardFragment.setUserVisibleHint(true);
                    }
                    break;
            }
        }
    };

    public DashboardFragment getDashboardFragment() {
        return dashboardFragment;
    }

    public FavouriteFragment getFavouriteFragment() {
        BaseFragment latestFragment = getLatestFragment();
        if (latestFragment != null && latestFragment instanceof FavouriteFragment) {
            return (FavouriteFragment) latestFragment;
        }
        return null;
    }

    public StackHelper getStackHelper() {
        return stackHelper;
    }

    public LeftSideMenuHelper getLeftSideMenuHelper() {
        return leftSideMenuHelper;
    }


//    public RightSideMenuHelper getRightSideMenuHelper() {
//        return rightSideMenuHelper;
//    }

    public ToolBarHelper getToolBarHelper() {
        return toolBarHelper;
    }

    public BetSlipFragment getBetSlipFragment() {
        return betSlipFragment;
    }

    public FrameLayout getBottom_container() {
        return bottom_container;
    }

    @Override
    public int getFragmentContainerResourceId() {
        return R.id.main_container;
    }

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_main;
    }


    @Override
    protected void onDestroy() {
        getMyApplication().getUserPrefs().removeListener(this);
        getMyApplication().getUserPrefs().removeBalanceListener(this);
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (leftSideMenuHelper.closeDrawer()) {
            return;
        }
//        if (rightSideMenuHelper.closeDrawer()) {
//            return;
//        }
        super.onBackPressed();
    }

    @Override
    public void initializeComponent() {
        super.initializeComponent();

        getFm().addOnBackStackChangedListener(onBackStackChangedListener);
        tv_headline = findViewById(R.id.tv_headline);
        rl_app_main_lay = findViewById(R.id.rl_app_main_lay);
        bottom_container = findViewById(R.id.bottom_container);
        iv_betSlip = findViewById(R.id.iv_betSlip);
        updateViewVisibility(iv_betSlip,View.INVISIBLE);
        userDataHelper = UserBalanceHelper.getNewInstances(this);
        stackHelper = StackHelper.getInstance(this);
        headLineHelper = HeadLineHelper.getNewInstances(this);
        checkLoggedInUserHelper = CheckLoggedInUserHelper.getNewInstances(this);
        checkLoggedInUserHelper.setCheckLoggedInUserHelperListener(this);

//        iv_betSlip.setOnClickListener(this);
        initializeModel();
        checkUserLogin();

        userLoggedInBalanceUpdate(getMyApplication().getUserBalanceModel());

        checkAppUpdate();

    }


    private void initializeModel() {
        toolBarHelper = new ToolBarHelper(this);
        leftSideMenuHelper = new LeftSideMenuHelper(this);
        //rightSideMenuHelper = new RightSideMenuHelper(this);

        getMyApplication().getUserPrefs().addListener(this);
        getMyApplication().getUserPrefs().addBalanceListener(this);
        getMyApplication().getUserPrefs().addStackListener(this);

    }

    private void checkUserLogin() {
        UserModel userModel = getMyApplication().getUserModel();
        if (userModel != null) {
            loadHomeScreen();
        }
    }


    private void loadHomeScreen() {
        if (!getMyApplication().getAppPrefs().getTermsConditions().contains(String.valueOf(getMyApplication().getUserModel().getUser_id()))) {
            loadTermsConditions();
            return;
        }
//        if (!getMyApplication().getUserModel().isTerms_conditions_accepted()) {
//            loadTermsConditions();
//            return;
//        }
//        if (getMyApplication().getUserModel().getChangePas() != 1) {
//            showChangePasswordFragment();
//            return;
//        }
        showDashboardFragment();
        addBetSlipFragment();
        startStackUpdater();

    }

    private void showDashboardFragment() {
        dashboardFragment = new DashboardFragment();
        changeFragment(dashboardFragment, false, true, 0,
                false);
        onBackStackChangedListener.onBackStackChanged();
    }

    private void showChangePasswordFragment() {
        ChangePasswordFragment fragment = new ChangePasswordFragment();
        fragment.autoOpen = true;
        fragment.setChangePasswordListener(new ChangePasswordFragment.ChangePasswordListener() {
            @Override
            public void passwordChangeSuccessfully() {
                loadHomeScreen();
            }
        });
        changeFragment(fragment, false, true, 0,
                false);
        onBackStackChangedListener.onBackStackChanged();

    }

    private void loadTermsConditions() {
        termConditionView = LayoutInflater.from(this).inflate(R.layout.dialog_term_conditions, null);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        rl_app_main_lay.addView(termConditionView, layoutParams);

        WebView wv_terms_conditions = termConditionView.findViewById(R.id.wv_terms_conditions);
        wv_terms_conditions.setBackgroundColor(0x00000000);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // chromium, enable hardware acceleration
            wv_terms_conditions.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else {
            // older android version, disable hardware acceleration
            wv_terms_conditions.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

        wv_terms_conditions.getSettings().setJavaScriptEnabled(true);
        wv_terms_conditions.getSettings().setDomStorageEnabled(true);
        wv_terms_conditions.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        wv_terms_conditions.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);

        wv_terms_conditions.loadDataWithBaseURL("",
                generateFormattedData(getMyApplication().getUserModel().getTerms_conditions()),
                "text/html", "UTF-8", "");


        TextView tv_accept = termConditionView.findViewById(R.id.tv_accept);
        tv_accept.setOnClickListener(this);
    }

    private String generateFormattedData(String data) {
        if (isValidString(data)) {
            return "<font color=black>" + data + "</font>";
        }
        return "";
    }

    private void playReveal() {
        RelativeLayout rl_main_lay = termConditionView.findViewById(R.id.rl_main_lay);

        Rect deviceRect = DeviceScreenModel.getInstance().getDeviceRect();

        int centerX = Math.round(deviceRect.exactCenterX());
        int centerY = Math.round(deviceRect.height() * 0.9f);
        float startRadius = deviceRect.height() * 0.5f;
        float endRadius = 0.0f;

        Animator animator =
                ViewAnimationUtils.createCircularReveal(rl_main_lay, centerX,
                        centerY, startRadius, endRadius);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.setDuration(500);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                rl_app_main_lay.removeView(termConditionView);
                loadHomeScreen();
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                getMyApplication().getUserModel().setTerms_conditions_accepted(true);
                getMyApplication().updateUserProfile(getMyApplication().getUserModel());

            }
        });
        animator.start();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_accept:
                String termsConditions = getMyApplication().getAppPrefs().getTermsConditions();
                StringBuilder builder = new StringBuilder();
                builder.append(termsConditions);
                if (builder.length() == 0)
                    builder.append(String.valueOf(getMyApplication().getUserModel().getUser_id()));
                else
                    builder.append(",").append(String.valueOf(getMyApplication().getUserModel().getUser_id()));
                getMyApplication().getAppPrefs().saveTermsConditions(builder.toString());
                playReveal();
                break;

            case R.id.iv_betSlip:
//                if (bottom_container.getVisibility() == View.GONE)
                updateViewVisibility(bottom_container, View.VISIBLE);
                if (betSlipFragment != null)
                    betSlipFragment.setupPeakHeightBottomSheet();
                break;
        }
    }

    @Override
    public void userLoggedIn(UserModel userModel) {

    }

    @Override
    public void loggedInUserUpdate(UserModel userModel) {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void loggedInUserClear() {
        sendActivityFinish(this, LoginActivity.class);
    }

    private void checkAppUpdate() {
        AppUpdateChecker appUpdateChecker = new AppUpdateChecker(this, new AppUpdateChecker.OnAppUpdateAvaliable() {
            @Override
            public void appUpdateAvaliable(AppUpdatemodal appUpdatemodal) {
                if (appUpdatemodal == null) return;
                if (!isFinishing()) {
                    Bundle bundle = new Bundle();
                    bundle.putString(CONSTANT_APP_UPDATE, new Gson().toJson(appUpdatemodal));
                    getMyApplication().clearLoggedInUser();
                    sendActivityFinish(getApplicationContext(), LoginActivity.class, bundle);
                    overridePendingTransition(R.anim.right_out, R.anim.right_in);
                }
            }
        });
        appUpdateChecker.checkAppUpdate();
    }

    @Override
    public void userLoggedInBalanceUpdate(UserBalanceModel userBalanceModel) {
        setupHeadline();
        getLeftSideMenuHelper().userLoggedInBalanceUpdate(userBalanceModel);
    }

    private void setupHeadline() {
        if (getMyApplication().getUserBalanceModel() != null) {
            UserBalanceModel userBalanceModel = getMyApplication().getUserBalanceModel();
            String headlines = userBalanceModel.getHeadlines();
            if (!previousHeadlineText.equals(headlines)) {
                previousHeadlineText = headlines;
                enableHeadline();
            }
        }
    }

    private void enableHeadline() {
        if (isValidString(previousHeadlineText)) {
            tv_headline.setText(previousHeadlineText);
            tv_headline.setSelected(true);
            updateViewVisibility(tv_headline, View.VISIBLE);

        } else {
            updateViewVisibility(tv_headline, View.GONE);
        }
    }

    @Override
    protected void onResume() {
        checkLoggedInUserHelper.startCheckLoggedInUserHelper();
        userDataHelper.startUserDataHelperUpdater();
        headLineHelper.startHeadLineHelperUpdater();


        super.onResume();
    }

    public void startStackUpdater() {
        stackHelper.startStackUpdater();

    }

    public void stopStackUpdater() {
        stackHelper.stopStackUpdater();

    }

    @Override
    protected void onPause() {
        checkLoggedInUserHelper.stopCheckLoggedInUserHelper();
        userDataHelper.stopUserDataUpdater();
        headLineHelper.stopHeadLineUpdater();
        super.onPause();
    }


    @Override
    public void onLoggedInUserUpdate(LoggedInUserModel loggedInUserModel) {
        if (loggedInUserModel != null) {
            if (!loggedInUserModel.isIs_login()) {
                getMyApplication().clearLoggedInUser();
                sendActivityFinish(getApplicationContext(), LoginActivity.class);
                overridePendingTransition(R.anim.right_out, R.anim.right_in);
            } else {
                UserModel userModel = getMyApplication().getUserModel();
                if (userModel != null && loggedInUserModel.getData() != null) {
                    if (userModel.isUnmatched() != loggedInUserModel.getData().isUnmatched()) {
                        userModel.setConfig_unmatched(loggedInUserModel.getData().getConfig_unmatched());
                        userModel.setIsMultiBet(loggedInUserModel.getData().getIsMultiBet());
                        getMyApplication().updateUserProfile(getMyApplication().getUserModel());
                    } else if (userModel.isMultiBet() != loggedInUserModel.getData().isMultiBet()) {
                        userModel.setConfig_unmatched(loggedInUserModel.getData().getConfig_unmatched());
                        userModel.setIsMultiBet(loggedInUserModel.getData().getIsMultiBet());
                        getMyApplication().updateUserProfile(getMyApplication().getUserModel());
                    }

                }
            }
        }
    }


    @Override
    public void userLoggedInStackUpdate(StackModel stackModel) {
        if (getBetSlipFragment() != null) {
            getBetSlipFragment().userLoggedInStackUpdate(stackModel);
            if (getBetSlipFragment().getAdapter() != null)
                getBetSlipFragment().getAdapter().notifyDataSetChanged();
        }

    }

    public void saveOneClickStakeSetting(StackRequestModel requestModel) {
        displayProgressBar(false);
        getWebRequestHelper().one_click_stake_setting(requestModel, this);
    }


    @Override
    public void onWebRequestResponse(WebRequest webRequest) {
        super.onWebRequestResponse(webRequest);
        dismissProgressBar();
        if (webRequest.checkSuccess()) {
            switch (webRequest.getWebRequestId()) {
                case WebRequestHelper.ID_ONE_CLICK_STAKE_SETTING:
                    handleOneClickStacktResponse(webRequest);
                    break;
            }
        } else {
            String msg = webRequest.getErrorMessageFromResponse();
            if (msg != null && !msg.isEmpty()) {
                webRequest.showInvalidResponse(msg);
            }
        }
    }

    private void handleOneClickStacktResponse(WebRequest webRequest) {
        WebServiceBaseResponseModel baseResponsePojo = webRequest.getBaseResponsePojo();
        StackRequestModel requestModel = webRequest.getExtraData(KEY_ONE_CLICK_STAKE_SETTING);
        if (requestModel != null && baseResponsePojo != null) {
            if (!baseResponsePojo.isError()) {
                StackModel userStack = getMyApplication().getUserStack();
                if (userStack != null) {
                    userStack.setOne_click_stake(requestModel.one_click_stake);
                    getMyApplication().updateUserStack(userStack);
                }
                showCustomToast(baseResponsePojo.getMessage());
                getBetSlipFragment().hideSaveCancelEtBtns();
//                getRightSideMenuHelper().hideSaveCancelEtBtns();
            }

        }
    }

    public void showMatchStackDialog(List<String> match_stake) {
        if (match_stake == null || match_stake.size() == 0) return;
        final StackDialog dialog = new StackDialog();
        dialog.setTitle("MATCH STACK");
        dialog.setList(match_stake);
        dialog.show(getFm(), StackDialog.class.getSimpleName());
    }

    public void addBetSlipFragment() {
        betSlipFragment = new BetSlipFragment();
        int enterAnimation = R.anim.enterfrombottom;
        int exitAnimation = 0;
        int enterAnimationBackStack = 0;
        int exitAnimationBackStack = R.anim.exittobottom;
        FragmentTransaction ft = getNewFragmentTransaction();
        ft.add(R.id.bottom_container, betSlipFragment, betSlipFragment.getClass().getSimpleName());
        ft.setCustomAnimations(enterAnimation, exitAnimation, enterAnimationBackStack, exitAnimationBackStack);
        ft.commit();
    }


    @Override
    public void onBetDataChange(List<BetSlipModel> betSlipModelList, BetSlipModel betSlipModel) {
        BaseFragment latestFragment = getLatestFragment();
        if (latestFragment != null) {
            if (latestFragment instanceof DashboardFragment) {
                ((DashboardFragment) latestFragment).onBetDataChange(betSlipModelList, betSlipModel);
            } else if (latestFragment instanceof FavouriteFragment) {
                ((FavouriteFragment) latestFragment).onBetDataChange(betSlipModelList, betSlipModel);
            } else if (latestFragment instanceof MatchDetailFragment) {
                ((MatchDetailFragment) latestFragment).onBetDataChange(betSlipModelList, betSlipModel);
            }
        }
    }
}
