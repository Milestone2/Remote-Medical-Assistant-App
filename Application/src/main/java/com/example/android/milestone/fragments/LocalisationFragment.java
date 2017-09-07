package com.example.android.milestone.fragments;

import android.Manifest;
import android.app.Dialog;
import android.content.pm.PackageManager;

import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.example.android.bluetoothlegatt.R;
import com.example.android.milestone.GPSTracker;
import com.example.android.milestone.MenuActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.ErrorDialogFragment;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.concurrent.Executor;

import static android.content.Context.LOCATION_SERVICE;
import static com.example.android.bluetoothlegatt.R.id.tvLongitude;

/**
 * Created by Emmanuel Roodly on 24/08/2017.
 */

public class LocalisationFragment extends Fragment implements OnMapReadyCallback {

    // private Location currentLocation = null;
    //private LocationRequest mlocationRequest;
    private long UPDATE_INTERVAL = 60000;
    private long FASTEST_INTERVAL = 5000;
    private final static String KEY_LOCATION = "location";



    boolean isGPSEnabled = false;
    // Flag for network status
    boolean isNetworkEnabled = false;
    // Flag for GPS status
    boolean canGetLocation = false;
    // Location location; // Location
    double latitude; // Latitude
    double longitude;
    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters
    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute


    // protected LocationManager locationManager;

    private final static int CONNECTION_FAILURE_RESOLUTION = 9000;
    private FusedLocationProviderClient mFusedLocationClient;

    public LatLng myloc;

    TextView tvLongitude;
    TextView tvLatitude;
    TextView tvgpsName;
    GoogleMap map;
    MapView mapView;
    SupportMapFragment supportMapFragment;

    BackendlessUser userInfo;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View racine = inflater.inflate(R.layout.localisation_ui, container, false);
        coordinatesGPS();
        tvLongitude = (TextView) racine.findViewById(R.id.tvLongitude);
        tvLatitude = (TextView) racine.findViewById(R.id.tvLatitude);
        tvgpsName = (TextView) racine.findViewById(R.id.tvgpsName);
        mapView = (MapView) racine.findViewById(R.id.mapView);

        userInfo = Backendless.UserService.CurrentUser();
        tvLongitude.setText(String.valueOf(longitude));
        tvLatitude.setText(String.valueOf(latitude));
        tvgpsName.setText(userInfo.getProperty("Nom").toString());

        mapView.onCreate(savedInstanceState);
        mapView.onResume();


        //currentLocation = new Location("dummyProvider");
        //currentLocation = getLocation();
        //myloc = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());

        //Initialisation de la carte
        mapView.getMapAsync(this);
        MapsInitializer.initialize(this.getContext());


        return racine;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        this.map = googleMap;
        this.map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        LatLng sydney = new LatLng(latitude, longitude);
        this.map.addMarker(new MarkerOptions().position(sydney).title("this is where I m"));

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

public  void  coordinatesGPS(){

    try{
    longitude = MenuActivity.gps.currentLocation().getLongitude();
        latitude = MenuActivity.gps.currentLocation().getLatitude();
    }catch (Exception e){
        Toast.makeText(getContext(),"Coordinates Err:"+ e.getMessage(),Toast.LENGTH_SHORT).show();
    }
}

}

