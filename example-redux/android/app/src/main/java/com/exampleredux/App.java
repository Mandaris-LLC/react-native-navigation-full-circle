package com.exampleredux;

import com.reactnativenavigation.NavigationApplication;

public class App extends NavigationApplication {
    @Override
    public boolean isDebug() {
        return BuildConfig.DEBUG;
    }
}
