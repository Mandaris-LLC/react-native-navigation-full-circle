package com.reactnativenavigation.layouts;

import android.content.Context;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.facebook.react.ReactInstanceManager;
import com.reactnativenavigation.views.ContentView;
import com.reactnativenavigation.views.ScrollDirectionListener;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class ScreenLayout extends LinearLayout implements ScrollDirectionListener.OnScrollChanged {

    private final ReactInstanceManager reactInstanceManager;
    private final String moduleName;
    private final Bundle passProps;
    private ContentView contentView;
    private TopBar topBar;

    public ScreenLayout(Context context, ReactInstanceManager reactInstanceManager, String moduleName, Bundle passProps) {
        super(context);
        this.reactInstanceManager = reactInstanceManager;
        this.moduleName = moduleName;
        this.passProps = passProps;
        setOrientation(VERTICAL);

        createViews();
    }

    private void createViews() {
        addTopBar();
        addContentView();
    }

    private void addTopBar() {
        topBar = new TopBar(getContext());
        addView(topBar, new LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT));
    }

    private void addContentView() {
        contentView = new ContentView(getContext(), reactInstanceManager, moduleName, passProps, this);
        addView(contentView, new LayoutParams(MATCH_PARENT, MATCH_PARENT));
    }

    @Override
    public void onScrollChanged(ScrollDirectionListener.Direction direction) {

    }
}
