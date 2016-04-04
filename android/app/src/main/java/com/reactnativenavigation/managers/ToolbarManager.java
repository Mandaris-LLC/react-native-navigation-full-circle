package com.reactnativenavigation.managers;

import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;

import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.reactnativenavigation.R;

/**
 *
 * Created by guyc on 19/03/16.
 */
public class ToolbarManager extends ViewGroupManager<Toolbar> {
    private static final String REACT_CLASS = "Toolbar";

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @Override
    public boolean needsCustomLayoutForChildren() {
        return true;
    }

    @Override
    protected Toolbar createViewInstance(ThemedReactContext reactContext) {
        return (Toolbar)
                ((LayoutInflater) reactContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.toolbar, null, false);
    }

    @ReactProp(name = "title")
    public void setTitle(Toolbar view, String title) {
        view.setTitle(title);
    }
}
