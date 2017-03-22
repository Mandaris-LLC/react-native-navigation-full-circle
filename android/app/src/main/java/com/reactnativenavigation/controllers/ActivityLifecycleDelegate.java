package com.reactnativenavigation.controllers;

import android.app.Activity;

import com.facebook.react.ReactInstanceManager;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
import com.reactnativenavigation.react.DevPermissionRequest;
import com.reactnativenavigation.react.NavigationEventEmitter;

import java.util.concurrent.atomic.AtomicBoolean;

public class ActivityLifecycleDelegate {

	private final ReactInstanceManager reactInstanceManager;
	private final DevPermissionRequest devPermissionRequest;
	private final AtomicBoolean appLaunchEmitted = new AtomicBoolean(false);

	public ActivityLifecycleDelegate(ReactInstanceManager reactInstanceManager, DevPermissionRequest devPermissionRequest) {
		this.reactInstanceManager = reactInstanceManager;
		this.devPermissionRequest = devPermissionRequest;
	}

	public void onActivityCreated(Activity activity) {
		appLaunchEmitted.set(false);
	}

	public void onActivityResumed(Activity activity, DefaultHardwareBackBtnHandler backBtnHandler) {
		if (devPermissionRequest.shouldAskPermission()) {
			devPermissionRequest.askPermission();
		} else {
			reactInstanceManager.onHostResume(activity, backBtnHandler);
			prepareReactApp();
		}
	}

	public void onActivityPaused(Activity activity) {
		if (reactInstanceManager.hasStartedCreatingInitialContext()) {
			reactInstanceManager.onHostPause(activity);
		}
	}

	public void onActivityDestroyed(Activity activity) {
		if (reactInstanceManager.hasStartedCreatingInitialContext()) {
			reactInstanceManager.onHostDestroy(activity);
		}
	}

	private void prepareReactApp() {
		if (shouldCreateContext()) {
			appLaunchEmitted.set(false);
			createReactContext(new Runnable() {
				@Override
				public void run() {
					emitAppLaunchedOnceIfNeeded();
				}
			});
		} else {
			emitAppLaunchedOnceIfNeeded();
		}
	}

	private void emitAppLaunchedOnceIfNeeded() {
		if (appLaunchEmitted.compareAndSet(false, true)) {
			NavigationEventEmitter.emit(reactInstanceManager.getCurrentReactContext()).appLaunched();
		}
	}

	private void createReactContext(final Runnable onComplete) {
		reactInstanceManager.addReactInstanceEventListener(new ReactInstanceManager.ReactInstanceEventListener() {
			@Override
			public void onReactContextInitialized(final ReactContext context) {
				reactInstanceManager.removeReactInstanceEventListener(this);
				onComplete.run();
			}
		});
		reactInstanceManager.createReactContextInBackground();
	}

	private boolean shouldCreateContext() {
		return !reactInstanceManager.hasStartedCreatingInitialContext();
	}
}
