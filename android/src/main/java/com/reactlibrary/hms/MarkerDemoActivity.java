/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2019-2019. All rights reserved.
 */

package com.reactlibrary.hms;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.ACCESS_WIFI_STATE;

import java.util.List;

import com.huawei.hmf.tasks.OnFailureListener;
import com.huawei.hmf.tasks.OnSuccessListener;
import com.huawei.hmf.tasks.Task;
import com.huawei.hms.libraries.places.api.Places;
import com.huawei.hms.libraries.places.api.model.AddressComponent;
import com.huawei.hms.libraries.places.api.model.Place;
import com.huawei.hms.libraries.places.api.model.PlaceLikelihood;
import com.huawei.hms.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.huawei.hms.libraries.places.api.net.FindAutocompletePredictionsResponse;
import com.huawei.hms.libraries.places.api.net.FindCurrentPlaceResponse;
import com.huawei.hms.libraries.places.api.net.PlacesClient;
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
import android.support.annotation.RequiresPermission;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 *
 */
public class MarkerDemoActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static final String TAG = "MarkerDemoActivity";

    private static final LatLng Beijing = new LatLng(48.893478, 2.334595);

    private static final LatLng Nanjing = new LatLng(48.7, 2.31);

    private static final LatLng Shanghai = new LatLng(48.85, 2.78);

    private SupportMapFragment mSupportMapFragment;

    private HuaweiMap hmap;

    private EditText title;

    private EditText snippet;

    private EditText tag;

    private Marker mShanghai;

    private Marker mBeijing;

    private Marker mNanjing;

    private int windowType;

    private boolean clusterable = false;

    private PlacesClient placesClient;

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.marker_demo_activity);
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.MarkerDemoMap);
        if (fragment instanceof SupportMapFragment) {
            mSupportMapFragment = (SupportMapFragment) fragment;
            mSupportMapFragment.getMapAsync(this);
        }
        title = findViewById(R.id.title);
        snippet = findViewById(R.id.snippet);
        tag = findViewById(R.id.tag);
        textView = findViewById(R.id.founctionsshow);
    }

    @Override
    public void onMapReady(HuaweiMap paramHuaweiMap) {
        LogM.i(TAG, "onMapReady: ");
        hmap = paramHuaweiMap;
        hmap.setMyLocationEnabled(true);
        hmap.setInfoWindowAdapter(new CustomInfoWindowAdapter());
        hmap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(48.893478, 2.334595),14));
        placesClient = Places.createClient(this);
    }

    /**
     * InfoWindowAdapter实现类
     * InfoWindowAdapter implementation class
     */
    class CustomInfoWindowAdapter implements HuaweiMap.InfoWindowAdapter {
        boolean isUserFlag = true;

        private final View mWindowView;

        private final View mContentsView;

        CustomInfoWindowAdapter() {
            mWindowView = getLayoutInflater().inflate(R.layout.custom_info_window, null);
            mContentsView = getLayoutInflater().inflate(R.layout.custom_info_contents, null);
        }

        @Override
        public View getInfoWindow(Marker marker) {
            View view = null;
            LogM.d(TAG, "getInfoWindow");
            if (windowType != 3) {
                return view;
            }
            render(marker, mWindowView);
            return mWindowView;
        }

        @Override
        public View getInfoContents(Marker marker) {
            View view = null;
            if (windowType != 2) {
                return view;
            }
            render(marker, mContentsView);
            return mContentsView;
        }

        private void render(Marker marker, View view) {
            setMarkerBadge(marker, view);

            setMarkerTextView(marker, view);

            setMarkerSnippet(marker, view);
        }

        private void setMarkerBadge(Marker marker, View view) {
            int markerBadge;
            // Use the equals method to determine if the marker is the same ,do not use"=="
            if (marker.equals(mBeijing)) {
                markerBadge = R.drawable.badge_bj;
            } else if (marker.equals(mShanghai)) {
                markerBadge = R.drawable.badge_sh;
            } else if (marker.equals(mNanjing)) {
                markerBadge = R.drawable.badge_nj;
            } else {
                markerBadge = 0;
            }
            ((ImageView) view.findViewById(R.id.badge)).setImageResource(markerBadge);
        }

        private void setMarkerTextView(Marker marker, View view) {
            String markerTitle = marker.getTitle();

            TextView titleView = null;

            Object object = view.findViewById(R.id.titlee);
            if (object instanceof TextView) {
                titleView = (TextView) object;
            }
            if (markerTitle == null) {
                titleView.setText("");
            } else {
                SpannableString titleText = new SpannableString(markerTitle);
                titleText.setSpan(new ForegroundColorSpan(Color.BLUE), 0, titleText.length(), 0);
                titleView.setText(titleText);
            }
        }

        private void setMarkerSnippet(Marker marker, View view) {
            String markerSnippet = marker.getSnippet();
            if (marker.getTag() != null) {
                markerSnippet = (String) marker.getTag();
            }
            TextView snippetView = ((TextView) view.findViewById(R.id.snippett));
            if (markerSnippet != null && !markerSnippet.isEmpty()) {
                SpannableString snippetText = new SpannableString(markerSnippet);
                snippetText.setSpan(new ForegroundColorSpan(Color.RED), 0, markerSnippet.length(), 0);
                snippetView.setText(snippetText);
            } else {
                snippetView.setText("");
            }
        }
    }

    /**
     * 添加标记到地图上
     * Add markers on the map
     */
    @RequiresPermission(allOf = {ACCESS_FINE_LOCATION, ACCESS_WIFI_STATE})
    public void addMarker(View view) {
        if (null == hmap) {
            return;
        }
        if (mBeijing == null && mShanghai == null && mNanjing == null) {
            // Uses a colored icon.
            mBeijing = hmap.addMarker(
                new MarkerOptions().position(Beijing).title("Beijing"));
            mShanghai = hmap.addMarker(new MarkerOptions().position(Shanghai)
                .alpha(0.5f)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.badge_ph)));
            mNanjing = hmap.addMarker(new MarkerOptions().position(Nanjing)
                .title("Nanjing")
                .snippet("Population: 8.335 million")
                .clusterable(false));
            hmap.setOnMarkerClickListener(new HuaweiMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    boolean clusterable = marker.isClusterable();
                    Toast.makeText(getApplicationContext(), String.valueOf(clusterable), Toast.LENGTH_SHORT).show();
                    return true;
                }
            });

            hmap.setOnInfoWindowClickListener(new HuaweiMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {
                    if (marker.equals(mNanjing)) {
                        Toast.makeText(getApplicationContext(), "mMelbourne infowindow is clicked", Toast.LENGTH_SHORT)
                            .show();
                    }

                    if (marker.equals(mShanghai)) {
                        Toast.makeText(getApplicationContext(), "mSydney infowindow is clicked", Toast.LENGTH_SHORT)
                            .show();
                    }

                    if (marker.equals(mBeijing)) {
                        Toast.makeText(getApplicationContext(), "mBrisbane infowindow is clicked", Toast.LENGTH_SHORT)
                            .show();
                    }
                }
            });
        }
    }

    /**
     * 设置标记是否聚集
     * Set whether the mark is clustered
     */
    public void setClusterTest(View view) {
        if (null == hmap) {
            return;
        }
        if (clusterable == false) {
            clusterable = true;
            hmap.setMarkersClustering(clusterable);
        } else {
            clusterable = false;
            hmap.setMarkersClustering(clusterable);
        }
    }

    /**
     * 拼接字符串
     * Splicing string
     */
    private String initString(FindAutocompletePredictionsResponse res) {
        StringBuffer sb = new StringBuffer();
        for (Place place : res.getPlaces()) {
            sb.append("LatLng-").append(place.getLatLng().toString()).append(";");
            sb.append("Attributions-").append(listToString(place.getAttributions())).append(";");
            sb.append("Rating-").append(place.getRating()).append(";");
            sb.append("setWebsiteUri-").append(place.getWebsiteUri()).append(";");
            sb.append("setViewport-").append(place.getViewport().toString()).append(";");
            sb.append("setName-").append(place.getName()).append(";");
            sb.append("getPhoneNumber-").append(place.getPhoneNumber()).append(";");
            sb.append("setAddress-").append(place.getAddress()).append(";");
            sb.append("setAddressComponents-")
                .append(setAddressComponentsToString(place.getAddressComponents().asList()))
                .append(";");
        }
        return sb.toString();
    }

    /**
     * 将AddressComponent转化为String
     * Convert AddressComponent to String
     */
    public String setAddressComponentsToString(List<AddressComponent> list) {
        StringBuffer result = new StringBuffer();
        boolean isFirst = true;
        for (AddressComponent ac : list) {
            if (isFirst) {
                result.append("$");
            }
            result.append(ac.getName())
                .append("|")
                .append(ac.getShortName())
                .append("|")
                .append(listToString(ac.getTypes()));
        }
        return result.toString();
    }

    /**
     * List转String
     * Convert List to String
     */
    public String listToString(List<String> list) {
        StringBuffer result = new StringBuffer();
        boolean isFirst = true;
        for (String string : list) {
            if (isFirst) {
                isFirst = false;
            } else {
                result.append(",");
            }
            result.append(string);
        }
        return result.toString();
    }

    /**
     * 移除Marker
     * Remove the marker
     */
    public void deleteMarker(View view) {
        if (null != mNanjing) {
            mNanjing.remove();
            mNanjing = null;
        }

        if (null != mShanghai) {
            mShanghai.remove();
            mShanghai = null;
        }

        if (null != mBeijing) {
            mBeijing.remove();
            mBeijing = null;
        }
    }

    /**
     * 设置marker的title属性
     * Set the title of the marker
     */
    public void setTitle(View view) {
        if (null != placesClient) {
            Log.d(TAG, "addMarker: start");
            Task<FindAutocompletePredictionsResponse> currentPlaceTask =
                placesClient.findAutocompletePredictions(FindAutocompletePredictionsRequest.newInstance("park"));
            currentPlaceTask.addOnSuccessListener(new OnSuccessListener<FindAutocompletePredictionsResponse>() {
                @Override
                public void onSuccess(FindAutocompletePredictionsResponse findAutocompletePredictionsResponse) {
                    Log.d(TAG, "onSuccess() called with: findCurrentPlaceResponse = ["
                        + initString(findAutocompletePredictionsResponse) + "]");
                }
            });

            currentPlaceTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(Exception e) {
                    Log.d(TAG, "onSuccess() called with: error = [" + e.getMessage() + "]");
                }
            });
        }

        String titleStr = title.getText().toString();
        if (titleStr != null && !"".equals(titleStr)) {
            mShanghai.setTitle(titleStr);
        }
    }

    /**
     * 设置marker的Tag
     * Set the tag of Marker
     */
    public void tagTest(View view) {
        String tagStr = tag.getText().toString();
        if (tagStr != null && !"".equals(tagStr)) {
            mBeijing.setTag(tagStr);
        }
    }
    /**
     * 设置为默认窗口
     * Set as default window
     */
    public void setSnippet(View view) {
        String snippetStr = snippet.getText().toString();
        if (snippetStr != null && !"".equals(snippetStr)) {
            mShanghai.setSnippet(snippetStr);
        }
    }

    /**
     * 设置为默认窗口
     * Set as default window
     */
    public void defaultWindow(View view) {
        windowType = 1;
    }
    /**
     * 设置为内容窗口
     * Set as content window
     */
    public void contentWindow(View view) {
        windowType = 2;
    }
    /**
     * 设置为自定义窗口
     * Set as custom window
     */
    public void customWindow(View view) {
        windowType = 3;
    }

    /**
     * 设置marker是否可拖动
     * Set whether the marker can be dragged
     */
    public void dragMarker(View view) {
        if (null == mNanjing) {
            return;
        }
        if (mNanjing.isDraggable()) {
            mNanjing.setDraggable(false);
        } else {
            mNanjing.setDraggable(true);
        }
    }
    /**
     * 设置marker的的图标
     * Set the icon of the marker
     */
    public void setMarkerIcon(View view) {
        if (null != mShanghai) {
            Bitmap bitmap =
                ResourceBitmapDescriptor.drawableToBitmap(this, ContextCompat.getDrawable(this, R.drawable.badge_tr));
            BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(bitmap);

            mShanghai.setIcon(bitmapDescriptor);
        }
    }
    /**
     * 设置marker的锚点
     * Set the anchor of the marker
     */
    public void setMarkerAnchor(View view) {
        if (mBeijing != null) {
            mBeijing.setAnchor(0.9F, 0.9F);
        }
    }

    /**
     * 隐藏信息窗
     * Hide information window
     */
    public void hideWindow(View view) {
        if (null != mBeijing) {
            mBeijing.hideInfoWindow();
        }
    }

    /**
     * 显示信息窗
     * Display information window
     */
    public void showInfoWindow(View view) {
        if (null != mBeijing) {
            mBeijing.showInfoWindow();
        }
    }
}
