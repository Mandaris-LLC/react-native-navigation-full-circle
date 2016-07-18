package com.reactnativenavigation.layouts;

import android.content.Context;

import com.reactnativenavigation.core.objects.Screen;

public class SomethingLayout extends BaseLayout {

    public SomethingLayout(Context context, Screen initialScreen) {
        super(context, initialScreen);
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public void onDestroy() {

    }
}
