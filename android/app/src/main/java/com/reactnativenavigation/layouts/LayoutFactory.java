package com.reactnativenavigation.layouts;

import android.app.Activity;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class LayoutFactory {
    public static class SideMenuParams {
        private boolean enabled;
    }

    public static class BottomTabsParams {

    }

    public static class Params {
        private SideMenuParams sideMenu;
        private BottomTabsParams bottomTabs;
    }

    public static Layout create(Activity activity, Params params) {
        LinearLayout root = createRoot(activity);
        FrameLayout content = createContent(activity);
        if (params.sideMenu.enabled) {
            createSideMenu(activity, content);
        }
        return null;
    }

    private static LinearLayout createRoot(Activity activity) {
        LinearLayout root = new LinearLayout(activity);
        root.setOrientation(LinearLayout.VERTICAL);
        return root;
    }

    private static void createSideMenu(Activity activity, FrameLayout content) {
        DrawerLayout drawerLayout = new DrawerLayout(activity);
        FrameLayout drawerContent = new FrameLayout(activity);
        drawerLayout.addView(content, new DrawerLayout.LayoutParams(MATCH_PARENT, 0, 1));
        DrawerLayout.LayoutParams drawerContentParams = new DrawerLayout.LayoutParams(WRAP_CONTENT, MATCH_PARENT);
        drawerContentParams.gravity = Gravity.START;
        drawerLayout.addView(drawerContent, drawerContentParams);
    }

    private static FrameLayout createContent(Activity activity) {
        FrameLayout content = new FrameLayout(activity);
        return content;
    }
}
