package com.reactnativenavigation.views;

import android.support.design.widget.Snackbar;
import android.view.View;

import com.reactnativenavigation.NavigationApplication;
import com.reactnativenavigation.params.SnackbarParams;

public class Snakbar {
    private final View parent;
    private final String navigatorEventId;
    private final SnackbarParams params;

    public Snakbar(View parent, String navigatorEventId, SnackbarParams params) {
        this.parent = parent;
        this.navigatorEventId = navigatorEventId;
        this.params = params;
    }

    public void show() {
        Snackbar snackbar = Snackbar.make(parent, params.text, params.duration);
        setAction(navigatorEventId, params, snackbar);
        setStyle(snackbar, params);
        snackbar.show();
    }

    private void setAction(final String navigatorEventId, final SnackbarParams params, Snackbar snackbar) {
        if (params.eventId != null) {
            snackbar.setAction(params.buttonText, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NavigationApplication.instance.sendNavigatorEvent(params.eventId, navigatorEventId);
                }
            });
        }
    }

    private void setStyle(Snackbar snackbar, SnackbarParams params) {
        if (params.buttonColor.hasColor()) {
            snackbar.setActionTextColor(params.buttonColor.getColor());
        }
    }
}
