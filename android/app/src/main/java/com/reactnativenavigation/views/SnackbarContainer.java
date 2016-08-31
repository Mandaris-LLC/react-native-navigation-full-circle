package com.reactnativenavigation.views;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;

import com.reactnativenavigation.params.SnackbarParams;

public class SnackbarContainer extends CoordinatorLayout {

    public SnackbarContainer(Context context) {
        super(context);
    }

    public void addSnackbar(SnackbarParams params) {
        Snackbar snackbar = Snackbar.make(this, params.text, params.duration);
        snackbar.show();
    }
}
