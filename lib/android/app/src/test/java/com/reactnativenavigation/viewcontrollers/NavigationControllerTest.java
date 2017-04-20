package com.reactnativenavigation.viewcontrollers;

import com.reactnativenavigation.BaseTest;

import org.junit.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class NavigationControllerTest extends BaseTest {

	@Test
	public void isAViewController() throws Exception {
		assertThat(new NavigationController()).isInstanceOf(ViewController.class);
	}

	@Test
	public void holdsAStackOfViewControllers() throws Exception {
		assertThat(new NavigationController().getChildControllers()).isEmpty();
		ViewController c1 = new ViewController();
		ViewController c2 = new ViewController();
		ViewController c3 = new ViewController();
		assertThat(new NavigationController(c1, c2, c3).getChildControllers()).containsExactly(c3, c2, c1);
	}

	@Test
	public void push() throws Exception {
		NavigationController uut = new NavigationController();
		ViewController c1 = new ViewController();
		assertThat(uut.getChildControllers()).isEmpty();
		uut.push(c1);
		assertThat(uut.getChildControllers()).containsExactly(c1);
	}

	@Test
	public void pop() throws Exception {
		NavigationController uut = new NavigationController();
		ViewController c1 = new ViewController();
		ViewController c2 = new ViewController();
		uut.push(c1);
		uut.push(c2);
		assertThat(uut.getChildControllers()).containsExactly(c2, c1);
		uut.pop();
		assertThat(uut.getChildControllers()).containsExactly(c1);
		uut.pop();
		assertThat(uut.getChildControllers()).isEmpty();
	}
}
