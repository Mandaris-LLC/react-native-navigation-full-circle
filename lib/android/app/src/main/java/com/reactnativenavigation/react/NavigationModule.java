package com.reactnativenavigation.react;

import android.view.View;

import com.facebook.react.ReactInstanceManager;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.reactnativenavigation.NavigationActivity;
import com.reactnativenavigation.layout.LayoutFactory;
import com.reactnativenavigation.layout.LayoutNode;
import com.reactnativenavigation.layout.containers.Container;
import com.reactnativenavigation.layout.containers.ContainerStack;
import com.reactnativenavigation.utils.UiThread;

public class NavigationModule extends ReactContextBaseJavaModule {
	private static final String NAME = "RNNBridgeModule";
	private ReactInstanceManager reactInstanceManager;

	public NavigationModule(final ReactApplicationContext reactContext, final ReactInstanceManager reactInstanceManager) {
		super(reactContext);
		this.reactInstanceManager = reactInstanceManager;
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
				final LayoutNode layoutTreeRoot = LayoutNode.parse(ArgsParser.parse(layoutTree));
				LayoutFactory factory = new LayoutFactory(activity(), reactInstanceManager);
				final View rootView = factory.create(layoutTreeRoot);
				activity().setContentView(rootView);
			}
		});
	}

	@ReactMethod
	public void push(final String onContainerId, final ReadableMap layoutTree) {
		handle(new Runnable() {
			@Override
			public void run() {
				final LayoutNode layoutNode = LayoutNode.parse(ArgsParser.parse(layoutTree));
				LayoutFactory factory = new LayoutFactory(activity(), reactInstanceManager);
				final View rootView = factory.create(layoutNode);
				((ContainerStack) activity().getContentView()).push((Container) rootView);
			}
		});
	}

	@ReactMethod
	public void pop(final String onContainerId) {
		handle(new Runnable() {
			@Override
			public void run() {
				((ContainerStack) activity().getContentView()).pop();
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
