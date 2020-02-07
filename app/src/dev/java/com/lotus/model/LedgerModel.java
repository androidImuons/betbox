package com.lotus.model;

import com.models.BaseModel;

public class LedgerModel extends BaseModel {

    private String Credit;
    private String Debit;
    private String MatchId;
    private String MarketId;
    private String EDate;
    private String narration;
    private String TypeID;
    private String UserID;
    private String ChildID;
    private String Balance;
    private String Mstcode;
    private String MstCode;
    private String CrDr;
    private String oppAcID;
    private String SrNo;

    public double getCredit() {
        return Double.parseDouble(getValidStringForBal(Credit));
    }
    public String getCreditText() {
        return String.valueOf(getCredit());
    }


    public void setCredit(String Credit) {
        this.Credit = Credit;
    }

    public double getDebit() {
        return Double.parseDouble(getValidStringForBal(Debit));
    }

    public String getDebitText() {
        return String.valueOf(getDebit());
    }


    public void setDebit(String Debit) {
        this.Debit = Debit;
    }

    public String getMatchId() {
        return getValidString(MatchId);
    }

    public void setMatchId(String MatchId) {
        this.MatchId = MatchId;
    }

    public String getMarketId() {
        return getValidString(MarketId);
    }

    public void setMarketId(String MarketId) {
        this.MarketId = MarketId;
    }

    public String getEDate() {
        return getValidString(EDate);
    }

    public void setEDate(String EDate) {
        this.EDate = EDate;
    }

    public String getNarration() {
        return getValidString(narration);
    }

    public void setNarration(String narration) {
        this.narration = narration;
    }

    public String getTypeID() {
        return getValidString(TypeID);
    }

    public void setTypeID(String TypeID) {
        this.TypeID = TypeID;
    }

    public String getUserID() {
        return getValidString(UserID);
    }

    public void setUserID(String UserID) {
        this.UserID = UserID;
    }

    public String getChildID() {
        return getValidString(ChildID);
    }

    public void setChildID(String ChildID) {
        this.ChildID = ChildID;
    }

    public String getBalance() {
        return getValidStringForBal(Balance);
    }

    public void setBalance(String Balance) {
        this.Balance = Balance;
    }

    public String getMstcode() {
        return getValidString(Mstcode);
    }

    public void setMstcode(String Mstcode) {
        this.Mstcode = Mstcode;
    }

    public String getCrDr() {
        return getValidString(CrDr);
    }

    public void setCrDr(String CrDr) {
        this.CrDr = CrDr;
    }

    public String getOppAcID() {
        return getValidString(oppAcID);
    }

    public void setOppAcID(String oppAcID) {
        this.oppAcID = oppAcID;
    }

    public String getSrNo() {
        return getValidString(SrNo);
    }

    public void setSrNo(String SrNo) {
        this.SrNo = SrNo;
    }

    public String getMstCode() {
        return MstCode;
    }

    public void setMstCode(String mstCode) {
        MstCode = mstCode;
    }
}
