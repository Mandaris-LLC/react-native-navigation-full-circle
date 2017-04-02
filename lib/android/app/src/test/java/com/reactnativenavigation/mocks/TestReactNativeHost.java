package com.reactnativenavigation.mocks;

import android.app.Application;

import com.facebook.react.ReactInstanceManager;
import com.reactnativenavigation.controllers.CommandsHandler;
import com.reactnativenavigation.react.NavigationReactNativeHost;

import static org.mockito.Mockito.mock;

public class TestReactNativeHost extends NavigationReactNativeHost {
	public TestReactNativeHost(Application application, boolean isDebug, CommandsHandler commandsHandler) {
		super(application, isDebug, commandsHandler);
	}

	@Override
	protected ReactInstanceManager createReactInstanceManager() {
		return mock(ReactInstanceManager.class);
	}
}
