package com.reactnativenavigation.viewcontrollers;

import android.app.Activity;
import android.view.View;
import android.view.ViewParent;

import com.reactnativenavigation.BaseTest;
import com.reactnativenavigation.mocks.SimpleViewController;

import org.assertj.android.api.Assertions;
import org.junit.Test;
import org.robolectric.Shadows;

import java.lang.reflect.Field;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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
		assertThat(uut.getParentStackController()).isNull();
		StackController nav = new StackController(activity, "stack");
		nav.push(uut);
		assertThat(uut.getParentStackController()).isEqualTo(nav);
	}

	@Test
	public void handleBackDefaultFalse() throws Exception {
		assertThat(uut.handleBack()).isFalse();
	}

	@Test
	public void holdsId() throws Exception {
		assertThat(uut.getId()).isEqualTo("uut");
	}

	@Test
	public void isSameId() throws Exception {
		assertThat(uut.isSameId("")).isFalse();
		assertThat(uut.isSameId(null)).isFalse();
		assertThat(uut.isSameId("uut")).isTrue();
	}

	@Test
	public void findControllerById_SelfOrNull() throws Exception {
		assertThat(uut.findControllerById("456")).isNull();
		assertThat(uut.findControllerById("uut")).isEqualTo(uut);
	}

	@Test
	public void onAppear_WhenShown() throws Exception {
		ViewController spy = spy(uut);
		spy.getView().getViewTreeObserver().dispatchOnGlobalLayout();
		Assertions.assertThat(spy.getView()).isNotShown();
		verify(spy, times(0)).onAppear();

		Shadows.shadowOf(spy.getView()).setMyParent(mock(ViewParent.class));
		spy.getView().getViewTreeObserver().dispatchOnGlobalLayout();
		Assertions.assertThat(spy.getView()).isShown();

		verify(spy, times(1)).onAppear();
	}

	@Test
	public void onAppear_CalledAtMostOnce() throws Exception {
		ViewController spy = spy(uut);
		Shadows.shadowOf(spy.getView()).setMyParent(mock(ViewParent.class));
		Assertions.assertThat(spy.getView()).isShown();
		spy.getView().getViewTreeObserver().dispatchOnGlobalLayout();
		spy.getView().getViewTreeObserver().dispatchOnGlobalLayout();
		spy.getView().getViewTreeObserver().dispatchOnGlobalLayout();

		verify(spy, times(1)).onAppear();
	}

	@Test
	public void onDisappear_WhenNotShown_AfterOnAppearWasCalled() throws Exception {
		ViewController spy = spy(uut);
		Shadows.shadowOf(spy.getView()).setMyParent(mock(ViewParent.class));
		Assertions.assertThat(spy.getView()).isShown();
		spy.getView().getViewTreeObserver().dispatchOnGlobalLayout();
		verify(spy, times(1)).onAppear();
		verify(spy, times(0)).onDisappear();

		spy.getView().setVisibility(View.GONE);
		spy.getView().getViewTreeObserver().dispatchOnGlobalLayout();
		Assertions.assertThat(spy.getView()).isNotShown();
		verify(spy, times(1)).onDisappear();
	}

	@Test
	public void onDisappear_CalledAtMostOnce() throws Exception {
		ViewController spy = spy(uut);
		Shadows.shadowOf(spy.getView()).setMyParent(mock(ViewParent.class));
		Assertions.assertThat(spy.getView()).isShown();
		spy.getView().getViewTreeObserver().dispatchOnGlobalLayout();
		spy.getView().setVisibility(View.GONE);
		spy.getView().getViewTreeObserver().dispatchOnGlobalLayout();
		spy.getView().getViewTreeObserver().dispatchOnGlobalLayout();
		spy.getView().getViewTreeObserver().dispatchOnGlobalLayout();
		verify(spy, times(1)).onDisappear();
	}

	@Test
	public void onDestroy_RemovesGlobalLayoutListener() throws Exception {
		ViewController spy = spy(uut);
		View view = spy.getView();
		Shadows.shadowOf(view).setMyParent(mock(ViewParent.class));

		spy.onDestroy();

		Assertions.assertThat(view).isShown();
		view.getViewTreeObserver().dispatchOnGlobalLayout();
		verify(spy, times(0)).onAppear();
		verify(spy, times(0)).onDisappear();
	}

	@Test
	public void onDestroy_NullifiesTheView() throws Exception {
		assertThat(uut.getView()).isNotNull();
		uut.onDestroy();
		Field field = ViewController.class.getDeclaredField("view");
		field.setAccessible(true);
		assertThat(field.get(uut)).isNull();
	}
}

