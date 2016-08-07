package com.reactnativenavigation.controllers;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.reactnativenavigation.NavigationApplication;

public abstract class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NavigationApplication.instance.startReactContext();
        setSplashLayout();
    }

    private void setSplashLayout() {
        final int splashLayout = getSplashLayout();
        if (splashLayout > 0) {
            setContentView(splashLayout);
        } else {
            setDefaultSplashLayout();
        }
    }

    private void setDefaultSplashLayout() {
        View view = new View(this);
        view.setBackgroundColor(Color.WHITE);
        setContentView(view);
    }

    /**
     * 
     * @return -1 if you don't need a splash layout
     */
    public abstract @LayoutRes int getSplashLayout();
}
