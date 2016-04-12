package com.reactnativenavigation.activities;

import com.facebook.react.BuildConfig;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactRootView;
import com.reactnativenavigation.R;
import com.reactnativenavigation.core.RctManager;
import com.reactnativenavigation.core.objects.Screen;

/**
 * Created by guyc on 13/04/16.
 */
public class RootActivity extends BaseReactActivity {

    /**
     * Returns the name of the main component registered from JavaScript.
     * This is used to schedule rendering of the component.
     */
    @Override
    protected String getMainComponentName() {
        return "RootComponent";
    }

    @Override
    protected ReactRootView createRootView() {
        ReactRootView rootView = super.createRootView();
        rootView.setId(R.id.react_root_view);
        return rootView;
    }

    /**
     * Returns whether dev mode should be enabled.
     * This enables e.g. the dev menu.
     */
    @Override
    protected boolean getUseDeveloperSupport() {
        return BuildConfig.DEBUG;
    }

    @Override
    protected ReactInstanceManager createReactInstanceManager() {
        return getReactInstanceManager();
    }

    protected ReactInstanceManager getReactInstanceManager() {
        RctManager rctManager = RctManager.getInstance();
        if (!rctManager.isInitialized()) {
            rctManager.init(this, getMainComponentName(), getPackages());
        }
        return rctManager.getReactInstanceManager();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    public void push(Screen screen) {
        //TODO
    }
}
