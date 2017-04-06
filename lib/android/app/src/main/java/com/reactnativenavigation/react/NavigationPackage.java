package com.reactnativenavigation.react;

import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.JavaScriptModule;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;
import com.reactnativenavigation.controllers.Store;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class NavigationPackage implements ReactPackage {

	private ReactNativeHost reactNativeHost;
	private Store store;

	public NavigationPackage(final ReactNativeHost reactNativeHost, final Store store) {
		this.reactNativeHost = reactNativeHost;
		this.store = store;
	}

	@Override
	public List<NativeModule> createNativeModules(ReactApplicationContext reactContext) {
		return Arrays.<NativeModule>asList(
				new NavigationModule(reactContext, reactNativeHost.getReactInstanceManager(), store)
		);
	}

	@Override
	public List<Class<? extends JavaScriptModule>> createJSModules() {
		return Collections.emptyList();
	}

	@Override
	public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
		return Collections.emptyList();
	}
}
