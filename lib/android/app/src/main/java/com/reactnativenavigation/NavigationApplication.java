package com.reactnativenavigation;

import android.app.Application;

import com.facebook.react.ReactApplication;
import com.facebook.react.ReactNativeHost;
import com.reactnativenavigation.controllers.Store;
import com.reactnativenavigation.react.DevPermissionRequest;
import com.reactnativenavigation.react.NavigationReactInitializer;
import com.reactnativenavigation.react.NavigationReactNativeHost;

public abstract class NavigationApplication extends Application implements ReactApplication {

	private Store store;
	private NavigationReactNativeHost reactNativeHost;
	private NavigationReactInitializer initializer;

	@Override
	public void onCreate() {
		super.onCreate();
		store = new Store();
		reactNativeHost = new NavigationReactNativeHost(this, isDebug(), store);
		initializer = new NavigationReactInitializer(reactNativeHost.getReactInstanceManager(), new DevPermissionRequest(isDebug()));
	}

	@Override
	public ReactNativeHost getReactNativeHost() {
		return reactNativeHost;
	}

	public abstract boolean isDebug();

	NavigationReactInitializer getInitializer() {
		return initializer;
	}
}
