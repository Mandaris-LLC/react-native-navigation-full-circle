package com.reactnativenavigation.layouts;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

import com.reactnativenavigation.params.ScreenParams;
import com.reactnativenavigation.params.TitleBarButtonParams;
import com.reactnativenavigation.params.TitleBarLeftButtonParams;
import com.reactnativenavigation.screens.ScreenAnimator;
import com.reactnativenavigation.screens.ScreenStack;
import com.reactnativenavigation.views.TitleBarBackButtonListener;

import java.util.List;

public class SingleScreenLayout extends RelativeLayout implements Layout {

    private final AppCompatActivity activity;
    private final ScreenParams screenParams;
    private ScreenStack stack;
    private ScreenAnimator screenAnimator;
    private TitleBarBackButtonListener titleBarBackButtonListener;

    public SingleScreenLayout(AppCompatActivity activity, ScreenParams screenParams, TitleBarBackButtonListener titleBarBackButtonListener) {
        this(activity, screenParams);
        this.titleBarBackButtonListener = titleBarBackButtonListener;
    }

    public SingleScreenLayout(AppCompatActivity activity, ScreenParams screenParams) {
        super(activity);
        this.activity = activity;
        this.screenParams = screenParams;
        createStack();
    }

    private void createStack() {
        if (stack != null) {
            stack.destroy();
        }
        stack = new ScreenStack(activity, this, this);
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        stack.pushInitialScreen(screenParams, lp);
    }

    @Override
    public boolean onBackPressed() {
        if (stack.canPop()) {
            stack.pop(true);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void destroy() {
        stack.destroy();
    }

    @Override
    public void push(ScreenParams params) {
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        stack.push(params, lp);
    }

    @Override
    public void pop(ScreenParams params) {
        stack.pop(params.animateScreenTransitions);
    }

    @Override
    public void popToRoot(ScreenParams params) {
        stack.popToRoot(params.animateScreenTransitions);
    }

    @Override
    public void newStack(ScreenParams params) {
        createStack();
    }

    @Override
    public void setTopBarVisible(String screenInstanceID, boolean visible, boolean animate) {
        stack.setScreenTopBarVisible(screenInstanceID, visible, animate);
    }

    @Override
    public void setTitleBarTitle(String screenInstanceId, String title) {
        stack.setScreenTitleBarTitle(screenInstanceId, title);
    }

    @Override
    public View asView() {
        return this;
    }

    @Override
    public void setTitleBarRightButtons(String screenInstanceId, String navigatorEventId,
                                        List<TitleBarButtonParams> titleBarRightButtons) {
        stack.setScreenTitleBarRightButtons(screenInstanceId, navigatorEventId, titleBarRightButtons);
    }

    @Override
    public void setTitleBarLeftButton(String screenInstanceId, String navigatorEventId, TitleBarLeftButtonParams titleBarLeftButtonParams) {
        stack.setScreenTitleBarLeftButton(screenInstanceId, navigatorEventId, titleBarLeftButtonParams);
    }

    @Override
    public boolean onTitleBarBackPress() {
        if (titleBarBackButtonListener != null) {
            return titleBarBackButtonListener.onTitleBarBackPress();
        }

        return onBackPressed();
    }
}
