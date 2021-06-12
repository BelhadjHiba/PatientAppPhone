package com.learntodroid.androidqrcodescanner;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.firebase.firestore.GeoPoint;
import com.learntodroid.androidqrcodescanner.utils.Save;

public class LocationService extends Service {
    private FusedLocationProviderClient fusedLocationProviderClient;
    private String pID;

    @SuppressLint("MissingPermission")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        pID= Save.read(this,"patientID",null);
//        fusedLocationProviderClient.getCurrentLocation().addOnCompleteListener()
        LocationManager mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000 * 30
                    , 5, mLocationListener);


        return START_STICKY;

    }
    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) {
            //your code here
            Constants.db.collection("Patient").document(pID).update("location", new GeoPoint(location.getLatitude(),location.getLongitude()));
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
