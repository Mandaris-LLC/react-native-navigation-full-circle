package com.reactnativenavigation;

import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class TestUtils {
    public static List<View> assertViewChildrenCount(ViewGroup view, int count) {
        assertThat(view.getChildCount()).isEqualTo(count);

        final List<View> children = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            children.add(view.getChildAt(i));
        }
        return children;
    }

    public static void assertViewChildren(ViewGroup view, View... children) {
        final List<View> childViews = assertViewChildrenCount(view, children.length);
        assertThat(childViews).isEqualTo(Arrays.asList(children));
    }
}
