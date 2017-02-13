package com.reactnativenavigation.playground;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;

import com.facebook.react.ReactNativeHost;
import com.reactnativenavigation.controllers.NavigationActivity;

import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.io.IOException;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(value = MethodSorters.NAME_ASCENDING)
public class ApplicationLifecycleTest {

    @Rule
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class, false, false);

    private void registerIdlingResource() {
        Espresso.registerIdlingResources(new IdlingResource() {
            @Override
            public String getName() {
                return "React Bridge";
            }

            @Override
            public boolean isIdleNow() {
                ReactNativeHost host = NavigationActivity.instance.getHost();
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

    @Test
    public void startApp_LoadsBridge_ThenShowsWelcomeScreen() throws UiObjectNotFoundException, IOException {
        rule.launchActivity(null);

        UiDevice.getInstance(getInstrumentation()).findObject(new UiSelector().text("Playground")).click();
        UiDevice.getInstance(getInstrumentation()).findObject(new UiSelector().text("Permit drawing over other apps")).click();
        UiDevice.getInstance(getInstrumentation()).pressBack();
        UiDevice.getInstance(getInstrumentation()).pressBack();

        registerIdlingResource();

        onView(withText("React Native Navigation!")).check(matches(isDisplayed()));
    }
}
