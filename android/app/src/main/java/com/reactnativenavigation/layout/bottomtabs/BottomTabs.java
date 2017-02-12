package com.reactnativenavigation.layout.bottomtabs;

import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static android.widget.RelativeLayout.ALIGN_PARENT_BOTTOM;

public class BottomTabs implements BottomNavigationView.OnNavigationItemSelectedListener {

    private static final AtomicInteger viewId = new AtomicInteger(1);

    private BottomNavigationView bottomNavigationView;
    private List<View> tabsContent;
    private int currentTabId;

    public void attach(RelativeLayout parentLayout) {
        createBottomNavigation(parentLayout);
        addButtomNavigationToParent(parentLayout);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        tabsContent = new ArrayList<>(bottomNavigationView.getMaxItemCount());
    }

    public void addTab(String label, View tabContent) {
        int tabId = getTabsCount();
        bottomNavigationView.getMenu().add(0, tabId, Menu.NONE, label);

        attachTabContent(tabContent);
        tabsContent.add(tabContent);

        if (isFirstTabEver()) {
            currentTabId = 0;
            showCurrentTab();
        } else {
            hideTab(tabId);
        }
    }

    public int getTabsCount() {
        return bottomNavigationView.getMenu().size();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        final int id = item.getItemId();

        hideCurrentTab();
        currentTabId = id;
        showCurrentTab();
        return true;
    }

    private void addButtomNavigationToParent(RelativeLayout parentLayout) {
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        lp.addRule(ALIGN_PARENT_BOTTOM);
        bottomNavigationView.setLayoutParams(lp);
        parentLayout.addView(bottomNavigationView, lp);
    }

    private void createBottomNavigation(RelativeLayout parentLayout) {
        bottomNavigationView = new BottomNavigationView(parentLayout.getContext());
        bottomNavigationView.setId(generateViewId());
        bottomNavigationView.setBackgroundColor(Color.DKGRAY);
    }

    private boolean isFirstTabEver() {
        return bottomNavigationView.getMenu().size() == 1;
    }

    private void attachTabContent(View tabContent) {
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        params.addRule(RelativeLayout.ABOVE, bottomNavigationView.getId());

        ViewGroup parent = (ViewGroup) bottomNavigationView.getParent();
        parent.addView(tabContent, params);
    }

    private void showCurrentTab() {
        tabsContent.get(currentTabId).setVisibility(View.VISIBLE);
    }

    private void hideCurrentTab() {
        hideTab(currentTabId);
    }

    private void hideTab(int tabId) {
        tabsContent.get(tabId).setVisibility(View.GONE);
    }

    private int generateViewId() {
        if (Build.VERSION.SDK_INT >= 17) {
            return View.generateViewId();
        } else {
            return compatGenerateViewId();
        }
    }

    private int compatGenerateViewId() {
        while(true) {
            final int result = viewId.get();
            // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
            int newValue = result + 1;
            if (newValue > 0x00FFFFFF) newValue = 1; // Roll over to 1, not 0.
            if (viewId.compareAndSet(result, newValue)) {
                return result;
            }
        }
    }
}
