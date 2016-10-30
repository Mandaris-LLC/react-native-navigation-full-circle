package com.reactnativenavigation.views.collapsingToolbar;

import android.content.Context;
import android.view.View;
import android.widget.ScrollView;

import com.reactnativenavigation.params.CollapsingTopBarParams;
import com.reactnativenavigation.views.TitleBar;
import com.reactnativenavigation.views.TopBar;

public class CollapsingTopBar extends TopBar implements CollapsingView {
    private CollapsingToolBar collapsingToolBar;
    private ScrollListener scrollListener;

    public CollapsingTopBar(Context context, CollapsingTopBarParams params) {
        super(context);
        createCollapsingTopBar(params);
    }

    public void setScrollListener(ScrollListener scrollListener) {
        this.scrollListener = scrollListener;
    }

    private void createCollapsingTopBar(CollapsingTopBarParams params) {
        collapsingToolBar = new CollapsingToolBar(getContext(), params);
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, (int) CollapsingToolBar.MAX_HEIGHT);
        titleBarAndContextualMenuContainer.addView(collapsingToolBar, lp);
    }

    @Override
    protected TitleBar createTitleBar() {
        return new CollapsingTitleBar(getContext(),
                collapsingToolBar.getCollapsedTopBarHeight(),
                scrollListener);
    }

    public int getTitleBarHeight() {
        return titleBar.getMeasuredHeight();
    }

    public CollapsingToolBar getCollapsingToolBar() {
        return collapsingToolBar;
    }

    public void collapse(float collapse) {
        setTranslationY(collapse);
        ((CollapsingTitleBar) titleBar).collapse(collapse);
        collapsingToolBar.collapse(collapse);
    }

    public void onScrollViewAdded(ScrollView scrollView) {
        scrollListener.onScrollViewAdded(scrollView);
    }

    @Override
    public float getFinalCollapseValue() {
        return getCollapsingToolBar().getCollapsedTopBarHeight() - getHeight();
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
