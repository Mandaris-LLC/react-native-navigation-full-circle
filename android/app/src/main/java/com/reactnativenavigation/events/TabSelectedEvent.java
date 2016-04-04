package com.reactnativenavigation.events;

import android.os.SystemClock;
import android.util.Log;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;

/**
 *
 * Created by guyc on 07/03/16.
 */
public class TabSelectedEvent extends Event<TabSelectedEvent> {
    public static final String EVENT_NAME = "tabSelected";
    private static final String KEY_POSITION = "position";

    private int mPosition;

    public TabSelectedEvent(int viewTag, int position) {
        super(viewTag, SystemClock.uptimeMillis());
        mPosition = position;
    }

    @Override
    public String getEventName() {
        return EVENT_NAME;
    }

    @Override
    public void dispatch(RCTEventEmitter rctEventEmitter) {
        Log.d("GUY", "DIspatching event: " + getViewTag());
        rctEventEmitter.receiveEvent(getViewTag(), EVENT_NAME, serializeData());
    }

    private WritableMap serializeData() {
        WritableMap data = Arguments.createMap();
        data.putInt(KEY_POSITION, mPosition);
        return data;
    }
}
