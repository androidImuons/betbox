package com.lotus.ui.main.leftsidemenu.cricket;


import android.view.View;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.lotus.R;
import com.lotus.appBase.AppBaseFragment;
import com.lotus.model.CompetitionDetailModel;
import com.lotus.model.InplayModel;
import com.lotus.model.MatchDetailModel;
import com.lotus.model.SeriesListModel;
import com.lotus.model.UserModel;
import com.lotus.model.web_response.MatchBySportIdResponseModel;
import com.lotus.ui.main.leftsidemenu.competitionDetail.CompetitionDetailFragment;
import com.lotus.ui.main.leftsidemenu.cricket.adapter.CompetitionsAdapter;
import com.lotus.ui.main.leftsidemenu.cricket.adapter.SeriesInplayAdapter;
import com.lotus.ui.main.matchDetail.MatchDetailFragment;
import com.medy.retrofitwrapper.WebRequest;
import com.utilities.ItemClickSupport;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CricketFragment extends AppBaseFragment {

    private TextView tvTitle;
    private TextView tv_inPlay_count;
    private RecyclerView rv_inPlay;
    private SeriesInplayAdapter inplayAdapter;
    private CardView card_no_record_found;
    private RecyclerView rv_competitions;
    private CompetitionsAdapter competitionsAdapter;
    private CardView card_no_record_found1;
    private MatchBySportIdResponseModel responseModel;

    private int seriesType;

    public void setGameType(int gameType) {
        seriesType = gameType;
    }

    @Override
    public int getLayoutResourceId() {
        return R.layout.fragment_cricket;
    }

    @Override
    public void initializeComponent() {
        super.initializeComponent();
        rv_inPlay = getView().findViewById(R.id.rv_inPlay);
        tv_inPlay_count = getView().findViewById(R.id.tv_inPlay_count);
        rv_competitions = getView().findViewById(R.id.rv_competitions);
        tvTitle = getView().findViewById(R.id.tvTitle);
        card_no_record_found = getView().findViewById(R.id.card_no_record_found);
        card_no_record_found1 = getView().findViewById(R.id.card_no_record_found1);
        updateViewVisibility(card_no_record_found, View.GONE);
        updateViewVisibility(card_no_record_found1, View.GONE);
        setUpTitle();

        initializeInPlayRecyclerView();
        initializeCompetitionsRecyclerView();

        try {
            UserModel userModel = getMyApplication().getUserModel();
            displayProgressBar(false, getString(R.string.please_wait));
            getWebRequestHelper().getInPlayMatchBySportId(seriesType, userModel, this);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


    }

    private void setUpTitle() {
        switch (seriesType) {
            case 4:
                tvTitle.setText("Cricket");
                break;
            case 1:
                tvTitle.setText("Football");

                break;
            case 2:
                tvTitle.setText("Tennis");

                break;
        }
    }

    private void initializeInPlayRecyclerView() {
        rv_inPlay = getView().findViewById(R.id.rv_inPlay);
        inplayAdapter = new SeriesInplayAdapter(getActivity());
        rv_inPlay.setLayoutManager(getVerticalLinearLayoutManager());
        rv_inPlay.setAdapter(inplayAdapter);
        new ItemClickSupport(rv_inPlay).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                InplayModel inplayModel = responseModel.getInPlay().get(position);
                MatchDetailModel matchDetailModel = new MatchDetailModel();
                matchDetailModel.setMatchid(inplayModel.getMstCode());
                matchDetailModel.setMatchName(inplayModel.getMatchName());
                matchDetailModel.setSportId(Long.parseLong(inplayModel.getSportID()));
                matchDetailModel.setMatchdate(inplayModel.getMstDate());
                addMatchDetailFragment(matchDetailModel);
            }
        });
    }

    private void initializeCompetitionsRecyclerView() {
        rv_competitions = getView().findViewById(R.id.rv_competitions);
        competitionsAdapter = new CompetitionsAdapter(getActivity());
        rv_competitions.setLayoutManager(getVerticalLinearLayoutManager());
        rv_competitions.setAdapter(competitionsAdapter);
        new ItemClickSupport(rv_competitions).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                try {
                    SeriesListModel seriesListModel = responseModel.getSeries().get(position);
                    UserModel userModel = getMyApplication().getUserModel();
                    displayProgressBar(false, getString(R.string.please_wait));
                    getWebRequestHelper().getSeriesWithMatchData(seriesListModel, userModel, CricketFragment.this);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    @Override
    public void onWebRequestResponse(WebRequest webRequest) {
        dismissProgressBar();
        super.onWebRequestResponse(webRequest);
        if (webRequest.getWebRequestId() == ID_GET_SERIES_LIST) {
            handleSeriesListResponse(webRequest);
        } else if (webRequest.getWebRequestId() == ID_MATCH_BY_SPORT_ID) {
            handleMatchBySportIdResponse(webRequest);
        } else if (webRequest.getWebRequestId() == ID_SERIES_WITH_MATCH_DATA) {
            handleSeriesDetailResponse(webRequest);
        }
    }


    private void handleMatchBySportIdResponse(WebRequest webRequest) {
        try {
            String responseString = webRequest.getResponseString();
            responseModel = new Gson().fromJson(responseString, MatchBySportIdResponseModel.class);
            if (responseModel == null) return;
            List<SeriesListModel> series = responseModel.getSeries();
            List<InplayModel> inPlay = responseModel.getInPlay();
            tv_inPlay_count.setText("(" + inPlay.size() + ")");
            if (inPlay.size() == 0) {
                updateViewVisibility(card_no_record_found, View.VISIBLE);
            }

            if (series.size() == 0) {
                updateViewVisibility(card_no_record_found1, View.VISIBLE);
            }

            inplayAdapter.updateData(responseModel.getInPlay());
            competitionsAdapter.updateData(responseModel.getSeries());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleSeriesListResponse(WebRequest webRequest) {

    }

    private void handleSeriesDetailResponse(WebRequest webRequest) {
        try {
            SeriesListModel seriesListModel = webRequest.getExtraData(KEY_SERIES_WITH_MATCH_DATA);
            String responseString = webRequest.getResponseString();
            JSONArray jsonArray = new JSONArray(responseString);
            if (jsonArray.length() == 0)
                showErrorMessage("No record found.");
            List<CompetitionDetailModel> list = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                String s = jsonArray.getJSONObject(i).toString();
                CompetitionDetailModel detailModel = new Gson().fromJson(s, CompetitionDetailModel.class);
                list.add(detailModel);
            }
            addCompetitionDetailFragment(list, seriesListModel);
        } catch (JSONException | JsonSyntaxException e) {
            e.printStackTrace();
        }
    }

    private void addCompetitionDetailFragment(List<CompetitionDetailModel> list, SeriesListModel seriesListModel) {
        if (list.size() == 0) return;
        CompetitionDetailFragment fragment = new CompetitionDetailFragment();
        fragment.setCompetitionDetailList(list);
        fragment.setSeriesName(seriesListModel);
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

}
