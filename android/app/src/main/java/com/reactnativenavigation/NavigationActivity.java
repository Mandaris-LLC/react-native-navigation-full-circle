package com.reactnativenavigation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
import com.reactnativenavigation.views.NavigationSplashView;

public class NavigationActivity extends AppCompatActivity implements DefaultHardwareBackBtnHandler {
    private View contentView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new NavigationSplashView(this));
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
    public void invokeDefaultOnBackPressed() {
        onBackPressed();
    }

    public long splashMinimumDuration() {
        return 1000;
    }
}
