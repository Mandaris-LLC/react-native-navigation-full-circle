package com.reactnativenavigation.views;

import android.widget.FrameLayout;

import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactRootView;
import com.reactnativenavigation.activities.BaseReactActivity;


/**
 *
 * Created by guyc on 10/03/16.
 */
public class RctView extends FrameLayout {

    public RctView(BaseReactActivity ctx, ReactInstanceManager rctInstanceManager, String componentName) {
        super(ctx);
        setLayoutParams(new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

        ReactRootView root = new ReactRootView(ctx);
        root.setLayoutParams(new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        root.startReactApplication(rctInstanceManager, componentName, null);
        addView(root);

        rctInstanceManager.onResume(ctx, ctx);
    }
}

