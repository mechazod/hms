
package com.reactlibrary.hms;

import com.huawei.hms.maps.HuaweiMap;
import com.huawei.hms.maps.HuaweiMap.OnCameraIdleListener;
import com.huawei.hms.maps.HuaweiMap.OnMapLongClickListener;
import com.huawei.hms.maps.OnMapReadyCallback;
import com.huawei.hms.maps.SupportMapFragment;
import com.huawei.hms.maps.model.LatLng;
import com.huawei.hms.maps.model.VisibleRegion;
import com.huawei.hms.maps.util.LogM;
import com.reactlibrary.R;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 功能描述 (Function)
 * 展示怎么监听HuaweiMap事件 (This shows how to listen to some HuaweiMap events)
 */
public class EventsDemoActivity extends AppCompatActivity implements HuaweiMap.OnMapClickListener,
    OnMapLongClickListener, OnCameraIdleListener, OnMapReadyCallback, HuaweiMap.OnMyLocationButtonClickListener {
    private static final String TAG = "EventsDemoActivity";

    private TextView mTapView;

    private TextView mToLatLngView;

    private TextView mToPointView;

    private HuaweiMap hMap;

    private SupportMapFragment mSupportMapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.events_demo);
        mSupportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mSupportMapFragment.getMapAsync(this);
        mTapView = (TextView) findViewById(R.id.tap_text);
        mToPointView = findViewById(R.id.toPoint);
        mToLatLngView = findViewById(R.id.toLatlng);
    }

    @Override
    public void onMapReady(HuaweiMap map) {
        hMap = map;
        hMap.setOnMapClickListener(this);
        hMap.setOnMapLongClickListener(this);
        hMap.setOnCameraIdleListener(this);
        hMap.setMyLocationEnabled(true);
        hMap.setOnMyLocationButtonClickListener(this);
    }

    /**
     *
     * 地图点击事件
     * Map click event
     */
    @Override
    public void onMapClick(LatLng latLng) {
        mTapView.setText("point=" + latLng + "is tapped");
        Point point = hMap.getProjection().toScreenLocation(latLng);
        mToPointView.setText("to point, point=" + point);
        LatLng newLatlng = hMap.getProjection().fromScreenLocation(point);
        mToLatLngView.setText("to latlng, latlng=" + newLatlng);
        VisibleRegion visibleRegion = hMap.getProjection().getVisibleRegion();
        LogM.i(TAG, visibleRegion.toString());
    }
    /**
     * 地图长按事件
     * Map long click event
     */
    @Override
    public void onMapLongClick(LatLng point) {
        mTapView.setText("long pressed, point=" + point);
    }

    /**
     * 当相机移动结束时进行回调
     * Callback when the camera move ends
     */
    @Override
    public void onCameraIdle() {
        Toast.makeText(this, "camera move stoped", Toast.LENGTH_LONG).show();
    }
    /**
     * 我的位置按钮点击事件
     * Map click event
     */
    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        return false;
    }
}
