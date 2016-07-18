package com.reactnativenavigation.views;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactRootView;

public class ContentView extends ReactRootView {

    public ContentView(Context context, ReactInstanceManager reactInstanceManager, String moduleName, Bundle passProps) {
        super(context);
        startReactApplication(reactInstanceManager, moduleName, passProps);
    }

    @Nullable
    public ScrollView findScrollView() {
        return findScrollView(this);
    }

    private ScrollView findScrollView(ViewGroup parent) {
        for (int i = 0; i < parent.getChildCount(); i++) {
            View child = parent.getChildAt(i);

            if (child instanceof ScrollView) {
                return (ScrollView) child;
            } else if (child instanceof ViewGroup) {
                return findScrollView((ViewGroup) child);
            }
        }

        return null;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }
}
