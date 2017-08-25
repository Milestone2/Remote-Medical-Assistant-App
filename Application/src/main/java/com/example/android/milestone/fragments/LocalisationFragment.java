package com.example.android.milestone.fragments;

import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bluetoothlegatt.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by Emmanuel Roodly on 24/08/2017.
 */

public class LocalisationFragment extends Fragment implements OnMapReadyCallback {

    private Location currentBestLocation = null;
    TextView tvLongitude;
    TextView tvLatitude;
    GoogleMap map;
    MapView mapView;
    SupportMapFragment supportMapFragment;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View racine = inflater.inflate(R.layout.localisation_ui, container, false);
        tvLongitude = (TextView) racine.findViewById(R.id.tvLongitude);
        tvLatitude = (TextView) racine.findViewById(R.id.tvLatitude);
        mapView = (MapView) racine.findViewById(R.id.mapView);

        tvLongitude.setText(String.valueOf(151));
        tvLatitude.setText(String.valueOf(-34));

        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        //Initialisation de la carte
        mapView.getMapAsync(this);
        MapsInitializer.initialize(this.getContext());



        return racine;
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {

        this.map = googleMap;
        this.map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        LatLng sydney = new LatLng(-34, 151);
        this.map.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        this.map.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }


    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }


    /*---------- Listener class to get coordinates ------------- */

}

