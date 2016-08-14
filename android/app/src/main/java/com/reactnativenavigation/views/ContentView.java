package com.reactnativenavigation.views;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.facebook.react.ReactRootView;
import com.reactnativenavigation.NavigationApplication;
import com.reactnativenavigation.react.ReactViewHacks;
import com.reactnativenavigation.screens.SingleScreen;
import com.reactnativenavigation.utils.ViewUtils;

public class ContentView extends ReactRootView {

    private final String screenId;
    private String navigatorEventId;
    private final Bundle navigationParams;

    boolean isContentVisible = false;
    private SingleScreen.OnDisplayListener onDisplayListener;

    public ContentView(Context context, String screenId, Bundle navigationParams) {
        this(context, screenId, navigationParams, null);
    }

    public ContentView(Context context, String screenId, Bundle navigationParams, String navigatorEventId) {
        super(context);
        this.screenId = screenId;
        this.navigatorEventId = navigatorEventId;
        this.navigationParams = navigationParams;
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

    @Override
    public void onViewAdded(final View child) {
        super.onViewAdded(child);
        detectContentViewVisible(child);
    }

    private void detectContentViewVisible(View child) {
        if (onDisplayListener != null) {
            ViewUtils.runOnPreDraw(child, new Runnable() {
                @Override
                public void run() {
                    if (!isContentVisible) {
                        isContentVisible = true;
                        onDisplayListener.onDisplay();
                        onDisplayListener = null;
                    }
                }
            });
        }
    }

    private void attachToJS() {
        startReactApplication(NavigationApplication.instance.getReactGateway().getReactInstanceManager(), screenId, navigationParams);
    }

    public void setOnDisplayListener(SingleScreen.OnDisplayListener onDisplayListener) {
        this.onDisplayListener = onDisplayListener;
    }
}
