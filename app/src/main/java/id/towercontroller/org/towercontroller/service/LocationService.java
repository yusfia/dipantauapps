package id.towercontroller.org.towercontroller.service;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import org.json.JSONObject;

import id.towercontroller.org.towercontroller.config.Constants;

/**
 * Created by Hafid on 11/29/2017.
 */

public class LocationService extends Service implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {
    private LocationRequest mLocationRequest;
    private GoogleApiClient mGoogleApiClient;
    private String TAG = this.getClass().getSimpleName();
    private double latitude, longitude;

    public LocationService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public void onCreate() {
        Log.i(TAG, "Service Created");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "Service Started");
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(1 * 1000)
                .setFastestInterval(1 * 1000);
        if (!mGoogleApiClient.isConnected()) {
            mGoogleApiClient.connect();
        }
        return START_NOT_STICKY;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "Permission Denied");
        } else {
            Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            Log.i(TAG, "LOCATION CONNECTED");
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
            if (location == null) {
                Log.i(TAG, "LOCATION NULL");
                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

            } else {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                Intent broadcastIntent = new Intent();
                broadcastIntent.setAction(Constants.actionPosition);
                broadcastIntent.putExtra("latitude", latitude);
                broadcastIntent.putExtra("longitude", longitude);
                sendBroadcast(broadcastIntent);
            }
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "Connection Suspended");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i(TAG, "Connection Failed");
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.i(TAG, "Location Changed");
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction(Constants.actionPosition);
        broadcastIntent.putExtra("latitude", latitude);
        broadcastIntent.putExtra("longitude", longitude);
        sendBroadcast(broadcastIntent);
    }

    @Override
    public void onDestroy() {
        if (mGoogleApiClient.isConnected()) mGoogleApiClient.disconnect();
        super.onDestroy();
    }
}