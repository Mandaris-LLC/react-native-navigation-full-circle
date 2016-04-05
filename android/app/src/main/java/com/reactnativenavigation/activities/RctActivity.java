package com.reactnativenavigation.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactRootView;
import com.reactnativenavigation.R;
import com.reactnativenavigation.core.RctManager;

/**
 *
 * Created by guyc on 10/03/16.
 */
public class RctActivity extends BaseReactActivity {
    public static final String EXTRA_COMPONENT_NAME = "componentName";
    private static final String TAG = "RctActivity";

    private String mComponentName = "";

    public void setComponentName(String componentName) {
        mComponentName = componentName;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (mComponentName == null) {
            Intent intent = getIntent();
            assert intent != null;
            mComponentName = intent.getStringExtra(EXTRA_COMPONENT_NAME);
            assert mComponentName != null;
        }

        super.onCreate(savedInstanceState);
    }

    @Override
    protected String getMainComponentName() {
        return mComponentName;
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
        return false;
    }

    @Override
    protected ReactInstanceManager createReactInstanceManager() {
        return getReactInstanceManager();
    }

    protected ReactInstanceManager getReactInstanceManager() {
        RctManager rctManager = RctManager.getInstance();
        if (!rctManager.isInitialized()) {
            rctManager.init(this, mComponentName, getPackages());
        }
        return rctManager.getReactInstanceManager();
    }

    @Override
    protected void onResume() {
        Log.v(TAG, "onResume component: " + mComponentName);
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
