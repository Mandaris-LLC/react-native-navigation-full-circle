package com.reactnativenavigation.params.parsers;

import android.os.Bundle;

import com.balysv.materialmenu.MaterialMenuDrawable;
import com.reactnativenavigation.params.TitleBarLeftButtonParams;

public class TitleBarLeftButtonParamsParser extends TitleBarButtonParamsParser {

    public TitleBarLeftButtonParams parseSingleButton(Bundle params) {
        TitleBarLeftButtonParams backButtonParams = new TitleBarLeftButtonParams(super.parseSingleButton(params));
        backButtonParams.initialIconState = getIconStateFromId(backButtonParams.eventId);
        return backButtonParams;
    }

    private MaterialMenuDrawable.IconState getIconStateFromId(String id) {
        switch (id) {
            case "back":
                return MaterialMenuDrawable.IconState.ARROW;
            case "cancel":
                return MaterialMenuDrawable.IconState.X;
            case "accept":
                return MaterialMenuDrawable.IconState.CHECK;
            case "sideMenu":
                return MaterialMenuDrawable.IconState.BURGER;
            default:
                throw new RuntimeException("Unsupported button type " + id);
        }
    }
}
