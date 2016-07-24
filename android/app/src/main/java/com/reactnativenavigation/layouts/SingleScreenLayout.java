package com.reactnativenavigation.layouts;

import android.content.Context;
import android.widget.FrameLayout;

import com.reactnativenavigation.params.ScreenParams;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

public class SingleScreenLayout extends FrameLayout implements Layout {

    private final ScreenParams screenParams;
    private ScreenLayout screenLayout;

    public SingleScreenLayout(Context context, ScreenParams screenParams) {
        super(context);
        this.screenParams = screenParams;
        createLayout();
    }

    private void createLayout() {
        screenLayout = new ScreenLayout(getContext(), screenParams);
        addView(screenLayout, new FrameLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT));
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void removeAllReactViews() {
        screenLayout.removeAllReactViews();
    }
}
