package com.reactnativenavigation.events;

import com.reactnativenavigation.params.FabParams;
import com.reactnativenavigation.params.ScreenParams;
import com.reactnativenavigation.params.TopTabParams;

public class ScreenChangedEvent implements Event {
    public static final String TYPE = "ScreenChangedEvent";
    public FabParams fabParams;

    public ScreenChangedEvent(ScreenParams screenParams) {
        this.fabParams = screenParams.fabParams;
    }

    public ScreenChangedEvent(TopTabParams topTabParams) {
        this.fabParams = topTabParams.fabParams;
    }

    @Override
    public String getType() {
        return TYPE;
    }
}
