package com.reactnativenavigation.bridge;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableNativeMap;
import com.reactnativenavigation.activities.BaseReactActivity;
import com.reactnativenavigation.activities.BottomTabActivity;
import com.reactnativenavigation.activities.RootActivity;
import com.reactnativenavigation.activities.SingleScreenActivity;
import com.reactnativenavigation.controllers.ModalController;
import com.reactnativenavigation.core.objects.Drawer;
import com.reactnativenavigation.core.objects.Screen;
import com.reactnativenavigation.utils.BridgeUtils;
import com.reactnativenavigation.utils.ContextProvider;

import java.util.ArrayList;

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
    public void startTabBasedApp(ReadableArray screens, ReadableMap style, ReadableMap drawerParams) {
        Activity context = ContextProvider.getActivityContext();
        if (context != null && !context.isFinishing()) {
            Intent intent = new Intent(context, BottomTabActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

            Bundle extras = new Bundle();
            extras.putSerializable(BottomTabActivity.EXTRA_SCREENS, createScreens(screens));
            if (drawerParams != null) {
                extras.putSerializable(BottomTabActivity.DRAWER_PARAMS, new Drawer(drawerParams));
            }
            if (style != null) {
                BridgeUtils.addMapToBundle(((ReadableNativeMap) style).toHashMap(), extras);
            }
            intent.putExtras(extras);

            context.startActivity(intent);
            //TODO add abstract isRoot() instead of instanceof?
            if (ContextProvider.getActivityContext() instanceof RootActivity) {
                context.overridePendingTransition(0, 0);
            }

            // Dismiss modals associated with previous activity
            ModalController.getInstance().dismissAllModals();
        }
    }

    private ArrayList<Screen> createScreens(ReadableArray screens) {
        ArrayList<Screen> ret = new ArrayList<>();
        for (int i = 0; i < screens.size(); i++) {
            ret.add(new Screen(screens.getMap(i)));
        }
        return ret;
    }

    @ReactMethod
    public void startSingleScreenApp(ReadableMap screen, ReadableMap drawerParams) {
        BaseReactActivity context = ContextProvider.getActivityContext();
        if (context != null && !context.isFinishing()) {
            Intent intent = new Intent(context, SingleScreenActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

            Bundle extras = new Bundle();
            extras.putSerializable(SingleScreenActivity.EXTRA_SCREEN, new Screen(screen));
            if (drawerParams != null) {
                extras.putSerializable(SingleScreenActivity.DRAWER_PARAMS, new Drawer(drawerParams));
            }
            intent.putExtras(extras);

            context.startActivity(intent);
            if (ContextProvider.getActivityContext() instanceof RootActivity) {
                context.overridePendingTransition(0, 0);
            }

            // Dismiss modals associated with previous activity
            ModalController.getInstance().dismissAllModals();
        }
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
