package com.stucom.socialgamesnetwork.DAO;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.stucom.socialgamesnetwork.model.Genre;
import com.stucom.socialgamesnetwork.ui.login.MyCallback;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IgdbDAO {

    public void getGenres(final Context context, final MyCallback callback) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String URL = "https://api-v3.igdb.com/genres?fields=*&limit=30";
        Log.d("SGN", URL);
        StringRequest request = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("SGN", response);
                        Gson gson = new Gson();
                        Type listGenre = new TypeToken<List<Genre>>() {
                        }.getType();
                        List<Genre> genres = gson.fromJson(response, listGenre);
                        Log.d("SGN", String.valueOf(genres));
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
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user-key", "d191590b7da257537341f8ca039f5d2f");
                return params;
            }
        };
        queue.add(request);
    }

    public void getGamesByGenre(final Context context, final MyCallback callback, final Genre genre) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String URL = "https://api-v3.igdb.com/games";
        Log.d("SGN", URL);
        final String requestBody = "fields id,name,genres; where genres = [9,26];";
        StringRequest request = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("SGN", response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user-key", "d191590b7da257537341f8ca039f5d2f");
                Log.d("SGN", "HEADERS");
                return params;
            }

            @Override
            public String getBodyContentType() {
                return super.getBodyContentType();
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                return requestBody.getBytes();
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                return super.parseNetworkResponse(response);
            }
        };
        queue.add(request);
    }
}
