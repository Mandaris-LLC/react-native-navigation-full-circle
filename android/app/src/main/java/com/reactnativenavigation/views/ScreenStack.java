package com.reactnativenavigation.views;

import android.animation.LayoutTransition;
import android.widget.FrameLayout;

import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactRootView;
import com.reactnativenavigation.activities.BaseReactActivity;
import com.reactnativenavigation.core.RctManager;
import com.reactnativenavigation.core.objects.Screen;
import com.reactnativenavigation.utils.ReflectionUtils;

import java.util.Stack;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

public class ScreenStack extends FrameLayout {

    private static class ScreenView {
        Screen screen;
        RctView view;

        public ScreenView(Screen screen, RctView view) {
            this.screen = screen;
            this.view = view;
        }
    }

    private final Stack<ScreenView> mStack = new Stack<>();
    private final ReactInstanceManager mReactInstanceManager =
            RctManager.getInstance().getReactInstanceManager();
    private final BaseReactActivity mReactActivity;

    public ScreenStack(BaseReactActivity context) {
        super(context);
        mReactActivity = context;
        setLayoutTransition(new LayoutTransition());
    }

    public void push(Screen screen) {
        RctView oldView = null;
        if (!mStack.isEmpty()) {
            oldView = mStack.peek().view;
        }
        RctView view = new RctView(mReactActivity, mReactInstanceManager, screen);
        addView(view, MATCH_PARENT, MATCH_PARENT);
        if (oldView != null) {
            ReactRootView reactRootView = oldView.getReactRootView();
            ReflectionUtils.setBooleanField(reactRootView, "mAttachScheduled", true);
            removeView(oldView);
        }
        mStack.push(new ScreenView(screen, view));
    }

    public Screen pop() {
        if (mStack.isEmpty()) {
            return null;
        }
        ScreenView popped = mStack.pop();
        if (!mStack.isEmpty()) {
            addView(mStack.peek().view, 0);
        }

        ReflectionUtils.setBooleanField(popped.view.getReactRootView(), "mAttachScheduled", false);
        removeView(popped.view);
        return popped.screen;
    }

    public boolean isEmpty() {
        return mStack.isEmpty();
    }

    public int getStackSize() {
        return mStack.size();
    }

    public Screen peek() {
        return mStack.peek().screen;
    }
}
