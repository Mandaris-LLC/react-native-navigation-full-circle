package com.reactnativenavigation.layouts;

import android.content.Context;
import android.support.design.widget.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 * Created by guyc on 06/03/16.
 */
public class ReactTabLayout extends TabLayout {

    public enum InitialState {
        TAB_POSITION_UNSET,
        TAB_POSITION_SET,
        TAB_SELECTED
    }

    private List<Tab> mTabs = new ArrayList<>();
    private List<Integer> mIds = new ArrayList<>();
    private InitialState mInitialState = InitialState.TAB_POSITION_UNSET;
    private int mInitialTabPosition;

    public ReactTabLayout(Context context) {
        super(context);
    }


    @Override
    public void addTab(Tab tab) {
        super.addTab(tab);
        mTabs.add(tab);
        mIds.add(UUID.randomUUID().hashCode());
    }

    public int getId(int index) {
        return mIds.get(index);
    }

    public int indexOf(Tab tab) {
        return mTabs.indexOf(tab);
    }

    @Override
    public void removeAllTabs() {
        mTabs.clear();
        mIds.clear();
        super.removeAllTabs();
    }

    public InitialState getInitialState() {
        return mInitialState;
    }

    public int getInitialTabPosition() {
        return mInitialTabPosition;
    }

    public void setInitialState(InitialState initialState) {
        mInitialState = initialState;
    }

    public void setInitialTabPosition(int initialTabPosition) {
        mInitialTabPosition = initialTabPosition;
    }
}

