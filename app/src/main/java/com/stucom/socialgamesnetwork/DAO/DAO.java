package com.stucom.socialgamesnetwork.DAO;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.stucom.socialgamesnetwork.model.Data;
import com.stucom.socialgamesnetwork.model.User;
import com.stucom.socialgamesnetwork.ui.login.MyCallback;

import java.lang.reflect.Type;

public class DAO {
    private User user;

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
                        Data apiResponse = gson.fromJson(response.toString(), typeToken);
                        if (apiResponse.getErrorCode() == 0) {
                            String token = apiResponse.getData().toString();
                            SharedPrefsManagement.saveData(context, "token", token);
                            SharedPrefsManagement.saveData(context, "email", email);
                            user = new User(email, password);
                            callback.login(user);
                        } else {
                            AlertDialog show = new AlertDialog.Builder(null)
                                    .setTitle("Error")
                                    .setMessage("Error: " + apiResponse.getErrorMsg())
                                    .setNeutralButton("OK", null)
                                    .show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AlertDialog show = new AlertDialog.Builder(null)
                        .setTitle("Error")
                        .setMessage("Network error")
                        .setNeutralButton("OK", null)
                        .show();
            }
        });
        queue.add(request);
    }
}

