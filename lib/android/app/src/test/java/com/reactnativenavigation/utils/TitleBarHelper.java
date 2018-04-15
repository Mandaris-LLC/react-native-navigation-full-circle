package com.reactnativenavigation.utils;

import android.support.v7.view.menu.ActionMenuItemView;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class TitleBarHelper {
    public static View getRightButton(Toolbar toolbar, int index) {
        return (View) ViewUtils.findChildrenByClassRecursive(toolbar, ActionMenuItemView.class).get(toolbar.getMenu().size() - index - 1);
    }
}
