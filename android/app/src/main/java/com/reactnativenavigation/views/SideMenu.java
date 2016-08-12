package com.reactnativenavigation.views;

import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.reactnativenavigation.params.SideMenuParams;
import com.reactnativenavigation.utils.ViewUtils;

public class SideMenu extends DrawerLayout {

    private ContentView sideMenuView;
    private RelativeLayout contentContainer;

    public RelativeLayout getContentContainer() {
        return contentContainer;
    }

    public void destroy() {
        sideMenuView.ensureUnmountOnDetachedFromWindow();
        removeView(sideMenuView);
    }

    public void openDrawer() {
        openDrawer(Gravity.LEFT);
    }

    public SideMenu(Context context, SideMenuParams sideMenuParams) {
        super(context);
        createContentContainer();
        createSideMenu(sideMenuParams);
    }

    private void createContentContainer() {
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        contentContainer = new RelativeLayout(getContext());
        contentContainer.setId(ViewUtils.generateViewId());
        addView(contentContainer, lp);
    }

    private void createSideMenu(SideMenuParams sideMenuParams) {
        sideMenuView = new ContentView(getContext(),
                sideMenuParams.screenId,
                sideMenuParams.navigationParams);

        DrawerLayout.LayoutParams lp =
                new LayoutParams((int) ViewUtils.convertDpToPixel(240), ViewGroup.LayoutParams.MATCH_PARENT);
        lp.gravity = Gravity.START;
        addView(sideMenuView, lp);
    }
}
