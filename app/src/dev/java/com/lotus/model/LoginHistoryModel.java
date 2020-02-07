package com.lotus.model;

import com.models.BaseModel;

public class LoginHistoryModel extends BaseModel {

    private String logstdt;
    private String ipadress;
    private String device_info;
    private String browser_info;
    private String mstruserid;

    public String getLogstdt() {
        return getValidString(logstdt);

    }

    public void setLogstdt(String logstdt) {
        this.logstdt = logstdt;
    }

    public String getIpadress() {
        return getValidString(ipadress);
    }

    public void setIpadress(String ipadress) {
        this.ipadress = ipadress;
    }

    public String getDevice_info() {
        return getValidString(device_info);
    }

    public void setDevice_info(String device_info) {
        this.device_info = device_info;
    }

    public String getBrowser_info() {
        return getValidString(browser_info);
    }

    public void setBrowser_info(String browser_info) {
        this.browser_info = browser_info;
    }

    public String getMstruserid() {
        return getValidString(mstruserid);
    }

    public void setMstruserid(String mstruserid) {
        this.mstruserid = mstruserid;
    }
}
