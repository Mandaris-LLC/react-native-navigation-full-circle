package com.reactnativenavigation.layouts;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;

import com.reactnativenavigation.params.ScreenParams;
import com.reactnativenavigation.params.TitleBarButtonParams;
import com.reactnativenavigation.screens.ScreenStack;

import java.util.List;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

public class SingleScreenLayout extends FrameLayout implements Layout {

    private final ScreenParams screenParams;
    private ScreenStack stack;

    public SingleScreenLayout(Context context, ScreenParams screenParams) {
        super(context);
        this.screenParams = screenParams;
        createStack(context);
    }

    private void createStack(Context context) {
        if (stack != null) {
            stack.destroy();
            removeView(stack);
        }
        stack = new ScreenStack(context, screenParams);
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
    public void onDestroy() {
        stack.destroy();
    }

    @Override
    public void removeAllReactViews() {
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
        createStack(getContext());
    }

    @Override
    public void setTopBarVisible(String screenInstanceID, boolean visible, boolean animate) {
        stack.setTopBarVisible(screenInstanceID, visible, animate);
    }

    @Override
    public void setTitleBarTitle(String screenInstanceId, String title) {
        stack.setTitleBarTitle(screenInstanceId, title);
    }

    @Override
    public View asView() {
        return this;
    }

    @Override
    public void setTitleBarButtons(String screenInstanceId, String navigatorEventId, List<TitleBarButtonParams> titleBarButtons) {
        stack.setTitleBarButtons(screenInstanceId, navigatorEventId, titleBarButtons);
    }
}
