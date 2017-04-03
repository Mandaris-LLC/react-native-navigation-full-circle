package com.reactnativenavigation;

import android.app.Application;

import com.facebook.react.ReactApplication;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactNativeHost;
import com.reactnativenavigation.controllers.ActivityLifecycleDelegate;
import com.reactnativenavigation.controllers.CommandsHandler;
import com.reactnativenavigation.react.DevPermissionRequest;
import com.reactnativenavigation.react.NavigationReactNativeHost;

public abstract class NavigationApplication extends Application implements ReactApplication {

	//TODO un mock this
	NavigationReactNativeHost reactNativeHost;
	ActivityLifecycleDelegate activityLifecycle;

	@Override
	public void onCreate() {
		super.onCreate();
		init();
	}

	@Override
	public ReactNativeHost getReactNativeHost() {
		return reactNativeHost;
	}

	public abstract boolean isDebug();

	//TODO inline this
	void init() {
		CommandsHandler commandsHandler = new CommandsHandler(this);
		reactNativeHost = new NavigationReactNativeHost(this, isDebug(), commandsHandler);

		ReactInstanceManager reactInstanceManager = reactNativeHost.getReactInstanceManager();
		DevPermissionRequest devPermissionRequest = new DevPermissionRequest(this, isDebug());
		activityLifecycle = new ActivityLifecycleDelegate(reactInstanceManager, devPermissionRequest);
	}
}
