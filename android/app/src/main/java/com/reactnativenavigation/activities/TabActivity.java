package com.reactnativenavigation.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("GUY", "onCreate TabActivity");
    }

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
        ViewPagerAdapter adapter = new ViewPagerAdapter(this, mViewPager, mToolbar, mScreens);
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setOnTabSelectedListener(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("GUY", "onResume TabActivity");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        boolean ret = super.onCreateOptionsMenu(menu);
        mToolbar.handleOnCreateOptionsMenuAsync();
        return ret;
    }
}
