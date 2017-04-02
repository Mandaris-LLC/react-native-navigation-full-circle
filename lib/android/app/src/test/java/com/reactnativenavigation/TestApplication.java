package com.reactnativenavigation;

import com.reactnativenavigation.controllers.ActivityLifecycleDelegate;
import com.reactnativenavigation.react.NavigationReactNativeHost;

import static org.mockito.Mockito.mock;

public class TestApplication extends NavigationApplication {

	@Override
	public boolean isDebug() {
		return true;
	}

	@Override
	protected void init() {
		reactNativeHost = mock(NavigationReactNativeHost.class);
		activityLifecycle = mock(ActivityLifecycleDelegate.class);
	}
}
