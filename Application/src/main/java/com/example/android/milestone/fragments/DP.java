package com.example.android.milestone.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bluetoothlegatt.R;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Emmanuel Roodly on 03/09/2017.
 */

public class DP extends DialogFragment{

    android.widget.DatePicker myDate;
    public Date selectedDate;

    private DateListener listener;


    public MonConnecteur monConnecteur; //Interface gerant les transfert de donnees | Fragment-Activite-Fragment
    static int year;
    static int month;
    static int day;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.date_picker, null);
        getDialog().setTitle("Pick a date");
        myDate = (android.widget.DatePicker) root.findViewById(R.id.myDate); //Castre de l'objet DatePicker
        Calendar calendar = Calendar.getInstance(); //Instanciation d'un object de la Classe Calendar afin de recevoir la date du systeme
        calendar.setTimeInMillis(System.currentTimeMillis());//generer la date actuel a partir du systeme android
        myDate.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), new android.widget.DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(android.widget.DatePicker datePicker, int i, int i1, int i2) {
                //Toast.makeText(getContext(), "year: " +i+ " | month: " +i1+ "| day: " +i2, Toast.LENGTH_SHORT).show();
                year = i;
                month = i1+1;
                day = i2;
                SendDateInfo();
                dismiss();
            }


        });



        return root;
    }




    public interface DateListener{
        void onFinishEditDate(int day, int month, int year);
    }

    public void SendDateInfo(){
        listener = (DateListener) getContext();
        listener.onFinishEditDate(day, month, year);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof DateListener){
            listener = (DateListener) context;
        }
    }

    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);
        if(childFragment instanceof DateListener){
            listener = (DateListener) childFragment;
        }
    }
}
