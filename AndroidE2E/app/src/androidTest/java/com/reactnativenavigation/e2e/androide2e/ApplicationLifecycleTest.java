package com.reactnativenavigation.e2e.androide2e;

import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.io.IOException;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(value = MethodSorters.NAME_ASCENDING)
public class ApplicationLifecycleTest {

    private static final String PACKAGE_NAME = "com.reactnativenavigation.playground";
    private static final int TIMEOUT = 2000;

    @Before
    public void beforeEach() {
        //
    }

    @After
    public void afterEach() throws IOException {
        uiDevice().executeShellCommand("am force-stop " + PACKAGE_NAME);
    }

    @Test
    public void _1_showSplash_AcceptsOverlayPermissions_ShowsWelcomeScreen() throws Exception {
        launchTheApp();
//        assertThat(uiDevice().wait(Until.hasObject(By.desc("NavigationSplashView")), TIMEOUT)).isTrue();
        acceptOverlayPermissionIfNeeded();
        assertMainShown();
    }

    @Test
    public void _2_relaunchFromBackground() throws Exception {
        launchTheApp();
        assertMainShown();

        uiDevice().pressHome();
        uiDevice().pressRecentApps();
        uiDevice().findObject(new UiSelector().text("Playground")).click();
        assertMainShown();
    }

    @Test
    public void _3_relaunchAfterClose() throws Exception {
        launchTheApp();
        assertMainShown();

        uiDevice().pressBack();

        launchTheApp();
        assertMainShown();
    }

    private void assertMainShown() {
        assertThat(uiDevice().findObject(new UiSelector().text("React Native Navigation!")).exists()).isTrue();
    }

    private void acceptOverlayPermissionIfNeeded() throws Exception {
        if (uiDevice().findObject(new UiSelector().text("Draw over other apps")).exists()) {
            uiDevice().findObject(new UiSelector().text("Playground")).click();
            uiDevice().findObject(new UiSelector().text("Permit drawing over other apps")).click();
            uiDevice().pressBack();
            uiDevice().pressBack();
        }
    }

    private void launchTheApp() throws Exception {
        uiDevice().wakeUp();
        uiDevice().pressHome();
        uiDevice().findObject(new UiSelector().description("Apps")).clickAndWaitForNewWindow();
        new UiScrollable(new UiSelector().scrollable(true)).scrollTextIntoView("Playground");
        uiDevice().findObject(new UiSelector().text("Playground")).clickAndWaitForNewWindow();
    }

    private UiDevice uiDevice() {
        return UiDevice.getInstance(getInstrumentation());
    }

}
