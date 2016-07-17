package com.reactnativenavigation.layouts;

import android.app.Activity;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class LayoutFactory {
    public static class SideMenuParams {
        private boolean enabled;
    }

    public static class BottomTabsParams {
        private boolean enabled;
    }

    public static class Params {
        private SideMenuParams sideMenu;
        private BottomTabsParams bottomTabs;
    }

    public static Layout create(Activity activity, Params params) {
        LinearLayout root = createRoot(activity);
        FrameLayout content = createContent(activity);
        if (params.sideMenu.enabled) {
            DrawerLayout sideMenu = createSideMenu(activity, content);
            root.addView(sideMenu, new LinearLayout.LayoutParams(MATCH_PARENT, 0, 1));
        } else {
            root.addView(content, new LinearLayout.LayoutParams(MATCH_PARENT, 0, 1));
        }
        if (params.bottomTabs.enabled) {
            AHBottomNavigation bottomTabs = createBottomTabs(activity);
            root.addView(bottomTabs, new LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT));
        }

        return null;
    }

    private static AHBottomNavigation createBottomTabs(Activity activity) {
        return new AHBottomNavigation(activity);
    }

    private static LinearLayout createRoot(Activity activity) {
        LinearLayout root = new LinearLayout(activity);
        root.setOrientation(LinearLayout.VERTICAL);
        return root;
    }

    private static DrawerLayout createSideMenu(Activity activity, FrameLayout content) {
        DrawerLayout drawerLayout = new DrawerLayout(activity);
        FrameLayout drawerContent = new FrameLayout(activity);
        drawerLayout.addView(content, new DrawerLayout.LayoutParams(MATCH_PARENT, 0, 1));
        DrawerLayout.LayoutParams drawerContentParams = new DrawerLayout.LayoutParams(WRAP_CONTENT, MATCH_PARENT);
        drawerContentParams.gravity = Gravity.START;
        drawerLayout.addView(drawerContent, drawerContentParams);
        return drawerLayout;
    }

    private static FrameLayout createContent(Activity activity) {
        return new FrameLayout(activity);
    }
}
