package com.lotus.ui.main.matchDetail.liveTv;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.lotus.BuildConfig;
import com.lotus.R;
import com.lotus.appBase.AppBaseFragment;
import com.lotus.ui.main.matchDetail.market.MarketFragment;

public class LiveTVFragment extends AppBaseFragment {

    RadioGroup rg_tvs;
    RadioButton rb1, rb2, rb3, rb4;

    FrameLayout fl_tv;

    LinearLayout ll_tvs_btn;
    TextView tv_tv1;
    TextView tv_tv2;

    View selectedTv;

    private MarketFragment marketFragment;

    public MarketFragment getMarketFragment() {
        return marketFragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getView() == null) {
            return super.onCreateView(inflater, container, savedInstanceState);
        }
        return getView();
    }

    @Override
    public int getLayoutResourceId() {
        return R.layout.fragment_live_tv;
    }

    @Override
    public int getFragmentContainerResourceId(Fragment fragment) {
        return R.id.fl_tv;
    }

    @Override
    public void initializeComponent() {
        super.initializeComponent();

        fl_tv = getView().findViewById(R.id.fl_tv);
        rg_tvs = getView().findViewById(R.id.rg_tvs);
        rb1 = getView().findViewById(R.id.rb1);
        rb2 = getView().findViewById(R.id.rb2);
        rb3 = getView().findViewById(R.id.rb3);
        rb4 = getView().findViewById(R.id.rb4);
        ll_tvs_btn = getView().findViewById(R.id.ll_tvs_btn);
        tv_tv1 = getView().findViewById(R.id.tv_tv1);
        tv_tv2 = getView().findViewById(R.id.tv_tv2);

        tv_tv1.setOnClickListener(this);
        tv_tv2.setOnClickListener(this);

        updateViewVisibility(rb3, View.GONE);
        updateViewVisibility(rb4, View.GONE);

        rg_tvs.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb1:
                        addTv(TV1);
                        break;
                    case R.id.rb2:
                        addTv(TV2);
                        break;
                    case R.id.rb3:
                        //addTv(TV3);
                        break;
                    case R.id.rb4:
                        // addTv(TV4);
                        break;
                }
            }
        });

        if (BuildConfig.TV_ACTIVE.equals("Y")) {
            updateViewVisibility(rg_tvs, View.GONE);
            updateViewVisibility(ll_tvs_btn, View.VISIBLE);
            updateViewVisibility(fl_tv, View.VISIBLE);
        } else {
            updateViewVisibility(rg_tvs, View.GONE);
            updateViewVisibility(ll_tvs_btn, View.GONE);
            updateViewVisibility(fl_tv, View.GONE);
        }

        if (selectedTv == null) {
            selectedTv = tv_tv1;
        }

        fl_tv.post(new Runnable() {
            @Override
            public void run() {

                int width = fl_tv.getWidth();
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) fl_tv.getLayoutParams();
                layoutParams.height = Math.round(width * 0.56f);
                fl_tv.setLayoutParams(layoutParams);

                selectedTv.performClick();
                addMarketFragment();

            }
        });

    }

    public void addMarketFragment() {
        marketFragment = new MarketFragment();
        int enterAnimation = 0;
        int exitAnimation = 0;
        int enterAnimationBackStack = 0;
        int exitAnimationBackStack = 0;
        FragmentTransaction ft = getNewChildFragmentTransaction();
        ft.add(R.id.market_container2, marketFragment, marketFragment.getClass().getSimpleName());
        ft.setCustomAnimations(enterAnimation, exitAnimation, enterAnimationBackStack, exitAnimationBackStack);
        ft.commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_tv1:
                selectedTv = v;
                tv_tv1.setSelected(true);
                tv_tv2.setSelected(false);
                addTv(TV1);
                break;
            case R.id.tv_tv2:
                selectedTv = v;
                tv_tv1.setSelected(false);
                tv_tv2.setSelected(true);
                addTv(TV2);
                break;
        }
    }

    private void addTv(String url) {
        Tv tv = new Tv();
        tv.setUrl(url);
        changeChildFragment(tv, false, true, 0, true);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (!isVisibleToUser) {
            try {
                Fragment fragmentById = getChildFm().findFragmentById(R.id.fl_tv);
                if (fragmentById != null) {
                    getChildFm().beginTransaction().remove(fragmentById).commit();

                }
            } catch (Exception ignore) {

            }
        } else {
            if (selectedTv != null) {
                selectedTv.performClick();
            }
//            try {
//                Fragment fragmentById = getChildFm().findFragmentById(R.id.fl_tv);
//                if (fragmentById == null) {
//                    addTv();
//
//                }
//            } catch (Exception ignore) {
//
//            }
        }
    }
}
