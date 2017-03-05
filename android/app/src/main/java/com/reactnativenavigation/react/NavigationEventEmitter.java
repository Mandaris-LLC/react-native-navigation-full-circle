package com.reactnativenavigation.react;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;

import static com.facebook.react.modules.core.DeviceEventManagerModule.RCTDeviceEventEmitter;

public class NavigationEventEmitter {

	public static NavigationEventEmitter emit(ReactContext context) {
		return new NavigationEventEmitter(context);
	}

	private final RCTDeviceEventEmitter emitter;

	private NavigationEventEmitter(ReactContext reactContext) {
		this.emitter = reactContext.getJSModule(RCTDeviceEventEmitter.class);
	}

	public void appLaunched() {
		emit("RNN.appLaunched");
	}

	private void emit(String eventName) {
		WritableMap data = Arguments.createMap();
		emitter.emit(eventName, data);
	}
}
