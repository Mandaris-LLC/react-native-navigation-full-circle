package com.reactnativenavigation.bridge;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule.RCTDeviceEventEmitter;

public class NavigationReactEventEmitter {

    private static final String KEY_EVENT_ID = "id";
    private static final String KEY_EVENT_TYPE = "type";
    private static final String KEY_NAVIGATOR_EVENT_ID = "navigatorEventID";
    private static final String EVENT_TYPE = "NavBarButtonPress";
    private RCTDeviceEventEmitter eventEmitter;

    public NavigationReactEventEmitter(ReactContext reactContext) {
        this.eventEmitter = reactContext.getJSModule(RCTDeviceEventEmitter.class);
    }

    public void sendEvent(String eventId, String navigatorEventId) {
        WritableMap params = Arguments.createMap();
        params.putString(KEY_EVENT_TYPE, EVENT_TYPE);
        params.putString(KEY_EVENT_ID, eventId);
        params.putString(KEY_NAVIGATOR_EVENT_ID, navigatorEventId);
        eventEmitter.emit(navigatorEventId, params);
    }
}
