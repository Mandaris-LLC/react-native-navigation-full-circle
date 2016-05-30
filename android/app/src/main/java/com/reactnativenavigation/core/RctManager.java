package com.reactnativenavigation.core;

import android.app.Application;

import com.facebook.react.LifecycleState;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.WritableMap;
import com.reactnativenavigation.activities.BaseReactActivity;
import com.reactnativenavigation.core.objects.Screen;

import static com.facebook.react.modules.core.DeviceEventManagerModule.RCTDeviceEventEmitter;

/**
 * Created by guyc on 22/02/16.
 */
public class RctManager {
    private static final String KEY_EVENT_ID = "id";
    private static RctManager sInstance;

    private ReactInstanceManager mReactManager;

    private RctManager() {
        // Singleton
    }

    public static synchronized RctManager getInstance() {
        if (sInstance == null) {
            sInstance = new RctManager();
        }
        return sInstance;
    }

    public ReactInstanceManager getReactInstanceManager() {
        return mReactManager;
    }

    public boolean isInitialized() {
        return mReactManager != null;
    }

    public void init(BaseReactActivity context) {
        createReactInstanceManager(context);
    }

    /**
     * Creates a React Instance Manager associated with this component name
     */
    public ReactInstanceManager createReactInstanceManager(BaseReactActivity reactActivity) {
        ReactInstanceManager.Builder builder = ReactInstanceManager.builder()
                .setApplication((Application) reactActivity.getApplicationContext())
                .setJSMainModuleName(reactActivity.getJSMainModuleName())
                .setUseDeveloperSupport(reactActivity.getUseDeveloperSupport())
                .setInitialLifecycleState(LifecycleState.BEFORE_RESUME);

        for (ReactPackage reactPackage : reactActivity.getPackages()) {
            builder.addPackage(reactPackage);
        }

        String jsBundleFile = reactActivity.getJSBundleFile();

        if (jsBundleFile != null) {
            builder.setJSBundleFile(jsBundleFile);
        } else {
            builder.setBundleAssetName(reactActivity.getBundleAssetName());
        }

        mReactManager = builder.build();
        return mReactManager;
    }

    public <T extends ReactContextBaseJavaModule> T getNativeModule(Class<T> nativeModuleClass) {
        if (mReactManager == null || mReactManager.getCurrentReactContext() == null) {
            return null;
        }

        return mReactManager.getCurrentReactContext().getNativeModule(nativeModuleClass);
    }

    /**
     * Sends an event to JavaScript using <a href="https://facebook.github.io/react-native/docs/native-modules-android.html#sending-events-to-javascript">RCTDeviceEventEmitter</a>
     * @param eventName Name of the event
     * @param params Event params
     * @param screen screen which should receive the event
     */
    public void sendEvent(String eventName, Screen screen, WritableMap params) {
        RCTDeviceEventEmitter eventEmitter = getEventEmitter();
        if (eventEmitter == null) {
            return;
        }

        params.putString(KEY_EVENT_ID, eventName);
        params.putString(Screen.KEY_NAVIGATOR_EVENT_ID, screen.navigatorEventId);
        eventEmitter.emit(screen.navigatorEventId, params);
    }

    private RCTDeviceEventEmitter getEventEmitter() {
        if (mReactManager == null) {
            return null;
        }

        ReactContext currentReactContext = mReactManager.getCurrentReactContext();
        if (currentReactContext == null) {
            return null;
        }

        return currentReactContext.getJSModule(RCTDeviceEventEmitter.class);
    }

    public void onDestroy() {
        mReactManager = null;
        sInstance = null;
    }
}

