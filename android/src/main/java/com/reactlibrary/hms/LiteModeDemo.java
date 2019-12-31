package com.reactlibrary.hms;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.huawei.hms.maps.CameraUpdateFactory;
import com.huawei.hms.maps.HuaweiMap;
import com.huawei.hms.maps.HuaweiMapOptions;
import com.huawei.hms.maps.MapView;
import com.huawei.hms.maps.OnMapReadyCallback;
import com.huawei.hms.maps.SupportMapFragment;
import com.huawei.hms.maps.model.LatLng;
import com.huawei.hms.maps.util.LogM;

/**
 * 简易模式
 *  Lite Mode
 */
public class LiteModeDemo extends AppCompatActivity implements OnMapReadyCallback {
    private static final String TAG = "LiteModeDemo";

    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";

    HuaweiMap hMap;

    MapView mMapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        HuaweiMapOptions huaweiMapOptions = new HuaweiMapOptions();
        huaweiMapOptions.liteMode(true);
        huaweiMapOptions.compassEnabled(true);
        huaweiMapOptions.zoomControlsEnabled(true);
        huaweiMapOptions.scrollGesturesEnabled(true);
        huaweiMapOptions.zoomGesturesEnabled(true);
        mMapView = new MapView(this, huaweiMapOptions);
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }

        mMapView.onCreate(mapViewBundle);
        mMapView.getMapAsync(this);
        setContentView(mMapView);
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
        hMap = map;
        hMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(48.893478, 2.334595),10));
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }
}
