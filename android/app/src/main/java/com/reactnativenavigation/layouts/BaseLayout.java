package com.reactnativenavigation.layouts;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;

import com.reactnativenavigation.R;
import com.reactnativenavigation.core.objects._Screen;
import com.reactnativenavigation.views.RctView;
import com.reactnativenavigation.views.RnnToolBar;
import com.reactnativenavigation.views.ScreenStack;

public abstract class BaseLayout extends FrameLayout implements Layout {

    private OnScreenPoppedListener onScreenPoppedListener;

    private RnnToolBar toolBar;
    private ScreenStack screenStack;

    public BaseLayout(Context context, _Screen initialScreen) {
        super(context);
        toolBar = (RnnToolBar) findViewById(R.id.toolbar);
        screenStack = (ScreenStack) findViewById(R.id.screenStack);

        toolBar.update(initialScreen);
        addInitialScreen(initialScreen);
    }

    private void addInitialScreen(_Screen screen) {
        screenStack.push(screen, new RctView.OnDisplayedListener() {
            @Override
            public void onDisplayed() {
                Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.slide_up);
                setAnimation(animation);
                animate();
            }
        });
    }

    @Override
    public void push(_Screen screen) {
        screenStack.push(screen);
    }

    @Override
    public _Screen pop() {
        _Screen popped = screenStack.pop();
        if (onScreenPoppedListener != null) {
            onScreenPoppedListener.onScreenPopped(popped);
        }
        return popped;
    }

    @Override
    public int getScreenCount() {
        return screenStack.getStackSize();
    }

    @Override
    public void removeAllReactViews() {
        screenStack.removeAllReactViews();
    }

    public void setOnScreenPoppedListener(OnScreenPoppedListener onScreenPoppedListener) {
        this.onScreenPoppedListener = onScreenPoppedListener;
    }
}
