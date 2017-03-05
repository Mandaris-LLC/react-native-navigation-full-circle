package com.reactnativenavigation.layout;

import android.app.Activity;
import android.view.View;

import com.reactnativenavigation.BaseTest;
import com.reactnativenavigation.layout.bottomtabs.BottomTabs;
import com.reactnativenavigation.layout.bottomtabs.BottomTabsLayout;
import com.reactnativenavigation.layout.bottomtabs.TooManyTabsException;

import org.junit.Before;
import org.junit.Test;
import org.robolectric.Robolectric;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class BottomTabsContainerTest extends BaseTest {

	private static final String TAB_NAME = "myTab";
	private static final String OTHER_TAB_NAME = "myOtherTab";

	private BottomTabs bottomTabs;
	private Activity activity;

	@Before
	public void setUp() throws Exception {
		bottomTabs = mock(BottomTabs.class);
		activity = Robolectric.buildActivity(Activity.class).get();
	}

	@Test
	public void addsTabToBottomTabs() throws Exception {
		ContainerStackLayout tabContent = new ContainerStackLayout(activity);

		BottomTabsLayout bottomTabsContainer = createBottomTabsContainer();
		bottomTabsContainer.addTabContent(TAB_NAME, tabContent);

		verify(bottomTabs).add(TAB_NAME);
	}

	@Test
	public void addsTabContentToLayout() throws Exception {
		ContainerStackLayout stackLayout = new ContainerStackLayout(activity);

		BottomTabsLayout bottomTabsContainer = createBottomTabsContainer();
		bottomTabsContainer.addTabContent(TAB_NAME, stackLayout);

		verify(bottomTabs).attach(bottomTabsContainer);
		TestUtils.assertViewChildren(bottomTabsContainer, stackLayout);
	}

	@Test
	public void addsTwoTabsContentToLayout() throws Exception {
		View tabContent = new ContainerStackLayout(activity);
		View otherTabContent = new ContainerStackLayout(activity);

		BottomTabsLayout bottomTabsContainer = createBottomTabsContainer();
		bottomTabsContainer.addTabContent(TAB_NAME, tabContent);
		bottomTabsContainer.addTabContent(OTHER_TAB_NAME, otherTabContent);

		TestUtils.assertViewChildren(bottomTabsContainer, tabContent, otherTabContent);
	}

	@Test(expected = TooManyTabsException.class)
	public void throwsExceptionWhenMoreThenFiveTabs() throws Exception {
		BottomTabsLayout bottomTabsContainer = createBottomTabsContainer();
		for (int i = 0; i < 6; i++) {
			ContainerStackLayout content = new ContainerStackLayout(activity);
			bottomTabsContainer.addTabContent("#" + i, content);
		}
	}

	@Test
	public void addFiveTabs() throws Exception {
		BottomTabsLayout bottomTabsContainer = createBottomTabsContainer();
		for (int i = 0; i < 5; i++) {
			ContainerStackLayout content = new ContainerStackLayout(activity);
			bottomTabsContainer.addTabContent("#" + i, content);
		}
	}

	@Test
	public void onlyFirstTabShouldBeVisible() throws Exception {
		ContainerStackLayout tabContent = new ContainerStackLayout(activity);
		ContainerStackLayout otherTabContent = new ContainerStackLayout(activity);

		BottomTabsLayout bottomTabsContainer = createBottomTabsContainer();
		bottomTabsContainer.addTabContent(TAB_NAME, tabContent);
		bottomTabsContainer.addTabContent(OTHER_TAB_NAME, otherTabContent);

		assertThat(tabContent.getVisibility()).isEqualTo(View.VISIBLE);
		assertThat(otherTabContent.getVisibility()).isEqualTo(View.GONE);
	}

	@Test
	public void listensToTabsSwitchingEvents() throws Exception {
		BottomTabsLayout bottomTabsContainer = createBottomTabsContainer();
		verify(bottomTabs).setSelectionListener(bottomTabsContainer);
	}

	@Test
	public void switchesBetweenFirstAndSecondTabs() throws Exception {
		ContainerStackLayout tabContent = new ContainerStackLayout(activity);
		ContainerStackLayout otherTabContent = new ContainerStackLayout(activity);

		BottomTabsLayout bottomTabsContainer = createBottomTabsContainer();
		bottomTabsContainer.addTabContent(TAB_NAME, tabContent);
		bottomTabsContainer.addTabContent(OTHER_TAB_NAME, otherTabContent);
		bottomTabsContainer.onTabSelected(1);

		assertThat(tabContent.getVisibility()).isEqualTo(View.GONE);
		assertThat(otherTabContent.getVisibility()).isEqualTo(View.VISIBLE);
	}

	@Test
	public void switchesBetweenSecondAndFirstTabs() throws Exception {
		ContainerStackLayout tabContent = new ContainerStackLayout(activity);
		ContainerStackLayout otherTabContent = new ContainerStackLayout(activity);

		BottomTabsLayout bottomTabsContainer = createBottomTabsContainer();
		bottomTabsContainer.addTabContent(TAB_NAME, tabContent);
		bottomTabsContainer.addTabContent(OTHER_TAB_NAME, otherTabContent);
		bottomTabsContainer.onTabSelected(1);
		bottomTabsContainer.onTabSelected(0);

		assertThat(tabContent.getVisibility()).isEqualTo(View.VISIBLE);
		assertThat(otherTabContent.getVisibility()).isEqualTo(View.GONE);
	}

	private BottomTabsLayout createBottomTabsContainer() {
		return new BottomTabsLayout(activity, bottomTabs);
	}
}
