package com.lotus.model;

import com.models.BaseModel;

public class InplayModel extends BaseModel {


    /**
     * volumeLimit : 1.00
     * oddsLimit : 0
     * matchName : Sasnovich v Buzarnescu
     * countryCode :
     * marketCount : 0
     * MstDate : 2018-10-15T13:16:00.000Z
     * name : Tennis
     * active : 1
     * SportID : 2
     * MstCode : 28956981
     * date : 15-10-2018
     * marketid : 1.149584838
     */

    private String volumeLimit;
    private String oddsLimit;
    private String matchName;
    private String countryCode;
    private String marketCount;
    private String MstDate;
    private String name;
    private String active;
    private String SportID;
    private String MstCode;
    private String date;
    private String marketid;

    public String getVolumeLimit() {
        return getValidString(volumeLimit);
    }

    public void setVolumeLimit(String volumeLimit) {
        this.volumeLimit = volumeLimit;
    }

    public String getOddsLimit() {
        return getValidString(oddsLimit);
    }

    public void setOddsLimit(String oddsLimit) {
        this.oddsLimit = oddsLimit;
    }

    public String getMatchName() {
        return getValidString(matchName);
    }

    public void setMatchName(String matchName) {
        this.matchName = matchName;
    }

    public String getCountryCode() {
        return getValidString(countryCode);
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getMarketCount() {
        return getValidString(marketCount);
    }

    public void setMarketCount(String marketCount) {
        this.marketCount = marketCount;
    }

    public String getMstDate() {
        return getValidString(MstDate);
    }

    public void setMstDate(String MstDate) {
        this.MstDate = MstDate;
    }

    public String getName() {
        return getValidString(name);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getActive() {
        return getValidString(active);
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getSportID() {
        return getValidString(SportID);
    }

    public void setSportID(String SportID) {
        this.SportID = SportID;
    }

    public String getMstCode() {
        return getValidString(MstCode);
    }

    public void setMstCode(String MstCode) {
        this.MstCode = MstCode;
    }

    public String getDate() {
        return getValidString(date);
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMarketid() {
        return getValidString(marketid);
    }

    public void setMarketid(String marketid) {
        this.marketid = marketid;
    }
}
