package com.reactnativenavigation.controllers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
import com.facebook.react.shell.MainReactPackage;
import com.reactnativenavigation.R;
import com.reactnativenavigation.react.NavigationEventEmitter;
import com.reactnativenavigation.react.NavigationPackage;
import com.reactnativenavigation.react.ReactDevPermission;

import java.util.Arrays;
import java.util.List;

public class NavigationActivity extends AppCompatActivity implements DefaultHardwareBackBtnHandler {

    private ReactNativeHost host;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        host = new ReactNativeHost(getApplication()) {
            @Override
            protected boolean getUseDeveloperSupport() {
                return NavigationApplication.instance.isDebug();
            }

            @Override
            protected List<ReactPackage> getPackages() {
                return Arrays.asList(
                        new MainReactPackage(),
                        new NavigationPackage()
                );
            }
        };

        if (ReactDevPermission.shouldAskPermission()) {
            ReactDevPermission.askPermission(this);
            return;
        }

        host.getReactInstanceManager().addReactInstanceEventListener(new ReactInstanceManager.ReactInstanceEventListener() {
            @Override
            public void onReactContextInitialized(ReactContext context) {
                new NavigationEventEmitter(context).emitAppLaunched();
            }
        });
        host.getReactInstanceManager().createReactContextInBackground();


    }

    @Override
    protected void onResume() {
        super.onResume();
        host.getReactInstanceManager().onHostResume(this, this);
    }

    @Override
    public void invokeDefaultOnBackPressed() {
        onBackPressed();
    }
}
