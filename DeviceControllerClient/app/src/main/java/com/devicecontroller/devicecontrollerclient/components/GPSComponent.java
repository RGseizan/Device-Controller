package com.devicecontroller.devicecontrollerclient.components;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.devicecontroller.devicecontrollerclient.connection.AppProtocole;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.concurrent.Executor;

public class GPSComponent extends CComponent {

    private static FusedLocationProviderClient fusedLocationClient;
    public static String GPSLoc;

    public static String getPosition(Activity activity) {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient (activity);
        final StringBuilder sb = new StringBuilder ();
        /*
            Ajout permission AndroidManifest
            <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
            <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>
            Bloquant si l'application n'as pas les autorisations
         */
        if (ActivityCompat.checkSelfPermission (activity.getApplicationContext (), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission (activity.getApplicationContext (), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Log.e ("NULL", "MANQUE AUTORISATION : " + sb.toString ());
            return null;
        }

        // TUTO : https://developer.android.com/training/location/retrieve-current#play-services
        // (ajouter dans Gradle (Module:app) :  implementation "com.google.android.gms:play-services-location:15.0.1"
        fusedLocationClient.getLastLocation ()
                .addOnSuccessListener (activity, new OnSuccessListener<Location> () {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        Log.e ("NULL", "NO LOC : " + sb.toString ());
                        if (location != null) {
                            // Logic to handle location object
                            Log.e ("location.getLatitude ()", "LAT : " + location.getLatitude ());
                            Log.e ("location.getLong ()", "LON : " + location.getLongitude ());
                            sb.append (location.getLatitude ());
                            sb.append (AppProtocole.DATA_SEPARATOR);
                            sb.append (location.getLongitude ());
                            Log.e ("SB1", "VAL SB : " + sb.toString ());
                            GPSLoc = sb.toString ();
                        }
                    }
                });

        Log.e ("SB2", "VAL SB : " + sb.toString ());
        return sb.toString ();
    }
}
