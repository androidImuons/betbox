package com.lotus.model.web_response;


import com.lotus.model.SportModel;
import com.medy.retrofitwrapper.WebServiceBaseResponseModel;

import java.util.List;

/**
 * @author Sunil kumar Yadav
 * @Since 21/5/18
 */
public class FavouriteMatchListResponseModel extends WebServiceBaseResponseModel {

    private List<SportModel> data;
    private List<String> match_stack;
    private List<String> session_stack;
    private List<String> one_click_stack;
    private String market_ids;

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

    public String getMarket_ids() {
        return market_ids;
    }

    public void setMarket_ids(String market_ids) {
        this.market_ids = market_ids;
    }

    public SportModel getUserMatchListBySportId(String sportId) {
        if (data == null) return null;
        for (SportModel datum : data) {
            if (String.valueOf(datum.getSportId()).equals(sportId)) {
                return datum;
            }
        }

        return null;
    }


}
