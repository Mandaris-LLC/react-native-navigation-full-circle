package com.reactnativenavigation;

import android.app.Application;

import com.facebook.react.ReactApplication;
import com.facebook.react.ReactNativeHost;
import com.reactnativenavigation.controllers.ActivityLifecycleDelegate;
import com.reactnativenavigation.controllers.Store;
import com.reactnativenavigation.react.NavigationReactNativeHost;

public abstract class NavigationApplication extends Application implements ReactApplication {

	private Store store;
	private NavigationReactNativeHost reactNativeHost;
	ActivityLifecycleDelegate activityLifecycle;

	@Override
	public void onCreate() {
		super.onCreate();
		store = new Store();
		reactNativeHost = new NavigationReactNativeHost(this, isDebug(), store);
		activityLifecycle = new ActivityLifecycleDelegate(reactNativeHost.getReactInstanceManager(), isDebug());
	}

	@Override
	public ReactNativeHost getReactNativeHost() {
		return reactNativeHost;
	}

	public abstract boolean isDebug();
}
