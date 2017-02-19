package com.reactnativenavigation.playground;

import android.annotation.TargetApi;
import android.provider.Settings;
import android.support.test.espresso.Espresso;
import android.support.test.filters.SdkSuppress;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiSelector;

import com.reactnativenavigation.views.NavigationSplashView;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 23)
@TargetApi(23)
@FixMethodOrder(value = MethodSorters.NAME_ASCENDING)
public class ApplicationLifecycleTest {

    private ReactIdlingResource reactIdlingResource = new ReactIdlingResource();

    @Rule
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class, false, false);

    @Before
    public void beforeEach() {
        Espresso.registerIdlingResources(reactIdlingResource);
    }

    @After
    public void afterEach() {
        Espresso.unregisterIdlingResources(reactIdlingResource);
    }

    private UiDevice uiDevice() {
        return UiDevice.getInstance(getInstrumentation());
    }

    private void acceptOverlayPermissionIfNeeded() throws Exception {
        if (Settings.canDrawOverlays(getInstrumentation().getContext())) {
            return;
        }
        uiDevice().findObject(new UiSelector().text("Playground")).click();
        uiDevice().findObject(new UiSelector().text("Permit drawing over other apps")).click();
        uiDevice().pressBack();
        uiDevice().pressBack();
    }

    @Test
    public void _1_ShowSplash_AcceptsOverlayPermissions_ShowsWelcomeScreen() throws Exception {
        rule.launchActivity(null);
        assertThat(rule.getActivity().getContentView()).isNotNull().isInstanceOf(NavigationSplashView.class);
        acceptOverlayPermissionIfNeeded();
        onView(withText("React Native Navigation!")).check(matches(isDisplayed()));
    }

    @Test
    public void _2_relaunchFromBackground() throws Exception {
        rule.launchActivity(null);
        onView(withText("React Native Navigation!")).check(matches(isDisplayed()));

        uiDevice().pressHome();
        uiDevice().pressRecentApps();
        uiDevice().findObject(new UiSelector().text("Playground")).click();

        onView(withText("React Native Navigation!")).check(matches(isDisplayed()));
    }

    @Test
    public void _3_relaunchAfterClose() throws Exception {
        rule.launchActivity(null);
        uiDevice().pressBack();
        rule.launchActivity(null);
        onView(withText("React Native Navigation!")).check(matches(isDisplayed()));
    }
}
//    xdescribe('android application lifecycle', () => {
////launch, pause, and resume
//
//        it('launch already running in background', () => {
//        //
//        });
//
//        it('launch after activity killed by system', () => {
//        //
//        });
//
//        it('launch and ask react overlay permissions', () => {
//        //
//        });
//
//        it('launch after reactContext killed by system', () => {
//        //
//        });
//
//        it('launch from push notification', () => {
//        //
//        });
//
//        it('launch from intent filter', () => {
//        //
//        });
//        });

