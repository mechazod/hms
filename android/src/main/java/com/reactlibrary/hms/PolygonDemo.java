/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2019-2019. All rights reserved.
 */

package com.reactlibrary.hms;

import static com.reactlibrary.hms.utils.CheckUtils.checkIsEdit;
import static com.reactlibrary.hms.utils.CheckUtils.checkIsRight;

import com.huawei.hms.maps.CameraUpdate;
import com.huawei.hms.maps.CameraUpdateFactory;
import com.huawei.hms.maps.HuaweiMap;
import com.huawei.hms.maps.OnMapReadyCallback;
import com.huawei.hms.maps.SupportMapFragment;
import com.reactlibrary.R;
import com.reactlibrary.hms.utils.MapUtils;
import com.huawei.hms.maps.model.LatLng;
import com.huawei.hms.maps.model.Polygon;
import com.huawei.hms.maps.model.PolygonOptions;
import com.huawei.hms.maps.util.LogM;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * about polygon
 * 多边形相关示例
 */
public class PolygonDemo extends AppCompatActivity implements OnMapReadyCallback {
    private static final String TAG = "PolygonDemo";

    private SupportMapFragment mSupportMapFragment;

    private HuaweiMap hmap;

    private Polygon mPolygon;

    private TextView polygonShown;

    private EditText oneLatitude;

    private EditText oneLongtitude;

    private EditText polygonTag;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_polygon_demo);
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.mapInPolygon);
        if (fragment instanceof SupportMapFragment) {
            mSupportMapFragment = (SupportMapFragment) fragment;
            mSupportMapFragment.getMapAsync(this);
        }
        polygonShown = findViewById(R.id.polygonShown);
        oneLatitude = findViewById(R.id.oneLatitude);
        oneLongtitude = findViewById(R.id.oneLongtitude);
        polygonTag = findViewById(R.id.polygonTag);
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
     * 添加多边形到地图
     * Add polygons to the map
     */
    public void addPolygon(View view) {
        if (null == hmap) {
            return;
        }
        if (null != mPolygon) {
            mPolygon.remove();
        }
        mPolygon =
            hmap.addPolygon(new PolygonOptions().addAll(MapUtils.createRectangle(MapUtils.HUAWEI_CENTER, 0.5, 0.5))
                .fillColor(Color.GREEN)
                .strokeColor(Color.BLACK));
        hmap.setOnPolygonClickListener(new HuaweiMap.OnPolygonClickListener() {
            @Override
            public void onPolygonClick(Polygon polygon) {
                LogM.i(TAG, "addPolygon and onPolygonClick start ");
            }
        });
    }

    /**
     * 移除多边形
     * Remove the polygon
     */
    public void removePolygon(View view) {
        if (null != mPolygon) {
            mPolygon.remove();
        }
    }
    /**
     * 设置多边形的点信息
     * Set the point position information of the polygon
     */
    public void setPoints(View v) {
        if (null != mPolygon) {
            String latitude = oneLatitude.getText().toString().trim();
            String longtitude = oneLongtitude.getText().toString().trim();
            if (checkIsEdit(latitude) || checkIsEdit(longtitude)) {
                Toast.makeText(this, "Please make sure the latitude & longtitude is Edited", Toast.LENGTH_SHORT).show();
            } else {
                if (!checkIsRight(latitude) || !checkIsRight(longtitude)) {
                    Toast.makeText(this, "Please make sure the latitude & longtitude is right", Toast.LENGTH_SHORT)
                        .show();
                } else {
                    mPolygon.setPoints(MapUtils
                        .createRectangle(new LatLng(Double.valueOf(latitude), Double.valueOf(longtitude)), 0.5, 0.5));
                }
            }
        }
    }

    /**
     * 获取多边形的点信息
     * Get the point position information of the polygon
     */
    public void getPoints(View v) {
        if (null != mPolygon) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < mPolygon.getPoints().toArray().length; i++) {
                stringBuilder.append(mPolygon.getPoints().get(i).toString());
            }
            Toast.makeText(this, "Polygon points is " + stringBuilder, Toast.LENGTH_LONG).show();
        }
    }
    /**
     * 设置多边形的轮廓颜色
     * Set the outline color of the polygon
     */
    public void setStokeColor(View v) {
        if (null != mPolygon) {
            mPolygon.setStrokeColor(Color.YELLOW);
        }
    }
    /**
     * 设置多边形的轮廓颜色
     * Get the outline color of the polygon
     */
    public void getStokeColor(View v) {
        if (null != mPolygon) {
            polygonShown.setText("Polygon color is " + Integer.toHexString(mPolygon.getStrokeColor()));
        }
    }
    /**
     * 设置多边形的填充颜色
     * Set the fill color of the polygon
     */
    public void setFillColor(View v) {
        if (null != mPolygon) {
            mPolygon.setFillColor(Color.CYAN);
        }
    }
    /**
     * 获取多边形的填充颜色
     * Get the fill color of the polygon
     */
    public void getFillColor(View v) {
        if (null != mPolygon) {
            polygonShown.setText("Polygon color is " + Integer.toHexString(mPolygon.getFillColor()));
        }
    }
    /**
     * 设置多边形的Tag
     * Set the tag of the polygon
     */
    public void setTag(View v) {
        if (null != mPolygon) {
            String tag = polygonTag.getText().toString().trim();
            if (checkIsEdit(tag)) {
                Toast.makeText(this, "Please make sure the width is Edited", Toast.LENGTH_SHORT).show();
            } else {
                mPolygon.setTag(tag);
            }
        }
    }
    /**
     * 获取多边形的Tag
     * Get the tag of the polygon
     */
    public void getTag(View v) {
        if (null != mPolygon) {
            polygonShown.setText(String.valueOf(mPolygon.getTag() == null ? "Tag is null" : mPolygon.getTag()));
        }
    }
    /**
     * 添加多边形点击事件
     * Add polygon click event
     */
    public void addClickEvent(View v) {
        if (null != mPolygon) {
            hmap.setOnPolygonClickListener(new HuaweiMap.OnPolygonClickListener() {
                @Override
                public void onPolygonClick(Polygon circle) {
                    Toast.makeText(getApplicationContext(), "Polygon is clicked.", Toast.LENGTH_LONG).show();
                }
            });
        }
    }
    /**
     * 设置多边形可点击
     * Set polygons clickable
     */
    public void setClickableTrue(View v) {
        if (null != mPolygon) {
            mPolygon.setClickable(true);
        }
    }

    /**
     * 设置多边形不可点击
     * Set polygons are not clickable
     */
    public void setClickableFalse(View v) {
        if (null != mPolygon) {
            mPolygon.setClickable(false);
        }
    }

}
