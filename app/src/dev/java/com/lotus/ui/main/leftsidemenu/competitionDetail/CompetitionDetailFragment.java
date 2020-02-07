package com.lotus.ui.main.leftsidemenu.competitionDetail;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.lotus.R;
import com.lotus.appBase.AppBaseFragment;
import com.lotus.model.CompetitionDetailModel;
import com.lotus.model.MatchDetailModel;
import com.lotus.model.SeriesListModel;
import com.lotus.ui.main.leftsidemenu.competitionDetail.adapter.CompetitionDetailAdapter;
import com.lotus.ui.main.matchDetail.MatchDetailFragment;
import com.utilities.ItemClickSupport;

import java.util.List;

public class CompetitionDetailFragment extends AppBaseFragment {

    private TextView tv_match_name;
    private RecyclerView recycler_view;
    private CompetitionDetailAdapter competitionsAdapter;
    private SeriesListModel seriesListModel;
    private List<CompetitionDetailModel> list;

    @Override
    public int getLayoutResourceId() {
        return R.layout.fragment_competition_detail;
    }

    @Override
    public void initializeComponent() {
        super.initializeComponent();
        tv_match_name = getView().findViewById(R.id.tv_match_name);
        if (seriesListModel != null)
            tv_match_name.setText(seriesListModel.getName());
        else
            tv_match_name.setText("");
        initializeInPlayRecyclerView();
    }

    private void initializeInPlayRecyclerView() {
        recycler_view = getView().findViewById(R.id.recycler_view);
        competitionsAdapter = new CompetitionDetailAdapter(getActivity(), list);
        recycler_view.setLayoutManager(getVerticalLinearLayoutManager());
        recycler_view.setAdapter(competitionsAdapter);
        new ItemClickSupport(recycler_view) {
            @Override
            public void onChildItemClicked(RecyclerView recyclerView, int parentPosition, int childPosition, View v) {
                try {
                    CompetitionDetailModel competitionDetailModel = list.get(childPosition);
                    if (competitionDetailModel == null) return;
                    addMatchDetailFragment(competitionDetailModel.getMatch().get(childPosition).getMatchListModel());
                } catch (IndexOutOfBoundsException e) {

                }
            }
        }.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {

            }
        });
    }

    public void setCompetitionDetailList(List<CompetitionDetailModel> competitionDetailList) {
        this.list = competitionDetailList;
    }

    public void setSeriesName(SeriesListModel seriesListModel) {
        this.seriesListModel = seriesListModel;
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
