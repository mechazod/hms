/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2019-2019. All rights reserved.
 */

package com.reactlibrary.hms;

import com.huawei.hms.maps.CameraUpdateFactory;
import com.huawei.hms.maps.HuaweiMap;
import com.huawei.hms.maps.OnMapReadyCallback;
import com.huawei.hms.maps.SupportMapFragment;
import com.huawei.hms.maps.model.BitmapDescriptor;
import com.huawei.hms.maps.model.BitmapDescriptorFactory;
import com.huawei.hms.maps.model.LatLng;
import com.huawei.hms.maps.model.Marker;
import com.huawei.hms.maps.model.MarkerOptions;
import com.huawei.hms.maps.util.LogM;
import com.huawei.hms.maps.util.ResourceBitmapDescriptor;
import com.reactlibrary.R;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Create a simple activity with a map and a marker on the map.
 */
public class SupportMapDemoActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = "SupportMapDemoActivity";

    private HuaweiMap hmap;

    private SupportMapFragment mSupportMapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LogM.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.supportmapfragment_demo);
        mSupportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.supportMap);
        mSupportMapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(HuaweiMap map) {
        LogM.d(TAG, "onMapReady: ");
        hmap = map;
        hmap.getUiSettings().setCompassEnabled(true);
        hmap.moveCamera(CameraUpdateFactory.newLatLngZoom(
            new LatLng(48.893478, 2.334595),14));
        hmap.setOnMapLongClickListener(new HuaweiMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                LogM.d(TAG, "onMapLongClick: latLng " + " please input latLng");
            }
        });
    }

    Marker mBeijing;
    Marker mShanghai;
    Marker mNanjing;
    private static final LatLng Beijing = new LatLng(48.893478, 2.334595);

    private static final LatLng Nanjing = new LatLng(32.0603, 118.7969);

    private static final LatLng Shanghai = new LatLng(48.7, 2.12);

    public void addMarker(View view) {
        if (mBeijing == null && mShanghai == null && mNanjing == null) {
            // Uses a colored icon.
            mBeijing = hmap.addMarker(
                    new MarkerOptions().position(Beijing).title("Beijing").clusterable(true));
            mShanghai = hmap.addMarker(new MarkerOptions().position(Shanghai)
                    .alpha(0.8f)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.badge_ph)));
        }
        if (mBeijing != null) {
            mBeijing.setTitle("hello");
            mBeijing.setSnippet("world");
            mBeijing.setTag("huaweimap");
            mShanghai.setTitle("Hello");
        }
        mBeijing.setDraggable(true);
        mShanghai.setDraggable(true);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mSupportMapFragment.onSaveInstanceState(outState);
    }


    boolean visiable = true;
    public void setVisiable(View view) {
        if (visiable) {
            mBeijing.setVisible(true);
            visiable = false;
        } else {
            mBeijing.setVisible(false);
            visiable = true;
        }
    }

    public void setAlpha(View view) {
        if (visiable) {
            mBeijing.setAlpha(0.5f);
            visiable = false;
        } else {
            mBeijing.setAlpha(1.0f);
            visiable = true;
        }
    }

    public void setFlat(View view) {
        if (visiable) {
            mBeijing.setFlat(true);
            visiable = false;
        } else {
            mBeijing.setFlat(false);
            visiable = true;
        }
    }

    public void setZIndex(View view) {
        if (visiable) {
            mBeijing.setZIndex(20f);
            visiable = false;
        } else {
            mBeijing.setZIndex(-20f);
            visiable = true;
        }
    }
    public void setRotation(View view) {
        if (visiable) {
            mBeijing.setRotation(30.0f);
            visiable = false;
        } else {
            mBeijing.setRotation(60.0f);
            visiable = true;
        }
    }

    public void removeMarker(View view) {
        mBeijing.remove();
        mBeijing = null;
    }

    public void showInfoWindow(View view) {
        if (visiable) {
            mBeijing.showInfoWindow();
            visiable = false;
        } else {
            mBeijing.hideInfoWindow();
            visiable = true;
        }
    }

    public void setAnchor(View view) {
        if (mBeijing != null) {
            mBeijing.setAnchor(0.9F, 0.9F);
        }
    }
    public void setIcon(View view) {

        if (null != mBeijing) {
            Bitmap bitmap =
                    ResourceBitmapDescriptor.drawableToBitmap(this, ContextCompat.getDrawable(this, R.drawable.badge_tr));
            BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(bitmap);
            mBeijing.setIcon(bitmapDescriptor);
        }
    }

}
