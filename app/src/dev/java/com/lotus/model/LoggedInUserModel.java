package com.lotus.model;

import com.models.BaseModel;

public class LoggedInUserModel extends BaseModel {

    private boolean is_login;
    private Data data;
    private String status;

    public boolean isIs_login() {
        return is_login;
    }

    public void setIs_login(boolean is_login) {
        this.is_login = is_login;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getStatus() {
        return getValidString(status);
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static class Data extends BaseModel{

        private String type;
        private String user_name;
        private String user_id;
        private String mstrpassword;
        private String config_unmatched;
        private String isMultiBet;
        private int session_time_out;
        private int config_max_odd_limit;
        private String subAdminId;


        public String getType() {
            return getValidString(type);
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUser_name() {
            return getValidString(user_name);
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getUser_id() {
            return getValidString(user_id);
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getMstrpassword() {
            return getValidString(mstrpassword);
        }

        public void setMstrpassword(String mstrpassword) {
            this.mstrpassword = mstrpassword;
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

        public int getSession_time_out() {
            return session_time_out;
        }

        public void setSession_time_out(int session_time_out) {
            this.session_time_out = session_time_out;
        }

        public int getConfig_max_odd_limit() {
            return config_max_odd_limit;
        }

        public void setConfig_max_odd_limit(int config_max_odd_limit) {
            this.config_max_odd_limit = config_max_odd_limit;
        }

        public String getSubAdminId() {
            return getValidString(subAdminId);
        }

        public void setSubAdminId(String subAdminId) {
            this.subAdminId = subAdminId;
        }

        public boolean isUnmatched() {
            return getConfig_unmatched().equalsIgnoreCase("Y") ? true : false;
        }

        public boolean isMultiBet() {
            return getIsMultiBet().equalsIgnoreCase("Y") ? true : false;
        }
    }
}
