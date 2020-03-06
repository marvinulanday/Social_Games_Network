package com.stucom.socialgamesnetwork.DAO;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.stucom.socialgamesnetwork.data.model.User;

public class DAO {
    private User user;

    public void getUser(String username, String password) {
        RequestQueue queue = Volley.newRequestQueue(null);
        String url = "";
        Log.d("Marvin", url);
        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("SGM", response);
                        Gson gson = new Gson();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Marvin", "ERROR: " + error.getMessage());
                    }
                }
        );
        queue.add(request);
    }
}
