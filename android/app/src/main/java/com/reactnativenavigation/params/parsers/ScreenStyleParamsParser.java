package com.reactnativenavigation.params.parsers;

import android.os.Bundle;

import com.reactnativenavigation.params.ScreenStyleParams;

public class ScreenStyleParamsParser {
    public static ScreenStyleParams parse(Bundle params) {
        ScreenStyleParams result = new ScreenStyleParams();
        if (params == null) {
            return result;
        }

        result.statusBarColor = ColorParser.parse(params.getString("statusBarColor"));
        result.topBarColor = ColorParser.parse(params.getString("topBarColor"));
        result.navigationBarColor = ColorParser.parse(params.getString("navigationBarColor"));
        result.titleBarHidden = params.getBoolean("titleBarHidden");
        result.backButtonHidden = params.getBoolean("backButtonHidden");
        result.topTabsHidden = params.getBoolean("topTabsHidden");
        result.bottomTabsHidden = params.getBoolean("bottomTabsHidden");
        result.bottomTabsHiddenOnScroll = params.getBoolean("bottomTabsHiddenOnScroll");
        return result;
    }
}
