package com.lotus.model.web_response;


import com.lotus.model.MarketIds;
import com.lotus.model.SportModel;
import com.models.BaseModel;

import java.util.List;

/**
 * @author Sunil kumar Yadav
 * @Since 21/5/18
 */
public class UserMatchListResponseModel extends BaseModel {

    private List<SportModel> data;
    private List<String> match_stack;
    private List<String> session_stack;
    private List<String> one_click_stack;
    private List<MarketIds> market_ids;
    private String all_market_ids;


    public List<SportModel> getData() {
        return data;
    }

    public void setData(List<SportModel> data) {
        this.data = data;
    }

    public List<String> getMatch_stack() {
        return match_stack;
    }

    public void setMatch_stack(List<String> match_stack) {
        this.match_stack = match_stack;
    }

    public List<String> getSession_stack() {
        return session_stack;
    }

    public void setSession_stack(List<String> session_stack) {
        this.session_stack = session_stack;
    }

    public List<String> getOne_click_stack() {
        return one_click_stack;
    }

    public void setOne_click_stack(List<String> one_click_stack) {
        this.one_click_stack = one_click_stack;
    }

    public List<MarketIds> getMarket_ids() {
        return market_ids;
    }

    public void setMarket_ids(List<MarketIds> market_ids) {
        this.market_ids = market_ids;
    }

    public String getAll_market_ids() {
        return getValidString(all_market_ids);
    }

    public void setAll_market_ids(String all_market_ids) {
        this.all_market_ids = all_market_ids;
    }

}
