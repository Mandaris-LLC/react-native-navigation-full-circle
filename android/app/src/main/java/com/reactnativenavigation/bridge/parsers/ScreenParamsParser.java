package com.reactnativenavigation.bridge.parsers;

import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableNativeMap;
import com.reactnativenavigation.controllers.ScreenParams;

public class ScreenParamsParser implements ParamsParser<ScreenParams> {
    private static final String KEY_TITLE = "title";
    private static final String KEY_SCREEN = "screen";
    private static final String KEY_LABEL = "label";
    private static final String KEY_SCREEN_INSTANCE_ID = "screenInstanceID";
    private static final String KEY_NAVIGATOR_ID = "navigatorID";
    private static final String KEY_NAVIGATOR_EVENT_ID = "navigatorEventID";
    private static final String KEY_ICON = "icon";
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
    private static final String KEY_PROPS = "passProps";

    @Override
    public ScreenParams parse(ReadableMap params) {
        ScreenParams result = new ScreenParams();
        result.title = params.getString(KEY_TITLE);
        result.label = params.getString(KEY_LABEL);
        result.screenId = params.getString(KEY_SCREEN);
        result.screenInstanceId = params.getString(KEY_SCREEN_INSTANCE_ID);
        result.navigatorId = params.getString(KEY_NAVIGATOR_ID);
        result.navigatorEventId = params.getString(KEY_NAVIGATOR_EVENT_ID);
        result.icon = getString(KEY_ICON);
        if (screen.hasKey(KEY_PROPS)) {
            passedProps = ((ReadableNativeMap) screen.getMap(KEY_PROPS)).toHashMap();
        }
        result.buttons = getButtons(screen);
        result.backButtonHidden = getBoolean(screen, KEY_BACK_BUTTON_HIDDEN);
        return result;
    }

}
