package com.reactnativenavigation;

import com.facebook.react.uimanager.DisplayMetricsHolder;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 25, constants = BuildConfig.class, manifest = "/../../../../../src/test/AndroidManifest.xml")
public abstract class BaseTest {

	@Before
	public void beforeEach() {
		DisplayMetricsHolder.initDisplayMetrics(RuntimeEnvironment.application);
	}
}
