package com.lotus.model;

import com.models.BaseModel;

public class PLByMatchIdModel extends BaseModel {

    private String is_deleted;
    private String mstruserid;
    private String usetype;
    private String UserId;
    private String bet_deleted;
    private String EventName;
    private String MarketName;
    private String PnL;
    private String Comm;
    private String CreatedOn;
    private String SrNo;
    private String matchId;
    private String MarketId;
    private String fancyId;

    public String getIs_deleted() {
        return getValidString(is_deleted);
    }

    public void setIs_deleted(String is_deleted) {
        this.is_deleted = is_deleted;
    }

    public String getMstruserid() {
        return getValidString(mstruserid);
    }

    public void setMstruserid(String mstruserid) {
        this.mstruserid = mstruserid;
    }

    public String getUsetype() {
        return getValidString(usetype);
    }

    public void setUsetype(String usetype) {
        this.usetype = usetype;
    }

    public String getUserId() {
        return getValidString(UserId);
    }

    public void setUserId(String UserId) {
        this.UserId = UserId;
    }

    public String getBet_deleted() {
        return getValidString(bet_deleted);
    }

    public void setBet_deleted(String bet_deleted) {
        this.bet_deleted = bet_deleted;
    }

    public String getEventName() {
        return getValidString(EventName);
    }

    public void setEventName(String EventName) {
        this.EventName = EventName;
    }

    public String getMarketName() {
        return getValidString(MarketName);
    }

    public void setMarketName(String MarketName) {
        this.MarketName = MarketName;
    }

    public String getPnL() {
        return getValidStringForBal(PnL);
    }

    public void setPnL(String PnL) {
        this.PnL = PnL;
    }

    public String getComm() {
        return getValidStringForBal(Comm);
    }

    public void setComm(String Comm) {
        this.Comm = Comm;
    }

    public String getCreatedOn() {
        return getValidString(CreatedOn);
    }

    public void setCreatedOn(String CreatedOn) {
        this.CreatedOn = CreatedOn;
    }

    public String getSrNo() {
        return getValidString(SrNo);
    }

    public void setSrNo(String SrNo) {
        this.SrNo = SrNo;
    }

    public String getMatchId() {
        return getValidString(matchId);
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public String getMarketId() {
        return getValidString(MarketId);
    }

    public void setMarketId(String MarketId) {
        this.MarketId = MarketId;
    }

    public String getFancyId() {
        return getValidString(fancyId);
    }

    public void setFancyId(String fancyId) {
        this.fancyId = fancyId;
    }


}
