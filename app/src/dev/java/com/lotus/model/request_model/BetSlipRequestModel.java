package com.lotus.model.request_model;

import com.lotus.model.BetSlipModel;
import com.lotus.ui.MyApplication;
import com.models.BaseModel;
import com.models.DeviceInfoModal;

public class BetSlipRequestModel extends BaseModel {

    //common values for all bets
    public long userId;
    public long type;

    public String matchdate;
    public String SportId;
    public String deviceInfo;
    public String deviceInformation;
    public String unique_id;
    public String is_session_fancy;
    public String matchId;
    public String MarketId;
    public int isback;
    public String stake;
    public String priceVal;
    public boolean inplay;

    //values for market bet
    public String selectionId;
    public String p_l;
    public String isMatched;
    public String placeName;
    public String MatchName;
    public int ApiVal = 0;

    //values for session bet
    public String ind_fancy_selection_id;
    public String FancyID;
    public String FancyId; //mFancy id
    public int OddValue;
    public String HeadName;
    public String TypeID;
    public String SessInptNo;
    public String SessInptYes;
    public String NoValume;
    public String YesValume;
    public String pointDiff;

    public BetSlipRequestModel(BetSlipModel betSlipModel) {
        this.matchdate = betSlipModel.getMatchdate();
        this.SportId = String.valueOf(betSlipModel.getSportId());
        this.deviceInfo = new DeviceInfoModal(MyApplication.getInstance()).toString();
        this.deviceInformation = this.deviceInfo;
        this.unique_id = betSlipModel.getUniqeId();
        this.is_session_fancy = betSlipModel.isSession() ? "Y" : "N";
        this.matchId = betSlipModel.getMatchid();
        this.MarketId = betSlipModel.getMarketid();
        this.isback = betSlipModel.isBack() ? 0 : 1;
        this.stake = betSlipModel.getCurrentStack();
        this.priceVal = betSlipModel.getCurrentOdds();

        if (betSlipModel.isSession()) {
            this.ind_fancy_selection_id = betSlipModel.getIndian_fancy_selection_id();
            this.FancyID = betSlipModel.getFancyID();
            this.FancyId = betSlipModel.getFancyId();
            this.OddValue = betSlipModel.isBack() ? 0 : 1;
            this.HeadName = betSlipModel.getSelectionName();
            this.TypeID = betSlipModel.getTypeID();
            this.SessInptNo = betSlipModel.getSessInptNo();
            this.SessInptYes = betSlipModel.getSessInptYes();
            this.NoValume = betSlipModel.getNoValume();
            this.YesValume = betSlipModel.getYesValume();
            this.pointDiff = betSlipModel.getPointDiff();
        } else {
            this.selectionId = betSlipModel.getSelectionId();
            this.p_l = betSlipModel.getCurrentPL();
            this.placeName = betSlipModel.getSelectionName();
            this.MatchName = betSlipModel.getMatchName();
        }

    }
}
