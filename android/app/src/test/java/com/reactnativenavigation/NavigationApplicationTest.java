package com.reactnativenavigation;

import com.facebook.react.ReactApplication;

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
	public void singleInstance() throws Exception {
		assertThat(RuntimeEnvironment.application).isSameAs(NavigationApplication.instance);
	}
}
