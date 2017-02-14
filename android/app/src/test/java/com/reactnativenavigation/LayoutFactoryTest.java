package com.reactnativenavigation;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.reactnativenavigation.layout.Container;
import com.reactnativenavigation.layout.ContainerStackLayout;
import com.reactnativenavigation.layout.LayoutFactory;
import com.reactnativenavigation.layout.LayoutNode;
import com.reactnativenavigation.layout.SideMenuLayout;
import com.reactnativenavigation.layout.bottomtabs.BottomTabs;
import com.reactnativenavigation.layout.bottomtabs.BottomTabsCreator;
import com.reactnativenavigation.layout.bottomtabs.BottomTabsLayout;

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

    private final static String NODE_ID = "myUniqueId";
    private final static String REACT_ROOT_VIEW_KEY = "myName";

    private final static String OTHER_NODE_ID = "anotherUniqueId";
    private final static String OTHER_REACT_ROOT_VIEW_KEY = "anotherName";

    private Activity activity;
    private View mockView;
    private View otherMockView;
    private LayoutFactory.ReactRootViewCreator reactRootViewCreator;

    @Before
    public void setUp() {
        activity = Robolectric.buildActivity(AppCompatActivity.class).get();
        mockView = new View(activity);
        otherMockView = new View(activity);
        reactRootViewCreator = mock(LayoutFactory.ReactRootViewCreator.class);
    }

    @Test
    public void returnsContainerThatHoldsTheRootView() throws Exception {
        when(reactRootViewCreator.create(eq(NODE_ID), eq(REACT_ROOT_VIEW_KEY))).thenReturn(mockView);
        final LayoutNode node = createContainerNode();

        final ViewGroup result = (ViewGroup) createLayoutFactory().create(node);

        assertThat(result).isInstanceOf(Container.class);
        TestUtils.assertViewChildren(result, mockView);
    }

    @Test
    public void returnsContainerStack() throws Exception {
        when(reactRootViewCreator.create(eq(NODE_ID), eq(REACT_ROOT_VIEW_KEY))).thenReturn(mockView);
        final LayoutNode containerNode = createContainerNode();
        final LayoutNode stackNode = createContainerStackNode(containerNode);

        final ViewGroup result = (ViewGroup) createLayoutFactory().create(stackNode);

        assertThat(result).isInstanceOf(ContainerStackLayout.class);
        ViewGroup container = (ViewGroup) TestUtils.assertViewChildrenCount(result, 1).get(0);
        TestUtils.assertViewChildren(container, mockView);
    }

    @Test
    public void returnsContainerStackWithMultipleViews() throws Exception {
        final View mockView1 = mock(View.class);
        final View mockView2 = mock(View.class);
        when(reactRootViewCreator.create(eq(NODE_ID), eq(REACT_ROOT_VIEW_KEY))).thenReturn(mockView1);
        when(reactRootViewCreator.create(eq(OTHER_NODE_ID), eq(OTHER_REACT_ROOT_VIEW_KEY))).thenReturn(mockView2);

        final LayoutNode containerNode1 = createContainerNode(NODE_ID, REACT_ROOT_VIEW_KEY);
        final LayoutNode containerNode2 = createContainerNode(OTHER_NODE_ID, OTHER_REACT_ROOT_VIEW_KEY);
        final LayoutNode stackNode = createContainerStackNode(containerNode1, containerNode2);

        final ViewGroup result = (ViewGroup) createLayoutFactory().create(stackNode);

        assertThat(result).isInstanceOf(ContainerStackLayout.class);
        List<View> containers = TestUtils.assertViewChildrenCount(result, 2);
        ViewGroup container1 = (ViewGroup) containers.get(0);
        ViewGroup container2 = (ViewGroup) containers.get(1);
        TestUtils.assertViewChildren(container1, mockView1);
        TestUtils.assertViewChildren(container2, mockView2);
    }

    @Test
    public void returnsSideMenuRoot() throws Exception {
        when(reactRootViewCreator.create(eq(NODE_ID), eq(REACT_ROOT_VIEW_KEY))).thenReturn(mockView);
        final LayoutNode containerNode = createSideMenuContainerNode(Arrays.asList(createContainerNode()));
        final ViewGroup result = (ViewGroup) createLayoutFactory().create(containerNode);
        assertThat(result).isInstanceOf(SideMenuLayout.class);
    }

    @Test
    public void hasContentContainer() throws Exception {
        when(reactRootViewCreator.create(eq(NODE_ID), eq(REACT_ROOT_VIEW_KEY))).thenReturn(mockView);
        LayoutNode contentContainer = createContainerNode();
        final LayoutNode sideMenu = createSideMenuContainerNode(Arrays.asList(contentContainer));
        final ViewGroup result = (ViewGroup) createLayoutFactory().create(sideMenu);
        assertThat(result.getChildAt(0)).isInstanceOf(Container.class);
    }

    @Test
    public void hasLeftMenu() throws Exception {
        when(reactRootViewCreator.create(eq(NODE_ID), eq(REACT_ROOT_VIEW_KEY))).thenReturn(mockView);
        LayoutNode sideMenuLeft = createSideMenuLeftNode();
        final LayoutNode sideMenu = createSideMenuContainerNode(Arrays.asList(sideMenuLeft));
        final ViewGroup result = (ViewGroup) createLayoutFactory().create(sideMenu);
        assertThat(result.getChildAt(0)).isInstanceOf(Container.class);
    }

    @Test
    public void hasRightMenu() throws Exception {
        when(reactRootViewCreator.create(eq(NODE_ID), eq(REACT_ROOT_VIEW_KEY))).thenReturn(mockView);
        LayoutNode sideMenuRight = createSideMenuRightNode();
        final LayoutNode sideMenu = createSideMenuContainerNode(Arrays.asList(sideMenuRight));
        final ViewGroup result = (ViewGroup) createLayoutFactory().create(sideMenu);
        assertThat(result.getChildAt(0)).isInstanceOf(Container.class);
    }

    @Test
    public void returnsSingleTabContent() throws Exception {
        BottomTabs bottomTabsMock = mock(BottomTabs.class);
        when(bottomTabsMock.size()).thenReturn(0);

        when(reactRootViewCreator.create(eq(NODE_ID), eq(REACT_ROOT_VIEW_KEY))).thenReturn(mockView);
        final LayoutNode containerNode = createContainerNode();
        final LayoutNode tabNode = createBottomTabNode(containerNode);

        final View result = createLayoutFactory(bottomTabsMock).create(tabNode);

        verify(bottomTabsMock).add("#0");

        assertThat(result).isInstanceOf(BottomTabsLayout.class);
        Container container = (Container) TestUtils.assertViewChildrenCount((BottomTabsLayout) result, 1).get(0);
        View view = TestUtils.assertViewChildrenCount(container, 1).get(0);
        assertThat(view).isEqualTo(mockView);
    }

    @Test
    public void returnsTwoTabContent() throws Exception {
        BottomTabs bottomTabsMock = mock(BottomTabs.class);
        when(bottomTabsMock.size()).thenReturn(0, 1);

        when(reactRootViewCreator.create(eq(NODE_ID), eq(REACT_ROOT_VIEW_KEY))).thenReturn(mockView);
        final LayoutNode firstTabRootNode = createContainerNode(NODE_ID, REACT_ROOT_VIEW_KEY);

        when(reactRootViewCreator.create(eq(OTHER_NODE_ID), eq(OTHER_REACT_ROOT_VIEW_KEY))).thenReturn(otherMockView);
        final LayoutNode secondTabRootNode = createContainerStackNode(createContainerNode(OTHER_NODE_ID, OTHER_REACT_ROOT_VIEW_KEY));

        final LayoutNode tabNode = createBottomTabNode(firstTabRootNode, secondTabRootNode);

        final View result = createLayoutFactory(bottomTabsMock).create(tabNode);

        assertThat(result).isInstanceOf(BottomTabsLayout.class);
        verify(bottomTabsMock).add(eq("#0"));
        verify(bottomTabsMock).add(eq("#1"));
    }


    @Test(expected = IllegalArgumentException.class)
    public void throwsExceptionForUnknownType() throws Exception {
        when(reactRootViewCreator.create(eq(NODE_ID), eq(REACT_ROOT_VIEW_KEY))).thenReturn(mockView);
        final LayoutNode node = new LayoutNode(NODE_ID, "***unknownType***", Collections.<String, Object>emptyMap());

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

        return new LayoutFactory(activity, reactRootViewCreator, bottomTabsCreator);
    }

    private LayoutNode createContainerNode() {
        return createContainerNode(NODE_ID, REACT_ROOT_VIEW_KEY);
    }

    private LayoutNode createSideMenuLeftNode() {
        List<LayoutNode> children = Arrays.asList(createContainerNode());
        return new LayoutNode("SideMenuLeft", children);
    }

    private LayoutNode createSideMenuRightNode() {
        List<LayoutNode> children = Arrays.asList(createContainerNode());
        return new LayoutNode("SideMenuRight", children);
    }

    private LayoutNode createContainerNode(final String id, final String name) {
        return new LayoutNode(id, "Container", new HashMap<String, Object>() {{ put("name", name); }});
    }

    private LayoutNode createSideMenuContainerNode(List<LayoutNode> children) {
        return new LayoutNode("SideMenuRoot", children);
    }

    private LayoutNode createContainerStackNode(LayoutNode... children) {
        return new LayoutNode("ContainerStack", Arrays.asList(children));
    }

    private LayoutNode createBottomTabNode(LayoutNode... children) {
        return new LayoutNode("BottomTabs", Arrays.asList(children));
    }
}
