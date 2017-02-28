package com.reactnativenavigation;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.facebook.react.ReactApplication;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
import com.facebook.react.shell.MainReactPackage;
import com.reactnativenavigation.react.NavigationEventEmitter;
import com.reactnativenavigation.react.NavigationPackage;
import com.reactnativenavigation.react.ReactDevPermission;
import com.reactnativenavigation.utils.UiThread;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

public abstract class NavigationApplication extends Application implements ReactApplication {
    public static NavigationApplication instance;
    private ReactNativeHost host;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        host = new ReactNativeHost(this) {
            @Override
            public boolean getUseDeveloperSupport() {
                return isDebug();
            }

            @Override
            protected List<ReactPackage> getPackages() {
                return Arrays.asList(
                        new MainReactPackage(),
                        new NavigationPackage()
                );
            }
        };

        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            private AtomicBoolean creating = new AtomicBoolean(true);
            private AtomicLong startTime = new AtomicLong();

            @Override
            public void onActivityCreated(final Activity activity, Bundle bundle) {
                if (!(activity instanceof NavigationActivity)) return;
                creating.set(true);
            }

            @Override
            public void onActivityStarted(Activity activity) {
            }

            @Override
            public void onActivityResumed(Activity activity) {
                if (!(activity instanceof NavigationActivity)) return;

                if (ReactDevPermission.shouldAskPermission()) {
                    ReactDevPermission.askPermission(activity);
                    return;
                }

                if (!host.getReactInstanceManager().hasStartedCreatingInitialContext()) {
                    startTime.set(System.currentTimeMillis());
                    host.getReactInstanceManager().addReactInstanceEventListener(new ReactInstanceManager.ReactInstanceEventListener() {
                        @Override
                        public void onReactContextInitialized(final ReactContext context) {
                            host.getReactInstanceManager().removeReactInstanceEventListener(this);

                            long millisPassed = System.currentTimeMillis() - startTime.get();
                            long diff = 1000 - millisPassed;

                            UiThread.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    new NavigationEventEmitter(context).emitAppLaunched();
                                }
                            }, diff);
                        }
                    });
                    host.getReactInstanceManager().createReactContextInBackground();
                    host.getReactInstanceManager().onHostResume(activity, (DefaultHardwareBackBtnHandler) activity);
                    return;
                }

                host.getReactInstanceManager().onHostResume(activity, (DefaultHardwareBackBtnHandler) activity);

                if (creating.compareAndSet(true, false)) {
                    UiThread.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            new NavigationEventEmitter(host.getReactInstanceManager().getCurrentReactContext()).emitAppLaunched();
                        }
                    }, 1000);
                }
            }

            @Override
            public void onActivityPaused(Activity activity) {
                if (!(activity instanceof NavigationActivity)) return;

                if (host.getReactInstanceManager().hasStartedCreatingInitialContext()) {
                    host.getReactInstanceManager().onHostPause(activity);
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
                if (!(activity instanceof NavigationActivity)) return;

                if (host.getReactInstanceManager().hasStartedCreatingInitialContext()) {
                    host.getReactInstanceManager().onHostDestroy(activity);
                }
            }
        });
    }

    @Override
    public ReactNativeHost getReactNativeHost() {
        return host;
    }

    public abstract boolean isDebug();
}
