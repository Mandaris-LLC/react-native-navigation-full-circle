package com.reactnativenavigation.react;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactRootView;
import com.reactnativenavigation.viewcontrollers.ContainerViewController;

public class ReactContainerView extends ReactRootView implements ContainerViewController.ContainerView {

	private final ReactInstanceManager reactInstanceManager;
	private final String containerId;
	private final String containerName;
	private boolean attachedToReactInstance = false;

	public ReactContainerView(final Context context, ReactInstanceManager reactInstanceManager, String containerName, String containerId) {
		super(context);
		this.reactInstanceManager = reactInstanceManager;
		this.containerName = containerName;
		this.containerId = containerId;
		start();
	}

	private void start() {
		setEventListener(new ReactRootView.ReactRootViewEventListener() {

			@Override
			public void onAttachedToReactInstance(final ReactRootView reactRootView) {
				reactRootView.setEventListener(null);
				attachedToReactInstance = true;
			}
		});
		final Bundle opts = new Bundle();
		opts.putString("containerId", containerId);
		startReactApplication(reactInstanceManager, containerName, opts);
	}

	@Override
	public boolean isReady() {
		return attachedToReactInstance;
	}

	@Override
	public View asView() {
		return this;
	}

	@Override
	public void destroy() {
		unmountReactApplication();
	}

	@Override
	public void sendContainerStart() {
		new NavigationEvent(reactInstanceManager.getCurrentReactContext()).containerStart(containerId);
	}

	@Override
	public void sendContainerStop() {
		new NavigationEvent(reactInstanceManager.getCurrentReactContext()).containerStop(containerId);
	}
}
