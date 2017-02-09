package com.reactnativenavigation.layout;

import android.app.Activity;
import android.view.View;

import java.util.List;
import java.util.Map;

public class LayoutFactory {
    public interface RootViewCreator {
        View createRootView(String id, String name);
    }

    public static class LayoutNode {
        public String id;
        public String type;
        public Map<String, Object> data;
        public List<LayoutNode> children;

        public LayoutNode() {
        }

        public LayoutNode(String id, String type, Map<String, Object> data) {
            this.id = id;
            this.type = type;
            this.data = data;
        }
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
//        return rootViewCreator.createRootView(id, name);
    }

    private void addChildrenNodes(ContainerStack containerStack, List<LayoutNode> children) {
        for (LayoutNode child : children) {
            containerStack.addView(create(child));
        }
    }
}
