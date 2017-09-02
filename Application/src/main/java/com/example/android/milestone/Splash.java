package com.example.android.milestone;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;

import com.example.android.bluetoothlegatt.R;

import static com.backendless.servercode.services.codegen.ServiceCodeFormat.android;

public class Splash extends Activity {

    int time = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(Splash.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        }, time);
    }

}
