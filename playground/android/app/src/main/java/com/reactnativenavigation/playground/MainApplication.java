package com.reactnativenavigation.playground;

import com.reactnativenavigation.controllers.NavigationApplication;

public class MainApplication extends NavigationApplication {

    @Override
    public boolean isDebug() {
        return BuildConfig.DEBUG;
    }
}
