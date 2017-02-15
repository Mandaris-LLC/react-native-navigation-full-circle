package com.reactnativenavigation.controllers;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
import com.facebook.react.shell.MainReactPackage;
import com.reactnativenavigation.react.NavigationEventEmitter;
import com.reactnativenavigation.react.NavigationPackage;
import com.reactnativenavigation.react.ReactDevPermission;
import com.reactnativenavigation.views.NavigationSplashView;

import java.util.Arrays;
import java.util.List;

public class NavigationActivity extends AppCompatActivity implements DefaultHardwareBackBtnHandler {
    private ReactNativeHost host;
    private View contentView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new NavigationSplashView(this));

        host = new ReactNativeHost(getApplication()) {
            @Override
            protected boolean getUseDeveloperSupport() {
                return NavigationApplication.instance.isDebug();
            }

            @Override
            protected List<ReactPackage> getPackages() {
                return Arrays.asList(
                        new MainReactPackage(),
                        new NavigationPackage(NavigationActivity.this)
                );
            }
        };

        host.getReactInstanceManager().addReactInstanceEventListener(new ReactInstanceManager.ReactInstanceEventListener() {
            @Override
            public void onReactContextInitialized(ReactContext context) {
                // TODO should we implement this line also if context already exists onCreate?
                new NavigationEventEmitter(context).emitAppLaunched();
            }
        });
    }

    @Override
    public void setContentView(View contentView) {
        super.setContentView(contentView);
        this.contentView = contentView;
    }

    @Nullable
    public View getContentView() {
        return contentView;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ReactDevPermission.shouldAskPermission()) {
            ReactDevPermission.askPermission(this);
        } else {
            host.getReactInstanceManager().createReactContextInBackground();
            host.getReactInstanceManager().onHostResume(this, this);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (host.getReactInstanceManager().hasStartedCreatingInitialContext()) {
            host.getReactInstanceManager().onHostPause(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (host.getReactInstanceManager().hasStartedCreatingInitialContext()) {
            host.getReactInstanceManager().onHostDestroy(this);
        }
    }

    @Override
    public void invokeDefaultOnBackPressed() {
        onBackPressed();
    }

    public ReactNativeHost getHost() {
        return host;
    }
}
