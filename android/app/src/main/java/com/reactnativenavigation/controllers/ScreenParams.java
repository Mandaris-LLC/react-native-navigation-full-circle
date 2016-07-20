package com.reactnativenavigation.controllers;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.reactnativenavigation.views.TitleBarButton;

import java.util.ArrayList;

public class ScreenParams {
    public String screenRegisteredId;
    public Bundle passProps;
    public ArrayList<TitleBarButton.Params> buttons;
    public String title;
    public String label;
    public String screenId;
    public String screenInstanceId;
    public String navigatorId;
    public String navigatorEventId;
    public Drawable icon;
    public ScreenStyleParams styleParams;
}
