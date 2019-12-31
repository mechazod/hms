/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2019-2019. All rights reserved.
 */

package com.reactlibrary.hms;

import com.huawei.hms.maps.CameraUpdateFactory;
import com.huawei.hms.maps.HuaweiMap;
import com.huawei.hms.maps.MapFragment;
import com.huawei.hms.maps.OnMapReadyCallback;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.huawei.hms.maps.model.LatLng;
import com.reactlibrary.R;

public class MapFragmentDemoActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = "MapFragmentDemoActivity";

    private HuaweiMap hmap;

    private MapFragment mMapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapfragment_demo);
        mMapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapFragment);
        mMapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(HuaweiMap map) {
        Log.d(TAG, "onMapReady: ");
        hmap = map;
        hmap.getUiSettings().setMyLocationButtonEnabled(false);
        hmap.moveCamera(CameraUpdateFactory.newLatLngZoom(
            new LatLng(48.893478, 2.334595),10));
    }
}
