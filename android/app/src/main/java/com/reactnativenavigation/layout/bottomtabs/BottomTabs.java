package com.reactnativenavigation.layout.bottomtabs;

import android.support.design.widget.BottomNavigationView;
import android.widget.RelativeLayout;

import static android.widget.RelativeLayout.ALIGN_PARENT_BOTTOM;

public class BottomTabs {

    private BottomNavigationView bottomNavigationView;

    public void attach(RelativeLayout parentLayout) {
        bottomNavigationView = new BottomNavigationView(parentLayout.getContext());

        RelativeLayout.LayoutParams lp =
                new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.addRule(ALIGN_PARENT_BOTTOM);
        bottomNavigationView.setLayoutParams(lp);
        parentLayout.addView(bottomNavigationView, lp);
    }

    public void addTab(String label) {
        bottomNavigationView.getMenu().add(label);
    }
}
