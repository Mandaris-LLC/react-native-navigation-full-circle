package com.reactnativenavigation.params.parsers;

import android.os.Bundle;

import com.reactnativenavigation.params.ScreenStyleParams;

public class ScreenStyleParamsParser {
    public static ScreenStyleParams parse(Bundle params) {
        ScreenStyleParams result = new ScreenStyleParams();
        if (params == null) {
            return result;
        }

        result.statusBarColor = new ScreenStyleParams.Color(ColorParser.parse(params.getString("statusBarColor")));
        result.topBarColor = new ScreenStyleParams.Color(ColorParser.parse(params.getString("topBarColor")));
        result.navigationBarColor = new ScreenStyleParams.Color(ColorParser.parse(params.getString("navigationBarColor")));
        result.titleBarHidden = params.getBoolean("titleBarHidden");
        result.titleBarTitleColor =
                new ScreenStyleParams.Color(ColorParser.parse(params.getString("titleBarTitleColor")));
        result.backButtonHidden = params.getBoolean("backButtonHidden");
        result.topTabsHidden = params.getBoolean("topTabsHidden");
        result.bottomTabsHidden = params.getBoolean("bottomTabsHidden");
        result.bottomTabsHiddenOnScroll = params.getBoolean("bottomTabsHiddenOnScroll");
        result.bottomTabsColor = new ScreenStyleParams.Color(ColorParser.parse(params.getString("bottomTabsColor")));
        result.bottomTabsButtonColor =
                new ScreenStyleParams.Color(ColorParser.parse(params.getString("bottomTabsButtonColor")));
        result.selectedBottomTabsButtonColor =
                new ScreenStyleParams.Color(ColorParser.parse(params.getString("selectedBottomTabsButtonColor")));
        result.drawUnderTopBar = params.getBoolean("drawUnderTopBar", false);
        return result;
    }
}
