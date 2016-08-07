package com.reactnativenavigation.screens;

import android.support.v7.app.AppCompatActivity;
import android.view.ViewManager;
import android.widget.FrameLayout;

import com.reactnativenavigation.params.ScreenParams;
import com.reactnativenavigation.params.StyleParams;
import com.reactnativenavigation.params.TitleBarButtonParams;
import com.reactnativenavigation.params.TitleBarLeftButtonParams;
import com.reactnativenavigation.utils.Task;
import com.reactnativenavigation.views.TitleBarBackButtonListener;

import java.util.List;
import java.util.Stack;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

// TODO there's really no reason for ScreenStack to extend FrameLayout. All screens can be added to parent.
public class ScreenStack extends FrameLayout {

    private final AppCompatActivity activity;
    private TitleBarBackButtonListener titleBarBackButtonListener;
    private Stack<Screen> stack = new Stack<>();

    public ScreenStack(AppCompatActivity activity,
                       ScreenParams initialScreenParams,
                       TitleBarBackButtonListener titleBarBackButtonListener) {
        super(activity);
        this.activity = activity;
        this.titleBarBackButtonListener = titleBarBackButtonListener;
        pushInitialScreen(initialScreenParams);
    }

    private void pushInitialScreen(ScreenParams initialScreenParams) {
        Screen initialScreen = ScreenFactory.create(activity, initialScreenParams, titleBarBackButtonListener);
        addScreen(initialScreen);
        initialScreen.show(initialScreenParams.animateScreenTransitions);
    }

    public void push(final ScreenParams params) {
        Screen nextScreen = ScreenFactory.create(activity, params, titleBarBackButtonListener);
        final Screen previousScreen = stack.peek();
        addScreen(nextScreen);
        nextScreen.show(params.animateScreenTransitions, new Runnable() {
            @Override
            public void run() {
                removePreviousWithoutUnmount(previousScreen);
            }
        });
    }

    private void addScreen(Screen screen) {
        screen.setVisibility(INVISIBLE);
        addView(screen, new FrameLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT));
        stack.push(screen);
    }

    private void removePreviousWithoutUnmount(Screen previous) {
        previous.preventUnmountOnDetachedFromWindow();
        removeView(previous);
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
                removeView(toRemove);
            }
        });
    }

    private void readdPrevious(Screen previous) {
        addView(previous, 0, new LayoutParams(MATCH_PARENT, MATCH_PARENT));
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
            removeView(screen);
        }
        stack.clear();
        removeFromScreen();
    }

    private void removeFromScreen() {
        ViewManager parent = (ViewManager) getParent();
        if (parent != null) {
            parent.removeView(this);
        }
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
}
