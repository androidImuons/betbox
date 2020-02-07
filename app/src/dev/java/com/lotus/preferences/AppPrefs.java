package com.lotus.preferences;

import android.content.Context;

import com.preferences.BasePrefs;

/**
 * @author Sunil kumar Yadav
 * @Since 17/5/18
 */

public class AppPrefs extends BasePrefs {

    static final String Prefsname = "prefs_app";
    private static final String KEY_TERMS_CONDITION = "term_condition";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    Context context;


    public AppPrefs (Context context) {
        this.context = context;
    }

    @Override
    public String getPrefsName () {
        return Prefsname;
    }

    @Override
    public Context getContext () {
        return context;
    }

    public void saveTermsConditions (String data) {
        setStringKeyValuePrefs(KEY_TERMS_CONDITION, data);
    }

    public String getTermsConditions () {
        return getStringKeyValuePrefs(KEY_TERMS_CONDITION);
    }

    public void updateRememberMe(String username,String password){
        setStringKeyValuePrefs(KEY_USERNAME, username);
        setStringKeyValuePrefs(KEY_PASSWORD, password);
    }

    public String getRememberUserName(){
        return getStringKeyValuePrefs(KEY_USERNAME);
    }

    public String getRememberPassword(){
        return getStringKeyValuePrefs(KEY_PASSWORD);
    }
}
