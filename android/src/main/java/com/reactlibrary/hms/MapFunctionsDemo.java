
package com.reactlibrary.hms;

import com.huawei.hms.maps.CameraUpdate;
import com.huawei.hms.maps.CameraUpdateFactory;
import com.huawei.hms.maps.HuaweiMap;
import com.huawei.hms.maps.OnMapReadyCallback;
import com.huawei.hms.maps.SupportMapFragment;
import com.reactlibrary.R;
import com.reactlibrary.hms.utils.MapUtils;
import com.huawei.hms.maps.util.LogM;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Basical functions
 * 地图基础功能
 */
public class MapFunctionsDemo extends AppCompatActivity implements OnMapReadyCallback {
    private static final String TAG = "MapFunctionsDemo";

    private SupportMapFragment mSupportMapFragment;

    private HuaweiMap hmap;

    private EditText left;

    private EditText right;

    private EditText top;

    private EditText bottom;

    private EditText minZoomlevel;

    private EditText maxZoomlevel;

    private TextView text;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_map_founctions_demo);
        mSupportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapInFunctions);
        mSupportMapFragment.getMapAsync(this);

        left = findViewById(R.id.paddingleft);
        right = findViewById(R.id.paddingright);
        top = findViewById(R.id.paddingtop);
        bottom = findViewById(R.id.paddingbottom);
        text = findViewById(R.id.founctionsshow);
        minZoomlevel = findViewById(R.id.minZoomlevel);
        maxZoomlevel = findViewById(R.id.maxZoomlevel);
    }

    @Override
    public void onMapReady(HuaweiMap paramHuaweiMap) {
        LogM.i(TAG, "onMapReady: ");
        hmap = paramHuaweiMap;
        hmap.setMyLocationEnabled(true);
        hmap.resetMinMaxZoomPreference();
    }
    /**
     * 获取最大缩放级别参数
     * Get the maximum zoom level parameter
     */
    public void getMaxZoomLevel(View view) {
        if (null != hmap) {
            text.setText(String.valueOf(hmap.getMaxZoomLevel()));
        }

    }

    /**
     * 获取最小缩放级别参数
     * Get the minimum zoom level parameter
     */
    public void getMinZoomLevel(View view) {
        if (null != hmap) {
            text.setText(String.valueOf(hmap.getMinZoomLevel()));
        }

    }
    /**
     * 获取地图类型
     * Get map type
     */
    public void getMapType(View view) {
        if (null != hmap) {
            text.setText((hmap.getMapType() == MapUtils.MAP_TYPE_NONE) ? "MAP_TYPE_NONE" : "MAP_TYPE_NORMAL");
        }

    }

    /**
     * 设置地图类型
     * Set map type
     */
    public void setMapType(View view) {
        if (null != hmap) {
            synchronized (hmap) {
                if (hmap.getMapType() == MapUtils.MAP_TYPE_NORMAL) {
                    hmap.setMapType(HuaweiMap.MAP_TYPE_NONE);
                } else {
                    hmap.setMapType(HuaweiMap.MAP_TYPE_NORMAL);
                }
            }

        }

    }
    /**
     * 获取3D模式设置
     * Get 3D mode settings
     */
    public void is3DMode(View view) {
        if (null != hmap) {
            text.setText(String.valueOf(hmap.isBuildingsEnabled()));
        }

    }

    /**
     * 打开3D开关
     * Turn on the 3D switch
     */
    public void Set3DMode(View view) {
        if (null != hmap) {
            if (hmap.isBuildingsEnabled()) {
                hmap.setBuildingsEnabled(false);
            } else {
                hmap.setBuildingsEnabled(true);
            }
        }

    }
    /**
     * 设置期望的相机缩放级数的最大值
     * Set the maximum value of the desired camera zoom level
     */
    public void setMaxZoomPreference(View view) {
        String text = maxZoomlevel.getText().toString();
        if ((text.trim().length() == 0) || (text.trim().isEmpty()) || (text == null) || ("".equals(text))) {
            Toast.makeText(this, "Please make sure the maxZoom is Edited", Toast.LENGTH_SHORT).show();
        } else {
            if (Float.valueOf(text.trim()) > MapUtils.MAX_ZOOM_LEVEL
                || Float.valueOf(text.trim()) < MapUtils.MIN_ZOOM_LEVEL || !isNumber(text.trim())) {
                Toast.makeText(this, "Please make sure the maxZoom is right", Toast.LENGTH_SHORT).show();
            } else {
                Float maxZoom = Float.valueOf(maxZoomlevel.getText().toString());
                LogM.i(TAG, "setMaxZoomPreference: " + maxZoom);
                if (null != hmap) {
                    hmap.setMaxZoomPreference(maxZoom);
                }

            }
        }
    }

    /**
     * 测试最大缩放参数
     * Test the maximum zoom parameter
     */
    public void testMaxZoom(View view) {
        CameraUpdate cameraUpdate = CameraUpdateFactory.zoomBy(1.0f);
        if (null != hmap) {
            hmap.moveCamera(cameraUpdate);
        }

    }

    /**
     * 设置期望的相机缩放级数的最小值
     * Set the minimum value of the desired camera zoom level
     */
    public void setMinZoomPreference(View view) {
        String text = minZoomlevel.getText().toString();
        if ((text.trim().length() == 0) || (text.trim().isEmpty()) || (text == null) || ("".equals(text))) {
            Toast.makeText(this, "Please make sure the minZoom is Edited", Toast.LENGTH_SHORT).show();
        } else {
            if (Float.valueOf(text.trim()) > MapUtils.MAX_ZOOM_LEVEL
                || Float.valueOf(text.trim()) < MapUtils.MIN_ZOOM_LEVEL || !isNumber(text.trim())) {
                Toast.makeText(this, "Please make sure the minZoom is right", Toast.LENGTH_SHORT).show();
            } else {
                if (null != hmap) {
                    hmap.setMinZoomPreference(Float.valueOf(minZoomlevel.getText().toString()));
                }

            }
        }
    }

    /**
     * 移除先前设置的缩放级别上下边界值
     * Remove the previously set zoom level upper and lower boundary values
     */
    public void resetMinMaxZoomPreference(View view) {
        if (null != hmap) {
            hmap.resetMinMaxZoomPreference();
        }

    }

    /**
     * 给地图设置地图边界填充宽度
     * Set the map border fill width for the map
     */
    public void setPadding(View view) {
        String leftString = left.getText().toString();
        String topString = top.getText().toString();
        String rightString = right.getText().toString();
        String bottomString = bottom.getText().toString();

        if ((leftString.trim().length() == 0) || (leftString.trim().isEmpty()) || (leftString == null)
            || ("".equals(leftString)) || (topString.trim().length() == 0) || (topString.trim().isEmpty())
            || (topString == null) || ("".equals(topString)) || (rightString.trim().length() == 0)
            || (rightString.trim().isEmpty()) || (rightString == null) || ("".equals(rightString))
            || (bottomString.trim().length() == 0) || (bottomString.trim().isEmpty()) || (bottomString == null)
            || ("".equals(bottomString))) {
        } else {
            if (!isNumber(leftString.trim()) || !isNumber(topString.trim()) || !isNumber(rightString.trim())
                || !isNumber(bottomString.trim())) {
                Toast.makeText(this, "Please make sure the padding value is right", Toast.LENGTH_SHORT).show();
            } else {
                if (null != hmap) {
                    hmap.setPadding(Integer.valueOf(left.getText().toString()),
                        Integer.valueOf(top.getText().toString()), Integer.valueOf(right.getText().toString()),
                        Integer.valueOf(bottom.getText().toString()));
                }

            }
        }
    }

    public static boolean isNumber(String value) {
        return isInteger(value) || isDouble(value);
    }

    /**
     * 判断字符串是否是Integer
     * Determine if the string is an integer
     */
    public static boolean isInteger(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * 判断字符串是否是float类型
     *  Determine if the string is a float
     */
    public static boolean isDouble(String value) {
        try {
            Double.parseDouble(value);
            if (value.contains(".")) {
                return true;
            }
            return false;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
