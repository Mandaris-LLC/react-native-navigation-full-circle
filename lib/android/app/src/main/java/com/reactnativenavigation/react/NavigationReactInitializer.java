package com.reactnativenavigation.react;

import com.facebook.react.ReactInstanceManager;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.devsupport.interfaces.PackagerStatusCallback;
import com.reactnativenavigation.NavigationActivity;
import com.reactnativenavigation.utils.UiThread;

public class NavigationReactInitializer implements ReactInstanceManager.ReactInstanceEventListener {

	private final ReactInstanceManager reactInstanceManager;
	private final DevPermissionRequest devPermissionRequest;
	private final boolean isDebug;
	private boolean waitingForAppLaunchEvent = true;

	public NavigationReactInitializer(ReactInstanceManager reactInstanceManager, boolean isDebug) {
		this.reactInstanceManager = reactInstanceManager;
		this.devPermissionRequest = new DevPermissionRequest(isDebug);
		this.isDebug = isDebug;
	}

	public void onActivityCreated(NavigationActivity activity) {
		reactInstanceManager.addReactInstanceEventListener(this);
		waitingForAppLaunchEvent = true;
	}

	public void onActivityResumed(NavigationActivity activity) {
		if (devPermissionRequest.shouldAskPermission(activity)) {
			devPermissionRequest.askPermission(activity);
		} else {
			reactInstanceManager.onHostResume(activity, activity);
			checkBundleThenPrepareReact(activity);
		}
	}

	public void onActivityPaused(NavigationActivity activity) {
		if (reactInstanceManager.hasStartedCreatingInitialContext()) {
			reactInstanceManager.onHostPause(activity);
		}
	}

	public void onActivityDestroyed(NavigationActivity activity) {
		reactInstanceManager.removeReactInstanceEventListener(this);
		if (reactInstanceManager.hasStartedCreatingInitialContext()) {
			reactInstanceManager.onHostDestroy(activity);
		}
	}

	private void checkBundleThenPrepareReact(final NavigationActivity activity) {
		if (isDebug) {
			reactInstanceManager.getDevSupportManager().isPackagerRunning(new PackagerStatusCallback() {
				@Override
				public void onPackagerStatusFetched(final boolean packagerIsRunning) {
					UiThread.post(new Runnable() {
						@Override
						public void run() {
							if (!packagerIsRunning) {
								activity.toast("Packager is not running!");
							} else {
								prepareReactAppWithWorkingBundle();
							}
						}
					});
				}
			});
		} else {
			prepareReactAppWithWorkingBundle();
		}
	}

	private void prepareReactAppWithWorkingBundle() {
		if (shouldCreateContext()) {
			reactInstanceManager.createReactContextInBackground();
		} else if (waitingForAppLaunchEvent) {
			emitAppLaunched();
		}
	}

	private void emitAppLaunched() {
		waitingForAppLaunchEvent = false;
		new NavigationEvent(reactInstanceManager.getCurrentReactContext()).appLaunched();
	}

	private boolean shouldCreateContext() {
		return !reactInstanceManager.hasStartedCreatingInitialContext();
	}

	@Override
	public void onReactContextInitialized(final ReactContext context) {
		emitAppLaunched();
	}
}
