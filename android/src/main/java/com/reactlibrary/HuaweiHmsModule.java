package com.reactlibrary;

import android.app.Activity;
import android.content.Intent;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReadableMap;
import com.reactlibrary.hms.MainActivity;

public class HuaweiHmsModule extends ReactContextBaseJavaModule {

    private final ReactApplicationContext reactContext;
    private final String ACTIVITY_NOT_FIND = "ACTIVITY_NOT_FIND";

    public static final int MAP_REQUEST = 0x11;

    private ReadableMap options;

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
    public void launchMap(final ReadableMap options, final Promise promise) {
        final Activity currentActivity = getCurrentActivity();
        this.options = options;
        mHMSPromise = promise;

        if (currentActivity == null) {
            if (mHMSPromise != null) {
                mHMSPromise.reject(ACTIVITY_NOT_FIND, "can't find current Activity");
            }
            return;
        }

        this.showHMSDemo(options, promise);
    }

    @ReactMethod
    public void showHMSDemo(final ReadableMap options, final Promise promise) {
        this.mHMSPromise = promise;
        Intent intent = new Intent(reactContext, MainActivity.class);
        reactContext.startActivityForResult(intent, MAP_REQUEST, null);
    }

}
