package com.reactnativenavigation.activities;

import com.reactnativenavigation.BuildConfig;

/**
 * Base Activity for React Native applications.
 *
 * Created by guyc on 02/04/16.
 */
public class CompoundReactActivity extends BaseReactActivity {
    @Override
    protected String getMainComponentName() {
        return "";
    }

    @Override
    protected boolean getUseDeveloperSupport() {
        return BuildConfig.DEBUG;
    }
}

