package com.reactnativenavigation.screens;

import android.support.v7.app.AppCompatActivity;

import com.reactnativenavigation.params.ScreenParams;
import com.reactnativenavigation.views.TitleBarBackButtonListener;

public class ScreenFactory {
    public static Screen create(AppCompatActivity activity,
                                ScreenParams screenParams,
                                TitleBarBackButtonListener titleBarBackButtonListener) {
        if (screenParams.isFragmentScreen()) {
            return new FragmentScreen(activity, screenParams, titleBarBackButtonListener);
        } else if (screenParams.hasTopTabs()) {
            return new TabbedScreen(activity, screenParams, titleBarBackButtonListener);
        } else {
            return new SingleScreen(activity, screenParams, titleBarBackButtonListener);
        }
    }
}
