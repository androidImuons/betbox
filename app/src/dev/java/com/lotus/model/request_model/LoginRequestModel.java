package com.lotus.model.request_model;

import com.medy.retrofitwrapper.RequestJSON;

/**
 * @author Sunil kumar Yadav
 * @Since 17/5/18
 */

@RequestJSON
public class LoginRequestModel extends AppBaseRequestModel {

    public String browser_info;
    public String device_info;
    public String username1;
    public String password1;
    public String captcha;
}
