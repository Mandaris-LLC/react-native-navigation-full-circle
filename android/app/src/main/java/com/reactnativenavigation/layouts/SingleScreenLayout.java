package com.reactnativenavigation.layouts;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;

import com.reactnativenavigation.params.ScreenParams;
import com.reactnativenavigation.params.TitleBarButtonParams;
import com.reactnativenavigation.params.TitleBarLeftButtonParams;
import com.reactnativenavigation.screens.ScreenStack;

import java.util.List;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

public class SingleScreenLayout extends FrameLayout implements Layout {

    private final AppCompatActivity activity;
    private final ScreenParams screenParams;
    private ScreenStack stack;

    public SingleScreenLayout(AppCompatActivity activity, ScreenParams screenParams) {
        super(activity);
        this.activity = activity;
        this.screenParams = screenParams;
        createStack();
    }

    private void createStack() {
        if (stack != null) {
            stack.destroy();
            removeView(stack);
        }
        stack = new ScreenStack(activity, screenParams);
        addView(stack, new LayoutParams(MATCH_PARENT, MATCH_PARENT));
    }

    @Override
    public boolean onBackPressed() {
        if (stack.canPop()) {
            stack.pop();
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
        stack.push(params);
    }

    @Override
    public void pop(ScreenParams params) {
        stack.pop();
    }

    @Override
    public void popToRoot(ScreenParams params) {
        stack.popToRoot();
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
}
