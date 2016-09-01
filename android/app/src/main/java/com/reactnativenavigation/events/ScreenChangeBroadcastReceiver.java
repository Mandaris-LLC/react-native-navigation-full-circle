package com.reactnativenavigation.events;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

import com.reactnativenavigation.NavigationApplication;

public class ScreenChangeBroadcastReceiver extends BroadcastReceiver {
    private OnScreenChangeListener onTabSelectedListener;

    public interface OnScreenChangeListener {
        void onScreenChangeListener();
    }

    public ScreenChangeBroadcastReceiver(OnScreenChangeListener onTabSelectedListener) {
        this.onTabSelectedListener = onTabSelectedListener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        onTabSelectedListener.onScreenChangeListener();
    }

    public void register() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ScreenChangeBroadcast.ACTION);
        getBroadcastManager().registerReceiver(this, intentFilter);
    }

    public void unregister() {
        getBroadcastManager().unregisterReceiver(this);
    }

    private LocalBroadcastManager getBroadcastManager() {
        return LocalBroadcastManager.getInstance(NavigationApplication.instance);
    }
}
