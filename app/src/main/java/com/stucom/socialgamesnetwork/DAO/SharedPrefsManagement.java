package com.stucom.socialgamesnetwork.DAO;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefsManagement {

    /**
     * Guarda el dato establecido por clave-valor en el SharedPreferences
     *
     * @param context
     * @param key     clave del valor
     * @param value   valor a guardar
     */
    public static void saveData(Context context, String key, String value) {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.apply();
    }

    /**
     * Recoge el dato por clave en el SharedPreferences
     *
     * @param context
     * @param key     clave a buscar
     */
    public static String getData(Context context, String key) {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        String value = sharedPref.getString(key, "");
        return value;
    }

    /**
     * Elimina todos los datos del SharedPreferences
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
