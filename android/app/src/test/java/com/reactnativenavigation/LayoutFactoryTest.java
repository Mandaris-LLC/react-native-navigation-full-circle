package com.reactnativenavigation;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.reactnativenavigation.layout.bottomtabs.BottomTabs;
import com.reactnativenavigation.layout.bottomtabs.BottomTabsContainer;
import com.reactnativenavigation.layout.Container;
import com.reactnativenavigation.layout.ContainerStack;
import com.reactnativenavigation.layout.LayoutFactory;
import com.reactnativenavigation.layout.LayoutNode;
import com.reactnativenavigation.layout.bottomtabs.BottomTabsCreator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
public class LayoutFactoryTest {

    private final static String VIEW_ID = "myUniqueId";
    private final static String VIEW_NAME = "myName";

    private final static String OTHER_VIEW_ID = "anotherUniqueId";
    private final static String OTHER_VIEW_NAME = "anotherName";

    private Activity activity;
    private View mockView;
    private LayoutFactory.RootViewCreator rootViewCreator;

    @Before
    public void setUp() {
        activity = Robolectric.buildActivity(AppCompatActivity.class).get();
        mockView = new View(activity);
        rootViewCreator = mock(LayoutFactory.RootViewCreator.class);
    }

    @Test
    public void returnsContainerThatHoldsTheRootView() throws Exception {
        when(rootViewCreator.createRootView(eq(VIEW_ID), eq(VIEW_NAME))).thenReturn(mockView);
        final LayoutNode node = createContainerNode();

        final ViewGroup result = (ViewGroup) createLayoutFactory().create(node);

        assertThat(result).isInstanceOf(Container.class);
        assertViewChildren(result, mockView);
    }

    @Test
    public void returnsContainerStack() throws Exception  {
        when(rootViewCreator.createRootView(eq(VIEW_ID), eq(VIEW_NAME))).thenReturn(mockView);
        final LayoutNode containerNode = createContainerNode();
        final LayoutNode stackNode = getContainerStackNode(containerNode);

        final ViewGroup result = (ViewGroup) createLayoutFactory().create(stackNode);

        assertThat(result).isInstanceOf(ContainerStack.class);
        ViewGroup container = (ViewGroup) assertViewChildrenCount(result, 1).get(0);
        assertViewChildren(container, mockView);
    }

    @Test
    public void returnsContainerStackWithMultipleViews() throws Exception  {
        final View mockView1 = mock(View.class);
        final View mockView2 = mock(View.class);
        when(rootViewCreator.createRootView(eq(VIEW_ID), eq(VIEW_NAME))).thenReturn(mockView1);
        when(rootViewCreator.createRootView(eq(OTHER_VIEW_ID), eq(OTHER_VIEW_NAME))).thenReturn(mockView2);

        final LayoutNode containerNode1 = createContainerNode(VIEW_ID, VIEW_NAME);
        final LayoutNode containerNode2 = createContainerNode(OTHER_VIEW_ID, OTHER_VIEW_NAME);
        final LayoutNode stackNode = getContainerStackNode(Arrays.asList(containerNode1, containerNode2));

        final ViewGroup result = (ViewGroup) createLayoutFactory().create(stackNode);

        assertThat(result).isInstanceOf(ContainerStack.class);
        List<View> containers = assertViewChildrenCount(result, 2);
        ViewGroup container1 = (ViewGroup) containers.get(0);
        ViewGroup container2 = (ViewGroup) containers.get(1);
        assertViewChildren(container1, mockView1);
        assertViewChildren(container2, mockView2);
    }

    @Test
    public void returnsSingleTabContent() throws Exception {
        when(rootViewCreator.createRootView(eq(VIEW_ID), eq(VIEW_NAME))).thenReturn(mockView);
        final LayoutNode containerNode = createContainerNode();
        final LayoutNode tabNode = createTabNode(containerNode);

        final View result = createLayoutFactory().create(tabNode);

        assertThat(result).isInstanceOf(BottomTabsContainer.class);
        View containerView = assertViewChildrenCount((BottomTabsContainer) result, 1).get(0);
        assertThat(containerView).isInstanceOf(Container.class);
        assertViewChildren((Container) containerView, mockView);
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwsExceptionForUnknownType() throws Exception  {
        when(rootViewCreator.createRootView(eq(VIEW_ID), eq(VIEW_NAME))).thenReturn(mockView);
        final LayoutNode node = new LayoutNode(VIEW_ID, "***unknownType***", Collections.<String, Object>emptyMap());

        createLayoutFactory().create(node);
    }

    private LayoutFactory createLayoutFactory() {
        BottomTabs bottomTabs = mock(BottomTabs.class);
        BottomTabsCreator bottomTabsCreator = mock(BottomTabsCreator.class);
        when(bottomTabsCreator.create()).thenReturn(bottomTabs);
        return new LayoutFactory(activity, rootViewCreator, bottomTabsCreator);
    }

    private LayoutNode createContainerNode() {
        return createContainerNode(VIEW_ID, VIEW_NAME);
    }

    private LayoutNode createContainerNode(final String id, final String name) {
        return new LayoutNode(id, "Container", new HashMap<String, Object>() {{ put("name", name); }});
    }

    private LayoutNode getContainerStackNode(LayoutNode innerNode) {
        return getContainerStackNode(Arrays.asList(innerNode));
    }

    private LayoutNode getContainerStackNode(List<LayoutNode> children) {
        LayoutNode node = new LayoutNode();
        node.type = "ContainerStack";
        node.children = children;
        return node;
    }

    private LayoutNode createTabNode(LayoutNode children) {
        LayoutNode node = new LayoutNode();
        node.type = "BottomTabs";
        node.children = Arrays.asList(children);
        return node;
    }

    private List<View> assertViewChildrenCount(ViewGroup view, int count) {
        assertThat(view.getChildCount()).isEqualTo(count);

        final List<View> children = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            children.add(view.getChildAt(i));
        }
        return children;
    }

    private void assertViewChildren(ViewGroup view, View... children) {
        final List<View> childViews = assertViewChildrenCount(view, children.length);
        assertThat(childViews).isEqualTo(Arrays.asList(children));
    }
}
