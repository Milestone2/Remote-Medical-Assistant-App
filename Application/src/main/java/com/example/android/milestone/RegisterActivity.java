package com.example.android.milestone;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.example.android.bluetoothlegatt.R;

import java.io.IOException;

public class RegisterActivity extends AppCompatActivity {

    EditText etR_nom;
    EditText etR_doctor;
    EditText etR_email;
    EditText etR_pass1;
    EditText etR_pass2;
    EditText etR_Telephone;
    EditText etR_poids;
    EditText etR_birth;
    EditText etR_hauteur;
    EditText etR_groupeS;
    Button btnRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        etR_nom = (EditText) findViewById(R.id.etR_name);
        etR_doctor = (EditText) findViewById(R.id.etR_prenom);
        etR_email = (EditText) findViewById(R.id.etR_email);
        etR_Telephone = (EditText) findViewById(R.id.etR_telephone);
        etR_pass1 = (EditText) findViewById(R.id.etR_pass1);
        etR_pass2 = (
                EditText) findViewById(R.id.etR_pass2);
        etR_poids = (EditText) findViewById(R.id.etR_poids);
        etR_birth = (EditText) findViewById(R.id.etR_birth);
        etR_hauteur = (EditText) findViewById( R.id.etR_hauteur);
        etR_groupeS = (EditText) findViewById(R.id.etR_GroupeS);
        btnRegister = (Button) findViewById(R.id.btnRegister);

        Backendless.setUrl( Defaults.SERVER_URL );
        Backendless.initApp( getApplicationContext(), Defaults.APPLICATION_ID, Defaults.API_KEY );


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etR_pass1.getText().toString().equals(etR_pass2.getText().toString())){//Passwords match
                    if(!TextUtils.isEmpty(etR_nom.getText().toString())){
                        if(!TextUtils.isEmpty(etR_email.getText().toString())){
                            if(isNetworkAvailable()) {
                                if(isOnline()) {
                                    registerUser(etR_nom.getText().toString(),
                                            etR_email.getText().toString(),
                                            etR_pass1.getText().toString(),
                                            etR_Telephone.getText().toString(),
                                            etR_doctor.getText().toString(),
                                            Double.valueOf(etR_hauteur.getText().toString()),
                                            Double.valueOf(etR_poids.getText().toString()),
                                            etR_birth.getText().toString(),
                                            etR_groupeS.getText().toString());
                                }else{
                                    Toast.makeText(RegisterActivity.this, "No internet ", Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                Toast.makeText(RegisterActivity.this, "Use Wi-Fi or Mobile Network", Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(RegisterActivity.this, "email adress is required", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(RegisterActivity.this, "Name is required", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(RegisterActivity.this, "Passwords must be the same", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }


    public void registerUser(String name, String email, String password, String tel, String doctor, double height, double weight, String birth, String gs){

        BackendlessUser user = new BackendlessUser();
        user.setEmail(email);
        user.setPassword(password);
        user.setProperty("Nom", name);
        user.setProperty("Doctor", doctor);
        user.setProperty("Telephone", tel);
        user.setProperty("birth", birth);
        user.setProperty("height", height);
        user.setProperty("weight", weight);
        user.setProperty("GS", gs);


        Backendless.UserService.register(user, new AsyncCallback<BackendlessUser>() {
            @Override
            public void handleResponse(BackendlessUser response) {
                Toast.makeText(RegisterActivity.this, "Register succesfull \n please confirm your registration by email", Toast.LENGTH_SHORT).show();
                finish();

            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(RegisterActivity.this, "error ", Toast.LENGTH_SHORT).show();
                Log.d("DEBUG", fault.getMessage().toString());
            }
        });
    }

    public Boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

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
}
