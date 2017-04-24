package com.reactnativenavigation.viewcontrollers;

import android.app.Activity;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.reactnativenavigation.BaseTest;
import com.reactnativenavigation.mocks.SimpleViewController;

import org.junit.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class StackControllerTest extends BaseTest {

	private Activity activity;
	private StackController uut;
	private ViewController child1;
	private ViewController child2;
	private ViewController child3;

	@Override
	public void beforeEach() {
		super.beforeEach();
		activity = newActivity();
		uut = new StackController(activity);
		child1 = new SimpleViewController(activity, "child1");
		child2 = new SimpleViewController(activity, "child2");
		child3 = new SimpleViewController(activity, "child3");
	}

	@Test
	public void isAViewController() throws Exception {
		assertThat(uut).isInstanceOf(ViewController.class);
	}

	@Test
	public void holdsAStackOfViewControllers() throws Exception {
		assertThat(uut.getStack()).isEmpty();
		uut.push(child1);
		uut.push(child2);
		uut.push(child3);
		assertThat(uut.getStack()).containsOnly(child3, child2, child1);
		assertThat(uut.peek()).isEqualTo(child3);
	}

	@Test
	public void push() throws Exception {
		assertThat(uut.getStack()).isEmpty();
		uut.push(child1);
		assertThat(uut.getStack()).containsOnly(child1);
	}

	@Test
	public void pop() throws Exception {
		uut.push(child1);
		uut.push(child2);
		assertThat(uut.getStack()).containsOnly(child2, child1);
		uut.pop();
		assertThat(uut.getStack()).containsOnly(child1);
	}

	@Test
	public void stackOperations() throws Exception {
		assertThat(uut.peek()).isNull();
		assertThat(uut.size()).isZero();
		assertThat(uut.isEmpty()).isTrue();
		uut.push(child1);
		assertThat(uut.peek()).isEqualTo(child1);
		assertThat(uut.size()).isEqualTo(1);
		assertThat(uut.isEmpty()).isFalse();
	}

	@Test
	public void pushAssignsRefToSelfOnPushedController() throws Exception {
		assertThat(child1.getStackController()).isNull();
		uut.push(child1);
		assertThat(child1.getStackController()).isEqualTo(uut);

		StackController anotherNavController = new StackController(activity);
		anotherNavController.push(child2);
		assertThat(child2.getStackController()).isEqualTo(anotherNavController);
	}

	@Test
	public void handleBack_PopsUnlessSingleChild() throws Exception {
		assertThat(uut.isEmpty()).isTrue();
		assertThat(uut.handleBack()).isFalse();

		uut.push(child1);
		assertThat(uut.size()).isEqualTo(1);
		assertThat(uut.handleBack()).isFalse();

		uut.push(child2);
		assertThat(uut.size()).isEqualTo(2);
		assertThat(uut.handleBack()).isTrue();
		assertThat(uut.size()).isEqualTo(1);
		assertThat(uut.handleBack()).isFalse();
	}

	@Test
	public void popDoesNothingWhenZeroOrOneChild() throws Exception {
		assertThat(uut.getStack()).isEmpty();
		uut.pop();
		assertThat(uut.getStack()).isEmpty();

		uut.push(child1);
		uut.pop();
		assertThat(uut.getStack()).containsOnly(child1);
	}

	@Test
	public void canPopWhenSizeIsMoreThanOne() throws Exception {
		assertThat(uut.getStack()).isEmpty();
		assertThat(uut.canPop()).isFalse();
		uut.push(child1);
		assertThat(uut.getStack()).containsOnly(child1);
		assertThat(uut.canPop()).isFalse();
		uut.push(child2);
		assertThat(uut.getStack()).containsOnly(child1, child2);
		assertThat(uut.canPop()).isTrue();
	}

	@Test
	public void constructsSelfWithFrameLayout() throws Exception {
		assertThat(uut.getView())
				.isNotNull()
				.isInstanceOf(ViewGroup.class)
				.isInstanceOf(FrameLayout.class);
	}

	@Test
	public void pushAddsToViewTree() throws Exception {
		assertThat(uut.getView().getChildCount()).isZero();
		uut.push(child1);
		assertHasSingleChildViewOfController(child1);
	}

	@Test
	public void pushRemovesPreviousFromTree() throws Exception {
		assertThat(uut.getView().getChildCount()).isZero();
		uut.push(child1);
		assertHasSingleChildViewOfController(child1);
		uut.push(child2);
		assertHasSingleChildViewOfController(child2);
	}

	@Test
	public void popReplacesViewWithPrevious() throws Exception {
		uut.push(child1);
		uut.push(child2);
		assertHasSingleChildViewOfController(child2);
		uut.pop();
		assertHasSingleChildViewOfController(child1);
	}

	@Test
	public void popSpecificWhenTopIsRegularPop() throws Exception {
		uut.push(child1);
		uut.push(child2);
		uut.pop(child2);
		assertThat(uut.getStack()).containsOnly(child1);
		assertHasSingleChildViewOfController(child1);
	}

	@Test
	public void popSpecificDeepInStack() throws Exception {
		uut.push(child1);
		uut.push(child2);
		assertHasSingleChildViewOfController(child2);

		uut.pop(child1);
		assertThat(uut.getStack()).containsOnly(child2);
		assertHasSingleChildViewOfController(child2);
	}

	@Test
	public void getStackControllerReturnsSelf() throws Exception {
		assertThat(uut.getStackController()).isEqualTo(uut);
	}

	private void assertHasSingleChildViewOfController(ViewController childController) {
		assertThat(uut.getView().getChildCount()).isEqualTo(1);
		assertThat(uut.getView().getChildAt(0)).isEqualTo(childController.getView());
	}
}
