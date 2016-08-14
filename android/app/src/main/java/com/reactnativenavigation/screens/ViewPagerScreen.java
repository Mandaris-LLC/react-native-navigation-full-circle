package com.reactnativenavigation.screens;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.reactnativenavigation.params.ScreenParams;
import com.reactnativenavigation.params.TopTabParams;
import com.reactnativenavigation.views.ContentView;
import com.reactnativenavigation.views.LeftButtonOnClickListener;

import java.util.ArrayList;
import java.util.List;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

public class ViewPagerScreen extends Screen {

    private static final int OFFSCREEN_PAGE_LIMIT = 99;
    private List<ContentView> contentViews;
    private ViewPager viewPager;

    public ViewPagerScreen(AppCompatActivity activity, ScreenParams screenParams, LeftButtonOnClickListener backButtonListener) {
        super(activity, screenParams, backButtonListener);
    }

    @Override
    protected void createContent() {
        TabLayout tabLayout = topBar.initTabs();
        createViewPager();
        addPages();
        setupViewPager(tabLayout);
    }

    private void createViewPager() {
        viewPager = new ViewPager(getContext());
        viewPager.setOffscreenPageLimit(OFFSCREEN_PAGE_LIMIT);
        LayoutParams lp = new LayoutParams(MATCH_PARENT, MATCH_PARENT);
        if (screenParams.styleParams.drawScreenBelowTopBar) {
            lp.addRule(BELOW, topBar.getId());
        }
        addView(viewPager, lp);
    }

    private void addPages() {
        contentViews = new ArrayList<>();
        for (TopTabParams tab : screenParams.topTabParams) {
            ContentView contentView = new ContentView(getContext(), tab.screenId, tab.navigationParams, screenParams.navigatorEventId);
            addContent(contentView);
            contentViews.add(contentView);
        }
    }

    private void setupViewPager(TabLayout tabLayout) {
        ContentViewPagerAdapter adapter = new ContentViewPagerAdapter(contentViews, screenParams.topTabParams);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void addContent(ContentView contentView) {
        LayoutParams params = new LayoutParams(MATCH_PARENT, MATCH_PARENT);
        viewPager.addView(contentView, params);
    }

    @Override
    public void ensureUnmountOnDetachedFromWindow() {
        for (ContentView contentView : contentViews) {
            contentView.ensureUnmountOnDetachedFromWindow();
        }
    }

    @Override
    public void preventUnmountOnDetachedFromWindow() {
        for (ContentView contentView : contentViews) {
            contentView.preventUnmountOnDetachedFromWindow();
        }
    }

    @Override
    public void preventMountAfterReattachedToWindow() {
        for (ContentView contentView : contentViews) {
            contentView.preventMountAfterReattachedToWindow();
        }
    }

    @Override
    public void setOnDisplayListener(OnDisplayListener onContentViewDisplayedListener) {
        contentViews.get(0).setOnDisplayListener(onContentViewDisplayedListener);
    }
}
