package com.lotus.preferences;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.lotus.model.StackModel;
import com.lotus.model.UserBalanceModel;
import com.lotus.model.UserModel;
import com.preferences.BasePrefs;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * @author Sunil kumar Yadav
 * @Since 17/5/18
 */


public class UserPrefs extends BasePrefs {

    static final String Prefsname = "prefs_user";

    static String KEY_LOGGEDIN_USER = "logged_in_user";
    static String KEY_LOGGEDIN_USER_BALANCE = "logged_in_user_balance";
    static String KEY_LOGGEDIN_USER_STACK = "logged_in_user_stack";
    static String KEY_COOKIES = "COOKIES";

    Context context;

    List<UserPrefsListener> userPrefsListenerList = new ArrayList<>();
    List<UserBalanceListener> userBalanceListenerList = new ArrayList<>();
    List<UserStackListener> userStackListenerList = new ArrayList<>();

    public UserPrefs(Context context) {
        this.context = context;
    }

    public void addListener(UserPrefsListener userPrefsListener) {
        userPrefsListenerList.add(userPrefsListener);
    }

    public void removeListener(UserPrefsListener userPrefsListener) {
        userPrefsListenerList.remove(userPrefsListener);
    }

    public void addBalanceListener(UserBalanceListener userBalanceListener) {
        userBalanceListenerList.add(userBalanceListener);
    }




    public void removeBalanceListener(UserBalanceListener userBalanceListener) {
        userBalanceListenerList.remove(userBalanceListener);
    }

    public void clearListeners() {
        userPrefsListenerList.clear();
        userBalanceListenerList.clear();
        userStackListenerList.clear();

    }


    public void addStackListener(UserStackListener userStackListener) {
        if (!userStackListenerList.contains(userStackListener)) {
            userStackListenerList.add(userStackListener);
        }
    }

    public void removeStackListener(UserStackListener userStackListener) {
        if (userStackListenerList.contains(userStackListener)) {
            userStackListenerList.remove(userStackListener);
        }
    }

    @Override
    public String getPrefsName() {
        return Prefsname;
    }

    @Override
    public Context getContext() {
        return context;
    }

    public UserModel getLoggedInUser() {
        UserModel userModel = null;
        try {
            String userDetail = getStringKeyValuePrefs(KEY_LOGGEDIN_USER);
            if (isValidString(userDetail)) {
                userModel = new Gson().fromJson(userDetail, UserModel.class);
            }
        } catch (JsonSyntaxException e) {

        }
        return userModel;
    }

    public void saveLoggedInUser(UserModel userModel) {
        if (userModel == null) return;
        String userDetail = new Gson().toJson(userModel);
        if (setStringKeyValuePrefs(KEY_LOGGEDIN_USER, userDetail)) {
            triggerUserLoggedIn(userModel);
        }
    }


    private void triggerUserLoggedIn(UserModel userModel) {
        for (UserPrefsListener userPrefsListener : userPrefsListenerList) {
            userPrefsListener.userLoggedIn(userModel);
        }
    }

    public void updateLoggedInUser(UserModel userModel) {
        if (userModel == null) return;
        String userdetail = new Gson().toJson(userModel);
        if (setStringKeyValuePrefs(KEY_LOGGEDIN_USER, userdetail)) {
            triggerLoggedInUserUpdate(userModel);
        }
    }

    private void triggerLoggedInUserUpdate(UserModel userModel) {
        for (UserPrefsListener userPrefsListener : userPrefsListenerList) {
            userPrefsListener.loggedInUserUpdate(userModel);
        }
    }


    public void clearLoggedInUser() {
        if (setStringKeyValuePrefs(KEY_LOGGEDIN_USER, "")) {
            triggerLoggedInUserClear();
        }
    }

    private void triggerLoggedInUserClear() {
        for (UserPrefsListener userPrefsListener : userPrefsListenerList) {
            userPrefsListener.loggedInUserClear();
        }
    }

    public void saveCookies(HashSet<String> cookies) {
        if (cookies == null) return;
        if (setStringSetKeyValuePrefs(KEY_COOKIES, cookies)) {

        }
    }

    public HashSet<String> getCookies() {
        return getStringSetKeyValuePrefs(KEY_COOKIES);
    }


    public interface UserPrefsListener {
        void userLoggedIn(UserModel userModel);

        void loggedInUserUpdate(UserModel userModel);

        void loggedInUserClear();
    }


    public UserBalanceModel getLoggedInUserBalance() {
        UserBalanceModel userBalanceModel = null;
        try {
            String userDetail = getStringKeyValuePrefs(KEY_LOGGEDIN_USER_BALANCE);
            if (isValidString(userDetail)) {
                userBalanceModel = new Gson().fromJson(userDetail, UserBalanceModel.class);
            }
        } catch (JsonSyntaxException e) {

        }
        return userBalanceModel;
    }


    public void updateLoggedInUserBalance(UserBalanceModel userBalanceModel) {
        if (userBalanceModel == null) return;
        String userdetail = new Gson().toJson(userBalanceModel);
        if (setStringKeyValuePrefs(KEY_LOGGEDIN_USER_BALANCE, userdetail)) {
            triggerLoggedInUserBalanceUpdate(userBalanceModel);
        }
    }

    public void updateLoggedInUserStack(StackModel stackModel) {
        if (stackModel == null) return;
        String stackDetail = new Gson().toJson(stackModel);
        if (setStringKeyValuePrefs(KEY_LOGGEDIN_USER_STACK, stackDetail)) {
            triggerLoggedInUserStackUpdate(stackModel);
        }
    }

    private void triggerLoggedInUserBalanceUpdate(UserBalanceModel userBalanceModel) {
        for (UserBalanceListener userBalanceListener : userBalanceListenerList) {
            userBalanceListener.userLoggedInBalanceUpdate(userBalanceModel);
        }
    }

    private void triggerLoggedInUserStackUpdate(StackModel stackModel) {
        for (UserStackListener stackListener : userStackListenerList) {
            stackListener.userLoggedInStackUpdate(stackModel);
        }
    }

    public interface UserBalanceListener {
        void userLoggedInBalanceUpdate(UserBalanceModel userBalanceModel);
    }

    public interface UserStackListener {
        void userLoggedInStackUpdate(StackModel stackModel);
    }
}
