package com.reactnativenavigation.packages;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.JavaScriptModule;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;
import com.reactnativenavigation.managers.AppBarLayoutManager;
import com.reactnativenavigation.managers.CoordinatorLayoutManager;
import com.reactnativenavigation.managers.TabLayoutManager;
import com.reactnativenavigation.managers.ToolbarManager;
import com.reactnativenavigation.modules.RctActivityModule;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 *
 * Created by guyc on 07/03/16.
 */
public class RCCPackage implements ReactPackage {

    @Override
    public List<NativeModule> createNativeModules(ReactApplicationContext reactContext) {
        return Arrays.<NativeModule>asList(
                new RctActivityModule(reactContext)
        );
    }

    @Override
    public List<Class<? extends JavaScriptModule>> createJSModules() {
        return Collections.emptyList();
    }

    @Override
    public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
        return Arrays.<ViewManager>asList(
                new TabLayoutManager(),
                new AppBarLayoutManager(),
                new CoordinatorLayoutManager(),
                new ToolbarManager()
        );
    }
}
