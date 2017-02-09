package com.reactnativenavigation.layout;

import android.app.Activity;
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
                String name = (String) node.data.get("name");
                return new Container(activity, rootViewCreator, node.id, name);

            case "ContainerStack":
                ContainerStack containerStack = new ContainerStack(activity);
                List<LayoutNode> children = node.children;
                addChildrenNodes(containerStack, children);
                return containerStack;
        }

        return null;
    }

    private void addChildrenNodes(ContainerStack containerStack, List<LayoutNode> children) {
        for (LayoutNode child : children) {
            containerStack.addView(create(child));
        }
    }
}
