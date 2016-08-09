package com.reactnativenavigation.views;

import android.content.Context;
import android.os.Bundle;

import com.facebook.react.ReactRootView;
import com.reactnativenavigation.NavigationApplication;
import com.reactnativenavigation.params.ScreenParams;
import com.reactnativenavigation.react.ReactViewHacks;

public class ContentView extends ReactRootView {

    private final String screenId;
    private final String navigatorEventId;
    private final Bundle navigationParams;

    public ContentView(Context context, ScreenParams screenParams, String screenId) {
        super(context);
        this.screenId = screenId;
        navigatorEventId = screenParams.navigatorEventId;
        navigationParams = screenParams.navigationParams;
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
        startReactApplication(NavigationApplication.instance.getReactGateway().getReactInstanceManager(), screenId, navigationParams);
    }
}
