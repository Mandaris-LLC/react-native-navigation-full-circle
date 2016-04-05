package com.reactnativenavigation.adapters;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.react.ReactInstanceManager;
import com.reactnativenavigation.activities.BaseReactActivity;
import com.reactnativenavigation.core.RctManager;
import com.reactnativenavigation.core.Screen;
import com.reactnativenavigation.views.RctView;

import java.util.ArrayList;

/**
 * Created by guyc on 02/04/16.
 */
public class ViewPagerAdapter extends PagerAdapter {

    private BaseReactActivity mContext;
    private final ArrayList<Screen> mScreens;
    private final ReactInstanceManager mReactInstanceManager;

    public ViewPagerAdapter(BaseReactActivity context, ArrayList<Screen> screens) {
        mContext = context;
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
}
