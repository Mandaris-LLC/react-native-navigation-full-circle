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



    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }
}
