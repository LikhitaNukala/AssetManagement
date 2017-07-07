package com.example.android.myscannerapp;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;

import com.google.android.gms.instantapps.ActivityCompat;

/**
 * Created by Likhita on 19-06-2017.
 */

class LocationTrack extends Service implements LocationListener {
    @Nullable
    private Context mContext;
    boolean checkGPS = false;
    boolean checkNetwork = false;
    boolean canGetLocation = false;
    Location loc;
    double latitude;
    double longitude;
    private static final long MIN_TIME_BTW_UPDATES = 1000 * 60 * 10;
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;

    protected LocationManager locationManager;

    public LocationTrack(Context mContext) {
        locationManager = (LocationManager) mContext
                .getSystemService(LOCATION_SERVICE);
    }


    public Location getLocation(String provider) {
        if (locationManager.isProviderEnabled(provider)) {
            if (android.support.v4.app.ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && android.support.v4.app.ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                if ((Activity) mContext != null && android.support.v4.app.ActivityCompat.checkSelfPermission((Activity) mContext, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && android.support.v4.app.ActivityCompat.checkSelfPermission((Activity) mContext, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    android.support.v4.app.ActivityCompat.requestPermissions((Activity) mContext, new String[]{
                            android.Manifest.permission.ACCESS_FINE_LOCATION
                    }, 10);
                }

                locationManager.requestLocationUpdates(provider,
                        MIN_TIME_BTW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                if (locationManager != null) {
                    loc = locationManager.getLastKnownLocation(provider);
                    return loc;
                }

            }
            return null;
        }
        return null;
    }
        // if (android.support.v4.app.ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && android.support.v4.app.ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        // TODO: Consider calling
        //    ActivityCompat#requestPermissions
        // here to request the missing permissions, and then overriding
        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
        //                                          int[] grantResults)
        // to handle the case where the user grants the permission. See the documentation
        // for ActivityCompat#requestPermissions for more details.

        /*int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                if (android.support.v4.app.ActivityCompat.shouldShowRequestPermissionRationale((Activity) mContext, android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getApplicationContext());
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("Location permission is necessary");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {

                            if (mContext instanceof MainActivity) {
                                MainActivity.requestRuntimeForLocation();
                            }
//                            android.support.v4.app.ActivityCompat.requestPermissions((Activity)mContext, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
                        }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();

                } *//*else {
                    android.support.v4.app.ActivityCompat.requestPermissions((Activity)mContext, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
                }*//*
            }*/

            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.






        /*try {
           // locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);
            checkGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
           // checkNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            if (checkGPS || checkNetwork) {
                this.canGetLocation = true;

            }
            if (checkGPS) {
                if (android.support.v4.app.ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && android.support.v4.app.ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.


                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BTW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    if(locationManager!=null){
                        loc=locationManager.getLastKnownLocation()
                        }
                    }
                }
            }
            if(checkNetwork){
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,MIN_TIME_BTW_UPDATES,MIN_DISTANCE_CHANGE_FOR_UPDATES,this);
                if (locationManager!=null){
                    loc=locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    if (loc!=null){
                        latitude=loc.getLatitude();
                        longitude=loc.getLongitude();

                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();

        }
        return loc;*/


    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
    public double getLongitude() {
        if (loc != null) {
            longitude = loc.getLongitude();
        }
        return longitude;
    }

    public double getLatitude() {
        if (loc != null) {
            latitude = loc.getLatitude();
        }
        return latitude;
    }

    public boolean canGetLocation() {
        return this.canGetLocation;
    }
    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);


        alertDialog.setTitle("GPS is not Enabled!");

        alertDialog.setMessage("Do you want to turn on GPS?");


        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                mContext.startActivity(intent);
            }
        });


        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });


        alertDialog.show();
    }


    public void stopListener() {
        if (locationManager != null) {

            if (android.support.v4.app.ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && android.support.v4.app.ActivityCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationManager.removeUpdates(LocationTrack.this);
        }
    }

}
