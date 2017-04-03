package com.reactnativenavigation.layout.containers;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactRootView;
import com.facebook.react.bridge.ReactContext;
import com.reactnativenavigation.NavigationApplication;
import com.reactnativenavigation.react.NavigationEventEmitter;

public class Container extends FrameLayout {
	private final ReactNativeHost reactNativeHost;
	private final String id;
	private final String name;
	private ReactRootView rootView;

	public Container(Activity activity, ReactNativeHost reactNativeHost, String id, String name) {
		super(activity);
		this.reactNativeHost = reactNativeHost;
		this.id = id;
		this.name = name;
		addView(createReactRootView());
	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		onStop();
	}

	public void destroy() {
		rootView.unmountReactApplication();
	}

	private View createReactRootView() {
		rootView = new ReactRootView(getContext());
		Bundle opts = new Bundle();
		opts.putString("id", id);
		rootView.startReactApplication(reactNativeHost.getReactInstanceManager(), name, opts);
		rootView.setEventListener(new ReactRootView.ReactRootViewEventListener() {
			@Override
			public void onAttachedToReactInstance(final ReactRootView reactRootView) {
				rootView.setEventListener(null);
				onStart();
			}
		});
		return rootView;
	}

	private void onStart() {
		new NavigationEventEmitter(reactContext()).containerStart(id);
	}

	private void onStop() {
		new NavigationEventEmitter(reactContext()).containerStop(id);
	}

	private ReactContext reactContext() {
		//TODO this is wrong, we should inject reactContext somehow
		return ((NavigationApplication) getContext().getApplicationContext()).getReactNativeHost().getReactInstanceManager().getCurrentReactContext();
	}
}
