package com.example.android.milestone.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.bluetoothlegatt.R;
import com.example.android.milestone.MenuActivity;
import com.example.android.milestone.SendActivity;


import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.android.milestone.RandomData.*;

/**
 * Created by Owner on 8/19/2017.
 */

public class HomeFragment extends Fragment {
    public TextView tvHeartInfo;
    public TextView tvLungInfo;
    public TextView tvTempInfo;
    ImageView ivHeart;
    ImageView ivLung;
    ImageView ivTemp;
    final int max = 99;
    final int min = 60;
    int status = 32;
    SendActivity send;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View racine_status = inflater.inflate(R.layout.home, container, false);

        tvHeartInfo = (TextView) racine_status.findViewById(R.id.tvHeartInfo);
        tvLungInfo = (TextView) racine_status.findViewById(R.id.tvLungInfo);
        tvTempInfo = (TextView) racine_status.findViewById(R.id.tvTempInfo);
        ivHeart = (ImageView) racine_status.findViewById(R.id.ivHeart);
        ivLung = (ImageView) racine_status.findViewById(R.id.ivLung);
        ivTemp = (ImageView) racine_status.findViewById(R.id.ivTemp);

        Animation pulseAnim = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.pulse);
        Animation lungAnim = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.lung);
        Animation tempAnim = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.temp);

        ivHeart.startAnimation(pulseAnim);
        ivLung.startAnimation(lungAnim);
        ivTemp.startAnimation(tempAnim);

        return racine_status;
    }

    public static HomeFragment newInstance() {
        HomeFragment homeFragment = new HomeFragment();
        Bundle args = new Bundle();
        homeFragment.setArguments(args);
        return homeFragment;
    }


    @Override
    public void onResume() {
        super.onResume();
        //update current time view after every 1 seconds
        final Handler handler = new Handler();

        final Runnable updateTask = new Runnable() {
            @Override
            public void run() {
                updateTextView();
                handler.postDelayed(this, 30000);
            }
        };

        handler.postDelayed(updateTask, 30000);
    }

    public void updateTextView() {
        int pulse = pulse();
        int resp = respiration();
        tvHeartInfo.setText(" " + pulse + " bpm ");
        tvLungInfo.setText(" " + resp + " bpm ");
        //tvTempInfo.setText(" " + temp() + " ° C");
        sos(pulse, resp);
    }


    public final void sos(int pulse, int resp) {
        if ((pulse < 30 || pulse > 120) || (resp < 10 || resp > 30)) {
            send = new SendActivity();
            //send.sendSMS("33515777",pulse,resp);

            for (int i = 0; i < MenuActivity.c.size(); i++) {
                send.sendSMS(String.valueOf(MenuActivity.c.get(i).getTel()), pulse, resp);
            }

            //Toast.makeText(getContext(), "SOS SMS", Toast.LENGTH_LONG).show();
        }
    }

}


