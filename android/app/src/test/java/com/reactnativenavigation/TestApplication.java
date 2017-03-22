package com.reactnativenavigation;

import com.reactnativenavigation.controllers.ActivityLifecycleDelegate;
import com.reactnativenavigation.mocks.TestReactNativeHost;

public class TestApplication extends NavigationApplication {

	@Override
	public boolean isDebug() {
		return true;
	}

	@Override
	protected Config createConfig() {
		TestReactNativeHost host = new TestReactNativeHost(this, isDebug());
		ActivityLifecycleDelegate delegate = new ActivityLifecycleDelegate(host.getReactInstanceManager());
		return new Config(host, delegate);
	}
}
