package com.reactnativenavigation.views;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactRootView;
import com.reactnativenavigation.NavigationApplication;
import com.reactnativenavigation.react.ReactViewHacks;

public class ContentView extends ReactRootView {

    private final String screenId;
    private final Bundle passProps;
    private Bundle navigationParams;
    private ScrollViewAttacher scrollViewAttacher;

    public ContentView(Context context, String screenId, Bundle passProps, Bundle navigationParams, ScrollDirectionListener.OnScrollChanged scrollListener) {
        super(context);
        this.screenId = screenId;
        this.passProps = passProps;
        this.navigationParams = navigationParams;
        scrollViewAttacher = new ScrollViewAttacher(scrollListener);
    }

    public void init() {
        ReactInstanceManager react = NavigationApplication.instance.getNavigationReactInstance().getReactInstanceManager();
        startReactApplication(react, screenId, mergePropsAndNavigationParams());
    }

    @Override
    public void onViewAdded(View child) {
        super.onViewAdded(child);
        scrollViewAttacher.onViewAdded(child);
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
}
