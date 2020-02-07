package com.lotus.model.web_response;

import com.lotus.model.MatchMarketModel;
import com.medy.retrofitwrapper.WebServiceBaseResponseModel;

import java.util.List;

public class MarketListingResponseModel extends WebServiceBaseResponseModel {

    private List<String> match_stack;
    private List<String> session_stack;
    private List<String> one_click_stack;
    private List<MatchMarketModel> data;

    public List<String> getMatch_stack () {
        return match_stack;
    }

    public void setMatch_stack (List<String> match_stack) {
        this.match_stack = match_stack;
    }

    public List<String> getSession_stack () {
        return session_stack;
    }

    public void setSession_stack (List<String> session_stack) {
        this.session_stack = session_stack;
    }

    public List<String> getOne_click_stack () {
        return one_click_stack;
    }

    public void setOne_click_stack (List<String> one_click_stack) {
        this.one_click_stack = one_click_stack;
    }

    public List<MatchMarketModel> getData () {
        return data;
    }

    public void setData (List<MatchMarketModel> data) {
        this.data = data;
    }
}
