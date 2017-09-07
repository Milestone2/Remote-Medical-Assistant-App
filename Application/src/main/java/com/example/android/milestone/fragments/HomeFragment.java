package com.example.android.milestone.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bluetoothlegatt.R;


import java.util.Random;

import static com.example.android.milestone.RandomData.*;

/**
 * Created by Owner on 8/19/2017.
 */

public class HomeFragment extends Fragment {
    public TextView tvHeartInfo;
    public TextView tvLungInfo;
    public TextView tvTempInfo;
    FloatingActionButton floatingHeart;
    FloatingActionButton floatingLung;
    FloatingActionButton floatingTemp;
    final int max = 99;
    final int min = 60;
    int status = 32;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View racine_status = inflater.inflate(R.layout.home, container, false);
        tvHeartInfo = (TextView) racine_status.findViewById(R.id.tvHeartInfo);
        tvLungInfo = (TextView) racine_status.findViewById(R.id.tvLungInfo);
        tvTempInfo = (TextView) racine_status.findViewById(R.id.tvTempInfo);
        floatingHeart = (FloatingActionButton) racine_status.findViewById(R.id.floatingHeart);
        floatingLung = (FloatingActionButton) racine_status.findViewById(R.id.floatingLung);
        floatingTemp = (FloatingActionButton) racine_status.findViewById(R.id.floatingTemp);
        tvHeartInfo.setText("Chargement...");
        tvLungInfo.setText("Chargement...");
        tvTempInfo.setText("Chargement...");

       new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                new Timer().scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        Random r = new Random();
                        status = r.nextInt(max - ( min + 1)) + min;
                    }

                }, 0, 20000);
            }
        }, 1000);

        floatingHeart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvHeartInfo.setText(status + "BPM");
            }
        });


        return racine_status;
    }

    public static HomeFragment newInstance(){
        HomeFragment homeFragment = new HomeFragment();
        Bundle args = new Bundle();
        homeFragment.setArguments(args);
        return homeFragment;
    }


    @Override
    public void onResume() {
        super.onResume();
        //update current time view after every 1 seconds
        final Handler handler=new Handler();

        final Runnable updateTask=new Runnable() {
            @Override
            public void run() {
                updateTextView();
                handler.postDelayed(this,120000);
            }
        };

        handler.postDelayed(updateTask,120000);
    }

    public void updateTextView(){
        tvHeartInfo.setText(" "+ pulse() + " bpm ");
        tvLungInfo.setText(" "+ respiration() + " bpm " );
    }

}


