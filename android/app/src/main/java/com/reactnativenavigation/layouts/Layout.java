package com.reactnativenavigation.layouts;

import com.reactnativenavigation.core.objects.Screen;

public interface Layout {
    boolean onBackPressed();

    void onDestroy();

    void removeAllReactViews();

    void push(Screen screen);

    Screen pop();

    int getScreenCount();
}
