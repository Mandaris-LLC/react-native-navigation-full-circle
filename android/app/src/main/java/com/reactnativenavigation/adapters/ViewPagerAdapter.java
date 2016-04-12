package com.reactnativenavigation.adapters;

import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.react.ReactInstanceManager;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.reactnativenavigation.activities.BaseReactActivity;
import com.reactnativenavigation.core.RctManager;
import com.reactnativenavigation.core.objects.Screen;
import com.reactnativenavigation.views.RctView;
import com.reactnativenavigation.views.RnnToolBar;

import java.util.ArrayList;

/**
 * Created by guyc on 02/04/16.
 */
public class ViewPagerAdapter extends PagerAdapter implements TabLayout.OnTabSelectedListener {

    private static final String EVENT_ON_TAB_SELECTED = "OnTabSelected";

    private BaseReactActivity mContext;
    private ViewPager mViewPager;
    private RnnToolBar mToolbar;
    private final ArrayList<Screen> mScreens;
    private final ReactInstanceManager mReactInstanceManager;

    public ViewPagerAdapter(BaseReactActivity context, ViewPager viewPager, RnnToolBar toolbar,
                            ArrayList<Screen> screens) {
        mContext = context;
        mViewPager = viewPager;
        mToolbar = toolbar;
        mScreens = screens;
        mReactInstanceManager = RctManager.getInstance().getReactInstanceManager();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Screen screen = mScreens.get(position);
        View view = new RctView(mContext, mReactInstanceManager, screen);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object view) {
        container.removeView((View) view);
    }

    @Override
    public int getCount() {
        return mScreens.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mScreens.get(position).title;
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        // Set the viewPager's current item
        int position = tab.getPosition();
        mViewPager.setCurrentItem(position);

        // Send tab selected event
        WritableMap params = Arguments.createMap();
        Screen screen = mScreens.get(position);
        params.putString(Screen.KEY_NAVIGATOR_EVENT_ID, screen.navigatorEventId);

        mToolbar.setupToolbarButtonsAsync(mScreens.get(position));

        RctManager.getInstance().sendEvent(EVENT_ON_TAB_SELECTED, screen.navigatorEventId, params);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
