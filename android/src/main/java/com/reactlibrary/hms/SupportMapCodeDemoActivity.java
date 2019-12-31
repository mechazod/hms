/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2019-2019. All rights reserved.
 */

package com.reactlibrary.hms;

import com.huawei.hms.maps.CameraUpdateFactory;
import com.huawei.hms.maps.HuaweiMap;
import com.huawei.hms.maps.HuaweiMapOptions;
import com.huawei.hms.maps.OnMapReadyCallback;
import com.huawei.hms.maps.SupportMapFragment;
import com.huawei.hms.maps.model.CameraPosition;
import com.huawei.hms.maps.model.LatLng;
import com.reactlibrary.R;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Create a simple activity with a map and a marker on the map.
 */
public class SupportMapCodeDemoActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = "SupportMapCodeActivity";

    private HuaweiMap hmap;

    private SupportMapFragment mSupportMapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.supportmapcode_demo);
        LatLng southwest = new LatLng(30, 118);
        CameraPosition cameraPosition =
                CameraPosition.builder().target(southwest).zoom(2).bearing(2.0f).tilt(2.5f).build();
        HuaweiMapOptions huaweiMapOptions = new HuaweiMapOptions().camera(cameraPosition);
        mSupportMapFragment = SupportMapFragment.newInstance(huaweiMapOptions);
        mSupportMapFragment.getMapAsync(this);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frame_supportmapcode, mSupportMapFragment);
        fragmentTransaction.commit();
        mSupportMapFragment.onAttach(this);


    }

    @Override
    public void onMapReady(HuaweiMap map) {
        Log.d(TAG, "onMapReady: ");
        hmap = map;
        hmap.moveCamera(CameraUpdateFactory.newLatLngZoom(
            new LatLng(48.893478, 2.334595),10));
    }
}
