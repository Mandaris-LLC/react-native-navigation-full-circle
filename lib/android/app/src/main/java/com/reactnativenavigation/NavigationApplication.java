package com.reactnativenavigation;

import android.app.Application;

import com.facebook.react.ReactApplication;
import com.facebook.react.ReactNativeHost;
import com.reactnativenavigation.controllers.ActivityLifecycleDelegate;
import com.reactnativenavigation.react.DevPermissionRequestImpl;
import com.reactnativenavigation.react.NavigationReactNativeHost;

public abstract class NavigationApplication extends Application implements ReactApplication {
	public static NavigationApplication instance;

	public static class Config {
		public ReactNativeHost reactNativeHost;
		public ActivityLifecycleDelegate activityLifecycleDelegate;
	}

	private Config config;

	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
		config = createConfig();
	}

	public final Config getConfig() {
		return config;
	}

	@Override
	public ReactNativeHost getReactNativeHost() {
		return getConfig().reactNativeHost;
	}

	public abstract boolean isDebug();

	/**
	 * override this to inject your own configuration
	 *
	 * @return the Config
	 */
	protected Config createConfig() {
		Config config = new Config();
		config.reactNativeHost = new NavigationReactNativeHost(this, isDebug());
		config.activityLifecycleDelegate = new ActivityLifecycleDelegate(config.reactNativeHost.getReactInstanceManager(), new DevPermissionRequestImpl());
		return config;
	}
}
