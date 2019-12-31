/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2019-2019. All rights reserved.
 */

package com.reactlibrary.hms;

import com.huawei.hms.maps.CameraUpdateFactory;
import com.huawei.hms.maps.HuaweiMap;
import com.huawei.hms.maps.HuaweiMapOptions;
import com.huawei.hms.maps.MapFragment;
import com.huawei.hms.maps.OnMapReadyCallback;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.huawei.hms.maps.model.LatLng;
import com.reactlibrary.R;

public class MapFragmentCodeDemoActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static final String TAG = "MapFragmentCodeActivity";

    private HuaweiMap hmap;

    private MapFragment mMapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapfragmentcode_demo);
        HuaweiMapOptions huaweiMapOptions = new HuaweiMapOptions();
        huaweiMapOptions.compassEnabled(true);
        huaweiMapOptions.zoomGesturesEnabled(true);
        mMapFragment = MapFragment.newInstance(huaweiMapOptions);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frame_mapfragmentcode, mMapFragment);
        fragmentTransaction.commit();
        mMapFragment.onAttach(this);

        mMapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(HuaweiMap map) {
        Log.d(TAG, "onMapReady: ");
        hmap = map;
        hmap.setBuildingsEnabled(true);
        hmap.moveCamera(CameraUpdateFactory.newLatLngZoom(
            new LatLng(48.893478, 2.334595),10));
    }
}
