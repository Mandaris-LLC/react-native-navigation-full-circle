package com.reactnativenavigation.layout;

import android.app.Activity;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

import com.reactnativenavigation.layout.bottomtabs.BottomTabsCreator;
import com.reactnativenavigation.layout.bottomtabs.BottomTabsLayout;
import com.reactnativenavigation.utils.ViewIdGenerator;

import java.util.List;

public class LayoutFactory {

    public interface ReactRootViewCreator {
        View create(String id, String name);
    }

    private final Activity activity;
    private final ReactRootViewCreator reactRootViewCreator;
    private final BottomTabsCreator bottomTabsCreator; // TODO: revisit this, may not be needed

    public LayoutFactory(Activity activity, ReactRootViewCreator reactRootViewCreator, BottomTabsCreator bottomTabsCreator) {
        this.activity = activity;
        this.reactRootViewCreator = reactRootViewCreator;
        this.bottomTabsCreator = bottomTabsCreator;
    }

    public View create(LayoutNode node) {
        switch (node.type) {
            case "Container":
                return createContainerView(node);
            case "ContainerStack":
                return createContainerStackView(node);
            case "BottomTabs":
                return createBottomTabs(node);
            case "SideMenuRoot":
                return createSideMenuRoot(node);
            case "SideMenuCenter":
                return createSideMenuContent(node);
            case "SideMenuLeft":
                return createSideMenuLeft(node);
            case "SideMenuRight":
                return createSideMenuRight(node);
            default:
                throw new IllegalArgumentException("Invalid node type: "+node.type);
        }
    }

    private View createSideMenuRoot(LayoutNode node) {
        SideMenuLayout sideMenuLayout = new SideMenuLayout(activity);
        for (LayoutNode child : node.children) {
            sideMenuLayout.addView(create(child));
        }
        return sideMenuLayout;
    }

    private View createSideMenuContent(LayoutNode node) {
        return create(node.children.get(0));
    }

    private View createSideMenuLeft(LayoutNode node) {
        View view = create(node.children.get(0));
        view.setId(ViewIdGenerator.generate());
        DrawerLayout.LayoutParams lp = new DrawerLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.LEFT;
        view.setLayoutParams(lp);
        return view;
    }

    private View createSideMenuRight(LayoutNode node) {
        View view = create(node.children.get(0));
        view.setId(ViewIdGenerator.generate());
        DrawerLayout.LayoutParams lp = new DrawerLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.RIGHT;
        view.setLayoutParams(lp);
        return view;
    }

    private View createContainerView(LayoutNode node) {
        final String name = (String) node.data.get("name");
        Container container = new Container(activity, reactRootViewCreator, node.id, name);
        container.setId(ViewIdGenerator.generate());
        return container;

    }

    private View createContainerStackView(LayoutNode node) {
        final ContainerStackLayout containerStack = new ContainerStackLayout(activity);
        containerStack.setId(ViewIdGenerator.generate());
        addChildrenNodes(containerStack, node.children);
        return containerStack;
    }

    private View createBottomTabs(LayoutNode node) {
        final BottomTabsLayout tabsContainer = new BottomTabsLayout(activity, bottomTabsCreator.create());
        for (int i = 0; i < node.children.size(); i++) {
            final View tabContent = create(node.children.get(i));
            tabsContainer.addTabContent("#" + i, tabContent);
        }
        return tabsContainer;
    }

    private void addChildrenNodes(ContainerStackLayout containerStack, List<LayoutNode> children) {
        for (LayoutNode child : children) {
            containerStack.addView(create(child));
        }
    }
}
