package com.reactnativenavigation.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.reactnativenavigation.R;
import com.reactnativenavigation.adapters.Page;
import com.reactnativenavigation.adapters.ViewPagerAdapter;
import com.reactnativenavigation.core.RctManager;

import java.util.ArrayList;

/**
 * Created by guyc on 02/04/16.
 */
public class TabActivity extends CompoundReactActivity {
    private static final String TAG = "TabActivity";
    public static final String EXTRA_TABS = "extraTabs";

    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.v(TAG, "onCreate");
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void handleOnCreate() {
        mReactInstanceManager = RctManager.getInstance().getReactInstanceManager();

        setContentView(R.layout.tab_activity);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);

        setupToolbar();
        setupViews();
    }


    private void setupToolbar() {
        setSupportActionBar(mToolbar);
    }
    private void setupViews() {
        ArrayList<Page> pages = (ArrayList<Page>) getIntent().getSerializableExtra(EXTRA_TABS);
        ViewPagerAdapter adapter = new ViewPagerAdapter(this, pages);
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onStart() {
        Log.v(TAG, "onStart");
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.v(TAG, "onResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
