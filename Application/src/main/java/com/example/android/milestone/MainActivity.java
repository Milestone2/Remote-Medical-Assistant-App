package com.example.android.milestone;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import android.support.design.widget.Snackbar;
import android.support.transition.ChangeBounds;
import android.support.transition.Fade;
import android.support.transition.Scene;
import android.support.transition.TransitionManager;
import android.support.transition.TransitionSet;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.local.UserTokenStorageFactory;
import com.example.android.bluetoothlegatt.R;
import com.example.android.milestone.fragments.ResetPasswordFragment;
import com.example.android.milestone.models.User;

import java.io.IOException;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;


public class MainActivity extends AppCompatActivity {
    EditText etUserName;
    EditText etPassword;
    TextView tvResetPassword;
    TextView tvRegister;
    Button btLogin;
    private Toolbar toolbar;
    FragmentManager fm;
    ResetPasswordFragment reset;
    public String userToken;
    ProgressBar progressLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        etUserName = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        tvRegister = (TextView) findViewById(R.id.tvRegister);
        btLogin = (Button) findViewById(R.id.btLogIn);

        tvResetPassword = (TextView) findViewById(R.id.tvResetPassword);
        progressLogin = (ProgressBar) findViewById(R.id.progressLogin);
        progressLogin.setVisibility(View.INVISIBLE);
        reset = new ResetPasswordFragment();
        fm = getSupportFragmentManager();
        enableViews();
        Backendless.setUrl( Defaults.SERVER_URL );
        Backendless.initApp( getApplicationContext(), Defaults.APPLICATION_ID, Defaults.API_KEY );






        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                disableViews();
                if (isNetworkAvailable()) {//check connection
                    if (isOnline()) {//check internet
                        if (!TextUtils.isEmpty(etUserName.getText().toString())) {//email not null
                            if (!TextUtils.isEmpty(etPassword.getText().toString())) {//password not null
                                progressLogin.setVisibility(View.VISIBLE);
                                logIn(etUserName.getText().toString(), etPassword.getText().toString());//login
                            } else {
                                Snackbar.make(view, "Entrer votre mot de passe", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                                enableViews();
                            }
                        } else {
                            Snackbar.make(view, "Entrer votre addresse mail", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                            enableViews();
                        }
                    }else{
                        Snackbar.make(view, "Pas d'accès internet", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                        enableViews();
                    }
                }else{
                    Snackbar.make(view, "utilisé le Wi-Fi ou le réseau mobile", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    enableViews();
                }
            }
        });

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(a);
            }
        });

        tvResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset.show(fm, "Reset");
            }
        });
    }


    public void enableViews(){
        etUserName.setEnabled(true);
        etPassword.setEnabled(true);
        tvResetPassword.setClickable(true);
        tvRegister.setClickable(true);
    }
    public  void disableViews(){
        etUserName.setEnabled(false);
        etPassword.setEnabled(false);
        tvResetPassword.setClickable(false);
        tvRegister.setClickable(false);
    }


    public  void logIn(String email, String password){
        Backendless.UserService.login(email, password, new AsyncCallback<BackendlessUser>() {
            @Override
            public void handleResponse(BackendlessUser response) {
                Toast.makeText(MainActivity.this, "Log in succesfull", Toast.LENGTH_SHORT).show();
                Log.d("DEBUG", response.toString());
                Intent i = new Intent(getApplicationContext(),MenuActivity.class);
                i.putExtra("userInfo", response);
                progressLogin.setVisibility(View.INVISIBLE);
                startActivity(i);
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Snackbar.make(getCurrentFocus(), fault.getMessage(), Snackbar.LENGTH_LONG).setAction("Action", null).show();
                progressLogin.setVisibility(View.INVISIBLE);
                Log.d("DEBUG", fault.toString());
                enableViews();
            }
        }, true);
    }

    //check connection before login
    public Boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    //Check internet before  login
    public boolean isOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int     exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        } catch (IOException e)          { e.printStackTrace(); }
        catch (InterruptedException e) { e.printStackTrace(); }
        return false;
    }

    private void goToScene(Scene scene) {
        ChangeBounds changeBounds = new ChangeBounds();
        Fade fadeOut = new Fade(Fade.OUT);
        Fade fadeIn = new Fade(Fade.IN);
        TransitionSet transition = new TransitionSet();
        transition.setOrdering(TransitionSet.ORDERING_SEQUENTIAL);
        transition
                .addTransition(fadeOut)
                .addTransition(changeBounds)
                .addTransition(fadeIn);
        TransitionManager.go(scene, transition);
    }


}
