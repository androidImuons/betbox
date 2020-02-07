package com.lotus.model;

import com.models.BaseModel;

import java.util.List;

public class MatchBetFairSessionModel extends BaseModel {

    private String market_id;
    private ValueBean value;

    public String getMarket_id() {
        return getValidString(market_id);
    }

    public void setMarket_id(String market_id) {
        this.market_id = market_id;
    }

    public ValueBean getValue() {
        return value;
    }

    public void setValue(ValueBean value) {
        this.value = value;
    }

    public static class ValueBean {
        private List<SessionBean> session;

        public List<SessionBean> getSession() {
            return session;
        }

        public void setSession(List<SessionBean> session) {
            this.session = session;
        }

        public static class SessionBean extends BaseModel {
            /**
             * GameStatus : Ball Running
             * BackSize1 : 90
             * BackPrice1 : 122
             * RunnerName : FALL OF 1ST WKT AUS 2
             * SelectionId : 71
             * LayPrice1 : 122
             * LaySize1 : 110
             */

            private String GameStatus;
            private String BackSize1;
            private String BackPrice1;
            private String RunnerName;
            private String SelectionId;
            private String LayPrice1;
            private String LaySize1;

            public String getGameStatus() {
                return getValidString(GameStatus);
            }

            public void setGameStatus(String GameStatus) {
                this.GameStatus = GameStatus;
            }

            public String getBackSize1() {
                return getValidString(BackSize1);
            }

            public void setBackSize1(String BackSize1) {
                this.BackSize1 = BackSize1;
            }

            public String getBackPrice1() {
                return getValidString(BackPrice1);
            }

            public void setBackPrice1(String BackPrice1) {
                this.BackPrice1 = BackPrice1;
            }

            public String getRunnerName() {
                return getValidString(RunnerName);
            }

            public void setRunnerName(String RunnerName) {
                this.RunnerName = RunnerName;
            }

            public String getSelectionId() {
                return getValidString(SelectionId);
            }

            public void setSelectionId(String SelectionId) {
                this.SelectionId = SelectionId;
            }

            public String getLayPrice1() {
                return getValidString(LayPrice1);
            }

            public void setLayPrice1(String LayPrice1) {
                this.LayPrice1 = LayPrice1;
            }

            public String getLaySize1() {
                return getValidString(LaySize1);
            }

            public void setLaySize1(String LaySize1) {
                this.LaySize1 = LaySize1;
            }
        }
    }
}
