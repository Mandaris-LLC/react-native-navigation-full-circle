package com.reactnativenavigation.layouts;

import com.reactnativenavigation.core.objects._Screen;

public interface Layout {
    boolean onBackPressed();

    void onDestroy();

    void removeAllReactViews();

    void push(_Screen screen);

    _Screen pop();

    int getScreenCount();
}
