package com.reactnativenavigation;

import org.junit.Test;
import org.robolectric.Robolectric;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class NavigationActivityTest extends BaseTest {
    @Test
    public void showsSplashView() throws Exception {
        NavigationActivity activity = Robolectric.setupActivity(NavigationActivity.class);
        assertThat(activity).isNotNull();
    }
}
