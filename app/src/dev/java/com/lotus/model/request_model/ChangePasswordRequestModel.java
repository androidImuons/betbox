package com.lotus.model.request_model;

import com.medy.retrofitwrapper.RequestJSON;

/**
 * @author Sunil kumar Yadav
 * @Since 17/5/18
 */

@RequestJSON
public class ChangePasswordRequestModel extends AppBaseRequestModel {

    public String old_password;
    public String newpassword;
    public String Renewpassword;
    public long user_id;
    public long user_type;
}
