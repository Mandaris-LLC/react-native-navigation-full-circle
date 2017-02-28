package com.reactnativenavigation.e2e.androide2e;

import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.BySelector;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ApplicationLifecycleTest {

    private static final String PACKAGE_NAME = "com.reactnativenavigation.playground";
    private static final long TIMEOUT = 3000;

    @Before
    public void beforeEach() throws Exception {
        uiDevice().wakeUp();
        uiDevice().setOrientationNatural();
    }

    @After
    public void afterEach() throws Exception {
        uiDevice().executeShellCommand("am force-stop " + PACKAGE_NAME);
    }

    @Test
    public void _1_showSplash_AcceptsOverlayPermissions_ShowsWelcomeScreen() throws Exception {
        launchTheApp();
//        assertThat(uiDevice().wait(Until.hasObject(By.desc("NavigationSplashView")), TIMEOUT)).isTrue();
        assertMainShown();
    }

    @Test
    public void _2_relaunchFromBackground() throws Exception {
        launchTheApp();
        assertMainShown();

        uiDevice().pressHome();
        uiDevice().pressRecentApps();
        elementByText("Playground").click();

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

    @Test
    public void _4_deviceOrientationDoesNotDestroyActivity() throws Exception {
        launchTheApp();
        assertMainShown();
        elementByText("PUSH").click();
        assertExists(By.text("Pushed screen"));

        uiDevice().setOrientationLeft();
        Thread.sleep(100);

        assertExists(By.text("Pushed screen"));
    }

    @Test
    public void _5_relaunchAfterActivityKilledBySystem() throws Exception {
        launchTheApp();
        assertMainShown();

        uiDevice().pressHome();
        uiDevice().executeShellCommand("am kill " + PACKAGE_NAME);

        uiDevice().pressRecentApps();
        elementByText("Playground").click();
        assertMainShown();
    }

    private void assertMainShown() {
        assertExists(By.text("React Native Navigation!"));
    }

    private void acceptOverlayPermissionIfNeeded() throws Exception {
        if (elementByText("Draw over other apps").exists()) {
            if (!elementByText("Playground").exists()) {
                scrollToText("Playground");
            }
            elementByText("Playground").click();
            elementByText("Permit drawing over other apps").click();
            uiDevice().pressBack();
            uiDevice().pressBack();
        }
    }

    private void launchTheApp() throws Exception {
        uiDevice().executeShellCommand("am start -n " + PACKAGE_NAME + "/.MainActivity");
        uiDevice().waitForIdle();
        acceptOverlayPermissionIfNeeded();
    }

    private UiDevice uiDevice() {
        return UiDevice.getInstance(getInstrumentation());
    }

    private UiObject elementByText(String text) {
        return uiDevice().findObject(new UiSelector().text(text));
    }

    private void scrollToText(String txt) throws Exception {
        new UiScrollable(new UiSelector().scrollable(true)).scrollTextIntoView(txt);
    }

    private void assertExists(BySelector selector) {
        assertThat(uiDevice().wait(Until.hasObject(selector), TIMEOUT)).isTrue();
    }
}
