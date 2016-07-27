package com.reactnativenavigation.params;

import android.os.Bundle;

import java.util.List;

public class ScreenParams {
    public String screenId;
    public Bundle passProps;
    public List<TitleBarButtonParams> rightButtons;
    public TitleBarLeftButtonParams leftButton;
    public String title;
    public ScreenStyleParams styleParams;
    public List<TopTabParams> topTabParams;
    //    public String tabLabel; TODO when tabs are supported move to TabParams
    //    public Drawable tabIcon;

    // TODO navigationParams should be a terminated. destroyed. annihilated. disintegrated. banished from existence. + abolished, eradicated, exterminated...
    public String screenInstanceId;
    public String navigatorEventId;
    public Bundle navigationParams;

    public boolean hasTopTabs() {
        return topTabParams != null && !topTabParams.isEmpty();
    }
}
