package com.reactnativenavigation.layouts;

import android.animation.LayoutTransition;
import android.content.Context;
import android.view.View;
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
        Screen screen = new ScreenImpl(getContext(), screenParams);
        addView((View) screen, new FrameLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT));
        stack.push(screen);
    }

    public Screen pop() {
        Screen popped = stack.pop();
        removeView((View) popped);
        return popped;
    }

    public void popToRoot() {
        while (getStackSize() > 1) {
            pop();
        }
    }

    public void destroy() {
        while (!isEmpty()) {
            Screen screen = pop();
            screen.removeAllReactViews();
        }
        removeAllViews();
    }

    public boolean isEmpty() {
        return stack.isEmpty();
    }

    public int getStackSize() {
        return stack.size();
    }

    public Screen peek() {
        return stack.peek();
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
