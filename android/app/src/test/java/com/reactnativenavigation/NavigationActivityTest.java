package com.reactnativenavigation;

import android.view.View;

import org.junit.Ignore;
import org.junit.Test;
import org.robolectric.Robolectric;
import org.robolectric.android.controller.ActivityController;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class NavigationActivityTest extends BaseTest {
	@Test
	@Ignore
	public void holdsContentView() throws Exception {
		NavigationActivity activity = Robolectric.setupActivity(NavigationActivity.class);
		assertThat(activity.getContentView()).isNull();
		View view = new View(activity);
		activity.setContentView(view);
		assertThat(activity.getContentView()).isSameAs(view);
	}

	@Test
	public void reportsLifecycleEventsToDelegate() throws Exception {
		ActivityController<NavigationActivity> activityController = Robolectric.buildActivity(NavigationActivity.class);
		activityController.create();
	}
}
