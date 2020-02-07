package com.lotus.model;

import com.models.BaseModel;

import java.util.List;

/**
 * Created by Azher on 17/11/18.
 */
public class WinLossModel extends BaseModel {

    private String marketId;

    private List<RunnersBean> runners;

    public List<RunnersBean> getRunners() {
        return runners;
    }

    public void setRunners(List<RunnersBean> runners) {
        this.runners = runners;
    }

    public static class RunnersBean extends BaseModel{
        /**
         * winValue : 2400.00
         * lossValue : 0
         * selectionId : 7337
         */

        private String winValue;
        private String lossValue;
        private String selectionId;

        public String getSelectionId() {
            return getValidString(selectionId);
        }

        public void setSelectionId(String selectionId) {
            this.selectionId = selectionId;
        }

        public String getWinValue () {
            String value = getValidString(winValue);
            return value.isEmpty() ? "0.0" : value;
        }

        public void setWinValue (String winValue) {
            this.winValue = winValue;
        }

        public String getLossValue () {
            String value = getValidString(lossValue);
            return value.isEmpty() ? "0.0" : value;
        }

        public void setLossValue (String lossValue) {
            this.lossValue = lossValue;
        }

    }
}
