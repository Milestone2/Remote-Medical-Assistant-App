package com.example.android.milestone.fragments;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bluetoothlegatt.R;

/**
 * Created by Owner on 8/19/2017.
 */

public class HomeFragment extends Fragment {
    TextView tvHeartInfo;
    TextView tvLungInfo;
    TextView tvTempInfo;
    FloatingActionButton floatingHeart;
    FloatingActionButton floatingLung;
    FloatingActionButton floatingTemp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View racine_status = inflater.inflate(R.layout.home_ui, container, false);
        tvHeartInfo = (TextView) racine_status.findViewById(R.id.tvHeartInfo);
        tvLungInfo = (TextView) racine_status.findViewById(R.id.tvLungInfo);
        tvTempInfo = (TextView) racine_status.findViewById(R.id.tvTempInfo);
        floatingHeart = (FloatingActionButton) racine_status.findViewById(R.id.floatingHeart);
        floatingLung = (FloatingActionButton) racine_status.findViewById(R.id.floatingLung);
        floatingTemp = (FloatingActionButton) racine_status.findViewById(R.id.floatingTemp);

        return racine_status;
    }

    public static HomeFragment newInstance(){
        HomeFragment homeFragment = new HomeFragment();
        Bundle args = new Bundle();
        homeFragment.setArguments(args);
        return homeFragment;
    }
}
