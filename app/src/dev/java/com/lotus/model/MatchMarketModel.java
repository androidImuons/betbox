package com.lotus.model;

import androidx.recyclerview.widget.DiffUtil;

import com.models.BaseModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sunil kumar Yadav
 * @Since 21/5/18
 */
public class MatchMarketModel extends BaseModel {


    private String marketid;
    private String market_name;
    private String is_favourite;
    private String status;
    List<RunnersModel> runners;
    private boolean IsMatchDisable = true;
    private int visibility = 1;
    private boolean inPlay = false;

    DiffUtil.DiffResult diffResultRunners;

    public DiffUtil.DiffResult getDiffResultRunners() {
        return diffResultRunners;
    }

    public void setDiffResultRunners(DiffUtil.DiffResult diffResultRunners) {
        this.diffResultRunners = diffResultRunners;
    }


    public String getStatus() {
        return getValidString(status);
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isSuspend() {
        return getStatus().equals("SUSPENDED");
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null) {
            if (obj instanceof MatchMarketModel) {
                return ((MatchMarketModel) obj).getMarketid().equals(getMarketid());
            }
        }
        return false;
    }

    public int getVisibility() {
        return visibility;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }

    public boolean isVisibility() {
        return getVisibility() == 1;
    }

    public void setInPlay(boolean inPlay) {
        this.inPlay = inPlay;
    }

    public boolean isInPlay() {
        return inPlay;
    }

    public String getIs_favourite() {
        return getValidString(is_favourite);
    }

    public void setIs_favourite(String is_favourite) {
        this.is_favourite = is_favourite;
    }


    public String getMarketid() {
        return getValidString(marketid);
    }

    public void setMarketid(String marketid) {
        this.marketid = marketid;
    }

    public String getMarket_name() {
        return market_name;
    }

    public void setMarket_name(String market_name) {
        this.market_name = market_name;
    }

    public List<RunnersModel> getRunners() {
        return runners;
    }

    public void setRunners(List<RunnersModel> runners) {
        this.runners = runners;
    }


    public boolean isMatchDisable() {
        return IsMatchDisable;
    }

    public void setMatchDisable(boolean matchDisable) {
        IsMatchDisable = matchDisable;
    }


    public boolean isFavourite() {
        return getIs_favourite().equalsIgnoreCase("Y");
    }


    public void shiftWinLossValues(WinLossModel win_loss) {
        if (win_loss == null || win_loss.getRunners() == null || win_loss.getRunners().size() == 0) {
            for (RunnersModel runner : runners) {
                runner.setWinValue("0");
                runner.setLossValue("0");
            }
            return;
        }
        List<WinLossModel.RunnersBean> winLossRunner = win_loss.getRunners();
        for (WinLossModel.RunnersBean runnersBean : winLossRunner) {
            List<RunnersModel> runners = getRunners();
            for (RunnersModel runner : runners) {
                if (runner.getSelectionId().equals(runnersBean.getSelectionId())) {
                    runner.setWinValue(runnersBean.getWinValue());
                    runner.setLossValue(runnersBean.getLossValue());
                    break;
                }
            }
        }
    }


    public boolean checkContentSame(MatchMarketModel newData) {
        if (!this.getMarketid().equals(newData.getMarketid())) {
            return false;
        }
        if (!this.getMarket_name().equals(newData.getMarket_name())) {
            return false;
        }
        if (!this.getIs_favourite().equals(newData.getIs_favourite())) {
            return false;
        }
        if (this.getVisibility() != newData.getVisibility()) {
            return false;
        }
        if (this.isMatchDisable() != newData.isMatchDisable()) {
            return false;
        }
        if (this.isInPlay() != newData.isInPlay()) {
            return false;
        }
        if (!this.getStatus().equals(newData.getStatus())) {
            return false;
        }
        return true;
    }


    public MatchMarketModel copyOf(MatchMarketModel matchMarketModel) {
        MatchMarketModel copyData = new MatchMarketModel();
        copyData.marketid = matchMarketModel.getMarketid();
        copyData.market_name = matchMarketModel.getMarket_name();
        copyData.is_favourite = matchMarketModel.getIs_favourite();
        copyData.visibility = matchMarketModel.getVisibility();
        copyData.status = matchMarketModel.getStatus();
        List<RunnersModel> copyRunners = new ArrayList<>();
        if (matchMarketModel.getRunners() != null) {
            for (RunnersModel runnersModel : matchMarketModel.getRunners()) {
                copyRunners.add(runnersModel.copyOf(runnersModel));
            }
        }
        copyData.setRunners(copyRunners);
        copyData.IsMatchDisable = matchMarketModel.isMatchDisable();
        copyData.inPlay = matchMarketModel.isInPlay();
        return copyData;
    }

    public void updateRunnerBackLay(List<RunnersModel> runnersModelList) {
        if (runnersModelList == null) return;
        for (RunnersModel runnersBean : getRunners()) {
            for (RunnersModel runner : runnersModelList) {
                if (runner.getSelectionId().equals(runnersBean.getSelectionId())) {
                    runnersBean.setBack(runner.getBack());
                    runnersBean.setLay(runner.getLay());
                    break;
                }
            }
        }
    }

    public void updateRunnerName(List<RunnersModel> runnersModelList) {
        if (runnersModelList == null) return;
        for (RunnersModel runnersBean : getRunners()) {
            if (!runnersBean.isValidString(runnersBean.getRunnerName())) {
                for (RunnersModel runner : runnersModelList) {
                    if (runner.getSelectionId().equals(runnersBean.getSelectionId())) {
                        runnersBean.setRunnerName(runner.getRunnerName());
                        break;
                    }
                }
            }
        }
    }
}
