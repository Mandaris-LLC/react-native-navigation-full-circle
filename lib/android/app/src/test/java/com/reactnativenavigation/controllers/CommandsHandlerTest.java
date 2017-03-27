package com.reactnativenavigation.controllers;

import com.reactnativenavigation.BaseTest;
import com.reactnativenavigation.NavigationActivity;
import com.reactnativenavigation.layout.Container;

import org.json.JSONObject;
import org.junit.Test;
import org.robolectric.Robolectric;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class CommandsHandlerTest extends BaseTest {

	@Test
	public void setRootCreatesTheLayout_SetsOnActivity() throws Exception {
		CommandsHandler uut = new CommandsHandler();
		JSONObject json = new JSONObject("{" +
				"id: containerId123," +
				"type: Container" +
				"}");
		NavigationActivity activity = Robolectric.setupActivity(NavigationActivity.class);
		uut.setRoot(activity, json);

		assertThat(activity.getContentView()).isInstanceOf(Container.class);
		assertThat(((Container) activity.getContentView()).getContainerId()).isEqualTo("containerId123");
	}
}
