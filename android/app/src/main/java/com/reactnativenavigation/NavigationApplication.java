package com.reactnativenavigation;

import android.app.Application;
import android.os.Handler;

public abstract class NavigationApplication extends Application {

    public static NavigationApplication instance;

    private Handler handler;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        handler = new Handler(getMainLooper());
    }

    public abstract boolean isDebug();

    public Handler getMainHandler() {
        return handler;
    }


}
