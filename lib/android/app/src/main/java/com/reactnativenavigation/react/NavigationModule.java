package com.reactnativenavigation.react;

import com.facebook.react.ReactInstanceManager;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.reactnativenavigation.NavigationActivity;
import com.reactnativenavigation.controllers.Store;
import com.reactnativenavigation.layout.LayoutFactory;
import com.reactnativenavigation.layout.LayoutNode;
import com.reactnativenavigation.parse.JSONParser;
import com.reactnativenavigation.parse.LayoutNodeParser;
import com.reactnativenavigation.utils.UiThread;
import com.reactnativenavigation.viewcontrollers.ViewController;

public class NavigationModule extends ReactContextBaseJavaModule {
	private static final String NAME = "RNNBridgeModule";
	private final ReactInstanceManager reactInstanceManager;
	private Store store;

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
				LayoutFactory factory = new LayoutFactory(activity(), reactInstanceManager, store);
				final ViewController rootView = factory.createAndSaveToStore(layoutTree);
				activity().setLayout(rootView);
			}
		});
	}

	@ReactMethod
	public void push(final String onContainerId, final ReadableMap rawLayoutTree) {
		handle(new Runnable() {
			@Override
			public void run() {
				final LayoutNode layoutTree = LayoutNodeParser.parse(JSONParser.parse(rawLayoutTree));
				LayoutFactory factory = new LayoutFactory(activity(), reactInstanceManager, store);
				final ViewController rootView = factory.createAndSaveToStore(layoutTree);
				store.getViewController(onContainerId).getStackController().push(rootView);
			}
		});
	}

	@ReactMethod
	public void pop(final String onContainerId) {
		handle(new Runnable() {
			@Override
			public void run() {
				ViewController layout = store.getViewController(onContainerId);
				layout.getStackController().pop(layout);
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
