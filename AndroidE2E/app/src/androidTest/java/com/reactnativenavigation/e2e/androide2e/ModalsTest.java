package com.reactnativenavigation.e2e.androide2e;

import android.support.test.uiautomator.By;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.fail;

public class ModalsTest extends BaseTest {
	@Ignore
	@Test
	public void showModal() throws Exception {
		launchTheApp();
		assertMainShown();
		elementByText("SHOW MODAL").click();
		assertExists(By.text("Modal Screen"));
	}

	@Ignore
	@Test
	public void dismissModal() throws Exception {
		fail("to implement");
	}

	@Ignore
	@Test
	public void showMultipleModals() throws Exception {
		fail("to implement");
	}

	@Ignore
	@Test
	public void dismissUnknownContainerId() throws Exception {
		fail("to implement");
	}

	@Ignore
	@Test
	public void dismissModalByContainerIdWhenNotOnTop() throws Exception {
		fail("to implement");
	}

	@Ignore
	@Test
	public void dismissAllPreviousModalsByIdWhenTheyAreBelowTopPresented() throws Exception {
		fail("to implement");
	}

	@Ignore
	@Test
	public void dismissSomeModalByIdDeepInTheStack() throws Exception {
		fail("to implement");
	}

	@Ignore
	@Test
	public void dismissAllModals() throws Exception {
		fail("to implement");
	}
}
