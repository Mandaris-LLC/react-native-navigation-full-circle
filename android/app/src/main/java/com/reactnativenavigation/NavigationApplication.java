package com.reactnativenavigation;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.facebook.react.ReactApplication;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
import com.reactnativenavigation.controllers.ActivityLifecycleDelegate;
import com.reactnativenavigation.react.NavigationReactNativeHost;

public abstract class NavigationApplication extends Application implements ReactApplication {
	public static NavigationApplication instance;

	public static class Config {
		public final ReactNativeHost reactNativeHost;
		public final ActivityLifecycleDelegate activityLifecycleDelegate;

		public Config(ReactNativeHost reactNativeHost, ActivityLifecycleDelegate activityLifecycleDelegate) {
			this.reactNativeHost = reactNativeHost;
			this.activityLifecycleDelegate = activityLifecycleDelegate;
		}
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
				config().activityLifecycleDelegate.onActivityCreated(activity);
			}

			@Override
			public void onActivityStarted(Activity activity) {

			}

			@Override
			public void onActivityResumed(Activity activity) {
				config().activityLifecycleDelegate.onActivityResumed(activity, (DefaultHardwareBackBtnHandler) activity);
			}

			@Override
			public void onActivityPaused(Activity activity) {
				config().activityLifecycleDelegate.onActivityPaused(activity);
			}

			@Override
			public void onActivityStopped(Activity activity) {

			}

			@Override
			public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

			}

			@Override
			public void onActivityDestroyed(Activity activity) {
				config().activityLifecycleDelegate.onActivityDestroyed(activity);
			}
		});
	}

	@Override
	public ReactNativeHost getReactNativeHost() {
		return config().reactNativeHost;
	}

	public abstract boolean isDebug();

	public Config config() {
		return config;
	}

	/**
	 * override this to inject your own configuration
	 *
	 * @return the Config
	 */
	protected Config createConfig() {
		ReactNativeHost reactNativeHost = new NavigationReactNativeHost(this, isDebug());
		ActivityLifecycleDelegate activityLifecycleDelegate = new ActivityLifecycleDelegate(reactNativeHost.getReactInstanceManager());
		return new Config(reactNativeHost, activityLifecycleDelegate);
	}
}
