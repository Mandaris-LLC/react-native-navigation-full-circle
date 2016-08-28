package com.reactnativenavigation.react;

import android.app.Application;

import com.facebook.react.ReactNativeHost;

public abstract class ReactGatewayHost extends ReactNativeHost implements ReactGateway {
    protected ReactGatewayHost(Application application) {
        super(application);
    }
}
