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
        setBackgroundColor(params.bottomTabsColor);
        if (params.bottomTabsButtonColor.hasColor()) {
            setInactiveColor(params.bottomTabsButtonColor.getColor());
        }

        if (params.selectedBottomTabsButtonColor.hasColor()) {
            setAccentColor(params.selectedBottomTabsButtonColor.getColor());
        }
    }

    private void setBackgroundColor(ScreenStyleParams.Color bottomTabsColor) {
        if (bottomTabsColor.hasColor()) {
            setDefaultBackgroundColor(bottomTabsColor.getColor());
        } else {
            setDefaultBackgroundColor(Color.WHITE);
        }
    }
}
