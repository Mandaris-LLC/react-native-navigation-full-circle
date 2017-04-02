package com.reactnativenavigation;

import com.reactnativenavigation.controllers.ActivityLifecycleDelegate;
import com.reactnativenavigation.controllers.CommandsHandler;
import com.reactnativenavigation.mocks.TestDevPermissionRequest;
import com.reactnativenavigation.mocks.TestReactNativeHost;
import com.reactnativenavigation.react.ReactRootViewCreatorImpl;

public class TestApplication extends NavigationApplication {

	@Override
	public boolean isDebug() {
		return true;
	}

	@Override
	protected void init() {
		reactNativeHost = new TestReactNativeHost(this, isDebug(), new CommandsHandler(new ReactRootViewCreatorImpl()));
		activityLifecycleDelegate = new ActivityLifecycleDelegate(reactNativeHost.getReactInstanceManager(), new TestDevPermissionRequest());
	}
}
