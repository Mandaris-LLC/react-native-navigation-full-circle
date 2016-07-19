package com.reactnativenavigation.views;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.drawable.DrawerArrowDrawable;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.reactnativenavigation.R;
import com.reactnativenavigation.activities.BaseReactActivity;
import com.reactnativenavigation.core.objects._Button;
import com.reactnativenavigation.core.objects._Screen;
import com.reactnativenavigation.utils.ContextProvider;
import com.reactnativenavigation.react.ImageLoader;
import com.reactnativenavigation.utils.ImageUtils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by guyc on 09/04/16.
 */
public class RnnToolBar extends Toolbar {

    private List<_Screen> mScreens;
    private AsyncTask mDrawerIconTask;
    private AsyncTask mSetupToolbarTask;
    private Drawable mBackground;
    private Drawable mDrawerIcon;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ArrayList<View> mMenuItems;

    public RnnToolBar(Context context) {
        super(context);
        init();
    }

    private void init() {
        mMenuItems = new ArrayList<>();
        mBackground = getBackground();
    }

    public void setScreens(List<_Screen> screens) {
        mScreens = screens;
    }

    public void handleOnCreateOptionsMenuAsync() {
//        if (mScreens != null) {
//            setupToolbarButtonsAsync(null, mScreens.get(0));
//        }
    }

    public ActionBarDrawerToggle setupDrawer(DrawerLayout drawerLayout, _Screen drawerScreen, _Screen screen) {
        if (drawerLayout == null || drawerScreen == null) {
            return null;
        }

        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(
            ContextProvider.getActivityContext(),
            mDrawerLayout,
            this,
            R.string.drawer_open,
            R.string.drawer_close
        );
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        setupDrawerIconAsync(drawerScreen.icon, screen);

        return mDrawerToggle;
    }

    public void setDrawerIcon(Drawable icon) {
        mDrawerIcon = icon;
    }

    public void showDrawer(boolean animated) {
        if (mDrawerLayout == null) {
            return;
        }

        mDrawerLayout.openDrawer(Gravity.LEFT);
    }

    public void hideDrawer(boolean animated) {
        if (mDrawerLayout == null) {
            return;
        }

        mDrawerLayout.closeDrawer(Gravity.LEFT);
    }

    public void toggleDrawer(boolean animated) {
        if (mDrawerLayout == null) {
            return;
        }

        boolean visible = mDrawerLayout.isDrawerOpen(Gravity.LEFT);
        if (visible) {
            hideDrawer(animated);
        } else {
            showDrawer(animated);
        }
    }

    public void setupDrawerIconAsync(String drawerIconSource, _Screen screen) {
        if (mDrawerIconTask == null) {
            mDrawerIconTask = new SetupDrawerIconTask(this, drawerIconSource, screen).execute();
        }
    }

    public void setupToolbarButtonsAsync(_Screen newScreen) {
        if (newScreen != null) {
            this.setupToolbarButtonsAsync(null, newScreen);
        }
    }


    public void setupToolbarButtonsAsync(_Screen oldScreen, _Screen newScreen) {
        if (mSetupToolbarTask == null) {
            mSetupToolbarTask = new SetupToolbarButtonsTask(this, oldScreen, newScreen).execute();
        }
    }

    public void showToolbar(boolean animated) {
        ActionBar actionBar = ((AppCompatActivity) getContext()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setShowHideAnimationEnabled(animated);
            // We hide the ToolBar's parent (AppBarLayout) since this animates the shadow added by AppBar as well
            ((View) getParent()).setVisibility(VISIBLE);
        }
    }

    public void hideToolbar(boolean animated) {
        ActionBar actionBar = ((AppCompatActivity) getContext()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setShowHideAnimationEnabled(animated);
            // We hide the ToolBar's parent (AppBarLayout) since this animates the shadow added by AppBar as well
            ((View) getParent()).setVisibility(GONE);
        }
    }

    private void showToolbar() {
        showToolbar(false);
    }

    private void hideToolbar() {
        hideToolbar(false);
    }

    public void setNavUpButton() {
        setNavUpButton(null);
    }

    @SuppressWarnings({"ConstantConditions"})
    public void setNavUpButton(_Screen screen) {
        BaseReactActivity context = ContextProvider.getActivityContext();
        if (context == null) {
            return;
        }

        ActionBar actionBar = context.getSupportActionBar();
        if (actionBar == null) {
            return;
        }

        boolean isBack = screen != null;
        boolean hasDrawer = mDrawerToggle != null;

        Drawable navIcon = null;
        DrawerArrowDrawable navArrow = null;
        if (hasDrawer && mDrawerIcon == null) {
            navArrow = (DrawerArrowDrawable) this.getNavigationIcon();
        } else {
            if (isBack && !screen.backButtonHidden) {
                navArrow = new DrawerArrowDrawable(context);
            } else if (hasDrawer) {
                navIcon = mDrawerIcon;
            }
        }

        if (navArrow != null) {
            navArrow.setProgress(isBack ? 1.0f : 0.0f);
            if (screen != null && screen.navBarButtonColor != null) {
                navArrow.setColor(screen.navBarButtonColor);
            } else {
                navArrow.setColor(Color.BLACK);
            }
            navIcon = navArrow;
        }

        actionBar.setHomeAsUpIndicator(navIcon);
        actionBar.setDisplayHomeAsUpEnabled(navIcon != null);
    }

    /**
     * Update the ToolBar from screen. This function sets any properties that are defined
     * in the screen.
     * @param screen The currently displayed screen
     */
    @UiThread
    public void update(@NonNull _Screen screen) {
        ((AppCompatActivity) getContext()).setSupportActionBar(this);
        setTitle(screen.title);
        setStyle(screen);
    }

    public void updateAndSetButtons(_Screen screen) {
        update(screen);
        setupToolbarButtonsAsync(screen);
    }

    private static class SetupDrawerIconTask extends AsyncTask<Void, Void, Drawable> {
        private final WeakReference<RnnToolBar> mToolbarWR;
        private final String mDrawerIconSource;
        private final Integer mTintColor;

        public SetupDrawerIconTask(RnnToolBar toolBar, String drawerIconSource, _Screen screen) {
            mToolbarWR = new WeakReference<>(toolBar);
            mDrawerIconSource = drawerIconSource;
            mTintColor = screen.navBarButtonColor;
        }

        @Override
        protected Drawable doInBackground(Void... params) {
            Context context = ContextProvider.getActivityContext();
            if (context == null || mDrawerIconSource == null) {
                return null;
            }

            return ImageLoader.loadImage(mDrawerIconSource);
        }

        @Override
        protected void onPostExecute(Drawable drawerIcon) {
            RnnToolBar toolBar = mToolbarWR.get();
            if (drawerIcon != null) {
                if (mTintColor != null) {
                    ImageUtils.tint(drawerIcon, mTintColor);
                }
                toolBar.setDrawerIcon(drawerIcon);
            }

            toolBar.setNavUpButton();
            mToolbarWR.clear();
        }
    }

    private static class SetupToolbarButtonsTask extends AsyncTask<Void, Void, Map<String, Drawable>> {
        private final List<_Button> mOldButtons;
        private final List<_Button> mNewButtons;
        private final WeakReference<RnnToolBar> mToolbarWR;
        @ColorInt private final Integer mTintColor;
        private final int mIconDimensions;

        public SetupToolbarButtonsTask(RnnToolBar toolBar, _Screen oldScreen, _Screen newScreen) {
            mToolbarWR = new WeakReference<>(toolBar);
            mOldButtons = oldScreen == null ? null : oldScreen.getButtons();
            mNewButtons = newScreen.getButtons();
            mTintColor = newScreen.navBarButtonColor;
            mIconDimensions = (int) ImageUtils.convertDpToPixel(48, toolBar.getContext());
        }

        @Override
        protected Map<String, Drawable> doInBackground(Void... params) {
            Context context = ContextProvider.getActivityContext();
            if (context == null) {
                return null;
            }

            Map<String, Drawable> icons = new HashMap<>();
            for (_Button button : mNewButtons) {
                if (button.hasIcon()) {
                    icons.put(button.id, button.getIcon(context, mIconDimensions));
                }
            }
            return icons;
        }

        @Override
        protected void onPostExecute(Map<String, Drawable> icons) {
            final Context context = ContextProvider.getActivityContext();
            if (context == null) {
                return;
            }

            Menu menu = ((BaseReactActivity) context).getMenu();
            if (menu == null) {
                RnnToolBar toolBar = mToolbarWR.get();
                if (toolBar != null) {
                    toolBar.mSetupToolbarTask = null;
                }
                mToolbarWR.clear();
                return;
            }

            // Remove prev screen buttons
            if(mOldButtons == null) {
                menu.clear();
            } else {
                for (_Button btn : mOldButtons) {
                    menu.removeItem(btn.getItemId());
                }
            }

            // Add new screen buttons
            final List<String> textButtons = new ArrayList<>();
            final int size = mNewButtons.size();
            for (int i = 0; i < size; i++) {
                _Button button = mNewButtons.get(i);
                MenuItem item = menu.add(Menu.NONE, button.getItemId(), size - i - 1, button.title);
                item.setShowAsAction(getMenuItemShowAction(button.showAsAction));

                // Set button icon
                if (button.hasIcon()) {
                    Drawable icon = icons.get(button.id);
                    if (mTintColor != null) {
                        ImageUtils.tint(icon, mTintColor);
                    }
                    item.setIcon(icon);
                } else {
                    textButtons.add(button.title);
                }

                // Disable button if needed
                if (button.disabled) {
                    item.setEnabled(false);
                }
            }

            final RnnToolBar toolBar = mToolbarWR.get();
            if (toolBar != null) {
                // Tint overflow icon which appears when there's not enough space in Toolbar for icons
                if (mTintColor != null) {
                    ImageUtils.tint(toolBar.getOverflowIcon(), mTintColor);
                }

                // Tint text buttons
                if (textButtons.size() > 0 && mTintColor != null) {
                    final View decorView = ((Activity) context).getWindow().getDecorView();
                    decorView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                        @Override
                        public void onGlobalLayout() {
                            decorView.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                            // Find TextViews
                            for (String text : textButtons) {
                                decorView.findViewsWithText(toolBar.mMenuItems, text, View.FIND_VIEWS_WITH_CONTENT_DESCRIPTION);
                            }

                            // Set text color
                            for (View button : toolBar.mMenuItems) {
                                ((TextView) button).setTextColor(mTintColor);
                            }

                            toolBar.mMenuItems.clear();
                        }
                    });
                }

                toolBar.mSetupToolbarTask = null;
                mToolbarWR.clear();
            }
        }

        private int getMenuItemShowAction(String action) {
            switch (action) {
                case "never":
                    return MenuItem.SHOW_AS_ACTION_NEVER;
                case "always":
                    return MenuItem.SHOW_AS_ACTION_ALWAYS;
                case "withText":
                    return MenuItem.SHOW_AS_ACTION_WITH_TEXT;
                default:
                    return MenuItem.SHOW_AS_ACTION_IF_ROOM;
            }
        }
    }
}
