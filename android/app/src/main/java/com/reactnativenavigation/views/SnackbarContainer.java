package com.reactnativenavigation.views;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;

import com.reactnativenavigation.params.SnackbarParams;

public class SnackbarContainer extends CoordinatorLayout {

    public SnackbarContainer(Context context) {
        super(context);
    }

    public void showSnackbar(final String navigatorEventId, final SnackbarParams params) {
        new Snakbar(this, navigatorEventId, params).show();
    }
}
