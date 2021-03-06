package com.example.android.milestone;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.local.UserTokenStorageFactory;
import com.example.android.bluetoothlegatt.R;
import com.example.android.milestone.models.User;

import static com.backendless.servercode.services.codegen.ServiceCodeFormat.android;
import static com.example.android.bluetoothlegatt.R.id.book_now;
import static com.example.android.bluetoothlegatt.R.id.progressLogin;

public class Splash extends Activity {

    int time = 3000;
    public String userToken;
    public Intent start;
    public boolean valid;
    ImageView ivHeart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ivHeart = (ImageView) findViewById(R.id.ivHeart);
        Animation pulseAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.pulse);
        ivHeart.startAnimation(pulseAnim);

        Backendless.setUrl( Defaults.SERVER_URL );
        Backendless.initApp( getApplicationContext(), Defaults.APPLICATION_ID, Defaults.API_KEY );
        start = new Intent (Splash.this, MainActivity.class);

        userToken = UserTokenStorageFactory.instance().getStorage().get();
        start = new Intent(Splash.this, MainActivity.class);

        if( userToken != null && !userToken.equals( "" ) ) {
            String currentUserId = Backendless.UserService.loggedInUser();
            if (!currentUserId.equals("")) {
                Backendless.UserService.findById(currentUserId, new AsyncCallback<BackendlessUser>() {
                    @Override
                    public void handleResponse(BackendlessUser response) {
                        Log.d("DEBUG", response.toString());
                        Backendless.UserService.setCurrentUser(response);
                        start = new Intent(Splash.this, MenuActivity.class);
                        start.putExtra("userInfo", response);
                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {
                        Log.d("DEBUG", fault.getMessage());               
                    }
                });
            }
        }


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(start);
                finish();

            }
        }, time);
    }



}
