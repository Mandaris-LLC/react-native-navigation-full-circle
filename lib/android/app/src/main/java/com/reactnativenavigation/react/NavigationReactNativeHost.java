package com.reactnativenavigation.react;

import android.app.Application;

import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactPackage;
import com.facebook.react.shell.MainReactPackage;
import com.reactnativenavigation.controllers.CommandsHandler;

import java.util.Arrays;
import java.util.List;

public class NavigationReactNativeHost extends ReactNativeHost {

	private final boolean isDebug;
	private CommandsHandler commandsHandler;

	public NavigationReactNativeHost(Application application, boolean isDebug, CommandsHandler commandsHandler) {
		super(application);
		this.isDebug = isDebug;
		this.commandsHandler = commandsHandler;
	}

	@Override
	public boolean getUseDeveloperSupport() {
		return isDebug;
	}

	@Override
	protected List<ReactPackage> getPackages() {
		return Arrays.<ReactPackage>asList(
				new MainReactPackage(),
				new NavigationPackage(commandsHandler)
		);
	}
}
