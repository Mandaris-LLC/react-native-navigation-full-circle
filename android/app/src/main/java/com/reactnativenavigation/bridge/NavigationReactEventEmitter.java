package com.reactnativenavigation.bridge;

import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule.RCTDeviceEventEmitter;
import com.reactnativenavigation.core.objects._Screen;

public class NavigationReactEventEmitter {

    private static final String KEY_EVENT_ID = "id";
    private static final String KEY_EVENT_TYPE = "type";
    private static final String EVENT_TYPE = "NavBarButtonPress";

    public void sendEvent(RCTDeviceEventEmitter eventEmitter, String eventName, String navigatorEventId, WritableMap params) {
        if (eventEmitter == null) {
            return;
        }

        params.putString(KEY_EVENT_TYPE, EVENT_TYPE);
        params.putString(KEY_EVENT_ID, eventName);
        params.putString(_Screen.KEY_NAVIGATOR_EVENT_ID, navigatorEventId);
        eventEmitter.emit(navigatorEventId, params);
    }
}
