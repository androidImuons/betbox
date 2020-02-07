package com.lotus.model;


import androidx.annotation.Nullable;

import com.models.BaseModel;

import java.util.List;


public class IndianSessionModel extends BaseModel {

    private String ID;
    private String MatchID;
    private String HeadName;
    private String TypeID;
    private String Remarks;
    private String date;
    private String time;
    private String SessInptYes;
    private String SessInptNo;
    private String fancyRange;
    private String PlayerId;
    private int active;
    private String result;
    private String upDwnBack;
    private String upDwnLay;
    private String MFancyID;
    private String SprtId;
    private String rateDiff;
    private String pointDiff;
    private String MaxStake;
    private String NoValume;
    private String YesValume;
    private String DisplayMsg;
    private String RateChange;
    private String upDwnLimit;
    private String HelperID;
    private String is_indian_fancy;
    private String fancy_mode;
    private String ind_fancy_selection_id;
    private String max_session_bet_liability;
    private String max_session_liability;
    private String FncyId;
    private String IsPlay;
    private String IsPlayIcon;
    private String market_id;
    private List<FancyPosition> fancy_position;
    private int max_exposure;


    @Override
    public boolean equals(@Nullable Object obj) {

        if (obj != null) {
            if (obj instanceof IndianSessionModel) {
                return ((IndianSessionModel) obj).getID().equals(getID());
            }
        }

        return false;
    }


    public String getID() {
        return getValidString(ID);
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getMatchID() {
        return getValidString(MatchID);
    }

    public void setMatchID(String MatchID) {
        this.MatchID = MatchID;
    }

    public String getHeadName() {
        return getValidString(HeadName);
    }

    public void setHeadName(String HeadName) {
        this.HeadName = HeadName;
    }

    public String getTypeID() {
        return getValidString(TypeID);
    }

    public void setTypeID(String TypeID) {
        this.TypeID = TypeID;
    }

    public String getRemarks() {
        return getValidString(Remarks);
    }

    public void setRemarks(String Remarks) {
        this.Remarks = Remarks;
    }

    public String getDate() {
        return getValidString(date);
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return getValidString(time);
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSessInptYes() {
        return getValidString(SessInptYes);
    }

    public void setSessInptYes(String SessInptYes) {
        this.SessInptYes = SessInptYes;
    }

    public String getSessInptNo() {
        return getValidString(SessInptNo);
    }

    public void setSessInptNo(String SessInptNo) {
        this.SessInptNo = SessInptNo;
    }

    public String getFancyRange() {
        return getValidString(fancyRange);
    }

    public void setFancyRange(String fancyRange) {
        this.fancyRange = fancyRange;
    }

    public String getPlayerId() {
        return getValidString(PlayerId);
    }

    public void setPlayerId(String PlayerId) {
        this.PlayerId = PlayerId;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public String getResult() {
        return getValidString(result);
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getUpDwnBack() {
        return getValidString(upDwnBack);
    }

    public void setUpDwnBack(String upDwnBack) {
        this.upDwnBack = upDwnBack;
    }

    public String getUpDwnLay() {
        return getValidString(upDwnLay);
    }

    public void setUpDwnLay(String upDwnLay) {
        this.upDwnLay = upDwnLay;
    }

    public String getMFancyID() {
        return getValidString(MFancyID);
    }

    public void setMFancyID(String MFancyID) {
        this.MFancyID = MFancyID;
    }

    public String getSprtId() {
        return getValidString(SprtId);
    }

    public void setSprtId(String SprtId) {
        this.SprtId = SprtId;
    }

    public String getRateDiff() {
        return getValidString(rateDiff);
    }

    public void setRateDiff(String rateDiff) {
        this.rateDiff = rateDiff;
    }

    public String getPointDiff() {
        return getValidString(pointDiff);
    }

    public void setPointDiff(String pointDiff) {
        this.pointDiff = pointDiff;
    }

    public String getMaxStake() {
        return getValidString(MaxStake);
    }

    public void setMaxStake(String MaxStake) {
        this.MaxStake = MaxStake;
    }

    public String getNoValume() {
        return getValidString(NoValume);
    }

    public void setNoValume(String NoValume) {
        this.NoValume = NoValume;
    }

    public String getYesValume() {
        return getValidString(YesValume);
    }

    public void setYesValume(String YesValume) {
        this.YesValume = YesValume;
    }

    public String getDisplayMsg() {
        return getValidString(DisplayMsg);
    }

    public void setDisplayMsg(String DisplayMsg) {
        this.DisplayMsg = DisplayMsg;
    }

    public String getRateChange() {
        return getValidString(RateChange);
    }

    public void setRateChange(String RateChange) {
        this.RateChange = RateChange;
    }

    public String getUpDwnLimit() {
        return getValidString(upDwnLimit);
    }

    public void setUpDwnLimit(String upDwnLimit) {
        this.upDwnLimit = upDwnLimit;
    }

    public String getHelperID() {
        return getValidString(HelperID);
    }

    public void setHelperID(String HelperID) {
        this.HelperID = HelperID;
    }

    public String getIs_indian_fancy() {
        return getValidString(is_indian_fancy);
    }

    public void setIs_indian_fancy(String is_indian_fancy) {
        this.is_indian_fancy = is_indian_fancy;
    }

    public boolean isIndianFancy() {
        return getIs_indian_fancy().equals("1");
    }


    public String getFancy_mode() {
        return getValidString(fancy_mode);
    }

    public void setFancy_mode(String fancy_mode) {
        this.fancy_mode = fancy_mode;
    }


    public boolean isAutoMaticFancy() {
        return getFancy_mode().equals("A");
    }

    public String getInd_fancy_selection_id() {
        return getValidString(ind_fancy_selection_id);
    }

    public void setInd_fancy_selection_id(String ind_fancy_selection_id) {
        this.ind_fancy_selection_id = ind_fancy_selection_id;
    }

    public String getMax_session_bet_liability() {
        return getValidString(max_session_bet_liability);
    }

    public void setMax_session_bet_liability(String max_session_bet_liability) {
        this.max_session_bet_liability = max_session_bet_liability;
    }

    public String getMax_session_liability() {
        return getValidString(max_session_liability);
    }

    public void setMax_session_liability(String max_session_liability) {
        this.max_session_liability = max_session_liability;
    }

    public String getFncyId() {
        return getValidString(FncyId);
    }

    public void setFncyId(String FncyId) {
        this.FncyId = FncyId;
    }

    public String getIsPlay() {
        return getValidString(IsPlay);
    }

    public void setIsPlay(String IsPlay) {
        this.IsPlay = IsPlay;
    }

    public String getIsPlayIcon() {
        return getValidString(IsPlayIcon);
    }

    public void setIsPlayIcon(String IsPlayIcon) {
        this.IsPlayIcon = IsPlayIcon;
    }

    public String getMarket_id() {
        return getValidString(market_id);
    }

    public void setMarket_id(String market_id) {
        this.market_id = market_id;
    }

    public List<FancyPosition> getFancy_position() {
        return fancy_position;
    }

    public void setFancy_position(List<FancyPosition> fancy_position) {
        this.fancy_position = fancy_position;
    }

    public int getMax_exposure() {
        return max_exposure;
    }

    public void setMax_exposure(int max_exposure) {
        this.max_exposure = max_exposure;
    }


    public boolean checkContentSame(IndianSessionModel indianSessionModel) {

        if (!this.getID().equals(indianSessionModel.getID())) {
            return false;
        }

        if (!this.getMatchID().equals(indianSessionModel.getMatchID())) {
            return false;
        }

        if (!this.getHeadName().equals(indianSessionModel.getHeadName())) {
            return false;
        }

        if (!this.getTypeID().equals(indianSessionModel.getTypeID())) {
            return false;
        }

        if (!this.getRemarks().equals(indianSessionModel.getRemarks())) {
            return false;
        }

        if (!this.getDate().equals(indianSessionModel.getDate())) {
            return false;
        }
        if (!this.getTime().equals(indianSessionModel.getTime())) {
            return false;
        }

        if (!this.getFancyRange().equals(indianSessionModel.getFancyRange())) {
            return false;
        }
        if (!this.getPlayerId().equals(indianSessionModel.getPlayerId())) {
            return false;
        }


        if (!this.getResult().equals(indianSessionModel.getResult())) {
            return false;
        }

        if (!this.getUpDwnBack().equals(indianSessionModel.getUpDwnBack())) {
            return false;
        }

        if (!this.getUpDwnLay().equals(indianSessionModel.getUpDwnLay())) {
            return false;
        }
        if (!this.getMFancyID().equals(indianSessionModel.getMFancyID())) {
            return false;
        }
        if (!this.getSprtId().equals(indianSessionModel.getSprtId())) {
            return false;
        }
        if (!this.getRateDiff().equals(indianSessionModel.getRateDiff())) {
            return false;
        }
        if (!this.getPointDiff().equals(indianSessionModel.getPointDiff())) {
            return false;
        }
        if (!this.getMaxStake().equals(indianSessionModel.getMaxStake())) {
            return false;
        }
        if (!this.getRateChange().equals(indianSessionModel.getRateChange())) {
            return false;
        }
        if (!this.getUpDwnLimit().equals(indianSessionModel.getUpDwnLimit())) {
            return false;
        }
        if (!this.getHelperID().equals(indianSessionModel.getHelperID())) {
            return false;
        }
        if (!this.getIs_indian_fancy().equals(indianSessionModel.getIs_indian_fancy())) {
            return false;
        }
        if (!this.getFancy_mode().equals(indianSessionModel.getFancy_mode())) {
            return false;
        }
        if (!this.getInd_fancy_selection_id().equals(indianSessionModel.getInd_fancy_selection_id())) {
            return false;
        }
        if (!this.getFncyId().equals(indianSessionModel.getFncyId())) {
            return false;
        }
        if (!this.getIsPlay().equals(indianSessionModel.getIsPlay())) {
            return false;
        }
        if (!this.getIsPlayIcon().equals(indianSessionModel.getIsPlayIcon())) {
            return false;
        }
        if (!this.getMarket_id().equals(indianSessionModel.getMarket_id())) {
            return false;
        }
        if (this.getMax_exposure() != indianSessionModel.getMax_exposure()) {
            return false;
        }
        if ((this.fancy_position == null || this.fancy_position.size() == 0) &&
                (indianSessionModel.fancy_position != null && indianSessionModel.fancy_position.size() > 0)) {
            return false;
        }

        if ((this.fancy_position != null && this.fancy_position.size() > 0) &&
                (indianSessionModel.fancy_position == null || indianSessionModel.fancy_position.size() == 0)) {
            return false;
        }

        if (this.fancy_position != null && indianSessionModel.fancy_position != null) {
            if (!this.fancy_position.equals(indianSessionModel.fancy_position)) {
                return false;
            }
        }

        if (!isIndianFancy() || !isAutoMaticFancy()) {
            if (this.getActive() != indianSessionModel.getActive()) {
                return false;
            }

            if (!this.getSessInptYes().equals(indianSessionModel.getSessInptYes())) {
                return false;
            }
            if (!this.getSessInptNo().equals(indianSessionModel.getSessInptNo())) {
                return false;
            }
            if (!this.getNoValume().equals(indianSessionModel.getNoValume())) {
                return false;
            }
            if (!this.getYesValume().equals(indianSessionModel.getYesValume())) {
                return false;
            }
            if (!this.getDisplayMsg().equals(indianSessionModel.getDisplayMsg())) {
                return false;
            }
        }

        return true;
    }

    public boolean checkContentSame2(IndianSessionModel indianSessionModel) {

        if (this.getActive() != indianSessionModel.getActive()) {
            return false;
        }

        if (!this.getSessInptYes().equals(indianSessionModel.getSessInptYes())) {
            return false;
        }
        if (!this.getSessInptNo().equals(indianSessionModel.getSessInptNo())) {
            return false;
        }
        if (!this.getNoValume().equals(indianSessionModel.getNoValume())) {
            return false;
        }
        if (!this.getYesValume().equals(indianSessionModel.getYesValume())) {
            return false;
        }
        if (!this.getDisplayMsg().equals(indianSessionModel.getDisplayMsg())) {
            return false;
        }

        return true;
    }

    public IndianSessionModel copyOf(IndianSessionModel newData) {
        IndianSessionModel indianSessionModel = new IndianSessionModel();
        indianSessionModel.setID(newData.getID());
        indianSessionModel.setMatchID(newData.getMatchID());
        indianSessionModel.setHeadName(newData.getHeadName());
        indianSessionModel.setTypeID(newData.getTypeID());
        indianSessionModel.setRemarks(newData.getRemarks());
        indianSessionModel.setDate(newData.getDate());
        indianSessionModel.setTime(newData.getTime());
        indianSessionModel.setSessInptYes(newData.getSessInptYes());
        indianSessionModel.setSessInptNo(newData.getSessInptNo());
        indianSessionModel.setFancyRange(newData.getFancyRange());
        indianSessionModel.setPlayerId(newData.getPlayerId());
        indianSessionModel.setActive(newData.getActive());
        indianSessionModel.setResult(newData.getResult());
        indianSessionModel.setUpDwnBack(newData.getUpDwnBack());
        indianSessionModel.setUpDwnLay(newData.getUpDwnLay());
        indianSessionModel.setMFancyID(newData.getMFancyID());
        indianSessionModel.setSprtId(newData.getSprtId());
        indianSessionModel.setRateDiff(newData.getRateDiff());
        indianSessionModel.setPointDiff(newData.getPointDiff());
        indianSessionModel.setMaxStake(newData.getMaxStake());
        indianSessionModel.setNoValume(newData.getNoValume());
        indianSessionModel.setYesValume(newData.getYesValume());
        indianSessionModel.setDisplayMsg(newData.getDisplayMsg());
        indianSessionModel.setRateChange(newData.getRateChange());
        indianSessionModel.setUpDwnLimit(newData.getUpDwnLimit());
        indianSessionModel.setHelperID(newData.getHelperID());
        indianSessionModel.setIs_indian_fancy(newData.getIs_indian_fancy());
        indianSessionModel.setFancy_mode(newData.getFancy_mode());
        indianSessionModel.setInd_fancy_selection_id(newData.getInd_fancy_selection_id());
        indianSessionModel.setMax_session_bet_liability(newData.getMax_session_bet_liability());
        indianSessionModel.setMax_session_liability(newData.getMax_session_liability());
        indianSessionModel.setFncyId(newData.getFncyId());
        indianSessionModel.setIsPlay(newData.getIsPlay());
        indianSessionModel.setIsPlayIcon(newData.getIsPlayIcon());
        indianSessionModel.setMarket_id(newData.getMarket_id());
        indianSessionModel.setFancy_position(newData.getFancy_position());
        indianSessionModel.setMax_exposure(newData.getMax_exposure());
        return indianSessionModel;
    }

}
