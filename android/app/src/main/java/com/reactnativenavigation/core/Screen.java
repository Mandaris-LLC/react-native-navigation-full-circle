package com.reactnativenavigation.core;

import com.facebook.react.bridge.ReadableMap;

import java.io.Serializable;

/**
 * Created by guyc on 02/04/16.
 */
public class Screen implements Serializable {
    private static final long serialVersionUID = -1033475305421107791L;
    private static final String KEY_TITLE = "title";
    private static final String KEY_SCREEN = "screen";
    public static final String KEY_SCREEN_INSTANCE_ID = "screenInstanceID";
    public static final String KEY_STACK_ID = "stackID";
    public static final String KEY_NAVIGATOR_ID = "navigatorID";
    public static final String KEY_NAVIGATOR_EVENT_ID = "navigatorEventID";

    public String title;
    public String screenId;
    public String screenInstanceId;
    public String stackId;
    public String navigatorId;
    public String navigatorEventId;

    public Screen(ReadableMap screen) {
        title = getString(screen, KEY_TITLE);
        screenId = getString(screen, KEY_SCREEN);
        screenInstanceId = getString(screen, KEY_SCREEN_INSTANCE_ID);
        stackId = getString(screen, KEY_STACK_ID);
        navigatorId = getString(screen, KEY_NAVIGATOR_ID);
        navigatorEventId = getString(screen, KEY_NAVIGATOR_EVENT_ID);
    }

    private String getString(ReadableMap map, String key) {
        return map.hasKey(key) ? map.getString(key) : null;
    }
}
