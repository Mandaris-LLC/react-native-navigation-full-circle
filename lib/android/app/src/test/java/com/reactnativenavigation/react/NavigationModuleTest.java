package com.reactnativenavigation.react;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactMethod;
import com.reactnativenavigation.BaseTest;
import com.reactnativenavigation.NavigationApplication;
import com.reactnativenavigation.controllers.CommandsHandler;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyZeroInteractions;

public class NavigationModuleTest extends BaseTest {

	private NavigationModule uut;

	@Before
	public void beforeEach() {
		ReactApplicationContext reactApplicationContext = mock(ReactApplicationContext.class);
		uut = new NavigationModule(reactApplicationContext);
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
}
