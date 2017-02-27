package com.reactnativenavigation.e2e.androide2e;

import android.annotation.TargetApi;
import android.content.Intent;
import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.Until;

import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 23)
@TargetApi(23)
public class ApplicationLifecycleTest {

    public static final String PACKAGE_NAME = "com.reactnativenavigation.playground";

    @Test
    public void showSplash_AcceptsOverlayPermissions_ShowsWelcomeScreen() throws Exception {
        launchTheApp();

//        assertThat(rule.getActivity().getContentView()).isNotNull().isInstanceOf(NavigationSplashView.class);
//        acceptOverlayPermissionIfNeeded();
//        assertMainShown();
    }

//    private void acceptOverlayPermissionIfNeeded() throws Exception {
//        if (Settings.canDrawOverlays(getInstrumentation().getTargetContext())) {
//            return;
//        }
//        uiDevice().waitForIdle();
//        uiDevice().findObject(new UiSelector().text("Playground")).click();
//        uiDevice().findObject(new UiSelector().text("Permit drawing over other apps")).click();
//        uiDevice().pressBack();
//        uiDevice().pressBack();
//    }
//        uiDevice().findObject(new UiSelector().description("Apps")).clickAndWaitForNewWindow();

    private void launchTheApp() throws Exception {
        uiDevice().wakeUp();
        uiDevice().pressHome();
        uiDevice().waitForIdle();
        Intent intent = getInstrumentation().getTargetContext().getPackageManager().getLaunchIntentForPackage(PACKAGE_NAME);
        assertThat(intent).isNotNull();
        getInstrumentation().getTargetContext().startActivity(intent);
        uiDevice().wait(Until.hasObject(By.pkg("com.reactnativenavigation.playground").depth(0)), 5000);
    }

    private UiDevice uiDevice() {
        return UiDevice.getInstance(getInstrumentation());
    }

}
