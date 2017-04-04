package com.reactnativenavigation.layout.containers;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactRootView;
import com.reactnativenavigation.react.NavigationEventEmitter;

public class Container extends FrameLayout {

	private final String id;
	private final String name;
	private ReactRootView rootView;
	private final ReactInstanceManager reactInstanceManager;


	public Container(Activity activity, String id, String name, final ReactInstanceManager reactInstanceManager) {
		super(activity);

		this.id = id;
		this.name = name;
		this.reactInstanceManager = reactInstanceManager;
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
		rootView.startReactApplication(reactInstanceManager, name, opts);
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
		new NavigationEventEmitter(reactInstanceManager.getCurrentReactContext()).containerStart(id);
	}

	private void onStop() {
		new NavigationEventEmitter(reactInstanceManager.getCurrentReactContext()).containerStop(id);
	}

}
