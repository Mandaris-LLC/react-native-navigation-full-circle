package com.reactnativenavigation.e2e.androide2e;

import android.support.test.uiautomator.By;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ApplicationLifecycleTest extends BaseTest {

	@Test
	public void _1_acceptsOverlayPermissions_ShowsWelcomeScreen() throws Exception {
		launchTheApp();
		assertMainShown();
	}

	@Test
	public void _2_relaunchFromBackground() throws Exception {
		launchTheApp();
		assertMainShown();
		elementByText("PUSH").click();
		assertExists(By.text("Pushed Screen"));

		device().pressHome();
		device().pressRecentApps();
		elementByText("Playground").click();

		assertExists(By.text("Pushed Screen"));
	}

	@Test
	public void _3_relaunchAfterClose() throws Exception {
		launchTheApp();
		elementByText("PUSH").click();
		assertExists(By.text("Pushed Screen"));

		device().pressBack();

		launchTheApp();
		assertMainShown();
	}

	@Test
	public void _4_deviceOrientationDoesNotDestroyActivity() throws Exception {
		launchTheApp();
		elementByText("PUSH").click();
		assertExists(By.text("Pushed Screen"));

		device().setOrientationLeft();
		Thread.sleep(100);

		assertExists(By.text("Pushed Screen"));
	}

	@Test
	public void _5_relaunchAfterActivityKilledBySystem() throws Exception {
		launchTheApp();
		elementByText("PUSH").click();
		assertExists(By.text("Pushed Screen"));

		device().pressHome();
		device().waitForIdle();
		device().executeShellCommand("am kill " + PACKAGE_NAME);

		device().pressRecentApps();
		elementByText("Playground").click();
		assertMainShown();
	}

	private void assertMainShown() {
		assertExists(By.text("React Native Navigation!"));
	}
}
