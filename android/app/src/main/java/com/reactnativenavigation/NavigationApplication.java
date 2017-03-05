package com.reactnativenavigation;

import android.app.Application;

import com.facebook.react.ReactApplication;
import com.facebook.react.ReactNativeHost;
import com.reactnativenavigation.controllers.NavigationActivityLifecycleHandler;
import com.reactnativenavigation.react.NavigationReactNativeHost;

public abstract class NavigationApplication extends Application implements ReactApplication {
    public static NavigationApplication instance;
    private ReactNativeHost host;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        host = new NavigationReactNativeHost(this);
        registerActivityLifecycleCallbacks(new NavigationActivityLifecycleHandler(host.getReactInstanceManager()));
    }

    @Override
    public ReactNativeHost getReactNativeHost() {
        return host;
    }

    public abstract boolean isDebug();
}
