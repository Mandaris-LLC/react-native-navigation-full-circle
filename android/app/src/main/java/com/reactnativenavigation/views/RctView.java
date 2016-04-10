package com.reactnativenavigation.views;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactRootView;
import com.reactnativenavigation.activities.BaseReactActivity;
import com.reactnativenavigation.core.objects.Screen;

/**
 * Created by guyc on 10/03/16.
 */
public class RctView extends FrameLayout {

    public RctView(BaseReactActivity ctx, ReactInstanceManager rctInstanceManager, Screen screen) {
        super(ctx);
        setLayoutParams(new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

        ReactRootView root = new ReactRootView(ctx);
        root.setLayoutParams(new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

        String componentName = screen.screenId;
        Bundle passProps = new Bundle();
        passProps.putString(Screen.KEY_SCREEN_INSTANCE_ID, screen.screenInstanceId);
        passProps.putString(Screen.KEY_STACK_ID, screen.stackId);
        passProps.putString(Screen.KEY_NAVIGATOR_ID, screen.navigatorId);
        passProps.putString(Screen.KEY_NAVIGATOR_EVENT_ID, screen.navigatorEventId);

        root.startReactApplication(rctInstanceManager, componentName, passProps);

        addView(root);

        rctInstanceManager.onResume(ctx, ctx);
    }
}

