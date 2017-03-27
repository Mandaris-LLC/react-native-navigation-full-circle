package com.reactnativenavigation.react;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.reactnativenavigation.NavigationActivity;
import com.reactnativenavigation.NavigationApplication;
import com.reactnativenavigation.utils.UiThread;

public class NavigationModule extends ReactContextBaseJavaModule {
	private static final String NAME = "RNNBridgeModule";

	public NavigationModule(ReactApplicationContext reactContext) {
		super(reactContext);
	}

	@Override
	public String getName() {
		return NAME;
	}

	public NavigationActivity activity() {
		return (NavigationActivity) getCurrentActivity();
	}

	@ReactMethod
	public void setRoot(final ReadableMap layoutTree) {
		handle(new Runnable() {
			@Override
			public void run() {
				NavigationApplication.instance.getConfig().commandsHandler.setRoot(activity(), layoutTree);
			}
		});
	}

	@ReactMethod
	public void push(final String onContainerId, final ReadableMap layout) {
		handle(new Runnable() {
			@Override
			public void run() {
				NavigationApplication.instance.getConfig().commandsHandler.push(activity(), onContainerId, layout);
			}
		});
	}

	@ReactMethod
	public void pop(final String onContainerId) {
		handle(new Runnable() {
			@Override
			public void run() {
				NavigationApplication.instance.getConfig().commandsHandler.pop(activity(), onContainerId);
			}
		});
	}

	private void handle(Runnable task) {
		if (activity() == null) return;
		UiThread.post(task);
	}
}
