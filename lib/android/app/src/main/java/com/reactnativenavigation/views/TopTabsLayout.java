package com.reactnativenavigation.views;

import android.annotation.*;
import android.content.*;
import android.support.annotation.*;
import android.support.v4.view.*;
import android.view.*;
import android.widget.*;

import com.reactnativenavigation.parse.*;
import com.reactnativenavigation.presentation.*;
import com.reactnativenavigation.utils.*;
import com.reactnativenavigation.viewcontrollers.toptabs.*;

import java.util.*;

@SuppressLint("ViewConstructor")
public class TopTabsLayout extends RelativeLayout implements Component {

    private TopBar topBar;
    private List<TopTabController> tabs;
    private TopTabsViewPager viewPager;
    private final OptionsPresenter optionsPresenter;

    public TopTabsLayout(Context context, List<TopTabController> tabs, TopTabsAdapter adapter) {
        super(context);
        this.tabs = tabs;
        topBar = new TopBar(context, this, null);
        topBar.setId(View.generateViewId());
        viewPager = new TopTabsViewPager(context, tabs, adapter);
        optionsPresenter = new OptionsPresenter(this);
        initViews();
    }

    private void initViews() {
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.addRule(BELOW, topBar.getId());
        addView(topBar, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        addView(viewPager, layoutParams);
        topBar.setupTopTabsWithViewPager(viewPager);
    }

    @Override
    public void applyOptions(Options options) {
        optionsPresenter.applyOptions(options);
    }

    @Override
    public void sendOnNavigationButtonPressed(String id) {
        viewPager.sendOnNavigationButtonPressed(id);
    }

    @Override
    public TopBar getTopBar() {
        return topBar;
    }

    @Override
    public View getContentView() {
        return viewPager;
    }

    @Override
    public void drawBehindTopBar() {

    }

    @Override
    public void drawBelowTopBar() {

    }

    @RestrictTo(RestrictTo.Scope.TESTS)
    public ViewPager getViewPager() {
        return viewPager;
    }


    public void performOnCurrentTab(Task<TopTabController> task) {
        task.run(tabs.get(viewPager.getCurrentItem()));
    }

    public void switchToTab(int index) {
        viewPager.setCurrentItem(index);
    }

    public int getCurrentItem() {
        return viewPager.getCurrentItem();
    }
}
