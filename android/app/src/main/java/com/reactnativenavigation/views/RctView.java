package com.reactnativenavigation.views;

import android.os.Bundle;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactRootView;
import com.reactnativenavigation.activities.BaseReactActivity;
import com.reactnativenavigation.core.objects.Screen;

import java.util.HashMap;

/**
 * Created by guyc on 10/03/16.
 */
public class RctView extends FrameLayout {

    private ReactRootView mReactRootView;

    /**
     * Interface used to run some code when the {@link ReactRootView} is visible.
     */
    public interface OnDisplayedListener {
        /**
         * This method will be invoked when the {@link ReactRootView} is visible.
         */
        public void onDisplayed();
    }

    public ReactRootView getReactRootView() {
        return mReactRootView;
    }

    public RctView(BaseReactActivity ctx, ReactInstanceManager rctInstanceManager, Screen screen) {
        this(ctx, rctInstanceManager, screen, null);
    }

    public RctView(BaseReactActivity ctx, ReactInstanceManager rctInstanceManager, Screen screen,
                   final OnDisplayedListener onDisplayedListener) {
        super(ctx);
        setLayoutParams(new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

        mReactRootView = new ReactRootView(ctx);
        mReactRootView.setLayoutParams(new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

        String componentName = screen.screenId;
        Bundle passProps = new Bundle();
        passProps.putString(Screen.KEY_SCREEN_INSTANCE_ID, screen.screenInstanceId);
        passProps.putString(Screen.KEY_NAVIGATOR_ID, screen.navigatorId);
        passProps.putString(Screen.KEY_NAVIGATOR_EVENT_ID, screen.navigatorEventId);
        if (screen.passedProps != null) {
            spreadHashMap(screen.passedProps, passProps);
        }

        mReactRootView.startReactApplication(rctInstanceManager, componentName, passProps);

        if (onDisplayedListener != null) {
            mReactRootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    onDisplayedListener.onDisplayed();
                    mReactRootView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            });
        }

        addView(mReactRootView);
    }

    private Bundle spreadHashMap(HashMap<String, Object> map, Bundle bundle) {
        for (String key : map.keySet()) {
            Object value = map.get(key);
            if (value instanceof String) {
                bundle.putString(key, (String) value);
            } else if (value instanceof Integer) {
                bundle.putInt(key, (Integer) value);
            } else if (value instanceof Double) {
                bundle.putDouble(key, ((Double) value).doubleValue());
            } else if (value instanceof Boolean) {
                bundle.putBoolean(key, (Boolean) value);
            } else if (value instanceof HashMap) {
                bundle.putBundle(key, spreadHashMap((HashMap<String, Object>) value, new Bundle()));
                //TODO nulls and Arrays needed
            }
        }
        return bundle;
    }
}

