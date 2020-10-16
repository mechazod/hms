/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2019-2019. All rights reserved.
 */

package com.reactlibrary.hms;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.huawei.hms.location.LocationAvailability;
import com.huawei.hms.location.LocationCallback;
import com.huawei.hms.location.LocationRequest;
import com.huawei.hms.location.LocationResult;
import com.huawei.hms.location.LocationServices;
import com.huawei.hms.location.SettingsClient;
import com.huawei.hms.maps.CameraUpdateFactory;
import com.huawei.hms.maps.HuaweiMap;
import com.huawei.hms.maps.HuaweiMap.OnMapClickListener;
import com.huawei.hms.maps.LocationSource;
import com.huawei.hms.maps.OnMapReadyCallback;
import com.huawei.hms.maps.SupportMapFragment;
import com.huawei.hms.maps.model.BitmapDescriptorFactory;
import com.huawei.hms.maps.model.LatLng;
import com.huawei.hms.maps.model.MarkerOptions;
import com.reactlibrary.R;
import com.reactlibrary.hms.logger.LocationLog;

import java.util.List;

/**
 * 获取位置源信息
 * Get location source information
 */

public class LocationSourceDemoActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = "LocationSourceDemoActiv";

    private PressLocationSource pressLocationSource;

    private SettingsClient settingsClient;

    LocationCallback mLocationCallback;

    LocationRequest mLocationRequest;

    private double latCurrentLocation;
    private double lonCurrentLocation;
    private double latTarget;
    private double lonTarget;

    /**
     * 每当用户点击地图时，报告LocationSource以获得新位置
     * Report LocationSource for new location whenever the user taps on the map
     */
    private static class PressLocationSource implements LocationSource, OnMapClickListener {

        private OnLocationChangedListener locationChangedListener;

        private boolean mPaused;

        @Override
        public void activate(OnLocationChangedListener listener) {
            Log.d(TAG, "activate listener " + listener);
            locationChangedListener = listener;
        }

        @Override
        public void deactivate() {
            Log.d(TAG, "deactivate listener ");
            locationChangedListener = null;
        }

        public void onPause() {
            mPaused = true;
        }

        public void onResume() {
            mPaused = false;
        }

        @Override
        public void onMapClick(LatLng latLng) {
            if (locationChangedListener != null && !mPaused) {
                locationChangedListener.onLocationChanged(getLocation(latLng));
            }
        }

        private Location getLocation(LatLng latLng) {
            Location location = new Location("Provider");
            location.setLatitude(latLng.latitude);
            location.setLongitude(latLng.longitude);
            location.setAccuracy(200);
            return location;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.locationsourcedemo);

        Bundle bundle = getIntent().getExtras();

        this.latCurrentLocation = bundle.getDouble("LAT_CURRENT", 0);
        this.lonCurrentLocation = bundle.getDouble("LON_CURRENT", 0);
        this.latTarget = bundle.getDouble("LAT_TARGET", 14.402940);
        this.lonTarget = bundle.getDouble("LON_TARGET", 120.989517);

        settingsClient = LocationServices.getSettingsClient(this);
        mLocationRequest = new LocationRequest();
        // Set the interval for location updates, in milliseconds.
        mLocationRequest.setInterval(10000);
        // set the priority of the request
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if (null == mLocationCallback) {
            mLocationCallback = new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    if (locationResult != null) {
                        List<Location> locations = locationResult.getLocations();
                    }
                }

                @Override
                public void onLocationAvailability(LocationAvailability locationAvailability) {
                    if (locationAvailability != null) {
                        boolean flag = locationAvailability.isLocationAvailable();
                        LocationLog.i(TAG, "onLocationAvailability isLocationAvailable:" + flag);
                    }
                }
            };
        }

        // check location permisiion
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
            Log.i(TAG, "sdk < 28 Q");
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                String[] strings =
                        {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
                ActivityCompat.requestPermissions(this, strings, 1);
            }
        } else {
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this,
                    "android.permission.ACCESS_BACKGROUND_LOCATION") != PackageManager.PERMISSION_GRANTED) {
                String[] strings = {android.Manifest.permission.ACCESS_FINE_LOCATION,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION,
                        "android.permission.ACCESS_BACKGROUND_LOCATION"};
                ActivityCompat.requestPermissions(this, strings, 2);
            }
        }

        pressLocationSource = new PressLocationSource();

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapForLocationDemo);
        mapFragment.getMapAsync(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        pressLocationSource.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        pressLocationSource.onPause();
    }

    @Override
    public void onMapReady(HuaweiMap map) {
        map.setOnMapClickListener(pressLocationSource);
        map.setMyLocationEnabled(true);
        map.setLocationSource(pressLocationSource);

        LatLng mTarget = new LatLng(this.latTarget, this.lonTarget);

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(mTarget, 17));

        map.addMarker(new MarkerOptions().position(mTarget)
                .title("SSS")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.huawei_marker)));

    }
}
