package com.reactnativenavigation;

import android.app.Application;

import com.facebook.react.ReactApplication;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactNativeHost;
import com.reactnativenavigation.controllers.ActivityLifecycleDelegate;
import com.reactnativenavigation.controllers.CommandsHandler;
import com.reactnativenavigation.react.DevPermissionRequestImpl;
import com.reactnativenavigation.react.NavigationReactNativeHost;
import com.reactnativenavigation.react.ReactRootViewCreatorImpl;

public abstract class NavigationApplication extends Application implements ReactApplication {

	NavigationReactNativeHost reactNativeHost;
	ActivityLifecycleDelegate activityLifecycleDelegate;

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

	void init() {
		ReactRootViewCreatorImpl reactRootViewCreator = new ReactRootViewCreatorImpl();
		CommandsHandler commandsHandler = new CommandsHandler(reactRootViewCreator);
		reactNativeHost = new NavigationReactNativeHost(this, isDebug(), commandsHandler);

		ReactInstanceManager reactInstanceManager = reactNativeHost.getReactInstanceManager();
		DevPermissionRequestImpl devPermissionRequest = new DevPermissionRequestImpl(this, isDebug());
		activityLifecycleDelegate = new ActivityLifecycleDelegate(reactInstanceManager, devPermissionRequest);
	}
}
