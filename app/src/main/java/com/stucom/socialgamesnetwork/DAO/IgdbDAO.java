package com.stucom.socialgamesnetwork.DAO;

import android.content.Context;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.stucom.socialgamesnetwork.R;
import com.stucom.socialgamesnetwork.callbacks.IgdbCallback;
import com.stucom.socialgamesnetwork.model.Genre;
import com.stucom.socialgamesnetwork.model.Videogame;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class IgdbDAO {

    /**
     * Selecciona los géneros existentes de la API
     *
     * @param context  Activity donde se realiza la petición
     * @param callback Actualizará la lista de géneros
     */
    public void getGenres(final Context context, final IgdbCallback callback) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String URL = "https://api-v3.igdb.com/genres?fields=*&limit=30";
        StringRequest request = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        Type listGenre = new TypeToken<List<Genre>>() {
                        }.getType();
                        List<Genre> genresAPI = gson.fromJson(response, listGenre);

                        callback.findGenres(context, genresAPI);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, R.string.networkerror, Toast.LENGTH_SHORT).show();
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

    /**
     * Recoge una lista de videojuegos filtrados por géneros y por una cadena de texto
     *
     * @param context  Activity donde se realiza la petición
     * @param callback Actualizará la lista de videojuegos mostrador por pantalla
     * @param genres   Lista de géneros a filtrar
     * @param search   Cadena de texto a buscar
     * @param offset   sistema de paginación
     * @param add      Se añade a la lista o se modifica
     */
    public void getGamesByGenreAndText(final Context context, final IgdbCallback callback, final Set<Genre> genres, final String search, int offset, final boolean add) {
        RequestQueue queue = Volley.newRequestQueue(context);

        String URL = "https://api-v3.igdb.com/games";

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("fields id, name, parent_game.name, summary, total_rating, first_release_date, genres.*, cover.*, platforms.*, game_modes.*, involved_companies.*; limit 15; offset " + offset + "; where rating >= 75;");
        if (!genres.isEmpty()) {
            stringBuilder.append("where genres = [");
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
        if (!search.isEmpty()) {
            stringBuilder.append("search \"" + search + "\";");
        } else {
            stringBuilder.append("sort popularity desc;");
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
                        callback.findGames(context, videogamesAPI, add);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, R.string.networkerror, Toast.LENGTH_SHORT).show();
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

    /**
     * Recoge un videojuego por su ID
     *
     * @param context   Activity donde se realiza la petición
     * @param callback  Insertará los datos del videojuego recogido
     * @param videogame videojuego a buscar
     */
    public void getGameById(final Context context, final IgdbCallback callback, final int videogame) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String URL = "https://api-v3.igdb.com/games";
        final String requestBody = "fields id, name, parent_game.name, summary, total_rating, first_release_date,  genres.*, cover.*, platforms.*, game_modes.*, involved_companies.*; where id =" + videogame + ";";
        StringRequest request = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        Type listVideogame = new TypeToken<List<Videogame>>() {
                        }.getType();
                        List<Videogame> videogamesAPI = gson.fromJson(response, listVideogame);
                        callback.getGame(context, videogamesAPI.get(0));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, R.string.networkerror, Toast.LENGTH_SHORT).show();
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

    /**
     * Recoge un videojuego por su ID
     *
     * @param context   Activity donde se realiza la petición
     * @param callback  Insertará los datos del videojuego recogido
     * @param videogame videojuego a buscar
     * @param tvTitle   View a insertar uno de los datos
     * @param ivImg     View a insertar uno de los datos
     * @param pbRating  View a insertar uno de los datos
     * @param tvRating  View a insertar uno de los datos
     * @param tvGenres  View a insertar uno de los datos
     */
    public void getGameById(final Context context, final IgdbCallback callback, final int videogame, final TextView tvTitle, final ImageView ivImg, final ProgressBar pbRating, final TextView tvRating, final TextView tvGenres) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String URL = "https://api-v3.igdb.com/games";
        final String requestBody = "fields id, name, parent_game.name, summary, total_rating, first_release_date,  genres.*, cover.*, platforms.*, game_modes.*, involved_companies.*; where id =" + videogame + ";";
        StringRequest request = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        Type listVideogame = new TypeToken<List<Videogame>>() {
                        }.getType();
                        List<Videogame> videogamesAPI = gson.fromJson(response, listVideogame);
                        callback.getFavouriteGame(context, videogamesAPI.get(0), tvTitle, ivImg, pbRating, tvRating, tvGenres);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, R.string.networkerror, Toast.LENGTH_SHORT).show();
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
