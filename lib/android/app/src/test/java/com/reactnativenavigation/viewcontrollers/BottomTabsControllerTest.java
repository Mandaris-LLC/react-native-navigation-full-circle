package com.reactnativenavigation.viewcontrollers;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.reactnativenavigation.BaseTest;
import com.reactnativenavigation.mocks.SimpleViewController;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BottomTabsControllerTest extends BaseTest {

	private Activity activity;
	private BottomTabsController uut;
	private ViewController child1;
	private ViewController child2;
	private ViewController child3;

	@Override
	public void beforeEach() {
		super.beforeEach();
		activity = newActivity();
		uut = new BottomTabsController(activity, "uut");
		child1 = new SimpleViewController(activity, "child1");
		child2 = new SimpleViewController(activity, "child2");
		child3 = new SimpleViewController(activity, "child3");
	}

	@Test
	public void containsRelativeLayoutView() throws Exception {
		assertThat(uut.getView()).isInstanceOf(RelativeLayout.class);
		assertThat(uut.getView().getChildAt(0)).isInstanceOf(BottomNavigationView.class);
	}

	@Test(expected = RuntimeException.class)
	public void setTabs_ThrowWhenMoreThan5() throws Exception {
		List<ViewController> tabs = createTabs();
		tabs.add(new SimpleViewController(activity, "6"));
		uut.setTabs(tabs);
	}

	@Test
	public void setTabs_AddAllViewsAsGoneExceptFirst() throws Exception {
		List<ViewController> tabs = createTabs();
		uut.setTabs(tabs);
		assertThat(uut.getView().getChildCount()).isEqualTo(6);
		assertThat(uut.getView().getChildAt(1).getVisibility()).isEqualTo(View.VISIBLE);
		assertThat(uut.getView().getChildAt(2).getVisibility()).isEqualTo(View.GONE);
		assertThat(uut.getView().getChildAt(3).getVisibility()).isEqualTo(View.GONE);
		assertThat(uut.getView().getChildAt(4).getVisibility()).isEqualTo(View.GONE);
		assertThat(uut.getView().getChildAt(5).getVisibility()).isEqualTo(View.GONE);
	}

	@Test
	public void onTabSelected_SelectsTab() throws Exception {
		uut.setTabs(createTabs());
		assertThat(uut.getSelectedIndex()).isZero();

		MenuItem menuItem = mock(MenuItem.class);
		when(menuItem.getItemId()).thenReturn(3);
		assertThat(uut.onNavigationItemSelected(menuItem)).isTrue();

		assertThat(uut.getSelectedIndex()).isEqualTo(3);
		assertThat(uut.getTabs().get(0).getView().getVisibility()).isEqualTo(View.GONE);
		assertThat(uut.getTabs().get(1).getView().getVisibility()).isEqualTo(View.GONE);
		assertThat(uut.getTabs().get(2).getView().getVisibility()).isEqualTo(View.GONE);
		assertThat(uut.getTabs().get(3).getView().getVisibility()).isEqualTo(View.VISIBLE);
		assertThat(uut.getTabs().get(4).getView().getVisibility()).isEqualTo(View.GONE);
	}

	@NonNull
	private List<ViewController> createTabs() {
		List<ViewController> tabs = new ArrayList<>();
		tabs.add(new SimpleViewController(activity, "1"));
		tabs.add(new SimpleViewController(activity, "2"));
		tabs.add(new SimpleViewController(activity, "3"));
		tabs.add(new SimpleViewController(activity, "4"));
		tabs.add(new SimpleViewController(activity, "5"));
		return tabs;
	}
}
