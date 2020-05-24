package com.stucom.socialgamesnetwork.DAO;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.stucom.socialgamesnetwork.R;
import com.stucom.socialgamesnetwork.callbacks.SgnCallback;
import com.stucom.socialgamesnetwork.model.Data;
import com.stucom.socialgamesnetwork.model.User;
import com.stucom.socialgamesnetwork.model.Videogame;
import com.stucom.socialgamesnetwork.ui.login.MyCallback;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
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
                            User user = new User(email);
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

    public void selectUserByEmail(final Context context, final MyCallback callback) {
        final String token = SharedPrefsManagement.getData(context, "token");
        final String email = SharedPrefsManagement.getData(context, "email");
        RequestQueue queue = Volley.newRequestQueue(context);

        String URL = "http://www.arturviader.com/socialgamesnetwork/selectUserByEmail?email=" + email + "&token=" + token;
        int score;
        StringRequest request = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Gson gson = new Gson();
                        Type typeToken = new TypeToken<Data>() {
                        }.getType();
                        Data apiResponse = gson.fromJson(response, typeToken);
                        JsonElement jsonElement = gson.toJsonTree(apiResponse.getData());

                        User user = gson.fromJson(jsonElement, User.class);
                        callback.login(user);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("SGN", String.valueOf(error));
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                return params;
            }
        };
        queue.add(request);
    }

    public void updateUser(final Context context, final MyCallback callback, final User user, final String newPassword, final String passwordConfirm) {
        final String token = SharedPrefsManagement.getData(context, "token");
        RequestQueue queue = Volley.newRequestQueue(context);
        String URL = "http://www.arturviader.com/socialgamesnetwork/updateUser";


        StringRequest request = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("SGN", response);
                        Gson gson = new Gson();
                        Type typeToken = new TypeToken<Data>() {
                        }.getType();
                        Data apiResponse = gson.fromJson(response, typeToken);

                        switch (apiResponse.getErrorCode()) {
                            case 0:
                                user.setPassword("");
                                callback.login(user);
                                break;
                            case 4:
                                Toast.makeText(context, R.string.errorConfirmPassword, Toast.LENGTH_SHORT).show();
                                break;
                            case 5:
                                Toast.makeText(context, R.string.errorPasswordIncorrect, Toast.LENGTH_SHORT).show();
                                break;
                            case 6:
                                Toast.makeText(context, R.string.samePassword, Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                Toast.makeText(context, R.string.errorUpdateUser, Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, R.string.errorUpdateUser, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", user.getEmail());
                params.put("name", user.getName());
                params.put("surname", user.getSurname());
                params.put("oldPassword", user.getPassword());
                params.put("newPassword", newPassword);
                params.put("confirmPassword", passwordConfirm);
                params.put("token", token);
                return params;
            }
        };
        queue.add(request);
    }

    public void addFavouriteVideogame(final Context context, final SgnCallback callback, final int videogame) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String URL = "http://www.arturviader.com/socialgamesnetwork/insertFavourite";

        final String token = SharedPrefsManagement.getData(context, "token");
        final String email = SharedPrefsManagement.getData(context, "email");

        StringRequest request = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("SGN", response);
                        Gson gson = new Gson();
                        Type typeToken = new TypeToken<Data>() {
                        }.getType();
                        Data apiResponse = gson.fromJson(response, typeToken);
                        switch (apiResponse.getErrorCode()) {
                            case 0:
                                callback.isGameFavourite(context, (Boolean) apiResponse.getData());
                                break;
                            case 2:
                                break;
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("token", token);
                params.put("idGame", String.valueOf(videogame));
                return params;
            }
        };
        queue.add(request);
    }

    public void deleteFavouriteVideogame(final Context context, final SgnCallback callback, final int videogame) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String URL = "http://www.arturviader.com/socialgamesnetwork/deleteFavourite";

        final String token = SharedPrefsManagement.getData(context, "token");
        final String email = SharedPrefsManagement.getData(context, "email");

        StringRequest request = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("SGN", response);
                        Gson gson = new Gson();
                        Type typeToken = new TypeToken<Data>() {
                        }.getType();
                        Data apiResponse = gson.fromJson(response, typeToken);

                        switch (apiResponse.getErrorCode()) {
                            case 0:
                                if ((Boolean) apiResponse.getData())
                                    callback.isGameFavourite(context, false);
                                break;
                            case 2:
                                break;
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("token", token);
                params.put("idGame", String.valueOf(videogame));
                return params;
            }
        };
        queue.add(request);
    }

    public void isVideogameFavourite(final Context context, final SgnCallback callback, final int videogame) {
        RequestQueue queue = Volley.newRequestQueue(context);

        final String token = SharedPrefsManagement.getData(context, "token");
        final String email = SharedPrefsManagement.getData(context, "email");
        String URL = "http://www.arturviader.com/socialgamesnetwork/videogameIsFavourite?token=" + token + "&email=" + email + "&idGame=" + videogame;


        StringRequest request = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        Type typeToken = new TypeToken<Data>() {
                        }.getType();
                        Data apiResponse = gson.fromJson(response, typeToken);
                        Log.d("SGN", String.valueOf(apiResponse));
                        switch (apiResponse.getErrorCode()) {
                            case 0:
                                callback.isGameFavourite(context, (Boolean) apiResponse.getData());
                                break;
                            case 2:
                                break;
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(request);
    }

    public void selectFavourites(final Context context, final SgnCallback callback) {
        final String token = SharedPrefsManagement.getData(context, "token");
        final String email = SharedPrefsManagement.getData(context, "email");
        RequestQueue queue = Volley.newRequestQueue(context);
        String URL = "http://www.arturviader.com/socialgamesnetwork/selectFavourites?email=" + email + "&token=" + token;
        StringRequest request = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        Type type1 = new TypeToken<Data>() {
                        }.getType();
                        Data apiResponse = gson.fromJson(response, type1);
                        JsonElement jsonElement = gson.toJsonTree(apiResponse.getData());
                        Type type2 = new TypeToken<List<Videogame>>() {
                        }.getType();
                        List<Videogame> favourites = gson.fromJson(jsonElement, type2);
                        callback.setListGames(context, favourites);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("SGN", String.valueOf(error));
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                return params;
            }
        };
        queue.add(request);
    }

    public void getHistoryByUser(final Context context, final SgnCallback sgnCallback) {
        final String token = SharedPrefsManagement.getData(context, "token");
        final String email = SharedPrefsManagement.getData(context, "email");
        RequestQueue queue = Volley.newRequestQueue(context);
        String URL = "http://www.arturviader.com/socialgamesnetwork/selecthistory?email=" + email + "&token=" + token;
        StringRequest request = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        Type type1 = new TypeToken<Data>() {
                        }.getType();
                        Data apiResponse = gson.fromJson(response, type1);
                        JsonElement jsonElement = gson.toJsonTree(apiResponse.getData());
                        Type type2 = new TypeToken<List<Videogame>>() {
                        }.getType();
                        List<Videogame> videogameList = gson.fromJson(jsonElement, type2);
                        sgnCallback.setListGames(context, videogameList);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("SGN", String.valueOf(error));
            }
        });
        queue.add(request);
    }
}
