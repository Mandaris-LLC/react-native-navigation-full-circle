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
import com.reactnativenavigation.views.NavigationSplashView;

import java.util.concurrent.atomic.AtomicLong;

public class NavigationActivityLifecycleHandler implements Application.ActivityLifecycleCallbacks {
    private interface OnContextCreated {
        void onContextCreated(long timeElapsed);
    }

    private static final int SPLASH_MINIMUM_DURATION = 1000;

    private final ReactInstanceManager reactInstanceManager;

    public NavigationActivityLifecycleHandler(ReactInstanceManager reactInstanceManager) {
        this.reactInstanceManager = reactInstanceManager;
    }

    @Override
    public void onActivityCreated(final Activity activity, Bundle bundle) {
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

    private void handleResumed(NavigationActivity activity) {
        if (ReactDevPermission.shouldAskPermission()) {
            ReactDevPermission.askPermission(activity);
        } else {
            reactInstanceManager.onHostResume(activity, activity);
            prepareReactApp(activity);
        }
    }

    private void prepareReactApp(NavigationActivity activity) {
        if (shouldCreateContext()) {
            createReactContext(new OnContextCreated() {
                @Override
                public void onContextCreated(long timeElapsed) {
                    emitAppLaunchedAfterDelay(SPLASH_MINIMUM_DURATION - timeElapsed);
                }
            });
        } else if (waitingForAppLaunchedEvent(activity)) {
            emitAppLaunchedAfterDelay(SPLASH_MINIMUM_DURATION);
        }
    }

    /**
     * @return true if we are a newly created activity, but react context already exists
     */
    private boolean waitingForAppLaunchedEvent(NavigationActivity activity) {
        return activity.getContentView() instanceof NavigationSplashView;
    }


    private void createReactContext(final OnContextCreated onContextCreated) {
        final AtomicLong startTime = new AtomicLong(System.currentTimeMillis());
        reactInstanceManager.addReactInstanceEventListener(new ReactInstanceManager.ReactInstanceEventListener() {
            @Override
            public void onReactContextInitialized(final ReactContext context) {
                reactInstanceManager.removeReactInstanceEventListener(this);
                onContextCreated.onContextCreated(System.currentTimeMillis() - startTime.get());
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

    private void emitAppLaunchedAfterDelay(long delay) {
        UiThread.postDelayed(new Runnable() {
            @Override
            public void run() {
                new NavigationEventEmitter(reactInstanceManager.getCurrentReactContext()).emitAppLaunched();
            }
        }, delay);
    }
}
