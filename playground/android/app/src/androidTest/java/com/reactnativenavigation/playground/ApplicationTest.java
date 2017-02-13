package com.reactnativenavigation.playground;

import android.app.Activity;
import android.app.Instrumentation;
import android.provider.Settings;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class ApplicationTest {

    @Rule
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class, false, false);

    @Test
    public void startAppShowsTheSplash() throws InterruptedException {
        Intents.init();
        Intents.intending(hasAction(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)).respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK, null));

        rule.launchActivity(null);

        onView(withText("Splash :)")).check(matches(isDisplayed()));
        Intents.release();
    }

    @Test
    public void startAppLoadsBridgeShowsWelcomeScreen() throws UiObjectNotFoundException {
        rule.launchActivity(null);

        UiDevice.getInstance(getInstrumentation()).findObject(new UiSelector().text("Playground")).click();
        UiDevice.getInstance(getInstrumentation()).findObject(new UiSelector().text("Permit drawing over other apps")).click();
        UiDevice.getInstance(getInstrumentation()).pressBack();
        UiDevice.getInstance(getInstrumentation()).pressBack();

        onView(withText("React Native Navigation!")).check(matches(isDisplayed()));
    }
}
