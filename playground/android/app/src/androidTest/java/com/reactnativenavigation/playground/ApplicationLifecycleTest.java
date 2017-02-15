package com.reactnativenavigation.playground;

import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.filters.SdkSuppress;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiSelector;

import com.facebook.react.ReactNativeHost;
import com.reactnativenavigation.controllers.NavigationActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assume.assumeFalse;
import static org.junit.Assume.assumeTrue;

@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 23)
@RequiresApi(api = 23)
public class ApplicationLifecycleTest {

    @Rule
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule<MainActivity>(MainActivity.class, false, false) {
        @Override
        protected void afterActivityLaunched() {
            super.afterActivityLaunched();
            registerIdlingResource(rule.getActivity());
        }
    };

    private void registerIdlingResource(final NavigationActivity activity) {
        Espresso.registerIdlingResources(new IdlingResource() {
            @Override
            public String getName() {
                return "React Bridge";
            }

            @Override
            public boolean isIdleNow() {
                ReactNativeHost host = activity.getHost();
                return host != null
                        && host.hasInstance()
                        && host.getReactInstanceManager().hasStartedCreatingInitialContext()
                        && host.getReactInstanceManager().getCurrentReactContext() != null;
            }

            @Override
            public void registerIdleTransitionCallback(final ResourceCallback callback) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (!isIdleNow()) {
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        callback.onTransitionToIdle();
                    }
                }).start();
            }
        });
    }

    private void launchActivity() {
        rule.launchActivity(null);
    }

    private void acceptPermission() throws Exception {
        UiDevice.getInstance(getInstrumentation()).findObject(new UiSelector().text("Playground")).click();
        UiDevice.getInstance(getInstrumentation()).findObject(new UiSelector().text("Permit drawing over other apps")).click();
        UiDevice.getInstance(getInstrumentation()).pressBack();
        UiDevice.getInstance(getInstrumentation()).pressBack();
    }


    @Test
    public void startApp_HandleOverlayPermissions_LoadsBridge_ThenShowsWelcomeScreen() throws Exception {
        assumeFalse(Settings.canDrawOverlays(getInstrumentation().getContext()));
        launchActivity();
        acceptPermission();
        onView(withText("React Native Navigation!")).check(matches(isDisplayed()));
    }

    @Test
    public void startApp_NoPermissionRequired_ShowsWelcomeScreen() {
        assumeTrue(Settings.canDrawOverlays(getInstrumentation().getContext()));
        launchActivity();
        onView(withText("React Native Navigation!")).check(matches(isDisplayed()));
    }
}
