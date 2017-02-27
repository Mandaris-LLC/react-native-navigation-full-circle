package com.reactnativenavigation.e2e.androide2e;

import android.annotation.TargetApi;
import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 23)
@TargetApi(23)
public class EnvironmentTest {
    @Test
    public void instrumentationAndAssertJ() throws Exception {
        assertThat(getInstrumentation().getTargetContext().getPackageName()).isEqualTo("com.reactnativenavigation.e2e.androide2e");
    }
}
