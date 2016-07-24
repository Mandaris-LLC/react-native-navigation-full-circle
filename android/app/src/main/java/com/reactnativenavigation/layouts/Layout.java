package com.reactnativenavigation.layouts;

import com.reactnativenavigation.params.ScreenParams;

public interface Layout {
    boolean onBackPressed();

    void onDestroy();

    void removeAllReactViews();

    void push(ScreenParams params);
}
