package com.lotus.model.web_response;


import com.lotus.model.ChangePasswordModel;

/**
 * @author Sunil kumar Yadav
 * @Since 19/5/18
 */
public class ChangePasswordResponseModel extends BaseWebServiceModelResponse {

    ChangePasswordModel data;

    public ChangePasswordModel getData () {
        return data;
    }

    public void setData (ChangePasswordModel data) {
        this.data = data;
    }
}
