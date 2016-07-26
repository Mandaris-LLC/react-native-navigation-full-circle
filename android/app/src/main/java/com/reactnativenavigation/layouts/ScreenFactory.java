package com.reactnativenavigation.layouts;

import android.content.Context;

import com.reactnativenavigation.params.ScreenParams;

public class ScreenFactory {
    public static Screen create(Context context, ScreenParams screenParams) {
        if (screenParams.hasTopTabs()) {
            return new TabbedScreen(context, screenParams);
        } else {
            return new SingleScreen(context, screenParams);
        }
    }
}
