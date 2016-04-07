package com.reactnativenavigation.core;

import android.app.Application;
import android.content.Context;

import com.facebook.react.LifecycleState;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.WritableMap;

import java.util.List;

import static com.facebook.react.modules.core.DeviceEventManagerModule.RCTDeviceEventEmitter;

/**
 * Created by guyc on 22/02/16.
 */
public class RctManager {
    private static final String TAG = "RctManager";
    private static final String KEY_EVENT_ID = "id";
    private static RctManager sInstance;

    private ReactInstanceManager mReactManager;
    private boolean mUseLocalDevServer = true;

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

    public void init(Context context, String componentName, List<ReactPackage> packages) {
        createReactInstanceManager(context, componentName, packages);
    }

    /**
     * Creates a React Instance Manager associated with this component name
     * @param context
     * @param jsBundleName
     * @param packages
     */
    public ReactInstanceManager createReactInstanceManager(Context context, String jsBundleName, List<ReactPackage> packages) {
        // Get component name
        ReactInstanceManager.Builder builder = ReactInstanceManager.builder()
                .setApplication((Application) context.getApplicationContext())
                .setUseDeveloperSupport(true)
                .setInitialLifecycleState(LifecycleState.BEFORE_RESUME);
        for (ReactPackage reactPackage : packages) {
            builder.addPackage(reactPackage);
        }
        if (mUseLocalDevServer) {
            // Set module name to be loaded from local node server
            builder.setJSMainModuleName("index.android");
            builder.setBundleAssetName("index.android.bundle");
        } else {
            throw new RuntimeException("Implement me!");
            //            // Get the bundle uri
            //            String confResourceName = AssetManager.getConfUrlResourceName(jsBundleName);
            //            Context context = AppDelegate.sharedApplication().getApplicationContext();
            //            Uri uri =
            //                    AssetManager.sharedAssetManager().getConfResourceUrl(confResourceName, context);
            //            // Load bundled jsBundle
            //            builder.setJSBundleFile(uri.getPath());
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
     * @param navigatorEventId Id of the navigator
     */
    public void sendEvent(String eventName, String navigatorEventId, WritableMap params) {
        RCTDeviceEventEmitter eventEmitter = getEventEmitter();
        if (eventEmitter == null) {
            return;
        }

        params.putString(KEY_EVENT_ID, eventName);
        eventEmitter.emit(navigatorEventId, params);
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
}

