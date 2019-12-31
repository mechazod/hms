/*
 * Copyright (C) Huawei Technologies Co., Ltd. 2008-2020. All rights reserved.
 */

package com.reactlibrary.hms;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.huawei.hms.maps.CameraUpdate;
import com.huawei.hms.maps.CameraUpdateFactory;
import com.huawei.hms.maps.HuaweiMap;
import com.huawei.hms.maps.OnMapReadyCallback;
import com.huawei.hms.maps.SupportMapFragment;
import com.huawei.hms.maps.common.util.DistanceCalculator;
import com.reactlibrary.R;
import com.reactlibrary.hms.utils.MapUtils;
import com.huawei.hms.maps.model.LatLng;
import com.huawei.hms.maps.util.LogM;

import java.util.ArrayList;
import java.util.List;

import static com.reactlibrary.hms.utils.CheckUtils.checkIsEdit;
import static com.reactlibrary.hms.utils.CheckUtils.checkIsRight;

/**
 * 功能描述/ Function
 * 两点之间距离计算 / Distance calculation between two points
 */
public class DistanceCalculatorDemo extends AppCompatActivity implements OnMapReadyCallback {
    private static final String TAG = "DistanceCalculatorDemo";

    private SupportMapFragment mSupportMapFragment;

    private HuaweiMap hmap;

    private EditText oneLatitude;

    private EditText oneLongtitude;

    private EditText anotherLatitude;

    private EditText anotherLongtitude;

    private TextView result;

    private LatLng onePoint;

    private LatLng anotherPoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distance_calculator_demo);

        mSupportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapInPolyline);
        mSupportMapFragment.getMapAsync(this);

        oneLatitude = findViewById(R.id.oneLatitude);
        oneLongtitude = findViewById(R.id.oneLongtitude);
        anotherLatitude = findViewById(R.id.anotherLatitude);
        anotherLongtitude = findViewById(R.id.anotherLongtitude);
        result = findViewById(R.id.result);
    }

    @Override
    public void onMapReady(HuaweiMap paramHuaweiMap) {
        LogM.i(TAG, "onMapReady: ");
        hmap = paramHuaweiMap;
        hmap.setMyLocationEnabled(true);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLng(new LatLng(31.97846, 118.76454));
        hmap.animateCamera(cameraUpdate);
    }

    /**
     * 设置一个点的位置
     * Set the position of a point
     */
    public void setOne(View v) {
        String latitude = oneLatitude.getText().toString().trim();
        String longtitude = oneLongtitude.getText().toString().trim();
        if (checkIsEdit(latitude) || checkIsEdit(longtitude)) {
            onePoint = new LatLng(31,108);
            oneLatitude.setText(String.valueOf(31));
            oneLongtitude.setText(String.valueOf(108));
            Toast.makeText(this, "setDefaultPoint",Toast.LENGTH_SHORT).show();
            return;
        } else {
            if (!checkIsRight(latitude) || !checkIsRight(longtitude)) {
                Toast.makeText(this, "Please make sure the latitude & longtitude is right", Toast.LENGTH_SHORT)
                        .show();
            } else {
                onePoint = new LatLng(Double.valueOf(latitude),Double.valueOf(longtitude));
                Toast.makeText(this, "setOnePointSuccess",Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 设置另一个点的位置
     * Set the location of another point
     */
    public void setAnother(View v) {
         String latitude = anotherLatitude.getText().toString().trim();
        String longtitude = anotherLongtitude.getText().toString().trim();
        if (checkIsEdit(latitude) || checkIsEdit(longtitude)) {
            anotherPoint = new LatLng(32,107);
            anotherLatitude.setText(String.valueOf(32));
            anotherLongtitude.setText(String.valueOf(107));
            Toast.makeText(this, "setDefaultPoint",Toast.LENGTH_SHORT).show();
            return;
        } else {
            if (!checkIsRight(latitude) || !checkIsRight(longtitude)) {
                Toast.makeText(this, "Please make sure the latitude & longtitude is right", Toast.LENGTH_SHORT)
                        .show();
            } else {
                anotherPoint = new LatLng(Double.valueOf(latitude),Double.valueOf(longtitude));
                Toast.makeText(this, "setAnotherPointSuccess",Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 距离计算
     * Compute the distance
     */
    public void distanceCompute(View view){
        double distance = DistanceCalculator.computeDistanceBetween(onePoint, anotherPoint);
        result.setText("distance is" + String.valueOf(distance));
    }
}
