package com.reactnativenavigation.views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.view.Gravity;
import android.view.ViewGroup;

import com.reactnativenavigation.events.ScreenChangeBroadcastReceiver;
import com.reactnativenavigation.params.FabParams;
import com.reactnativenavigation.params.SnackbarParams;
import com.reactnativenavigation.utils.ViewUtils;

public class SnackbarAndFabContainer extends CoordinatorLayout implements Snakbar.OnDismissListener, ScreenChangeBroadcastReceiver.OnScreenChangeListener {
    private static final String TAG = "SnackbarAndFabContainer";
    private Snakbar snakbar;
    private ScreenChangeBroadcastReceiver screenChangeBroadcastReceiver;

    public SnackbarAndFabContainer(Context context) {
        super(context);
        registerTabSelectedReceiver();
    }

    private void registerTabSelectedReceiver() {
        screenChangeBroadcastReceiver = new ScreenChangeBroadcastReceiver(this);
        screenChangeBroadcastReceiver.register();
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
    public void onDismiss() {
        snakbar = null;
    }

    public void destroy() {
        screenChangeBroadcastReceiver.unregister();
    }

    @Override
    public void onScreenChangeListener() {
        onScreenChange();
    }

    public void showFab(@NonNull FabParams fabParams) {
        FloatingActionButton fab = new FloatingActionButton(getContext());
        CoordinatorLayout.LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.RIGHT | Gravity.BOTTOM;
        int margin = (int) ViewUtils.convertDpToPixel(16);
        lp.setMargins(0, 0, margin, margin);
        addView(fab, lp);
    }
}
