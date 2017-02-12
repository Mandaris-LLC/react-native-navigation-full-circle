package com.reactnativenavigation.layout;

import android.app.Activity;
import android.view.View;

import com.reactnativenavigation.layout.bottomtabs.BottomTabsContainer;
import com.reactnativenavigation.layout.bottomtabs.BottomTabsCreator;

import java.util.List;

public class LayoutFactory {
    public interface RootViewCreator {
        View createRootView(String id, String name);
    }

    private final Activity activity;
    private final RootViewCreator rootViewCreator;
    private final BottomTabsCreator bottomTabsCreator; // TODO: revisit this, may not be needed

    public LayoutFactory(Activity activity, RootViewCreator rootViewCreator, BottomTabsCreator bottomTabsCreator) {
        this.activity = activity;
        this.rootViewCreator = rootViewCreator;
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
            default:
                throw new IllegalArgumentException("Invalid node type: "+node.type);
        }
    }

    private View createContainerView(LayoutNode node) {
        final String name = (String) node.data.get("name");
        return new Container(activity, rootViewCreator, node.id, name);
    }

    private View createContainerStackView(LayoutNode node) {
        final ContainerStack containerStack = new ContainerStack(activity);
        addChildrenNodes(containerStack, node.children);
        return containerStack;
    }

    private View createBottomTabs(LayoutNode node) {
        final BottomTabsContainer tabsContainer = new BottomTabsContainer(activity, bottomTabsCreator);
        tabsContainer.setTabContent(create(node.children.get(0)));
        return tabsContainer;
    }

    private void addChildrenNodes(ContainerStack containerStack, List<LayoutNode> children) {
        for (LayoutNode child : children) {
            containerStack.addView(create(child));
        }
    }
}
