package com.reactnativenavigation.viewcontrollers;

import com.reactnativenavigation.BaseTest;

import org.junit.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class NavigationControllerTest extends BaseTest {

	private NavigationController uut;
	private ViewController child1;
	private ViewController child2;
	private ViewController child3;

	@Override
	public void beforeEach() {
		super.beforeEach();
		uut = new NavigationController();
		child1 = new ViewController();
		child2 = new ViewController();
		child3 = new ViewController();
	}

	@Test
	public void isAViewController() throws Exception {
		assertThat(uut).isInstanceOf(ViewController.class);
	}

	@Test
	public void holdsAStackOfViewControllers() throws Exception {
		assertThat(uut.getChildControllers()).isEmpty();
		assertThat(new NavigationController(child1, child2, child3).getChildControllers()).containsExactly(child3, child2, child1);
		assertThat(new NavigationController(child1, child2, child3).getChildControllers().peek()).isEqualTo(child3);
	}

	@Test
	public void push() throws Exception {
		assertThat(uut.getChildControllers()).isEmpty();
		uut.push(child1);
		assertThat(uut.getChildControllers()).containsExactly(child1);
	}

	@Test
	public void pop() throws Exception {
		uut.push(child1);
		uut.push(child2);
		assertThat(uut.getChildControllers()).containsExactly(child2, child1);
		uut.pop();
		assertThat(uut.getChildControllers()).containsExactly(child1);
		uut.pop();
		assertThat(uut.getChildControllers()).isEmpty();
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

}
