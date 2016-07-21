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

    public void addTitleBarAndSetButtons(List<TitleBarButtonParams> buttons, String screenInstanceId) {
        titleBar = new TitleBar(getContext());
        addView(titleBar);
        titleBar.setButtons(buttons, screenInstanceId);
    }

    public void setTitleBarVisibility(boolean isHidden) {
        titleBar.setVisibility(isHidden ? GONE : VISIBLE);
    }

    public void setTitle(String title) {
        titleBar.setTitle(title);
    }
}
