package com.reactnativenavigation.react;

import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactPackage;
import com.facebook.react.shell.MainReactPackage;
import com.reactnativenavigation.NavigationApplication;

import java.util.Arrays;
import java.util.List;

public class NavigationReactNativeHost extends ReactNativeHost {
    public NavigationReactNativeHost(NavigationApplication application) {
        super(application);
    }

    @Override
    public boolean getUseDeveloperSupport() {
        return NavigationApplication.instance.isDebug();
    }

    @Override
    protected List<ReactPackage> getPackages() {
        return Arrays.asList(
                new MainReactPackage(),
                new NavigationPackage()
        );
    }
}
