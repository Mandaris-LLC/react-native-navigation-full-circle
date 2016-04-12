package com.reactnativenavigation.adapters;

import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.react.ReactInstanceManager;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.reactnativenavigation.activities.BaseReactActivity;
import com.reactnativenavigation.core.RctManager;
import com.reactnativenavigation.core.objects.Screen;
import com.reactnativenavigation.views.ScreenStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by guyc on 02/04/16.
 */
public class ViewPagerAdapter extends PagerAdapter implements TabLayout.OnTabSelectedListener {

    private static final String EVENT_ON_TAB_SELECTED = "OnTabSelected";

    private BaseReactActivity mContext;
    private ViewPager mViewPager;
    private Toolbar mToolbar;
    private final ReactInstanceManager mReactInstanceManager;
    private final ArrayList<ScreenStack> screenStacks;
    private final Map<String, ScreenStack> stacksByNavId;


    public ViewPagerAdapter(BaseReactActivity context, ViewPager viewPager, Toolbar toolbar,
                            ArrayList<Screen> screens) {
        mContext = context;
        mViewPager = viewPager;
        mToolbar = toolbar;
        screenStacks = new ArrayList<>();
        stacksByNavId  = new HashMap<>();
        for(Screen screen: screens){
            ScreenStack stack = new ScreenStack(context);
            stack.push(screen);
            screenStacks.add(stack);
            stacksByNavId.put(screen.navigatorId, stack);
        }
        mReactInstanceManager = RctManager.getInstance().getReactInstanceManager();
    }

    public void pushScreen(Screen screen){
        ScreenStack stack = stacksByNavId.get(screen.navigatorId);
        stack.push(screen);
    }

    public Screen pop(String navID){
        ScreenStack stack = stacksByNavId.get(navID);
        if(stack != null)
            return stack.pop();
        return null;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ScreenStack view = screenStacks.get(position);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object view) {
        container.removeView((View) view);
    }

    @Override
    public int getCount() {
        return screenStacks.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return screenStacks.get(position).peek().title;
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        // Set the viewPager's current item
        int position = tab.getPosition();
        mViewPager.setCurrentItem(position);

        // Send tab selected event
        WritableMap params = Arguments.createMap();
        Screen screen = screenStacks.get(position).peek();
        params.putString(Screen.KEY_NAVIGATOR_EVENT_ID, screen.navigatorEventId);

//        mToolbar.setupToolbarButtonsAsync(mScreens.get(position));

        RctManager.getInstance().sendEvent(EVENT_ON_TAB_SELECTED, screen.navigatorEventId, params);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
