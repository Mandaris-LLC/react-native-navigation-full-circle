package com.reactnativenavigation.screens;

import android.support.v7.app.AppCompatActivity;

import com.reactnativenavigation.params.ScreenParams;
import com.reactnativenavigation.views.ContentView;
import com.reactnativenavigation.views.TitleBarBackButtonListener;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

public class SingleScreen extends Screen {

    private ContentView contentView;

    public SingleScreen(AppCompatActivity activity, ScreenParams screenParams,
                        TitleBarBackButtonListener titleBarBarBackButtonListener) {
        super(activity, screenParams, titleBarBarBackButtonListener);
    }

    @Override
    protected void createContent() {
        contentView = new ContentView(getContext(), screenParams.screenId, screenParams.passProps, screenParams.navigationParams, this);
        LayoutParams params = new LayoutParams(MATCH_PARENT, MATCH_PARENT);
        if (screenParams.styleParams.drawBelowTopBar) {
            params.addRule(BELOW, topBar.getId());
        }
        addView(contentView, params);
    }

    @Override
    public void ensureUnmountOnDetachedFromWindow() {
        contentView.ensureUnmountOnDetachedFromWindow();
    }

    @Override
    public void preventUnmountOnDetachedFromWindow() {
        contentView.preventUnmountOnDetachedFromWindow();
    }

    @Override
    public void preventMountAfterReattachedToWindow() {
        contentView.preventMountAfterReattachedToWindow();
    }
}
