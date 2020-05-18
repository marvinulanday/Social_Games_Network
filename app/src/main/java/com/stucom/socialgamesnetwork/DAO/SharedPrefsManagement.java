package com.stucom.socialgamesnetwork.DAO;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefsManagement {

    /**
     * Saves the data given to the SharedPreferences
     *
     * @param context
     * @param key
     * @param value
     */
    public static void saveData(Context context, String key, String value) {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.apply();
    }

    /**
     * Gets the data from to the SharedPreferences
     *
     * @param context
     * @param key
     */
    public static String getData(Context context, String key) {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        String value = sharedPref.getString(key, "");
        return value;
    }

    /**
     * Deletes all the data from the SharedPreferences
     *
     * @param context
     */
    public static void deleteData(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.clear();
        editor.apply();
    }
}
