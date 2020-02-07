package com.lotus.model;

import com.models.BaseModel;

public class OnePageReportModel extends BaseModel {

    private String mstcode;
    private String Description;
    private String selectionName;
    private String Type;
    private String Odds;
    private String Stack;
    private String MstDate;
    private String P_L;
    private String Profit;
    private String Liability;
    private String STATUS;
    private String UserNm;
    private String DealerNm;
    private String SrNo;

    public String getMstcode() {
        return getValidString(mstcode);
    }

    public void setMstcode(String mstcode) {
        this.mstcode = mstcode;
    }

    public String getDescription() {
        return getValidString(Description);

    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public String getSelectionName() {
        return getValidString(selectionName);

    }

    public void setSelectionName(String selectionName) {
        this.selectionName = selectionName;
    }

    public String getType() {
        return getValidString(Type);
    }

    public void setType(String Type) {
        this.Type = Type;
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

    public String getMstDate() {
        return getValidString(MstDate);
    }

    public void setMstDate(String MstDate) {
        this.MstDate = MstDate;
    }

    public String getP_L() {
        return getValidStringForBal(P_L);
    }

    public void setP_L(String P_L) {
        this.P_L = P_L;
    }

    public String getProfit() {
        return getValidStringForBal(Profit);
    }

    public void setProfit(String Profit) {
        this.Profit = Profit;
    }

    public String getLiability() {
        return getValidStringForBal(Liability);
    }

    public void setLiability(String Liability) {
        this.Liability = Liability;
    }

    public String getSTATUS() {
        return getValidString(STATUS);
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String getUserNm() {
        return getValidString(UserNm);
    }

    public void setUserNm(String UserNm) {
        this.UserNm = UserNm;
    }

    public String getDealerNm() {
        return getValidString(DealerNm);
    }

    public void setDealerNm(String DealerNm) {
        this.DealerNm = DealerNm;
    }

    public String getSrNo() {
        return getValidString(SrNo);
    }

    public void setSrNo(String SrNo) {
        this.SrNo = SrNo;
    }
}
