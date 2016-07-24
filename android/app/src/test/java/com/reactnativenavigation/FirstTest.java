package com.reactnativenavigation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

//TODO test the shit out of this project
@RunWith(RobolectricTestRunner.class)
public class FirstTest {
    @Test
    public void woohoo() {
        assertThat(1 + 2, is(3));
    }
}
