package com.reactnativenavigation.views;

import android.animation.LayoutTransition;
import android.widget.FrameLayout;

import com.facebook.react.ReactInstanceManager;
import com.reactnativenavigation.activities.BaseReactActivity;
import com.reactnativenavigation.core.RctManager;
import com.reactnativenavigation.core.objects.Screen;

import java.util.Stack;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

public class ScreenStack extends FrameLayout {

    private class ScreenView{
        Screen screen;
        RctView view;

        public ScreenView(Screen screen, RctView view) {
            this.screen = screen;
            this.view = view;
        }
    }

    private final Stack<ScreenView> stack = new Stack<>();
    private final ReactInstanceManager mReactInstanceManager = RctManager.getInstance().getReactInstanceManager();
    private final BaseReactActivity reactActivity;

    public ScreenStack(BaseReactActivity context){
        super(context);
        reactActivity = context;
        setLayoutTransition(new LayoutTransition());
    }

    public void push(Screen screen){
        RctView view = new RctView(reactActivity, mReactInstanceManager, screen);
        addView(view, MATCH_PARENT, MATCH_PARENT);
        stack.push(new ScreenView(screen, view));
    }

    public Screen pop(){
        ScreenView popped = stack.pop();
        removeView(popped.view);
        return popped.screen;
    }

    public boolean isEmpty(){
        return stack.isEmpty();
    }

    public int getStackSize(){
        return stack.size();
    }

    public Screen peek(){
        return stack.peek().screen;
    }
}
