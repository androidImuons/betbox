package com.lotus.model.web_response;

import com.lotus.model.PLByMatchIdModel;
import com.medy.retrofitwrapper.WebServiceBaseResponseModel;

import java.util.List;

public class PLByMatchIdResponseModel extends WebServiceBaseResponseModel {


    private List<PLByMatchIdModel> userPL;

    public List<PLByMatchIdModel> getUserPL() {
        return userPL;
    }

    public void setUserPL(List<PLByMatchIdModel> userPL) {
        this.userPL = userPL;
    }
}
