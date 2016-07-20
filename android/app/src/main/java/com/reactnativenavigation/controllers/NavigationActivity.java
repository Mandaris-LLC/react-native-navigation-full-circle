package com.reactnativenavigation.controllers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import com.facebook.react.ReactPackage;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
import com.facebook.react.shell.MainReactPackage;
import com.reactnativenavigation.bridge.NavigationReactPackage;
import com.reactnativenavigation.params.ActivityParams;
import com.reactnativenavigation.params.parsers.ActivityParamsParser;
import com.reactnativenavigation.layouts.Layout;
import com.reactnativenavigation.layouts.ScreenLayout;
import com.reactnativenavigation.react.JsDevReloadHandler;
import com.reactnativenavigation.react.NavigationReactInstance;
import com.reactnativenavigation.react.RedboxPermission;

import java.util.Arrays;
import java.util.List;


public class NavigationActivity extends AppCompatActivity implements NavigationReactInstance.ReactContextCreator, DefaultHardwareBackBtnHandler {

    public static final String PARAMS_BUNDLE = "PARAMS_BUNDLE";
    /**
     * Although we start multiple activities, we make sure to pass Intent.CLEAR_TASK | Intent.NEW_TASK
     * So that we actually have only 1 instance of the activity running at one time.
     * We hold the currentActivity (resume->pause) so we know when we need to destroy the javascript context.
     * This is somewhat weird, and in the future either fully support multiple activities, OR a single activity with changing contentView ala ReactNative impl.
     */
    private static Activity currentActivity;
    private NavigationReactInstance navigationReactInstance;
    private ModalController modalController;
    private Layout layout = new Layout() {
        @Override
        public boolean onBackPressed() {
            return false;
        }

        @Override
        public void onDestroy() {

        }

        @Override
        public void removeAllReactViews() {

        }
    };
    private ActivityParams activityParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityParams = ActivityParamsParser.parse(getIntent().getBundleExtra(PARAMS_BUNDLE));

        modalController = new ModalController();
        navigationReactInstance = new NavigationReactInstance(this);
        navigationReactInstance.startReactContextOnceInBackgroundAndExecuteJS();
        RedboxPermission.permissionToShowRedboxIfNeeded(this);
        createLayout();
    }

    private void createLayout() {
        ScreenLayout screenLayout = new ScreenLayout(this, navigationReactInstance.getReactInstanceManager(), activityParams.screenParams);
        setContentView(screenLayout);
    }

    @Override
    protected void onResume() {
        super.onResume();
        currentActivity = this;
        navigationReactInstance.onResume(this, this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        currentActivity = null;
        navigationReactInstance.onPause();
    }

    @Override
    protected void onDestroy() {
//        modalController.onDestroy();
        layout.onDestroy();
        super.onDestroy();
        if (currentActivity == null || currentActivity.isFinishing()) {
            navigationReactInstance.onHostDestroy();
        }
    }


    private NavigationReactInstance getNavigationReactInstance() {
        return navigationReactInstance;
    }

    @Override
    public List<ReactPackage> createReactPackages() {
        return Arrays.asList(
                new MainReactPackage(),
                new NavigationReactPackage()
        );
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
//        if (modalController.onBackPressed()) {
//            return;
//        }
        if (layout.onBackPressed()) {
            return;
        }
        navigationReactInstance.onBackPressed();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        navigationReactInstance.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return JsDevReloadHandler.onKeyUp(navigationReactInstance.getReactInstanceManager(), getCurrentFocus(), keyCode)
                || super.onKeyUp(keyCode, event);
    }
}
