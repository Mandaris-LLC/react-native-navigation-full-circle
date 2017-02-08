package com.reactnativenavigation.react;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

public class NavigationEventEmitter {

    private final DeviceEventManagerModule.RCTDeviceEventEmitter emitter;

    public NavigationEventEmitter(ReactContext reactContext) {
        this.emitter = reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class);
    }

    public void emitAppLaunched() {
        emit("RNN.appLaunched");
    }

    private void emit(String eventName) {
        WritableMap data = Arguments.createMap();
        emitter.emit(eventName, data);
    }
}
