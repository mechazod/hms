/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2019-2019. All rights reserved.
 */

package com.reactlibrary.hms;

import java.io.File;
import java.io.FileOutputStream;

import com.huawei.hms.maps.CameraUpdateFactory;
import com.huawei.hms.maps.HuaweiMap;
import com.huawei.hms.maps.MapView;
import com.huawei.hms.maps.OnMapReadyCallback;
import com.huawei.hms.maps.model.BitmapDescriptorFactory;
import com.huawei.hms.maps.model.LatLng;
import com.huawei.hms.maps.model.MarkerOptions;
import com.huawei.hms.maps.util.LogM;
import com.reactlibrary.R;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class MapViewDemoActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = "MapViewDemoActivity";

    private HuaweiMap hmap;

    private MapView mMapView;

    private double latCurrentLocation;
    private double lonCurrentLocation;
    private double latTarget;
    private double lonTarget;

    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LogM.d(TAG, "onCreate:");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapview_demo);
        mMapView = findViewById(R.id.mapView);
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }

        Bundle bundle = getIntent().getExtras();

        this.latCurrentLocation = bundle.getDouble("LAT_CURRENT", 0);
        this.lonCurrentLocation = bundle.getDouble("LON_CURRENT", 0);
        this.latTarget = bundle.getDouble("LAT_TARGET", 14.402940);
        this.lonTarget = bundle.getDouble("LON_TARGET", 120.989517);


        mMapView.onCreate(mapViewBundle);
        mMapView.getMapAsync(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mMapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mMapView.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onMapReady(HuaweiMap map) {
        Log.d(TAG, "onMapReady: ");

        LatLng mTarget = new LatLng(this.latTarget, this.lonTarget);

        hmap = map;
        hmap.setMyLocationEnabled(false);

        hmap.moveCamera(CameraUpdateFactory.newLatLngZoom(mTarget, 17));

        hmap.addMarker(new MarkerOptions().position(mTarget)
                .title("SSS")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.huawei_marker)));
    }

    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
}
