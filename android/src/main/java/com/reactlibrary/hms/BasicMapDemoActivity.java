/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2019-2019. All rights reserved.
 */

package com.reactlibrary.hms;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.huawei.hms.maps.CameraUpdate;
import com.huawei.hms.maps.HuaweiMap;
import com.huawei.hms.maps.OnMapReadyCallback;
import com.reactlibrary.R;

/**
 * 创建基本的地图活动
 * This shows how we create a basic activity with a map.
 */
public class BasicMapDemoActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = "BasicMapDemoActivity";
    private HuaweiMap hmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basic_demo);

    }
    @Override
    public void onMapReady(HuaweiMap map) {
        Log.d(TAG, "onMapReady: ");
        hmap = map;
        CameraUpdate c=null;
        c.getCameraUpdate();
    }

    /**
     * 启动createMapView活动
     * Start the createMapView activity
     */
    public void createMapView(View view) {

        Log.d(TAG, "createMapView: ");
        Intent i = new Intent(this, MapViewDemoActivity.class);
        startActivity(i);
    }
    /**
     * 启动createMapViewCode活动
     * Start the createMapViewCode activity
     */
    public void createMapViewCode(View view) {

        Log.d(TAG, "createMapViewCode: ");
        Intent i = new Intent(this, MapViewCodeDemoActivity.class);
        startActivity(i);
    }
    /**
     * 启动createMapFragment活动
     * Start the createMapFragment activity
     */
    public void createMapFragment(View view) {

        Log.d(TAG, "createMapFragment: ");
        Intent i = new Intent(this, MapFragmentDemoActivity.class);
        startActivity(i);
    }
    /**
     * 启动createMapFragmentCode活动
     * Start the createMapFragmentCode activity
     */
    public void createMapFragmentCode(View view) {

        Log.d(TAG, "createMapFragmentCode: ");
        Intent i = new Intent(this, MapFragmentCodeDemoActivity.class);
        startActivity(i);
    }
    /**
     * 启动createSupportMapFragment活动
     * Start the createSupportMapFragment activity
     */
    public void createSupportMapFragment(View view) {

        Log.d(TAG, "createSupportMapFragment: ");
        Intent i = new Intent(this, SupportMapDemoActivity.class);
        startActivity(i);
    }
    /**
     * 启动createSupportMapFragmentCode活动
     * Start the createSupportMapFragmentCode activity
     */
    public void createSupportMapFragmentCode(View view) {

        Log.d(TAG, "createSupportMapFragmentCode: ");
        Intent i = new Intent(this, SupportMapCodeDemoActivity.class);
        startActivity(i);
    }
}
