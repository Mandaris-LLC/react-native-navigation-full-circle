package com.reactnativenavigation.views;

import android.content.Context;
import android.os.Bundle;
import android.view.ViewGroup;

import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactRootView;
import com.reactnativenavigation.react.ReactViewHacks;

public class ContentView extends ReactRootView {

    private final ReactInstanceManager reactInstanceManager;
    private final String moduleName;
    private final Bundle passProps;
    private final ScrollDirectionListener.OnScrollChanged scrollListener;

    public ContentView(Context context, ReactInstanceManager reactInstanceManager, String moduleName, Bundle passProps, ScrollDirectionListener.OnScrollChanged scrollListener) {
        super(context);
        this.reactInstanceManager = reactInstanceManager;
        this.moduleName = moduleName;
        this.passProps = passProps;
        this.scrollListener = scrollListener;
    }

    public void init() {
        startReactApplication(reactInstanceManager, moduleName, passProps);
        new ScrollViewAttacher(this, scrollListener).attach();
    }

    public void removeFromParentWithoutUnmount() {
        // Hack in order to prevent the react view from getting unmounted
        ReactViewHacks.preventUnmountOnDetachedFromWindow(this);
        ((ViewGroup) getParent()).removeView(this);
    }

    public void removeFromParentAndUnmount() {
        ReactViewHacks.ensureUnmountOnDetachedFromWindow(this);
        ((ViewGroup) getParent()).removeView(this);
    }
}
