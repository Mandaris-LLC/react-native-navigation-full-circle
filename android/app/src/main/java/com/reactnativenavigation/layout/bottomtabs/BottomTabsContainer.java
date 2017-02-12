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

    public void addTabContent(View tabContent) {
        bottomTabs.addTab("#" + bottomTabs.getTabsCount(), tabContent);
    }

    private void createBottomTabs(BottomTabsCreator bottomTabsCreator) {
        bottomTabs = bottomTabsCreator.create();
        bottomTabs.attach(this);
    }
}
