
package com.reactlibrary.hms;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import com.huawei.hms.maps.HuaweiMap;
import com.huawei.hms.maps.OnMapReadyCallback;
import com.huawei.hms.maps.SupportMapFragment;
import com.huawei.hms.maps.UiSettings;
import com.huawei.hms.maps.util.LogM;
import com.reactlibrary.R;

/**
 * 地图手势相关
 * about gesture
 */
public class GestureDemo extends AppCompatActivity implements OnMapReadyCallback {
    private static final String TAG = "GestureDemo";

    private SupportMapFragment mSupportMapFragment;

    private HuaweiMap hmap;

    private UiSettings mUiSettings;

    private CheckBox mMyLocationButtonCheckbox;

    private CheckBox mMyLocationLayerCheckbox;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestures_demo);
        mSupportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapInGestures);
        mSupportMapFragment.getMapAsync(this);

        mMyLocationButtonCheckbox = (CheckBox) findViewById(R.id.isShowMylocationButton);
        mMyLocationLayerCheckbox = (CheckBox) findViewById(R.id.isMyLocationLayerEnabled);
    }

    @Override
    public void onMapReady(HuaweiMap paramHuaweiMap) {
        LogM.i(TAG, "onMapReady: ");
        hmap = paramHuaweiMap;
        hmap.setMyLocationEnabled(false);
        hmap.getUiSettings().setCompassEnabled(false);
        hmap.getUiSettings().setZoomControlsEnabled(false);
        hmap.getUiSettings().setMyLocationButtonEnabled(false);
        mUiSettings = hmap.getUiSettings();
    }

    private boolean checkReady() {

        if (hmap == null) {
            Toast.makeText(this, "Map is not ready yet", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * 设置地图缩放按钮可用
     * Set map zoom button available
     */
    public void setZoomButtonsEnabled(View v) {
        if (!checkReady()) {
            return;
        }
        mUiSettings.setZoomControlsEnabled(((CheckBox) v).isChecked());
    }

    /**
     * 设置指南针可用
     * Set compass available
     */
    public void setCompassEnabled(View v) {
        if (!checkReady()) {
            return;
        }
        mUiSettings.setCompassEnabled(((CheckBox) v).isChecked());
    }
    /**
     * 设置我的位置按钮可用
     * Set my location button is available
     */
    public void setMyLocationButtonEnabled(View v) {
        if (!checkReady()) {
            return;
        }
        if (ContextCompat.checkSelfPermission(this,
            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (mMyLocationLayerCheckbox.isChecked()) {
                mUiSettings.setMyLocationButtonEnabled(mMyLocationButtonCheckbox.isChecked());
            } else {
                Toast.makeText(this, "请先打开mylocation Layer图层", Toast.LENGTH_LONG).show();
                mMyLocationButtonCheckbox.setChecked(false);
            }

        } else {
            Toast.makeText(this, "未获取到系统定位权限，请先打开系统定位权限", Toast.LENGTH_LONG).show();
            mMyLocationButtonCheckbox.setChecked(false);

        }
    }
    /**
     * 设置我的位置图层可用
     * Set my location layer available
     */
    public void setMyLocationLayerEnabled(View v) {
        if (!checkReady()) {
            return;
        }
        if (ContextCompat.checkSelfPermission(this,
            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            hmap.setMyLocationEnabled(mMyLocationLayerCheckbox.isChecked());
        } else {
            mMyLocationLayerCheckbox.setChecked(false);
        }
    }
    /**
     * 设置滚动手势可用
     * Set scroll gestures available
     */
    public void setScrollGesturesEnabled(View v) {
        if (!checkReady()) {
            return;
        }
        mUiSettings.setScrollGesturesEnabled(((CheckBox) v).isChecked());
    }
    /**
     * 设置缩放手势可用
     * Set zoom gestures available
     */
    public void setZoomGesturesEnabled(View v) {
        if (!checkReady()) {
            return;
        }
        mUiSettings.setZoomGesturesEnabled(((CheckBox) v).isChecked());
    }
    /**
     * 设置倾斜手势可用
     * Set tilt gestures available
     */
    public void setTiltGesturesEnabled(View v) {
        if (!checkReady()) {
            return;
        }
        mUiSettings.setTiltGesturesEnabled(((CheckBox) v).isChecked());
    }
    /**
     * 设置旋转手势可以用
     * Set the rotation gesture available
     */
    public void setRotateGesturesEnabled(View v) {
        if (!checkReady()) {
            return;
        }
        mUiSettings.setRotateGesturesEnabled(((CheckBox) v).isChecked());
    }
}
