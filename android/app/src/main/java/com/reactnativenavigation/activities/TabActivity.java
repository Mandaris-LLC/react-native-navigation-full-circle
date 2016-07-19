package com.reactnativenavigation.activities;

import android.support.v4.view.ViewPager;
import android.view.Menu;

import com.reactnativenavigation.R;
import com.reactnativenavigation.adapters.ViewPagerAdapter;
import com.reactnativenavigation.core.objects._Screen;
import com.reactnativenavigation.utils.StyleHelper;
import com.reactnativenavigation.views.RnnTabLayout;
import com.reactnativenavigation.views.RnnToolBar;

import java.util.ArrayList;

/**
 * This class is currently not supported and will be removed in future release.
 * Created by guyc on 02/04/16.
 */
@Deprecated
public class TabActivity extends BaseReactActivity {
    public static final String EXTRA_SCREENS = "extraScreens";

    private RnnTabLayout mTabLayout;
    private ViewPager mViewPager;
    private ViewPagerAdapter mAdapter;

    @Override
    protected void handleOnCreate() {
        super.handleOnCreate();
        reactInstanceManager = RctManager.getInstance().getReactInstanceManager();

        setContentView(R.layout.tab_activity);
        toolbar = (RnnToolBar) findViewById(R.id.toolbar);
        mTabLayout = (RnnTabLayout) findViewById(R.id.tabLayout);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);

        ArrayList<_Screen> screens = (ArrayList<_Screen>) getIntent().getSerializableExtra(EXTRA_SCREENS);

        setupToolbar(screens);
        setupViewPager(screens);
    }

    private void setupToolbar(ArrayList<_Screen> screens) {
        _Screen initialScreen = screens.get(0);
        toolbar.setScreens(screens);
        toolbar.update(initialScreen);
        setNavigationStyle(initialScreen);
    }

    public void setNavigationStyle(_Screen screen) {
        mTabLayout.setStyle(screen);
    }

    private void setupViewPager(ArrayList<_Screen> screens) {
        mAdapter = new ViewPagerAdapter(this, mViewPager, toolbar, screens);
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setOnTabSelectedListener(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        boolean ret = super.onCreateOptionsMenu(menu);
        toolbar.handleOnCreateOptionsMenuAsync();
        return ret;
    }

    @Override
    public void removeAllReactViews() {

    }

    @Override
    public void push(_Screen screen) {
        super.push(screen);
        StyleHelper.updateStyles(toolbar, screen);
        mAdapter.push(screen);
    }

    @Override
    public _Screen pop(String navigatorId) {
        super.pop(navigatorId);
        _Screen popped = mAdapter.pop(navigatorId);
        setNavigationStyle(getCurrentScreen());
        return popped;
    }

    @Override
    public _Screen getCurrentScreen() {
        return mAdapter.peek(getCurrentNavigatorId());
    }

    @Override
    protected String getCurrentNavigatorId() {
        return mAdapter.getNavigatorId(mViewPager.getCurrentItem());
    }

    @Override
    public int getScreenStackSize() {
        return mAdapter.getStackSizeForNavigatorId(getCurrentNavigatorId());
    }
}
