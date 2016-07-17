package com.reactnativenavigation.bridge;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;

/**
 * The basic abstract components we will expose:
 * BottomTabs
 * TopBar
 * - TitleBar
 * - TopTabs (segmented control / view pager)
 * DeviceStatusBar
 * AndroidNavigationBar
 * SideMenu
 */
public class NavigationReactModule extends ReactContextBaseJavaModule {
    public static final String NAME = "NavigationReactModule";

    public NavigationReactModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return NAME;
    }

    @ReactMethod
    public void startApp(final ReadableMap params) {
    }

    @ReactMethod
    public void setScreenTitleBarTitle(final ReadableMap params) {
    }

    @ReactMethod
    public void setScreenTitleBarButtons(final ReadableMap params) {
    }

    @ReactMethod
    public void setTabBadge(final ReadableMap params) {
    }

    @ReactMethod
    public void selectBottomTab(final ReadableMap params) {
    }

    @ReactMethod
    public void toggleSideMenuVisible(final ReadableMap params) {
    }

    @ReactMethod
    public void toggleTopBarVisible(final ReadableMap params) {
    }

    @ReactMethod
    public void setTopBarVisible(final ReadableMap params) {
    }

    @ReactMethod
    public void toggleBottomTabsVisible(final ReadableMap params) {
    }

    @ReactMethod
    public void setBottomTabsVisible(final ReadableMap params) {
    }

    @ReactMethod
    public void push(final ReadableMap params) {
    }

    @ReactMethod
    public void pop(final ReadableMap params) {
    }

    @ReactMethod
    public void popToRoot(final ReadableMap params) {
    }

    @ReactMethod
    public void newStack(final ReadableMap params) {
    }

    @ReactMethod
    public void showModal(final ReadableMap params) {
    }

    @ReactMethod
    public void dismissAllModals(final ReadableMap params) {
    }

    @ReactMethod
    public void dismissTopModal() {
    }
}
