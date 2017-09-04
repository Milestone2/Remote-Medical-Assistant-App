package com.example.android.milestone.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.bluetoothlegatt.R;
import com.google.android.gms.vision.text.Text;

import static com.example.android.bluetoothlegatt.R.id.etEmail;
import static com.example.android.bluetoothlegatt.R.id.etName;
import static com.example.android.bluetoothlegatt.R.id.etNumber1;
import static com.example.android.bluetoothlegatt.R.id.etNumber2;
import static com.example.android.bluetoothlegatt.R.id.etPrenom;
import static com.example.android.bluetoothlegatt.R.id.tvH_date;

/**
 * Created by Emmanuel Roodly on 03/09/2017.
 */

public class AddHistory extends DialogFragment implements DatePicker.DateListener {

    EditText etSummary;
    EditText etDetail;
    Button btSaveHistory;
    Button btH_cancel;
    ImageButton ibDate;
    FragmentManager fm;
    public String historydate = "";
    DatePicker datePicker;
    TextView tvDateH;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View racine = inflater.inflate(R.layout.add_history, null);

        etSummary = (EditText) racine.findViewById(R.id.etSummary);
        etDetail = (EditText) racine.findViewById(R.id.etDetail);
        btSaveHistory = (Button) racine.findViewById(R.id.btSavehistory);
        btH_cancel = (Button) racine.findViewById(R.id.btH_cancel);
        ibDate = (ImageButton) racine.findViewById(R.id.ibCalendar);
        tvDateH = (TextView) racine.findViewById(R.id.tvdateH);
        fm = getFragmentManager();
        datePicker = new DatePicker();

        ibDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker.setTargetFragment(AddHistory.this, 300);
                datePicker.show(fm, "Date");
            }
        });


        btSaveHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(tvDateH.getText().toString())) {
                    if (!TextUtils.isEmpty(etSummary.getText().toString()) && !TextUtils.isEmpty(etDetail.getText().toString())) {
                        SendHistoryInfo();
                        dismiss();
                    }
                } else {
                    ibDate.setBackgroundColor(Color.CYAN);
                    Toast.makeText(getContext(), "Choisissez une date", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btH_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        return racine;
    }

    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);

    }


    @Override
    public void onFinishEditDate(int day, int month, int year) {
        tvDateH.setText(day + "/" + month + "/" + year);
        ibDate.setBackgroundColor(Color.TRANSPARENT);
    }

    //Definition de l'interface permettant d'envoyer les informations du rendez-vous pour les enregistrer
    public interface HistoryListener {
        void onFinishEditHistory(String summary, String detail, String histDate);
    }

    public void SendHistoryInfo() {
        HistoryListener listener = (HistoryListener) getTargetFragment();
        listener.onFinishEditHistory(etSummary.getText().toString(), etDetail.getText().toString(), tvDateH.getText().toString());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
