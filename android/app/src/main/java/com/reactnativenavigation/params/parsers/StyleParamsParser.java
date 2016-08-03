package com.reactnativenavigation.params.parsers;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.reactnativenavigation.params.AppStyle;
import com.reactnativenavigation.params.StyleParams;

public class StyleParamsParser {
    private Bundle params;

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
        result.titleBarButtonColor = getColor("titleBarButtonColor", getTitleBarButtonColor());
        result.backButtonHidden = getBoolean("backButtonHidden", isDefaultBackButtonHidden());
        result.topTabsHidden = getBoolean("topTabsHidden", isDefaultTopTabsHidden());

        // TODO: Uncomment once we support drawBelowTopBar again
        //result.drawScreenBelowTopBar = params.getBoolean("drawBelowTopBar", isDefaultScreenBelowTopBar());
        result.drawScreenBelowTopBar = true;

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
        return AppStyle.appStyle == null ? new StyleParams.Color() : AppStyle.appStyle.navigationBarColor;
    }

    @Nullable
    private StyleParams.Color getDefaultSelectedBottomTabsButtonColor() {
        return AppStyle.appStyle == null ? new StyleParams.Color() : AppStyle.appStyle.selectedBottomTabsButtonColor;
    }

    @Nullable
    private StyleParams.Color getDefaultBottomTabsButtonColor() {
        return AppStyle.appStyle == null ? new StyleParams.Color() : AppStyle.appStyle.bottomTabsButtonColor;
    }

    @Nullable
    private StyleParams.Color getDefaultBottomTabsColor() {
        return AppStyle.appStyle == null ? new StyleParams.Color() : AppStyle.appStyle.bottomTabsColor;
    }

    private boolean isDefaultBottomTabsHiddenOnScroll() {
        return AppStyle.appStyle != null && AppStyle.appStyle.bottomTabsHiddenOnScroll;
    }

    private boolean isDefaultBottomTabsHidden() {
        return AppStyle.appStyle != null && AppStyle.appStyle.bottomTabsHidden;
    }

    private boolean isDefaultScreenBelowTopBar() {
        return AppStyle.appStyle == null || AppStyle.appStyle.drawScreenBelowTopBar;
    }

    private boolean isDefaultTopTabsHidden() {
        return AppStyle.appStyle != null && AppStyle.appStyle.topTabsHidden;
    }

    private boolean isDefaultBackButtonHidden() {
        return AppStyle.appStyle != null && AppStyle.appStyle.backButtonHidden;
    }

    @Nullable
    private StyleParams.Color getDefaultTitleBarColor() {
        return AppStyle.appStyle == null ? new StyleParams.Color() : AppStyle.appStyle.titleBarTitleColor;
    }

    @Nullable
    private StyleParams.Color getTitleBarButtonColor() {
        return AppStyle.appStyle == null ? new StyleParams.Color() : AppStyle.appStyle.titleBarButtonColor;
    }

    private boolean isDefaultTopBarHidden() {
        return AppStyle.appStyle != null && AppStyle.appStyle.titleBarHidden;
    }

    @Nullable
    private StyleParams.Color getDefaultTopBarColor() {
        return AppStyle.appStyle == null ? new StyleParams.Color() : AppStyle.appStyle.topBarColor;
    }

    @Nullable
    private StyleParams.Color getDefaultStatusBarColor() {
        return AppStyle.appStyle == null ? new StyleParams.Color() : AppStyle.appStyle.statusBarColor;
    }

    private boolean getBoolean(String titleBarHidden, boolean defaultValue) {
        return params.containsKey(titleBarHidden) ? params.getBoolean(titleBarHidden) : defaultValue;
    }

    @NonNull
    private StyleParams.Color getColor(String key, StyleParams.Color defaultColor) {
        StyleParams.Color color = StyleParams.Color.parse(params.getString(key));
        if (color.hasColor()) {
            return color;
        } else {
            return defaultColor != null && defaultColor.hasColor() ? defaultColor : color;
        }
    }
}
