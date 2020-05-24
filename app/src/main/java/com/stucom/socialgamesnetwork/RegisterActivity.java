package com.stucom.socialgamesnetwork;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
import com.stucom.socialgamesnetwork.ui.login.LoginActivity;

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
    Button btnSignIn;

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
        btnSignIn = findViewById(R.id.bRegister);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!tEmail.getText().toString().isEmpty() && !tUsername.getText().toString().isEmpty() && !tName.getText().toString().isEmpty() && !tSurname.getText().toString().isEmpty() && !tPassword.getText().toString().isEmpty() && !tConfirm.getText().toString().isEmpty()) {
                    if (tPassword.getText().toString().equals(tConfirm.getText().toString())) {
                        signIn();
                    } else {
                        Toast.makeText(RegisterActivity.this, R.string.incorrectConfirmation, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(RegisterActivity.this, R.string.emptyFields, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void signIn() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String URL = "http://www.arturviader.com/socialgamesnetwork/insertUser";
        StringRequest request = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        Type typeToken = new TypeToken<Data>() {
                        }.getType();
                        Data apiResponse = gson.fromJson(response, typeToken);
                        if (apiResponse.getErrorCode() == 0) {
                            final EditText etCode = new EditText(RegisterActivity.this);
                            new AlertDialog.Builder(RegisterActivity.this)
                                    .setTitle(R.string.insertcode1)
                                    .setMessage(R.string.insertcode2)
                                    .setView(etCode)
                                    .setPositiveButton(R.string.register, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int whichButton) {
                                            int codi = Integer.parseInt(etCode.getText().toString());
                                            verifyCode(codi);
                                        }
                                    })
                                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int whichButton) {

                                        }
                                    })
                                    .show();
                        } else {
                            Toast.makeText(RegisterActivity.this, apiResponse.getErrorMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RegisterActivity.this, R.string.networkerror, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
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

    private void verifyCode(final int code) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String URL = "http://www.arturviader.com/socialgamesnetwork/loginConfirmation";
        StringRequest request = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        Type typeToken = new TypeToken<Data>() {
                        }.getType();
                        Data apiResponse = gson.fromJson(response, typeToken);
                        if (apiResponse.getErrorCode() == 0) {
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(RegisterActivity.this, apiResponse.getErrorMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RegisterActivity.this, R.string.networkerror, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", tEmail.getText().toString());
                params.put("password", tPassword.getText().toString());
                params.put("confirmation", Integer.toString(code));
                return params;
            }
        };
        queue.add(request);
    }
}
