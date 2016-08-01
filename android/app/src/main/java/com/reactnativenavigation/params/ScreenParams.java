package com.reactnativenavigation.params;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import java.util.List;

public class ScreenParams {
    public String screenId;
    public Bundle passProps;
    public List<TitleBarButtonParams> rightButtons;
    public TitleBarLeftButtonParams leftButton;
    public String title;
    public StyleParams styleParams;
    public List<TopTabParams> topTabParams;
    public String fragmentCreatorClassName;

    //    public String tabLabel; TODO when tabs are supported move to TabParams
    //    public Drawable tabIcon;
    public String tabLabel;
    public Drawable tabIcon;

    // TODO navigationParams should be a terminated. destroyed. annihilated. disintegrated. banished from existence. + abolished, eradicated, exterminated...
    public String screenInstanceId;
    public String navigatorEventId;
    public Bundle navigationParams;

    public boolean hasTopTabs() {
        return topTabParams != null && !topTabParams.isEmpty();
    }

    public boolean isFragmentScreen() {
        return fragmentCreatorClassName != null;
    }
}
