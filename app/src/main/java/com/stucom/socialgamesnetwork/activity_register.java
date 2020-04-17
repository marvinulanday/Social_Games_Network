package com.stucom.socialgamesnetwork;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.stucom.socialgamesnetwork.model.Data;
import com.stucom.socialgamesnetwork.model.Game;
import com.stucom.socialgamesnetwork.model.Opinion;
import com.stucom.socialgamesnetwork.model.Recommendation;
import com.stucom.socialgamesnetwork.model.Score;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class activity_register extends AppCompatActivity {
    TextView tEmail;
    TextView tUsername;
    TextView tName;
    TextView tSurname;
    TextView tPassword;
    TextView tConfirm;
    Button bRegister;
    String token;

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
                if(!tEmail.getText().toString().isEmpty() && !tUsername.getText().toString().isEmpty() && !tName.getText().toString().isEmpty() && !tSurname.getText().toString().isEmpty() && !tPassword.getText().toString().isEmpty() && !tConfirm.getText().toString().isEmpty())
                {
                    if (tPassword.getText().toString().equals(tConfirm.getText().toString()))
                    {
                        registra();
                        //Game game = new Game("10","Sonic");
                        //insertGame(game);
                    }
                    else
                    {
                        AlertDialog show = new AlertDialog.Builder(activity_register.this)
                                .setTitle("Error")
                                .setMessage(R.string.incorrectConfirmation)
                                .setNeutralButton("OK", null)
                                .show();
                    }
                }
                else
                {
                    AlertDialog show = new AlertDialog.Builder(activity_register.this)
                            .setTitle("Error")
                            .setMessage(R.string.emptyFields)
                            .setNeutralButton("OK", null)
                            .show();
                }
            }
        });
    }

    private void registra()
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

                            final EditText tcodi = new EditText(activity_register.this);

                            new AlertDialog.Builder(activity_register.this)
                                    .setTitle(R.string.insertcode1)
                                    .setMessage(R.string.insertcode2)
                                    .setView(tcodi)
                                    .setPositiveButton(R.string.register, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int whichButton) {
                                            int codi = Integer.parseInt(tcodi.getText().toString());
                                            confirmacodi(codi);

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
                            AlertDialog show = new AlertDialog.Builder(activity_register.this)
                                    .setTitle("Error")
                                    .setMessage("Error: " + apiResponse.getErrorMsg())
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

    private void confirmacodi(final int codi)
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

                            AlertDialog show = new AlertDialog.Builder(activity_register.this)
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
                            AlertDialog show = new AlertDialog.Builder(activity_register.this)
                                    .setTitle("Error")
                                    .setMessage("Error: " + apiResponse.getErrorMsg())
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
                params.put("email", tEmail.getText().toString());
                params.put("password", tPassword.getText().toString());
                params.put("confirmation", Integer.toString(codi));
                return params;
            }
        };
        queue.add(request);
    }

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
                            AlertDialog show = new AlertDialog.Builder(activity_register.this)
                                    .setTitle("Error")
                                    .setMessage(R.string.errorInsertGame)
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
                params.put("idGame", game.getIdGame());
                params.put("name", game.getName());
                params.put("token", token);
                return params;
            }
        };
        queue.add(request);
    }

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
                            AlertDialog show = new AlertDialog.Builder(activity_register.this)
                                    .setTitle("Error")
                                    .setMessage(R.string.errorInsertGame)
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
                params.put("email", opinion.getEmail());
                params.put("idGame", opinion.getIdGame());
                params.put("text", opinion.getText());
                params.put("token", token);
                return params;
            }
        };
        queue.add(request);
    }

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
                            AlertDialog show = new AlertDialog.Builder(activity_register.this)
                                    .setTitle("Error")
                                    .setMessage(R.string.errorInsertGame)
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
                params.put("idGameBase", recommendation.getIdGameBase());
                params.put("idGameRecommended", recommendation.getIdGameRecommended());
                params.put("text", recommendation.getText());
                params.put("token", token);
                return params;
            }
        };
        queue.add(request);
    }

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
                            AlertDialog show = new AlertDialog.Builder(activity_register.this)
                                    .setTitle("Error")
                                    .setMessage(R.string.errorInsertGame)
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
                params.put("email", score.getEmail());
                params.put("idGame", score.getIdGame());
                params.put("positive", score.getPositive().toString());
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
