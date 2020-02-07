package com.lotus.ui.helpers;

import android.app.Dialog;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.drawerlayout.widget.DrawerLayout;

import com.base.BaseFragment;
import com.lotus.R;
import com.lotus.model.StackModel;
import com.lotus.model.UserBalanceModel;
import com.lotus.model.UserModel;
import com.lotus.preferences.UserPrefs;
import com.lotus.ui.login.LoginActivity;
import com.lotus.ui.main.MainActivity;
import com.lotus.ui.main.dialog.LogoutDialog;
import com.lotus.ui.main.rightsidemenu.changepassword.ChangePasswordFragment;
import com.lotus.ui.main.rightsidemenu.mybets.MyBetsFragment;
import com.lotus.ui.main.rightsidemenu.transferstatement.TransferStatementFragment;


/**
 * Created by Sunil kumar yadav on 27/2/18.
 */

public class RightSideMenuHelper implements View.OnClickListener, UserPrefs.UserBalanceListener,
        UserPrefs.UserStackListener {

    MainActivity mainActivity;
    DrawerLayout drawer_layout;
//    NavigationView navigation_view_right;

    TextView tv_user_name;
    TextView tv_available_credit;
    TextView tv_credit_limit;
    TextView tv_winning_amount;
    TextView tv_net_exposure;

    private LinearLayout ll_my_bets;
    private TextView tv_my_bets;
    private LinearLayout ll_betting_p_l;
    private TextView tv_betting_p_l;
    private LinearLayout ll_transfer_statement;
    private TextView tv_transfer_statement;
    private LinearLayout ll_change_password;
    private TextView tv_change_password;
    private LinearLayout ll_sign_out;
    private TextView tv_sign_out;
    private LinearLayout ll_account_statement;
    private TextView tv_account_statement;


    public RightSideMenuHelper(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        initializeComponent();
    }

    private void initializeComponent() {
        initView();
        initSetUserData();
        drawer_layout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View view, float v) {

            }

            @Override
            public void onDrawerOpened(View view) {

            }

            @Override
            public void onDrawerClosed(View view) {

            }

            @Override
            public void onDrawerStateChanged(int i) {

            }
        });


    }


    private void initView() {
        drawer_layout = mainActivity.findViewById(R.id.drawer_layout);
//        navigation_view_right = mainActivity.findViewById(R.id.navigation_view_right);
        tv_user_name = mainActivity.findViewById(R.id.tv_user_name);
        tv_available_credit = mainActivity.findViewById(R.id.tv_available_credit);
        tv_credit_limit = mainActivity.findViewById(R.id.tv_credit_limit);
        tv_winning_amount = mainActivity.findViewById(R.id.tv_winning_amount);
        tv_net_exposure = mainActivity.findViewById(R.id.tv_net_exposure);

        ll_my_bets = mainActivity.findViewById(R.id.ll_my_bets);
        tv_my_bets = mainActivity.findViewById(R.id.tv_my_bets);
        ll_betting_p_l = mainActivity.findViewById(R.id.ll_betting_p_l);
        tv_betting_p_l = mainActivity.findViewById(R.id.tv_betting_p_l);
        ll_transfer_statement = mainActivity.findViewById(R.id.ll_transfer_statement);
        tv_transfer_statement = mainActivity.findViewById(R.id.tv_transfer_statement);
        ll_change_password = mainActivity.findViewById(R.id.ll_change_password);
        tv_change_password = mainActivity.findViewById(R.id.tv_change_password);
        ll_sign_out = mainActivity.findViewById(R.id.ll_sign_out);
        tv_sign_out = mainActivity.findViewById(R.id.tv_sign_out);
        ll_account_statement = mainActivity.findViewById(R.id.ll_account_statement);
        tv_account_statement = mainActivity.findViewById(R.id.tv_account_statement);


        ll_my_bets.setOnClickListener(this);
        ll_betting_p_l.setOnClickListener(this);
        ll_transfer_statement.setOnClickListener(this);
        ll_change_password.setOnClickListener(this);
        ll_sign_out.setOnClickListener(this);
        ll_account_statement.setOnClickListener(this);

    }

    public void initSetUserData() {
        UserModel userModel = mainActivity.getMyApplication().getUserModel();
        if (userModel != null) {
            tv_user_name.setText(userModel.getUser_name());
            userLoggedInBalanceUpdate(mainActivity.getMyApplication().getUserBalanceModel());
        }
    }

    public void handleDrawer() {
        if (!closeDrawer()) {
            // drawer_layout.openDrawer(navigation_view_right);
        }
    }

    public boolean closeDrawer() {
//        if (drawer_layout.isDrawerOpen(navigation_view_right)) {
//            drawer_layout.closeDrawers();
//            return true;
//        }
        return false;
    }

    public void drawerLock(boolean value) {
        int drawerMode = value ? DrawerLayout.LOCK_MODE_UNLOCKED
                : DrawerLayout.LOCK_MODE_LOCKED_CLOSED;
        drawer_layout.setDrawerLockMode(drawerMode);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_my_bets:
                addMyBetsFragment(0);
                break;
            case R.id.ll_betting_p_l:
                addMyBetsFragment(1);
                break;
            case R.id.ll_account_statement:
                addMyBetsFragment(2);
                break;
            case R.id.ll_transfer_statement:
                addTransferStatementFragment();
                break;
            case R.id.ll_change_password:
                addChangePasswordFragment();
                break;

            case R.id.ll_sign_out:
                logout();
                break;
        }
        closeDrawer();
    }


    public void addMyBetsFragment(int type) {
        MyBetsFragment fragment = new MyBetsFragment();
        fragment.setType(type);
        int enterAnimation = R.anim.right_in;
        int exitAnimation = 0;
        int enterAnimationBackStack = 0;
        int exitAnimationBackStack = R.anim.left_out;
        mainActivity.changeFragment(fragment, true, true, 0,
                enterAnimation, exitAnimation, enterAnimationBackStack, exitAnimationBackStack, false);
    }

    public void addTransferStatementFragment() {
        BaseFragment latestFragment = mainActivity.getLatestFragment();
        if (latestFragment != null && latestFragment instanceof TransferStatementFragment) return;

        TransferStatementFragment fragment = new TransferStatementFragment();
        int enterAnimation = R.anim.right_in;
        int exitAnimation = 0;
        int enterAnimationBackStack = 0;
        int exitAnimationBackStack = R.anim.left_out;
        mainActivity.changeFragment(fragment, true, true, 0,
                enterAnimation, exitAnimation, enterAnimationBackStack, exitAnimationBackStack, false);
    }

    public void addChangePasswordFragment() {
        BaseFragment latestFragment = mainActivity.getLatestFragment();
        if (latestFragment != null && latestFragment instanceof ChangePasswordFragment) return;

        ChangePasswordFragment fragment = new ChangePasswordFragment();
        int enterAnimation = R.anim.right_in;
        int exitAnimation = 0;
        int enterAnimationBackStack = 0;
        int exitAnimationBackStack = R.anim.left_out;
        mainActivity.changeFragment(fragment, true, true, 0,
                enterAnimation, exitAnimation, enterAnimationBackStack, exitAnimationBackStack, false);
    }

    @Override
    public void userLoggedInBalanceUpdate(UserBalanceModel userBalanceModel) {
        if (userBalanceModel != null) {
            tv_available_credit.setText(userBalanceModel.getBalanceText());
            tv_credit_limit.setText(userBalanceModel.getBalanceText());
            if (userBalanceModel.getP_L() > 0)
                tv_winning_amount.setTextColor(mainActivity.getResources().getColor(R.color.color_green));
            else
                tv_winning_amount.setTextColor(mainActivity.getResources().getColor(R.color.color_red));
            tv_winning_amount.setText(userBalanceModel.getP_LText());
            if (userBalanceModel.getLiability() > 0)
                tv_net_exposure.setTextColor(mainActivity.getResources().getColor(R.color.color_green));
            else
                tv_net_exposure.setTextColor(mainActivity.getResources().getColor(R.color.color_red));
            tv_net_exposure.setText(userBalanceModel.getLiabilityText());
            mainActivity.getToolBarHelper().setTv_credit(userBalanceModel.getBalanceText());
        }
    }

    @Override
    public void userLoggedInStackUpdate(StackModel stackModel) {
        mainActivity.stopStackUpdater();
        if (stackModel == null) return;

    }


    private void logout() {
        LogoutDialog confirmationDialog = new LogoutDialog(mainActivity);
        confirmationDialog.setConfirmationDialogListener(new LogoutDialog.ConfirmationDialogListener() {
            @Override
            public void onClickConfirm(Dialog dialog) {
                dialog.dismiss();
                mainActivity.getMyApplication().getUserPrefs().clearLoggedInUser();
                mainActivity.sendActivityFinish(mainActivity, LoginActivity.class);
            }
        });
        confirmationDialog.show();
    }

}
