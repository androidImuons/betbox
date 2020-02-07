package com.lotus.model;

import androidx.annotation.Nullable;

import com.models.BaseModel;


public class BetSlipModel extends BaseModel {

    private boolean session = false;
    private String selectionId;
    private String selectionName;
    private String price;
    private String size;
    private String type;
    private String matchName;
    private String series_name;
    private String matchid;
    private String marketid;
    private String matchdate;
    private long SportId;

    private String indian_fancy_selection_id;
    private String FancyID;
    private String FancyId;

    public String TypeID;
    public String SessInptNo;
    public String SessInptYes;
    public String NoValume;
    public String YesValume;
    public String pointDiff;


    private String currentOdds;
    private String currentStack;
    private String currentPL;
    private String errorMsg;

    public String getUniqeId() {
        if (isSession()) {
            return getFancyID() + "_" + getMatchid() + "_" + getType();
        } else {
            return getSelectionId() + "_" + getMatchid() + "_" + getType();
        }
    }

    public boolean isSession() {
        return session;
    }

    public void setSession(boolean session) {
        this.session = session;
    }

    public void setCurrentOdds(String currentOdds) {
        this.currentOdds = currentOdds;
    }

    public String getCurrentOdds() {
        return getValidStringForBal(currentOdds);
    }

    public String getCurrentPL() {
        return getValidString(currentPL);
    }

    public void setCurrentPL(String currentPL) {
        this.currentPL = currentPL;
    }

    public String getCurrentStack() {
        return getValidString(currentStack);
    }

    public void setCurrentStack(String currentStack) {
        this.currentStack = currentStack;
    }

    public String getSelectionId() {
        return getValidString(selectionId);
    }

    public void setSelectionId(String selectionId) {
        this.selectionId = selectionId;
    }

    public String getSelectionName() {
        return getValidString(selectionName);
    }

    public void setSelectionName(String selectionName) {
        this.selectionName = selectionName;
    }

    public String getPrice() {
        return getValidStringForBal(price);
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSize() {
        return getValidStringForBal(size);
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getType() {
        return getValidStringForBal(type);
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMatchName() {
        return getValidString(matchName);
    }

    public void setMatchName(String matchName) {
        this.matchName = matchName;
    }

    public String getSeries_name() {
        return getValidString(series_name);
    }

    public void setSeries_name(String series_name) {
        this.series_name = series_name;
    }

    public String getMatchid() {
        return getValidString(matchid);
    }

    public void setMatchid(String matchid) {
        this.matchid = matchid;
    }

    public String getMarketid() {
        return getValidString(marketid);
    }

    public void setMarketid(String marketid) {
        this.marketid = marketid;
    }

    public String getMatchdate() {
        return getValidString(matchdate);
    }

    public void setMatchdate(String matchdate) {
        this.matchdate = matchdate;
    }

    public long getSportId() {
        return SportId;
    }

    public void setSportId(long sportId) {
        SportId = sportId;
    }


    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj != null && obj instanceof BetSlipModel)
            return (((BetSlipModel) obj).getSelectionId().equals(getSelectionId())
                    && ((BetSlipModel) obj).getMarketid().equals(getMarketid())
                    && ((BetSlipModel) obj).getType().equals(getType()));

        return false;
    }

    public boolean isBack() {
        return getType().equals("Back");
    }

    public String getLiability() {
        if (isSession()) {
            return getCurrentStack();
        }
        if (isBack()) {
            return getCurrentStack();
        } else {
            return getCurrentPL();
        }
    }


    public String getIndian_fancy_selection_id() {
        return indian_fancy_selection_id;
    }

    public void setIndian_fancy_selection_id(String indian_fancy_selection_id) {
        this.indian_fancy_selection_id = indian_fancy_selection_id;
    }

    public String getFancyID() {
        return FancyID;
    }

    public void setFancyID(String fancyID) {
        FancyID = fancyID;
    }

    public String getFancyId() {
        return FancyId;
    }

    public void setFancyId(String fancyId) {
        FancyId = fancyId;
    }

    public String getTypeID() {
        return TypeID;
    }

    public void setTypeID(String typeID) {
        TypeID = typeID;
    }

    public String getSessInptNo() {
        return SessInptNo;
    }

    public void setSessInptNo(String sessInptNo) {
        SessInptNo = sessInptNo;
    }

    public String getSessInptYes() {
        return SessInptYes;
    }

    public void setSessInptYes(String sessInptYes) {
        SessInptYes = sessInptYes;
    }

    public String getNoValume() {
        return NoValume;
    }

    public void setNoValume(String noValume) {
        NoValume = noValume;
    }

    public String getYesValume() {
        return YesValume;
    }

    public void setYesValume(String yesValume) {
        YesValume = yesValume;
    }

    public String getPointDiff() {
        return pointDiff;
    }

    public void setPointDiff(String pointDiff) {
        this.pointDiff = pointDiff;
    }

    public double getFormattedOdds() {
        try {
            return Double.parseDouble(getCurrentOdds());
        } catch (NumberFormatException ignore) {

        }
        return 0.00;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getErrorMsg() {
        return getValidString(errorMsg);
    }
}
