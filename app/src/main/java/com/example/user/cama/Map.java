package com.example.user.cama;

import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class Map extends AppCompatActivity {
    public static int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION =1;
    private FusedLocationProviderClient mFusedLocationClient;
//    private LocationCallback mLocationCallback;
//    private LocationRequest mLocationRequest;
//    private boolean mRequestingLocationUpdates = true;
//    protected final static String REQUESTING_LOCATION_UPDATES_KEY = "requesting-location-updates-key";

    private double userLatitude, userLongtitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        if ( ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {

            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);

        }
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            userLatitude = location.getLatitude();
                            userLongtitude = location.getLongitude();
                        }
                    }
                });
        ImageButton userButton = new ImageButton(this);

//        String baseDir = Environment.getExternalStorageDirectory().getAbsolutePath();
//        String fileName = "locationIcon.jpg";
//        String pathName = "\\images\\" + fileName;
//        Bitmap bmp = BitmapFactory.decodeFile(pathName);
//        userButton.setImageBitmap(bmp);
        userButton.setImageResource(R.drawable.ic_location_icon);
        LinearLayout.LayoutParams params =  new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        //params.setMargins(20,20,20,20);
        userButton.setLayoutParams(params);

//        mLocationCallback = new LocationCallback() {
//            @Override
//            public void onLocationResult(LocationResult locationResult) {
//                if (locationResult == null) {
//                    return;
//                }
//                for (Location location : locationResult.getLocations()) {
//                    // Update UI with location data
//                    // ...
//                }
//            };
//        };


    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        if (mRequestingLocationUpdates) {
//            startLocationUpdates();
//        }
//    }
//
//    private void startLocationUpdates() {
//        mFusedLocationClient.requestLocationUpdates(mLocationRequest,
//                mLocationCallback,
//                null /* Looper */);
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        stopLocationUpdates();
//    }
//
//    private void stopLocationUpdates() {
//        mFusedLocationClient.removeLocationUpdates(mLocationCallback);
//    }
//
//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        outState.putBoolean(REQUESTING_LOCATION_UPDATES_KEY,
//                mRequestingLocationUpdates);
//        // ...
//        super.onSaveInstanceState(outState);
//    }
//
//    private void updateValuesFromBundle(Bundle savedInstanceState) {
//        if (savedInstanceState == null) {
//            return;
//        }
//
//        // Update the value of mRequestingLocationUpdates from the Bundle.
//        if (savedInstanceState.keySet().contains(REQUESTING_LOCATION_UPDATES_KEY)) {
//            mRequestingLocationUpdates = savedInstanceState.getBoolean(
//                    REQUESTING_LOCATION_UPDATES_KEY);
//        }
//
//        // ...
//
//        // Update UI to match restored state
//        updateUI();
//    }

}


