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

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private ArrayList<Screen> mScreens;
    private ViewPagerAdapter mAdapter;

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

    private void setupToolbar() {
        setSupportActionBar(mToolbar);
        mToolbar.setScreens(mScreens);
    }

    private void setupViewPager() {
        mAdapter = new ViewPagerAdapter(this, mViewPager, mToolbar, mScreens);
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setOnTabSelectedListener(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        boolean ret = super.onCreateOptionsMenu(menu);
        mToolbar.handleOnCreateOptionsMenuAsync();
        return ret;
    }

    @Override
    public void push(Screen screen) {
        super.push(screen);
        mAdapter.push(screen);
    }

    @Override
    public Screen pop(String navigatorId) {
        super.pop(navigatorId);
        return mAdapter.pop(navigatorId);
    }

    @Override
    public String getCurrentNavigatorId() {
        return mAdapter.getNavigatorId(mViewPager.getCurrentItem());
    }

    @Override
    public int getScreenStackSize() {
        return mAdapter.getStackSizeForNavigatorId(getCurrentNavigatorId());
    }
}
