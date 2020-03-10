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
import com.stucom.socialgamesnetwork.model.Data;
import com.stucom.socialgamesnetwork.model.User;
import com.stucom.socialgamesnetwork.ui.login.MyCallback;

public class DAO {
    private User user;

    /**
     * Comprueba si el siguiente usuario con username y password existe en la base de datos.
     *
     * @param context  Activity donde se realiza la petición
     * @param callback Se utilizará para llamar a la Activity procediente y hacer cambios en el modelo MVVM
     * @param username Nombre de usuario
     * @param password Password escrita por el usuario
     */
    public void getUser(Context context, final MyCallback callback, final String username, final String password) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "http://www.arturviader.com/socialgamesnetwork/login?username=" + username + "&password=" + password;
        Log.d("SGN", url);
        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("SGN", response);
                        Gson gson = new Gson();
                        Data data = gson.fromJson(response, Data.class);
                        if (!data.getData().toString().isEmpty()) {
                            user = new User(username, password);
                            callback.login(user);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("SGN", "ERROR: " + error.getMessage());
                    }
                }
        );
        queue.add(request);
    }

}
