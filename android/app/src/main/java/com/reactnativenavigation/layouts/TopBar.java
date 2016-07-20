package com.reactnativenavigation.layouts;

import android.content.Context;
import android.support.design.widget.AppBarLayout;

import com.reactnativenavigation.params.TitleBarButtonParams;
import com.reactnativenavigation.views.TitleBar;

import java.util.List;

public class TopBar extends AppBarLayout {

    private TitleBar titleBar;

    public TopBar(Context context) {
        super(context);
        setFitsSystemWindows(true);
    }

    public void addTitleBarAndSetButtons(List<TitleBarButtonParams> buttons) {
        titleBar = new TitleBar(getContext());
        titleBar.setButtons(buttons);
    }

    public void setTitleBarVisibility(boolean isHidden) {
        titleBar.setVisibility(isHidden ? GONE : VISIBLE);
    }
}
