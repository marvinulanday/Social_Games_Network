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
import com.stucom.socialgamesnetwork.data.model.User;

public class DAO {
    private User user;

    public void getUser(Context context, String username, String password) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "";
        Log.d("SGN", url);
        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("SGN", response);
                        Gson gson = new Gson();

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
