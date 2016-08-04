package com.reactnativenavigation.screens;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.reactnativenavigation.NavigationApplication;
import com.reactnativenavigation.params.TopTabParams;
import com.reactnativenavigation.views.ContentView;

import java.util.List;

public class ContentViewPagerAdapter extends PagerAdapter {

    private List<ContentView> contentViews;
    private List<TopTabParams> topTabParams;
    private int currentPosition = 0;

    public ContentViewPagerAdapter(List<ContentView> contentViews, List<TopTabParams> topTabParams) {
        this.contentViews = contentViews;
        this.topTabParams = topTabParams;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return contentViews.get(position);
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
        if (position != currentPosition) {
            currentPosition = position;
            sendPageChangeEvent();
        }
    }

    private void sendPageChangeEvent() {
        WritableMap data = Arguments.createMap();
        String navigatorEventId = contentViews.get(currentPosition).getNavigatorEventId();
        data.putInt("position", currentPosition);
        NavigationApplication.instance.sendNavigatorEvent("selectedTabChanged", navigatorEventId, data);
    }

    @Override
    public int getCount() {
        return contentViews.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return topTabParams.get(position).title;
    }
}
