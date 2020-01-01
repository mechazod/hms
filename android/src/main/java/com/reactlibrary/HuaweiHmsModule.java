package com.reactlibrary;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReadableMap;
import com.reactlibrary.hms.MainActivity;
import com.reactlibrary.hms.MapViewDemoActivity;

public class HuaweiHmsModule extends ReactContextBaseJavaModule {

    private final ReactApplicationContext reactContext;
    private final String ACTIVITY_NOT_FIND = "ACTIVITY_NOT_FIND";

    public static final int MAP_REQUEST = 0x11;

    private ReadableMap options;
    private double latCurrentLocation;
    private double lonCurrentLocation;
    private double latTarget;
    private double lonTarget;

    protected Promise mHMSPromise;

    public HuaweiHmsModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return "HuaweiHms";
    }


    @ReactMethod
    public void showHMSDemo(ReadableMap options) {

        this.options = options.getMap("hmsOptions");
        this.latCurrentLocation = this.options.hasKey("latCurrent") ? this.options.getDouble("latCurrent") : 0;
        this.lonCurrentLocation = this.options.hasKey("lonCurrent") ? this.options.getDouble("lonCurrent") : 0;
        this.latTarget = this.options.hasKey("latTarget") ? this.options.getDouble("latTarget") : 14.402940;
        this.lonTarget = this.options.hasKey("lonTarget") ? this.options.getDouble("lonTarget") : 120.989517;

        final Activity currentActivity = getCurrentActivity();
        Intent intent = new Intent(getReactApplicationContext(), MapViewDemoActivity.class);
        intent.putExtra("LON_CURRENT", this.latCurrentLocation);
        intent.putExtra("LAT_CURRENT", this.lonCurrentLocation);
        intent.putExtra("LON_TARGET", this.lonTarget);
        intent.putExtra("LAT_TARGET", this.latTarget);
        currentActivity.startActivityForResult(intent, MAP_REQUEST);
    }
}
