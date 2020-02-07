package com.lotus.model;

import com.models.BaseModel;

/**
 * @author Sunil kumar Yadav
 * @Since 19/5/18
 */
public class ChangePasswordModel extends BaseModel {

    String mstrpassword;

    public String getMstrpassword() {
        return getValidString(mstrpassword);
    }

    public void setMstrpassword(String mstrpassword) {
        this.mstrpassword = mstrpassword;
    }
}
