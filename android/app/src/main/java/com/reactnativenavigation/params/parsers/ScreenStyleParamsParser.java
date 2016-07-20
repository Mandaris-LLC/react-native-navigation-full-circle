package com.reactnativenavigation.params.parsers;

import android.os.Bundle;

import com.reactnativenavigation.params.ScreenStyleParams;

public class ScreenStyleParamsParser {
    public ScreenStyleParams parse(Bundle params) {
        ScreenStyleParams result = new ScreenStyleParams();
        result.statusBarColor = params.getInt("statusBarColor");
        result.topBarColor = params.getInt("topBarColor");
        result.navigationBarColor = params.getInt("navigationBarColor");
        result.titleBarHidden = params.getBoolean("titleBarHidden");
        result.backButtonHidden = params.getBoolean("backButtonHidden");
        result.topTabsHidden = params.getBoolean("topTabsHidden");
        result.bottomTabsHidden = params.getBoolean("bottomTabsHidden");
        result.bottomTabsHiddenOnScroll = params.getBoolean("bottomTabsHiddenOnScroll");
        return result;
    }
}
