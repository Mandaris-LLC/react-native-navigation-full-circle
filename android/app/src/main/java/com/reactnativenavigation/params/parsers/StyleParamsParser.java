package com.reactnativenavigation.params.parsers;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.reactnativenavigation.params.StyleParams;

public class StyleParamsParser {
    private static StyleParams appStyle;
    private Bundle params;

    public static void setAppStyle(Bundle params) {
        StyleParamsParser.appStyle = new StyleParamsParser(params.getBundle("appStyle")).parse();
    }

    public StyleParamsParser(Bundle params) {
        this.params = params;
    }

    public StyleParams parse() {
        StyleParams result = new StyleParams();
        if (params == null) {
            return result;
        }

        result.statusBarColor = getColor("statusBarColor", getDefaultStatusBarColor());

        result.topBarColor = getColor("topBarColor", getDefaultTopBarColor());
        result.titleBarHidden = getBoolean("titleBarHidden", isDefaultTopBarHidden());
        result.titleBarTitleColor = getColor("titleBarTitleColor", getDefaultTitleBarColor());
        result.titleBarButtonColor = getColor("titleBarButtonColor", getDefaultTitleBarColor());
        result.backButtonHidden = getBoolean("backButtonHidden", isDefaultBackButtonHidden());
        result.topTabsHidden = getBoolean("topTabsHidden", isDefaultTopTabsHidden());

        result.drawScreenBelowTopBar = params.getBoolean("drawBelowTopBar", isDefaultScreenBelowTopBar());

        result.bottomTabsHidden = getBoolean("bottomTabsHidden", isDefaultBottomTabsHidden());
        result.bottomTabsHiddenOnScroll =
                getBoolean("bottomTabsHiddenOnScroll", isDefaultBottomTabsHiddenOnScroll());
        result.bottomTabsColor = getColor("bottomTabsColor", getDefaultBottomTabsColor());
        result.bottomTabsButtonColor = getColor("bottomTabsButtonColor", getDefaultBottomTabsButtonColor());
        result.selectedBottomTabsButtonColor =
                getColor("bottomTabsSelectedButtonColor", getDefaultSelectedBottomTabsButtonColor());

        result.navigationBarColor = getColor("navigationBarColor", getDefaultNavigationColor());

        return result;
    }

    @Nullable
    private StyleParams.Color getDefaultNavigationColor() {
        return appStyle == null ? new StyleParams.Color(-1) : appStyle.navigationBarColor;
    }

    @Nullable
    private StyleParams.Color getDefaultSelectedBottomTabsButtonColor() {
        return appStyle == null ? new StyleParams.Color(-1) : appStyle.selectedBottomTabsButtonColor;
    }

    @Nullable
    private StyleParams.Color getDefaultBottomTabsButtonColor() {
        return appStyle == null ? new StyleParams.Color(-1) : appStyle.bottomTabsButtonColor;
    }

    @Nullable
    private StyleParams.Color getDefaultBottomTabsColor() {
        return appStyle == null ? new StyleParams.Color(-1) : appStyle.bottomTabsColor;
    }

    private boolean isDefaultBottomTabsHiddenOnScroll() {
        return appStyle != null && appStyle.bottomTabsHiddenOnScroll;
    }

    private boolean isDefaultBottomTabsHidden() {
        return appStyle != null && appStyle.bottomTabsHidden;
    }

    private boolean isDefaultScreenBelowTopBar() {
        return appStyle == null || appStyle.drawScreenBelowTopBar;
    }

    private boolean isDefaultTopTabsHidden() {
        return appStyle != null && appStyle.topTabsHidden;
    }

    private boolean isDefaultBackButtonHidden() {
        return appStyle != null && appStyle.backButtonHidden;
    }

    @Nullable
    private StyleParams.Color getDefaultTitleBarColor() {
        return appStyle == null ? new StyleParams.Color(-1) : appStyle.titleBarTitleColor;
    }

    @Nullable
    private StyleParams.Color getTitleBarButtonColor() {
        return appStyle == null ? new StyleParams.Color(-1) : appStyle.titleBarButtonColor;
    }

    private boolean isDefaultTopBarHidden() {
        return appStyle != null && appStyle.titleBarHidden;
    }

    @Nullable
    private StyleParams.Color getDefaultTopBarColor() {
        return appStyle == null ? new StyleParams.Color(-1) : appStyle.topBarColor;
    }

    @Nullable
    private StyleParams.Color getDefaultStatusBarColor() {
        return appStyle == null ? new StyleParams.Color(-1) : appStyle.statusBarColor;
    }

    private boolean getBoolean(String titleBarHidden, boolean defaultValue) {
        return params.containsKey(titleBarHidden) ? params.getBoolean(titleBarHidden) : defaultValue;
    }

    @NonNull
    private StyleParams.Color getColor(String key, StyleParams.Color defaultColor) {
        StyleParams.Color color = new StyleParams.Color(ColorParser.parse(params.getString(key)));
        if (color.hasColor()) {
            return color;
        } else {
            return defaultColor != null && defaultColor.hasColor() ? defaultColor : color;
        }
    }
}
