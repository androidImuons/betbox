package com.lotus.model.web_response;

import com.lotus.model.BetHistoryPLModel;
import com.medy.retrofitwrapper.WebServiceBaseResponseModel;

import java.util.List;

public class BetHistoryPLResponseModel extends WebServiceBaseResponseModel {


    private List<BetHistoryPLModel> getBetPl;

    public List<BetHistoryPLModel> getGetBetPl() {
        return getBetPl;
    }

    public void setGetBetPl(List<BetHistoryPLModel> getBetPl) {
        this.getBetPl = getBetPl;
    }
}
