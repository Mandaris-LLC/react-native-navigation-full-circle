package com.reactnativenavigation.views;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;

import com.reactnativenavigation.params.StyleParams;
import com.reactnativenavigation.params.TitleBarButtonParams;
import com.reactnativenavigation.params.TitleBarLeftButtonParams;
import com.reactnativenavigation.utils.ViewUtils;

import java.util.List;

public class TopBar extends AppBarLayout {

    private TitleBar titleBar;
    private TabLayout tabLayout;

    public TopBar(Context context) {
        super(context);
        setFitsSystemWindows(true);
        setId(ViewUtils.generateViewId());
    }

    public void addTitleBarAndSetButtons(List<TitleBarButtonParams> rightButtons, TitleBarLeftButtonParams leftButton,
                                         TitleBarBackButtonListener titleBarBackButtonListener,
                                         String navigatorEventId) {
        titleBar = new TitleBar(getContext());
        addView(titleBar);
        titleBar.setRightButtons(rightButtons, navigatorEventId);
        titleBar.setLeftButton(leftButton, titleBarBackButtonListener, navigatorEventId);
    }

    public void setTitle(String title) {
        titleBar.setTitle(title);
    }

    public void setStyle(StyleParams styleParams) {
        if (styleParams.topBarColor.hasColor()) {
            setBackgroundColor(styleParams.topBarColor.getColor());
        }
        setVisibility(styleParams.topBarHidden ? GONE : VISIBLE);
        titleBar.setStyle(styleParams);
    }

    public void setTitleBarRightButtons(String navigatorEventId, List<TitleBarButtonParams> titleBarButtons) {
        titleBar.setRightButtons(titleBarButtons, navigatorEventId);
    }

    public TabLayout initTabs() {
        tabLayout = new TabLayout(getContext());
        addView(tabLayout);
        return tabLayout;
    }

    public void setTitleBarRightButton(String navigatorEventId,
                                       TitleBarBackButtonListener titleBarBackButtonListener,
                                       TitleBarLeftButtonParams titleBarLeftButtonParams) {
        titleBar.setLeftButton(titleBarLeftButtonParams, titleBarBackButtonListener, navigatorEventId);
    }

    public TitleBar getTitleBar() {
        return titleBar;
    }
}
