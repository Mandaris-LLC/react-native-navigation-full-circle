package com.reactnativenavigation;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.facebook.react.ReactApplication;
import com.facebook.react.ReactNativeHost;
import com.reactnativenavigation.react.NavigationReactInitializer;
import com.reactnativenavigation.react.NavigationReactNativeHost;

public abstract class NavigationApplication extends Application implements ReactApplication, Application.ActivityLifecycleCallbacks {

	private NavigationReactNativeHost reactNativeHost;
	private NavigationReactInitializer initializer;

	@Override
	public void onCreate() {
		super.onCreate();
		reactNativeHost = new NavigationReactNativeHost(this, isDebug());
		initializer = new NavigationReactInitializer(reactNativeHost.getReactInstanceManager(), isDebug());
		registerActivityLifecycleCallbacks(this);
	}

	@Override
	public ReactNativeHost getReactNativeHost() {
		return reactNativeHost;
	}

	public abstract boolean isDebug();

	@Override
	public void onActivityCreated(final Activity activity, final Bundle savedInstanceState) {
		if (activity instanceof NavigationActivity) {
			initializer.onActivityCreated((NavigationActivity) activity);
		}
	}

	@Override
	public void onActivityStarted(final Activity activity) {
	}

	@Override
	public void onActivityResumed(final Activity activity) {
		if (activity instanceof NavigationActivity) {
			initializer.onActivityResumed((NavigationActivity) activity);
		}
	}

	@Override
	public void onActivityPaused(final Activity activity) {
		if (activity instanceof NavigationActivity) {
			initializer.onActivityPaused((NavigationActivity) activity);
		}
	}

	@Override
	public void onActivityStopped(final Activity activity) {
	}

	@Override
	public void onActivitySaveInstanceState(final Activity activity, final Bundle outState) {
	}

	@Override
	public void onActivityDestroyed(final Activity activity) {
		if (activity instanceof NavigationActivity) {
			initializer.onActivityDestroyed((NavigationActivity) activity);
		}
	}
}
