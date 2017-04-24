package com.reactnativenavigation.react;

import android.support.annotation.NonNull;

import com.facebook.react.ReactInstanceManager;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.reactnativenavigation.NavigationActivity;
import com.reactnativenavigation.Store;
import com.reactnativenavigation.layout.LayoutFactory;
import com.reactnativenavigation.layout.LayoutNode;
import com.reactnativenavigation.parse.JSONParser;
import com.reactnativenavigation.parse.LayoutNodeParser;
import com.reactnativenavigation.utils.UiThread;
import com.reactnativenavigation.viewcontrollers.ViewController;

public class NavigationModule extends ReactContextBaseJavaModule {
	private static final String NAME = "RNNBridgeModule";
	private final ReactInstanceManager reactInstanceManager;
	private final Store store;

	public NavigationModule(final ReactApplicationContext reactContext, final ReactInstanceManager reactInstanceManager, final Store store) {
		super(reactContext);
		this.reactInstanceManager = reactInstanceManager;
		this.store = store;
	}

	@Override
	public String getName() {
		return NAME;
	}

	@ReactMethod
	public void setRoot(final ReadableMap rawLayoutTree) {
		handle(new Runnable() {
			@Override
			public void run() {
				final LayoutNode layoutTree = LayoutNodeParser.parse(JSONParser.parse(rawLayoutTree));
				final ViewController viewController = newLayoutFactory().createAndSaveToStore(layoutTree);
				activity().setViewController(viewController);
			}
		});
	}

	@ReactMethod
	public void push(final String onContainerId, final ReadableMap rawLayoutTree) {
		handle(new Runnable() {
			@Override
			public void run() {
				final LayoutNode layoutTree = LayoutNodeParser.parse(JSONParser.parse(rawLayoutTree));
				final ViewController viewController = newLayoutFactory().createAndSaveToStore(layoutTree);
				store.getViewController(onContainerId).getStackController().push(viewController);
			}
		});
	}

	@ReactMethod
	public void pop(final String onContainerId) {
		handle(new Runnable() {
			@Override
			public void run() {
				store.getViewController(onContainerId).getStackController().pop(store.getViewController(onContainerId));
			}
		});
	}

	private NavigationActivity activity() {
		return (NavigationActivity) getCurrentActivity();
	}

	@NonNull
	private LayoutFactory newLayoutFactory() {
		return new LayoutFactory(activity(), reactInstanceManager, store);
	}

	private void handle(Runnable task) {
		if (activity() == null) return;
		UiThread.post(task);
	}
}
