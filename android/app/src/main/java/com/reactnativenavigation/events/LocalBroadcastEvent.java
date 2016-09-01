package com.reactnativenavigation.events;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.reactnativenavigation.NavigationApplication;

public abstract class LocalBroadcastEvent {

    public abstract Intent getIntent();

    public void send() {
        LocalBroadcastManager.getInstance(NavigationApplication.instance).sendBroadcast(getIntent());
    }

}
