package com.reactnativenavigation.core.objects;

import com.facebook.react.bridge.ReadableMap;

import java.io.Serializable;

public class Drawer extends JsonObject implements Serializable {
    private static final long serialVersionUID = 982836768712398756L;

    private static final String KEY_LEFT = "left";
    private static final String KEY_RIGHT = "right";
    private static final String KEY_DISABLE_OPEN_GESTURE = "disableOpenGesture";

    public final _Screen left;
    public final _Screen right;
    public final boolean disableOpenGesture;

    public Drawer(ReadableMap params) {
        left = params.hasKey(KEY_LEFT) ? new _Screen(params.getMap(KEY_LEFT)) : null;
        right = params.hasKey(KEY_RIGHT) ? new _Screen(params.getMap(KEY_RIGHT)) : null;
        disableOpenGesture = getBoolean(params, KEY_DISABLE_OPEN_GESTURE);
    }
}
