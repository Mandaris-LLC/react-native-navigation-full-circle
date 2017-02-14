package com.reactnativenavigation.layout.bottomtabs;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import static android.view.View.generateViewId;
import static android.widget.RelativeLayout.ALIGN_PARENT_BOTTOM;

public class BottomTabs implements BottomNavigationView.OnNavigationItemSelectedListener {

    interface BottomTabsSelectionListener {
        void onTabSelected(int index);
    }

    private BottomNavigationView bottomNavigationView;
    private BottomTabsSelectionListener listener;

    public void attach(RelativeLayout parentLayout) {
        createBottomNavigation(parentLayout);
        addBottomNavigationToParent(parentLayout);
    }

    public void setSelectionListener(BottomTabsSelectionListener listener) {
        this.listener = listener;
    }

    public void add(String label) {
        int tabId = size();
        bottomNavigationView.getMenu().add(0, tabId, Menu.NONE, label);
    }

    public int size() {
        return bottomNavigationView.getMenu().size();
    }

    int getViewId() {
        return bottomNavigationView.getId();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        listener.onTabSelected(item.getItemId());
        return true;
    }

    private void createBottomNavigation(RelativeLayout parentLayout) {
        bottomNavigationView = new BottomNavigationView(parentLayout.getContext());
        bottomNavigationView.setId(generateViewId());
        bottomNavigationView.setBackgroundColor(Color.DKGRAY);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    private void addBottomNavigationToParent(RelativeLayout parentLayout) {
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        lp.addRule(ALIGN_PARENT_BOTTOM);
        bottomNavigationView.setLayoutParams(lp);
        parentLayout.addView(bottomNavigationView, lp);
    }
}
