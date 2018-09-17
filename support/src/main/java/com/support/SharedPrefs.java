package com.support;

/**
 * Created by raghav on 23/8/17.
 */

public class SharedPrefs {

    public static boolean isLoggedIn() {
        return SharedPrefsHelper.getInstance().get(AppPrefStrings.IS_LOGGED_IN, false);
    }

    public static void setLoginStatus(boolean status) {
        SharedPrefsHelper.getInstance().save(AppPrefStrings.IS_LOGGED_IN, status);
    }

    public static String getToken() {
        return SharedPrefsHelper.getInstance().get(AppPrefStrings.TOKEN, "");
    }

    public static void setToken(String token) {
        SharedPrefsHelper.getInstance().save(AppPrefStrings.TOKEN, token);
    }

    public static String getUID() {
        return SharedPrefsHelper.getInstance().get(AppPrefStrings.UID, "");
    }

    public static void setUID(String uid) {
        SharedPrefsHelper.getInstance().save(AppPrefStrings.UID, uid);
    }

    public interface AppPrefStrings {
        String IS_LOGGED_IN = "IsLoggedIn";
        String TOKEN = "token";
        String UID = "uid";
    }
}
