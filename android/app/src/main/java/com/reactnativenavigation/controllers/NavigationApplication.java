package com.reactnativenavigation.controllers;

import android.app.Application;

public abstract class NavigationApplication extends Application {
    public static NavigationApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public abstract boolean isDebug();
}
