package com.reactnativenavigation.params;

import com.balysv.materialmenu.MaterialMenuDrawable;

public class TitleBarLeftButtonParams extends TitleBarButtonParams {
    public MaterialMenuDrawable.IconState iconState;

    public TitleBarLeftButtonParams(TitleBarButtonParams params) {
        icon = params.icon;
        color = params.color;
        eventId = params.eventId;
        enabled = params.enabled;
    }

    public boolean isBackButton() {
        return eventId.equals("back");
    }
}
