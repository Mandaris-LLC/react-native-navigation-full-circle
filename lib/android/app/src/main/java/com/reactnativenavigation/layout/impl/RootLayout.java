package com.reactnativenavigation.layout.impl;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactRootView;
import com.reactnativenavigation.layout.Layout;
import com.reactnativenavigation.react.NavigationEvent;
import com.reactnativenavigation.utils.CompatUtils;

public class RootLayout implements Layout, View.OnAttachStateChangeListener {

	private final String id;
	private final String name;
	private final FrameLayout view;
	private final ReactRootView reactRootView;
	private final ReactInstanceManager reactInstanceManager;

	public RootLayout(Activity activity, String id, String name, final ReactInstanceManager reactInstanceManager) {
		this.id = id;
		this.name = name;
		this.reactInstanceManager = reactInstanceManager;
		reactRootView = new ReactRootView(activity);
		reactRootView.addOnAttachStateChangeListener(this);
		Bundle opts = new Bundle();
		opts.putString("id", this.id);
		reactRootView.startReactApplication(this.reactInstanceManager, this.name, opts);
		reactRootView.setEventListener(new ReactRootView.ReactRootViewEventListener() {
			@Override
			public void onAttachedToReactInstance(final ReactRootView reactRootView) {
				reactRootView.setEventListener(null);
				onStart();
			}
		});
		view = new FrameLayout(activity);
		view.addView(reactRootView);
		view.setId(CompatUtils.generateViewId());
	}

	@Override
	public void destroy() {
		reactRootView.unmountReactApplication();
	}

	@Override
	public void onViewAttachedToWindow(final View v) {
		//
	}

	@Override
	public void onViewDetachedFromWindow(final View v) {
		onStop();
	}

	@Override
	public View getView() {
		return view;
	}

	private void onStart() {
		new NavigationEvent(reactInstanceManager.getCurrentReactContext()).containerStart(id);
	}

	private void onStop() {
		new NavigationEvent(reactInstanceManager.getCurrentReactContext()).containerStop(id);
	}
}
