package com.reactnativenavigation.playground;

import android.support.test.espresso.IdlingResource;

import com.facebook.react.ReactNativeHost;
import com.facebook.react.bridge.NotThreadSafeBridgeIdleDebugListener;
import com.reactnativenavigation.NavigationApplication;
import com.reactnativenavigation.utils.UiThread;

import java.util.concurrent.atomic.AtomicBoolean;

class ReactIdlingResource implements IdlingResource, NotThreadSafeBridgeIdleDebugListener {
    private ResourceCallback callback;
    private AtomicBoolean registered = new AtomicBoolean(false);
    private AtomicBoolean bridgeIdle = new AtomicBoolean(false);
    private AtomicBoolean shouldRun = new AtomicBoolean(false);

    ReactIdlingResource() {
    }

    public void start() {
        shouldRun.set(true);
        UiThread.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (shouldRun.get()) {
                    isIdleNow();
                    UiThread.postDelayed(this, 100);
                }
            }
        }, 100);
    }

    public void stop() {
        shouldRun.set(false);
        ReactNativeHost host = NavigationApplication.instance.getReactNativeHost();
        if (host != null && host.getReactInstanceManager().getCurrentReactContext() != null)
            host.getReactInstanceManager().getCurrentReactContext().getCatalystInstance().removeBridgeIdleDebugListener(this);
    }


    @Override
    public String getName() {
        return "ReactIdlingResource";
    }

    @Override
    public boolean isIdleNow() {
        ReactNativeHost host = NavigationApplication.instance.getReactNativeHost();
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
        if (idle && callback != null) {
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
        bridgeIdle.set(true);
    }

    @Override
    public void onTransitionToBridgeBusy() {
        bridgeIdle.set(false);
    }
}
