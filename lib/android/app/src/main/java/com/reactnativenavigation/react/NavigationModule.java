package com.reactnativenavigation.react;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.reactnativenavigation.NavigationActivity;
import com.reactnativenavigation.controllers.CommandsHandler;
import com.reactnativenavigation.utils.UiThread;

public class NavigationModule extends ReactContextBaseJavaModule {
	private static final String NAME = "RNNBridgeModule";
	private CommandsHandler commandsHandler;

	public NavigationModule(final ReactApplicationContext reactContext, CommandsHandler commandsHandler) {
		super(reactContext);
		this.commandsHandler = commandsHandler;
	}

	@Override
	public String getName() {
		return NAME;
	}

	@ReactMethod
	public void setRoot(final ReadableMap layoutTree) {
		handle(new Runnable() {
			@Override
			public void run() {
				commandsHandler.setRoot(activity(), ArgsParser.parse(layoutTree));
			}
		});
	}

	@ReactMethod
	public void push(final String onContainerId, final ReadableMap layoutTree) {
		handle(new Runnable() {
			@Override
			public void run() {
				commandsHandler.push(activity(), onContainerId, ArgsParser.parse(layoutTree));
			}
		});
	}

	@ReactMethod
	public void pop(final String onContainerId) {
		handle(new Runnable() {
			@Override
			public void run() {
				commandsHandler.pop(activity(), onContainerId);
			}
		});
	}

	NavigationActivity activity() {
		return (NavigationActivity) getCurrentActivity();
	}

	private void handle(Runnable task) {
		if (activity() == null) return;
		UiThread.post(task);
	}
}
