package com.reactnativenavigation.react;

import com.facebook.react.ReactInstanceManager;
import com.facebook.react.bridge.ReactContext;
import com.reactnativenavigation.NavigationActivity;

import java.util.concurrent.atomic.AtomicBoolean;

public class NavigationReactInitializer {

	private final ReactInstanceManager reactInstanceManager;
	private final DevPermissionRequest devPermissionRequest;
	private final AtomicBoolean appLaunchEmitted = new AtomicBoolean(false);

	public NavigationReactInitializer(ReactInstanceManager reactInstanceManager, final DevPermissionRequest devPermissionRequest) {
		this.reactInstanceManager = reactInstanceManager;
		this.devPermissionRequest = devPermissionRequest;
	}

	public void onActivityCreated(NavigationActivity activity) {
		appLaunchEmitted.set(false);
	}

	public void onActivityResumed(NavigationActivity activity) {
		if (devPermissionRequest.shouldAskPermission(activity)) {
			devPermissionRequest.askPermission(activity);
		} else {
			reactInstanceManager.onHostResume(activity, activity);
			prepareReactApp();
		}
	}

	public void onActivityPaused(NavigationActivity activity) {
		if (reactInstanceManager.hasStartedCreatingInitialContext()) {
			reactInstanceManager.onHostPause(activity);
		}
	}

	public void onActivityDestroyed(NavigationActivity activity) {
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
			new NavigationEvent(reactInstanceManager.getCurrentReactContext()).appLaunched();
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
