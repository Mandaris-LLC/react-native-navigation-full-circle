package com.reactnativenavigation.controllers;

import android.app.Application;

import com.facebook.react.bridge.ReactContext;
import com.reactnativenavigation.react.NavigationEventEmitter;

public abstract class NavigationApplication extends Application {
    public static NavigationApplication instance;
    public NavigationEventEmitter eventEmitter;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

    }

    public abstract boolean isDebug();

    public void onReactContextInitialized(ReactContext context) {
        eventEmitter = new NavigationEventEmitter(context);
    }
}
