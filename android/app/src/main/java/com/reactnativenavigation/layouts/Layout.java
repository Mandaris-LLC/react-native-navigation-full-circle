package com.reactnativenavigation.layouts;

import com.reactnativenavigation.params.ScreenParams;

public interface Layout {
    boolean onBackPressed();

    void onDestroy();

    void removeAllReactViews();

    void push(ScreenParams params);

    void pop(ScreenParams params);

    void popToRoot(ScreenParams params);
}
