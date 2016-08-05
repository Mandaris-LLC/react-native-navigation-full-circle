package com.reactnativenavigation.views;

import android.content.Context;
import android.os.Bundle;

import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactRootView;
import com.reactnativenavigation.NavigationApplication;
import com.reactnativenavigation.params.ScreenParams;
import com.reactnativenavigation.react.ReactViewHacks;

public class ContentView extends ReactRootView {

    private final String screenId;
    private final String navigatorEventId;
    private final Bundle passProps;

    public ContentView(Context context, ScreenParams screenParams, String screenId, Bundle passProps) {
        super(context);
        this.screenId = screenId;
        navigatorEventId = screenParams.navigatorEventId;
        this.passProps = mergePropsAndNavigationParams(screenParams, passProps);
        attachToJS();
    }

    public String getNavigatorEventId() {
        return navigatorEventId;
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

    private void attachToJS() {
        ReactInstanceManager react = NavigationApplication.instance.getNavigationReactInstance().getReactInstanceManager();
        startReactApplication(react, screenId, passProps);
    }

    private Bundle mergePropsAndNavigationParams(ScreenParams screenParams, Bundle passProps) {
        Bundle navigationParams = (Bundle) screenParams.navigationParams.clone();
        if (passProps != null) {
            navigationParams.putAll(passProps);
        }
        return navigationParams;
    }
}
