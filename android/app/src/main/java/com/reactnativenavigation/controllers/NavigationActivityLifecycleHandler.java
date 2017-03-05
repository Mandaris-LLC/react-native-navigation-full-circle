package com.reactnativenavigation.controllers;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.facebook.react.ReactInstanceManager;
import com.facebook.react.bridge.ReactContext;
import com.reactnativenavigation.NavigationActivity;
import com.reactnativenavigation.react.NavigationEventEmitter;
import com.reactnativenavigation.react.ReactDevPermission;

import java.util.concurrent.atomic.AtomicBoolean;

public class NavigationActivityLifecycleHandler implements Application.ActivityLifecycleCallbacks {
	private final ReactInstanceManager reactInstanceManager;
	private final AtomicBoolean appLaunchEmitted = new AtomicBoolean(false);

	public NavigationActivityLifecycleHandler(ReactInstanceManager reactInstanceManager) {
		this.reactInstanceManager = reactInstanceManager;
	}

	@Override
	public void onActivityCreated(final Activity activity, Bundle bundle) {
		if (activity instanceof NavigationActivity) {
			handleCreated();
		}
	}

	@Override
	public void onActivityStarted(Activity activity) {
	}

	@Override
	public void onActivityResumed(Activity activity) {
		if (activity instanceof NavigationActivity) {
			handleResumed((NavigationActivity) activity);
		}
	}

	@Override
	public void onActivityPaused(Activity activity) {
		if (activity instanceof NavigationActivity) {
			handlePaused((NavigationActivity) activity);
		}
	}

	@Override
	public void onActivityStopped(Activity activity) {
	}

	@Override
	public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
	}

	@Override
	public void onActivityDestroyed(Activity activity) {
		if (activity instanceof NavigationActivity) {
			handleDestroyed((NavigationActivity) activity);
		}
	}

	private void handleCreated() {
		appLaunchEmitted.set(false);
	}

	private void handleResumed(NavigationActivity activity) {
		if (ReactDevPermission.shouldAskPermission()) {
			ReactDevPermission.askPermission(activity);
		} else {
			reactInstanceManager.onHostResume(activity, activity);
			prepareReactApp();
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

	private void handlePaused(NavigationActivity activity) {
		if (reactInstanceManager.hasStartedCreatingInitialContext()) {
			reactInstanceManager.onHostPause(activity);
		}
	}

	private void handleDestroyed(NavigationActivity activity) {
		if (reactInstanceManager.hasStartedCreatingInitialContext()) {
			reactInstanceManager.onHostDestroy(activity);
		}
	}
}
