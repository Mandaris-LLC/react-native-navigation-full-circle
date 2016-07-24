package com.reactnativenavigation.layouts;

import android.content.Context;
import android.widget.FrameLayout;

import com.reactnativenavigation.params.ScreenParams;

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
        }
        stack = new ScreenStack(context, screenParams);
        addView(stack, new LayoutParams(MATCH_PARENT, MATCH_PARENT));
    }

    @Override
    public boolean onBackPressed() {
        if (stack.isEmpty()) {
            return false;
        } else {
            stack.pop();
            return true;
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
}
