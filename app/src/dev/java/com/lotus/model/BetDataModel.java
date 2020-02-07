package com.lotus.model;

import com.models.BaseModel;

import java.util.List;

public class BetDataModel extends BaseModel {

    private String result;
    private List<BetUserData> betUserData;
    private List<BetUserData> fancyUserData;

    public String getResult() {
        return getValidString(result);
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<BetUserData> getBetUserData() {
        return betUserData;
    }

    public void setBetUserData(List<BetUserData> betUserData) {
        this.betUserData = betUserData;
    }

    public List<BetUserData> getFancyUserData() {
        return fancyUserData;
    }

    public void setFancyUserData(List<BetUserData> fancyUserData) {
        this.fancyUserData = fancyUserData;
    }

    public static class BetUserData extends BaseModel {
        /**
         * userName : android_sunil
         * ParantName : dealer
         * selectionName : West Indies
         * Odds : 20.00
         * Stack : 1000
         * isBack : 0
         * MstDate : 2018-10-12 16:29:30
         * IsMatched : 1
         * MarketId : 1.149177099
         * SelectionId : 235
         * MatchId : 28941949
         * MstCode : 1053
         * P_L : 19000.00
         * UserId : 463
         * SrNo : 1
         */

        private String userName;
        private String ParantName;
        private String selectionName;
        private String Odds;
        private String Stack;
        private String isBack;
        private String MstDate;
        private String IsMatched;
        private String MarketId;
        private String SelectionId;
        private String MatchId;
        private String MstCode;
        private String P_L;
        private String UserId;
        private String SrNo;

        public String getUserName() {
            return getValidString(userName);
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getParantName() {
            return getValidString(ParantName);
        }

        public void setParantName(String ParantName) {
            this.ParantName = ParantName;
        }

        public String getSelectionName() {
            return getValidString(selectionName);
        }

        public void setSelectionName(String selectionName) {
            this.selectionName = selectionName;
        }

        public String getOdds() {
            return getValidString(Odds);
        }

        public void setOdds(String Odds) {
            this.Odds = Odds;
        }

        public String getStack() {
            return getValidString(Stack);
        }

        public void setStack(String Stack) {
            this.Stack = Stack;
        }

        public String getIsBack() {
            return getValidString(isBack);
        }

        public void setIsBack(String isBack) {
            this.isBack = isBack;
        }

        public String getMstDate() {
            return getValidString(MstDate);
        }

        public void setMstDate(String MstDate) {
            this.MstDate = MstDate;
        }

        public String getIsMatched() {
            return getValidString(IsMatched);
        }

        public void setIsMatched(String IsMatched) {
            this.IsMatched = IsMatched;
        }

        public String getMarketId() {
            return getValidString(MarketId);
        }

        public void setMarketId(String MarketId) {
            this.MarketId = MarketId;
        }

        public String getSelectionId() {
            return getValidString(SelectionId);
        }

        public void setSelectionId(String SelectionId) {
            this.SelectionId = SelectionId;
        }

        public String getMatchId() {
            return getValidString(MatchId);
        }

        public void setMatchId(String MatchId) {
            this.MatchId = MatchId;
        }

        public String getMstCode() {
            return getValidString(MstCode);
        }

        public void setMstCode(String MstCode) {
            this.MstCode = MstCode;
        }

        public String getP_L() {
            return getValidString(P_L);
        }

        public void setP_L(String P_L) {
            this.P_L = P_L;
        }

        public String getUserId() {
            return getValidString(UserId);
        }

        public void setUserId(String UserId) {
            this.UserId = UserId;
        }

        public String getSrNo() {
            return getValidString(SrNo);
        }

        public void setSrNo(String SrNo) {
            this.SrNo = SrNo;
        }

        public boolean checkContentSame(BetUserData newData) {

            if (!this.getSelectionName().equals(newData.getSelectionName())) {
                return false;
            }
            if (!this.getMstDate().equals(newData.getMstDate())) {
                return false;
            }
            if (!this.getOdds().equals(newData.getOdds())) {
                return false;
            }
            if (!this.getStack().equals(newData.getStack())) {
                return false;
            }
            if (!this.getP_L().equals(newData.getP_L())) {
                return false;
            }

            return true;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj != null) {
                if (obj instanceof BetUserData) {
                    return ((BetUserData) obj).getMstCode().equals(getMstCode());
                }

            }
            return false;
        }
    }
}
