package com.reactnativenavigation;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.facebook.react.ReactApplication;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
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

		registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
			@Override
			public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
				getConfig().activityLifecycleDelegate.onActivityCreated(activity);
			}

			@Override
			public void onActivityStarted(Activity activity) {

			}

			@Override
			public void onActivityResumed(Activity activity) {
				getConfig().activityLifecycleDelegate.onActivityResumed(activity, (DefaultHardwareBackBtnHandler) activity);
			}

			@Override
			public void onActivityPaused(Activity activity) {
				getConfig().activityLifecycleDelegate.onActivityPaused(activity);
			}

			@Override
			public void onActivityStopped(Activity activity) {

			}

			@Override
			public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

			}

			@Override
			public void onActivityDestroyed(Activity activity) {
				getConfig().activityLifecycleDelegate.onActivityDestroyed(activity);
			}
		});
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
