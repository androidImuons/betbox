package com.lotus.model;

import com.models.BaseModel;

public class AccountStatementModel extends BaseModel {


    private String Sdate;
    private String mstrUserId;
    private String Chips;
    private String Narration;
    private String Credit;
    private String Debit;
    private String IsBack;
    private String isFancy;
    private String Balance;

    public String getSdate() {
        return getValidString(Sdate);
    }

    public void setSdate(String Sdate) {
        this.Sdate = Sdate;
    }

    public String getMstrUserId() {
        return getValidString(mstrUserId);
    }

    public void setMstrUserId(String mstrUserId) {
        this.mstrUserId = mstrUserId;
    }

    public String getChips() {
        return getValidString(Chips);
    }

    public void setChips(String Chips) {
        this.Chips = Chips;
    }

    public String getNarration() {
        return getValidString(Narration);
    }

    public void setNarration(String Narration) {
        this.Narration = Narration;
    }

    public String getCredit() {
        return getValidStringForBal(Credit);
    }

    public void setCredit(String Credit) {
        this.Credit = Credit;
    }

    public String getDebit() {
        return getValidStringForBal(Debit);
    }

    public void setDebit(String Debit) {
        this.Debit = Debit;
    }

    public String getIsBack() {
        return getValidString(IsBack);
    }

    public void setIsBack(String IsBack) {
        this.IsBack = IsBack;
    }

    public String getIsFancy() {
        return getValidString(isFancy);
    }

    public void setIsFancy(String isFancy) {
        this.isFancy = isFancy;
    }

    public String getBalance() {
        return getValidStringForBal(Balance);
    }

    public void setBalance(String Balance) {
        this.Balance = Balance;
    }
}
