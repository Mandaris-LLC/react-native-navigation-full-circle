package com.reactnativenavigation.controllers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
import com.facebook.react.shell.MainReactPackage;
import com.reactnativenavigation.R;
import com.reactnativenavigation.layout.StackLayout;
import com.reactnativenavigation.react.NavigationEventEmitter;
import com.reactnativenavigation.react.NavigationPackage;
import com.reactnativenavigation.react.ReactDevPermission;

import java.util.Arrays;
import java.util.List;

public class NavigationActivity extends AppCompatActivity implements DefaultHardwareBackBtnHandler {
    public static NavigationActivity instance;
    private ReactNativeHost host;
    private StackLayout root;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
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

        host.getReactInstanceManager().addReactInstanceEventListener(new ReactInstanceManager.ReactInstanceEventListener() {
            @Override
            public void onReactContextInitialized(ReactContext context) {
                new NavigationEventEmitter(context).emitAppLaunched();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ReactDevPermission.shouldAskPermission()) {
            ReactDevPermission.askPermission(this);
        } else {
            host.getReactInstanceManager().createReactContextInBackground();
        }
        host.getReactInstanceManager().onHostResume(this, this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        host.getReactInstanceManager().onHostPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        instance = null;
        host.getReactInstanceManager().onHostDestroy(this);
    }

    @Override
    public void invokeDefaultOnBackPressed() {
        onBackPressed();
    }

    public ReactNativeHost getHost() {
        return host;
    }

    public void setRoot(StackLayout rootView) {
        this.root = rootView;
        setContentView(rootView.asView());
    }

    public void push(View view) {
        root.push(view);
    }

    public void pop() {
        root.pop();
    }
}
