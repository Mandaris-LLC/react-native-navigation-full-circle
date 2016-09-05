package com.reactnativenavigation.events;

import com.reactnativenavigation.params.ScreenParams;

public class ScreenChangedEvent implements Event {
    public static final String TYPE = "ScreenChangedEvent";
    private ScreenParams screenParams;

    public ScreenChangedEvent(ScreenParams screenParams) {
        this.screenParams = screenParams;
    }

    public ScreenParams getScreenParams() {
        return screenParams;
    }

    @Override
    public String getType() {
        return TYPE;
    }
}
