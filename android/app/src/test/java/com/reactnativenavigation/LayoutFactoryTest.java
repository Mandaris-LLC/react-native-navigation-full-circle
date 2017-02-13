package com.reactnativenavigation;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.reactnativenavigation.layout.Container;
import com.reactnativenavigation.layout.ContainerStack;
import com.reactnativenavigation.layout.LayoutFactory;
import com.reactnativenavigation.layout.LayoutNode;
import com.reactnativenavigation.layout.bottomtabs.BottomTabs;
import com.reactnativenavigation.layout.bottomtabs.BottomTabsContainer;
import com.reactnativenavigation.layout.bottomtabs.BottomTabsCreator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
public class LayoutFactoryTest {

    private final static String VIEW_ID = "myUniqueId";
    private final static String VIEW_NAME = "myName";

    private final static String OTHER_VIEW_ID = "anotherUniqueId";
    private final static String OTHER_VIEW_NAME = "anotherName";

    private Activity activity;
    private View mockView;
    private View otherMockView;
    private LayoutFactory.RootViewCreator rootViewCreator;

    @Before
    public void setUp() {
        activity = Robolectric.buildActivity(AppCompatActivity.class).get();
        mockView = new View(activity);
        otherMockView = new View(activity);
        rootViewCreator = mock(LayoutFactory.RootViewCreator.class);
    }

    @Test
    public void returnsContainerThatHoldsTheRootView() throws Exception {
        when(rootViewCreator.createRootView(eq(VIEW_ID), eq(VIEW_NAME))).thenReturn(mockView);
        final LayoutNode node = createContainerNode();

        final ViewGroup result = (ViewGroup) createLayoutFactory().create(node);

        assertThat(result).isInstanceOf(Container.class);
        TestUtils.assertViewChildren(result, mockView);
    }

    @Test
    public void returnsContainerStack() throws Exception {
        when(rootViewCreator.createRootView(eq(VIEW_ID), eq(VIEW_NAME))).thenReturn(mockView);
        final LayoutNode containerNode = createContainerNode();
        final LayoutNode stackNode = createContainerStackNode(containerNode);

        final ViewGroup result = (ViewGroup) createLayoutFactory().create(stackNode);

        assertThat(result).isInstanceOf(ContainerStack.class);
        ViewGroup container = (ViewGroup) TestUtils.assertViewChildrenCount(result, 1).get(0);
        TestUtils.assertViewChildren(container, mockView);
    }

    @Test
    public void returnsContainerStackWithMultipleViews() throws Exception {
        final View mockView1 = mock(View.class);
        final View mockView2 = mock(View.class);
        when(rootViewCreator.createRootView(eq(VIEW_ID), eq(VIEW_NAME))).thenReturn(mockView1);
        when(rootViewCreator.createRootView(eq(OTHER_VIEW_ID), eq(OTHER_VIEW_NAME))).thenReturn(mockView2);

        final LayoutNode containerNode1 = createContainerNode(VIEW_ID, VIEW_NAME);
        final LayoutNode containerNode2 = createContainerNode(OTHER_VIEW_ID, OTHER_VIEW_NAME);
        final LayoutNode stackNode = createContainerStackNode(containerNode1, containerNode2);

        final ViewGroup result = (ViewGroup) createLayoutFactory().create(stackNode);

        assertThat(result).isInstanceOf(ContainerStack.class);
        List<View> containers = TestUtils.assertViewChildrenCount(result, 2);
        ViewGroup container1 = (ViewGroup) containers.get(0);
        ViewGroup container2 = (ViewGroup) containers.get(1);
        TestUtils.assertViewChildren(container1, mockView1);
        TestUtils.assertViewChildren(container2, mockView2);
    }

    @Test
    public void returnsSingleTabContent() throws Exception {
        BottomTabs bottomTabsMock = mock(BottomTabs.class);
        when(bottomTabsMock.getTabsCount()).thenReturn(0);

        when(rootViewCreator.createRootView(eq(VIEW_ID), eq(VIEW_NAME))).thenReturn(mockView);
        final LayoutNode containerNode = createContainerNode();
        final LayoutNode tabNode = createTabNode(containerNode);

        final View result = createLayoutFactory(bottomTabsMock).create(tabNode);

        verify(bottomTabsMock).add("#0");

        assertThat(result).isInstanceOf(BottomTabsContainer.class);
        Container container = (Container) TestUtils.assertViewChildrenCount((BottomTabsContainer) result, 1).get(0);
        View view = TestUtils.assertViewChildrenCount(container, 1).get(0);
        assertThat(view).isEqualTo(mockView);
    }

    @Test
    public void returnsTwoTabContent() throws Exception {
        BottomTabs bottomTabsMock = mock(BottomTabs.class);
        when(bottomTabsMock.getTabsCount()).thenReturn(0, 1);

        when(rootViewCreator.createRootView(eq(VIEW_ID), eq(VIEW_NAME))).thenReturn(mockView);
        final LayoutNode firstTabRootNode = createContainerNode(VIEW_ID, VIEW_NAME);

        when(rootViewCreator.createRootView(eq(OTHER_VIEW_ID), eq(OTHER_VIEW_NAME))).thenReturn(otherMockView);
        final LayoutNode secondTabRootNode = createContainerStackNode(createContainerNode(OTHER_VIEW_ID, OTHER_VIEW_NAME));

        final LayoutNode tabNode = createTabNode(firstTabRootNode, secondTabRootNode);

        final View result = createLayoutFactory(bottomTabsMock).create(tabNode);

        assertThat(result).isInstanceOf(BottomTabsContainer.class);
        verify(bottomTabsMock).add(eq("#0"));
        verify(bottomTabsMock).add(eq("#1"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwsExceptionForUnknownType() throws Exception {
        when(rootViewCreator.createRootView(eq(VIEW_ID), eq(VIEW_NAME))).thenReturn(mockView);
        final LayoutNode node = new LayoutNode(VIEW_ID, "***unknownType***", Collections.<String, Object>emptyMap());

        createLayoutFactory().create(node);
    }

    private LayoutFactory createLayoutFactory() {
        return createLayoutFactory(null);
    }

    private LayoutFactory createLayoutFactory(BottomTabs bottomTabs) {
        BottomTabsCreator bottomTabsCreator = null;
        if (bottomTabs != null) {
            bottomTabsCreator = mock(BottomTabsCreator.class);
            when(bottomTabsCreator.create()).thenReturn(bottomTabs);
        }

        return new LayoutFactory(activity, rootViewCreator, bottomTabsCreator);
    }

    private LayoutNode createContainerNode() {
        return createContainerNode(VIEW_ID, VIEW_NAME);
    }

    private LayoutNode createContainerNode(final String id, final String name) {
        return new LayoutNode(id, "Container", new HashMap<String, Object>() {{ put("name", name); }});
    }

    private LayoutNode createContainerStackNode(LayoutNode... children) {
        LayoutNode node = new LayoutNode();
        node.type = "ContainerStack";
        node.children = Arrays.asList(children);
        return node;
    }

    private LayoutNode createTabNode(LayoutNode... children) {
        LayoutNode node = new LayoutNode();
        node.type = "BottomTabs";
        node.children = Arrays.asList(children);
        return node;
    }
}
