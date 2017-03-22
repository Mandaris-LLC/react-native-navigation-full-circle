package com.reactnativenavigation.controllers;

import android.app.Activity;

import com.facebook.react.ReactInstanceManager;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
import com.reactnativenavigation.react.NavigationEventEmitter;
import com.reactnativenavigation.react.ReactDevPermission;

import java.util.concurrent.atomic.AtomicBoolean;

public class ActivityLifecycleDelegate {

	private final Activity activity;
	private final DefaultHardwareBackBtnHandler backBtnHandler;
	private final ReactInstanceManager reactInstanceManager;
	private final AtomicBoolean appLaunchEmitted = new AtomicBoolean(false);

	public ActivityLifecycleDelegate(Activity activity,
	                                 DefaultHardwareBackBtnHandler backBtnHandler,
	                                 ReactInstanceManager reactInstanceManager) {
		this.activity = activity;
		this.backBtnHandler = backBtnHandler;
		this.reactInstanceManager = reactInstanceManager;
	}

	public void onActivityCreated() {
		appLaunchEmitted.set(false);
	}

	public void onActivityResumed() {
		if (ReactDevPermission.shouldAskPermission()) {
			ReactDevPermission.askPermission(activity);
		} else {
			reactInstanceManager.onHostResume(activity, backBtnHandler);
			prepareReactApp();
		}
	}

	public void onActivityPaused() {
		if (reactInstanceManager.hasStartedCreatingInitialContext()) {
			reactInstanceManager.onHostPause(activity);
		}
	}

	public void onActivityDestroyed() {
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
