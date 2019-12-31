/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2019-2019. All rights reserved.
 */

package com.reactlibrary.hms;

import com.huawei.hms.maps.CameraUpdate;
import com.huawei.hms.maps.CameraUpdateFactory;
import com.huawei.hms.maps.HuaweiMap;
import com.huawei.hms.maps.HuaweiMap.OnCameraIdleListener;
import com.huawei.hms.maps.HuaweiMap.OnCameraMoveListener;
import com.huawei.hms.maps.HuaweiMap.OnCameraMoveStartedListener;
import com.huawei.hms.maps.OnMapReadyCallback;
import com.huawei.hms.maps.SupportMapFragment;
import com.huawei.hms.maps.model.CameraPosition;
import com.huawei.hms.maps.model.LatLng;
import com.huawei.hms.maps.model.LatLngBounds;
import com.huawei.hms.maps.util.LogM;
import com.reactlibrary.R;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * �������� (Function)
 * չʾ����ƶ���ͼ��� (Show how to move a map camera)
 */

public class CameraDemoActivity extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener,
    OnCameraMoveStartedListener, OnCameraMoveListener, OnCameraIdleListener {
    private static final String TAG = "CameraDemoActivity";

    private SupportMapFragment mSupportMapFragment;

    private HuaweiMap hmap;

    public static final String[] PERMISION =
        {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};

    public static final int REQUEST_CODE = 0X01;

    private TextView cameraChange;

    private float mMaxZoom = 22.0f;

    private float mMinZoom = 0.0f;

    private static final float ZOOM_DELTA = 2.0f;

    private EditText cameraLat;

    private EditText cameraLng;

    private EditText cameraZoom;

    private EditText cameraTilt;

    private EditText cameraBearing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_demo);
        if (!hasLocationPermission()) {
            ActivityCompat.requestPermissions(this, PERMISION, REQUEST_CODE);
        }
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.mapInCamera);
        if (fragment instanceof SupportMapFragment) {
            mSupportMapFragment = (SupportMapFragment) fragment;
            mSupportMapFragment.getMapAsync(this);
        }
        cameraLat =findViewById(R.id.cameraLat);
        cameraLng = findViewById(R.id.cameraLng);
        cameraZoom = findViewById(R.id.cameraZoom);
        cameraTilt = findViewById(R.id.cameraLat);
        cameraBearing = findViewById(R.id.cameraBearing);
        Button btn1 = findViewById(R.id.animateCamera);
        btn1.setOnClickListener(this);
        Button btn2 = findViewById(R.id.getCameraPosition);
        btn2.setOnClickListener(this);
        Button btn3 = findViewById(R.id.moveCamera);
        btn3.setOnClickListener(this);
        Button btn4 = findViewById(R.id.ZoomBy);
        btn4.setOnClickListener(this);
        Button btn5 = findViewById(R.id.newLatLngBounds);
        btn5.setOnClickListener(this);
        Button btn6 = findViewById(R.id.setCameraPosition);
        btn6.setOnClickListener(this);

        cameraChange = findViewById(R.id.cameraChange);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
        @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, permissions[i] + " permission setting successfully", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, permissions[i] + " permission setting failed", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        }
    }

    /**
     *�ж��Ƿ�ӵ��λ�����
     *Determine if you have the location permission
     */
    private boolean hasLocationPermission() {
        for (String permission : PERMISION) {
            if (ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * �ж��Ƿ���GPS
     * Determine whether to turn on GPS
     */
    private boolean isGPSOpen(final Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (gps || network) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onMapReady(HuaweiMap huaweiMap) {
        LogM.i(TAG, "onMapReady: ");
        hmap = huaweiMap;
        if (isGPSOpen(this)) {
            hmap.setMyLocationEnabled(true);
            hmap.getUiSettings().setMyLocationButtonEnabled(true);
        } else {
            hmap.setMyLocationEnabled(false);
            hmap.getUiSettings().setMyLocationButtonEnabled(false);
        }
        hmap.setOnCameraMoveStartedListener(this);
        hmap.setOnCameraIdleListener(this);
        hmap.setOnCameraMoveListener(this);
        hmap.setOnMapLoadedCallback(new HuaweiMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                LogM.i(TAG, "onMapLoaded:successful");
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (null == hmap) {
            LogM.w(TAG, "map is null");
            return;
        }
        if (v.getId() == R.id.animateCamera) {
            CameraUpdate cu = CameraUpdateFactory.newLatLng(new LatLng(20, 120));
            Toast.makeText(this, cu.getCameraUpdate().getLatLng().toString(), Toast.LENGTH_LONG).show();
            hmap.animateCamera(cu);
        }
        if (v.getId() == R.id.getCameraPosition) {
            CameraPosition position = hmap.getCameraPosition();
            Toast.makeText(getApplicationContext(), position.toString(), Toast.LENGTH_LONG).show();

            //Displays the maximum zoom level and minimum scaling level of the current camera.
            LogM.i(TAG, position.toString());
            LogM.i(TAG, "MaxZoomLevel:" + hmap.getMaxZoomLevel() + " MinZoomLevel:" + hmap.getMinZoomLevel());
        }
        if (R.id.moveCamera == v.getId()) {
            CameraPosition build = new CameraPosition.Builder().target(new LatLng(60, 60)).build();
            CameraUpdate cu = CameraUpdateFactory.newCameraPosition(build);
            Toast.makeText(this, cu.getCameraUpdate().getCameraPosition().toString(), Toast.LENGTH_LONG).show();
            hmap.moveCamera(cu);
        }
        if (R.id.ZoomBy == v.getId()) {
            CameraUpdate cameraUpdate = CameraUpdateFactory.zoomBy(2);
            Toast.makeText(this, "amount = 2", Toast.LENGTH_LONG).show();
            hmap.moveCamera(cameraUpdate);
        }
        if (R.id.newLatLngBounds == v.getId()) {
            LatLng southwest = new LatLng(30, 60);
            LatLng northeast = new LatLng(60, 120);
            LatLngBounds latLngBounds = new LatLngBounds(southwest, northeast);
            Toast
                .makeText(this,
                    "southwest =" + southwest.toString() + " northeast=" + northeast.toString() + " padding=2",
                    Toast.LENGTH_LONG)
                .show();
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(latLngBounds, 2);
            hmap.moveCamera(cameraUpdate);
        }
        if (R.id.setCameraPosition == v.getId()) {
            LatLng southwest = new LatLng(30, 60);
            CameraPosition cameraPosition =
                CameraPosition.builder().target(southwest).zoom(10).bearing(2.0f).tilt(2.5f).build();
            Toast.makeText(this, cameraPosition.toString(), Toast.LENGTH_LONG).show();
            CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
            hmap.moveCamera(cameraUpdate);
        }
    }
    /**
     * �������ʼ�ƶ�ʱ���лص�
     * Callback when the camera starts moving
     */
    @Override
    public void onCameraMoveStarted(int reason) {
        String reasonText = "UNKNOWN_REASON";
        LogM.i(TAG, "onCameraMoveStarted: susccessful");
        if (reason == REASON_DEVELOPER_ANIMATION) {
            LogM.i(TAG, "onCameraMove");
        }
    }
    /**
     * ������ƶ�����ʱ���лص�
     * Callback when the camera move ends
     */
    @Override
    public void onCameraIdle() {
        cameraChange.setText(hmap.getCameraPosition().toString());
        LogM.i(TAG, "onCameraIdle: sucessful");
    }

    /**
     * ��������ƶ��ص�
     * Set camera move callback
     */
    @Override
    public void onCameraMove() {
        LogM.i(TAG, "onCameraMove: successful");
    }
    /**
     * ����������ŵ�����
     * Set the upper limit of camera zoom
    */
    public void onSetMaxZoomClamp(View view) {
        mMaxZoom -= ZOOM_DELTA;
        // Constrains the maximum zoom level.
        hmap.setMaxZoomPreference(mMaxZoom);
        Toast.makeText(this, "Max zoom preference set to: " + mMaxZoom, Toast.LENGTH_SHORT).show();
    }

    /**
     * ����������ŵ�����
     * Set the lower limit of camera zoom
     */
    public void onSetMinZoomClamp(View view) {
        mMinZoom += ZOOM_DELTA;
        // Constrains the minimum zoom level.
        hmap.setMinZoomPreference(mMinZoom);
        Toast.makeText(this, "Min zoom preference set to: " + mMinZoom, Toast.LENGTH_SHORT).show();
    }

    /**
     * ��������ľ�γ�ȡ�����ϵ������б�Ƕȡ���ת�Ƕ����õ�ͼ���
     * Set the map camera based on the latitude and longitude, zoom factor, tilt angle, and rotation angle
     */
    public void setCameraPosition(View view) {
        try {
            LatLng latLng = null;
            float zoom = 2.0f;
            float bearing = 2.0f;
            float tilt = 2.0f;
            if (!TextUtils.isEmpty(cameraLng.getText()) && !TextUtils.isEmpty(cameraLat.getText())) {
                latLng = new LatLng(Float.valueOf(cameraLat.getText().toString().trim()),
                        Float.valueOf(cameraLng.getText().toString().trim()));
            }
            if (!TextUtils.isEmpty(cameraZoom.getText())) {
                zoom = Float.valueOf(cameraZoom.getText().toString().trim());
            }
            if (!TextUtils.isEmpty(cameraBearing.getText())) {
                bearing = Float.valueOf(cameraBearing.getText().toString().trim());
            }
            if (!TextUtils.isEmpty(cameraTilt.getText())) {
                tilt = Float.valueOf(cameraTilt.getText().toString().trim());
            }
            CameraPosition cameraPosition =
                    CameraPosition.builder().target(latLng).zoom(zoom).bearing(bearing).tilt(tilt).build();
            CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
            hmap.moveCamera(cameraUpdate);
        } catch (IllegalArgumentException e) {
            LogM.e(TAG, "IllegalArgumentException " + e.toString());
            Toast.makeText(this, "IllegalArgumentException", Toast.LENGTH_SHORT).show();
        } catch (NullPointerException e) {
            LogM.e(TAG, "NullPointerException " + e.toString());
            Toast.makeText(this, "NullPointerException", Toast.LENGTH_SHORT).show();
        }
    }
}
