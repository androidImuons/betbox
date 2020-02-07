package com.lotus.model.web_response;

import com.lotus.model.IndianSessionModel;
import com.medy.retrofitwrapper.WebServiceBaseResponseModel;

import java.util.List;

public class IndianSessionResponseModel extends WebServiceBaseResponseModel {

    List<IndianSessionModel> data;

    public List<IndianSessionModel> getData () {
        return data;
    }

    public void setData (List<IndianSessionModel> data) {
        this.data = data;
    }
}
