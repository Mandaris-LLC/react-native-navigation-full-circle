package com.reactnativenavigation.viewcontrollers;

import android.view.View;

import com.reactnativenavigation.BaseTest;

import org.junit.Test;
import org.robolectric.shadow.api.Shadow;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class ViewControllerTest extends BaseTest {

	private ViewController uut;

	@Override
	public void beforeEach() {
		super.beforeEach();
		uut = new ViewController();
	}

	@Test
	public void holdsAView() throws Exception {
		assertThat(uut.getView()).isNull();
		View view = Shadow.newInstanceOf(View.class);
		uut.setView(view);
		assertThat(uut.getView()).isEqualTo(view);
	}

	@Test
	public void holdsAReferenceToNavigationControllerOrNull() throws Exception {
		assertThat(uut.getNavigationController()).isNull();
		NavigationController nav = new NavigationController(uut);
		assertThat(uut.getNavigationController()).isEqualTo(nav);
	}
}
