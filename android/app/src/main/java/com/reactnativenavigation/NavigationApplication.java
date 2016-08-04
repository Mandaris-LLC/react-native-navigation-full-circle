package com.reactnativenavigation;

import android.app.Application;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.shell.MainReactPackage;
import com.reactnativenavigation.bridge.NavigationReactPackage;
import com.reactnativenavigation.react.NavigationReactInstance;

import java.util.ArrayList;
import java.util.List;

public abstract class NavigationApplication extends Application {

    public static NavigationApplication instance;
    private NavigationReactInstance navigationReactInstance;
    private Handler handler;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        handler = new Handler(getMainLooper());
    }

    public void runOnMainThread(Runnable runnable) {
        handler.post(runnable);
    }

    public void runOnMainThread(Runnable runnable, long delay) {
        handler.postDelayed(runnable, delay);
    }

    public NavigationReactInstance getNavigationReactInstance() {
        return navigationReactInstance;
    }

    public final List<ReactPackage> createReactPackages() {
        List<ReactPackage> list = new ArrayList<>();
        list.add(new MainReactPackage());
        list.add(new NavigationReactPackage());
        addAdditionalReactPackagesIfNeeded(list);
        return list;
    }

    private void addAdditionalReactPackagesIfNeeded(List<ReactPackage> list) {
        List<ReactPackage> additionalReactPackages = createAdditionalReactPackages();
        if (additionalReactPackages == null) {
            return;
        }

        for (ReactPackage reactPackage : additionalReactPackages) {
            if (reactPackage instanceof MainReactPackage)
                throw new RuntimeException("Do not create a new MainReactPackage. This is created for you.");
            if (reactPackage instanceof NavigationReactPackage)
                throw new RuntimeException("Do not create a new NavigationReactPackage. This is created for you.");
        }

        list.addAll(additionalReactPackages);
    }

    public String getJsEntryFileName() {
        return "index.android";
    }

    public String getBundleAssetName() {
        return "index.android.bundle";
    }

    public ReactContext getReactContext() {
        if (navigationReactInstance == null) {
            return null;
        }
        return navigationReactInstance.getReactInstanceManager().getCurrentReactContext();
    }

    public boolean isReactInstanceManagerInitialized() {
        return navigationReactInstance != null && navigationReactInstance.getReactInstanceManager() != null;
    }

    public void sendNavigatorEvent(String eventId, String navigatorEventId) {
        if (navigationReactInstance == null) {
            return;
        }
        navigationReactInstance.getReactEventEmitter().sendNavigatorEvent(eventId, navigatorEventId);
    }

    public void sendNavigatorEvent(String eventId, String navigatorEventId, WritableMap data) {
        if (navigationReactInstance == null) {
            return;
        }
        navigationReactInstance.getReactEventEmitter().sendNavigatorEvent(eventId, navigatorEventId, data);
    }

    public void sendEvent(String eventId, String navigatorEventId) {
        if (navigationReactInstance == null) {
            return;
        }
        navigationReactInstance.getReactEventEmitter().sendEvent(eventId, navigatorEventId);
    }

    public void sendNavigatorEvent(String eventId, WritableMap arguments) {
        if (navigationReactInstance == null) {
            return;
        }
        navigationReactInstance.getReactEventEmitter().sendEvent(eventId, arguments);
    }

    public void startReactContext() {
        navigationReactInstance = new NavigationReactInstance();
        navigationReactInstance.startReactContextOnceInBackgroundAndExecuteJS();
    }

    public abstract boolean isDebug();

    @Nullable
    public abstract List<ReactPackage> createAdditionalReactPackages();
}
