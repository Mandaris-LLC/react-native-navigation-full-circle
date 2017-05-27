package com.reactnativenavigation.e2e.androide2e;

import android.support.test.uiautomator.By;

import org.junit.Ignore;
import org.junit.Test;

public class ScreenStyleStaticTest extends BaseTest {

	@Test
	@Ignore
	public void declareNavigationStyleOnContainerComponent() throws Exception {
		launchTheApp();
		assertMainShown();
		elementByText("PUSH").click();
		assertExists(By.text("Static Title"));
	}
}
