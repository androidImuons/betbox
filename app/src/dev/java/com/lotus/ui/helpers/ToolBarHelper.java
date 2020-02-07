package com.lotus.ui.helpers;

import android.content.res.Resources;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.BaseFragment;
import com.lotus.R;
import com.lotus.ui.main.MainActivity;
import com.lotus.ui.main.dashboard.DashboardFragment;
import com.lotus.ui.main.dialog.search.SearchDialog;
import com.lotus.ui.main.leftsidemenu.cricket.CricketFragment;
import com.lotus.ui.main.leftsidemenu.horseracing.HorseRacingFragment;
import com.lotus.ui.main.rightsidemenu.changepassword.ChangePasswordFragment;
import com.lotus.ui.main.rightsidemenu.mybets.MyBetsFragment;
import com.lotus.ui.main.rightsidemenu.transferstatement.TransferStatementFragment;

/**
 * Created by Sunil kumar yadav on 27/2/18.
 */

public class ToolBarHelper implements View.OnClickListener {

    MainActivity mainActivity;
    RelativeLayout rl_tool_bar;
    ImageView iv_menu_left;
    ImageView iv_back;
    ImageView iv_logo;
    TextView tv_title;
    ImageView iv_search;
    LinearLayout ll_right_menu;
    private ImageView iv_user_icon;
    private TextView tv_credit;


    public void setTv_credit(String value) {
        this.tv_credit.setText(value);
    }

    public ToolBarHelper(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        initializeComponent();
    }

    private void initializeComponent() {
        rl_tool_bar = mainActivity.findViewById(R.id.rl_tool_bar);
        iv_menu_left = mainActivity.findViewById(R.id.iv_menu_left);
        iv_back = mainActivity.findViewById(R.id.iv_back);
        iv_logo = mainActivity.findViewById(R.id.iv_logo);
        tv_title = mainActivity.findViewById(R.id.tv_title);
        updateViewVisibility(tv_title, View.GONE);
        iv_search = mainActivity.findViewById(R.id.iv_search);
        ll_right_menu = mainActivity.findViewById(R.id.ll_right_menu);
        iv_user_icon = mainActivity.findViewById(R.id.iv_user_icon);
        tv_credit = mainActivity.findViewById(R.id.tv_credit);


        iv_back.setVisibility(View.GONE);


        iv_logo.setOnClickListener(this);
        iv_menu_left.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        iv_search.setOnClickListener(this);
        ll_right_menu.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_logo:
                mainActivity.changeFragment(null, false, true, 0,
                        0, 0, 0, 0, false);
                break;
            case R.id.iv_menu_left:
                mainActivity.getLeftSideMenuHelper().handleDrawer();
                break;
            case R.id.iv_back:
                mainActivity.onBackPressed();
                break;

            case R.id.iv_search:
                showSearchDialog();
//                if (mainActivity.getLatestFragment() != null)
//                    mainActivity.getLatestFragment().onClick(v);
                break;

            case R.id.ll_right_menu:
               // mainActivity.getRightSideMenuHelper().handleDrawer();
                break;
        }
    }

    private void showSearchDialog() {
        SearchDialog dialog = new SearchDialog();
        dialog.show(mainActivity.getFm(), SearchDialog.class.getSimpleName());

    }

    private Resources getResources() {
        return mainActivity.getResources();
    }

    public void updateViewVisibility(View view, int visibility) {
        if (view != null && view.getVisibility() != visibility)
            view.setVisibility(visibility);
    }

    public void onCreateViewFragment(BaseFragment baseFragment) {
        mainActivity.hideKeyboard();

        if (baseFragment instanceof DashboardFragment) {
            updateViewVisibility(iv_menu_left, View.VISIBLE);
            updateViewVisibility(iv_back, View.GONE);
            updateViewVisibility(iv_logo, View.VISIBLE);
            updateViewVisibility(tv_title, View.GONE);
            updateViewVisibility(iv_search, View.VISIBLE);
            updateViewVisibility(ll_right_menu, View.VISIBLE);
        } else if (baseFragment instanceof CricketFragment) {
            updateViewVisibility(iv_menu_left, View.VISIBLE);
            updateViewVisibility(iv_back, View.GONE);
            updateViewVisibility(iv_logo, View.VISIBLE);
            updateViewVisibility(tv_title, View.GONE);
            updateViewVisibility(iv_search, View.VISIBLE);
            updateViewVisibility(ll_right_menu, View.VISIBLE);
        } else if (baseFragment instanceof HorseRacingFragment) {
            updateViewVisibility(iv_menu_left, View.VISIBLE);
            updateViewVisibility(iv_back, View.GONE);
            updateViewVisibility(iv_logo, View.VISIBLE);
            updateViewVisibility(tv_title, View.GONE);
            updateViewVisibility(iv_search, View.VISIBLE);
            updateViewVisibility(ll_right_menu, View.VISIBLE);
        } else if (baseFragment instanceof TransferStatementFragment) {
            updateViewVisibility(iv_menu_left, View.VISIBLE);
            updateViewVisibility(iv_back, View.GONE);
            updateViewVisibility(iv_logo, View.VISIBLE);
            updateViewVisibility(tv_title, View.GONE);
            updateViewVisibility(iv_search, View.VISIBLE);
            updateViewVisibility(ll_right_menu, View.VISIBLE);
        } else if (baseFragment instanceof ChangePasswordFragment) {
            boolean autoOpen = ((ChangePasswordFragment) baseFragment).autoOpen;
            if (autoOpen) {
                updateViewVisibility(iv_menu_left, View.GONE);
                updateViewVisibility(iv_back, View.VISIBLE);
                updateViewVisibility(iv_logo, View.VISIBLE);
                updateViewVisibility(tv_title, View.GONE);
                updateViewVisibility(iv_search, View.GONE);
                updateViewVisibility(ll_right_menu, View.GONE);
            } else {
                updateViewVisibility(iv_menu_left, View.VISIBLE);
                updateViewVisibility(iv_back, View.GONE);
                updateViewVisibility(iv_logo, View.VISIBLE);
                updateViewVisibility(tv_title, View.GONE);
                updateViewVisibility(iv_search, View.VISIBLE);
                updateViewVisibility(ll_right_menu, View.VISIBLE);
            }

        } else if (baseFragment instanceof MyBetsFragment) {
            updateViewVisibility(iv_menu_left, View.VISIBLE);
            updateViewVisibility(iv_back, View.GONE);
            updateViewVisibility(iv_logo, View.VISIBLE);
            updateViewVisibility(tv_title, View.GONE);
            updateViewVisibility(iv_search, View.VISIBLE);
            updateViewVisibility(ll_right_menu, View.VISIBLE);
        }

    }

    public void onDestroyViewFragment(BaseFragment baseFragment) {

    }
}
