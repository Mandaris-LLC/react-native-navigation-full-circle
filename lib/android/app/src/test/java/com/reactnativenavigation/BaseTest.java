package com.reactnativenavigation;

import android.app.Activity;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 25, constants = BuildConfig.class, manifest = "/../../../../../src/test/AndroidManifest.xml")
public abstract class BaseTest {
	@Before
	public void beforeEach() {
		//
	}

	@After
	public void afterEach() {
		//
	}

	public Activity newActivity() {
		return Robolectric.setupActivity(Activity.class);
	}
}
