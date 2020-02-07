package com.lotus.model.web_response;

import com.lotus.appBase.AppBaseWebServiceBaseResponseModel;
import com.lotus.model.LoginHistoryModel;

import java.util.List;

public class LoginHistoryResponseModel extends AppBaseWebServiceBaseResponseModel {

    private int count;
    private List<LoginHistoryModel> data;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<LoginHistoryModel> getLoginHistory() {
        return data;
    }

    public void setLoginHistory(List<LoginHistoryModel> data) {
        this.data = data;
    }
}
