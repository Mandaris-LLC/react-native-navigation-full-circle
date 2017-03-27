package com.reactnativenavigation.react;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.facebook.react.ReactRootView;
import com.reactnativenavigation.BaseTest;

import org.assertj.core.api.Condition;
import org.junit.Test;
import org.robolectric.Robolectric;

import java.lang.reflect.Method;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class NavigationReactRootViewCreatorTest extends BaseTest {
	@Test
	public void createsReactRootView() throws Exception {
		NavigationReactRootViewCreator uut = new NavigationReactRootViewCreator();
		View result = uut.create(Robolectric.buildActivity(Activity.class).get(), "the container id", "the container name");
		assertThat(result).isNotNull().isInstanceOf(ReactRootView.class);
	}

	@Test
	public void startReactContainer() throws Exception {
		NavigationReactRootViewCreator uut = new NavigationReactRootViewCreator();
		ReactRootView result = (ReactRootView) uut.create(Robolectric.buildActivity(Activity.class).get(), "the container id", "the container name");
		Method getJSModuleName = ReactRootView.class.getDeclaredMethod("getJSModuleName");
		Method getLaunchOptions = ReactRootView.class.getDeclaredMethod("getLaunchOptions");
		getJSModuleName.setAccessible(true);
		getLaunchOptions.setAccessible(true);
		assertThat(getJSModuleName.invoke(result)).isEqualTo("the container name");
		assertThat(getLaunchOptions.invoke(result)).isNotNull().isInstanceOf(Bundle.class).has(new Condition<Object>() {
			@Override
			public boolean matches(final Object value) {
				return ((Bundle) value).getString("id").equals("the container id");
			}
		});
	}
}
