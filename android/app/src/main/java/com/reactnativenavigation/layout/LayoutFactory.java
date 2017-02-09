package com.reactnativenavigation.layout;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.View;

import java.util.List;

public class LayoutFactory {
    public interface RootViewCreator {
        View createRootView(String id, String name);
    }

    private Activity activity;
    private RootViewCreator rootViewCreator;

    public LayoutFactory(Activity activity, RootViewCreator rootViewCreator) {
        this.activity = activity;
        this.rootViewCreator = rootViewCreator;
    }

    public View create(LayoutNode node) {
        switch (node.type) {
            case "Container":
                return createContainerView(node);
            case "ContainerStack":
                return createContainerStackView(node);
            default:
                throw new IllegalArgumentException("Invalid node type: "+node.type);
        }
    }

    @NonNull
    private View createContainerStackView(LayoutNode node) {
        ContainerStack containerStack = new ContainerStack(activity);
        List<LayoutNode> children = node.children;
        addChildrenNodes(containerStack, children);
        return containerStack;
    }

    @NonNull
    private View createContainerView(LayoutNode node) {
        String name = (String) node.data.get("name");
        return new Container(activity, rootViewCreator, node.id, name);
    }

    private void addChildrenNodes(ContainerStack containerStack, List<LayoutNode> children) {
        for (LayoutNode child : children) {
            containerStack.addView(create(child));
        }
    }
}
