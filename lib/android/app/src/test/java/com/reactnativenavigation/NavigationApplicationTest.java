package com.reactnativenavigation;

import com.facebook.react.ReactApplication;
import com.facebook.react.ReactNativeHost;
import com.reactnativenavigation.controllers.ActivityLifecycleDelegate;

import org.junit.Test;
import org.robolectric.RuntimeEnvironment;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class NavigationApplicationTest extends BaseTest {

	@Test
	public void reactApplication() throws Exception {
		assertThat(RuntimeEnvironment.application).isInstanceOf(ReactApplication.class);
		assertThat(((ReactApplication) RuntimeEnvironment.application).getReactNativeHost()).isNotNull();
	}

	@Test
	public void isDebug() throws Exception {
		assertThat(((NavigationApplication) RuntimeEnvironment.application).isDebug()).isTrue();
	}

	@Test
	public void singleGlobalInstance() throws Exception {
		assertThat(RuntimeEnvironment.application).isSameAs(NavigationApplication.instance);
	}

	@Test
	public void holdsConfiguration() throws Exception {
		assertThat(NavigationApplication.instance.getConfig()).isInstanceOf(NavigationApplication.Config.class);
	}

	@Test
	public void config() throws Exception {
		assertThat(NavigationApplication.instance.getConfig().reactNativeHost).isInstanceOf(ReactNativeHost.class);
		assertThat(NavigationApplication.instance.getConfig().activityLifecycleDelegate).isInstanceOf(ActivityLifecycleDelegate.class);
	}
}
