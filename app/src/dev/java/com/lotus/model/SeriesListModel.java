package com.lotus.model;

import com.models.BaseModel;

public class SeriesListModel extends BaseModel {

    private String seriesId;
    private String Name;
    private String region;
    private String mCount;
    private String SportID;
    private String sportname;
    private String active;
    private String is_online;

    public String getSeriesId() {
        return getValidString(seriesId);
    }

    public void setSeriesId(String seriesId) {
        this.seriesId = seriesId;
    }

    public String getName() {
        return getValidString(Name);
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getRegion() {
        return getValidString(region);
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getMCount() {
        return getValidString(mCount);
    }

    public void setMCount(String mCount) {
        this.mCount = mCount;
    }

    public String getSportID() {
        return getValidString(SportID);
    }

    public void setSportID(String SportID) {
        this.SportID = SportID;
    }

    public String getSportname() {
        return getValidString(sportname);
    }

    public void setSportname(String sportname) {
        this.sportname = sportname;
    }

    public String getActive() {
        return getValidString(active);
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getIs_online() {
        return getValidString(is_online);
    }

    public void setIs_online(String is_online) {
        this.is_online = is_online;
    }

}
