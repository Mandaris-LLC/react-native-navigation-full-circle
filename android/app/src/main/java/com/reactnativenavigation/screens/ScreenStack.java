package com.reactnativenavigation.screens;

import android.animation.LayoutTransition;
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
            return;
        }

        Screen toRemove = stack.pop();
        Screen previous = stack.peek();

        readdPrevious(previous);

        toRemove.ensureUnmountOnDetachedFromWindow();
        removeView(toRemove);
    }

    private void readdPrevious(Screen previous) {
        addView(previous, new LayoutParams(MATCH_PARENT, MATCH_PARENT));
        previous.preventMountAfterReattachedToWindow();
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
                param.setTitleBarLeftButton(navigatorEventId, ScreenStack.this, titleBarLeftButtonParams);
            }
        });
    }

    @Override
    public void onTitleBarBackPress() {
        if (canPop()) {
            pop();
        }
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
