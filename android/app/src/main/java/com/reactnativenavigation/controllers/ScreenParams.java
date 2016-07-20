package com.reactnativenavigation.controllers;

import android.os.Bundle;

import com.reactnativenavigation.views.TitleBarButton;

import java.util.ArrayList;

public class ScreenParams {
    public String screenId;
    public String screenInstanceId;
    public Bundle passProps;
    public ArrayList<TitleBarButton.Params> buttons;
    public String title;
    public ScreenStyleParams styleParams;
    //    public String tabLabel; TODO when tabs are supported move to TabParams
    //    public Drawable tabIcon;
}
