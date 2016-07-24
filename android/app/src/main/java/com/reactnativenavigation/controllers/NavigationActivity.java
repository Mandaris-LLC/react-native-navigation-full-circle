package com.reactnativenavigation.controllers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;

import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
import com.reactnativenavigation.NavigationApplication;
import com.reactnativenavigation.layouts.Layout;
import com.reactnativenavigation.layouts.SingleScreenLayout;
import com.reactnativenavigation.params.ActivityParams;
import com.reactnativenavigation.params.parsers.ActivityParamsParser;
import com.reactnativenavigation.react.JsDevReloadHandler;
import com.reactnativenavigation.react.NavigationReactInstance;
import com.reactnativenavigation.react.RedboxPermission;

public class NavigationActivity extends AppCompatActivity implements DefaultHardwareBackBtnHandler, NavigationReactInstance.OnJsDevReloadListener {

    public static final String PARAMS_BUNDLE = "PARAMS_BUNDLE";
    /**
     * Although we start multiple activities, we make sure to pass Intent.CLEAR_TASK | Intent.NEW_TASK
     * So that we actually have only 1 instance of the activity running at one time.
     * We hold the currentActivity (resume->pause) so we know when we need to destroy the javascript context.
     * This is somewhat weird, and in the future either fully support multiple activities,
     * OR a single activity with changing contentView similar to ReactNative impl.
     */
    private static Activity currentActivity;
    private ActivityParams activityParams;
    private ModalController modalController;
    private Layout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityParams = ActivityParamsParser.parse(getIntent().getBundleExtra(PARAMS_BUNDLE));

        modalController = new ModalController();

        RedboxPermission.permissionToShowRedboxIfNeeded(this);
        createLayout();
    }

    private void createLayout() {
        //TODO layout factory (tabsLayout etc)
        layout = new SingleScreenLayout(this, activityParams.screenParams);
        setContentView((View) layout);
    }

    @Override
    protected void onResume() {
        super.onResume();
        currentActivity = this;
        getNavigationReactInstance().onResume(this, this, this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        currentActivity = null;
        getNavigationReactInstance().onPause();
    }

    @Override
    protected void onDestroy() {
        modalController.onDestroy();
        layout.onDestroy();
        super.onDestroy();
        if (currentActivity == null || currentActivity.isFinishing()) {
            getNavigationReactInstance().onHostDestroy();
        }
    }

    @Override
    public void onJsDevReload() {
        layout.removeAllReactViews();
    }

    @Override
    public void invokeDefaultOnBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onBackPressed() {
        if (modalController.onBackPressed()) {
            return;
        }
        if (layout.onBackPressed()) {
            return;
        }
        getNavigationReactInstance().onBackPressed();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        getNavigationReactInstance().onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return JsDevReloadHandler.onKeyUp(getCurrentFocus(), keyCode) || super.onKeyUp(keyCode, event);
    }

    private NavigationReactInstance getNavigationReactInstance() {
        return NavigationApplication.instance.getNavigationReactInstance();
    }
}
