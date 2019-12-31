
package com.reactlibrary.hms;

import static com.reactlibrary.hms.utils.CheckUtils.checkIsEdit;
import static com.reactlibrary.hms.utils.CheckUtils.checkIsRight;

import com.huawei.hms.maps.CameraUpdate;
import com.huawei.hms.maps.CameraUpdateFactory;
import com.huawei.hms.maps.HuaweiMap;
import com.huawei.hms.maps.OnMapReadyCallback;
import com.huawei.hms.maps.SupportMapFragment;
import com.huawei.hms.maps.model.CameraPosition;
import com.huawei.hms.maps.model.Circle;
import com.huawei.hms.maps.model.CircleOptions;
import com.huawei.hms.maps.model.LatLng;
import com.huawei.hms.maps.util.LogM;
import com.reactlibrary.R;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 圆相关接口实现
 * circle related
 */

public class CircleDemo extends AppCompatActivity implements OnMapReadyCallback {
    private static final String TAG = "CircleDemo";

    private SupportMapFragment mSupportMapFragment;

    private HuaweiMap hmap;

    private Circle mCircle;

    private TextView circleShown;

    private EditText centerLatitude;

    private EditText centerLongtitude;

    private EditText circleRadius;

    private EditText circleStokeWidth;

    private EditText circleTag;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_demo);
        mSupportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapInCircle);
        mSupportMapFragment.getMapAsync(this);

        circleShown = findViewById(R.id.circleShown);
        centerLatitude = findViewById(R.id.centerLatitude);
        centerLongtitude = findViewById(R.id.centerLongtitude);
        circleRadius = findViewById(R.id.circleRadius);
        circleStokeWidth = findViewById(R.id.circleStokeWidth);
        circleTag = findViewById(R.id.circleTag);
    }

    @Override
    public void onMapReady(HuaweiMap paramHuaweiMap) {
        LogM.i(TAG, "onMapReady: ");
        hmap = paramHuaweiMap;
        hmap.setMyLocationEnabled(true);
    }
    /**
     * 移除圆
     * remove the circle
     */
    public void removeCircle(View view) {
        if (null != mCircle) {
            mCircle.remove();
        }
    }
    /**
     * 添加圆到地图上
     * add a circle on the map
     */
    public void addCircle(View view) {
        if(null == hmap){
            return;
        }
        if (null != mCircle) {
            mCircle.remove();
        }
        mCircle = hmap
            .addCircle(new CircleOptions().center(new LatLng(31.97846, 118.76454)).radius(500).fillColor(0XFF00FFFF).strokeWidth(10).strokeColor(Color.RED));
    }
    /**
     * 设置圆的圆心
     * Set the center of the circle
     */
    public void setCenter(View v) {
        if (null != mCircle) {
            LatLng center = null;
            if (!TextUtils.isEmpty(centerLatitude.getText()) && !TextUtils.isEmpty(centerLongtitude.getText())) {
                String latitude = centerLatitude.getText().toString().trim();
                String longtitude = centerLongtitude.getText().toString().trim();
                center = new LatLng(Double.valueOf(latitude), Double.valueOf(longtitude));
            }
            try {
                this.mCircle.setCenter(center);
            } catch (NullPointerException e) {
                LogM.e(TAG, "NullPointerException " + e.toString());
                Toast.makeText(this, "NullPointerException", Toast.LENGTH_SHORT).show();
            }
        }
    }
    /**
     * 获得圆心坐标
     * Get the center coordinates
     */
    public void getCenter(View v) {
        if (null != mCircle) {

            circleShown.setText("Circle center is " + this.mCircle.getCenter().toString());
        }
    }
    /**
     * 设置圆半径
     * Set the radius of the circle
     */
    public void setRadius(View v) {
        if (null != mCircle) {
            String radius = circleRadius.getText().toString().trim();
            if (checkIsEdit(radius)) {
                Toast.makeText(this, "Please make sure the radius is Edited", Toast.LENGTH_SHORT).show();
            } else {
                if (!checkIsRight(radius)) {
                    Toast.makeText(this, "Please make sure the radius is right", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        this.mCircle.setRadius(Double.valueOf(radius));
                    } catch (IllegalArgumentException e) {
                        Toast.makeText(this, "IllegalArgumentException ", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }
    /**
     * 获得圆半径
     * Get the radius of the circle
     */
    public void getRadius(View v) {
        if (null != mCircle) {
            circleShown.setText("Circle radius is " + this.mCircle.getRadius());
        }
    }
    /**
     * 获取圆的填充颜色
     * Get the fill color of the circle
     */
    public void setFillColor(View v) {
        if (null != mCircle) {
            this.mCircle.setFillColor(Color.RED);
        }
    }
    /**
     * 设置圆的填充颜色
     * Set the fill color of the circle
     */
    public void getFillColor(View v) {
        if (null != mCircle) {
            circleShown.setText("Circle fill color is " + Integer.toHexString(this.mCircle.getFillColor()));
        }
    }

    boolean flag = true;
    public void setStokeColor(View v) {
        if (flag) {
            this.mCircle.setStrokeColor(Color.RED);
            flag = false;
        } else {
            this.mCircle.setStrokeColor(Color.GRAY);
            flag = true;
        }
    }
    /**
     * 获取圆的轮廓颜色
     * Get the outline color of the circle
     */
    public void getStokeColor(View v) {
        if (null != mCircle) {
            circleShown.setText("Circle stroke color is " + Integer.toHexString(this.mCircle.getStrokeColor()));
        }
    }
    /**
     * 设置圆的轮廓宽度
     * Set the outline width of the circle
     */
    public void setWidth(View v) {
        if (null != mCircle) {
            String width = circleStokeWidth.getText().toString().trim();
            if (checkIsEdit(width)) {
                Toast.makeText(this, "Please make sure the width is Edited", Toast.LENGTH_SHORT).show();
            } else {
                if (!checkIsRight(width)) {
                    Toast.makeText(this, "Please make sure the width is right", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        this.mCircle.setStrokeWidth(Float.valueOf(width));
                    } catch (IllegalArgumentException e) {
                        LogM.e(TAG, "IllegalArgumentException " + e.toString());
                        Toast.makeText(this, "IllegalArgumentException", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        }
    }
    /**
     * 获取圆的轮廓宽度
     * Get the outline width of the circle
     */
    public void getWidth(View v) {
        if (null != mCircle) {
            circleShown.setText("Circle stroke width is " + this.mCircle.getStrokeWidth());
        }
    }
    /**
     * 设置圆的Tag
     * Set the tag of the circle
     */
    public void setTag(View v) {
        if (null != mCircle) {
            String tag = circleTag.getText().toString().trim();
            if (checkIsEdit(tag)) {
                Toast.makeText(this, "Please make sure the width is Edited", Toast.LENGTH_SHORT).show();
            } else {
                this.mCircle.setTag(tag);
            }
        }
    }
    /**
     * 获得圆的Tag
     * Get the tag of the circle
     */
    public void getTag(View v) {
        if (null != mCircle) {
            circleShown.setText(String.valueOf(this.mCircle.getTag()));
        }
    }
    /**
     *
     */
    public void addClickEvent(View v) {
        if (null != mCircle) {
            hmap.setOnCircleClickListener(new HuaweiMap.OnCircleClickListener() {
                @Override
                public void onCircleClick(Circle circle) {
                    if (mCircle.equals(circle))
                    {
                        Toast.makeText(getApplicationContext(), "Circle is clicked.", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }
    /**
     *
     */
    public void setClickableTrue(View v) {
        if (null != mCircle) {
            this.mCircle.setClickable(true);
        }
    }

    /**
     *
     */
    public void setClickableFalse(View v) {
        if (null != mCircle) {
            this.mCircle.setClickable(false);
        }
    }

}
