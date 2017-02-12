package com.reactnativenavigation.layout.bottomtabs;

import android.app.Activity;
import android.view.View;
import android.widget.RelativeLayout;

public class BottomTabsContainer extends RelativeLayout {

    private BottomTabs bottomTabs;

    public BottomTabsContainer(Activity activity, BottomTabsCreator bottomTabsCreator) {
        super(activity);
        createBottomTabs(bottomTabsCreator);
    }

    public void setTabContent(View tab) {
        bottomTabs.addTab("#0");

        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        addView(tab, lp);
    }

    private void createBottomTabs(BottomTabsCreator bottomTabsCreator) {
        bottomTabs = bottomTabsCreator.create();
        bottomTabs.attach(this);
    }
}
