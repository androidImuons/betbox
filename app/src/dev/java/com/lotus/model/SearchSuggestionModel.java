package com.lotus.model;

import com.models.BaseModel;

/**
 * Created by Azher on 16/11/18.
 */
public class SearchSuggestionModel extends BaseModel {


    /**
     * matchName : Australia v India (1st T20)
     * matchid : 29012686
     * MstDate : 2018-11-21T07:50:00.000Z
     * SportId : 4
     */

    private String matchName;
    private String matchid;
    private String MstDate;
    private long SportId;


    public String getMatchName() {
        return getValidString(matchName);
    }

    public void setMatchName(String matchName) {
        this.matchName = matchName;
    }

    public String getMatchid() {
        return getValidString(matchid);
    }

    public void setMatchid(String matchid) {
        this.matchid = matchid;
    }

    public String getMstDate() {
        return getValidString(MstDate);
    }

    public void setMstDate(String MstDate) {
        this.MstDate = MstDate;
    }

    public long getSportId() {
        return SportId;
    }

    public void setSportId(long SportId) {
        this.SportId = SportId;
    }

    public MatchDetailModel getMatchListModel() {
        MatchDetailModel matchDetailModel = new MatchDetailModel();
        matchDetailModel.setMatchid(getMatchid());
        matchDetailModel.setMatchName(getMatchName());
        matchDetailModel.setSportId(getSportId());
        matchDetailModel.setMatchdate(getMstDate());
        return matchDetailModel;
    }
}
