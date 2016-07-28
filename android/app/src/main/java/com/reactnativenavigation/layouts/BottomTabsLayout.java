package com.reactnativenavigation.layouts;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.RelativeLayout;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.reactnativenavigation.params.ActivityParams;
import com.reactnativenavigation.params.ScreenParams;
import com.reactnativenavigation.params.TitleBarButtonParams;
import com.reactnativenavigation.params.TitleBarLeftButtonParams;
import com.reactnativenavigation.screens.ScreenStack;
import com.reactnativenavigation.views.BottomTabs;

import java.util.ArrayList;
import java.util.List;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class BottomTabsLayout extends RelativeLayout implements Layout, AHBottomNavigation.OnTabSelectedListener {

    private ActivityParams params;
    private BottomTabs bottomTabs;
    private List<ScreenStack> screenStacks;
    private int currentStack = 0;

    public BottomTabsLayout(Context context, ActivityParams params) {
        super(context);
        this.params = params;
        screenStacks = new ArrayList<>();
        createLayout();
    }

    private void createLayout() {
        createBottomTabs();
        addBottomTabsToScreen();
        addInitialScreen();
    }

    private void createBottomTabs() {
        bottomTabs = new BottomTabs(getContext());
        setBottomTabsStyle();
        addTabs();
    }

    private void addBottomTabsToScreen() {
        LayoutParams lp = new LayoutParams(MATCH_PARENT, WRAP_CONTENT);
        lp.addRule(ALIGN_PARENT_BOTTOM);
        addView(bottomTabs, lp);
    }

    private void addInitialScreen() {
        addView(getFirstScreenStack());
    }

    private void setBottomTabsStyle() {
//        bottomTabs.setForceTitlesDisplay(style.getBoolean(TAB_STYLE_INACTIVE_TITLES, DEFAULT_TAB_INACTIVE_TITLES));
        bottomTabs.setForceTint(true);
//        bottomTabs.setDefaultBackgroundColor(getColor(style, TAB_STYLE_BAR_BG_COLOR, DEFAULT_TAB_BAR_BG_COLOR));
//        bottomTabs.setInactiveColor(getColor(style, TAB_STYLE_BUTTON_COLOR, DEFAULT_TAB_BUTTON_COLOR));
//        bottomTabs.setAccentColor(getColor(style, TAB_STYLE_SELECTED_COLOR, DEFAULT_TAB_SELECTED_COLOR));
    }

    private void addTabs() {
        for (ScreenParams screenParams : params.tabParams) {
            ScreenStack stack = new ScreenStack(getContext(), screenParams);
            screenStacks.add(stack);

            AHBottomNavigationItem item = new AHBottomNavigationItem(screenParams.title, screenParams.tabIcon,
                    Color.GRAY);
            bottomTabs.addItem(item);
            bottomTabs.setOnTabSelectedListener(this);
        }
    }

    @Override
    public View asView() {
        return this;
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public void setTopBarVisible(String screenInstanceId, boolean hidden, boolean animated) {
        for (int i = 0; i < bottomTabs.getItemsCount(); i++) {
            screenStacks.get(i).setTopBarVisible(screenInstanceId, hidden, animated);
        }
    }

    @Override
    public void setTitleBarTitle(String screenInstanceId, String title) {
        for (int i = 0; i < bottomTabs.getItemsCount(); i++) {
            screenStacks.get(i).setTitleBarTitle(screenInstanceId, title);
        }
    }

    @Override
    public void setTitleBarRightButtons(String screenInstanceId, String navigatorEventId, List<TitleBarButtonParams> titleBarButtons) {
        for (int i = 0; i < bottomTabs.getItemsCount(); i++) {
            screenStacks.get(i).setTitleBarRightButtons(screenInstanceId, navigatorEventId, titleBarButtons);
        }
    }

    @Override
    public void setTitleBarLeftButton(String screenInstanceId, String navigatorEventId, TitleBarLeftButtonParams titleBarLeftButtonParams) {
        for (int i = 0; i < bottomTabs.getItemsCount(); i++) {
            screenStacks.get(i).setTitleBarLeftButton(screenInstanceId, navigatorEventId, titleBarLeftButtonParams);
        }
    }

    @Override
    public void push(ScreenParams screenParams) {
        for (int i = 0; i < bottomTabs.getItemsCount(); i++) {
//            screenStacks.get(i).push(screenParams);
        }
    }

    @Override
    public void pop(ScreenParams screenParams) {
//        for (int i = 0; i < bottomTabs.getItemsCount(); i++) {
//            screenStacks.get(i).pop();
//        }
    }

    @Override
    public void popToRoot(ScreenParams params) {

    }

    @Override
    public void newStack(ScreenParams params) {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void onTabSelected(int position, boolean wasSelected) {
        removeView(getCurrentScreenStack());
        addView(screenStacks.get(position), 0, new LayoutParams(MATCH_PARENT, MATCH_PARENT));
        currentStack = position;
    }

    private ScreenStack getCurrentScreenStack() {
        return screenStacks.get(currentStack);
    }

    private ScreenStack getFirstScreenStack() {
        return screenStacks.get(0);
    }
}
