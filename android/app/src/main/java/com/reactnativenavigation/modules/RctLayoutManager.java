package com.reactnativenavigation.modules;

import android.app.Activity;
import android.content.Intent;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.reactnativenavigation.activities.RctActivity;
import com.reactnativenavigation.utils.ContextProvider;

/**
 * Created by guyc on 20/03/16.
 */
public class RctLayoutManager extends ReactContextBaseJavaModule {
    public static final String REACT_CLASS = "RctLayoutManager";

    public RctLayoutManager(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @ReactMethod
    public void setLayout(String componentName) {
        Activity context = ContextProvider.getActivityContext();
        if (context != null && !context.isFinishing()) {
            Intent rctActivityIntent = new Intent(context, RctActivity.class);
            rctActivityIntent.putExtra(RctActivity.EXTRA_COMPONENT_NAME, componentName);
            context.startActivity(rctActivityIntent);
        }
    }
}
