package com.reactnativenavigation.bridge.parsers;

import android.os.Bundle;

import com.reactnativenavigation.controllers.ScreenParams;

public class ScreenParamsParser {
    private static final String KEY_TITLE = "title";
    private static final String KEY_SCREEN_ID = "screenId";
    private static final String KEY_SCREEN_INSTANCE_ID = "screenInstanceID";
    private static final String KEY_PROPS = "passProps";
    private static final String KEY_LABEL = "tabLabel";
    private static final String KEY_ICON = "tabIcon";
    private static final String KEY_NAVIGATOR_BUTTONS = "navigatorButtons";
    private static final String KEY_RIGHT_BUTTONS = "rightButtons";
    private static final String KEY_TOOL_BAR_STYLE = "navigatorStyle";
    private static final String KEY_STATUS_BAR_COLOR = "statusBarColor";
    private static final String KEY_TOOL_BAR_COLOR = "navBarBackgroundColor";
    private static final String KEY_TOOL_BAR_HIDDEN = "navBarHidden";
    private static final String KEY_NAVIGATION_BAR_COLOR = "navigationBarColor";
    private static final String KEY_NAV_BAR_BUTTON_COLOR = "navBarButtonColor";
    private static final String KEY_NAV_BAR_TEXT_COLOR = "navBarTextColor";
    private static final String KEY_BACK_BUTTON_HIDDEN = "backButtonHidden";
    private static final String KEY_TAB_NORMAL_TEXT_COLOR = "tabNormalTextColor";
    private static final String KEY_TAB_SELECTED_TEXT_COLOR = "tabSelectedTextColor";
    private static final String KEY_TAB_INDICATOR_COLOR = "tabIndicatorColor";
    private static final String KEY_BOTTOM_TABS_HIDDEN = "tabBarHidden";
    private static final String KEY_BOTTOM_TABS_HIDDEN_ON_SCROLL = "bottomTabsHiddenOnScroll";

    public ScreenParams parse(Bundle params) {
        ScreenParams result = new ScreenParams();
        result.screenId = params.getString(KEY_SCREEN_ID);
        result.screenInstanceId = params.getString(KEY_SCREEN_INSTANCE_ID);
        result.passProps = params.getBundle(KEY_PROPS);
        result.buttons = null;
        result.title = params.getString(KEY_TITLE);
        result.styleParams = null;
        return result;
    }

}
