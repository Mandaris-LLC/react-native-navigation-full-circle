package com.reactnativenavigation.modules;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.reactnativenavigation.activities.RctActivity;
import com.reactnativenavigation.activities.TabActivity;
import com.reactnativenavigation.adapters.ViewPagerAdapter;
import com.reactnativenavigation.utils.ContextProvider;

/**
 *
 * Created by guyc on 10/03/16.
 */
public class RctActivityModule extends ReactContextBaseJavaModule {
    public static final String REACT_CLASS = "RctActivity";

    public RctActivityModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @ReactMethod
    public void startActivity(String componentName) {
        Activity context = ContextProvider.getActivityContext();
        if (context != null && !context.isFinishing()) {
            Intent rctActivityIntent = new Intent(context, RctActivity.class);
            rctActivityIntent.putExtra(RctActivity.EXTRA_COMPONENT_NAME, componentName);
            context.startActivity(rctActivityIntent);
        }
    }

    @ReactMethod
    public void startTabBasedApp(ReadableArray tabs) {
        Log.v(REACT_CLASS, "Starting tab based app\n" + tabs);


        Activity context = ContextProvider.getActivityContext();
        if (context != null && !context.isFinishing()) {
            // Create the intent
            Intent intent = new Intent(context, TabActivity.class);


            // Set extras
            Bundle extras = new Bundle();
            extras.putSerializable(TabActivity.EXTRA_TABS, ViewPagerAdapter.createDataSet(tabs));
            intent.putExtras(extras);

            context.startActivity(intent);
        }
    }
}
