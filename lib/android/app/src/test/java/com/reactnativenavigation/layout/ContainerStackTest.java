package com.reactnativenavigation.layout;

import android.support.annotation.NonNull;

import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactNativeHost;
import com.reactnativenavigation.BaseTest;
import com.reactnativenavigation.NavigationActivity;
import com.reactnativenavigation.layout.containers.Container;
import com.reactnativenavigation.layout.containers.ContainerStack;

import org.junit.Test;
import org.robolectric.Robolectric;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ContainerStackTest extends BaseTest {
	@Test
	public void push() throws Exception {
		NavigationActivity context = Robolectric.setupActivity(NavigationActivity.class);
		ContainerStack uut = new ContainerStack(context);
		assertThat(uut.isEmpty()).isTrue();
		assertThat(uut.getChildCount()).isZero();

		Container container = createContainer(context);
		uut.push(container);
		assertThat(uut.isEmpty()).isFalse();

		assertThat(uut.getChildCount()).isEqualTo(1);

	}

	@NonNull
	private Container createContainer(final NavigationActivity context) {
		ReactNativeHost reactNativeHost = mock(ReactNativeHost.class);
		when(reactNativeHost.getReactInstanceManager()).thenReturn(mock(ReactInstanceManager.class));
		return new Container(context, "id", "name", reactNativeHost.getReactInstanceManager());
	}
}
