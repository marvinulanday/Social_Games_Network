package com.stucom.socialgamesnetwork.DAO;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.stucom.socialgamesnetwork.callbacks.IgdbCallback;
import com.stucom.socialgamesnetwork.model.Genre;
import com.stucom.socialgamesnetwork.model.Videogame;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class IgdbDAO {

    public void getGenres(final Context context, final IgdbCallback callback) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String URL = "https://api-v3.igdb.com/genres?fields=*&limit=30";
        Log.d("SGN", URL);
        StringRequest request = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        Type listGenre = new TypeToken<List<Genre>>() {
                        }.getType();
                        List<Genre> genresAPI = gson.fromJson(response, listGenre);

                        Log.d("SGN", genresAPI.toString());

                        callback.findGenres(context, genresAPI);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("SGN", String.valueOf(error));
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

    public void getGames(final Context context, final Set<Genre> genres) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String URL = "https://api-v3.igdb.com/games";
        Log.d("SGN", URL);
        StringBuilder stringBuilder = new StringBuilder("fields id,name,genres; where genres = [");
        int i = 0;
        for (Genre genre : genres) {
            stringBuilder.append(genre.getId());
            if (i < genres.size() - 1) {
                stringBuilder.append(", ");
            }
            i++;
        }
        stringBuilder.append("];");

        final String requestBody = stringBuilder.toString();
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
                return params;
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                return requestBody.getBytes();
            }

        };
        queue.add(request);
    }

    public void getGamesByGenre(final Context context, final IgdbCallback callback, final Set<Genre> genres) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String URL = "https://api-v3.igdb.com/games";
        Log.d("SGN", URL);
        StringBuilder stringBuilder = new StringBuilder();
        if (genres.isEmpty()) {
            stringBuilder.append("fields *, genres.*, cover.*; limit 5; sort popularity desc;");
        } else {
            stringBuilder.append("fields *, genres.*, cover.*;  limit 5; sort popularity desc; where genres = [");
            int i = 0;
            for (Genre genre : genres) {
                stringBuilder.append(genre.getId());
                if (i < genres.size() - 1) {
                    stringBuilder.append(", ");
                }
                i++;
            }
            stringBuilder.append("];");
        }
        final String requestBody = stringBuilder.toString();
        StringRequest request = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        Type listVideogame = new TypeToken<List<Videogame>>() {
                        }.getType();
                        List<Videogame> videogamesAPI = gson.fromJson(response, listVideogame);

                        Log.d("SGN", videogamesAPI.toString());

                        callback.findGames(context, videogamesAPI);

                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("SGN", String.valueOf(error));
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user-key", "d191590b7da257537341f8ca039f5d2f");
                return params;
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                return requestBody.getBytes();
            }

        };
        queue.add(request);
    }

    public void getGameById(final Context context, final IgdbCallback callback, final Videogame videogame) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String URL = "https://api-v3.igdb.com/games";
        Log.d("SGN", URL);
        final String requestBody = "fields *, genres.*, cover.*; where id =" + videogame.getIdGame() + "; sort popularity desc;";
        StringRequest request = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        Type listVideogame = new TypeToken<List<Videogame>>() {
                        }.getType();
                        List<Videogame> videogamesAPI = gson.fromJson(response, listVideogame);

                        Log.d("SGN", String.valueOf(videogamesAPI.get(0)));

                        callback.getGame(context, videogamesAPI.get(0));

                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("SGN", String.valueOf(error));
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user-key", "d191590b7da257537341f8ca039f5d2f");
                return params;
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                return requestBody.getBytes();
            }

        };
        queue.add(request);
    }


}
