package com.reactnativenavigation.viewcontrollers;

import android.app.Activity;

import com.reactnativenavigation.BaseTest;
import com.reactnativenavigation.mocks.TestContainerView;
import com.reactnativenavigation.parse.NavigationOptions;

import org.junit.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class ContainerViewControllerTest extends BaseTest {
	private ContainerViewController uut;
	private Activity activity;

	@Override
	public void beforeEach() {
		super.beforeEach();
		activity = newActivity();
	}

	@Test
	public void createsViewFromContainerViewCreator() throws Exception {
		final ContainerViewController.ContainerView view = new TestContainerView(activity);
		uut = new ContainerViewController(newActivity(), "id1", "containerName", new ContainerViewController.ContainerViewCreator() {
			@Override
			public ContainerViewController.ContainerView create(final Activity activity1, final String containerId, final String containerName) {
				return view;
			}
		}, new NavigationOptions());
		assertThat(uut.getView()).isSameAs(view);
	}

	@Test
	public void containerViewDestroyedOnDestory() throws Exception {
		final ContainerViewController.ContainerView view = spy(new TestContainerView(activity));
		uut = new ContainerViewController(newActivity(), "id1", "containerName", new ContainerViewController.ContainerViewCreator() {
			@Override
			public ContainerViewController.ContainerView create(final Activity activity1, final String containerId, final String containerName) {
				return view;
			}
		}, new NavigationOptions());
		uut.ensureViewIsCreated();

		verify(view, times(0)).destroy();
		uut.destroy();
		verify(view, times(1)).destroy();
	}
}
