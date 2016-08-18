package com.reactnativenavigation.params.parsers;

import android.os.Bundle;

import com.reactnativenavigation.params.NavigationParams;
import com.reactnativenavigation.params.SideMenuParams;

public class SideMenuParamsParser extends Parser {

    public static SideMenuParams parse(Bundle sideMenu) {
        SideMenuParams result = new SideMenuParams();
        result.screenId = sideMenu.getString("screenId");
        result.navigationParams = new NavigationParams(sideMenu.getBundle("navigationParams"));
        result.disableOpenGesture = sideMenu.getBoolean("disableOpenGesture", false);
        return result;
    }
}
