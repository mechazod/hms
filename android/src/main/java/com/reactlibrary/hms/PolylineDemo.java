
package com.reactlibrary.hms;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.huawei.hms.maps.CameraUpdate;
import com.huawei.hms.maps.CameraUpdateFactory;
import com.huawei.hms.maps.HuaweiMap;
import com.huawei.hms.maps.OnMapReadyCallback;
import com.huawei.hms.maps.SupportMapFragment;
import com.reactlibrary.R;
import com.reactlibrary.hms.utils.MapUtils;
import com.huawei.hms.maps.model.LatLng;
import com.huawei.hms.maps.model.Polyline;
import com.huawei.hms.maps.model.PolylineOptions;
import com.huawei.hms.maps.util.LogM;

import java.util.ArrayList;
import java.util.List;

import static com.reactlibrary.hms.utils.CheckUtils.checkIsEdit;
import static com.reactlibrary.hms.utils.CheckUtils.checkIsRight;

/**
 * about polyline
 *
 */
public class
PolylineDemo extends AppCompatActivity implements OnMapReadyCallback {
    private static final String TAG = "PolylineDemo";

    private SupportMapFragment mSupportMapFragment;

    private HuaweiMap hmap;

    private Polyline mPolyline;

    private TextView polylineShown;

    private EditText oneLatitude;

    private EditText oneLongtitude;

    private EditText polylineStokeWidth;

    private EditText polylineTag;

    private List<LatLng> points = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_polyline_demo);
        mSupportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapInPolyline);
        mSupportMapFragment.getMapAsync(this);

        polylineShown = findViewById(R.id.polylineShown);
        oneLatitude = findViewById(R.id.oneLatitude);
        oneLongtitude = findViewById(R.id.oneLongtitude);
        polylineStokeWidth = findViewById(R.id.polylineStokeWidth);
        polylineTag = findViewById(R.id.polylineTag);

        points.add(MapUtils.HUAWEI_CENTER);
        points.add(MapUtils.APARTMENT_CENTER);
        points.add(MapUtils.EPARK_CENTER);
    }


    @Override
    public void onMapReady(HuaweiMap paramHuaweiMap) {
        LogM.i(TAG, "onMapReady: ");
        hmap = paramHuaweiMap;
        hmap.setMyLocationEnabled(true);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLng(new LatLng(31.97846, 118.76454));
        hmap.animateCamera(cameraUpdate);

    }

    public void addPolyline(View view) {
        if(null == hmap){
            return;
        }
        if (null != mPolyline) {
            mPolyline.remove();
        }
        mPolyline = hmap.addPolyline(
            new PolylineOptions().add(MapUtils.HUAWEI_CENTER, MapUtils.APARTMENT_CENTER, MapUtils.EPARK_CENTER)
                .color(Color.BLUE)
                .width(3));
        hmap.setOnPolylineClickListener(new HuaweiMap.OnPolylineClickListener() {

            @Override
            public void onPolylineClick(Polyline polyline) {
                LogM.i(TAG, "onMapReady:onPolylineClick ");
            }
        });
    }

    public void removePolyline(View view) {
        if (null != mPolyline) {
            mPolyline.remove();
        }
        points.clear();
        points.add(MapUtils.HUAWEI_CENTER);
        points.add(MapUtils.APARTMENT_CENTER);
        points.add(MapUtils.EPARK_CENTER);
    }

    public void setOnePoint(View v) {
        if (null != mPolyline) {
            String latitude = oneLatitude.getText().toString().trim();
            String longtitude = oneLongtitude.getText().toString().trim();
            if (checkIsEdit(latitude) || checkIsEdit(longtitude)) {
                Toast.makeText(this, "Please make sure the latitude & longtitude is Edited", Toast.LENGTH_SHORT).show();
            } else {
                if (!checkIsRight(latitude) || !checkIsRight(longtitude)) {
                    Toast.makeText(this, "Please make sure the latitude & longtitude is right", Toast.LENGTH_SHORT)
                        .show();
                } else {
                    points.add(new LatLng(Double.valueOf(latitude), Double.valueOf(longtitude)));
                    this.mPolyline.setPoints(points);
                }
            }
        }
    }

    public void getPoints(View v) {
        if (null != mPolyline) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < this.mPolyline.getPoints().toArray().length; i++) {
                stringBuilder.append(this.mPolyline.getPoints().get(i).toString());
            }
            Toast.makeText(this, "Polyline points is " + stringBuilder, Toast.LENGTH_LONG).show();
        }
    }

    public void setStokeColor(View v) {

        if (null != mPolyline) {
            this.mPolyline.setColor(Color.YELLOW);
        }
    }

    public void getStokeColor(View v) {
        if (null != mPolyline) {
            polylineShown.setText("Polyline color is " + Integer.toHexString(this.mPolyline.getColor()));
        }
    }

    public void setWidth(View v) {
        if (null != mPolyline) {
            String width = polylineStokeWidth.getText().toString().trim();
            if (checkIsEdit(width)) {
                Toast.makeText(this, "Please make sure the width is Edited", Toast.LENGTH_SHORT).show();
            } else {
                if (!checkIsRight(width)) {
                    Toast.makeText(this, "Please make sure the width is right", Toast.LENGTH_SHORT).show();
                } else {
                    this.mPolyline.setWidth(Float.valueOf(width));
                }
            }
        }
    }

    public void getWidth(View v) {
        if (null != mPolyline) {
            polylineShown.setText("Circle width is " + this.mPolyline.getWidth());
        }
    }

    public void setTag(View v) {
        if (null != mPolyline) {
            String tag = polylineTag.getText().toString().trim();
            if (checkIsEdit(tag)) {
                Toast.makeText(this, "Please make sure the width is Edited", Toast.LENGTH_SHORT).show();
            } else {
                this.mPolyline.setTag(tag);
            }
        }
    }

    public void getTag(View v) {
        if (null != mPolyline) {
            polylineShown
                .setText(String.valueOf(this.mPolyline.getTag() == null ? "Tag is null" : this.mPolyline.getTag()));
        }
    }

    public void addClickEvent(View v) {
        if (null != mPolyline) {
            hmap.setOnPolylineClickListener(new HuaweiMap.OnPolylineClickListener() {
                @Override
                public void onPolylineClick(Polyline circle) {
                    Toast.makeText(getApplicationContext(), "Polyline is clicked.", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    public void setClickableTrue(View v) {
        if (null != mPolyline) {
            this.mPolyline.setClickable(true);
        }
    }

    public void setClickableFalse(View v) {
        if (null != mPolyline) {
            this.mPolyline.setClickable(false);
        }
    }
}
