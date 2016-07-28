package com.reactnativenavigation.layouts;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.reactnativenavigation.params.ActivityParams;
import com.reactnativenavigation.params.ScreenParams;
import com.reactnativenavigation.params.TitleBarButtonParams;
import com.reactnativenavigation.params.TitleBarLeftButtonParams;
import com.reactnativenavigation.screens.ScreenStack;
import com.reactnativenavigation.views.BottomTabs;

import java.util.List;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class BottomTabsLayout extends RelativeLayout implements Layout, AHBottomNavigation.OnTabSelectedListener {

    private final AppCompatActivity activity;
    private ActivityParams params;
    private BottomTabs bottomTabs;
    private ScreenStack[] screenStacks;
    private int currentStackIndex = 0;

    public BottomTabsLayout(AppCompatActivity activity, ActivityParams params) {
        super(activity);
        this.activity = activity;
        this.params = params;
        screenStacks = new ScreenStack[(params.tabParams.size())];
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
        bottomTabs.addTabs(params.tabParams, this);
    }

    private void addBottomTabsToScreen() {
        LayoutParams lp = new LayoutParams(MATCH_PARENT, WRAP_CONTENT);
        lp.addRule(ALIGN_PARENT_BOTTOM);
        addView(bottomTabs, lp);
    }

    private void addInitialScreen() {
        createAndAddScreenStack(0);
    }

    private void setBottomTabsStyle() {
//        bottomTabs.setForceTitlesDisplay(style.getBoolean(TAB_STYLE_INACTIVE_TITLES, DEFAULT_TAB_INACTIVE_TITLES));
        bottomTabs.setForceTint(true);
//        bottomTabs.setDefaultBackgroundColor(getColor(style, TAB_STYLE_BAR_BG_COLOR, DEFAULT_TAB_BAR_BG_COLOR));
//        bottomTabs.setInactiveColor(getColor(style, TAB_STYLE_BUTTON_COLOR, DEFAULT_TAB_BUTTON_COLOR));
//        bottomTabs.setAccentColor(getColor(style, TAB_STYLE_SELECTED_COLOR, DEFAULT_TAB_SELECTED_COLOR));
    }

    @Override
    public View asView() {
        return this;
    }

    @Override
    public boolean onBackPressed() {
        if (getCurrentScreenStack().canPop()) {
            getCurrentScreenStack().pop();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void setTopBarVisible(String screenInstanceId, boolean hidden, boolean animated) {
        for (int i = 0; i < bottomTabs.getItemsCount(); i++) {
            screenStacks[i].setTopBarVisible(screenInstanceId, hidden, animated);
        }
    }

    @Override
    public void setTitleBarTitle(String screenInstanceId, String title) {
        for (int i = 0; i < bottomTabs.getItemsCount(); i++) {
            screenStacks[i].setTitleBarTitle(screenInstanceId, title);
        }
    }

    @Override
    public void setTitleBarRightButtons(String screenInstanceId, String navigatorEventId, List<TitleBarButtonParams> titleBarButtons) {
        for (int i = 0; i < bottomTabs.getItemsCount(); i++) {
            screenStacks[i].setTitleBarRightButtons(screenInstanceId, navigatorEventId, titleBarButtons);
        }
    }

    @Override
    public void setTitleBarLeftButton(String screenInstanceId, String navigatorEventId, TitleBarLeftButtonParams titleBarLeftButtonParams) {
        for (int i = 0; i < bottomTabs.getItemsCount(); i++) {
            screenStacks[i].setTitleBarLeftButton(screenInstanceId, navigatorEventId, titleBarLeftButtonParams);
        }
    }

    @Override
    public void push(ScreenParams screenParams) {
        getCurrentScreenStack().push(screenParams);
    }

    @Override
    public void pop(ScreenParams screenParams) {
        getCurrentScreenStack().pop();
    }

    @Override
    public void popToRoot(ScreenParams params) {
        getCurrentScreenStack().popToRoot();
    }

    @Override
    public void newStack(ScreenParams params) {
        ScreenStack currentScreenStack = getCurrentScreenStack();
        currentScreenStack.destroy();
        removeView(currentScreenStack);

        ScreenStack newStack = new ScreenStack(activity, params);
        screenStacks[currentStackIndex] = newStack;
        addView(newStack, 0, new RelativeLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT));
    }

    @Override
    public void destroy() {
        for (ScreenStack screenStack : screenStacks) {
            screenStack.destroy();
            removeView(screenStack);
        }
        screenStacks = null;
    }

    @Override
    public void onTabSelected(int position, boolean wasSelected) {
        removeCurrentStack();

        ScreenStack newStack = screenStacks[position];
        if (newStack == null) {
            createAndAddScreenStack(position);
        } else {
            addView(newStack, 0, new LayoutParams(MATCH_PARENT, MATCH_PARENT));
            newStack.preventMountAfterReatachedToWindow();
        }
        currentStackIndex = position;
    }

    private void createAndAddScreenStack(int position) {
        ScreenStack newStack = new ScreenStack(activity, params.tabParams.get(position));
        screenStacks[position] = newStack;
        addView(newStack, 0, new LayoutParams(MATCH_PARENT, MATCH_PARENT));
    }

    private void removeCurrentStack() {
        ScreenStack currentScreenStack = getCurrentScreenStack();
        currentScreenStack.preventUnmountOnDetachedFromWindow();
        removeView(currentScreenStack);
    }

    private ScreenStack getCurrentScreenStack() {
        return screenStacks[currentStackIndex];
    }
}
