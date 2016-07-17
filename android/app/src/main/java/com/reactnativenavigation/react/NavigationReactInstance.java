package com.reactnativenavigation.react;

import android.app.Application;

import com.facebook.react.LifecycleState;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.modules.core.DeviceEventManagerModule.RCTDeviceEventEmitter;
import com.reactnativenavigation.BuildConfig;

import java.util.List;

public class NavigationReactInstance {

    private final ReactInstanceManager reactInstanceManager;

    public interface ReactContextCreator {
        Application getApplication();

        List<ReactPackage> createReactPackages();

        void onJsDevReload();
    }

    public NavigationReactInstance(final ReactContextCreator reactContextCreator) {
        reactInstanceManager = createReactInstanceManager(reactContextCreator);

        if (BuildConfig.DEBUG) {
            replaceJsDevReloadListener(reactContextCreator);
        }
    }

    private RCTDeviceEventEmitter getEventEmitter() {
        ReactContext currentReactContext = reactInstanceManager.getCurrentReactContext();
        if (currentReactContext == null) {
            return null;
        }

        return currentReactContext.getJSModule(RCTDeviceEventEmitter.class);
    }

    private void replaceJsDevReloadListener(final ReactContextCreator reactContextCreator) {
        new JsDevReloadListenerReplacer(reactInstanceManager, new JsDevReloadListenerReplacer.Listener() {
            @Override
            public void onJsDevReload() {
                reactContextCreator.onJsDevReload();
            }
        }).replace();
    }

    private ReactInstanceManager createReactInstanceManager(final ReactContextCreator reactContextCreator) {
        ReactInstanceManager.Builder builder = ReactInstanceManager.builder()
                .setApplication(reactContextCreator.getApplication())
                .setJSMainModuleName("index.android")
                .setBundleAssetName("index.android.bundle")
                .setUseDeveloperSupport(BuildConfig.DEBUG)
                .setInitialLifecycleState(LifecycleState.BEFORE_RESUME);

        for (ReactPackage reactPackage : reactContextCreator.createReactPackages()) {
            builder.addPackage(reactPackage);
        }

        return builder.build();
    }
}
