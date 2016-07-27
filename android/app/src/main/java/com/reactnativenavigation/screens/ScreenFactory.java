package com.reactnativenavigation.screens;

import android.content.Context;

import com.reactnativenavigation.params.ScreenParams;
import com.reactnativenavigation.views.TitleBarBackButtonListener;

public class ScreenFactory {
    public static Screen create(Context context,
                                ScreenParams screenParams,
                                TitleBarBackButtonListener titleBarBackButtonListener) {
        if (screenParams.hasTopTabs()) {
            return new TabbedScreen(context, screenParams);
        } else {
            return new SingleScreen(context, screenParams, titleBarBackButtonListener);
        }
    }
}
