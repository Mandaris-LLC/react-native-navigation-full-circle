package com.reactnativenavigation;

import android.app.Application;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.reactnativenavigation.react.NavigationReactGateway;
import com.reactnativenavigation.react.ReactGateway;

import java.util.List;

public abstract class NavigationApplication extends Application {

    public static NavigationApplication instance;

    private ReactGateway reactGateway;
    private Handler handler;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        reactGateway = new NavigationReactGateway();
        handler = new Handler(getMainLooper());
    }

    public void startReactContext() {
        reactGateway.startReactContextOnceInBackgroundAndExecuteJS();
    }

    public void runOnMainThread(Runnable runnable) {
        handler.post(runnable);
    }

    public void runOnMainThread(Runnable runnable, long delay) {
        handler.postDelayed(runnable, delay);
    }

    public ReactGateway getReactGateway() {
        return reactGateway;
    }

    public boolean isReactContextInitialized() {
        return reactGateway.isInitialized();
    }

    public void onReactInitialized(ReactContext reactContext) {
        // nothing
    }
    
    public String getJsEntryFileName() {
        return "index.android";
    }

    public String getBundleAssetName() {
        return "index.android.bundle";
    }

    public abstract boolean isDebug();


    @Nullable
    public abstract List<ReactPackage> createAdditionalReactPackages();

    //TODO move all these navigator junk elsewhere
    public void sendNavigatorEvent(String eventId, String navigatorEventId) {
        if (!isReactContextInitialized()) {
            return;
        }
        reactGateway.getReactEventEmitter().sendNavigatorEvent(eventId, navigatorEventId);
    }

    public void sendNavigatorEvent(String eventId, String navigatorEventId, WritableMap data) {
        if (!isReactContextInitialized()) {
            return;
        }
        reactGateway.getReactEventEmitter().sendNavigatorEvent(eventId, navigatorEventId, data);
    }

    public void sendEvent(String eventId, String navigatorEventId) {
        if (!isReactContextInitialized()) {
            return;
        }
        reactGateway.getReactEventEmitter().sendEvent(eventId, navigatorEventId);
    }

    public void sendNavigatorEvent(String eventId, WritableMap arguments) {
        if (!isReactContextInitialized()) {
            return;
        }
        reactGateway.getReactEventEmitter().sendEvent(eventId, arguments);
    }

}
