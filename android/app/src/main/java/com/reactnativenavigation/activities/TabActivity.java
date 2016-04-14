package com.reactnativenavigation.activities;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.Menu;

import com.reactnativenavigation.R;
import com.reactnativenavigation.adapters.ViewPagerAdapter;
import com.reactnativenavigation.core.RctManager;
import com.reactnativenavigation.core.objects.Screen;
import com.reactnativenavigation.views.RnnToolBar;

import java.util.ArrayList;

/**
 * Created by guyc on 02/04/16.
 */
public class TabActivity extends BaseReactActivity {
    public static final String EXTRA_SCREENS = "extraScreens";

    private RnnToolBar mToolbar;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private ArrayList<Screen> mScreens;
    private ViewPagerAdapter adapter;

    @Override
    protected void handleOnCreate() {
        mReactInstanceManager = RctManager.getInstance().getReactInstanceManager();

        setContentView(R.layout.tab_activity);
        mToolbar = (RnnToolBar) findViewById(R.id.toolbar);
        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);

        mScreens = (ArrayList<Screen>) getIntent().getSerializableExtra(EXTRA_SCREENS);

        setupToolbar();
        setupViewPager();
    }

    @Override
    public void push(Screen screen) {
        adapter.pushScreen(screen);
    }

    @Override
    public Screen pop(String navID) {
        return adapter.pop(navID);
    }

    private void setupToolbar() {
        setSupportActionBar(mToolbar);
        mToolbar.setScreens(mScreens);
    }

    private void setupViewPager() {
        adapter = new ViewPagerAdapter(this, mViewPager, mToolbar, mScreens);
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setOnTabSelectedListener(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        boolean ret = super.onCreateOptionsMenu(menu);
        mToolbar.handleOnCreateOptionsMenuAsync();
        return ret;
    }

    @Override
    public String getActiveNavigatorID() {
        return adapter.getNavID(mViewPager.getCurrentItem());
    }

    @Override
    public int getScreenStackSize() {
        return adapter.getStackSizeForNavigatorId(getActiveNavigatorID());
    }
}
