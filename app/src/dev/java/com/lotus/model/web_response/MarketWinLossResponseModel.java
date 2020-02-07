package com.lotus.model.web_response;

import com.lotus.model.MarketWinLossModel;
import com.medy.retrofitwrapper.WebServiceBaseResponseModel;

import java.util.List;

public class MarketWinLossResponseModel extends WebServiceBaseResponseModel {

    List<MarketWinLossModel> data;

    public List<MarketWinLossModel> getData () {
        return data;
    }

    public void setData (List<MarketWinLossModel> data) {
        this.data = data;
    }
}
