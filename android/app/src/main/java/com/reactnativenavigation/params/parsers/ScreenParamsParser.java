package com.reactnativenavigation.params.parsers;

import android.os.Bundle;

import com.reactnativenavigation.params.ScreenParams;

public class ScreenParamsParser {
    private static final String KEY_TITLE = "title";
    private static final String KEY_SCREEN_ID = "screenId";
    private static final String KEY_SCREEN_INSTANCE_ID = "screenInstanceID";
    private static final String KEY_PROPS = "passProps";
    private static final String KEY_BUTTONS = "titleBarButtons";
    private static final String STYLE_PARAMS = "styleParams";

    public ScreenParams parse(Bundle params) {
        ScreenParams result = new ScreenParams();
        result.screenId = params.getString(KEY_SCREEN_ID);
        result.screenInstanceId = params.getString(KEY_SCREEN_INSTANCE_ID);
        result.passProps = params.getBundle(KEY_PROPS);
        result.buttons = new TitleBarButtonParamsParser().parse(params.getBundle(KEY_BUTTONS));
        result.title = params.getString(KEY_TITLE);
        result.styleParams = new ScreenStyleParamsParser().parse(params.getBundle(STYLE_PARAMS));
        return result;
    }

}
