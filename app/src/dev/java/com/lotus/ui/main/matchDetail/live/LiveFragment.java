package com.lotus.ui.main.matchDetail.live;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.base.BaseFragment;
import com.base.WrappingViewPagerAdapter;
import com.customviews.WrappingViewPager;
import com.lotus.R;
import com.lotus.appBase.AppBaseFragment;
import com.lotus.model.MatchDetailModel;
import com.lotus.model.MatchScoreModel;
import com.lotus.model.MatchScoreModel2;
import com.lotus.ui.main.matchDetail.MatchDetailFragment;
import com.lotus.ui.main.matchDetail.market.MarketFragment;
import com.lotus.utilites.MatchScoreHelper;

import java.util.concurrent.atomic.AtomicBoolean;

public class LiveFragment extends AppBaseFragment
        implements MatchScoreHelper.MatchScoreHelperListener {

    private TextView tv_match_name1;
    private TextView tv_date;

    private NestedScrollView nested_scroll_view;
    private TextView tv_no_data;

    private LinearLayout match_score2;
    private LinearLayout ll_cricket_score_card;
    private LinearLayout ll_tenis_score_card;
    private LinearLayout ll_sccore_score_card;
    private LinearLayout ll_first_inning;
    private LinearLayout ll_second_inning;
    private TextView tv_match_name_score;
    private TextView tv_team1;
    private TextView tv_team11;
    private TextView tv_team2;
    private TextView tv_team22;
    private TextView tv_team1_score;
    private TextView tv_team11_score;
    private TextView tv_team2_score;
    private TextView tv_team22_score;

    private TextView tv_tenis_current_set;
    private TextView tv_tenis_home_name;
    private TextView tv_tenis_home_score;
    private TextView view_home_serving;
    private TextView tv_tenis_away_name;
    private TextView tv_tenis_away_score;
    private TextView view_away_serving;

    private WrappingViewPager match_score_pager;
    WrappingViewPagerAdapter wrappingViewPagerAdapter;

    private MarketFragment marketFragment;

    public MarketFragment getMarketFragment() {
        return marketFragment;
    }

    @Override
    public int getLayoutResourceId() {
        return R.layout.fragment_live;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getView() == null)
            return super.onCreateView(inflater, container, savedInstanceState);
        return getView();
    }

    @Override
    public void initializeComponent() {
        super.initializeComponent();

        tv_match_name1 = getView().findViewById(R.id.tv_match_name1);
        tv_date = getView().findViewById(R.id.tv_date);


        match_score2 = getView().findViewById(R.id.match_score2);
        ll_cricket_score_card = getView().findViewById(R.id.ll_cricket_score_card);
        ll_tenis_score_card = getView().findViewById(R.id.ll_tenis_score_card);
        ll_sccore_score_card = getView().findViewById(R.id.ll_sccore_score_card);
        ll_first_inning = getView().findViewById(R.id.ll_first_inning);
        ll_second_inning = getView().findViewById(R.id.ll_second_inning);
        tv_match_name_score = getView().findViewById(R.id.tv_match_name_score);
        tv_team1 = getView().findViewById(R.id.tv_team1);
        tv_team11 = getView().findViewById(R.id.tv_team11);
        tv_team2 = getView().findViewById(R.id.tv_team2);
        tv_team22 = getView().findViewById(R.id.tv_team22);
        tv_team1_score = getView().findViewById(R.id.tv_team1_score);
        tv_team11_score = getView().findViewById(R.id.tv_team11_score);
        tv_team2_score = getView().findViewById(R.id.tv_team2_score);
        tv_team22_score = getView().findViewById(R.id.tv_team22_score);

        tv_tenis_current_set = getView().findViewById(R.id.tv_tenis_current_set);
        tv_tenis_home_name = getView().findViewById(R.id.tv_tenis_home_name);
        tv_tenis_home_score = getView().findViewById(R.id.tv_tenis_home_score);
        view_home_serving = getView().findViewById(R.id.view_home_serving);
        tv_tenis_away_name = getView().findViewById(R.id.tv_tenis_away_name);
        tv_tenis_away_score = getView().findViewById(R.id.tv_tenis_away_score);
        view_away_serving = getView().findViewById(R.id.view_away_serving);


        nested_scroll_view = getView().findViewById(R.id.view_nested_scroll);
        tv_no_data = getView().findViewById(R.id.tv_no_data);
        updateViewVisibility(tv_no_data, View.GONE);
        match_score_pager = getView().findViewById(R.id.match_score_pager);
        setupScoreBoardViewPager();

        updateViewVisibility(match_score2, View.GONE);
        updateViewVisibility(match_score_pager, View.GONE);

        onMatchScoreUpdate(((MatchDetailFragment) getParentFragment()).matchScoreModel);
        onMatchScoreUpdate2(((MatchDetailFragment) getParentFragment()).matchScoreModel2);
        updateNoDataView();

        addMarketFragment();


    }

    public void addMarketFragment() {
        marketFragment = new MarketFragment();
        int enterAnimation = 0;
        int exitAnimation = 0;
        int enterAnimationBackStack = 0;
        int exitAnimationBackStack = 0;
        FragmentTransaction ft = getNewChildFragmentTransaction();
        ft.add(R.id.market_container, marketFragment, marketFragment.getClass().getSimpleName());
        ft.setCustomAnimations(enterAnimation, exitAnimation, enterAnimationBackStack, exitAnimationBackStack);
        ft.commit();
    }


    private void setupScoreBoardViewPager() {

        wrappingViewPagerAdapter = new WrappingViewPagerAdapter(getChildFm());
        wrappingViewPagerAdapter.addFragment(new ScoreboardFragment(), "");
        wrappingViewPagerAdapter.addFragment(new ProjectedboardFragment(), "");

        match_score_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                onMatchScoreUpdate(((MatchDetailFragment) getParentFragment()).matchScoreModel);
                onMatchScoreUpdate2(((MatchDetailFragment) getParentFragment()).matchScoreModel2);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        match_score_pager.setAdapter(wrappingViewPagerAdapter);

    }


    public void setupMatchData() {
        if (getMatchDeatilModel() == null) return;
        tv_match_name_score.setText(getMatchDeatilModel().getMatchName());
        tv_match_name1.setText(getMatchDeatilModel().getMatchName());
        tv_date.setText(getMatchDeatilModel().getFormattedMatchdate2());
    }


    public MatchDetailModel getMatchDeatilModel() {
        if (isValidActivity()) {
            BaseFragment latestFragment = (BaseFragment) getParentFragment();
            if (latestFragment instanceof MatchDetailFragment) {
                return ((MatchDetailFragment) latestFragment).getMatchDetailModel();
            }
        }
        return null;
    }


    @Override
    public void onMatchScoreUpdate(MatchScoreModel responseModel) {
        setData(responseModel);
        updateNoDataView();
    }

    @Override
    public void onMatchScoreUpdate2(MatchScoreModel2 responseModel) {
        setData2(responseModel);
        updateNoDataView();
    }

    private void setData(MatchScoreModel responseModel) {
        try {
            if (getMatchDeatilModel() == null) return;
            if (getMatchDeatilModel().getSportId() != 4) {
                return;
            }
            if (responseModel != null && responseModel.isSuccess()) {
                MatchScoreModel.ResultBean result = responseModel.getResult();
                if (result == null) {
                    updateViewVisibility(match_score_pager, View.GONE);
                    return;
                }
                updateViewVisibility(match_score_pager, View.VISIBLE);

                if (wrappingViewPagerAdapter != null) {
                    ScoreboardFragment item = (ScoreboardFragment) wrappingViewPagerAdapter.getItem(0);
                    item.onMatchScoreUpdate(responseModel);
                    ProjectedboardFragment item1 = (ProjectedboardFragment) wrappingViewPagerAdapter.getItem(1);
                    item1.onMatchScoreUpdate(responseModel);

                }
            } else {
                updateViewVisibility(match_score_pager, View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setData2(MatchScoreModel2 responseModel) {
        if (getMatchDeatilModel() == null) return;

        if (responseModel == null) {
            updateViewVisibility(match_score2, View.GONE);
        } else {

            String[] teamsName = responseModel.getTeamsName();
            String[] teamsScore = responseModel.getTeamsScore(responseModel.getEventTypeId());
            if (responseModel.getEventTypeId() == 4) {
                tv_team1.setText(teamsName[0]);
                tv_team11.setText(teamsName[0]);
                tv_team2.setText(teamsName[1]);
                tv_team22.setText(teamsName[1]);

                String inning1ScoreT1 = teamsScore[0];
                String inning1ScoreT2 = teamsScore[1];
                String inning2ScoreT1 = teamsScore[2];
                String inning2ScoreT2 = teamsScore[3];

                tv_team1_score.setText(isValidString(inning1ScoreT1) ? inning1ScoreT1 : "/ ()");
                tv_team2_score.setText(isValidString(inning1ScoreT2) ? inning1ScoreT2 : "/ ()");


                if (isValidString(inning2ScoreT1) || isValidString(inning2ScoreT2)) {
                    tv_team11_score.setText(isValidString(inning2ScoreT1) ? inning2ScoreT1 : "/ ()");
                    tv_team22_score.setText(isValidString(inning2ScoreT2) ? inning2ScoreT2 : "/ ()");
                    updateViewVisibility(ll_second_inning, View.VISIBLE);
                } else {
                    updateViewVisibility(ll_second_inning, View.GONE);
                }

                updateViewVisibility(ll_cricket_score_card, View.VISIBLE);
                updateViewVisibility(ll_tenis_score_card, View.GONE);
                updateViewVisibility(ll_sccore_score_card, View.GONE);

            } else if (responseModel.getEventTypeId() == 2) {
                tv_tenis_current_set.setText(responseModel.getGameStatus());
                boolean[] teamsServing = responseModel.getTeamsServing(2);

                tv_tenis_home_name.setText(teamsName[0]);
                tv_tenis_away_name.setText(teamsName[1]);

                view_home_serving.setSelected(teamsServing[0]);
                view_away_serving.setSelected(teamsServing[1]);

//                tv_tenis_home_score.setText(teamsScore[0]);
//                tv_tenis_away_score.setText(teamsScore[1]);
                justify(tv_tenis_home_score, teamsScore[0]);
                justify(tv_tenis_away_score, teamsScore[1]);

                updateViewVisibility(ll_cricket_score_card, View.GONE);
                updateViewVisibility(ll_tenis_score_card, View.VISIBLE);
                updateViewVisibility(ll_sccore_score_card, View.GONE);
            } else if (responseModel.getEventTypeId() == 1) {
                tv_tenis_current_set.setText(responseModel.getGameStatus());
                boolean[] teamsServing = responseModel.getTeamsServing(1);

                tv_tenis_home_name.setText(teamsName[0]);
                tv_tenis_away_name.setText(teamsName[1]);

                view_home_serving.setActivated(teamsServing[0]);
                view_away_serving.setActivated(teamsServing[1]);

                tv_tenis_home_score.setText(teamsScore[0]);
                tv_tenis_away_score.setText(teamsScore[1]);

                updateViewVisibility(ll_cricket_score_card, View.GONE);
                updateViewVisibility(ll_tenis_score_card, View.VISIBLE);
                updateViewVisibility(ll_sccore_score_card, View.GONE);
            }

            if (match_score_pager.getVisibility() == View.GONE)
                updateViewVisibility(match_score2, View.VISIBLE);
        }
    }

    private void updateNoDataView() {
        if (getMatchDeatilModel() != null) {
//            if (getMatchDeatilModel().getSportId() != 4) {
//                updateViewVisibility(tv_no_data, View.VISIBLE);
//                updateViewVisibility(nested_scroll_view, View.GONE);
//            } else {
            if (match_score_pager.getVisibility() == View.GONE && match_score2.getVisibility() == View.GONE) {
                updateViewVisibility(tv_no_data, View.VISIBLE);
                updateViewVisibility(nested_scroll_view, View.GONE);
            } else {
                updateViewVisibility(tv_no_data, View.GONE);
                updateViewVisibility(nested_scroll_view, View.VISIBLE);
            }
            setupMatchData();
//            }
        }

    }

    public void justify(final TextView textView, String data) {

        final AtomicBoolean isJustify = new AtomicBoolean(false);

        final String textString = data;

        final TextPaint textPaint = textView.getPaint();

        final SpannableStringBuilder builder = new SpannableStringBuilder();

        textView.post(new Runnable() {
            @Override
            public void run() {

                if (!isJustify.get()) {

                    final int lineCount = textView.getLineCount();
                    final int textViewWidth = textView.getWidth();

                    for (int i = 0; i < lineCount; i++) {

                        int lineStart = textView.getLayout().getLineStart(i);
                        int lineEnd = textString.length();

                        String lineString = textString.substring(lineStart, lineEnd);

//                        if (i == lineCount - 1) {
//                            builder.append(new SpannableString(lineString));
//                            break;
//                        }

                        String trimSpaceText = lineString.trim();
                        String removeSpaceText = lineString.replaceAll(" ", "");

                        float removeSpaceWidth = textPaint.measureText(removeSpaceText);
                        float spaceCount = trimSpaceText.length() - removeSpaceText.length();

                        float eachSpaceWidth = (textViewWidth - removeSpaceWidth) / spaceCount;

                        SpannableString spannableString = new SpannableString(lineString);
                        for (int j = 0; j < trimSpaceText.length(); j++) {
                            char c = trimSpaceText.charAt(j);
                            if (c == ' ') {
                                Drawable drawable = new ColorDrawable(0x00ffffff);
                                drawable.setBounds(0, 0, (int) eachSpaceWidth, 0);
                                ImageSpan span = new ImageSpan(drawable);
                                spannableString.setSpan(span, j, j + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                            }
                        }

                        builder.append(spannableString);
                    }

                    textView.setText(builder);
                    isJustify.set(true);
                }
            }
        });
    }


}
