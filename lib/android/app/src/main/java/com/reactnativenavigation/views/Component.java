package com.reactnativenavigation.views;

import android.view.View;

import com.reactnativenavigation.parse.Options;

public interface Component {
    void applyOptions(Options options);

    void sendOnNavigationButtonPressed(String id);

    TopBar getTopBar();

    View getContentView();

    void drawBehindTopBar();

    void drawBelowTopBar();
}
