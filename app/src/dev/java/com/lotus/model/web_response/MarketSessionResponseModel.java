package com.lotus.model.web_response;

import com.lotus.model.MatchBetFairSessionModel;
import com.lotus.model.OddsByMarketIdsModel;
import com.models.BaseModel;

import java.util.List;

public class MarketSessionResponseModel extends BaseModel {

    List<OddsByMarketIdsModel> data;
    List<MatchBetFairSessionModel> session;

    public List<OddsByMarketIdsModel> getData () {
        return data;
    }

    public void setData (List<OddsByMarketIdsModel> data) {
        this.data = data;
    }

    public List<MatchBetFairSessionModel> getSession () {
        return session;
    }

    public void setSession (List<MatchBetFairSessionModel> session) {
        this.session = session;
    }
}
