/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2019-2019. All rights reserved.
 */

package com.reactlibrary.hms;

import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.huawei.hms.maps.HuaweiMap;
import com.huawei.hms.maps.HuaweiMap.OnMapClickListener;
import com.huawei.hms.maps.LocationSource;
import com.huawei.hms.maps.OnMapReadyCallback;
import com.huawei.hms.maps.SupportMapFragment;
import com.huawei.hms.maps.model.LatLng;
import com.reactlibrary.R;

/**
 * 获取位置源信息
 * Get location source information
 */

public class LocationSourceDemoActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = "LocationSourceDemoActiv";

    private PressLocationSource pressLocationSource;

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
    }
}
