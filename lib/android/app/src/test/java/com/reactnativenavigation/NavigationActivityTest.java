package com.reactnativenavigation;

import android.view.View;

import com.reactnativenavigation.controllers.ActivityLifecycleDelegate;

import org.junit.Test;
import org.robolectric.Robolectric;
import org.robolectric.android.controller.ActivityController;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

public class NavigationActivityTest extends BaseTest {


	@Test
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
		NavigationApplication.instance.getConfig().activityLifecycleDelegate = mock(ActivityLifecycleDelegate.class);
		ActivityLifecycleDelegate activityLifecycleDelegate = NavigationApplication.instance.getConfig().activityLifecycleDelegate;

		verifyZeroInteractions(activityLifecycleDelegate);

		activityController.create();
		verify(activityLifecycleDelegate, times(1)).onActivityCreated(activityController.get());
		verifyNoMoreInteractions(activityLifecycleDelegate);

		activityController.resume();
		verify(activityLifecycleDelegate, times(1)).onActivityResumed(activityController.get(), activityController.get());
		verifyNoMoreInteractions(activityLifecycleDelegate);

		activityController.pause();
		verify(activityLifecycleDelegate, times(1)).onActivityPaused(activityController.get());
		verifyNoMoreInteractions(activityLifecycleDelegate);

		activityController.destroy();
		verify(activityLifecycleDelegate, times(1)).onActivityDestroyed(activityController.get());
		verifyNoMoreInteractions(activityLifecycleDelegate);
	}
}