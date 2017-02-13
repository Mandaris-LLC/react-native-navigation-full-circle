package com.reactnativenavigation.layout.bottomtabs;

import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import java.util.concurrent.atomic.AtomicInteger;

import static android.widget.RelativeLayout.ALIGN_PARENT_BOTTOM;

public class BottomTabs implements BottomNavigationView.OnNavigationItemSelectedListener {

    public interface BottomTabsSelectionListener {
        void onTabSelected(int index);
    }

    private static final AtomicInteger viewId = new AtomicInteger(1);

    private BottomNavigationView bottomNavigationView;
    private BottomTabsSelectionListener listener;

    public void attach(RelativeLayout parentLayout) {
        createBottomNavigation(parentLayout);
        addButtomNavigationToParent(parentLayout);
    }

    public void setSelectionListener(BottomTabsSelectionListener listener) {
        this.listener = listener;
    }

    public void add(String label) {
        int tabId = getTabsCount();
        bottomNavigationView.getMenu().add(0, tabId, Menu.NONE, label);
    }

    public int getTabsCount() {
        return bottomNavigationView.getMenu().size();
    }

    public int getViewId() {
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

    private void addButtomNavigationToParent(RelativeLayout parentLayout) {
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        lp.addRule(ALIGN_PARENT_BOTTOM);
        bottomNavigationView.setLayoutParams(lp);
        parentLayout.addView(bottomNavigationView, lp);
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
