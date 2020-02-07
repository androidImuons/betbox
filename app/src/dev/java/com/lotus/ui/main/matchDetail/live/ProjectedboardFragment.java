package com.lotus.ui.main.matchDetail.live;

import android.widget.TextView;

import com.google.gson.internal.LinkedTreeMap;
import com.lotus.R;
import com.lotus.appBase.AppBaseFragment;
import com.lotus.model.MatchScoreModel;
import com.lotus.model.MatchScoreModel2;
import com.lotus.utilites.MatchScoreHelper;

import java.util.List;

public class ProjectedboardFragment extends AppBaseFragment implements MatchScoreHelper.MatchScoreHelperListener {

    private TextView tv_proj_scr;
    private TextView tv_pship;
    private TextView tv_crr;
    private TextView tv_last_wicket;
    private TextView tv_batsmen_first;
    private TextView tv_batsmen_first_run;
    private TextView tv_batsmen_first_ball;
    private TextView tv_batsmen_first_four;
    private TextView tv_batsmen_first_six;
    private TextView tv_batsmen_first_strike_rate;
    private TextView tv_batsmen_second;
    private TextView tv_batsmen_second_run;
    private TextView tv_batsmen_second_ball;
    private TextView tv_batsmen_second_four;
    private TextView tv_batsmen_second_six;
    private TextView tv_batsmen_second_strike_rate;
    private TextView tv_bowler_name;
    private TextView tv_bowler_over;
    private TextView tv_bowler_maidens;
    private TextView tv_bowler_wickets;
    private TextView tv_bowler_runs;
    private TextView tv_bowler_name_economy;

    @Override
    public int getLayoutResourceId() {
        return R.layout.fragment_projectedboard;
    }

    @Override
    public void initializeComponent() {
        super.initializeComponent();
        tv_proj_scr = getView().findViewById(R.id.tv_proj_scr);
        tv_pship = getView().findViewById(R.id.tv_pship);
        tv_crr = getView().findViewById(R.id.tv_crr);
        tv_last_wicket = getView().findViewById(R.id.tv_last_wicket);
        tv_batsmen_first = getView().findViewById(R.id.tv_batsmen_first);
        tv_batsmen_first_run = getView().findViewById(R.id.tv_batsmen_first_run);
        tv_batsmen_first_ball = getView().findViewById(R.id.tv_batsmen_first_ball);
        tv_batsmen_first_four = getView().findViewById(R.id.tv_batsmen_first_four);
        tv_batsmen_first_six = getView().findViewById(R.id.tv_batsmen_first_six);
        tv_batsmen_first_strike_rate = getView().findViewById(R.id.tv_batsmen_first_strike_rate);
        tv_batsmen_second = getView().findViewById(R.id.tv_batsmen_second);
        tv_batsmen_second_run = getView().findViewById(R.id.tv_batsmen_second_run);
        tv_batsmen_second_ball = getView().findViewById(R.id.tv_batsmen_second_ball);
        tv_batsmen_second_four = getView().findViewById(R.id.tv_batsmen_second_four);
        tv_batsmen_second_six = getView().findViewById(R.id.tv_batsmen_second_six);
        tv_batsmen_second_strike_rate = getView().findViewById(R.id.tv_batsmen_second_strike_rate);
        tv_bowler_name = getView().findViewById(R.id.tv_bowler_name);
        tv_bowler_over = getView().findViewById(R.id.tv_bowler_over);
        tv_bowler_maidens = getView().findViewById(R.id.tv_bowler_maidens);
        tv_bowler_wickets = getView().findViewById(R.id.tv_bowler_wickets);
        tv_bowler_runs = getView().findViewById(R.id.tv_bowler_runs);
        tv_bowler_name_economy = getView().findViewById(R.id.tv_bowler_name_economy);
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
                    tv_proj_scr.setText(battingTeam.getProjScr());
                    tv_pship.setText(battingTeam.getPartnerShipScore());
                    tv_crr.setText(battingTeam.getRunRate());
                }


                /**
                 * last batsmen out
                 */
                MatchScoreModel.ResultBean.LastBatsmanOutBean lastBatsmanOut = result.getLastBatsmanOut();
                if (lastBatsmanOut != null) {
                    tv_last_wicket.setText(lastBatsmanOut.getName() + " " +
                            lastBatsmanOut.getBatsManRuns() + "(" + lastBatsmanOut.getBalls() + ")");
                }
                List<MatchScoreModel.ResultBean.BatsmenBean> batsmen = result.getBatsmen();
                if (batsmen == null || batsmen.size() == 0) {
                    tv_batsmen_first.setText("--");
                    tv_batsmen_first_run.setText("-");
                    tv_batsmen_first_ball.setText("-");
                    tv_batsmen_first_four.setText("-");
                    tv_batsmen_first_six.setText("-");
                    tv_batsmen_first_strike_rate.setText("-");

                    tv_batsmen_second.setText("--");
                    tv_batsmen_second_run.setText("-");
                    tv_batsmen_second_ball.setText("-");
                    tv_batsmen_second_four.setText("-");
                    tv_batsmen_second_six.setText("-");
                    tv_batsmen_second_strike_rate.setText("-");
                } else if (batsmen.size() == 1) {
                    MatchScoreModel.ResultBean.BatsmenBean batsmenBean = batsmen.get(0);
                    tv_batsmen_first.setText(batsmenBean.getName());
                    tv_batsmen_first_run.setText(batsmenBean.getBatsmanRuns());
                    tv_batsmen_first_ball.setText(batsmenBean.getBalls());
                    tv_batsmen_first_four.setText(batsmenBean.getFours());
                    tv_batsmen_first_six.setText(batsmenBean.getSixes());
                    tv_batsmen_first_strike_rate.setText(String.valueOf(batsmenBean.getStrikeRate()));
                } else if (batsmen.size() == 2) {
                    MatchScoreModel.ResultBean.BatsmenBean batsmenFirst = batsmen.get(0);
                    if (batsmenFirst.isIsOnStrike())
                        tv_batsmen_first.setText(batsmenFirst.getName() + "*");
                    else
                        tv_batsmen_first.setText(batsmenFirst.getName());

                    tv_batsmen_first_run.setText(batsmenFirst.getBatsmanRuns());
                    tv_batsmen_first_ball.setText(batsmenFirst.getBalls());
                    tv_batsmen_first_four.setText(batsmenFirst.getFours());
                    tv_batsmen_first_six.setText(batsmenFirst.getSixes());
                    tv_batsmen_first_strike_rate.setText(String.valueOf(batsmenFirst.getStrikeRate()));

                    MatchScoreModel.ResultBean.BatsmenBean batsmenSecond = batsmen.get(1);
                    if (batsmenSecond.isIsOnStrike())
                        tv_batsmen_second.setText(batsmenSecond.getName() + "*");
                    else
                        tv_batsmen_second.setText(batsmenSecond.getName());

                    tv_batsmen_second_run.setText(batsmenSecond.getBatsmanRuns());
                    tv_batsmen_second_ball.setText(batsmenSecond.getBalls());
                    tv_batsmen_second_four.setText(batsmenSecond.getFours());
                    tv_batsmen_second_six.setText(batsmenSecond.getSixes());
                    tv_batsmen_second_strike_rate.setText(String.valueOf(batsmenSecond.getStrikeRate()));
                }

                Object bowler = result.getBowler();
                if (bowler instanceof LinkedTreeMap) {
                    LinkedTreeMap<String, Object> map = (LinkedTreeMap<String, Object>) bowler;
                    tv_bowler_name.setText(String.valueOf(map.get("name")));
                    double overs = (double) map.get("overs");
                    tv_bowler_over.setText(String.valueOf(overs));
                    double maidens = (double) map.get("maidens");
                    tv_bowler_maidens.setText(String.valueOf((int) maidens));
                    double wickets = (double) map.get("wickets");
                    tv_bowler_wickets.setText(String.valueOf((int) wickets));
                    double bowlerRuns = (double) map.get("bowlerRuns");
                    tv_bowler_runs.setText(String.valueOf((int) bowlerRuns));
                    String economy = String.valueOf(map.get("economy"));
                    tv_bowler_name_economy.setText(economy);
                } else {
                    tv_bowler_name.setText("-");
                    tv_bowler_over.setText("-");
                    tv_bowler_maidens.setText("-");
                    tv_bowler_wickets.setText("-");
                    tv_bowler_runs.setText("-");
                    tv_bowler_name_economy.setText("-");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
