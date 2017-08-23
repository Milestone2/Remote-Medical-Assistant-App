package com.example.android.milestone.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.example.android.milestone.SendActivity;

/**
 * Created by Owner on 8/21/2017.
 */

public class SendFragment extends Fragment{
    //SendActivity send;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SendActivity send=((SendActivity)getActivity());
        //send.sendEmail();
        //send.sendSMS("33515777");
        //Toast.makeText(getContext(),"yes yes",Toast.LENGTH_LONG).show();
        send.test();


    }

}
