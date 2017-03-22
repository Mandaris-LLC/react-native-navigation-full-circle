package com.reactnativenavigation.mocks;

import com.reactnativenavigation.BuildConfig;
import com.reactnativenavigation.NavigationApplication;

public class TestApplication extends NavigationApplication {
	@Override
	public boolean isDebug() {
		return BuildConfig.DEBUG;
	}
}
