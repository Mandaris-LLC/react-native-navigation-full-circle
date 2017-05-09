package com.reactnativenavigation.viewcontrollers;

import android.app.Activity;
import android.view.ViewGroup;

import com.reactnativenavigation.BaseTest;
import com.reactnativenavigation.mocks.SimpleViewController;

import org.junit.Test;
import org.robolectric.Shadows;

import java.util.Arrays;

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
		assertHasSingleChildViewOf(uut, child1);
	}

	@Test
	public void setRoot_ReplacesExistingChildControllerViews() throws Exception {
		uut.setRoot(child1);
		uut.setRoot(child2);
		assertHasSingleChildViewOf(uut, child2);
	}

	@Test
	public void hasUniqueId() throws Exception {
		assertThat(uut.getId()).startsWith("navigator");
		assertThat(new Navigator(activity).getId()).isNotEqualTo(uut.getId());
	}

	@Test
	public void push() throws Exception {
		StackController stackController = new StackController(activity, "stack1");
		stackController.push(child1);
		uut.setRoot(stackController);

		assertHasSingleChildViewOf(uut, stackController);
		assertHasSingleChildViewOf(stackController, child1);

		uut.push(child1.getId(), child2);

		assertHasSingleChildViewOf(uut, stackController);
		assertHasSingleChildViewOf(stackController, child2);
	}

	@Test
	public void push_InvalidPushWithoutAStack_DoesNothing() throws Exception {
		uut.setRoot(child1);
		uut.push(child1.getId(), child2);
		assertHasSingleChildViewOf(uut, child1);
	}

	@Test
	public void push_OnCorrectStackByFindingChildId() throws Exception {
		BottomTabsController bottomTabsController = new BottomTabsController(activity, "tabsController");
		StackController stack1 = new StackController(activity, "stack1");
		StackController stack2 = new StackController(activity, "stack2");
		stack1.push(child1);
		stack2.push(child2);
		bottomTabsController.setTabs(Arrays.<ViewController>asList(stack1, stack2));

		uut.setRoot(bottomTabsController);
		SimpleViewController newChild = new SimpleViewController(activity, "new child");
		uut.push(child2.getId(), newChild);

		assertThat(stack1.getChildControllers()).doesNotContain(newChild);
		assertThat(stack2.getChildControllers()).contains(newChild);
	}

	private void assertHasSingleChildViewOf(ViewController parent, ViewController child) {
		assertThat(((ViewGroup) parent.getView()).getChildCount()).isEqualTo(1);
		assertThat(((ViewGroup) parent.getView()).getChildAt(0)).isEqualTo(child.getView()).isNotNull();
	}
}
