package com.reactnativenavigation.viewcontrollers;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.reactnativenavigation.BaseTest;
import com.reactnativenavigation.mocks.SimpleViewController;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class ParentControllerTest extends BaseTest {

	private Activity activity;

	@Override
	public void beforeEach() {
		super.beforeEach();
		activity = newActivity();
	}

	@Test
	public void holdsViewGroup() throws Exception {
		ParentController uut = new ParentController(activity, "uut") {
			@Override
			public Collection<ViewController> getChildControllers() {
				return Collections.emptyList();
			}

			@NonNull
			@Override
			protected View createView() {
				return new FrameLayout(activity);
			}
		};

		assertThat(uut.getView()).isInstanceOf(ViewGroup.class);
	}

	@Test
	public void findControllerById_ReturnsSelfIfSameId() throws Exception {
		ParentController uut = new ParentController(activity, "uut") {
			@Override
			public Collection<ViewController> getChildControllers() {
				return Collections.emptyList();
			}

			@NonNull
			@Override
			protected View createView() {
				return new FrameLayout(activity);
			}
		};

		assertThat(uut.findControllerById("123")).isNull();
		assertThat(uut.findControllerById(uut.getId())).isEqualTo(uut);
	}

	@Test
	public void findControllerById_DeeplyInOneOfTheChildren() throws Exception {
		ViewController child1 = new SimpleViewController(activity, "child1");
		ViewController child2 = new SimpleViewController(activity, "child2");

		final StackController someInnerStack = new StackController(activity, "stack1");
		someInnerStack.push(child1);
		someInnerStack.push(child2);

		ParentController uut = new ParentController(activity, "uut") {
			@Override
			public Collection<ViewController> getChildControllers() {
				return Arrays.<ViewController>asList(someInnerStack);
			}

			@NonNull
			@Override
			protected View createView() {
				return new FrameLayout(activity);
			}
		};

		assertThat(uut.findControllerById("stack1")).isEqualTo(someInnerStack);
		assertThat(uut.findControllerById("child1")).isEqualTo(child1);
		assertThat(uut.findControllerById("child2")).isEqualTo(child2);
	}

	@Test
	public void findParentStackControllerForChildId() throws Exception {
		ViewController child1 = new SimpleViewController(activity, "child1");
		ViewController child2 = new SimpleViewController(activity, "child2");

		final StackController someInnerStack = new StackController(activity, "stack1");
		someInnerStack.push(child1);
		someInnerStack.push(child2);

		ParentController uut = new ParentController(activity, "uut") {
			@Override
			public Collection<ViewController> getChildControllers() {
				return Arrays.<ViewController>asList(someInnerStack);
			}

			@NonNull
			@Override
			protected View createView() {
				return new FrameLayout(activity);
			}
		};

		assertThat(uut.findParentStackControllerForChildId("not existing child")).isNull();
		assertThat(uut.findParentStackControllerForChildId("child2")).isEqualTo(someInnerStack);
	}
}
