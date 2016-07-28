package com.reactnativenavigation.screens;

import android.animation.LayoutTransition;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.reactnativenavigation.params.ScreenParams;
import com.reactnativenavigation.params.TitleBarButtonParams;
import com.reactnativenavigation.params.TitleBarLeftButtonParams;
import com.reactnativenavigation.views.TitleBarBackButtonListener;

import java.util.List;
import java.util.Stack;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

// TODO there's really no reason for ScreenStack to extend FrameLayout. All screens can be added to parent.
public class ScreenStack extends FrameLayout implements TitleBarBackButtonListener {
    private final AppCompatActivity activity;
    private Stack<Screen> stack = new Stack<>();

    public ScreenStack(AppCompatActivity activity, ScreenParams initialScreenParams) {
        super(activity);
        this.activity = activity;
        setLayoutTransition(new LayoutTransition());
        pushInitialScreen(initialScreenParams);
    }

    private void pushInitialScreen(ScreenParams initialScreenParams) {
        addScreen(initialScreenParams);
    }

    public void push(ScreenParams screenParams) {
        Screen previous = stack.peek();
        addScreen(screenParams);
        removePreviousWithoutUnmount(previous);
    }

    private void addScreen(ScreenParams screenParams) {
        Screen screen = ScreenFactory.create(activity, screenParams, this);
        addView(screen, new FrameLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT));
        stack.push(screen);
    }

    private void removePreviousWithoutUnmount(Screen previous) {
        previous.preventUnmountOnDetachedFromWindow();
        removeView(previous);
    }

    public void pop() {
        if (!canPop()) {
            throw new RuntimeException("Can't pop ScreenStack of size " + getStackSize());
        }

        Screen toRemove = stack.pop();
        Screen previous = stack.peek();

        readdPrevious(previous);

        toRemove.ensureUnmountOnDetachedFromWindow();
        removeView(toRemove);
    }

    private void readdPrevious(Screen previous) {
        addView(previous, new LayoutParams(MATCH_PARENT, MATCH_PARENT));
    }

    public void popToRoot() {
        while (canPop()) {
            pop();
        }
    }

    public void destroy() {
        for (Screen screen : stack) {
            screen.ensureUnmountOnDetachedFromWindow();
            removeView(screen);
        }
        stack.clear();
    }

    public int getStackSize() {
        return stack.size();
    }

    public boolean canPop() {
        return getStackSize() > 1;
    }

    public void setTopBarVisible(String screenInstanceId, boolean visible, boolean animate) {
        Screen screen = findScreenByScreenInstanceId(screenInstanceId);
        if (screen != null) {
            screen.setTopBarVisible(visible, animate);
        }
    }

    public Screen findScreenByScreenInstanceId(String screenInstanceId) {
        if (stack.isEmpty()) {
            return null;
        }

        for (Screen screen : stack) {
            if (screen.getScreenInstanceId().equals(screenInstanceId)) {
                return screen;
            }
        }

        return null;
    }

    public void setTitleBarTitle(String screenInstanceId, String title) {
        Screen screen = findScreenByScreenInstanceId(screenInstanceId);
        if (screen != null) {
            screen.setTitleBarTitle(title);
        }
    }

    public void setTitleBarRightButtons(String screenInstanceId, String navigatorEventId, List<TitleBarButtonParams> titleBarButtons) {
        Screen screen = findScreenByScreenInstanceId(screenInstanceId);
        if (screen != null) {
            screen.setTitleBarRightButtons(navigatorEventId, titleBarButtons);
        }
    }

    public void setTitleBarLeftButton(String screenInstanceId, String navigatorEventId, TitleBarLeftButtonParams titleBarLeftButtonParams) {
        Screen screen = findScreenByScreenInstanceId(screenInstanceId);
        if (screen != null) {
            screen.setTitleBarLeftButton(navigatorEventId, this, titleBarLeftButtonParams);
        }
    }

    @Override
    public void onTitleBarBackPress() {
        if (canPop()) {
            pop();
        }
    }

    public void preventUnmountOnDetachedFromWindow() {
        for (Screen screen : stack) {
            screen.preventUnmountOnDetachedFromWindow();
        }
    }

    public void ensureUnmountOnDetachedFromWindow() {
        for (Screen screen : stack) {
            screen.ensureUnmountOnDetachedFromWindow();
        }
    }

    public void preventMountAfterReatachedToWindow() {
        for (Screen screen : stack) {
            screen.preventMountAfterReattachedToWindow();
        }
    }
}
