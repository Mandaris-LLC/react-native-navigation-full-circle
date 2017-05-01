package com.reactnativenavigation.viewcontrollers;

import android.app.Activity;
import android.view.View;

import com.reactnativenavigation.BaseTest;
import com.reactnativenavigation.mocks.SimpleViewController;

import org.junit.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class ViewControllerTest extends BaseTest {

	private ViewController uut;
	private Activity activity;

	@Override
	public void beforeEach() {
		super.beforeEach();
		activity = newActivity();
		uut = new SimpleViewController(activity, "uut");
	}

	@Test
	public void holdsAView() throws Exception {
		assertThat(uut.getView()).isNotNull().isInstanceOf(View.class);
	}

	@Test
	public void holdsARefToActivity() throws Exception {
		assertThat(uut.getActivity()).isNotNull().isEqualTo(activity);
	}

	@Test
	public void canOverrideViewCreation() throws Exception {
		final View otherView = new View(activity);
		ViewController myController = new ViewController(activity, "vc") {
			@Override
			protected View createView() {
				return otherView;
			}
		};
		assertThat(myController.getView()).isEqualTo(otherView);
	}

	@Test
	public void holdsAReferenceToStackControllerOrNull() throws Exception {
		assertThat(uut.getStackController()).isNull();
		StackController nav = new StackController(activity, "stack");
		nav.push(uut);
		assertThat(uut.getStackController()).isEqualTo(nav);
	}

	@Test
	public void handleBackDefaultFalse() throws Exception {
		assertThat(uut.handleBack()).isFalse();
	}

	@Test
	public void holdsId() throws Exception {
		assertThat(uut.getId()).isEqualTo("uut");
	}
}
