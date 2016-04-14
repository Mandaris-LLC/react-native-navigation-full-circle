package com.reactnativenavigation.adapters;

import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.reactnativenavigation.activities.BaseReactActivity;
import com.reactnativenavigation.core.RctManager;
import com.reactnativenavigation.core.objects.Screen;
import com.reactnativenavigation.views.RnnToolBar;
import com.reactnativenavigation.views.ScreenStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by guyc on 02/04/16.
 */
public class ViewPagerAdapter extends PagerAdapter implements TabLayout.OnTabSelectedListener {

    private static final String EVENT_ON_TAB_SELECTED = "OnTabSelected";

    private ViewPager mViewPager;
    private RnnToolBar mToolbar;
    private final ArrayList<ScreenStack> screenStacks;
    private final ArrayList<String> navIDs;
    private final Map<String, ScreenStack> stacksByNavId;


    public ViewPagerAdapter(BaseReactActivity context, ViewPager viewPager, RnnToolBar toolbar,
                            ArrayList<Screen> screens) {
        mViewPager = viewPager;
        mToolbar = toolbar;
        screenStacks = new ArrayList<>();
        navIDs = new ArrayList<>();
        stacksByNavId = new HashMap<>();
        for (Screen screen : screens) {
            ScreenStack stack = new ScreenStack(context);
            stack.push(screen);
            screenStacks.add(stack);
            navIDs.add(screen.navigatorId);
            stacksByNavId.put(screen.navigatorId, stack);
        }
    }

    public void pushScreen(Screen screen) {
        ScreenStack stack = stacksByNavId.get(screen.navigatorId);
        stack.push(screen);
    }

    public Screen pop(String navID) {
        ScreenStack stack = stacksByNavId.get(navID);
        if (stack != null) {
            return stack.pop();
        }
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

        mToolbar.setupToolbarButtonsAsync(screenStacks.get(position).peek());

        RctManager.getInstance().sendEvent(EVENT_ON_TAB_SELECTED, screen.navigatorEventId, params);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    public String getNavID(int position) {
        return navIDs.get(position);
    }

    public int getStackSizeForNavigatorId(String activeNavigatorID) {
        return stacksByNavId.get(activeNavigatorID).getStackSize();
    }
}
