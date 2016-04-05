package com.example;


import com.reactnativenavigation.activities.RctActivity;

public class MainActivity extends RctActivity {

    @Override
    protected String getMainComponentName() {
        return "App";
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
