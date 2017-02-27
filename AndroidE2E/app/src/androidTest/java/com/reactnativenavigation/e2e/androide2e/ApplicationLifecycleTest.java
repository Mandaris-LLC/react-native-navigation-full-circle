package com.reactnativenavigation.e2e.androide2e;

import android.annotation.TargetApi;
import android.app.Instrumentation;
import android.content.Intent;
import android.provider.Settings;
import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 23)
@TargetApi(23)
public class ApplicationLifecycleTest {

    private static final String PACKAGE_NAME = "com.reactnativenavigation.playground";
    private static final int TIMEOUT = 10000;
    private Instrumentation.ActivityMonitor activityMonitor;

    @Before
    public void beforeEach() {
        activityMonitor = new Instrumentation.ActivityMonitor("com.reactnativenavigation.playground.MainActivity", null, false);
        getInstrumentation().addMonitor(activityMonitor);
    }

    @After
    public void afterEach() {
        getInstrumentation().removeMonitor(activityMonitor);
    }

    @Test
    public void showSplash_AcceptsOverlayPermissions_ShowsWelcomeScreen() throws Exception {
        launchTheApp();
        assertThat(uiDevice().wait(Until.hasObject(By.desc("NavigationSplashView")), TIMEOUT)).isTrue();

//        UiObject2 o = uiDevice().findObject(By.desc("NavigationSplashView"));
//        assertThat(uiDevice().wait(Until.hasObject(By.clazz("com.reactnativenavigation.views.NavigationSplashView")), TIMEOUT)).isTrue();
//        assertThat(activityMonitor.waitForActivity()).isNotNull();
//        activity = activityMonitor.getLastActivity();
//        uiDevice().wait(Until.hasObject(By.clazz("com.reactnativenavigation.views.NavigationSplashView")), TIMEOUT);
//        acceptOverlayPermissionIfNeeded();

//        assertThat(uiDevice().findObject(new UiSelector().className("com.reactnativenavigation.views.NavigationSplashView")).exists()).isTrue();
//        assertThat(rule.getActivity().getContentView()).isNotNull().isInstanceOf(NavigationSplashView.class);
//        assertMainShown();
    }

    private void acceptOverlayPermissionIfNeeded() throws Exception {
        if (Settings.canDrawOverlays(activityMonitor.getLastActivity())) {
            return;
        }
        uiDevice().waitForIdle();
        uiDevice().findObject(new UiSelector().text("Playground")).click();
        uiDevice().findObject(new UiSelector().text("Permit drawing over other apps")).click();
        uiDevice().pressBack();
        uiDevice().pressBack();
    }
//        uiDevice().findObject(new UiSelector().description("Apps")).clickAndWaitForNewWindow();

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
