package com.reactnativenavigation;

import android.app.Application;
import android.os.Handler;
import android.support.annotation.NonNull;

import com.facebook.react.ReactPackage;
import com.facebook.react.shell.MainReactPackage;
import com.reactnativenavigation.bridge.NavigationReactPackage;
import com.reactnativenavigation.react.NavigationReactInstance;

import java.util.Arrays;
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
        navigationReactInstance = new NavigationReactInstance();
        navigationReactInstance.startReactContextOnceInBackgroundAndExecuteJS();
    }

    public void runOnMainThread(Runnable runnable) {
        handler.post(runnable);
    }

    public NavigationReactInstance getNavigationReactInstance() {
        return navigationReactInstance;
    }

    public final List<ReactPackage> createReactPackages() {
        List<ReactPackage> list = Arrays.asList(
                new MainReactPackage(),
                new NavigationReactPackage());
        addAdditionalReactPackagesIfNeeded(list);
        return list;
    }

    private void addAdditionalReactPackagesIfNeeded(List<ReactPackage> list) {
        List<ReactPackage> additionalReactPackages = createAdditionalReactPackages();

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

    public abstract boolean isDebug();

    @NonNull
    public abstract List<ReactPackage> createAdditionalReactPackages();

    public void sendEvent(String eventId, String navigatorEventId) {
        navigationReactInstance.getReactEventEmitter().sendEvent(eventId, navigatorEventId);
    }
}
