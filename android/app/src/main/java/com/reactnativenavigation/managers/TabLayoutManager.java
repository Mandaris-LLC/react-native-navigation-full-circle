package com.reactnativenavigation.managers;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.infer.annotation.Assertions;
import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.events.EventDispatcher;
import com.reactnativenavigation.layouts.ReactTabLayout;
import com.reactnativenavigation.layouts.ReactTabLayout.InitialState;
import com.reactnativenavigation.utils.ResourceUtils;

import java.util.Map;

import static android.support.design.widget.TabLayout.Tab;

/**
 *
 * Created by guyc on 06/03/16.
 */
public class TabLayoutManager extends ViewGroupManager<ReactTabLayout> {
    private static final String REACT_CLASS = "TabLayout";
    public static final int COMMAND_SET_VIEW_PAGER_TYPE = 1;
    public static final String PARAM_ICON = "icon";
    public static final String PARAM_TEXT = "text";
    public static final String REGISTRATION_NAME = "registrationName";
    public static final String EVENT_ON_TAB_SELECTED = "onTabSelected";
    public static final String COMMAND_SET_VIEW_PAGER = "setViewPager";
    private static final String TAG = TabLayoutManager.class.getSimpleName();

    private enum TabMode {
        SCROLLABLE(TabLayout.MODE_SCROLLABLE),
        FIXED(TabLayout.MODE_FIXED);

        int mode;

        TabMode(int mode) {
            this.mode = mode;
        }

        public static TabMode fromString(String text) {
            return text != null ? Enum.valueOf(TabMode.class, text.trim().toUpperCase()) : null;
        }
    }

    private enum TabGravity {
        FILL(TabLayout.GRAVITY_FILL),
        CENTER(TabLayout.GRAVITY_CENTER);

        int gravity;

        TabGravity(int gravity) {
            this.gravity = gravity;
        }

        public static TabGravity fromString(String text) {
            return text != null ? Enum.valueOf(TabGravity.class, text.trim().toUpperCase()) : null;
        }
    }

    private EventDispatcher mDispatcher;

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @Override
    protected ReactTabLayout createViewInstance(ThemedReactContext reactContext) {
        ReactTabLayout tabLayout = new ReactTabLayout(reactContext);
//        setupWithViewPager(tabLayout);
        return tabLayout;
    }

    private void setupWithViewPager(final ReactTabLayout tabLayout, final ReadableArray tabs) {
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                if (tabLayout.getParent() == null) {
                    Log.e(TAG, "Tried to setupViewPager but parent is null");
                    return;
                }
                
                ViewGroup parent = (ViewGroup) tabLayout.getParent().getParent();
                if (parent == null) {
                    return;
                }

                for (int i = 0; i < parent.getChildCount(); i++) {
                    View child = parent.getChildAt(i);
                    if (child instanceof ViewPager) {
                        // Setup TabLayout with ViewPager
                        ViewPager viewPager = (ViewPager) child;
                        tabLayout.setupWithViewPager(viewPager);

                        // Add tabs
                        if (tabs != null) {
                            tabLayout.removeAllTabs();
                            populateTabLayoutWithTabs(tabLayout, tabs);
                            tabLayout.setOnTabSelectedListener(new OnTabSelectedListener(viewPager, tabLayout, TabLayoutManager.this));
                        }
                    }
                }

                Log.i("GUY", "childCount" + parent.getChildCount());
                for (int i = 0; i < parent.getChildCount(); i++) {
                    Log.d("GUY", "[" + 1 + "] " + parent.getChildAt(i).getClass().getSimpleName());
                }
            }
        });

    }

    @Override
    public void addView(ReactTabLayout tabLayout, View child, int index) {
        Tab tab = tabLayout.newTab();
        tabLayout.addTab(tab);

        // when initial position was stored, update tab selection
        if (tabLayout.getInitialState() == InitialState.TAB_POSITION_SET &&
            tabLayout.getInitialTabPosition() == index) {
            tabLayout.setInitialState(InitialState.TAB_SELECTED);
            tab.select();
        }
    }

    @ReactProp(name = "tabs")
    public void setTabs(ReactTabLayout tabLayout, ReadableArray tabs) {
        setupWithViewPager(tabLayout, tabs);
    }


    @ReactProp(name = "selectedTab", defaultInt = 0)
    public void setSelectedTab(ReactTabLayout view, int selectedTab) {
        selectTab(view, selectedTab);
    }

    private void selectTab(ReactTabLayout tabLayout, int position) {
        if (position < 0 || position >= tabLayout.getTabCount()) {
            Log.w(REACT_CLASS, "Tried to select tab " + position + " but index is out of bounds");
            return;
        }

        Tab tab = tabLayout.getTabAt(position);
        if (tab != null) {
            tab.select();
        }
    }

    @ReactProp(name = "selectedTabIndicatorColor")
    public void setSelectedTabIndicatorColor(ReactTabLayout view, int indicatorColor) {
        view.setSelectedTabIndicatorColor(indicatorColor);
    }

    @ReactProp(name = "tabMode")
    public void setTabMode(ReactTabLayout view, String mode) {
        TabMode tabMode = TabMode.fromString(mode);
        if (tabMode == null) {
            Log.w(REACT_CLASS, "Invalid tabMode [" + tabMode + "]");
        } else {
            view.setTabMode(tabMode.mode);
        }
    }

    @ReactProp(name = "tabGravity")
    public void setTabGravity(ReactTabLayout view, String gravity) {
        TabGravity tabGravity = TabGravity.fromString(gravity);
        if (tabGravity == null) {
            Log.w(REACT_CLASS, "Invalid tabGravity [" + gravity + "]");
        } else {
            view.setTabGravity(tabGravity.gravity);
        }
    }

    @Override
    public boolean needsCustomLayoutForChildren() {
        return true;
    }

    @Override
    protected void addEventEmitters(ThemedReactContext reactContext, ReactTabLayout view) {
        mDispatcher = reactContext.getNativeModule(UIManagerModule.class).getEventDispatcher();
    }

//    @Override
//    public Map<String, Object> getExportedCustomDirectEventTypeConstants() {
//        return MapBuilder.of(
//                TabSelectedEvent.EVENT_NAME, (Object) MapBuilder.of(REGISTRATION_NAME, EVENT_ON_TAB_SELECTED)
//        );
//    }

    /*
    * This method allows us to expose an interface which we can use from react.
    * If we obtain a reference to this native view, we can call `setViewPager` method and handle
    * the call in `receiveCommand`
    * */
    @Override
    public Map<String, Integer> getCommandsMap() {
        return MapBuilder.of(COMMAND_SET_VIEW_PAGER, COMMAND_SET_VIEW_PAGER_TYPE);
    }

    @Override
    public void receiveCommand(ReactTabLayout tabLayout, int commandType, ReadableArray args) {
        Assertions.assertNotNull(tabLayout);
        Assertions.assertNotNull(args);

        switch (commandType) {
            case COMMAND_SET_VIEW_PAGER_TYPE: {
                // Setup TabLayout with ViewPager
                int viewPagerId = args.getInt(0);
                ViewPager viewPager = (ViewPager) tabLayout.getRootView().findViewById(viewPagerId);
                tabLayout.setupWithViewPager(viewPager);

                // Add tabs
                ReadableArray tabs = args.getArray(1);
                if (tabs != null) {
                    tabLayout.removeAllTabs();
                    this.populateTabLayoutWithTabs(tabLayout, tabs);
//                    tabLayout.setOnTabSelectedListener(new OnTabSelectedListener(viewPager, tabLayout, this));
                }

                return;
            }

            default:
                throw new JSApplicationIllegalArgumentException(String.format(
                        "Unsupported command %d received by %s.", commandType, REACT_CLASS));
        }
    }

    private void populateTabLayoutWithTabs(ReactTabLayout tabLayout, ReadableArray tabs) {
        for (int i = 0; i < tabs.size(); i++) {
            ReadableMap tabMap = tabs.getMap(i);
            TabLayout.Tab tab = tabLayout.newTab();

            // Set tab text
            if (tabMap.hasKey(PARAM_TEXT)) {
                tab.setText(tabMap.getString(PARAM_TEXT));
            }

            // Set tab icon
            if (tabMap.hasKey(PARAM_ICON)) {
                Context ctx = tabLayout.getContext();
                Drawable icon = ResourceUtils.getDrawable(ctx, tabMap.getString(PARAM_ICON));
                if (icon != null) {
                    tab.setIcon(icon);
                }
            }

            tabLayout.addTab(tab);
        }
    }

    private static class OnTabSelectedListener extends TabLayout.ViewPagerOnTabSelectedListener {
        private final ReactTabLayout mTabLayout;
        private TabLayoutManager mTabLayoutManager;

        public OnTabSelectedListener(ViewPager viewPager, ReactTabLayout tabLayout, TabLayoutManager manager) {
            super(viewPager);
            mTabLayout = tabLayout;
            mTabLayoutManager = manager;
        }

        @Override
        public void onTabSelected(Tab tab) {
            super.onTabSelected(tab);
            int position = mTabLayout.indexOf(tab);
//            mTabLayoutManager.mDispatcher.dispatchEvent(new TabSelectedEvent(mTabLayout.getId(position), position));
//            mTabLayoutManager.mDispatcher.dispatchEvent(new TabSelectedEvent(mTabLayout.getId(), position));
        }

        @Override
        public void onTabUnselected(Tab tab) {
        }

        @Override
        public void onTabReselected(Tab tab) {
        }
    }
}
