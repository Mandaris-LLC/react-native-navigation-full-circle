package com.reactnativenavigation.core.objects;

import com.facebook.react.bridge.ReadableMap;

/**
 * Created by guyc on 08/04/16.
 */
public class JsonObject {

    protected String getString(ReadableMap map, String key) {
        return getString(map, key, null);
    }

    protected String getString(ReadableMap map, String key, String defaultValue) {
        return map.hasKey(key) ? map.getString(key) : defaultValue;
    }

    protected int getInt(ReadableMap map, String key) {
        return map.hasKey(key) ? map.getInt(key) : -1;
    }
}
