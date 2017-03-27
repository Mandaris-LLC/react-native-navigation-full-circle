package com.reactnativenavigation.react;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;

import static com.facebook.react.modules.core.DeviceEventManagerModule.RCTDeviceEventEmitter;

public class NavigationEventEmitter {
	private static final String onAppLaunched = "RNN.appLaunched";
	private static final String containerStart = "RNN.containerStart";
	private static final String containerStop = "RNN.containerStop";

	public static NavigationEventEmitter emit(ReactContext context) {
		return new NavigationEventEmitter(context);
	}

	private final RCTDeviceEventEmitter emitter;

	private NavigationEventEmitter(ReactContext reactContext) {
		this.emitter = reactContext.getJSModule(RCTDeviceEventEmitter.class);
	}

	public void appLaunched() {
		emit(onAppLaunched);
	}

	private void emit(String eventName) {
		emit(eventName, Arguments.createMap());
	}

	private void emit(String eventName, WritableMap data) {
		emitter.emit(eventName, data);
	}

	private void emit(String eventName, String param) {
		emitter.emit(eventName, param);
	}

	public void containerStop(String id) {
		WritableMap data = Arguments.createMap();
		data.putString("id", id);
//        emit(containerStop, data);
		emit(containerStop, id);
	}

	public void containerStart(String id) {
		WritableMap data = Arguments.createMap();
		data.putString("id", id);
//        emit(containerStart, data);
		emit(containerStart, id);
	}
}
