package com.reactnativenavigation.events;

public class ScreenChangedEvent implements Event {
    public static final String TYPE = "ScreenChangedEvent";

    @Override
    public String getType() {
        return TYPE;
    }
}
