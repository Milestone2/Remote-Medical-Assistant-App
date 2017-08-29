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
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
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
    BackendlessUser userInfo;
    TextView tvProfilName;
    TextView tvProfilDate;
    TextView tv1;//user address
    TextView tv2;//user phone number
    TextView tv3;//user ID
    TextView tv5;//user main docteur
    TextView tv6;//user height
    TextView tv7;//user birthday
    TextView tvGS;
    TextView tvProfilePoids;//user weigth
    TextView tvTempreceiver;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View racine_profil = inflater.inflate(R.layout.profil_ui, null);
        userInfo = Backendless.UserService.CurrentUser();
        spTemp = (Spinner) racine_profil.findViewById(R.id.spTemperature); //Spinner qui doit afficher C | F
        temp = new ArrayList<>();
        temp.add("˚C");
        temp.add("˚F");

        tv1 = (TextView) racine_profil.findViewById(R.id.tv1);
        tv2 = (TextView) racine_profil.findViewById(R.id.tv2);
        tv3 = (TextView) racine_profil.findViewById(R.id.tv3);
        tv5 = (TextView) racine_profil.findViewById(R.id.tv5);
        tv6 = (TextView) racine_profil.findViewById(R.id.tv6);
        tv7 = (TextView) racine_profil.findViewById(R.id.tv7);
        tvProfilName = (TextView) racine_profil.findViewById(R.id.tvProfilName);
        tvProfilDate = (TextView) racine_profil.findViewById(R.id.tvProfilDate);
        tvGS = (TextView) racine_profil.findViewById(R.id.tvGS);
        tvProfilePoids = (TextView) racine_profil.findViewById(R.id.tvProfilPoids);
        tvTempreceiver = (TextView) racine_profil.findViewById(R.id.tvTempReceiver);

        tvProfilName.setText(userInfo.getProperty("Nom").toString());
        tvProfilDate.setText(userInfo.getEmail());
        tv1.setText("Addresse:  "+ userInfo.getProperty("address").toString());
        tv2.setText("Telephone:  "+ userInfo.getProperty("Telephone").toString());
        tv3.setText("ID:  " + userInfo.getUserId());
        tv5.setText("Docteur (ref.)  : " + userInfo.getProperty("Doctor").toString());
        tvGS.setText("G.S: " + userInfo.getProperty("GS"));
        tv6.setText("Hauteur:  " + userInfo.getProperty("height").toString() + " m");
        tv7.setText("Date de naissance: " + userInfo.getProperty("birth").toString());
        tvProfilePoids.setText("Poids:  " + userInfo.getProperty("weight").toString() + " Kg");



        adapterTemp = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, temp);
        adapterTemp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTemp.setAdapter(adapterTemp);
        final double temf = Integer.valueOf(userInfo.getProperty("temp_m").toString()) * 1.8 + 32;
        final int farei = (int) temf;

        spTemp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(spTemp.getSelectedItemPosition() == 0){
                    tvTempreceiver.setText(userInfo.getProperty("temp_m").toString());
                }else{
                    tvTempreceiver.setText(farei + "");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return racine_profil;
    }

    public  static  ProfileFragment newInstance(BackendlessUser user){
        ProfileFragment profileFragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putSerializable("userInfo", user);
        profileFragment.setArguments(args);
        return profileFragment;
    }
}
