package com.reactnativenavigation;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

public abstract class NavigationApplication extends Application {
    public static NavigationApplication instance;
    private Handler handler;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        handler = new Handler(Looper.getMainLooper());
    }

    public void runOnUiThread(Runnable runnable) {
        handler.post(runnable);
    }

    public void runOnUiThreadDelayed(Runnable runnable, long delayMillis) {
        handler.postDelayed(runnable, delayMillis);
    }

    public abstract boolean isDebug();
}
