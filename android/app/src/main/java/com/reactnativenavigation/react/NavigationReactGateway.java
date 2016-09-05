package com.reactnativenavigation.react;

import android.app.Activity;
import android.content.Intent;

import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
import com.facebook.react.shell.MainReactPackage;
import com.reactnativenavigation.NavigationApplication;
import com.reactnativenavigation.bridge.NavigationReactEventEmitter;
import com.reactnativenavigation.bridge.NavigationReactPackage;

import java.util.ArrayList;
import java.util.List;

public class NavigationReactGateway extends ReactGatewayHost implements ReactInstanceManager.ReactInstanceEventListener {

    private OnJsDevReloadListener onJsDevReloadListener;
    private NavigationReactEventEmitter reactEventEmitter;

    public NavigationReactGateway() {
        super(NavigationApplication.instance);
    }

    @Override
    public void startReactContextOnceInBackgroundAndExecuteJS() {
        getReactInstanceManager().createReactContextInBackground();
    }

    public boolean isInitialized() {
        return hasInstance() && getReactInstanceManager().getCurrentReactContext() != null;
    }

    public ReactContext getReactContext() {
        return getReactInstanceManager().getCurrentReactContext();
    }

    public NavigationReactEventEmitter getReactEventEmitter() {
        return reactEventEmitter;
    }

    public void onBackPressed() {
        getReactInstanceManager().onBackPressed();
    }

    public void onDestroyApp() {
        getReactInstanceManager().onHostDestroy();
        getReactInstanceManager().removeReactInstanceEventListener(this);
        clear();
    }

    public void onPauseActivity() {
        getReactInstanceManager().onHostPause();
        onJsDevReloadListener = null;
    }

    public void onResumeActivity(Activity activity, DefaultHardwareBackBtnHandler defaultHardwareBackBtnHandler, OnJsDevReloadListener onJsDevReloadListener) {
        this.onJsDevReloadListener = onJsDevReloadListener;
        getReactInstanceManager().onHostResume(activity, defaultHardwareBackBtnHandler);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        getReactInstanceManager().onActivityResult(requestCode, resultCode, data);
    }

    private void replaceJsDevReloadListener(ReactInstanceManager manager) {
        new JsDevReloadListenerReplacer(manager, new JsDevReloadListenerReplacer.Listener() {
            @Override
            public void onJsDevReload() {
                if (onJsDevReloadListener != null)
                    onJsDevReloadListener.onJsDevReload();
            }
        }).replace();
    }

    @Override
    protected ReactInstanceManager createReactInstanceManager() {
        ReactInstanceManager manager = super.createReactInstanceManager();
        if (NavigationApplication.instance.isDebug()) {
            replaceJsDevReloadListener(manager);
        }
        manager.addReactInstanceEventListener(this);
        return manager;
    }

    @Override
    protected boolean getUseDeveloperSupport() {
        return NavigationApplication.instance.isDebug();
    }

    @Override
    protected List<ReactPackage> getPackages() {
        return createReactPackages();
    }

    private List<ReactPackage> createReactPackages() {
        List<ReactPackage> list = new ArrayList<>();
        list.add(new MainReactPackage());
        list.add(new NavigationReactPackage());
        addAdditionalReactPackagesIfNeeded(list);
        return list;
    }

    private void addAdditionalReactPackagesIfNeeded(List<ReactPackage> list) {
        List<ReactPackage> additionalReactPackages = NavigationApplication.instance.createAdditionalReactPackages();
        if (additionalReactPackages == null) {
            return;
        }

        for (ReactPackage reactPackage : additionalReactPackages) {
            if (reactPackage instanceof MainReactPackage)
                throw new RuntimeException("Do not create a new MainReactPackage. This is created for you.");
            if (reactPackage instanceof NavigationReactPackage)
                throw new RuntimeException("Do not create a new NavigationReactPackage. This is created for you.");
        }

        list.addAll(additionalReactPackages);
    }

    @Override
    public void onReactContextInitialized(ReactContext context) {
        reactEventEmitter = new NavigationReactEventEmitter(context);
        NavigationApplication.instance.onReactInitialized(context);
    }
}
