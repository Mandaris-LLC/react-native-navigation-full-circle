package com.reactnativenavigation.views;

import android.content.Context;
import android.graphics.Color;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.reactnativenavigation.params.ScreenParams;
import com.reactnativenavigation.params.ScreenStyleParams;

import java.util.List;

public class BottomTabs extends AHBottomNavigation {
    public BottomTabs(Context context) {
        super(context);
        setForceTint(true);
    }

    public void addTabs(List<ScreenParams> params, OnTabSelectedListener onTabSelectedListener) {
        for (ScreenParams screenParams : params) {
            AHBottomNavigationItem item = new AHBottomNavigationItem(screenParams.title, screenParams.tabIcon,
                    Color.GRAY);
            addItem(item);
            setOnTabSelectedListener(onTabSelectedListener);
        }
    }

    public void setStyleFromScreen(ScreenStyleParams params) {
        if (params.bottomTabsColor.hasColor()) {
            setDefaultBackgroundColor(params.bottomTabsColor.getColor());
        } else {
            setDefaultBackgroundColor(Color.WHITE);
        }

//        setForceTitlesDisplay(true);
//        setInactiveColor(params.bottomTabsButtonColor.getColor());
//        setAccentColor(params.selectedBottomTabsButtonColor.getColor());
    }
}
