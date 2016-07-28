package com.reactnativenavigation.params.parsers;

import android.os.Bundle;

import com.reactnativenavigation.params.ScreenParams;
import com.reactnativenavigation.react.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class ScreenParamsParser extends Parser {
    private static final String KEY_TITLE = "title";
    private static final String KEY_SCREEN_ID = "screenId";
    private static final String KEY_SCREEN_INSTANCE_ID = "screenInstanceID";
    private static final String KEY_NAVIGATOR_EVENT_ID = "navigatorEventID";
    private static final String KEY_PROPS = "passProps";
    private static final String KEY_NAVIGATION_PARAMS = "navigationParams";
    private static final String KEY_RIGHT_BUTTONS = "rightButtons";
    private static final String KEY_LEFT_BUTTON = "leftButton";
    private static final String STYLE_PARAMS = "styleParams";
    public static final String TOP_TABS = "topTabs";

    @SuppressWarnings("ConstantConditions")
    public static ScreenParams parse(Bundle params) {
        ScreenParams result = new ScreenParams();
        result.screenId = params.getString(KEY_SCREEN_ID);
        result.screenInstanceId = params.getString(KEY_SCREEN_INSTANCE_ID);
        result.passProps = params.getBundle(KEY_PROPS);
        assertKeyExists(params, KEY_NAVIGATION_PARAMS);
        result.navigationParams = params.getBundle(KEY_NAVIGATION_PARAMS);
        result.navigatorEventId = result.navigationParams.getString(KEY_NAVIGATOR_EVENT_ID);
        result.screenInstanceId = result.navigationParams.getString(KEY_SCREEN_INSTANCE_ID);
        if (hasKey(params, KEY_RIGHT_BUTTONS)) {
            result.rightButtons = new TitleBarButtonParamsParser().parseButtons(params.getBundle(KEY_RIGHT_BUTTONS));
        }
        if (hasKey(params, KEY_LEFT_BUTTON)) {
            result.leftButton = new TitleBarLeftButtonParamsParser().parseSingleButton(params.getBundle(KEY_LEFT_BUTTON));
        }
        result.title = params.getString(KEY_TITLE);
        result.styleParams = ScreenStyleParamsParser.parse(params.getBundle(STYLE_PARAMS));
        if (hasKey(params, TOP_TABS)) {
            result.topTabParams = TopTabParamsParser.parse(params.getBundle(TOP_TABS));
        }
        if (hasKey(params, "label")) {
            result.tabLabel = params.getString("label");
        }
        if (hasKey(params, "icon")) {
            result.tabIcon = ImageLoader.loadImage(params.getString("icon"));
        }
        return result;
    }

    public static List<ScreenParams> parseTabs(Bundle params) {
        List<ScreenParams> result = new ArrayList<>();
        for (String key : params.keySet()) {
            result.add(ScreenParamsParser.parse(params.getBundle(key)));
        }
        return result;
    }
}
