package com.reactnativenavigation.layout;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactRootView;
import com.reactnativenavigation.react.NavigationEvent;
import com.reactnativenavigation.viewcontrollers.ViewController;

public class ReactRootViewController extends ViewController {

	private final String name;
	private final ReactInstanceManager reactInstanceManager;
	private boolean attachedToReactInstance = false;

	public ReactRootViewController(final Activity activity, final String id, final String name, final ReactInstanceManager reactInstanceManager) {
		super(activity, id);
		this.name = name;
		this.reactInstanceManager = reactInstanceManager;
	}

//	@Override
//	public void destroy() {
//		reactRootView.unmountReactApplication();
//	}

	@Override
	public void onAppear() {
		super.onAppear();
		new NavigationEvent(reactInstanceManager.getCurrentReactContext()).containerStart(getId());
	}

	@Override
	public void onDisappear() {
		super.onDisappear();
		new NavigationEvent(reactInstanceManager.getCurrentReactContext()).containerStop(getId());
	}

	@Override
	protected boolean isViewShown() {
		return super.isViewShown() && attachedToReactInstance;
	}

	@NonNull
	@Override
	protected View createView() {
		ReactRootView reactRootView = new ReactRootView(getActivity());
		Bundle opts = new Bundle();
		opts.putString("id", getId());
		reactRootView.startReactApplication(this.reactInstanceManager, this.name, opts);
		reactRootView.setEventListener(new ReactRootView.ReactRootViewEventListener() {
			@Override
			public void onAttachedToReactInstance(final ReactRootView reactRootView) {
				reactRootView.setEventListener(null);
				attachedToReactInstance = true;
			}
		});
		return reactRootView;
	}
}
