package com.reactnativenavigation.bridge;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.reactnativenavigation.controllers.NavigationCommandsHandler;
import com.reactnativenavigation.params.TitleBarButtonParams;
import com.reactnativenavigation.params.parsers.TitleBarButtonParamsParser;

import java.util.List;

/**
 * The basic abstract components we will expose:
 * BottomTabs (app) - boolean
 * TopBar (per screen)
 * - TitleBar
 * - TopTabs (segmented control / view pager)
 * DeviceStatusBar (app) (colors are per screen)
 * AndroidNavigationBar (app) (colors are per screen)
 * SideMenu (app) - boolean
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
        NavigationCommandsHandler.startApp(BundleConverter.toBundle(params));
    }

    @ReactMethod
    public void setScreenTitleBarTitle(String screenInstanceId, String title) {
        NavigationCommandsHandler.setScreenTitleBarTitle(screenInstanceId, title);
    }

    @ReactMethod
    public void setScreenTitleBarButtons(String screenInstanceId, String navigatorEventId, ReadableArray buttons) {
        List<TitleBarButtonParams> titleBarButtons = TitleBarButtonParamsParser.parse(BundleConverter.toBundle(buttons));
        NavigationCommandsHandler.setScreenTitleBarButtons(screenInstanceId, navigatorEventId, titleBarButtons);
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
    public void setTopBarVisible(String screenInstanceID, boolean hidden, boolean animated) {
        NavigationCommandsHandler.setTopBarVisible(screenInstanceID, hidden, animated);
    }

    @ReactMethod
    public void toggleBottomTabsVisible(final ReadableMap params) {
    }

    @ReactMethod
    public void setBottomTabsVisible(final ReadableMap params) {
    }

    @ReactMethod
    public void push(final ReadableMap params) {
        NavigationCommandsHandler.push(BundleConverter.toBundle(params));
    }

    @ReactMethod
    public void pop(final ReadableMap params) {
        NavigationCommandsHandler.pop(BundleConverter.toBundle(params));
    }

    @ReactMethod
    public void popToRoot(final ReadableMap params) {
        NavigationCommandsHandler.popToRoot(BundleConverter.toBundle(params));
    }

    @ReactMethod
    public void newStack(final ReadableMap params) {
        NavigationCommandsHandler.newStack(BundleConverter.toBundle(params));
    }

    @ReactMethod
    public void showModal(final ReadableMap params) {
        NavigationCommandsHandler.showModal(BundleConverter.toBundle(params));
    }

    @ReactMethod
    public void dismissAllModals(final ReadableMap params) {
    }

    @ReactMethod
    public void dismissTopModal() {
        NavigationCommandsHandler.dismissTopModal();
    }
}
