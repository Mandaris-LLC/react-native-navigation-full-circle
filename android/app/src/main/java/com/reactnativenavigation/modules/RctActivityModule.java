package com.reactnativenavigation.modules;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.reactnativenavigation.activities.RctActivity;
import com.reactnativenavigation.activities.SingleScreenActivity;
import com.reactnativenavigation.activities.TabActivity;
import com.reactnativenavigation.core.Screen;
import com.reactnativenavigation.utils.ContextProvider;

import java.util.ArrayList;

/**
 * Created by guyc on 10/03/16.
 */
public class RctActivityModule extends ReactContextBaseJavaModule {
    public static final String REACT_CLASS = "RctActivity";

    public RctActivityModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @ReactMethod
    public void startActivity(String componentName) {
        Activity context = ContextProvider.getActivityContext();
        if (context != null && !context.isFinishing()) {
            Intent rctActivityIntent = new Intent(context, RctActivity.class);
            rctActivityIntent.putExtra(RctActivity.EXTRA_COMPONENT_NAME, componentName);
            context.startActivity(rctActivityIntent);
        }
    }

    @ReactMethod
    public void startTabBasedApp(ReadableArray screens) {
        Activity context = ContextProvider.getActivityContext();
        if (context != null && !context.isFinishing()) {
            Intent intent = new Intent(context, TabActivity.class);

            Bundle extras = new Bundle();
            extras.putSerializable(TabActivity.EXTRA_SCREENS, createScreens(screens));
            intent.putExtras(extras);

            context.startActivity(intent);
        }
    }

    private ArrayList<Screen> createScreens(ReadableArray screens) {
        ArrayList<Screen> ret = new ArrayList<>();
        for(int i = 0; i < screens.size(); i++) {
            ret.add(new Screen(screens.getMap(i)));
        }
        return ret;
    }

    @ReactMethod
    public void startSingleScreenApp(ReadableMap screen) {
        navigatorPush(screen);
    }

    @ReactMethod
    public void navigatorPush(ReadableMap screen) {
        Activity context = ContextProvider.getActivityContext();
        if (context != null && !context.isFinishing()) {
            Intent intent = new Intent(context, SingleScreenActivity.class);

            Bundle extras = new Bundle();
            extras.putSerializable(SingleScreenActivity.EXTRA_SCREEN, new Screen(screen));
            intent.putExtras(extras);

            context.startActivity(intent);
        }
    }
}
