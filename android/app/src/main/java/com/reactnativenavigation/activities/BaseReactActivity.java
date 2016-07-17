package com.reactnativenavigation.activities;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

import com.facebook.react.ReactInstanceManager;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
import com.reactnativenavigation.views.RnnToolBar;
import com.reactnativenavigation.views.ScreenStack;

import javax.annotation.Nullable;

public abstract class BaseReactActivity extends AppCompatActivity implements DefaultHardwareBackBtnHandler {

    protected static final String KEY_ANIMATED = "animated";
    protected static final String KEY_BADGE = "badge";
    protected static final String KEY_HIDDEN = "hidden";
    protected static final String KEY_SIDE = "side";
    protected static final String KEY_TAB_INDEX = "tabIndex";
    protected static final String KEY_TITLE = "title";
    protected static final String KEY_TO = "to";
    protected static final String KEY_NAVIGATOR_ID = "navigatorID";

    private static final String TAG = "BaseReactActivity";

    @Nullable
    protected ReactInstanceManager reactInstanceManager;

    private Menu menu;
    protected RnnToolBar toolbar;
    protected ActionBarDrawerToggle drawerToggle;
    protected DrawerLayout drawerLayout;
    protected ScreenStack drawerStack;


//    @CallSuper
//    public void push(Screen screen) {
//        StyleHelper.updateStyles(toolbar, screen);
//        if (toolbar != null) {
//            toolbar.update(screen);
//
//            if (getCurrentNavigatorId().equals(screen.navigatorId) &&
//                    getScreenStackSize() >= 1) {
//                toolbar.setNavUpButton(screen);
//            }
//        }
//    }

//    @CallSuper
//    public Screen pop(String navigatorId) {
//        if (toolbar != null &&
//                getCurrentNavigatorId().equals(navigatorId) &&
//                getScreenStackSize() <= 2) {
//            toolbar.setNavUpButton();
//        }
//
//        return null;
//    }
//
//    @CallSuper
//    public Screen popToRoot(String navigatorId) {
//        if (toolbar != null) {
//            toolbar.setNavUpButton();
//        }
//
//        return null;
//    }
//
//    @CallSuper
//    public Screen resetTo(Screen screen) {
//        StyleHelper.updateStyles(toolbar, screen);
//        if (toolbar != null) {
//            toolbar.setNavUpButton();
//        }
//
//        return null;
//    }

//    @CallSuper
//    public Screen getCurrentScreen() {
//        ModalController modalController = ModalController.getInstance();
//        if (modalController.isModalDisplayed()) {
//            RnnModal modal = modalController.get();
//            assert modal != null;
//            return modal.getCurrentScreen();
//        }
//
//        return null;
//    }

//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//        if (drawerToggle != null) {
//            drawerToggle.onConfigurationChanged(newConfig);
//        }
//    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        this.menu = menu;
//        Screen currentScreen = getCurrentScreen();
//        if (toolbar != null && currentScreen != null && !isFinishing()) {
//            toolbar.setupToolbarButtonsAsync(currentScreen);
//        }
//        return super.onCreateOptionsMenu(menu);
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (drawerToggle != null &&
//                getScreenStackSize() == 1 &&
//                drawerToggle.onOptionsItemSelected(item)) {
//            return true;
//        }
//
//        if (item.getItemId() == android.R.id.home) {
//            onBackPressed();
//        } else {
//            String eventId = Button.getButtonEventId(item);
//            assert eventId != null;
//
//            WritableMap params = Arguments.createMap();
//            RctManager.getInstance().sendEvent(eventId, getCurrentScreen(), params);
//        }
//        return super.onOptionsItemSelected(item);
//    }

//    @Override
//    public void onPostCreate(Bundle savedInstanceState) {
//        super.onPostCreate(savedInstanceState);
//        if (drawerToggle != null) {
//            drawerToggle.syncState();
//        }
//    }

//    public Menu getMenu() {
//        return menu;
//    }


//    protected void setupDrawer(Screen screen, Drawer drawer, int drawerFrameId, int drawerLayoutId) {
//        if (drawer == null || drawer.left == null) {
//            return;
//        }
//
//        drawerStack = new ScreenStack(this);
//        FrameLayout drawerFrame = (FrameLayout) findViewById(drawerFrameId);
//        drawerFrame.addView(drawerStack);
//        drawerStack.push(drawer.left);
//
//        drawerLayout = (DrawerLayout) findViewById(drawerLayoutId);
//        drawerToggle = toolbar.setupDrawer(drawerLayout, drawer.left, screen);
//    }

//    public void setNavigationButtons(ReadableMap buttons) {
//        if (toolbar == null) {
//            return;
//        }
//        getCurrentScreen().setButtons(buttons);
//        toolbar.setupToolbarButtonsAsync(getCurrentScreen());
//    }
//
//    public void setNavigationTitle(ReadableMap title) {
//        if (toolbar == null) {
//            return;
//        }
//
//        toolbar.setTitle(title.getString(KEY_TITLE));
//    }

//    public void toggleNavigationBar(ReadableMap params) {
//        if (toolbar == null) {
//            return;
//        }
//
//        boolean hide = params.getBoolean(KEY_HIDDEN);
//        boolean animated = params.getBoolean(KEY_ANIMATED);
//        if (hide) {
//            toolbar.hideToolbar(animated);
//        } else {
//            toolbar.showToolbar(animated);
//        }
//    }

//    public void toggleDrawer(ReadableMap params) {
//        if (toolbar == null || drawerToggle == null) {
//            return;
//        }
//
//        boolean animated = params.getBoolean(KEY_ANIMATED);
//        String side = params.getString(KEY_SIDE);
//        String to = params.getString(KEY_TO);
//        switch (to) {
//            case "open":
//                toolbar.showDrawer(animated);
//                break;
//            case "closed":
//                toolbar.hideDrawer(animated);
//                break;
//            default:
//                toolbar.toggleDrawer(animated);
//                break;
//        }
//    }
//
//    public void showFAB(ReadableMap params) {
//    }
}
