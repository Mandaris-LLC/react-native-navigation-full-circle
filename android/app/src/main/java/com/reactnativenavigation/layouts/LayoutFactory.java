package com.reactnativenavigation.layouts;

import android.support.v7.app.AppCompatActivity;

import com.reactnativenavigation.params.ActivityParams;

public class LayoutFactory {
    public static Layout create(AppCompatActivity activity, ActivityParams params) {
        switch (params.type) {
            case TabBased:
                return createBottomTabsScreenLayout(activity, params);
            case SingleScreen:
            default:
                return createSingleScreenLayout(activity, params);
        }
    }

    private static Layout createSingleScreenLayout(AppCompatActivity activity, ActivityParams params) {
        return new SingleScreenLayout(activity, params.screenParams);
    }

    private static Layout createBottomTabsScreenLayout(AppCompatActivity activity, ActivityParams params) {
        return new BottomTabsLayout(activity, params);
    }
//
//    private static void addContentWithMenuIfNeeded(Activity activity, Params params, LinearLayout root, FrameLayout content) {
//        if (params.sideMenu.enabled) {
//            DrawerLayout sideMenu = createSideMenu(activity, content);
//            root.addView(sideMenu, new LinearLayout.LayoutParams(MATCH_PARENT, 0, 1));
//        } else {
//            root.addView(content, new LinearLayout.LayoutParams(MATCH_PARENT, 0, 1));
//        }
//    }
//
//    private static LinearLayout createRoot(Activity activity) {
//        LinearLayout root = new LinearLayout(activity);
//        root.setOrientation(LinearLayout.VERTICAL);
//        return root;
//    }
//
//    private static DrawerLayout createSideMenu(Activity activity, FrameLayout content) {
//        DrawerLayout drawerLayout = new DrawerLayout(activity);
//        FrameLayout drawerContent = new FrameLayout(activity);
//        drawerLayout.addView(content, new DrawerLayout.LayoutParams(MATCH_PARENT, 0, 1));
//        DrawerLayout.LayoutParams drawerContentParams = new DrawerLayout.LayoutParams(WRAP_CONTENT, MATCH_PARENT);
//        drawerContentParams.gravity = Gravity.START;
//        drawerLayout.addView(drawerContent, drawerContentParams);
//        return drawerLayout;
//    }
//
//    private static FrameLayout createContent(Activity activity) {
//        return new FrameLayout(activity);
//    }
}
