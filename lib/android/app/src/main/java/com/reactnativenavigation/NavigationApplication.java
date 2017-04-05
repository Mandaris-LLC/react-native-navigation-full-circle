package com.reactnativenavigation;

import android.app.Application;

import com.facebook.react.ReactApplication;
import com.facebook.react.ReactNativeHost;
import com.reactnativenavigation.controllers.ActivityLifecycleDelegate;
import com.reactnativenavigation.react.NavigationReactNativeHost;

public abstract class NavigationApplication extends Application implements ReactApplication {

	private NavigationReactNativeHost reactNativeHost;
	ActivityLifecycleDelegate activityLifecycle;

	@Override
	public void onCreate() {
		super.onCreate();
		reactNativeHost = new NavigationReactNativeHost(this, isDebug());
		activityLifecycle = new ActivityLifecycleDelegate(reactNativeHost.getReactInstanceManager(), isDebug());
	}

	@Override
	public ReactNativeHost getReactNativeHost() {
		return reactNativeHost;
	}

	public abstract boolean isDebug();
}
