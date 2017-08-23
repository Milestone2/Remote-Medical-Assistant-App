package com.example.android.milestone.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.example.android.milestone.bluetoothGattBLE.DeviceScanActivity;

/**
 * Created by Owner on 8/19/2017.
 */

public class SettingFragment extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i= new Intent(getContext(), DeviceScanActivity.class);
        startActivity(i);

    }
}
