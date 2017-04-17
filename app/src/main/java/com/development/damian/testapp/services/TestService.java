package com.development.damian.testapp.services;

import android.Manifest;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.development.damian.testapp.ApplicationState;
import com.development.damian.testapp.MainActivity;
import com.development.damian.testapp.R;
import com.development.damian.testapp.utils.Constants;


import java.text.SimpleDateFormat;


public class TestService extends Service implements LocationListener {

    Intent i;
    private LocationManager mgr = null;
    private String TAG = this.getClass().getSimpleName();
    private ApplicationState appState;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    private static int countLocations = 0;

    private Context mContext;

    boolean isGPSEnabled = false;
    boolean isNetworkEnabled = false;
    boolean canGetLocation = false;

    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 0;
    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 10000;

    private Handler hndSendLocation;
    private Runnable rnaSendLocation;

    protected LocationManager mLocationManager;

    public TestService() {
    }


    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate");
        appState = ((ApplicationState) getApplicationContext());
        mgr = (LocationManager) getSystemService(LOCATION_SERVICE);
        mContext = getApplicationContext();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (appState.isLogged()) {
            if (intent != null) {
                if (intent.getAction().equals(Constants.STARTFOREGROUND_ACTION)) {

                    getLocation();

                    int icon = R.mipmap.ic_launcher;
                    Notification statusBarNotification = new Notification(icon, "TestApp", 0);

                    statusBarNotification.flags |= Notification.FLAG_ONGOING_EVENT;

                    Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);
                    notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    startForeground(1, statusBarNotification);

                } else if (intent.getAction().equals((Constants.STOPFOREGROUND_ACTION))) {
                    stopForeground(false);
                    stopSelf();
                }
            }

            if (hndSendLocation == null) {
                hndSendLocation = new Handler();
                rnaSendLocation = new Runnable() {
                    @Override
                    public void run() {

                        //SendLocation();

                        hndSendLocation.postDelayed(this, 10000);

                    }
                };

                hndSendLocation.postDelayed(rnaSendLocation, 10000);
            }

        } else {
            stopForeground(false);
            stopSelf();
            if (hndSendLocation != null) {
                hndSendLocation.removeCallbacks(rnaSendLocation);
            }
        }

        return START_STICKY;
    }

    /**
     * Stop using GPS listener Calling this function will stop using GPS in your
     * app
     * */
    public void stopUsingGPS() {
        if (mLocationManager != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            mLocationManager.removeUpdates(TestService.this);
        }
    }

    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);

        // Setting Dialog Title
        alertDialog.setTitle(R.string.app_name);

        // Setting Dialog Message
        alertDialog
                .setMessage(R.string.MsgWithOutGeo);

        // On pressing Settings button
        alertDialog.setPositiveButton(getResources().getString(R.string.MsgGoToSettings),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(
                                Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        mContext.startActivity(intent);
                    }
                });

        // on pressing cancel button
        alertDialog.setNegativeButton(mContext.getResources().getString(R.string.cancel),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        // Showing Alert Message
        alertDialog.show();
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onLocationChanged(Location location) {
        SaveLocation(location);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    public void getLocation() {
        try {
            mLocationManager = (LocationManager) getApplicationContext()
                    .getSystemService(LOCATION_SERVICE);

            isGPSEnabled = mLocationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

            isNetworkEnabled = mLocationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {
                showSettingsAlert();
            } else {
                canGetLocation = true;
                if (isGPSEnabled) {
                    appState.setStateGPS(1);
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    mLocationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    Log.d("GPS", "GPS Enabled");
                }
                if (isNetworkEnabled) {
                    mLocationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    Log.d("Network", "Network Enabled");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        //stopUsingGPS();
    }

    private void SaveLocation(Location loc){
        Log.i(TAG, "onLocationChanged");
        Log.i(getClass().getSimpleName(),
                String.format("%s @ %d %f:%f %f (%f meters)",
                        loc.getProvider(),
                        loc.getTime(),
                        loc.getLatitude(),
                        loc.getLongitude(),
                        loc.getSpeed(),
                        loc.getAccuracy()));

        try {
            if(loc.getAccuracy() > 100 && appState.getStateGPS() != 3)
                appState.setStateGPS(2);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

   /* private void SendLocation() {
        if(UserInfo.getInstance(getApplicationContext()).hasSessionID()){

            Location location;
            location = UserInfo.getInstance(getApplicationContext()).getCurrentLocation();

            Date sDateTime = new Date(System.currentTimeMillis());

            String textToLog = "DATETIME: " + simpleDateFormat.format(sDateTime.getTime()) + "\n" +
                    "LATITUDE: " + location.getLatitude() + "\n" +
                    "LONGITUDE: " + location.getLongitude() + "\n" +
                    "-------------------";

            Utililities.appendLog("LocationServiceLog", textToLog);

            Log.i(TAG, "countLocations: " + countLocations);

            if(Utililities.isOnline(getApplicationContext())){

                Log.i(TAG, "Online");

                if(location != null){

                    TimeZone tz = TimeZone.getDefault();
                    int timezone = (tz.getRawOffset()/3600000);
                    String GMT =  (timezone >= 0)?"+"+ Integer.toString(timezone) : Integer.toString(timezone);

                    try {
                        JSONArray arrayJson = new JSONArray();

                        JSONObject objectJson = new JSONObject();
                        objectJson.put("latitude", location.getLatitude());
                        objectJson.put("longitude", location.getLongitude());
                        objectJson.put("speed", location.getSpeed());
                        int GSPActive = 0;
                        if (location.getProvider().equals("gps")){
                            GSPActive = 1;
                        }
                        objectJson.put("isGPSActive", GSPActive);
                        objectJson.put("datetime", simpleDateFormat.format(location.getTime()) + GMT);
                        objectJson.put("lostPackages", countLocations);
                        arrayJson.put(objectJson);

                        DoActions(arrayJson);

                        countLocations++;

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            }

        }
    }*/

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (hndSendLocation != null) {
            hndSendLocation.removeCallbacks(rnaSendLocation);
        }
    }

}
