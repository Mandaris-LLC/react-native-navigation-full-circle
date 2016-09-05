package com.reactnativenavigation.views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;

import com.reactnativenavigation.events.Event;
import com.reactnativenavigation.events.EventBus;
import com.reactnativenavigation.events.ScreenChangedEvent;
import com.reactnativenavigation.events.Subscriber;
import com.reactnativenavigation.params.FabParams;
import com.reactnativenavigation.params.SnackbarParams;

public class SnackbarAndFabContainer extends CoordinatorLayout implements Snakbar.OnDismissListener, Subscriber{
    private Snakbar snakbar;
    private FloatingActionButtonCoordinator actionButtonCoordinator;

    public SnackbarAndFabContainer(Context context) {
        super(context);
        EventBus.instance.register(this);
    }

    public void showSnackbar(final String navigatorEventId, final SnackbarParams params) {
        snakbar = new Snakbar(this, navigatorEventId, params);
        snakbar.show();
    }

    public void onScreenChange() {
        if (snakbar != null) {
            snakbar.dismiss();
            snakbar = null;
        }
    }

    @Override
    public void onDismiss(Snakbar snakbar) {
        if (this.snakbar == snakbar) {
            this.snakbar = null;
        }
    }

    public void destroy() {
        EventBus.instance.unregister(this);
    }

    public void showFab(@NonNull FabParams fabParams) {
        actionButtonCoordinator = new FloatingActionButtonCoordinator(this, fabParams);
    }

    @Override
    public void onEvent(Event event) {
        if (event.getType() == ScreenChangedEvent.TYPE) {
            onScreenChange();
        }
    }
}
