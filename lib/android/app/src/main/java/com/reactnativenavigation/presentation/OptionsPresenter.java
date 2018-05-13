package com.reactnativenavigation.presentation;

import android.app.Activity;
import android.view.View;

import com.reactnativenavigation.parse.Options;
import com.reactnativenavigation.parse.OrientationOptions;

public class OptionsPresenter {

    private Activity activity;

    public OptionsPresenter(Activity activity) {
        this.activity = activity;
    }

    public void present(View view, Options options) {
        applyOrientation(options.orientationOptions);
        applyViewOptions(view, options);
    }

    private void applyOrientation(OrientationOptions options) {
        activity.setRequestedOrientation(options.getValue());
    }

    private void applyViewOptions(View view, Options options) {
        if (options.screenBackgroundColor.hasValue()) {
            view.setBackgroundColor(options.screenBackgroundColor.get());
        }
    }

}
