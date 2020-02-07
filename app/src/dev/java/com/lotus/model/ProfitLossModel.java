package com.lotus.model;

import com.lotus.R;
import com.models.BaseModel;

public class ProfitLossModel extends BaseModel {

    private String id;
    private String sport_id;
    private String mstruserid;
    private String usetype;
    private String UserId;
    private String EventName;
    private String PnL;
    private String Comm;
    private String matchId;
    private String settle_date;

    public String getId() {
        return getValidString(id);
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSport_id() {
        return getValidString(sport_id);
    }

    public void setSport_id(String sport_id) {
        this.sport_id = sport_id;
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

    public String getEventName() {
        return getValidString(EventName);
    }

    public void setEventName(String EventName) {
        this.EventName = EventName;
    }

    public String getPnL() {
        return getValidStringForBal(PnL);
    }

    public double getPlDouble(){
        if(isValidString(getPnL())){
            try{
                return Double.parseDouble(getPnL());
            }catch (NumberFormatException e){

            }
        }
        return 0.0d;
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

    public String getMatchId() {
        return getValidString(matchId);
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public String getSettle_date() {
        return getValidString(settle_date);
    }

    public void setSettle_date(String settle_date) {
        this.settle_date = settle_date;
    }

    public int getAmountTextColor(double amount) {
        if (amount > 0) {
            return R.color.color_green;
        } else if (amount < 0)
            return R.color.color_red;
        else
            return R.color.et_text_color;
    }
}
