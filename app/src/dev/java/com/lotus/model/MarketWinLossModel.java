package com.lotus.model;

import com.models.BaseModel;

import java.util.List;

public class MarketWinLossModel extends BaseModel {

    private String marketId;
    private List<RunnersBean> runners;

    public String getMarketId() {
        return marketId;
    }

    public void setMarketId(String marketId) {
        this.marketId = marketId;
    }

    public List<RunnersBean> getRunners() {
        return runners;
    }

    public void setRunners(List<RunnersBean> runners) {
        this.runners = runners;
    }

    public static class RunnersBean {

        private String winValue;
        private String lossValue;
        private String selectionId;

        public String getWinValue() {
            return winValue;
        }

        public void setWinValue(String winValue) {
            this.winValue = winValue;
        }

        public String getLossValue() {
            return lossValue;
        }

        public void setLossValue(String lossValue) {
            this.lossValue = lossValue;
        }

        public String getSelectionId() {
            return selectionId;
        }

        public void setSelectionId(String selectionId) {
            this.selectionId = selectionId;
        }
    }
}
