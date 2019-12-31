package com.reactlibrary.hms;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.huawei.hms.maps.HuaweiMap;
import com.huawei.hms.maps.OnMapReadyCallback;
import com.huawei.hms.maps.SupportMapFragment;
import com.huawei.hms.maps.util.LogM;
import com.reactlibrary.R;

/**
 * Map support multi-language
 *
 */
public class MoreLanguageDemo extends AppCompatActivity implements OnMapReadyCallback {
    private static final String TAG = "MoreLanguageDemo";
    private SupportMapFragment mSupportMapFragment;

    private HuaweiMap hmap;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_language_demo);
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.mapInLanguage);
        if (fragment instanceof SupportMapFragment) {
            mSupportMapFragment = (SupportMapFragment) fragment;
        }
        mSupportMapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(HuaweiMap paramHuaweiMap) {
        LogM.i(TAG, "onMapReady: ");
        hmap = paramHuaweiMap;
        hmap.setMyLocationEnabled(true);
    }
}
