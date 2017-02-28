package com.reactnativenavigation.e2e.androide2e;

import android.content.Intent;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.Until;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(AndroidJUnit4.class)
public class ApplicationLifecycleTest {

    private static final String PACKAGE_NAME = "com.reactnativenavigation.playground";
    private static final int TIMEOUT = 10000;

    @Before
    public void beforeEach() {
        //
    }

    @After
    public void afterEach() {
        //
    }

    @Test
    public void showSplash_AcceptsOverlayPermissions_ShowsWelcomeScreen() throws Exception {
        launchTheApp();
//        assertThat(uiDevice().wait(Until.hasObject(By.desc("NavigationSplashView")), TIMEOUT)).isTrue();
        acceptOverlayPermissionIfNeeded();
        assertMainShown();
    }

    private void assertMainShown() {
        assertThat(uiDevice().findObject(By.text("React Native Navigation!"))).isNotNull();
    }

    private void acceptOverlayPermissionIfNeeded() throws Exception {
        if (uiDevice().findObject(By.text("Draw over other apps")) != null) {
            uiDevice().findObject(By.text("Playground")).click();
            uiDevice().findObject(By.text("Permit drawing over other apps")).click();
            uiDevice().pressBack();
            uiDevice().pressBack();
        }
    }

    private void launchTheApp() throws Exception {
        uiDevice().wakeUp();
        uiDevice().pressHome();
        uiDevice().waitForIdle();
        Intent intent = getInstrumentation().getContext().getPackageManager().getLaunchIntentForPackage(PACKAGE_NAME);
        assertThat(intent).isNotNull();
        getInstrumentation().getContext().startActivity(intent);
        uiDevice().wait(Until.hasObject(By.pkg(PACKAGE_NAME).depth(0)), TIMEOUT);
    }

    private UiDevice uiDevice() {
        return UiDevice.getInstance(getInstrumentation());
    }

}
