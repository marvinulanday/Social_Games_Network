package com.stucom.socialgamesnetwork.DAO;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.stucom.socialgamesnetwork.model.Data;
import com.stucom.socialgamesnetwork.model.User;
import com.stucom.socialgamesnetwork.ui.login.MyCallback;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class SgnDAO {

    /**
     * Comprueba si el siguiente usuario con email y password existe en la base de datos.
     *
     * @param context  Activity donde se realiza la petición
     * @param callback Se utilizará para llamar a la Activity procediente y hacer cambios en el modelo MVVM
     * @param email    Nombre de usuario
     * @param password Password escrita por el usuario
     */
    public void getUser(final Context context, final MyCallback callback, final String email, final String password) {

        RequestQueue queue = Volley.newRequestQueue(context);
        String URL = "http://www.arturviader.com/socialgamesnetwork/login?email=" + email + "&password=" + password + "";
        Log.d("SGN", URL);
        StringRequest request = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("SGN", response);
                        Gson gson = new Gson();
                        Type typeToken = new TypeToken<Data>() {
                        }.getType();
                        Data apiResponse = gson.fromJson(response, typeToken);
                        if (apiResponse.getErrorCode() == 0) {
                            String token = apiResponse.getData().toString();
                            SharedPrefsManagement.saveData(context, "token", token);
                            SharedPrefsManagement.saveData(context, "email", email);
                            User user = new User(email, password);
                            callback.login(user);
                        } else {
                            callback.login(null);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("SGN", String.valueOf(error));
            }
        });
        queue.add(request);
    }

    public void selectUserByEmail(final Context context, final MyCallback callback)
    {
        final String token = SharedPrefsManagement.getData(context, "token");
        final String email = SharedPrefsManagement.getData(context, "email");
        RequestQueue queue = Volley.newRequestQueue(context);

        String URL = "http://www.arturviader.com/socialgamesnetwork/selectUserByEmail?email=" + email + "&token=" + token;
        int score;
        StringRequest request = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override public void onResponse(String response) {

                        Gson gson = new Gson();
                        Type typeToken = new TypeToken<Data>() {}.getType();
                        Data apiResponse = gson.fromJson(response, typeToken);
                        JsonElement jsonElement = gson.toJsonTree(apiResponse.getData());

                        User user = gson.fromJson(jsonElement, User.class);
                        callback.login(user);
                    }
                }, new Response.ErrorListener() {
            @Override public void onErrorResponse(VolleyError error) {
                Log.d("SGN", String.valueOf(error));
            }
        }) {
            @Override protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                return params;
            }
        };
        queue.add(request);
    }

}

