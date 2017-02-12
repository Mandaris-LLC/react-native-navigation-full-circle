package com.reactnativenavigation.layout.bottomtabs;

import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.BottomNavigationView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import java.util.concurrent.atomic.AtomicInteger;

import static android.widget.RelativeLayout.ALIGN_PARENT_BOTTOM;

public class BottomTabs {

    private static final AtomicInteger viewId = new AtomicInteger(1);

    private BottomNavigationView bottomNavigationView;

    public void attach(RelativeLayout parentLayout) {
        bottomNavigationView = new BottomNavigationView(parentLayout.getContext());
        bottomNavigationView.setId(generateViewId());
        bottomNavigationView.setBackgroundColor(Color.DKGRAY);

        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        lp.addRule(ALIGN_PARENT_BOTTOM);
        bottomNavigationView.setLayoutParams(lp);
        parentLayout.addView(bottomNavigationView, lp);
    }

    public void addTab(String label, View tabContent) {
        bottomNavigationView.getMenu().add(label);

        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        params.addRule(RelativeLayout.ABOVE, bottomNavigationView.getId());

        ViewGroup parent = (ViewGroup) bottomNavigationView.getParent();
        parent.addView(tabContent, params);
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
