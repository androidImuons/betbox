package com.lotus.ui.main.matchDetail.live;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.lotus.R;
import com.lotus.appBase.AppBaseFragment;
import com.lotus.model.MatchDetailModel;
import com.lotus.model.MatchScoreModel;
import com.lotus.model.MatchScoreModel2;
import com.lotus.ui.main.matchDetail.live.adapter.ScoreTeamAdapter;
import com.lotus.ui.main.matchDetail.live.adapter.ScoreTeamOneAdapter;
import com.lotus.utilites.MatchScoreHelper;

public class ScoreboardFragment extends AppBaseFragment implements MatchScoreHelper.MatchScoreHelperListener {
    private TextView tv_match_name;
    private TextView tv_name_run_wicket_over;
    private TextView tv_toss_winner;
    private CardView card_over_board;
    private LinearLayout ll_current_over;
    private TextView tv_current_over;
    private RecyclerView recycler_view;
    private ScoreTeamAdapter adapter;
    private TextView tv_over;
    private TextView tv_runs;
    private TextView tv_total_run_wickets;
    private LinearLayout ll_last_over;
    private TextView tv_last_over;
    private ScoreTeamOneAdapter oneAdapter;
    private RecyclerView recycler_view1;
    private TextView tv_over_team2;
    private TextView tv_runs_team2;
    private TextView tv_total_run_wickets_team2;

    @Override
    public int getLayoutResourceId() {
        return R.layout.fragment_scoreboard;
    }

    @Override
    public void initializeComponent() {
        super.initializeComponent();
        tv_match_name = getView().findViewById(R.id.tv_match_name);
        tv_name_run_wicket_over = getView().findViewById(R.id.tv_name_run_wicket_over);
        tv_toss_winner = getView().findViewById(R.id.tv_toss_winner);
        card_over_board = getView().findViewById(R.id.card_over_board);
        ll_current_over = getView().findViewById(R.id.ll_current_over);
        tv_current_over = getView().findViewById(R.id.tv_current_over);
        recycler_view = getView().findViewById(R.id.recycler_view);
        tv_over = getView().findViewById(R.id.tv_over);
        tv_runs = getView().findViewById(R.id.tv_runs);
        tv_total_run_wickets = getView().findViewById(R.id.tv_total_run_wickets);
        ll_last_over = getView().findViewById(R.id.ll_last_over);
        tv_last_over = getView().findViewById(R.id.tv_last_over);
        recycler_view1 = getView().findViewById(R.id.recycler_view1);
        tv_over_team2 = getView().findViewById(R.id.tv_over_team2);
        tv_runs_team2 = getView().findViewById(R.id.tv_runs_team2);
        tv_total_run_wickets_team2 = getView().findViewById(R.id.tv_total_run_wickets_team2);

        initializeRecyclerView();
        initializeRecyclerView1();

        MatchDetailModel matchDeatilModel = ((LiveFragment) getParentFragment()).getMatchDeatilModel();
        if (matchDeatilModel != null) {
            tv_match_name.setText(matchDeatilModel.getMatchName());
        }


    }

    private void initializeRecyclerView() {
        recycler_view.setLayoutManager(getHorizontalLayoutManager());
        adapter = new ScoreTeamAdapter(getActivity());
        recycler_view.setNestedScrollingEnabled(false);
        recycler_view.setAdapter(adapter);

    }

    private void initializeRecyclerView1() {
        recycler_view1.setLayoutManager(getHorizontalLayoutManager());
        oneAdapter = new ScoreTeamOneAdapter(getActivity());
        recycler_view1.setNestedScrollingEnabled(false);
        recycler_view1.setAdapter(oneAdapter);
    }

    @Override
    public void onMatchScoreUpdate(MatchScoreModel responseModel) {
        setData(responseModel);
    }

    @Override
    public void onMatchScoreUpdate2(MatchScoreModel2 responseModel) {

    }

    private void setData(MatchScoreModel responseModel) {
        try {
            if (responseModel != null && responseModel.isSuccess()) {
                MatchScoreModel.ResultBean result = responseModel.getResult();
                if (result == null) {
                    return;
                }

                /**
                 * batting
                 */
                MatchScoreModel.ResultBean.BattingTeamBean battingTeam = result.getBattingTeam();
                if (battingTeam != null) {
                    tv_name_run_wicket_over.setText(battingTeam.getName()
                            + " " + battingTeam.getScore() + "-" + battingTeam.getWickets()
                            + " (" + battingTeam.getOvers() + "." + battingTeam.getBalls() + " Over)");
                }

                /**
                 * toss winner
                 */
                String status = result.getStatus();
                if (status.equalsIgnoreCase("InPlay")) {
                    MatchScoreModel.ResultBean.TossWinnerBean tossWinner = result.getTossWinner();
                    if (tossWinner != null) {
                        tv_toss_winner.setText(tossWinner.getName() + " " + tossWinner.getDecision());
                    }
                } else {
                    tv_toss_winner.setText(status);
                }


                /**
                 * current over
                 */
                MatchScoreModel.ResultBean.CurrentOverBean currentOver = result.getCurrentOver();
                if (currentOver != null) {
                    updateViewVisibility(ll_current_over, View.VISIBLE);
                    StringBuilder builder = new StringBuilder();
                    builder.append(currentOver.getBowlerName());
                    if (currentOver.getBatsNames() == null || currentOver.getBatsNames().size() == 0) {

                    } else {
                        builder.append(" to ");
                        if (currentOver.getBatsNames().size() > 0) {
                            builder.append(currentOver.getBatsNames().get(0));
                        }
                        if (currentOver.getBatsNames().size() > 1) {
                            builder.append(" and ");
                            builder.append(currentOver.getBatsNames().get(1));
                        }
                    }
                    tv_current_over.setText(builder.toString());

                    tv_over.setText("Ov" + currentOver.getOverNumber());
                    tv_runs.setText(currentOver.getRuns() + "runs");
                    tv_total_run_wickets.setText(currentOver.getScore() + "-" + currentOver.getWickets());
                    adapter.update(currentOver.getBalls());
                } else {
                    updateViewVisibility(ll_current_over, View.GONE);
                }

                /**
                 * last over
                 */
                MatchScoreModel.ResultBean.LastOverBean lastOver = result.getLastOver();
                if (lastOver != null) {
                    updateViewVisibility(ll_last_over, View.VISIBLE);
                    StringBuilder builder = new StringBuilder();
                    builder.append(lastOver.getBowlerName());
                    if (currentOver.getBatsNames() == null || currentOver.getBatsNames().size() == 0) {

                    } else {
                        builder.append(" to ");
                        if (lastOver.getBatsNames().size() > 0) {
                            builder.append(lastOver.getBatsNames().get(0));
                        }
                        if (lastOver.getBatsNames().size() > 1) {
                            builder.append(" and ");
                            builder.append(lastOver.getBatsNames().get(1));
                        }
                    }
                    tv_last_over.setText(builder.toString());
                    tv_over_team2.setText("Ov" + lastOver.getOverNumber());
                    tv_runs_team2.setText(lastOver.getRuns() + "runs");
                    tv_total_run_wickets_team2.setText(lastOver.getScore() + "-" + lastOver.getWickets());
                    oneAdapter.update(lastOver.getBalls());
                } else {
                    updateViewVisibility(ll_last_over, View.GONE);
                }

                if (ll_current_over.getVisibility() == View.GONE && ll_last_over.getVisibility() == View.GONE)
                    updateViewVisibility(card_over_board, View.GONE);
                else
                    updateViewVisibility(card_over_board, View.VISIBLE);

            } else {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
