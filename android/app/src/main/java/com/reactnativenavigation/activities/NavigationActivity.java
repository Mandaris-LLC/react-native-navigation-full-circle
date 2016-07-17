package com.reactnativenavigation.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import com.facebook.react.ReactPackage;
import com.facebook.react.common.ReactConstants;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
import com.facebook.react.shell.MainReactPackage;
import com.reactnativenavigation.BuildConfig;
import com.reactnativenavigation.bridge.NavigationReactPackage;
import com.reactnativenavigation.controllers.ModalController;
import com.reactnativenavigation.react.JsDevReloadHandler;
import com.reactnativenavigation.react.NavigationReactInstance;

import java.util.Arrays;
import java.util.List;


public class NavigationActivity extends AppCompatActivity implements NavigationReactInstance.ReactContextCreator, DefaultHardwareBackBtnHandler {

    private NavigationReactInstance navigationReactInstance;
    /**
     * Although we start multiple activities, we make sure to pass Intent.CLEAR_TASK | Intent.NEW_TASK
     * So that we actually have only 1 instance of the activity running at one time.
     * We hold the currentActivity (resume->pause) so we know when we need to destroy the javascript context.
     * This is somewhat weird, and in the future either fully support multiple activities, OR a single activity with changing contentView ala ReactNative impl.
     */
    private static Activity currentActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navigationReactInstance = new NavigationReactInstance(this);
        navigationReactInstance.startReactContextOnceInBackgroundAndExecuteJS();
        permissionToShowRedboxIfNeeded();
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
        super.onDestroy();
        if (currentActivity == null || currentActivity.isFinishing()) {
            navigationReactInstance.onHostDestroy();
        }
    }

    private void permissionToShowRedboxIfNeeded() {
        if (BuildConfig.DEBUG && Build.VERSION.SDK_INT >= 23 && !Settings.canDrawOverlays(this)) {
            Intent serviceIntent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
            startActivity(serviceIntent);
            String msg = "Overlay permissions needs to be granted in order for react native apps to run in dev mode";
            Log.w(ReactConstants.TAG, msg);
            Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
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
        if (modalController.onBackPressed()) {
            return;
        }
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
