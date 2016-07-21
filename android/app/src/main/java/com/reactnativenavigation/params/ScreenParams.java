package com.reactnativenavigation.params;

import android.os.Bundle;

import java.util.List;

public class ScreenParams {
    public String screenId;
    public String screenInstanceId;
    public Bundle passProps;
    public String navigatorEventId;
    public List<TitleBarButtonParams> buttons;
    public String title;
    public ScreenStyleParams styleParams;
    public Bundle navigationParams;
    //    public String tabLabel; TODO when tabs are supported move to TabParams
    //    public Drawable tabIcon;
}
