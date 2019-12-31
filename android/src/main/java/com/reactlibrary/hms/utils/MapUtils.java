
package com.reactlibrary.hms.utils;

import java.util.Arrays;
import java.util.List;

import com.huawei.hms.maps.model.LatLng;

public class MapUtils {

    public static final LatLng HUAWEI_CENTER = new LatLng(31.98559, 118.76613);

    public static final LatLng APARTMENT_CENTER = new LatLng(31.97480, 118.75682);

    public static final LatLng EPARK_CENTER = new LatLng(31.97846, 118.76454);

    public static List<LatLng> createRectangle(LatLng center, double halfWidth, double halfHeight) {
        return Arrays.asList(new LatLng(center.latitude - halfHeight, center.longitude - halfWidth),
            new LatLng(center.latitude - halfHeight, center.longitude + halfWidth),
            new LatLng(center.latitude + halfHeight, center.longitude + halfWidth),
            new LatLng(center.latitude + halfHeight, center.longitude - halfWidth),
            new LatLng(center.latitude - halfHeight, center.longitude - halfWidth));
    }

    public static final int MAP_TYPE_NONE = 0;

    public static final int MAP_TYPE_NORMAL = 1;

    public static final float MAX_ZOOM_LEVEL = 22;

    public static final float MIN_ZOOM_LEVEL = 0;

}
