package com.reactnativenavigation;

import android.app.Activity;

import com.reactnativenavigation.mocks.SimpleViewController;

import org.junit.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class StoreTest extends BaseTest {

	private Store uut;

	@Override
	public void beforeEach() {
		super.beforeEach();
		uut = new Store();
	}

	@Test
	public void holdsViewControllersById() throws Exception {
		SimpleViewController viewController = new SimpleViewController(mock(Activity.class), "my controller");

		assertThat(uut.getViewController("the id")).isNull();

		uut.setViewController("the id", viewController);
		assertThat(uut.getViewController("the id")).isEqualTo(viewController);
	}
}
