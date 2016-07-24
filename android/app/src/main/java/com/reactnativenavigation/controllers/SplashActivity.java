package com.reactnativenavigation.controllers;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.reactnativenavigation.NavigationApplication;

public abstract class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NavigationApplication.instance.startReactContext();
        //TODO show fancy splash
        View view = new View(this);
        view.setBackgroundColor(Color.RED);
        setContentView(view);
        Toast.makeText(this, "Loading navigation...", Toast.LENGTH_LONG).show();
    }
}
