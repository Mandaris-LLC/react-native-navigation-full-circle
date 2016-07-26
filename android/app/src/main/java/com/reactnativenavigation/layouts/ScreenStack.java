package com.reactnativenavigation.layouts;

import android.animation.LayoutTransition;
import android.content.Context;
import android.widget.FrameLayout;

import com.reactnativenavigation.params.ScreenParams;

import java.util.Stack;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

// TODO there's really no reason for ScreenStack to extend FrameLayout. All screens can be added to parent.
public class ScreenStack extends FrameLayout {
    private Stack<Screen> stack = new Stack<>();

    public ScreenStack(Context context, ScreenParams initialScreenParams) {
        super(context);
        setLayoutTransition(new LayoutTransition());
        push(initialScreenParams);
    }

    public void push(ScreenParams screenParams) {
        if (isEmpty()) {
            addScreen(screenParams);
        } else {
            Screen previous = stack.peek();
            addScreen(screenParams);
            removePreviousWithoutUnmount(previous);
        }
    }

    private void addScreen(ScreenParams screenParams) {
        Screen screen = ScreenFactory.create(getContext(), screenParams);
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

    public boolean isEmpty() {
        return stack.isEmpty();
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

    private Screen findScreenByScreenInstanceId(String screenInstanceId) {
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


    //    /**
//     * Remove the ScreenStack from {@code parent} while preventing all child react views from getting unmounted
//        -= USE WHEN SWITCHING TABS =-
//     */
//    public void removeFromScreen(ViewGroup parent) {
//        mStack.peek().view.onTemporallyRemovedFromScreen();
//
//        parent.removeView(this);
//    }
//    /**
//     * Add ScreenStack to {@code parent}
//       -= USE WHEN SWITCHING TABS =-
//     */
//    public void addToScreen(ViewGroup parent) {
//        mStack.peek().view.onReAddToScreen();
//
//        parent.addView(this, new CoordinatorLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT));
//    }
}
