package com.reactnativenavigation.react;

import android.os.Bundle;
import android.view.View;

import com.facebook.react.ReactRootView;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.reactnativenavigation.controllers.NavigationActivity;
import com.reactnativenavigation.layout.LayoutFactory;

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
                LayoutFactory factory = new LayoutFactory(NavigationActivity.instance, new LayoutFactory.RootViewCreator() {
                    @Override
                    public View createRootView(String id, String name) {
                        ReactRootView rootView = new ReactRootView(NavigationActivity.instance);
                        Bundle opts = new Bundle();
                        opts.putString("id", id);
                        rootView.startReactApplication(NavigationActivity.instance.getHost().getReactInstanceManager(), name, opts);
                        return rootView;
                    }
                });

//                Map<String, Object> node = new HashMap<String, Object>();
//                node.put("id", container.getString("id"));
//                HashMap<String, Object> data = new HashMap<>();
//                data.put("name", container.getMap("data").getString("name"));
//                node.put("data", data);
//                View rootView = factory.create(node);
//                NavigationActivity.instance.setContentView(rootView);
            }
        });
    }
}
