package com.reactnativenavigation.adapters;

import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.react.ReactInstanceManager;
import com.facebook.react.bridge.ReadableArray;
import com.reactnativenavigation.activities.BaseReactActivity;
import com.reactnativenavigation.core.RctManager;
import com.reactnativenavigation.views.RctView;

import java.util.ArrayList;

/**
 *
 * Created by guyc on 02/04/16.
 */
public class ViewPagerAdapter extends PagerAdapter {

    private static final String TAG = "ViewPagerAdapter";
    private BaseReactActivity mContext;
    private final ArrayList<Page> mPages;
    private final ReactInstanceManager mReactInstanceManager;

    public ViewPagerAdapter(BaseReactActivity context, ArrayList<Page> pages) {
        mContext = context;
        mPages = pages;
        mReactInstanceManager = RctManager.getInstance().getReactInstanceManager();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Log.v(TAG, "instantiateItem" + position);
        String screenId = mPages.get(position).screenId;
        View view = new RctView(mContext, mReactInstanceManager, screenId);
        container.addView(view);
        return view;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object view) {
        container.removeView((View) view);
    }

    @Override
    public int getCount() {
        return mPages.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mPages.get(position).title;
    }

    public static ArrayList<Page> createDataSet(ReadableArray tabs) {
        ArrayList<Page> ret = new ArrayList<>();
        for(int i = 0; i < tabs.size(); i++) {
            ret.add(new Page(tabs.getMap(i)));
        }
        return ret;
    }
}
