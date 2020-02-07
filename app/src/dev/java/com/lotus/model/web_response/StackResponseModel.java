package com.lotus.model.web_response;

import com.lotus.appBase.AppBaseWebServiceBaseResponseModel;
import com.lotus.model.StackModel;

public class StackResponseModel extends AppBaseWebServiceBaseResponseModel {


    private StackModel data;

    public StackModel getData() {
        return data;
    }

    public void setData(StackModel data) {
        this.data = data;
    }
}
