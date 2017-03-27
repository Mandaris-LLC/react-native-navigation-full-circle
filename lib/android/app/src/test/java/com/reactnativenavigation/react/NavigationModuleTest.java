package com.reactnativenavigation.react;

import com.facebook.react.bridge.JavaOnlyMap;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactMethod;
import com.reactnativenavigation.BaseTest;
import com.reactnativenavigation.NavigationActivity;
import com.reactnativenavigation.NavigationApplication;
import com.reactnativenavigation.controllers.CommandsHandler;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.robolectric.Robolectric;

import java.lang.reflect.Method;
import java.util.HashMap;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

public class NavigationModuleTest extends BaseTest {

	private NavigationModule uut;
	private ReactApplicationContext mockReactApplicationContext;

	@Before
	public void beforeEach() {
		mockReactApplicationContext = mock(ReactApplicationContext.class);
		uut = new NavigationModule(mockReactApplicationContext);
	}

	@Test
	public void bridgeModule() throws Exception {
		assertThat(uut.getName()).isEqualTo("RNNBridgeModule");
	}

	@Test
	public void allReactMethodsProtectAgainstNullActivity() throws Exception {
		NavigationApplication.instance.getConfig().commandsHandler = mock(CommandsHandler.class);

		assertThat(uut.activity()).isNull();

		for (Method method : NavigationModule.class.getDeclaredMethods()) {
			if (method.getAnnotation(ReactMethod.class) != null) {
				Object[] args = new Object[method.getParameterTypes().length];
				method.invoke(uut, args);
				verifyZeroInteractions(NavigationApplication.instance.getConfig().commandsHandler);
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Test
	public void parsesArgumentsIntoMap() throws Exception {
		NavigationApplication.instance.getConfig().commandsHandler = mock(CommandsHandler.class);
		when(mockReactApplicationContext.getCurrentActivity()).thenReturn(Robolectric.buildActivity(NavigationActivity.class).get());
		JavaOnlyMap input = new JavaOnlyMap();
		input.putString("key", "value");
		uut.setRoot(input);
		ArgumentCaptor<Object> captor = ArgumentCaptor.forClass(HashMap.class);
		verify(NavigationApplication.instance.getConfig().commandsHandler, times(1)).setRoot((NavigationActivity) any(), (HashMap<String, Object>) captor.capture());
		assertThat(captor.getAllValues()).hasSize(1);
		assertThat(captor.getValue()).isNotNull();
	}
}
