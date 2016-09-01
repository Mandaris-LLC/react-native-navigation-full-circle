package com.reactnativenavigation.events;

import android.content.Intent;

public class ScreenChangeBroadcast extends LocalBroadcastEvent {
    public static final String ACTION = "screenChange";

    @Override
    public Intent getIntent() {
        return new Intent(ACTION);
    }
}
