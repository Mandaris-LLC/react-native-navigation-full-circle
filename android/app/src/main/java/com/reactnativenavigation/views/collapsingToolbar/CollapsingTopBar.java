package com.reactnativenavigation.views.collapsingToolbar;

import android.content.Context;
import android.view.View;
import android.widget.ScrollView;

import com.reactnativenavigation.params.CollapsingTopBarParams;
import com.reactnativenavigation.utils.ViewUtils;
import com.reactnativenavigation.views.TitleBar;
import com.reactnativenavigation.views.TopBar;

public class CollapsingTopBar extends TopBar implements CollapsingView {
    private CollapsingTopBarBackground collapsingTopBarBackground;
    private ScrollListener scrollListener;
    private float finalCollapsedTranslation;

    public CollapsingTopBar(Context context, CollapsingTopBarParams params) {
        super(context);
        createCollapsingTopBar(params);
        ViewUtils.runOnPreDraw(this, new Runnable() {
            @Override
            public void run() {
                finalCollapsedTranslation = getCollapsingTopBarBackground().getCollapsedTopBarHeight() - getHeight();
            }
        });

    }

    public void setScrollListener(ScrollListener scrollListener) {
        this.scrollListener = scrollListener;
    }

    private void createCollapsingTopBar(CollapsingTopBarParams params) {
        collapsingTopBarBackground = new CollapsingTopBarBackground(getContext(), params);
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, (int) CollapsingTopBarBackground.MAX_HEIGHT);
        titleBarAndContextualMenuContainer.addView(collapsingTopBarBackground, lp);
    }

    @Override
    protected TitleBar createTitleBar() {
        return new CollapsingTitleBar(getContext(),
                collapsingTopBarBackground.getCollapsedTopBarHeight(),
                scrollListener);
    }

    public CollapsingTopBarBackground getCollapsingTopBarBackground() {
        return collapsingTopBarBackground;
    }

    public void collapse(float collapse) {
        setTranslationY(collapse);
        ((CollapsingTitleBar) titleBar).collapse(collapse);
        collapsingTopBarBackground.collapse(collapse);
    }

    public void onScrollViewAdded(ScrollView scrollView) {
        scrollListener.onScrollViewAdded(scrollView);
    }

    @Override
    public float getFinalCollapseValue() {
        return finalCollapsedTranslation;
    }

    public int getCollapsedHeight() {
        return collapsingTopBarBackground.getCollapsedTopBarHeight();
    }

    @Override
    public float getCurrentCollapseValue() {
        return getTranslationY();
    }

    @Override
    public View asView() {
        return this;
    }
}
