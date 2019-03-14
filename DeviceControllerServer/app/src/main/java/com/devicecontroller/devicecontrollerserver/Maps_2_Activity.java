package com.devicecontroller.devicecontrollerserver;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.devicecontroller.devicecontrollerserver.server.ServerManagement;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

public class Maps_2_Activity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_maps_2_);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager ()
                .findFragmentById (R.id.map);
        mapFragment.getMapAsync (this);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient (this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(final GoogleMap googleMap) {
        //TUTO API GOOGLE MAP : https://developers.google.com/maps/documentation/android/start#get-key
        mMap = googleMap;

        Log.e ("TEST LOCATION", "TEST1");
        // Add a marker in Sydney and move the camera
        Log.e ("TEST LOCATION", "TEST2");

        /*
            Ajout permission AndroidManifest
            <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
            <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>
            Bloquant si l'application n'as pas les autorisations
         */
        if (ActivityCompat.checkSelfPermission (this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission (this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Log.e ("TEST LOCATION", "TEST3");
            return;
        }
        Log.e ("TEST LOCATION", "TEST4");

        // TUTO : https://developer.android.com/training/location/retrieve-current#play-services
        // (ajouter dans Gradle (Module:app) :  implementation "com.google.android.gms:play-services-location:15.0.1"
        fusedLocationClient.getLastLocation ()
                .addOnSuccessListener (this, new OnSuccessListener<Location> () {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        Log.e ("!!!Avant null!!", "test0");

                        if (location != null) {
                            // Logic to handle location object

                            Log.e ("LAST LOCATION", "VAL = " + location.toString ());
                            Log.e ("LAST LOCATION", "VAL = " + location.getLatitude ());
                            Log.e ("LAST LOCATION", "VAL = " + location.getLongitude ());
                            //ServerManagement.LOCATION;
                            LatLng myPosition = new LatLng (location.getLatitude (), location.getLongitude ());
                           /* MarkerOptions markerOptions = new MarkerOptions();
                            markerOptions.position(myPosition);
                            markerOptions.title(myPosition.latitude + " : " + myPosition.longitude);
                            mMap.clear();
                            mMap.addMarker (new MarkerOptions ().position (myPosition).title ("DEVICE POSITION"));
                            mMap.moveCamera (CameraUpdateFactory.newLatLng (myPosition));*/


                            mMap.addMarker (new MarkerOptions ().position (myPosition).title ("DEVICE POSITION"));
                            mMap.moveCamera (CameraUpdateFactory.newLatLng (myPosition));
                        }
                    }
                });
    }
}
