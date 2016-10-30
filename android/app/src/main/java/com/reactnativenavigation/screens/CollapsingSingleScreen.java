package com.reactnativenavigation.screens;

import android.support.v7.app.AppCompatActivity;
import android.widget.ScrollView;

import com.reactnativenavigation.params.ScreenParams;
import com.reactnativenavigation.views.ContentView;
import com.reactnativenavigation.views.LeftButtonOnClickListener;
import com.reactnativenavigation.views.collapsingToolbar.CollapsingContentViewMeasurer;
import com.reactnativenavigation.views.collapsingToolbar.CollapsingTopBar;
import com.reactnativenavigation.views.collapsingToolbar.OnScrollViewAddedListener;
import com.reactnativenavigation.views.collapsingToolbar.ScrollListener;

public class CollapsingSingleScreen extends SingleScreen {

    public CollapsingSingleScreen(AppCompatActivity activity, ScreenParams screenParams, LeftButtonOnClickListener titleBarBarBackButtonListener) {
        super(activity, screenParams, titleBarBarBackButtonListener);
    }

    @Override
    protected void createTopBar() {
        final CollapsingTopBar topBar = new CollapsingTopBar(getContext(), styleParams.collapsingTopBarParams);
        topBar.setScrollListener(new ScrollListener(topBar,
                new ScrollListener.OnScrollListener() {
                    @Override
                    public void onScroll(float amount) {
                        contentView.collapse(amount);
                        topBar.collapse(amount);
                    }
                }
        ));
        this.topBar = topBar;
    }

    @Override
    protected void createContent() {
        contentView = new ContentView(getContext(), screenParams.screenId, screenParams.navigationParams);
        contentView.setViewMeasurer(new CollapsingContentViewMeasurer((CollapsingTopBar) topBar));
        setupScrollDetection((CollapsingTopBar) topBar);
        addView(contentView, createLayoutParams());
    }

    private void setupScrollDetection(final CollapsingTopBar topBar) {
        contentView.setupScrollDetection(new ScrollListener(topBar,
                new ScrollListener.OnScrollListener() {
                    @Override
                    public void onScroll(float amount) {
                        contentView.collapse(amount);
                        topBar.collapse(amount);
                    }
                }
        ));
        contentView.setOnScrollViewAddedListener(new OnScrollViewAddedListener() {
            @Override
            public void onScrollViewAdded(ScrollView scrollView) {
                topBar.onScrollViewAdded(scrollView);
            }
        });
    }
}
