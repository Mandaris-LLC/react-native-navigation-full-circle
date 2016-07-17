package com.reactnativenavigation.react;

import android.app.Activity;
import android.content.Intent;

import com.facebook.react.LifecycleState;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
import com.facebook.react.modules.core.DeviceEventManagerModule.RCTDeviceEventEmitter;
import com.reactnativenavigation.BuildConfig;
import com.reactnativenavigation.NavigationApplication;

import java.util.List;

public class NavigationReactInstance {

    private final ReactInstanceManager reactInstanceManager;

    public interface ReactContextCreator {
        List<ReactPackage> createReactPackages();

        void onJsDevReload();
    }

    public NavigationReactInstance(final ReactContextCreator reactContextCreator) {
        reactInstanceManager = createReactInstanceManager(reactContextCreator);

        if (BuildConfig.DEBUG) {
            replaceJsDevReloadListener(reactContextCreator);
        }
    }

    public ReactInstanceManager getReactInstanceManager() {
        return reactInstanceManager;
    }

    public void startReactContextOnceInBackgroundAndExecuteJS() {
        if (!reactInstanceManager.hasStartedCreatingInitialContext()) {
            reactInstanceManager.createReactContextInBackground();
        }
    }

    public RCTDeviceEventEmitter getEventEmitter() {
        ReactContext currentReactContext = reactInstanceManager.getCurrentReactContext();
        if (currentReactContext == null) {
            return null;
        }

        return currentReactContext.getJSModule(RCTDeviceEventEmitter.class);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        reactInstanceManager.onActivityResult(requestCode, resultCode, data);
    }

    public void onBackPressed() {
        reactInstanceManager.onBackPressed();
    }

    public void onResume(Activity activity, DefaultHardwareBackBtnHandler defaultHardwareBackBtnHandler) {
        reactInstanceManager.onHostResume(activity, defaultHardwareBackBtnHandler);
    }

    public void onPause() {
        reactInstanceManager.onHostPause();
    }

    public void onHostDestroy() {
        reactInstanceManager.onHostDestroy();
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
                .setApplication(NavigationApplication.instance)
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
