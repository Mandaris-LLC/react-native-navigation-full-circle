package com.reactnativenavigation.e2e.androide2e;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(AndroidJUnit4.class)
public class EnvironmentTest {
	@Test
	public void instrumentationAndAssertJ() throws Exception {
		assertThat(getInstrumentation().getTargetContext().getPackageName()).isEqualTo("com.reactnativenavigation.e2e.androide2e");
	}
}
