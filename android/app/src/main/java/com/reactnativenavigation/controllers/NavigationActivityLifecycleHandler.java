package com.reactnativenavigation.controllers;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.facebook.react.ReactInstanceManager;
import com.facebook.react.bridge.ReactContext;
import com.reactnativenavigation.NavigationActivity;
import com.reactnativenavigation.react.NavigationEventEmitter;
import com.reactnativenavigation.react.ReactDevPermission;
import com.reactnativenavigation.utils.UiThread;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

public class NavigationActivityLifecycleHandler implements Application.ActivityLifecycleCallbacks {
    private final ReactInstanceManager reactInstanceManager;
    private final AtomicBoolean creating = new AtomicBoolean(false);
    private final AtomicLong startTime = new AtomicLong(0);

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
        creating.set(true);
    }

    private void handleResumed(NavigationActivity activity) {
        if (ReactDevPermission.shouldAskPermission()) {
            ReactDevPermission.askPermission(activity);
        } else if (shouldCreateContext()) {
            createContextAndEmitLaunched();
            reactInstanceManager.onHostResume(activity, activity);
        } else {
            reactInstanceManager.onHostResume(activity, activity);
            if (creating.compareAndSet(true, false)) {
                // this should run only after activity closed and started again, but we already HAVE context
                emitAppLaunchedAfterDelay(reactInstanceManager.getCurrentReactContext(), 1000);
            }
        }
    }

    private void createContextAndEmitLaunched() {
        creating.set(false);
        startTime.set(System.currentTimeMillis());
        reactInstanceManager.addReactInstanceEventListener(new ReactInstanceManager.ReactInstanceEventListener() {
            @Override
            public void onReactContextInitialized(final ReactContext context) {
                reactInstanceManager.removeReactInstanceEventListener(this);

                long millisPassed = System.currentTimeMillis() - startTime.get();
                long diff = 1000 - millisPassed;

                emitAppLaunchedAfterDelay(context, diff);
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

    private void emitAppLaunchedAfterDelay(final ReactContext context, long delay) {
        UiThread.postDelayed(new Runnable() {
            @Override
            public void run() {
                new NavigationEventEmitter(context).emitAppLaunched();
            }
        }, delay);
    }
}
