package com.lotus.model.web_response;


import com.lotus.model.UserModel;

/**
 * @author Sunil kumar Yadav
 * @Since 17/5/18
 */
public class LoginResponseModel extends BaseWebServiceModelResponse {

    UserModel data;

    public UserModel getData() {
        return data;
    }

    public void setData(UserModel data) {
        this.data = data;
    }
}
