package com.reactnativenavigation.views;

import android.content.Context;
import android.os.Bundle;
import android.view.ViewGroup;

import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactRootView;
import com.reactnativenavigation.react.ReactViewHacks;

public class ContentView extends ReactRootView implements ScrollDirectionListener.OnChanged {

    private final ScrollViewAttacher scrollViewAttacher;

    public ContentView(Context context, ReactInstanceManager reactInstanceManager, String moduleName, Bundle passProps) {
        super(context);
        startReactApplication(reactInstanceManager, moduleName, passProps);
        scrollViewAttacher = new ScrollViewAttacher(this, this);
    }

    @Override
    public void onScrollChanged(ScrollDirectionListener.Direction direction) {

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
