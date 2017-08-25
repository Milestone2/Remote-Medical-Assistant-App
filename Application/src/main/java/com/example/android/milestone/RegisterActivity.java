package com.example.android.milestone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.example.android.bluetoothlegatt.R;

public class RegisterActivity extends AppCompatActivity {

    EditText etR_nom;
    EditText etR_prenom;
    EditText etR_email;
    EditText etR_pass1;
    EditText etR_pass2;
    Button btnRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        etR_nom = (EditText) findViewById(R.id.etR_name);
        etR_prenom = (EditText) findViewById(R.id.etR_prenom);
        etR_email = (EditText) findViewById(R.id.etR_email);
        etR_pass1 = (EditText) findViewById(R.id.etR_pass1);
        etR_pass2 = (EditText) findViewById(R.id.etR_pass2);
        btnRegister = (Button) findViewById(R.id.btnRegister);

        Backendless.setUrl( Defaults.SERVER_URL );
        Backendless.initApp( getApplicationContext(), Defaults.APPLICATION_ID, Defaults.API_KEY );


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }


    /*public  LoadingCallBack<BackendLessUser> createRegistrationCallback(){

        return new LoadingCallBack<BackendLessUser>(this, "sending registration request...");



    }*/

    public void registerUser(String name, String email, String password, AsyncCallback<BackendlessUser> registrationCallBack){

        BackendlessUser user = new BackendlessUser();
        user.setEmail(etR_email.getText().toString());
        user.setPassword(etR_pass1.getText().toString());
        user.setProperty("name", etR_nom.getText().toString());

        Backendless.UserService.register(user, registrationCallBack);
    }
}
