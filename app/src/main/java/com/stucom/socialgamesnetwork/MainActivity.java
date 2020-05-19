package com.stucom.socialgamesnetwork;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.stucom.socialgamesnetwork.DAO.SharedPrefsManagement;
import com.stucom.socialgamesnetwork.ui.login.LoginActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String token = SharedPrefsManagement.getData(MainActivity.this, "token");
        Intent intent;
        Log.d("SGN", "Token --> " + token);
        if (token.isEmpty()) {
            intent = new Intent(getApplicationContext(), LoginActivity.class);
        } else {
            intent = new Intent(getApplicationContext(), HomeActivity.class);
        }
        startActivity(intent);
    }

}
