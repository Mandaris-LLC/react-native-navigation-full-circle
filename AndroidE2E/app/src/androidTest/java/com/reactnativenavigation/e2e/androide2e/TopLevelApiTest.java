package com.reactnativenavigation.e2e.androide2e;

import android.support.test.uiautomator.By;

import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class TopLevelApiTest extends BaseTest {

	@Test
	public void switchToTabBasedApp_PassPropsFunction() throws Exception {
		launchTheApp();
		assertMainShown();
		elementByText("SWITCH TO TAB BASED APP").click();
		assertExists(By.text("This is tab 1"));
		assertExists(By.text("Hello from a function!"));
	}

	@Test
	public void switchToTabsWithSideMenu() throws Exception {
		launchTheApp();
		assertMainShown();
		elementByText("SWITCH TO APP WITH SIDE MENUS").click();
		assertExists(By.textStartsWith("This is a side menu center screen tab 1"));
		swipeRightOpenSideMenu();
		assertExists(By.text("This is a left side menu screen"));
	}

	@Test
	public void screenLifecycle() throws Exception {
		launchTheApp();
		assertMainShown();
		elementByText("PUSH LIFECYCLE SCREEN").click();
		assertExists(By.text("onStart"));
		elementByText("PUSH TO TEST ONSTOP").click();
		assertExists(By.text("onStop"));
	}

	@Test
	public void unmountIsCalledOnPop() throws Exception {
		launchTheApp();
		assertMainShown();
		elementByText("PUSH LIFECYCLE SCREEN").click();
		elementByText("onStart");
		device().pressBack();
		assertExists(By.text("componentWillUnmount"));
		elementByText("OK").click();
		assertExists(By.text("onStop"));
		elementByText("OK").click();
		assertMainShown();
	}
}
