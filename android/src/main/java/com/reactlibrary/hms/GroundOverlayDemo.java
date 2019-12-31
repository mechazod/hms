
package com.reactlibrary.hms;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.huawei.hms.maps.HuaweiMap;
import com.huawei.hms.maps.OnMapReadyCallback;
import com.huawei.hms.maps.SupportMapFragment;
import com.reactlibrary.R;
import com.reactlibrary.hms.utils.MapUtils;
import com.huawei.hms.maps.model.BitmapDescriptorFactory;
import com.huawei.hms.maps.model.GroundOverlay;
import com.huawei.hms.maps.model.GroundOverlayOptions;
import com.huawei.hms.maps.model.LatLng;
import com.huawei.hms.maps.model.LatLngBounds;
import com.huawei.hms.maps.util.LogM;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static com.reactlibrary.hms.utils.CheckUtils.checkIsEdit;
import static com.reactlibrary.hms.utils.CheckUtils.checkIsRight;

/**
 * 覆盖物相关
 * about groundoverlay
 *
 */
public class GroundOverlayDemo extends AppCompatActivity implements OnMapReadyCallback {
    private static final String TAG = "GroundOverlayDemo";

    private SupportMapFragment mSupportMapFragment;

    private HuaweiMap hmap;

    private GroundOverlay overlay;

    private EditText toprightLatitude;

    private EditText toprightLongtitude;

    private EditText bottomleftLatitude;

    private EditText bottomleftLongtitude;

    private EditText positionLatitude;

    private EditText positionLongtitude;

    private EditText imageWidth;

    private EditText imageHeight;

    private EditText groundOverlayTag;

    private TextView groundOverlayShown;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groudoverlay_demo);
        mSupportMapFragment =
            (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapInGroundOverlay);
        mSupportMapFragment.getMapAsync(this);

        toprightLatitude = findViewById(R.id.toprightLatitude);
        toprightLongtitude = findViewById(R.id.toprightLongtitude);
        bottomleftLatitude = findViewById(R.id.bottomleftLatitude);
        bottomleftLongtitude = findViewById(R.id.bottomleftLongtitude);
        positionLatitude = findViewById(R.id.positionLatitude);
        positionLongtitude = findViewById(R.id.positionLongtitude);
        imageWidth = findViewById(R.id.imageWidth);
        imageHeight = findViewById(R.id.imageHeight);
        groundOverlayTag = findViewById(R.id.groundOverlayTag);
        groundOverlayShown = findViewById(R.id.groundOverlayShown);
    }

    @Override
    public void onMapReady(HuaweiMap paramHuaweiMap) {
        LogM.i(TAG, "onMapReady: ");
        hmap = paramHuaweiMap;
        hmap.setMyLocationEnabled(true);
        hmap.getUiSettings().setZoomControlsEnabled(false);
    }

    /**
     * 使用assets目录中的图像创建GroundOverlay
     * Create a GroundOverlay using the images in the assets directory
     */
    public void addFromAsset(View view) {
        if (hmap == null) {
            return;
        }
        if (null != overlay) {
            overlay.remove();
        }
        LogM.d(TAG, "addFromAsset: ");
        GroundOverlayOptions options = new GroundOverlayOptions().position(MapUtils.HUAWEI_CENTER, 50, 50)
            .image(BitmapDescriptorFactory.fromAsset("images/niuyouguo.jpg"));
        this.overlay = hmap.addGroundOverlay(options);
    }

    /**
     * 使用位图图像的资源创建GroundOverlay
     * Create a GroundOverlay using the resources of the bitmap image
     */
    public void addFromResource(View view) {
        if (hmap == null) {
            return;
        }
        if (null != overlay) {
            overlay.remove();
        }
        LogM.d(TAG, "addFromResource: ");
        GroundOverlayOptions options = new GroundOverlayOptions().position(MapUtils.HUAWEI_CENTER, 50, 50)
            .image(BitmapDescriptorFactory.fromResource(R.drawable.niuyouguo));
        this.overlay = hmap.addGroundOverlay(options);
    }
    /**
     * 创建GroundOverlay
     * Create GroundOverlay
     */
    public void addFromBitmap(View view) {
        if (hmap == null) {
            return;
        }
        if (null != overlay) {
            overlay.remove();
        }
        LogM.d(TAG, "addFromBitmap: ");
        Drawable vectorDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.niuyouguo, null);
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(),
            Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        vectorDrawable.draw(canvas);
        GroundOverlayOptions options = new GroundOverlayOptions().position(MapUtils.HUAWEI_CENTER, 50, 50)
            .image(BitmapDescriptorFactory.fromBitmap(bitmap));
        this.overlay = hmap.addGroundOverlay(options);
    }
    /**
     * 创建GroundOverlay
     * Create GroundOverlay
     */
    public void addFromFile(View view) {
        if (hmap == null) {
            return;
        }
        if (null != overlay) {
            overlay.remove();
        }
        LogM.d(TAG, "addFromFile: ");
        FileOutputStream out = null;
        String fileName = "maomao.jpg";
        String localFile = this.getFilesDir() + File.separator + fileName;
        try {
            Bitmap bitmap = BitmapFactory.decodeStream(this.getAssets().open("images/niuyouguo.jpg"));
            out = new FileOutputStream(new File(localFile));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
        } catch (FileNotFoundException e) {
            LogM.d(TAG, "addFromFile FileNotFoundException: " + e.toString());
        } catch (IOException e) {
            LogM.d(TAG, "addFromFile IOException: " + e.toString());
        } finally {
            try {
                if (null != out) {
                    out.flush();
                    out.close();
                }
            } catch (IOException e) {
                LogM.d(TAG, "addFromFile close fileOutputStream IOException: " + e.toString());
            }
        }
        GroundOverlayOptions options = new GroundOverlayOptions().position(MapUtils.HUAWEI_CENTER, 30, 60)
            .image(BitmapDescriptorFactory.fromFile(fileName));
        this.overlay = hmap.addGroundOverlay(options);
    }

    /**
     * 创建GroundOverlay
     * Create GroundOverlay
     */
    public void addFromPath(View view) {
        if (hmap == null) {
            return;
        }
        LogM.d(TAG, "addFromPath");
        if (null != overlay) {
            overlay.remove();
        }
        String path = "/data/data/com.huawei.hms.maps.demo/niuyouguo.jpg";
        FileOutputStream out = null;
        try {
            Bitmap bitmap = BitmapFactory.decodeStream(this.getAssets().open("images/niuyouguo.jpg"));
            out = new FileOutputStream(new File(path));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
        } catch (FileNotFoundException e) {
            LogM.d(TAG, "addFromFile FileNotFoundException: " + e.toString());
        } catch (IOException e) {
            LogM.d(TAG, "addFromFile IOException: " + e.toString());
        } finally {
            try {
                if (null != out) {
                    out.flush();
                    out.close();
                }
            } catch (IOException e) {
                LogM.d(TAG, "addFromFile close fileOutputStream IOException: " + e.toString());
            }
        }

        GroundOverlayOptions options = new GroundOverlayOptions().position(MapUtils.HUAWEI_CENTER, 30, 60)
            .image(BitmapDescriptorFactory.fromPath(path));
        GroundOverlay hmapOverlay = hmap.addGroundOverlay(options);
        if (hmapOverlay == null) {
            return;
        }
        this.overlay = hmapOverlay;
    }
    /**
     * 移除Groudoverlay
     * Remove the Groudoverlay
     */
    public void removeGroudoverlay(View view) {
        LogM.d(TAG, "removeGroudoverlay: ");
        if (null != overlay) {
            overlay.remove();
        }
    }

    /**
     * 获取GroundOverlay的属性
     * Get the properties of the GroundOverlay
     */
    public void getAttributes(View view) {
        if (null != overlay) {
            String bounds = null;
            String position = null;
            if (this.overlay.getBounds() == null) {
                bounds = "null";
            } else {
                bounds = this.overlay.getBounds().toString();
            }
            if (this.overlay.getPosition() == null) {
                position = "null";
            } else {
                position = this.overlay.getPosition().toString();
            }

            Toast
                .makeText(this,
                    "position:" + position + "width:" + this.overlay.getWidth() + "height:" + this.overlay.getHeight()
                        + "bounds:" + bounds,
                    Toast.LENGTH_LONG)
                .show();
        }

    }

    /**
     * 设置GroundOverlay的范围
     * Set the scope of GroundOverlay
     */
    public void setPointsBy2Points(View view) {
        if (null != overlay) {
            String northeastLatitude = toprightLatitude.getText().toString().trim();
            String northeastLongtitude = toprightLongtitude.getText().toString().trim();
            String southwestLatitude = bottomleftLatitude.getText().toString().trim();
            String southwestLontitude = bottomleftLongtitude.getText().toString().trim();
            if (checkIsEdit(northeastLatitude) || checkIsEdit(northeastLongtitude) || checkIsEdit(southwestLatitude)
                    || checkIsEdit(southwestLontitude)) {
                Toast.makeText(this, "Please make sure these latlng are Edited", Toast.LENGTH_SHORT).show();
            } else {
                if (!checkIsRight(northeastLatitude) || !checkIsRight(northeastLongtitude)
                        || !checkIsRight(southwestLatitude) || !checkIsRight(southwestLontitude)) {
                    Toast.makeText(this, "Please make sure these latlng are right", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        this.overlay.setPositionFromBounds(new LatLngBounds(
                                new LatLng(Double.valueOf(southwestLatitude), Double.valueOf(southwestLontitude)),
                                new LatLng(Double.valueOf(northeastLatitude), Double.valueOf(northeastLongtitude))));
                    } catch (IllegalArgumentException e) {
                        Toast.makeText(this, "IllegalArgumentException ", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }
    /**
     * 获取GroundOverlay的范围
     * Get the scope of GroundOverlay
     */
    public void getPointsBy2Points(View view) {
        if (null != overlay) {
            if (this.overlay.getBounds() == null) {
                Toast.makeText(this, "the groundoverlay is added by the other function", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "LatlngBounds :" + this.overlay.getBounds().toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 设置GroundOverlayd的高度和宽度
     * Set the height and width of the GroundOverlay
     */
    public void setPointsBy1PointsWidthHeight(View view) {
        if (null != overlay) {
            String width = imageWidth.getText().toString().trim();
            String height = imageHeight.getText().toString().trim();
            String latitude = positionLatitude.getText().toString().trim();
            String longtitude = positionLongtitude.getText().toString().trim();
            if (checkIsEdit(width) || checkIsEdit(height) || checkIsEdit(latitude) || checkIsEdit(longtitude)) {
                Toast.makeText(this, "Please make sure the width & height & position is Edited", Toast.LENGTH_SHORT)
                    .show();
            } else {
                if (!checkIsRight(width) || !checkIsRight(height) || !checkIsRight(latitude)
                    || !checkIsRight(longtitude)) {
                    Toast.makeText(this, "Please make sure the width & height & position is right", Toast.LENGTH_SHORT)
                        .show();
                } else {
                    this.overlay.setPosition(new LatLng(Double.valueOf(latitude), Double.valueOf(longtitude)));
                    this.overlay.setDimensions(Float.valueOf(width), Float.valueOf(height));
                }
            }
        }

    }
    /**
     * 设置GroundOverlayd的高度、宽度和位置
     * Set the height, width, and position of the GroundOverlay
     */
    public void getPointsBy1PointsWidthHeight(View view) {
        if (null != overlay) {
            if (this.overlay.getPosition() == null || this.overlay.getHeight() == 0 || this.overlay.getWidth() == 0) {
                Toast.makeText(this, "the groundoverlay is added by the other function", Toast.LENGTH_SHORT).show();
            } else {
                Toast
                    .makeText(this,
                        "Position :" + this.overlay.getPosition().toString() + "With :" + this.overlay.getWidth()
                            + "Height :" + this.overlay.getHeight(),
                        Toast.LENGTH_SHORT)
                    .show();
            }
        }
    }

    /**
     * 更改GroundOverlay的图片
     * Change the image of GroundOverlay
     */
    public void setImage(View view) {
        if (null != overlay) {
            this.overlay.setImage(BitmapDescriptorFactory.fromResource(R.drawable.makalong));
        }
    }

    /**
     * 获取GroundOverlay的Tag
     * Get the tag of GroundOverlay
     */
    public void getTag(View v) {
        if (null != overlay) {
            groundOverlayShown.setText("Overlay tag is " + this.overlay.getTag());
        }
    }
    /**
     * 设置GroundOverlay的Tag
     * Set the tag of GroundOverlay
     */
    public void setTag(View v) {
        if (null != overlay) {
            String tag = groundOverlayTag.getText().toString().trim();
            if (checkIsEdit(tag)) {
                Toast.makeText(this, "Please make sure the tag is Edited", Toast.LENGTH_SHORT).show();
            } else {
                this.overlay.setTag(tag);
            }
        }
    }
    /**
     * 设置GroundOverlay可见
     * Set GroundOverlay visible
     */
    public void setVisibleTrue(View view) {
        if (null != overlay) {
            this.overlay.setVisible(true);
        }
    }
    /**
     * 设置GroundOverlay不可见
     * Setting GroundOverlay is not visible
     */
    public void setVisibleFalse(View view) {
        if (null != overlay) {
            this.overlay.setVisible(false);
        }
    }

}
