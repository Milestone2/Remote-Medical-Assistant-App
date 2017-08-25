package com.example.android.milestone.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.android.bluetoothlegatt.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Owner on 8/19/2017.
 */

public class ProfileFragment extends Fragment {

    Spinner spTemp;
    List<String> temp;//Liste d'element a mettre au spinner
    ArrayAdapter<String> adapterTemp;//Adpater liant la liste d'element au Spinner

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View racine_profil = inflater.inflate(R.layout.profil_ui, null);
        spTemp = (Spinner) racine_profil.findViewById(R.id.spTemperature); //Spinner qui doit afficher C | F
        temp = new ArrayList<>();
        temp.add("˚C");
        temp.add("˚F");
        adapterTemp = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, temp);
        adapterTemp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTemp.setAdapter(adapterTemp);
        spTemp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getContext(), spTemp.getSelectedItem().toString() , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return racine_profil;
    }
}
