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

    public void setTabContent(View tabContent) {
        bottomTabs.addTab("#0", tabContent);
    }

    private void createBottomTabs(BottomTabsCreator bottomTabsCreator) {
        bottomTabs = bottomTabsCreator.create();
        bottomTabs.attach(this);
    }
}
