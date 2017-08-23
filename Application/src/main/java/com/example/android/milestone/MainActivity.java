package com.example.android.milestone;


import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.android.bluetoothlegatt.R;


public class MainActivity extends AppCompatActivity {
    EditText etUserName;
    EditText etPassword;
    TextView tvResetPassword;
    Button btLogin;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        etUserName = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btLogin = (Button) findViewById(R.id.btLogIn);
        tvResetPassword = (TextView) findViewById(R.id.tvResetPassword);


        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent i = new Intent(getApplicationContext(),MenuActivity.class);
                startActivity(i);

            }
        });
    }

}
