package com.reactnativenavigation.views;

import android.content.Context;
import android.os.Bundle;

import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactRootView;
import com.reactnativenavigation.NavigationApplication;
import com.reactnativenavigation.react.ReactViewHacks;

public class ContentView extends ReactRootView {

    private final String screenId;
    private final Bundle passProps;
    private Bundle navigationParams;

    public ContentView(Context context, final String screenId, Bundle passProps, Bundle navigationParams) {
        super(context);
        this.screenId = screenId;
        this.passProps = passProps;
        this.navigationParams = navigationParams;
        attachToJS();
    }

    private void attachToJS() {
        ReactInstanceManager react = NavigationApplication.instance.getNavigationReactInstance().getReactInstanceManager();
        startReactApplication(react, screenId, mergePropsAndNavigationParams());
    }

    private Bundle mergePropsAndNavigationParams() {
        if (passProps != null) {
            navigationParams.putAll(passProps);
        }
        return navigationParams;
    }

    public void preventUnmountOnDetachedFromWindow() {
        ReactViewHacks.preventUnmountOnDetachedFromWindow(this);
    }

    public void ensureUnmountOnDetachedFromWindow() {
        ReactViewHacks.ensureUnmountOnDetachedFromWindow(this);
    }

    public void preventMountAfterReattachedToWindow() {
        ReactViewHacks.preventMountAfterReattachedToWindow(this);
    }
}
