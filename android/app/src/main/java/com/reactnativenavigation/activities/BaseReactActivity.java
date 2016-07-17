package com.reactnativenavigation.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.CallSuper;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.facebook.common.logging.FLog;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.common.ReactConstants;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
import com.facebook.react.shell.MainReactPackage;
import com.reactnativenavigation.BuildConfig;
import com.reactnativenavigation.bridge.NavigationReactPackage;
import com.reactnativenavigation.controllers.ModalController;
import com.reactnativenavigation.core.objects.Button;
import com.reactnativenavigation.core.objects.Drawer;
import com.reactnativenavigation.core.objects.Screen;
import com.reactnativenavigation.modal.RnnModal;
import com.reactnativenavigation.utils.ContextProvider;
import com.reactnativenavigation.utils.StyleHelper;
import com.reactnativenavigation.views.RnnToolBar;
import com.reactnativenavigation.views.ScreenStack;

import java.util.Arrays;
import java.util.List;

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
    private static final String REDBOX_PERMISSION_MESSAGE =
            "Overlay permissions needs to be granted in order for react native apps to run in dev mode";

    @Nullable
    protected ReactInstanceManager reactInstanceManager;
    private boolean shouldRefreshOnRR = false;
    private Menu menu;
    protected RnnToolBar toolbar;
    protected ActionBarDrawerToggle drawerToggle;
    protected DrawerLayout drawerLayout;
    protected ScreenStack drawerStack;

    /**
     * Returns a list of {@link ReactPackage} used by the app.
     * You'll most likely want to return at least the {@code MainReactPackage}.
     * If your app uses additional views or modules besides the default ones,
     * you'll want to include more packages here.
     */
    public List<ReactPackage> getPackages() {
        return Arrays.asList(
                new MainReactPackage(),
                new NavigationReactPackage()
        );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ContextProvider.setActivityContext(this);
        reactInstanceManager = createReactInstanceManager();
        handleOnCreate();

    }

    /**
     * A subclass may override this method if it needs to use a custom instance.
     */
    protected ReactInstanceManager createReactInstanceManager() {
        return getReactInstanceManager();
    }

    protected ReactInstanceManager getReactInstanceManager() {
        RctManager rctManager = RctManager.getInstance();
        if (!rctManager.isInitialized()) {
            rctManager.init(this);
        }
        return rctManager.getReactInstanceManager();
    }

    @CallSuper
    protected void handleOnCreate() {
        permissionToShowRedboxIfNeeded();
    }

    private void permissionToShowRedboxIfNeeded() {
        if (BuildConfig.DEBUG && Build.VERSION.SDK_INT >= 23 && !Settings.canDrawOverlays(this)) {
            Intent serviceIntent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
            startActivity(serviceIntent);
            FLog.w(ReactConstants.TAG, REDBOX_PERMISSION_MESSAGE);
            Toast.makeText(this, REDBOX_PERMISSION_MESSAGE, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        ContextProvider.setActivityContext(this);

        if (reactInstanceManager != null) {
            reactInstanceManager.onHostResume(this, this);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (reactInstanceManager != null) {
            reactInstanceManager.onHostPause();
        }

        ContextProvider.clearActivityContext();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Destroy react instance manager only if there are no resumed react activities
        BaseReactActivity activity = ContextProvider.getActivityContext();
        if (reactInstanceManager != null && (activity == null || activity.isFinishing())) {
            Log.i(TAG, "Destroying ReactInstanceManager");
            reactInstanceManager.onHostDestroy();
            RctManager.getInstance().onDestroy();
        } else {
            Log.d(TAG, "Not destroying ReactInstanceManager");
        }
    }

    @CallSuper
    public void push(Screen screen) {
        StyleHelper.updateStyles(toolbar, screen);
        if (toolbar != null) {
            toolbar.update(screen);

            if (getCurrentNavigatorId().equals(screen.navigatorId) &&
                    getScreenStackSize() >= 1) {
                toolbar.setNavUpButton(screen);
            }
        }
    }

    @CallSuper
    public Screen pop(String navigatorId) {
        if (toolbar != null &&
                getCurrentNavigatorId().equals(navigatorId) &&
                getScreenStackSize() <= 2) {
            toolbar.setNavUpButton();
        }

        return null;
    }

    @CallSuper
    public Screen popToRoot(String navigatorId) {
        if (toolbar != null) {
            toolbar.setNavUpButton();
        }

        return null;
    }

    @CallSuper
    public Screen resetTo(Screen screen) {
        StyleHelper.updateStyles(toolbar, screen);
        if (toolbar != null) {
            toolbar.setNavUpButton();
        }

        return null;
    }

    protected abstract String getCurrentNavigatorId();

    @CallSuper
    public Screen getCurrentScreen() {
        ModalController modalController = ModalController.getInstance();
        if (modalController.isModalDisplayed()) {
            RnnModal modal = modalController.get();
            assert modal != null;
            return modal.getCurrentScreen();
        }

        return null;
    }

    public abstract int getScreenStackSize();

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (drawerToggle != null) {
            drawerToggle.onConfigurationChanged(newConfig);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        Screen currentScreen = getCurrentScreen();
        if (toolbar != null && currentScreen != null && !isFinishing()) {
            toolbar.setupToolbarButtonsAsync(currentScreen);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle != null &&
                getScreenStackSize() == 1 &&
                drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        } else {
            String eventId = Button.getButtonEventId(item);
            assert eventId != null;

            WritableMap params = Arguments.createMap();
            RctManager.getInstance().sendEvent(eventId, getCurrentScreen(), params);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (drawerToggle != null) {
            drawerToggle.syncState();
        }
    }

    public Menu getMenu() {
        return menu;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (reactInstanceManager != null) {
            reactInstanceManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (reactInstanceManager != null &&
                reactInstanceManager.getDevSupportManager().getDevSupportEnabled()) {
            if (keyCode == KeyEvent.KEYCODE_MENU) {
                reactInstanceManager.showDevOptionsDialog();
                return true;
            }
            if (keyCode == KeyEvent.KEYCODE_R && !(getCurrentFocus() instanceof EditText)) {
                // Enable double-tap-R-to-reload
                if (shouldRefreshOnRR) {
                    reactInstanceManager.getDevSupportManager().handleReloadJS();
                    shouldRefreshOnRR = false;
                } else {
                    shouldRefreshOnRR = true;
                    new Handler().postDelayed(
                            new Runnable() {
                                @Override
                                public void run() {
                                    shouldRefreshOnRR = false;
                                }
                            },
                            200);
                }
            }
        }
        return super.onKeyUp(keyCode, event);
    }

    /**
     * Called after bundle was reloaded. This is a good chance to clean up previously connected react views.
     */
    public void onJSBundleReloaded() {
        removeAllReactViews();
    }

    protected abstract void removeAllReactViews();

    @Override
    public void onBackPressed() {
        ModalController modalController = ModalController.getInstance();
        if (modalController.isModalDisplayed()) {
            modalController.dismissModal();
            return;
        }

        if (getScreenStackSize() > 1) {
            pop(getCurrentNavigatorId());
        } else if (reactInstanceManager != null) {
            reactInstanceManager.onBackPressed();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void invokeDefaultOnBackPressed() {
        super.onBackPressed();
    }

    protected void setupDrawer(Screen screen, Drawer drawer, int drawerFrameId, int drawerLayoutId) {
        if (drawer == null || drawer.left == null) {
            return;
        }

        drawerStack = new ScreenStack(this);
        FrameLayout drawerFrame = (FrameLayout) findViewById(drawerFrameId);
        drawerFrame.addView(drawerStack);
        drawerStack.push(drawer.left);

        drawerLayout = (DrawerLayout) findViewById(drawerLayoutId);
        drawerToggle = toolbar.setupDrawer(drawerLayout, drawer.left, screen);
    }

    public void setNavigationButtons(ReadableMap buttons) {
        if (toolbar == null) {
            return;
        }
        getCurrentScreen().setButtons(buttons);
        toolbar.setupToolbarButtonsAsync(getCurrentScreen());
    }

    public void setNavigationTitle(ReadableMap title) {
        if (toolbar == null) {
            return;
        }

        toolbar.setTitle(title.getString(KEY_TITLE));
    }

    public void toggleNavigationBar(ReadableMap params) {
        if (toolbar == null) {
            return;
        }

        boolean hide = params.getBoolean(KEY_HIDDEN);
        boolean animated = params.getBoolean(KEY_ANIMATED);
        if (hide) {
            toolbar.hideToolbar(animated);
        } else {
            toolbar.showToolbar(animated);
        }
    }

    public void toggleDrawer(ReadableMap params) {
        if (toolbar == null || drawerToggle == null) {
            return;
        }

        boolean animated = params.getBoolean(KEY_ANIMATED);
        String side = params.getString(KEY_SIDE);
        String to = params.getString(KEY_TO);
        switch (to) {
            case "open":
                toolbar.showDrawer(animated);
                break;
            case "closed":
                toolbar.hideDrawer(animated);
                break;
            default:
                toolbar.toggleDrawer(animated);
                break;
        }
    }

    public void showFAB(ReadableMap params) {
    }
}
