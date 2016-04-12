package com.reactnativenavigation.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.common.logging.FLog;
import com.facebook.react.LifecycleState;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactPackage;
import com.facebook.react.ReactRootView;
import com.facebook.react.common.ReactConstants;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
import com.facebook.react.shell.MainReactPackage;
import com.reactnativenavigation.BuildConfig;
import com.reactnativenavigation.core.RctManager;
import com.reactnativenavigation.packages.RnnPackage;
import com.reactnativenavigation.utils.ContextProvider;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Nullable;

/**
 * Base Activity for React Native applications.
 */
public class BaseReactActivity extends AppCompatActivity implements DefaultHardwareBackBtnHandler {

    private static final String TAG = "BaseReactActivity";
    private static final String REDBOX_PERMISSION_MESSAGE =
            "Overlay permissions needs to be granted in order for react native apps to run in dev mode";

    protected  @Nullable ReactInstanceManager mReactInstanceManager;
    private LifecycleState mLifecycleState = LifecycleState.BEFORE_RESUME;
    private boolean mDoRefresh = false;
    private Menu mMenu;

    /**
     * Returns the name of the bundle in assets. If this is null, and no file path is specified for
     * the bundle, the app will only work with {@code getUseDeveloperSupport} enabled and will
     * always try to load the JS bundle from the packager server.
     * e.g. "index.android.bundle"
     */
    protected @Nullable String getBundleAssetName() {
        return "index.android.bundle";
    };

    /**
     * Returns a custom path of the bundle file. This is used in cases the bundle should be loaded
     * from a custom path. By default it is loaded from Android assets, from a path specified
     * by {getBundleAssetName}.
     * e.g. "file://sdcard/myapp_cache/index.android.bundle"
     */
    protected @Nullable String getJSBundleFile() {
        return null;
    }

    /**
     * Returns the name of the main module. Determines the URL used to fetch the JS bundle
     * from the packager server. It is only used when dev support is enabled.
     * This is the first file to be executed once the {@link ReactInstanceManager} is created.
     * e.g. "index.android"
     */
    protected String getJSMainModuleName() {
        return "index.android";
    }

    /**
     * Returns the launchOptions which will be passed to the {@link ReactInstanceManager}
     * when the application is started. By default, this will return null and an empty
     * object will be passed to your top level component as its initial props.
     * If your React Native application requires props set outside of JS, override
     * this method to return the Android.os.Bundle of your desired initial props.
     */
    protected @Nullable Bundle getLaunchOptions() {
        return null;
    }

    /**
     * Returns the name of the main component registered from JavaScript.
     * This is used to schedule rendering of the component.
     * e.g. "MoviesApp"
     */
    protected String getMainComponentName() {
        return "";
    }

    /**
     * Returns whether dev mode should be enabled. This enables e.g. the dev menu.
     */
    protected boolean getUseDeveloperSupport() {
        return BuildConfig.DEBUG;
    }

    /**
     * Returns a list of {@link ReactPackage} used by the app.
     * You'll most likely want to return at least the {@code MainReactPackage}.
     * If your app uses additional views or modules besides the default ones,
     * you'll want to include more packages here.
     */
    protected List<ReactPackage> getPackages() {
        return Arrays.asList(
                new MainReactPackage(),
                new RnnPackage()
        );
    }

    /**
     * A subclass may override this method if it needs to use a custom {@link ReactRootView}.
     */
    protected ReactRootView createRootView() {
        return new ReactRootView(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mReactInstanceManager = createReactInstanceManager();
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
            rctManager.init(getApplicationContext(), getMainComponentName(), getPackages());
        }
        return rctManager.getReactInstanceManager();
    }

    protected void handleOnCreate() {
        if (getUseDeveloperSupport() && Build.VERSION.SDK_INT >= 23) {
            // Get permission to show redbox in dev builds.
            if (!Settings.canDrawOverlays(this)) {
                Intent serviceIntent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                startActivity(serviceIntent);
                FLog.w(ReactConstants.TAG, REDBOX_PERMISSION_MESSAGE);
                Toast.makeText(this, REDBOX_PERMISSION_MESSAGE, Toast.LENGTH_LONG).show();
            }
        }

        ReactRootView mReactRootView = createRootView();
        mReactRootView.startReactApplication(mReactInstanceManager, getMainComponentName(), getLaunchOptions());
        setContentView(mReactRootView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ContextProvider.setActivityContext(this);

        mLifecycleState = LifecycleState.RESUMED;

        if (mReactInstanceManager != null) {
            mReactInstanceManager.onResume(this, this);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        mLifecycleState = LifecycleState.BEFORE_RESUME;

        if (mReactInstanceManager != null) {
            mReactInstanceManager.onPause();
        }

        ContextProvider.clearActivityContext();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Destroy react instance manager only if there are no resumed react activities
        Activity activity = ContextProvider.getActivityContext();
        if (mReactInstanceManager != null && !(activity instanceof BaseReactActivity)) {
            Log.i(TAG, "Destroying ReactInstanceManager");
            mReactInstanceManager.onDestroy();
        } else {
            Log.d(TAG, "Not destroying ReactInstanceManager");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        mMenu = menu;
        return super.onCreateOptionsMenu(menu);
    }

    public Menu getMenu() {
        return mMenu;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (mReactInstanceManager != null) {
            mReactInstanceManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (mReactInstanceManager != null &&
            mReactInstanceManager.getDevSupportManager().getDevSupportEnabled()) {
            if (keyCode == KeyEvent.KEYCODE_MENU) {
                mReactInstanceManager.showDevOptionsDialog();
                return true;
            }
            if (keyCode == KeyEvent.KEYCODE_R && !(getCurrentFocus() instanceof EditText)) {
                // Enable double-tap-R-to-reload
                if (mDoRefresh) {
                    mReactInstanceManager.getDevSupportManager().handleReloadJS();
                    mDoRefresh = false;
                } else {
                    mDoRefresh = true;
                    new Handler().postDelayed(
                            new Runnable() {
                                @Override
                                public void run() {
                                    mDoRefresh = false;
                                }
                            },
                            200);
                }
            }
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        if (mReactInstanceManager != null) {
            mReactInstanceManager.onBackPressed();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void invokeDefaultOnBackPressed() {
        super.onBackPressed();
    }
}
