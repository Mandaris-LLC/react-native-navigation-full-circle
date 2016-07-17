package com.reactnativenavigation.activities;

import android.os.Bundle;

public class NavigationActivity extends BaseReactActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startReactContextOnceInBackgroundAndExecuteJS();
    }

    private void startReactContextOnceInBackgroundAndExecuteJS() {
        if (!getReactInstanceManager().hasStartedCreatingInitialContext()) {
            getReactInstanceManager().createReactContextInBackground();
        }
    }

    @Override
    protected String getCurrentNavigatorId() {
        return null;
    }

    @Override
    public int getScreenStackSize() {
        return 0;
    }

    @Override
    protected void removeAllReactViews() {

    }
}
