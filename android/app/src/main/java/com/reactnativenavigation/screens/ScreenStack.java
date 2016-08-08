package com.reactnativenavigation.screens;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

import com.reactnativenavigation.params.ScreenParams;
import com.reactnativenavigation.params.StyleParams;
import com.reactnativenavigation.params.TitleBarButtonParams;
import com.reactnativenavigation.params.TitleBarLeftButtonParams;
import com.reactnativenavigation.utils.Task;
import com.reactnativenavigation.views.TitleBarBackButtonListener;

import java.util.List;
import java.util.Stack;

public class ScreenStack {

    private final AppCompatActivity activity;
    private RelativeLayout parent;
    private TitleBarBackButtonListener titleBarBackButtonListener;
    private Stack<Screen> stack = new Stack<>();

    public ScreenStack(AppCompatActivity activity,
                       RelativeLayout parent,
                       TitleBarBackButtonListener titleBarBackButtonListener) {
        this.activity = activity;
        this.parent = parent;
        this.titleBarBackButtonListener = titleBarBackButtonListener;
    }

    public void pushInitialScreen(ScreenParams initialScreenParams, RelativeLayout.LayoutParams params) {
        Screen initialScreen = ScreenFactory.create(activity, initialScreenParams, titleBarBackButtonListener);
        initialScreen.setVisibility(View.INVISIBLE);
        addScreen(initialScreen, params);
    }

    public void push(final ScreenParams params, RelativeLayout.LayoutParams layoutParams) {
        Screen nextScreen = ScreenFactory.create(activity, params, titleBarBackButtonListener);
        final Screen previousScreen = stack.peek();
        addScreen(nextScreen, layoutParams);
        nextScreen.show(params.animateScreenTransitions, new Runnable() {
            @Override
            public void run() {
                removePreviousWithoutUnmount(previousScreen);
            }
        });
    }

    private void addScreen(Screen screen, RelativeLayout.LayoutParams layoutParams) {
        parent.addView(screen, layoutParams);
        stack.push(screen);
    }

    private void removePreviousWithoutUnmount(Screen previous) {
        previous.preventUnmountOnDetachedFromWindow();
        parent.removeView(previous);
    }

    public void pop(boolean animated) {
        if (!canPop()) {
            return;
        }

        final Screen toRemove = stack.pop();
        Screen previous = stack.peek();

        readdPrevious(previous);
        toRemove.hide(animated, new Runnable() {
            @Override
            public void run() {
                toRemove.ensureUnmountOnDetachedFromWindow();
                parent.removeView(toRemove);
            }
        });
    }

    public Screen peek() {
        return stack.peek();
    }

    private void readdPrevious(Screen previous) {
        parent.addView(previous, 0);
        previous.preventMountAfterReattachedToWindow();
    }

    public void popToRoot(boolean animated) {
        while (canPop()) {
            pop(animated);
        }
    }

    public void destroy() {
        for (Screen screen : stack) {
            screen.ensureUnmountOnDetachedFromWindow();
            parent.removeView(screen);
        }
        stack.clear();
    }

    public int getStackSize() {
        return stack.size();
    }

    public boolean canPop() {
        return getStackSize() > 1;
    }

    public void setScreenTopBarVisible(String screenInstanceId, final boolean visible, final boolean animate) {
        performOnScreen(screenInstanceId, new Task<Screen>() {
            @Override
            public void run(Screen param) {
                param.setTopBarVisible(visible, animate);
            }
        });
    }

    public void setScreenTitleBarTitle(String screenInstanceId, final String title) {
        performOnScreen(screenInstanceId, new Task<Screen>() {
            @Override
            public void run(Screen param) {
                param.setTitleBarTitle(title);
            }
        });
    }

    public void setScreenTitleBarRightButtons(String screenInstanceId, final String navigatorEventId, final List<TitleBarButtonParams> titleBarButtons) {
        performOnScreen(screenInstanceId, new Task<Screen>() {
            @Override
            public void run(Screen param) {
                param.setTitleBarRightButtons(navigatorEventId, titleBarButtons);
            }
        });
    }

    public void setScreenTitleBarLeftButton(String screenInstanceId, final String navigatorEventId, final TitleBarLeftButtonParams titleBarLeftButtonParams) {
        performOnScreen(screenInstanceId, new Task<Screen>() {
            @Override
            public void run(Screen param) {
                param.setTitleBarLeftButton(navigatorEventId, titleBarBackButtonListener, titleBarLeftButtonParams);
            }
        });
    }

    public StyleParams getCurrentScreenStyleParams() {
        return stack.peek().getStyleParams();
    }

    private void performOnScreen(String screenInstanceId, Task<Screen> task) {
        if (stack.isEmpty()) {
            return;
        }

        for (Screen screen : stack) {
            if (screen.getScreenInstanceId().equals(screenInstanceId)) {
                task.run(screen);
                return;
            }
        }
    }

    public void showFirstScreen() {
        stack.peek().setVisibility(View.VISIBLE);
    }

    public void hideScreen() {
        stack.peek().setVisibility(View.INVISIBLE);
    }
}
