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
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(AndroidJUnit4.class)
public abstract class BaseTest {
	public static final String PACKAGE_NAME = "com.reactnativenavigation.playground";
	public static final long TIMEOUT = 3000;

	@Before
	public void beforeEach() throws Exception {
		device().wakeUp();
		device().setOrientationNatural();
	}

	@After
	public void afterEach() throws Exception {
		device().executeShellCommand("am force-stop " + PACKAGE_NAME);
	}

	public UiDevice device() {
		return UiDevice.getInstance(getInstrumentation());
	}

	public void launchTheApp() throws Exception {
		device().executeShellCommand("am start -n " + PACKAGE_NAME + "/.MainActivity");
		device().waitForIdle();
		acceptOverlayPermissionIfNeeded();
		device().wait(Until.gone(By.textContains("Please wait")), 1000 * 60 * 3);
	}

	public void assertMainShown() {
		assertExists(By.text("React Native Navigation!"));
	}

	public void acceptOverlayPermissionIfNeeded() throws Exception {
		if (elementByText("Draw over other apps").exists()) {
			if (!elementByText("Playground").exists()) {
				scrollToText("Playground");
			}
			elementByText("Playground").click();
			elementByText("Permit drawing over other apps").click();
			device().pressBack();
			device().pressBack();
		}
	}

	public UiObject elementByText(String text) {
		return device().findObject(new UiSelector().text(text));
	}

	public void scrollToText(String txt) throws Exception {
		new UiScrollable(new UiSelector().scrollable(true)).scrollTextIntoView(txt);
	}

	public void assertExists(BySelector selector) {
		assertThat(device().wait(Until.hasObject(selector), TIMEOUT)).isTrue();
	}

	public void swipeRightOpenSideMenu() {
		device().swipe(5, 152, 500, 152, 15);
	}
}
