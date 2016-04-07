package com.reactnativenavigation.managers;

import android.support.design.widget.CoordinatorLayout;
import android.view.View;

import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;

import java.util.Map;

/**
 * Created by guyc on 19/03/16.
 */
@Deprecated
public class CoordinatorLayoutManager extends ViewGroupManager<CoordinatorLayout> {
    private static final String REACT_CLASS = "CoordinatorLayout";
    private static final String COMMAND_CHILD_FLAGS = "setChildFlags";
    private static final int COMMAND_SET_CHILD_FLAGS_TYPE = 1;

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @Override
    public boolean needsCustomLayoutForChildren() {
        return true;
    }

    @Override
    protected CoordinatorLayout createViewInstance(ThemedReactContext reactContext) {
        CoordinatorLayout coordinatorLayout = new CoordinatorLayout(reactContext);
        coordinatorLayout.setFitsSystemWindows(true);
        return coordinatorLayout;
    }


    @Override
    public void addView(CoordinatorLayout parent, View child, int index) {
        child.setFitsSystemWindows(true);
        super.addView(parent, child, index);
    }

    @Override
    public Map<String, Integer> getCommandsMap() {
        return MapBuilder.of(COMMAND_CHILD_FLAGS, COMMAND_SET_CHILD_FLAGS_TYPE);
    }
}
