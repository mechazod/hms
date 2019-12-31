
package com.reactlibrary.hms;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.huawei.hms.maps.HuaweiMap;
import com.huawei.hms.maps.OnMapReadyCallback;
import com.huawei.hms.maps.SupportMapFragment;
import com.huawei.hms.maps.util.LogM;
import com.reactlibrary.R;

/**
 * Map supports traffic mode
 * 地图交通流量模式
 */
public class TrafficDemo extends AppCompatActivity implements OnMapReadyCallback {
    private static final String TAG = "TrafficDemo";

    private SupportMapFragment mSupportMapFragment;

    private HuaweiMap hmap;

    private TextView textIsTrafficed;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitvity_traffic_demo);
        mSupportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapInTraffic);
        mSupportMapFragment.getMapAsync(this);

        textIsTrafficed = findViewById(R.id.textIsTrafficed);
    }

    @Override
    public void onMapReady(HuaweiMap paramHuaweiMap) {
        LogM.i(TAG, "onMapReady: ");
        hmap = paramHuaweiMap;
        hmap.setMyLocationEnabled(true);
    }
    /**
     * 使交通流量模式可用
     * Make traffic flow mode available
     */
    public void enableTraffic(View view) {
        if(null != hmap){
            hmap.setTrafficEnabled(true);
        }

    }

    /**
     * 使交通流量模式不可用
     * Make traffic flow mode unavailable
     */
    public void disableTraffic(View view) {
        if(null != hmap){
            hmap.setTrafficEnabled(false);
        }

    }

    /**
     * 获得交通流量模式是否可用
     * Get traffic flow mode available
     */
    public void getIsTrafficEnabled(View view) {
        if(null != hmap){
            boolean bool = hmap.isTrafficEnabled();
            String text = (bool) ? "enabled" : "disabled";
            textIsTrafficed.setText("The TrafficMode is " + text);
        }

    }
}
