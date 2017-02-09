package com.reactnativenavigation;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import com.reactnativenavigation.layout.Container;
import com.reactnativenavigation.layout.ContainerStack;
import com.reactnativenavigation.layout.LayoutFactory;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Java6Assertions.assertThat;


@RunWith(RobolectricTestRunner.class)
public class LayoutFactoryTest {
    @Before
    public void setUp() {

    }

    @Test
    public void returnsContainerThatHoldsTheRootView() {
        final AtomicReference<String> idRef = new AtomicReference<>();
        final AtomicReference<String> nameRef = new AtomicReference<>();
        final AtomicReference<View> viewRef = new AtomicReference<>();

        LayoutFactory.RootViewCreator rootViewCreator = new LayoutFactory.RootViewCreator() {
            @Override
            public View createRootView(String id, String name) {
                idRef.set(id);
                nameRef.set(name);
                viewRef.set(new View(Robolectric.setupActivity(Activity.class)));
                return viewRef.get();
            }
        };

        Map<String, Object> node = new HashMap() {{
            Map<String, Object> data = new HashMap<>();
            data.put("name", "MyName");
            put("id", "myUniqueId");
            put("data", data);
            put("type", "Container");
        }};

        ViewGroup result =
                (ViewGroup) new LayoutFactory(Robolectric.buildActivity(Activity.class).get(), rootViewCreator).create(node);
        assertThat(result).isInstanceOf(Container.class);
        assertThat(result.getChildCount()).isEqualTo(1);
        assertThat(result.getChildAt(0)).isEqualTo(viewRef.get());
    }


    @Test
    public void returnsContainerStack() {
        final AtomicReference<String> idRef = new AtomicReference<>();
        final AtomicReference<String> nameRef = new AtomicReference<>();
        final AtomicReference<View> viewRef = new AtomicReference<>();

        LayoutFactory.RootViewCreator rootViewCreator = new LayoutFactory.RootViewCreator() {
            @Override
            public View createRootView(String id, String name) {
                idRef.set(id);
                nameRef.set(name);
                viewRef.set(new View(Robolectric.setupActivity(Activity.class)));
                return viewRef.get();
            }
        };

        Map<String, Object> node = new HashMap() {{
            Map<String, Object> data = new HashMap<>();
            data.put("name", "MyName");
            put("id", "myUniqueId");
            put("data", data);
            put("type", "Container");
        }};

        HashMap<String, Object> outerNode = new HashMap<>();
        outerNode.put("type", "ContainerStack");
        List<Map<String, Object>> children = new ArrayList<>();
        children.add(node);
        outerNode.put("children", children);

        ViewGroup result =
                (ViewGroup) new LayoutFactory(Robolectric.buildActivity(Activity.class).get(), rootViewCreator).create(outerNode);
        assertThat(result).isInstanceOf(ContainerStack.class);
        assertThat(result.getChildCount()).isEqualTo(1);
        ViewGroup container = (ViewGroup) result.getChildAt(0);
        assertThat(container.getChildCount()).isEqualTo(1);
        assertThat(container.getChildAt(0)).isEqualTo(viewRef.get());
    }
}
