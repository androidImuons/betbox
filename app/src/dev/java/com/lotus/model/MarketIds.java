package com.lotus.model;

import com.models.BaseModel;

public class MarketIds extends BaseModel {

    private long SportId;
    private String marketids;

    public long getSportId() {
        return SportId;
    }

    public void setSportId(long sportId) {
        SportId = sportId;
    }

    public String getMarketids() {
        return getValidString(marketids);
    }

    public void setMarketids(String marketids) {
        this.marketids = marketids;
    }
}
