package com.reactnativenavigation;

import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 25, constants = BuildConfig.class, manifest = "/../../../../../src/test/AndroidManifest.xml")
public abstract class BaseTest {
}
