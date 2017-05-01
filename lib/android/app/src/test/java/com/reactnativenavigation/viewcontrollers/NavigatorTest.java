package com.reactnativenavigation.viewcontrollers;

import android.app.Activity;

import com.reactnativenavigation.BaseTest;
import com.reactnativenavigation.mocks.SimpleViewController;

import org.junit.Test;
import org.robolectric.Shadows;

import java.util.regex.Pattern;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class NavigatorTest extends BaseTest {
	private Activity activity;
	private Navigator uut;
	private ViewController child1;
	private ViewController child2;

	@Override
	public void beforeEach() {
		super.beforeEach();
		activity = newActivity();
		uut = new Navigator(activity);
		child1 = new SimpleViewController(activity, "child1");
		child2 = new SimpleViewController(activity, "child2");
	}


	@Test
	public void isActivityResumed() throws Exception {
		assertThat(uut.isActivityResumed()).isFalse();
		uut.onActivityCreated();
		assertThat(uut.isActivityResumed()).isFalse();
		uut.onActivityResumed();
		assertThat(uut.isActivityResumed()).isTrue();
		uut.onActivityPaused();
		assertThat(uut.isActivityResumed()).isFalse();
	}

	@Test
	public void setsItselfAsContentView() throws Exception {
		assertThat(Shadows.shadowOf(activity).getContentView()).isNull();
		uut.onActivityCreated();
		assertThat(Shadows.shadowOf(activity).getContentView()).isNotNull().isEqualTo(uut.getView());
	}

	@Test
	public void setRoot_AddsChildControllerView() throws Exception {
		assertThat(uut.getView().getChildCount()).isZero();
		uut.setRoot(child1);
		assertHasSingleChildViewOf(child1);
	}

	@Test
	public void setRoot_ReplacesExistingChildControllerViews() throws Exception {
		uut.setRoot(child1);
		uut.setRoot(child2);
		assertHasSingleChildViewOf(child2);
	}

	@Test
	public void holdsUniqueId() throws Exception {
		assertThat(uut.getId()).startsWith("navigator").matches(Pattern.compile("navigator\\d"));
		assertThat(new Navigator(activity).getId()).isNotEqualTo(uut.getId());
	}

	private void assertHasSingleChildViewOf(ViewController vc) {
		assertThat(uut.getView().getChildCount()).isEqualTo(1);
		assertThat(uut.getView().getChildAt(0)).isEqualTo(vc.getView()).isNotNull();
	}

}
