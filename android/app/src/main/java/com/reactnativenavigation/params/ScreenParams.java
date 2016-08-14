package com.reactnativenavigation.params;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import java.util.List;

public class ScreenParams {
    public String screenId;
    public List<TitleBarButtonParams> rightButtons;
    public TitleBarLeftButtonParams leftButton;
    public String title;
    public StyleParams styleParams;
    public List<TopTabParams> topTabParams;
    public String fragmentCreatorClassName;
    public Bundle fragmentCreatorPassProps;
    public boolean animateScreenTransitions;

    public String tabLabel;
    public Drawable tabIcon;

    public String screenInstanceId;
    public String navigatorEventId;
    public String navigatorId;
    public Bundle navigationParams;

    public boolean hasTopTabs() {
        return topTabParams != null && !topTabParams.isEmpty();
    }

    public boolean isFragmentScreen() {
        return fragmentCreatorClassName != null;
    }
}
