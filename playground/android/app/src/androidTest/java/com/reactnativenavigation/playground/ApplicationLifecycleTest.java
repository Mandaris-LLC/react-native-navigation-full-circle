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
@FixMethodOrder(value = MethodSorters.NAME_ASCENDING)
@SdkSuppress(minSdkVersion = 23)
@TargetApi(23)
public class ApplicationLifecycleTest {

    private ReactIdlingResource reactIdlingResource = new ReactIdlingResource();

    @Rule
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class, false, false);

    @Before
    public void beforeEach() throws Exception {
        uiDevice().wakeUp();
        reactIdlingResource.start();
        Espresso.registerIdlingResources(reactIdlingResource);
    }

    @After
    public void afterEach() {
        reactIdlingResource.stop();
        Espresso.unregisterIdlingResources(reactIdlingResource);
        uiDevice().waitForIdle();
    }

    @Test
    public void _1_showSplash_AcceptsOverlayPermissions_ShowsWelcomeScreen() throws Exception {
        rule.launchActivity(null);
        assertThat(rule.getActivity().getContentView()).isNotNull().isInstanceOf(NavigationSplashView.class);
        acceptOverlayPermissionIfNeeded();
        assertMainShown();
    }

    @Test
    public void _2_relaunchFromBackground() throws Exception {
        rule.launchActivity(null);
        acceptOverlayPermissionIfNeeded();
        assertMainShown();

        uiDevice().pressHome();
        uiDevice().pressRecentApps();
        uiDevice().findObject(new UiSelector().text("Playground")).click();
        uiDevice().waitForIdle();

        assertMainShown();
    }

    @Test
    public void _3_relaunchAfterClose() throws Exception {
        rule.launchActivity(null);
        acceptOverlayPermissionIfNeeded();
        assertMainShown();

        uiDevice().pressBack();
        uiDevice().waitForIdle();

        rule.launchActivity(null);
        uiDevice().waitForIdle();

        assertMainShown();
    }

    @Test
    public void _4_deviceOrientationDoesNotDestroyActivity() throws Exception {
        MainActivity mainActivity = rule.launchActivity(null);
        acceptOverlayPermissionIfNeeded();
        assertMainShown();

        uiDevice().setOrientationRight();
        uiDevice().waitForIdle();

        Thread.sleep(5000);
        assertMainShown();
        assertThat(rule.getActivity()).isSameAs(mainActivity);
    }

    private void assertMainShown() {
        onView(withText("React Native Navigation!")).check(matches(isDisplayed()));
        uiDevice().waitForIdle();
    }

    private UiDevice uiDevice() {
        return UiDevice.getInstance(getInstrumentation());
    }

    private void acceptOverlayPermissionIfNeeded() throws Exception {
        if (Settings.canDrawOverlays(getInstrumentation().getContext())) {
            return;
        }
        uiDevice().waitForIdle();
        uiDevice().findObject(new UiSelector().text("Playground")).click();
        uiDevice().findObject(new UiSelector().text("Permit drawing over other apps")).click();
        uiDevice().pressBack();
        uiDevice().pressBack();
    }
}
