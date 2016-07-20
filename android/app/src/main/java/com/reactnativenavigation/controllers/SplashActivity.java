package com.reactnativenavigation.controllers;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public abstract class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO show fancy splash
        Toast.makeText(this, "Loading navigation...", Toast.LENGTH_SHORT).show();
    }
}
