package com.reactnativenavigation.params;

import android.os.Bundle;

import java.util.List;

public class ScreenParams {
    public String screenId;
    public Bundle passProps;
    public List<TitleBarButtonParams> buttons;
    public String title;
    public ScreenStyleParams styleParams;
    public List<TopTabParams> topTabParams;
    //    public String tabLabel; TODO when tabs are supported move to TabParams
    //    public Drawable tabIcon;

    // TODO navigationParams should be a class with a `toBundle` method or something. Then we wouldn't neet to keep these two fields here
    public String screenInstanceId;
    public String navigatorEventId;
    public Bundle navigationParams;

    public boolean hasTopTabs() {
        return topTabParams != null && !topTabParams.isEmpty();
    }
}
