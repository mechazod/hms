/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2019-2019. All rights reserved.
 */

package com.reactlibrary.hms;

import com.huawei.hms.maps.util.LogM;
import com.reactlibrary.R;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * 主活动，只提供功能入口
 * Home page only provides function module entry for map
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";

    Button GestureDemo;

    private static final String[] RUNTIME_PERMISSIONS = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.INTERNET
    };

    private static final int REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!hasPermissions(this, RUNTIME_PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, RUNTIME_PERMISSIONS, REQUEST_CODE);
        }

        Button btn1 = findViewById(R.id.Camera);
        Button btn2 = findViewById(R.id.BasicMap);
        btn2.setOnClickListener(this);
        Button GestureDemo = findViewById(R.id.GestureDemo);
        Button ControlsDemo = findViewById(R.id.ControlsDemo);
        Button CircleDemo = findViewById(R.id.CircleDemo);
        Button PolygonDemo = findViewById(R.id.PolygonDemo);
        Button PolylineDemo = findViewById(R.id.PolylineDemo);
        Button GroudOverlayDemo = findViewById(R.id.GroudOverlayDemo);
        Button LiteModeDemo = findViewById(R.id.LiteModeDemo);
        Button MoreLanguageDemo = findViewById(R.id.MoreLanguageDemo);
        Button TrafficDemo = findViewById(R.id.TrafficDemo);
        Button MapFounctions = findViewById(R.id.MapFounctions);
        Button addMarkerDemo = findViewById(R.id.AddMarkerDemo);
        Button eventsDemo = findViewById(R.id.EventsDemo);
        Button distanceDemo = findViewById(R.id.DistanceDemo);
        Button searchDemo = findViewById(R.id.SearchDemo);
        Button locationSourceDemo = findViewById(R.id.LocationSourceDemo);

        btn1.setOnClickListener(this);
        GestureDemo.setOnClickListener(this);
        ControlsDemo.setOnClickListener(this);
        CircleDemo.setOnClickListener(this);
        PolygonDemo.setOnClickListener(this);
        PolylineDemo.setOnClickListener(this);
        GroudOverlayDemo.setOnClickListener(this);
        LiteModeDemo.setOnClickListener(this);
        MoreLanguageDemo.setOnClickListener(this);
        TrafficDemo.setOnClickListener(this);
        MapFounctions.setOnClickListener(this);
        addMarkerDemo.setOnClickListener(this);
        eventsDemo.setOnClickListener(this);
        distanceDemo.setOnClickListener(this);
        searchDemo.setOnClickListener(this);
        locationSourceDemo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (R.id.Camera == v.getId()) {
            LogM.i(TAG, "onClick: cameraDemo");
            Intent i = new Intent(this, CameraDemoActivity.class);
            startActivity(i);
        } else if (R.id.GestureDemo == v.getId()) {
            LogM.i(TAG, "onClick: GestureDemo");
            Intent intent = new Intent(this, GestureDemo.class);
            startActivity(intent);

        } else if (R.id.ControlsDemo == v.getId()) {
            LogM.i(TAG, "onClick: ControlsDemo");
            Intent intent = new Intent(this, ControlsDemo.class);
            startActivity(intent);
        } else if (R.id.CircleDemo == v.getId()) {
            LogM.i(TAG, "onClick: CircleDemo");
            Intent intent = new Intent(this, CircleDemo.class);
            startActivity(intent);

        } else if (R.id.PolygonDemo == v.getId()) {
            LogM.i(TAG, "onClick: PolygonDemo");
            Intent intent = new Intent(this, PolygonDemo.class);
            startActivity(intent);

        } else if (R.id.PolylineDemo == v.getId()) {
            LogM.i(TAG, "onClick: GestureDemo");
            Intent intent = new Intent(this, PolylineDemo.class);
            startActivity(intent);

        } else if (R.id.GroudOverlayDemo == v.getId()) {
            LogM.i(TAG, "onClick: GroundOverlayDemo");
            Intent intent = new Intent(this, GroundOverlayDemo.class);
            startActivity(intent);

        } else if (R.id.LiteModeDemo == v.getId()) {
            LogM.i(TAG, "onClick: LiteModeDemo");
            Intent intent = new Intent(this, LiteModeDemo.class);
            startActivity(intent);

        } else if (R.id.MoreLanguageDemo == v.getId()) {
            LogM.i(TAG, "onClick: MoreLanguageDemo");
            Intent intent = new Intent(this, MoreLanguageDemo.class);
            startActivity(intent);

        } else if (R.id.TrafficDemo == v.getId()) {
            LogM.i(TAG, "onClick: TrafficDemo");
            Intent intent = new Intent(this, TrafficDemo.class);
            startActivity(intent);

        } else if (R.id.MapFounctions == v.getId()) {
            LogM.i(TAG, "onClick: MapFunctionsDemo");
            Intent intent = new Intent(this, MapFunctionsDemo.class);
            startActivity(intent);
        } else if (R.id.BasicMap == v.getId()) {
            LogM.i(TAG, "onClick: BasicMap");
            Intent i = new Intent(this, BasicMapDemoActivity.class);
            startActivity(i);
        } else if (R.id.AddMarkerDemo == v.getId()) {
            LogM.i(TAG, "onClick: AddMarkerDemo");
            Intent i = new Intent(this, MarkerDemoActivity.class);
            startActivity(i);
        } else if (R.id.EventsDemo == v.getId()) {
            LogM.i(TAG, "onClick: EventsDemo");
            Intent i = new Intent(this, EventsDemoActivity.class);
            startActivity(i);
        } else if (R.id.DistanceDemo == v.getId()) {
            LogM.i(TAG, "onClick: DistanceDemo");
            Intent i = new Intent(this, DistanceCalculatorDemo.class);
            startActivity(i);
        } else if (R.id.SearchDemo == v.getId()) {
            LogM.i(TAG, "onClick: SearchDemo");
            Intent i = new Intent(this, SearchDemoActivity.class);
            startActivity(i);
        } else if (R.id.LocationSourceDemo == v.getId()) {
            LogM.i(TAG, "onClick: LocationSourceDemo");
            Intent i = new Intent(this, LocationSourceDemoActivity.class);
            startActivity(i);
        } else {
            LogM.i(TAG, "onClick:  " + v.getId());
        }
    }

    private static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
}
