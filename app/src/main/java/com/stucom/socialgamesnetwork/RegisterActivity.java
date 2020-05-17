package com.stucom.socialgamesnetwork;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.stucom.socialgamesnetwork.model.Data;
import com.stucom.socialgamesnetwork.model.Favourite;
import com.stucom.socialgamesnetwork.model.Game;
import com.stucom.socialgamesnetwork.model.History;
import com.stucom.socialgamesnetwork.model.Opinion;
import com.stucom.socialgamesnetwork.model.Recommendation;
import com.stucom.socialgamesnetwork.model.Score;
import com.stucom.socialgamesnetwork.model.User;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    TextView tEmail;
    TextView tUsername;
    TextView tName;
    TextView tSurname;
    TextView tPassword;
    TextView tConfirm;
    Button bRegister;
    String token;
    int countScore;
    Score scoresGame[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        tEmail = findViewById(R.id.tEmail);
        tUsername = findViewById(R.id.tUsername);
        tName = findViewById(R.id.tName);
        tSurname = findViewById(R.id.tSurname);
        tPassword = findViewById(R.id.tPassword);
        tConfirm = findViewById(R.id.tConfirm);
        bRegister = findViewById(R.id.bRegister);
        bRegister.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Este es el token de mi usuario registrado, esto debería leerse del shared preferences que ya está el método
                token = "bc1d3253a4e4b6683041c3bdd4621b0d106c22235a3bdef2e90250be68e39952df16fcbfeddcd43395edd8cf7b7986a9fe17e73fa90548e88e68052909152cd3";
                //Game game = new Game("35", "Sonico");
                //insertGame(game);
                Opinion opinion = new Opinion("djvatio@hotmail.com", "10","Hola");
                insertOpinion(opinion);
                opinion.setText("holas");
                updateOpinion(opinion);
                //deleteOpinion(opinion);
                //Recommendation recommendation = new Recommendation("djvatio@hotmail.com", "1", "2", "Holas");
                //insertRecommendation(recommendation);
                //deleteRecommendation(recommendation);
                //updateRecommendation(recommendation);
                //Score score = new Score("djvatio@hotmail.com", "1",true);
                //insertScore(score);
                //score.setPositive(false);
                //updateScore(score);
                //deleteScore(score);
                //Favourite favourite = new Favourite("djvatio@hotmail.com", "1");
                //deleteFavourite(favourite);
                //insertFavourite(favourite);
                //History history = new History("djvatio@hotmail.com","1", new Date());
                //insertHistory(history);
                //updateHistory(history);
                //deleteHistory(history);
                //User user = new User("","","djvatio@hotmail.com");
                //deleteAllHistory(user);
                Game game = new Game("1","Sonic");
                //selectCountScore(game);
                selectScore(game);
                if(!tEmail.getText().toString().isEmpty() && !tUsername.getText().toString().isEmpty() && !tName.getText().toString().isEmpty() && !tSurname.getText().toString().isEmpty() && !tPassword.getText().toString().isEmpty() && !tConfirm.getText().toString().isEmpty())
                {
                    if (tPassword.getText().toString().equals(tConfirm.getText().toString()))
                    {
                        register();




                    }
                    else
                    {
                        AlertDialog show = new AlertDialog.Builder(RegisterActivity.this)
                                .setTitle("Error")
                                .setMessage(R.string.incorrectConfirmation)
                                .setNeutralButton("OK", null)
                                .show();
                    }
                }
                else
                {
                    AlertDialog show = new AlertDialog.Builder(RegisterActivity.this)
                            .setTitle("Error")
                            .setMessage(R.string.emptyFields)
                            .setNeutralButton("OK", null)
                            .show();
                }
            }
        });
    }

    private void register()
    {
        RequestQueue queue = Volley.newRequestQueue(this);
        String URL = "http://www.arturviader.com/socialgamesnetwork/insertUser";
        StringRequest request = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override public void onResponse(String response) {
                        Gson gson = new Gson();
                        Type typeToken = new TypeToken<Data>() {}.getType();
                        Data apiResponse = gson.fromJson(response.toString(), typeToken);

                        if(apiResponse.getErrorCode()==0)
                        {
                            final EditText tcodi = new EditText(RegisterActivity.this);
                            new AlertDialog.Builder(RegisterActivity.this)
                                    .setTitle(R.string.insertcode1)
                                    .setMessage(R.string.insertcode2)
                                    .setView(tcodi)
                                    .setPositiveButton(R.string.register, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int whichButton) {
                                            int codi = Integer.parseInt(tcodi.getText().toString());
                                            confirmCode(codi);

                                        }
                                    })
                                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int whichButton) {

                                        }
                                    })
                                    .show();
                        }
                        else
                        {
                            AlertDialog show = new AlertDialog.Builder(RegisterActivity.this)
                                    .setTitle("Error")
                                    .setMessage("Error: " + apiResponse.getErrorMsg())
                                    .setNeutralButton("OK", null)
                                    .show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override public void onErrorResponse(VolleyError error) {
                AlertDialog show = new AlertDialog.Builder(RegisterActivity.this)
                        .setTitle("Error")
                        .setMessage(R.string.networkerror)
                        .setNeutralButton("OK", null)
                        .show();
            }
        }) {
            @Override protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", tEmail.getText().toString());
                params.put("password", tPassword.getText().toString());
                params.put("username", tUsername.getText().toString());
                params.put("name", tName.getText().toString());
                params.put("surname", tSurname.getText().toString());

                return params;
            }
        };
        queue.add(request);
    }

    private void confirmCode(final int codi)
    {
        RequestQueue queue = Volley.newRequestQueue(this);
        String URL = "http://www.arturviader.com/socialgamesnetwork/loginConfirmation";
        StringRequest request = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override public void onResponse(String response) {

                        Gson gson = new Gson();
                        Type typeToken = new TypeToken<Data>() {}.getType();
                        Data apiResponse = gson.fromJson(response.toString(), typeToken);

                        if(apiResponse.getErrorCode()==0)
                        {
                            String token = apiResponse.getData().toString();

                            guardatoken(token,tEmail.getText().toString());
                            //visibilitzaunregister();

                            AlertDialog show = new AlertDialog.Builder(RegisterActivity.this)
                                    .setTitle(R.string.userregistrered1)
                                    .setMessage(R.string.userregistrered2)
                                    .setPositiveButton("OK",new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            finish();
                                        }
                                    }).show();
                        }
                        else
                        {
                            AlertDialog show = new AlertDialog.Builder(RegisterActivity.this)
                                    .setTitle("Error")
                                    .setMessage("Error: " + apiResponse.getErrorMsg())
                                    .setNeutralButton("OK", null)
                                    .show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override public void onErrorResponse(VolleyError error) {
                AlertDialog show = new AlertDialog.Builder(RegisterActivity.this)
                        .setTitle("Error")
                        .setMessage(R.string.networkerror)
                        .setNeutralButton("OK", null)
                        .show();
            }
        }) {
            @Override protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", tEmail.getText().toString());
                params.put("password", tPassword.getText().toString());
                params.put("confirmation", Integer.toString(codi));
                return params;
            }
        };
        queue.add(request);
    }


    /**
     * Insert a game
     * Verified
     * @param game
     */
    private void insertGame(final Game game)
    {
        RequestQueue queue = Volley.newRequestQueue(this);
        String URL = "http://www.arturviader.com/socialgamesnetwork/insertGame";
        StringRequest request = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override public void onResponse(String response) {

                        Gson gson = new Gson();
                        Type typeToken = new TypeToken<Data>() {}.getType();
                        Data apiResponse = gson.fromJson(response.toString(), typeToken);

                        if(apiResponse.getErrorCode()!=0)
                        {
                            AlertDialog show = new AlertDialog.Builder(RegisterActivity.this)
                                    .setTitle("Error")
                                    .setMessage(R.string.errorInsertGame)
                                    .setNeutralButton("OK", null)
                                    .show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override public void onErrorResponse(VolleyError error) {
                AlertDialog show = new AlertDialog.Builder(RegisterActivity.this)
                        .setTitle("Error")
                        .setMessage(R.string.networkerror)
                        .setNeutralButton("OK", null)
                        .show();
            }
        }) {
            @Override protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("idGame", game.getIdGame());
                params.put("name", game.getName());
                params.put("token", token);
                return params;
            }
        };
        queue.add(request);
    }

    private void selectScore(final Game game)
    {
        RequestQueue queue = Volley.newRequestQueue(this);
        String URL = "http://www.arturviader.com/socialgamesnetwork/selectScore";
        int score;
        StringRequest request = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override public void onResponse(String response) {

                        Gson gson = new Gson();
                        Type typeToken = new TypeToken<Data>() {}.getType();
                        Data apiResponse = gson.fromJson(response.toString(), typeToken);
                        scoresGame = (Score[]) apiResponse.getData();

                    }
                }, new Response.ErrorListener() {
            @Override public void onErrorResponse(VolleyError error) {
                AlertDialog show = new AlertDialog.Builder(RegisterActivity.this)
                        .setTitle("Error")
                        .setMessage(R.string.networkerror)
                        .setNeutralButton("OK", null)
                        .show();
            }
        }) {
            @Override protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("idGame", game.getIdGame());
                params.put("token", token);
                return params;
            }
        };
        queue.add(request);
    }

    private void selectCountScore(final Game game)
    {
        RequestQueue queue = Volley.newRequestQueue(this);
        String URL = "http://www.arturviader.com/socialgamesnetwork/selectCountScore";
        int score;
        StringRequest request = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override public void onResponse(String response) {

                        Gson gson = new Gson();
                        Type typeToken = new TypeToken<Data>() {}.getType();
                        Data apiResponse = gson.fromJson(response.toString(), typeToken);
                        countScore = (int) apiResponse.getData();

                    }
                }, new Response.ErrorListener() {
            @Override public void onErrorResponse(VolleyError error) {
                AlertDialog show = new AlertDialog.Builder(RegisterActivity.this)
                        .setTitle("Error")
                        .setMessage(R.string.networkerror)
                        .setNeutralButton("OK", null)
                        .show();
            }
        }) {
            @Override protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("idGame", game.getIdGame());
                params.put("token", token);
                return params;
            }
        };
        queue.add(request);
    }



    /**
     * Insert opinion
     * Verified
     * @param opinion
     */

    private void insertOpinion(final Opinion opinion)
    {
        RequestQueue queue = Volley.newRequestQueue(this);
        String URL = "http://www.arturviader.com/socialgamesnetwork/insertOpinion";
        StringRequest request = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override public void onResponse(String response) {

                        Gson gson = new Gson();
                        Type typeToken = new TypeToken<Data>() {}.getType();
                        Data apiResponse = gson.fromJson(response.toString(), typeToken);

                        if(apiResponse.getErrorCode()!=0)
                        {
                            AlertDialog show = new AlertDialog.Builder(RegisterActivity.this)
                                    .setTitle("Error")
                                    .setMessage(R.string.errorInsertOpinion)
                                    .setNeutralButton("OK", null)
                                    .show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override public void onErrorResponse(VolleyError error) {
                AlertDialog show = new AlertDialog.Builder(RegisterActivity.this)
                        .setTitle("Error")
                        .setMessage(R.string.networkerror)
                        .setNeutralButton("OK", null)
                        .show();
            }
        }) {
            @Override protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", opinion.getEmail());
                params.put("idGame", opinion.getIdGame());
                params.put("text", opinion.getText());
                params.put("token", token);
                return params;
            }
        };
        queue.add(request);
    }

    /**
     * Insert recommendation
     * Verified
     * @param recommendation
     */
    private void insertRecommendation(final Recommendation recommendation)
    {
        RequestQueue queue = Volley.newRequestQueue(this);
        String URL = "http://www.arturviader.com/socialgamesnetwork/insertRecommendation";
        StringRequest request = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override public void onResponse(String response) {

                        Gson gson = new Gson();
                        Type typeToken = new TypeToken<Data>() {}.getType();
                        Data apiResponse = gson.fromJson(response.toString(), typeToken);

                        if(apiResponse.getErrorCode()!=0)
                        {
                            AlertDialog show = new AlertDialog.Builder(RegisterActivity.this)
                                    .setTitle("Error")
                                    .setMessage(R.string.errorInsertRecommendation)
                                    .setNeutralButton("OK", null)
                                    .show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override public void onErrorResponse(VolleyError error) {
                AlertDialog show = new AlertDialog.Builder(RegisterActivity.this)
                        .setTitle("Error")
                        .setMessage(R.string.networkerror)
                        .setNeutralButton("OK", null)
                        .show();
            }
        }) {
            @Override protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", recommendation.getEmail());
                params.put("idGameBase", recommendation.getIdGameBase());
                params.put("idGameRecommended", recommendation.getIdGameRecommended());
                params.put("text", recommendation.getText());
                params.put("token", token);
                return params;
            }
        };
        queue.add(request);
    }

    /**
     * Insert score
     * Verified
     * @param score
     */
    private void insertScore(final Score score)
    {
        RequestQueue queue = Volley.newRequestQueue(this);
        String URL = "http://www.arturviader.com/socialgamesnetwork/insertScore";
        StringRequest request = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override public void onResponse(String response) {

                        Gson gson = new Gson();
                        Type typeToken = new TypeToken<Data>() {}.getType();
                        Data apiResponse = gson.fromJson(response.toString(), typeToken);

                        if(apiResponse.getErrorCode()!=0)
                        {
                            AlertDialog show = new AlertDialog.Builder(RegisterActivity.this)
                                    .setTitle("Error")
                                    .setMessage(R.string.errorInsertScore)
                                    .setNeutralButton("OK", null)
                                    .show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override public void onErrorResponse(VolleyError error) {
                AlertDialog show = new AlertDialog.Builder(RegisterActivity.this)
                        .setTitle("Error")
                        .setMessage(R.string.networkerror)
                        .setNeutralButton("OK", null)
                        .show();
            }
        }) {
            @Override protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", score.getEmail());
                params.put("idGame", score.getIdGame());
                params.put("positive", score.getPositive().toString());
                params.put("token", token);
                return params;
            }
        };
        queue.add(request);
    }

    /**
     * Insert favourite
     * Verified
     * @param favourite
     */
    private void insertFavourite(final Favourite favourite)
    {
        RequestQueue queue = Volley.newRequestQueue(this);
        String URL = "http://www.arturviader.com/socialgamesnetwork/insertFavourite";
        StringRequest request = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override public void onResponse(String response) {

                        Gson gson = new Gson();
                        Type typeToken = new TypeToken<Data>() {}.getType();
                        Data apiResponse = gson.fromJson(response.toString(), typeToken);

                        if(apiResponse.getErrorCode()!=0)
                        {
                            AlertDialog show = new AlertDialog.Builder(RegisterActivity.this)
                                    .setTitle("Error")
                                    .setMessage(R.string.errorInsertFavourite)
                                    .setNeutralButton("OK", null)
                                    .show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override public void onErrorResponse(VolleyError error) {
                AlertDialog show = new AlertDialog.Builder(RegisterActivity.this)
                        .setTitle("Error")
                        .setMessage(R.string.networkerror)
                        .setNeutralButton("OK", null)
                        .show();
            }
        }) {
            @Override protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", favourite.getEmail());
                params.put("idGame", favourite.getIdGame());
                params.put("token", token);
                return params;
            }
        };
        queue.add(request);
    }


    /**
     * Insert history
     * @param history
     * verified
     */
    private void insertHistory(final History history)
    {
        RequestQueue queue = Volley.newRequestQueue(this);
        String URL = "http://www.arturviader.com/socialgamesnetwork/insertHistory";
        StringRequest request = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override public void onResponse(String response) {

                        Gson gson = new Gson();
                        Type typeToken = new TypeToken<Data>() {}.getType();
                        Data apiResponse = gson.fromJson(response.toString(), typeToken);

                        if(apiResponse.getErrorCode()!=0)
                        {
                            AlertDialog show = new AlertDialog.Builder(RegisterActivity.this)
                                    .setTitle("Error")
                                    .setMessage(R.string.errorInsertHistory)
                                    .setNeutralButton("OK", null)
                                    .show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override public void onErrorResponse(VolleyError error) {
                AlertDialog show = new AlertDialog.Builder(RegisterActivity.this)
                        .setTitle("Error")
                        .setMessage(R.string.networkerror)
                        .setNeutralButton("OK", null)
                        .show();
            }
        }) {
            @Override protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", history.getEmail());
                params.put("idGame", history.getIdGame());
                params.put("token", token);
                return params;
            }
        };
        queue.add(request);
    }

    /**
     * Update history date
     * verified
     * @param history
     */
    private void updateHistory(final History history)
    {
        RequestQueue queue = Volley.newRequestQueue(this);
        String URL = "http://www.arturviader.com/socialgamesnetwork/updateHistory";
        StringRequest request = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override public void onResponse(String response) {

                        Gson gson = new Gson();
                        Type typeToken = new TypeToken<Data>() {}.getType();
                        Data apiResponse = gson.fromJson(response.toString(), typeToken);

                        if(apiResponse.getErrorCode()!=0)
                        {
                            AlertDialog show = new AlertDialog.Builder(RegisterActivity.this)
                                    .setTitle("Error")
                                    .setMessage(R.string.errorUpdateHistory)
                                    .setNeutralButton("OK", null)
                                    .show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override public void onErrorResponse(VolleyError error) {
                AlertDialog show = new AlertDialog.Builder(RegisterActivity.this)
                        .setTitle("Error")
                        .setMessage(R.string.networkerror)
                        .setNeutralButton("OK", null)
                        .show();
            }
        }) {
            @Override protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", history.getEmail());
                params.put("idGame", history.getIdGame());
                params.put("token", token);
                return params;
            }
        };
        queue.add(request);
    }

    /**
     * Update history date
     * verified
     * @param recommendation
     */
    private void updateRecommendation(final Recommendation recommendation)
    {
        RequestQueue queue = Volley.newRequestQueue(this);
        String URL = "http://www.arturviader.com/socialgamesnetwork/updateRecommendation";
        StringRequest request = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override public void onResponse(String response) {

                        Gson gson = new Gson();
                        Type typeToken = new TypeToken<Data>() {}.getType();
                        Data apiResponse = gson.fromJson(response.toString(), typeToken);

                        if(apiResponse.getErrorCode()!=0)
                        {
                            AlertDialog show = new AlertDialog.Builder(RegisterActivity.this)
                                    .setTitle("Error")
                                    .setMessage(R.string.errorUpdateRecommendation)
                                    .setNeutralButton("OK", null)
                                    .show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override public void onErrorResponse(VolleyError error) {
                AlertDialog show = new AlertDialog.Builder(RegisterActivity.this)
                        .setTitle("Error")
                        .setMessage(R.string.networkerror)
                        .setNeutralButton("OK", null)
                        .show();
            }
        }) {
            @Override protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", recommendation.getEmail());
                params.put("idGameBase", recommendation.getIdGameBase());
                params.put("idGameRecommended", recommendation.getIdGameRecommended());
                params.put("text", recommendation.getText());
                params.put("token", token);
                return params;
            }
        };
        queue.add(request);
    }

    /**
     * Update opinion
     * verified
     * @param opinion
     */
    private void updateOpinion(final Opinion opinion)
    {
        RequestQueue queue = Volley.newRequestQueue(this);
        String URL = "http://www.arturviader.com/socialgamesnetwork/updateOpinion";
        StringRequest request = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override public void onResponse(String response) {

                        Gson gson = new Gson();
                        Type typeToken = new TypeToken<Data>() {}.getType();
                        Data apiResponse = gson.fromJson(response.toString(), typeToken);

                        if(apiResponse.getErrorCode()!=0)
                        {
                            AlertDialog show = new AlertDialog.Builder(RegisterActivity.this)
                                    .setTitle("Error")
                                    .setMessage(R.string.errorUpdateOpinion)
                                    .setNeutralButton("OK", null)
                                    .show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override public void onErrorResponse(VolleyError error) {
                AlertDialog show = new AlertDialog.Builder(RegisterActivity.this)
                        .setTitle("Error")
                        .setMessage(R.string.networkerror)
                        .setNeutralButton("OK", null)
                        .show();
            }
        }) {
            @Override protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", opinion.getEmail());
                params.put("idGame", opinion.getIdGame());
                params.put("text", opinion.getText());
                params.put("token", token);
                return params;
            }
        };
        queue.add(request);
    }

    /**
     * Update score
     * verified
     * @param score
     */
    private void updateScore(final Score score)
    {
        RequestQueue queue = Volley.newRequestQueue(this);
        String URL = "http://www.arturviader.com/socialgamesnetwork/updateScore";
        StringRequest request = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override public void onResponse(String response) {

                        Gson gson = new Gson();
                        Type typeToken = new TypeToken<Data>() {}.getType();
                        Data apiResponse = gson.fromJson(response.toString(), typeToken);

                        if(apiResponse.getErrorCode()!=0)
                        {
                            AlertDialog show = new AlertDialog.Builder(RegisterActivity.this)
                                    .setTitle("Error")
                                    .setMessage(R.string.errorUpdateScore)
                                    .setNeutralButton("OK", null)
                                    .show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override public void onErrorResponse(VolleyError error) {
                AlertDialog show = new AlertDialog.Builder(RegisterActivity.this)
                        .setTitle("Error")
                        .setMessage(R.string.networkerror)
                        .setNeutralButton("OK", null)
                        .show();
            }
        }) {
            @Override protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", score.getEmail());
                params.put("idGame", score.getIdGame());
                params.put("positive", score.getPositive().toString());
                params.put("token", token);
                return params;
            }
        };
        queue.add(request);
    }

    private void updateUser(final User user)
    {
        RequestQueue queue = Volley.newRequestQueue(this);
        String URL = "http://www.arturviader.com/socialgamesnetwork/updateUser";
        StringRequest request = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override public void onResponse(String response) {

                        Gson gson = new Gson();
                        Type typeToken = new TypeToken<Data>() {}.getType();
                        Data apiResponse = gson.fromJson(response.toString(), typeToken);

                        if(apiResponse.getErrorCode()!=0)
                        {
                            AlertDialog show = new AlertDialog.Builder(RegisterActivity.this)
                                    .setTitle("Error")
                                    .setMessage(R.string.errorUpdateScore)
                                    .setNeutralButton("OK", null)
                                    .show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override public void onErrorResponse(VolleyError error) {
                AlertDialog show = new AlertDialog.Builder(RegisterActivity.this)
                        .setTitle("Error")
                        .setMessage(R.string.errorUpdateUser)
                        .setNeutralButton("OK", null)
                        .show();
            }
        }) {
            @Override protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", user.getEmail());
                params.put("password", user.getPassword());
                params.put("token", token);
                return params;
            }
        };
        queue.add(request);
    }


    /**
     * Delete favourite
     * @param favourite
     * verified
     */
    private void deleteFavourite(final Favourite favourite)
    {
        RequestQueue queue = Volley.newRequestQueue(this);
        String URL = "http://www.arturviader.com/socialgamesnetwork/deleteFavourite";
        StringRequest request = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override public void onResponse(String response) {

                        Gson gson = new Gson();
                        Type typeToken = new TypeToken<Data>() {}.getType();
                        Data apiResponse = gson.fromJson(response.toString(), typeToken);

                        if(apiResponse.getErrorCode()!=0)
                        {
                            AlertDialog show = new AlertDialog.Builder(RegisterActivity.this)
                                    .setTitle("Error")
                                    .setMessage(R.string.errorDeleteFavourite)
                                    .setNeutralButton("OK", null)
                                    .show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override public void onErrorResponse(VolleyError error) {
                AlertDialog show = new AlertDialog.Builder(RegisterActivity.this)
                        .setTitle("Error")
                        .setMessage(R.string.networkerror)
                        .setNeutralButton("OK", null)
                        .show();
            }
        }) {
            @Override protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", favourite.getEmail());
                params.put("idGame", favourite.getIdGame());
                params.put("token", token);
                return params;
            }
        };
        queue.add(request);
    }

   /* Revisar
    private void deleteRecommendation(final Recommendation recommendation)
    {
        RequestQueue queue = Volley.newRequestQueue(this);
        String URL = "http://www.arturviader.com/socialgamesnetwork/deleteRecommendation";
        StringRequest request = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override public void onResponse(String response) {

                        Gson gson = new Gson();
                        Type typeToken = new TypeToken<Data>() {}.getType();
                        Data apiResponse = gson.fromJson(response.toString(), typeToken);

                        if(apiResponse.getErrorCode()!=0)
                        {
                            AlertDialog show = new AlertDialog.Builder(activity_register.this)
                                    .setTitle("Error")
                                    .setMessage(R.string.errorDeleteRecommendation)
                                    .setNeutralButton("OK", null)
                                    .show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override public void onErrorResponse(VolleyError error) {
                AlertDialog show = new AlertDialog.Builder(activity_register.this)
                        .setTitle("Error")
                        .setMessage(R.string.networkerror)
                        .setNeutralButton("OK", null)
                        .show();
            }
        }) {
            @Override protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", recommendation.getEmail());
                params.put("idGameRecommended", recommendation.getIdGameRecommended());
                params.put("idGameBase", recommendation.getIdGameBase());
                params.put("token", token);
                return params;
            }
        };
        queue.add(request);
    }

    /**
     * Delete opinion
     * @param opinion
     * verified
     */
    private void deleteOpinion(final Opinion opinion)
    {
        RequestQueue queue = Volley.newRequestQueue(this);
        String URL = "http://www.arturviader.com/socialgamesnetwork/deleteOpinion";
        StringRequest request = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override public void onResponse(String response) {

                        Gson gson = new Gson();
                        Type typeToken = new TypeToken<Data>() {}.getType();
                        Data apiResponse = gson.fromJson(response.toString(), typeToken);

                        if(apiResponse.getErrorCode()!=0)
                        {
                            AlertDialog show = new AlertDialog.Builder(RegisterActivity.this)
                                    .setTitle("Error")
                                    .setMessage(R.string.errorDeleteOpinion)
                                    .setNeutralButton("OK", null)
                                    .show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override public void onErrorResponse(VolleyError error) {
                AlertDialog show = new AlertDialog.Builder(RegisterActivity.this)
                        .setTitle("Error")
                        .setMessage(R.string.networkerror)
                        .setNeutralButton("OK", null)
                        .show();
            }
        }) {
            @Override protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", opinion.getEmail());
                params.put("idGame", opinion.getIdGame());
                params.put("token", token);
                return params;
            }
        };
        queue.add(request);
    }

    /**
     * Delete one line of history
     * @param history
     * verified
     */
    private void deleteHistory(final History history)
    {
        RequestQueue queue = Volley.newRequestQueue(this);
        String URL = "http://www.arturviader.com/socialgamesnetwork/deleteHistory";
        StringRequest request = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override public void onResponse(String response) {

                        Gson gson = new Gson();
                        Type typeToken = new TypeToken<Data>() {}.getType();
                        Data apiResponse = gson.fromJson(response.toString(), typeToken);

                        if(apiResponse.getErrorCode()!=0)
                        {
                            AlertDialog show = new AlertDialog.Builder(RegisterActivity.this)
                                    .setTitle("Error")
                                    .setMessage(R.string.errorDeleteHistory)
                                    .setNeutralButton("OK", null)
                                    .show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override public void onErrorResponse(VolleyError error) {
                AlertDialog show = new AlertDialog.Builder(RegisterActivity.this)
                        .setTitle("Error")
                        .setMessage(R.string.networkerror)
                        .setNeutralButton("OK", null)
                        .show();
            }
        }) {
            @Override protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", history.getEmail());
                params.put("idGame", history.getIdGame());
                params.put("token", token);
                return params;
            }
        };
        queue.add(request);
    }

    /**
     * Delete one line of history
     * @param history
     * verified
     */
    private void deleteAllHistory(final User history)
    {
        RequestQueue queue = Volley.newRequestQueue(this);
        String URL = "http://www.arturviader.com/socialgamesnetwork/deleteAllHistory";
        StringRequest request = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override public void onResponse(String response) {

                        Gson gson = new Gson();
                        Type typeToken = new TypeToken<Data>() {}.getType();
                        Data apiResponse = gson.fromJson(response.toString(), typeToken);

                        if(apiResponse.getErrorCode()!=0)
                        {
                            AlertDialog show = new AlertDialog.Builder(RegisterActivity.this)
                                    .setTitle("Error")
                                    .setMessage(R.string.errorDeleteHistory)
                                    .setNeutralButton("OK", null)
                                    .show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override public void onErrorResponse(VolleyError error) {
                AlertDialog show = new AlertDialog.Builder(RegisterActivity.this)
                        .setTitle("Error")
                        .setMessage(R.string.networkerror)
                        .setNeutralButton("OK", null)
                        .show();
            }
        }) {
            @Override protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", history.getEmail());
                params.put("token", token);
                return params;
            }
        };
        queue.add(request);
    }

    /**
     * Delete Score
     * @param score
     */
    private void deleteScore(final Score score)
    {
        RequestQueue queue = Volley.newRequestQueue(this);
        String URL = "http://www.arturviader.com/socialgamesnetwork/deleteScore";
        StringRequest request = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override public void onResponse(String response) {

                        Gson gson = new Gson();
                        Type typeToken = new TypeToken<Data>() {}.getType();
                        Data apiResponse = gson.fromJson(response.toString(), typeToken);

                        if(apiResponse.getErrorCode()!=0)
                        {
                            AlertDialog show = new AlertDialog.Builder(RegisterActivity.this)
                                    .setTitle("Error")
                                    .setMessage(R.string.errorDeleteScore)
                                    .setNeutralButton("OK", null)
                                    .show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override public void onErrorResponse(VolleyError error) {
                AlertDialog show = new AlertDialog.Builder(RegisterActivity.this)
                        .setTitle("Error")
                        .setMessage(R.string.networkerror)
                        .setNeutralButton("OK", null)
                        .show();
            }
        }) {
            @Override protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", score.getEmail());
                params.put("idGame", score.getIdGame());
                params.put("token", token);
                return params;
            }
        };
        queue.add(request);
    }



    private void llegeixtoken()
    {
        SharedPreferences prefs=PreferenceManager.getDefaultSharedPreferences(this);
        token = prefs.getString("usertoken","");
    }

    private void guardatoken(String token, String correu) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(null);
        SharedPreferences.Editor ed = prefs.edit();
        ed.putString("usertoken", token);
        ed.putString("email", correu);
        ed.commit();
    }

}
