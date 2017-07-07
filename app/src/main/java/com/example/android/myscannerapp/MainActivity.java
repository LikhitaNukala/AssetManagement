package com.example.android.myscannerapp;/*
package com.example.android.myscannerapp;

import android.*;
import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.SimpleDateFormat;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;
import android.provider.Settings.Secure;


import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity implements LocationListener {
    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";
    final int MY_PERMISSIONS_REQUEST_LOCATION = 123;
    private static final int PERMISSION_REQUEST_CODE = 200;
    TextView unique_id;
    private ArrayList permissionsToRequest;
    private ArrayList permissionsRejected = new ArrayList();
    private ArrayList permissions = new ArrayList();
    protected static final int REQUEST_CHECK_SETTINGS = 0x1;
    private static final int ALL_PERMISSIONS_RESULT = 101;
    LocationTrack locationTrack;
    StoreLocationHelper helper = new StoreLocationHelper(this);
    public Context mContext;
    double latitude, longitude;
    Button apps_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unique_id = (TextView) findViewById(R.id.unique_id);
        final LocationTrack locationTrack = new LocationTrack(this);
        setOnListenerButton();
        String wifiName=getWifiName(this);
        unique_id.setText(wifiName);


        Button btn_location = (Button) findViewById(R.id.loc_btn);
        btn_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //checkLocationPermission();
                displayLocationSettingsRequest(getApplicationContext());
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);
                }
            }

            public void OnRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
                switch (requestCode) {
                    case PERMISSION_REQUEST_CODE:
                        if (grantResults.length > 0) {
                            boolean locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                            if (locationAccepted)
                                Toast.makeText(MainActivity.this, "Permission Granted, Now you can access location data.", Toast.LENGTH_LONG).show();
                            else {
                                Toast.makeText(MainActivity.this, "Permission Denied, You cannot access location data and camera.", Toast.LENGTH_LONG).show();
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    if (shouldShowRequestPermissionRationale(ACCESS_FINE_LOCATION)) {
                                        showMessageOKCancel("You need to allow access to location permission",
                                                new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                            requestPermissions(new String[]{ACCESS_FINE_LOCATION},
                                                                    PERMISSION_REQUEST_CODE);
                                                        }
                                                    }
                                                });
                                        return;
                                    }
                                }

                            }
                        }
                        break;
                }

            }
        });
        Button button_sim = (Button) findViewById(R.id.sim_btn);
        button_sim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SIMActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setOnListenerButton() {
        //final Context context=MainActivity.this;
        apps_button = (Button) findViewById(R.id.apps_btn);
        apps_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AppsActivity.class);
                startActivity(intent);
            }

        });
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(MainActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }


    public String getWifiName(Context context) {
        WifiManager manager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (manager.isWifiEnabled()) {
            WifiInfo wifiInfo = manager.getConnectionInfo();
            if (wifiInfo != null) {
                NetworkInfo.DetailedState state = WifiInfo.getDetailedStateOf(wifiInfo.getSupplicantState());
                if (state == NetworkInfo.DetailedState.CONNECTED || state == NetworkInfo.DetailedState.OBTAINING_IPADDR) {
                    return wifiInfo.getSSID();
                }
            }
        }
        return null;
    }


    private void showSettingsDialog(String provider) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                MainActivity.this);

        alertDialog.setTitle(provider + " SETTINGS");

        alertDialog
                .setMessage(provider + " is not enabled! Want to go to settings menu?");

        alertDialog.setPositiveButton("Settings",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(
                                Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        MainActivity.this.startActivity(intent);
                    }
                });

        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alertDialog.show();
    }


    private void saveLocation(double latitude, double longitude) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        Calendar c = Calendar.getInstance();
        String date = String.valueOf(c.get(Calendar.YEAR) + c.get(Calendar.MONTH) + c.get(Calendar.DATE));
        String time = String.valueOf(c.get(Calendar.HOUR) + c.get(Calendar.MINUTE) + c.get(Calendar.SECOND));
        cv.put(LocationContract.LocationEntry.TIME, date + " " + time);
        cv.put(LocationContract.LocationEntry.LATITUDE, latitude);
        cv.put(LocationContract.LocationEntry.LONGITUDE, longitude);
        db.insert(LocationContract.LocationEntry.TABLE_NAME, null, cv);
        Toast.makeText(this, "Time is:" + time + "location is" + latitude + longitude, Toast.LENGTH_LONG).show();
        return;
    }


    public void barcodeScan(View view) {
        try {
            Intent intent = new Intent(ACTION_SCAN);
            intent.putExtra("SCAN_MODE", "PRODUCT_MODE");
            startActivityForResult(intent, 0);
        } catch (ActivityNotFoundException e) {
            showDialog(MainActivity.this, "No scanner found", "Download a scanner code activity?", "Yes", "No").show();
        }
    }


    public void QRScan(View view) {
        Log.d("qr scan", "this func is invoked");
        try {
            Intent intent = new Intent(ACTION_SCAN);
            intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
            startActivityForResult(intent, 0);
        } catch (ActivityNotFoundException e) {
            showDialog(MainActivity.this, "Scanner not found", "Download a scanner activity", "Yes", "No").show();


        }
    }

    private AlertDialog showDialog(final Activity act, CharSequence title, CharSequence msg, CharSequence yes, CharSequence no) {
        AlertDialog.Builder downloadDialog = new AlertDialog.Builder(act);
        downloadDialog.setTitle(title);
        downloadDialog.setMessage(msg);
        downloadDialog.setPositiveButton(yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Uri uri = Uri.parse("market://search?q=pname:" + "com.google.zxing.client.android");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    act.startActivity(intent);

                } catch (ActivityNotFoundException e) {

                }
            }
        });
        downloadDialog.setNegativeButton(no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        return downloadDialog.show();

    }

    public void getUniqueId(View v) {


        String android_id = Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        telephonyManager.getDeviceId();
        unique_id.setText("Unique id:" + android_id + "\n IMEI number:" + telephonyManager);


    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == REQUEST_CHECK_SETTINGS) {
            if (resultCode == RESULT_OK) {
                String text = intent.getStringExtra("SCAN_RESULT");
                String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
                Toast toast = Toast.makeText(this, "text: " + text + "Format :" + format, Toast.LENGTH_LONG);
                toast.show();
            }
        }
    }

    private void displayLocationSettingsRequest(Context context) {
        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API).build();
        googleApiClient.connect();

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(1000 * 60);
        locationRequest.setFastestInterval(10000 / 2);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override

            public void onResult(@NonNull LocationSettingsResult result) {

                final Status status = result.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        LocationManager locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);


                        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        Log.d("location",String.valueOf(location));
                        if (location != null) {
                            latitude=location.getLatitude();
                            longitude=location.getLongitude();
                            Log.d("old","lat :  "+latitude);
                            Log.d("old","long :  "+longitude);
                            MainActivity.this.onLocationChanged(location);
                        }

                        Log.i("LocationSettingsStatus", "All location settings are satisfied.");
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        Log.i("LocationSettingsStatus", "Location settings are not satisfied. Show the user a dialog to upgrade location settings ");

                        try {
                            // Show the dialog by calling startResolutionForResult(), and check the result
                            // in onActivityResult().
                            status.startResolutionForResult(MainActivity.this, REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            Log.i("LocationSettingsStatus", "PendingIntent unable to execute request.");
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        Log.i("LocationSettingsStatus", "Location settings are inadequate, and cannot be fixed here. Dialog not created.");
                        break;
                }
            }
        });


    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d("onLocationChanged",String.valueOf(location));
        double lat=location.getLatitude();
        double longi=location.getLongitude();
        Log.d("lat",String.valueOf(lat));
        Log.d("longi",String.valueOf(longi));
        Calendar c = Calendar.getInstance();
        String date = String.valueOf(c.get(Calendar.YEAR) + c.get(Calendar.MONTH) + c.get(Calendar.DATE));
        String time = String.valueOf(c.get(Calendar.HOUR) + c.get(Calendar.MINUTE) + c.get(Calendar.SECOND));

        unique_id.setText("Time,Lat,Long:"+date+" "+time+" "+String.valueOf(lat)+" "+String.valueOf(longi));
        saveLocation(lat,longi);

    }
    public void getListOfApps(){


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

}
*/

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.android.myscannerapp.PagerAdapter;
import com.example.android.myscannerapp.R;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("SIM"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab 2"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab 3"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

//@Override
//public boolean onCreateOptionsMenu(Menu menu) {
//getMenuInflater().inflate(R.menu.menu_main, menu);
//return true;
//}
//
//@Override
//public boolean onOptionsItemSelected(MenuItem item) {
//int id = item.getItemId();
//if (id == R.id.action_settings) {
//return true;
//}
//
//return super.onOptionsItemSelected(item);
//}
}
