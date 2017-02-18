package com.reactnativenavigation.playground;

import android.support.test.espresso.IdlingResource;

import com.facebook.react.ReactNativeHost;
import com.facebook.react.bridge.NotThreadSafeBridgeIdleDebugListener;
import com.reactnativenavigation.NavigationActivity;
import com.reactnativenavigation.NavigationApplication;

import java.util.concurrent.atomic.AtomicBoolean;

class ReactIdlingResource implements IdlingResource, NotThreadSafeBridgeIdleDebugListener {
    private final NavigationActivity activity;
    private ResourceCallback callback;

    ReactIdlingResource(NavigationActivity activity) {
        android.util.Log.d("DebuggingIsHell", "------------------------------");
        this.activity = activity;
        NavigationApplication.instance.runOnUiThreadDelayed(new Runnable() {
            @Override
            public void run() {
                if (!isIdleNow()) {
                    NavigationApplication.instance.runOnUiThreadDelayed(this, 10);
                }
            }
        }, 10);
    }

    private AtomicBoolean registered = new AtomicBoolean(false);
    private AtomicBoolean bridgeIdle = new AtomicBoolean(false);

    @Override
    public String getName() {
        return "ReactIdlingResource";
    }

    @Override
    public boolean isIdleNow() {
        ReactNativeHost host = activity.getHost();
        boolean hasContext = host != null
                && host.getReactInstanceManager() != null
                && host.getReactInstanceManager().getCurrentReactContext() != null;
        if (!hasContext) {
            return false;
        }

        if (registered.compareAndSet(false, true)) {
            host.getReactInstanceManager().getCurrentReactContext().getCatalystInstance().addBridgeIdleDebugListener(this);
        }

        boolean idle = bridgeIdle.get();
        android.util.Log.d("DebuggingIsHell", "idle " + idle);
        if (idle) {
            callback.onTransitionToIdle();
        }
        return idle;
    }

    @Override
    public void registerIdleTransitionCallback(final ResourceCallback callback) {
        this.callback = callback;
    }

    @Override
    public void onTransitionToBridgeIdle() {
        android.util.Log.d("DebuggingIsHell", "IDLE");
        bridgeIdle.set(true);
    }

    @Override
    public void onTransitionToBridgeBusy() {
        android.util.Log.d("DebuggingIsHell", "busy");
        bridgeIdle.set(false);
    }
}
