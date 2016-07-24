package com.reactnativenavigation.react;

import android.content.Intent;

import com.facebook.react.LifecycleState;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactPackage;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
import com.reactnativenavigation.NavigationApplication;
import com.reactnativenavigation.bridge.NavigationReactEventEmitter;
import com.reactnativenavigation.controllers.NavigationActivity;

public class NavigationReactInstance {
    private final ReactInstanceManager reactInstanceManager;
    private NavigationReactEventEmitter reactEventEmitter;
    private OnJsDevReloadListener onJsDevReloadListener;

    public interface OnJsDevReloadListener {
        void onJsDevReload();
    }

    public NavigationReactInstance() {
        reactInstanceManager = createReactInstanceManager();

        if (NavigationApplication.instance.isDebug()) {
            replaceJsDevReloadListener();
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

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        reactInstanceManager.onActivityResult(requestCode, resultCode, data);
    }

    public void onBackPressed() {
        reactInstanceManager.onBackPressed();
    }

    public void onResume(NavigationActivity activity, DefaultHardwareBackBtnHandler defaultHardwareBackBtnHandler, OnJsDevReloadListener onJsDevReloadListener) {
        this.onJsDevReloadListener = onJsDevReloadListener;
        reactInstanceManager.onHostResume(activity, defaultHardwareBackBtnHandler);
        reactEventEmitter = new NavigationReactEventEmitter(reactInstanceManager.getCurrentReactContext());
    }

    public void onPause() {
        onJsDevReloadListener = null;
        reactEventEmitter = null;
        reactInstanceManager.onHostPause();
    }

    public void onHostDestroy() {
        reactInstanceManager.onHostDestroy();
    }

    private void replaceJsDevReloadListener() {
        new JsDevReloadListenerReplacer(reactInstanceManager, new JsDevReloadListenerReplacer.Listener() {
            @Override
            public void onJsDevReload() {
                if (onJsDevReloadListener != null)
                    onJsDevReloadListener.onJsDevReload();
            }
        }).replace();
    }

    private ReactInstanceManager createReactInstanceManager() {
        ReactInstanceManager.Builder builder = ReactInstanceManager.builder()
                .setApplication(NavigationApplication.instance)
                .setJSMainModuleName(NavigationApplication.instance.getJsEntryFileName())
                .setBundleAssetName(NavigationApplication.instance.getBundleAssetName())
                .setUseDeveloperSupport(NavigationApplication.instance.isDebug())
                .setInitialLifecycleState(LifecycleState.BEFORE_RESUME);

        for (ReactPackage reactPackage : NavigationApplication.instance.createReactPackages()) {
            builder.addPackage(reactPackage);
        }

        return builder.build();
    }

    public NavigationReactEventEmitter getReactEventEmitter() {
        return reactEventEmitter;
    }
}
