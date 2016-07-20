package com.reactnativenavigation;

import android.app.Application;
import android.os.Handler;

import com.facebook.react.ReactPackage;
import com.facebook.react.shell.MainReactPackage;
import com.reactnativenavigation.bridge.NavigationReactPackage;
import com.reactnativenavigation.react.NavigationReactInstance;

import java.util.Arrays;
import java.util.List;

public abstract class NavigationApplication extends Application implements NavigationReactInstance.ReactContextCreator {

    public static NavigationApplication instance;
    private NavigationReactInstance navigationReactInstance;
    private Handler handler;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        handler = new Handler(getMainLooper());
        navigationReactInstance = new NavigationReactInstance(this);
        navigationReactInstance.startReactContextOnceInBackgroundAndExecuteJS();
    }

    public Handler getMainHandler() {
        return handler;
    }

    public NavigationReactInstance getNavigationReactInstance() {
        return navigationReactInstance;
    }

    @Override
    public final List<ReactPackage> createReactPackages() {
        List<ReactPackage> list = Arrays.asList(
                new MainReactPackage(),
                new NavigationReactPackage());
        list.addAll(createAdditionalReactPackages());
        return list;
    }

    public abstract boolean isDebug();

    public abstract List<ReactPackage> createAdditionalReactPackages();
}
