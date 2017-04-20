package com.reactnativenavigation.viewcontrollers;

import android.view.View;

import com.reactnativenavigation.BaseTest;

import org.junit.Test;
import org.robolectric.shadow.api.Shadow;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class ViewControllerTest extends BaseTest {
	@Test
	public void holdsAView() throws Exception {
		ViewController uut = new ViewController();
		assertThat(uut.getView()).isNull();
		View view = Shadow.newInstanceOf(View.class);
		uut.setView(view);
		assertThat(uut.getView()).isEqualTo(view);
	}


}
