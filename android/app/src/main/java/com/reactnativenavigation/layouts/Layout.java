package com.reactnativenavigation.layouts;

public interface Layout {
    boolean onBackPressed();

    void onDestroy();

    void removeAllReactViews();
}
