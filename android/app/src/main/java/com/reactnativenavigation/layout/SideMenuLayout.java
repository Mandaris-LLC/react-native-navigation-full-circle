package com.reactnativenavigation.layout;

import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.view.View;

public class SideMenuLayout extends DrawerLayout implements StackLayout {
    private StackLayout stackLayout;

    public SideMenuLayout(Context context) {
        super(context);
    }

    @Override
    public void addView(View child) {
        super.addView(child);
        if (child instanceof StackLayout) {
            stackLayout = (StackLayout) child;
        }
    }

    @Override
    public void push(View view) {
        stackLayout.push(view);
    }

    @Override
    public void pop() {
        stackLayout.pop();
    }

    @Override
    public View asView() {
        return this;
    }
}
