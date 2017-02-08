package com.reactnativenavigation.react;

import android.os.Bundle;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.reactnativenavigation.controllers.NavigationActivity;

public class NavigationModule extends ReactContextBaseJavaModule {
    public static final String NAME = "RNNBridgeModule";

    public NavigationModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return NAME;
    }

    @ReactMethod
    public void setRoot(final ReadableMap layoutTree) {
        NavigationActivity.instance.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Bundle args = new Bundle();
                args.putString("id", layoutTree.getArray("children").getMap(0).getString("id"));
                NavigationActivity.instance.setRoot(layoutTree.getArray("children").getMap(0).getMap("data").getString("name"), args);
            }
        });
    }
}
