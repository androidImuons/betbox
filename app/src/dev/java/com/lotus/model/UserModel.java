package com.lotus.model;

import com.lotus.model.web_response.BaseWebServiceModelResponse;

public class UserModel extends BaseWebServiceModelResponse {

    private long type;
    private String user_name;
    private long user_id;
    private long ChangePas;
    private String TokenId;
    private long set_timeout;
    private String lgnstatus;
    private long last_login_id;
    private String last_login_time;
    private String mstrpassword;
    private String terms_conditions;
    private String config_unmatched;
    private String isMultiBet;
    boolean terms_conditions_accepted;

    public long getType() {
        return type;
    }

    public void setType(long type) {
        this.type = type;
    }

    public String getUser_name() {
        return getValidString(user_name);
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public long getChangePas() {
        return ChangePas;
    }

    public void setChangePas(long changePas) {
        ChangePas = changePas;
    }

    public String getTokenId() {
        return getValidString(TokenId);
    }

    public void setTokenId(String tokenId) {
        TokenId = tokenId;
    }

    public long getSet_timeout() {
        return set_timeout;
    }

    public void setSet_timeout(long set_timeout) {
        this.set_timeout = set_timeout;
    }

    public String getLgnstatus() {
        return getValidString(lgnstatus);
    }

    public void setLgnstatus(String lgnstatus) {
        this.lgnstatus = lgnstatus;
    }

    public long getLast_login_id() {
        return last_login_id;
    }

    public void setLast_login_id(long last_login_id) {
        this.last_login_id = last_login_id;
    }

    public String getLast_login_time() {
        return getValidString(last_login_time);
    }

    public void setLast_login_time(String last_login_time) {
        this.last_login_time = last_login_time;
    }

    public String getMstrpassword() {
        return getValidString(mstrpassword);
    }

    public void setMstrpassword(String mstrpassword) {
        this.mstrpassword = mstrpassword;
    }

    public String getTerms_conditions() {
        return getValidString(terms_conditions);
    }

    public void setTerms_conditions(String terms_conditions) {
        this.terms_conditions = terms_conditions;
    }

    public String getConfig_unmatched() {
        return getValidString(config_unmatched);
    }

    public void setConfig_unmatched(String config_unmatched) {
        this.config_unmatched = config_unmatched;
    }

    public String getIsMultiBet() {
        return getValidString(isMultiBet);
    }

    public void setIsMultiBet(String isMultiBet) {
        this.isMultiBet = isMultiBet;
    }

    public boolean isTerms_conditions_accepted() {
        return terms_conditions_accepted;
    }

    public void setTerms_conditions_accepted(boolean terms_conditions_accepted) {
        this.terms_conditions_accepted = terms_conditions_accepted;
    }

    public boolean isError() {
        return getError() > 0;
    }

    public boolean isUnmatched() {
        return getConfig_unmatched().equalsIgnoreCase("Y");
    }

    public boolean isMultiBet() {
        return getIsMultiBet().equalsIgnoreCase("Y");
    }

}
