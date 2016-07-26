package com.reactnativenavigation.screens;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.reactnativenavigation.params.ScreenParams;
import com.reactnativenavigation.params.TopTabParams;
import com.reactnativenavigation.views.ContentView;

import java.util.ArrayList;
import java.util.List;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

public class TabbedScreen extends Screen {

    private List<ContentView> contentViews = new ArrayList<>();
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;

    public  TabbedScreen(Context context, ScreenParams screenParams) {
        super(context, screenParams);
    }

    @Override
    protected void createContent() {
        TabLayout tabLayout = topBar.initTabs();
        viewPager = new ViewPager(getContext());
        addView(viewPager);
        viewPagerAdapter = new ViewPagerAdapter(getContext(), viewPager, contentViews);
//        tabLayout.setupWithViewPager(viewPager);

        for (TopTabParams topTabParam : screenParams.topTabParams) {
            ContentView contentView = new ContentView(getContext(), topTabParam.screenId, screenParams.passProps, screenParams.navigationParams, this);
            viewPager.addView(contentView, addBelowTopBar());
            contentView.init();

            tabLayout.addTab(tabLayout.newTab().setText(topTabParam.title));
//            viewPagerAdapter.addTabPage(topTabParam.title);
        }

    }

    @NonNull
    private LayoutParams addBelowTopBar() {
        LayoutParams params = new LayoutParams(MATCH_PARENT, MATCH_PARENT);
        if (!screenParams.styleParams.drawUnderTopBar) {
            params.addRule(BELOW, topBar.getId());
        }
        return params;
    }

    @Override
    public void ensureUnmountOnDetachedFromWindow() {
        for (ContentView contentView: contentViews) {
            contentView.ensureUnmountOnDetachedFromWindow();
        }
    }

    @Override
    public void preventUnmountOnDetachedFromWindow() {
        for (ContentView contentView: contentViews) {
            contentView.preventUnmountOnDetachedFromWindow();
        }
    }


    public class ViewPagerAdapter extends PagerAdapter implements TabLayout.OnTabSelectedListener, ViewPager.OnPageChangeListener {

        private static final String EVENT_ON_TAB_SELECTED = "OnTabSelected";

        private ViewPager mViewPager;
        //        private final ArrayList<ScreenStack> mScreenStacks;
        private final ArrayList<String> mNavigatorIds;
        private List<ContentView> contentViews;
        //        private final Map<String, ScreenStack> mStackByNavigatorId;
        private int mCurrentPage = 0;

        public ViewPagerAdapter(Context context, ViewPager viewPager, List<ContentView> contentViews) {
            this.mViewPager = viewPager;
            this.contentViews = contentViews;
//            mScreenStacks = new ArrayList<>();
            this.mNavigatorIds = new ArrayList<>();
//            mStackByNavigatorId = new HashMap<> ();
//            for (ContentView contentView : contentViews) {
//                ScreenStack stack = new ScreenStack(context);
//                stack.push(screen);
//                mScreenStacks.add(stack);
//                mNavigatorIds.add(screen.navigatorId);
//                mStackByNavigatorId.put(screen.navigatorId, stack);
//            }
        }

//        public void push(Screen screen) {
//            ScreenStack stack = mStackByNavigatorId.get(screen.navigatorId);
//            Screen prevScreen = mScreenStacks.get(mCurrentPage).peek();
//            mToolbar.setupToolbarButtonsAsync(prevScreen, screen);
//            stack.push(screen);
//        }
//
//        public Screen pop(String navigatorId) {
//            ScreenStack stack = mStackByNavigatorId.get(navigatorId);
//            Screen oldScreen =  stack != null ? stack.pop() : null;
//            Screen newScreen = stack.peek();
//            mToolbar.setupToolbarButtonsAsync(oldScreen, newScreen);
//            return oldScreen;
//        }
//
//        public Screen peek(String navigatorId) {
//            ScreenStack stack = mStackByNavigatorId.get(navigatorId);
//            return stack != null ? stack.peek() : null;
//        }

//        @Override
//        public Object instantiateItem(ViewGroup container, int position) {
//            ScreenStack view = mScreenStacks.get(position);
//            container.addView(view);
//            return view;
//        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object view) {
            container.removeView((View) view);
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
//            return contentViews.get(position).peek().label;
            return "";
        }

        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            // Set the viewPager's current item
            int position = tab.getPosition();
            mViewPager.setCurrentItem(position);

//            // Set screen buttons
//            Screen prevScreen = mScreenStacks.get(mCurrentPage).peek();
//            Screen newScreen = mScreenStacks.get(position).peek();
//            mToolbar.setupToolbarButtonsAsync(prevScreen, newScreen);
//
//            // Set title
//            mToolbar.setTitle(newScreen.title == null ? "" : newScreen.title);
//
//            // Set navigation color
//            StyleHelper.updateStyles(mToolbar, newScreen);
//
//            // Send tab selected event
//            WritableMap params = Arguments.createMap();
//            Screen screen = mScreenStacks.get(position).peek();
//            RctManager.getInstance().sendEvent(EVENT_ON_TAB_SELECTED, screen, params);
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }

        public String getNavigatorId(int position) {
            return mNavigatorIds.get(position);
        }

        public int getStackSizeForNavigatorId(String activeNavigatorID) {
//            return mStackByNavigatorId.get(activeNavigatorID).getStackSize();
            return 0;
        }


        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            mCurrentPage = position;
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}
