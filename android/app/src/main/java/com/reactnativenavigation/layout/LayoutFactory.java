package com.reactnativenavigation.layout;

import android.app.Activity;
import android.view.View;

import java.util.List;
import java.util.Map;

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

    public View create(Map<String, Object> node) {
        String id = (String) node.get("id");
        String type = (String) node.get("type");

        switch (type) {
            case "Container":
                String name = (String) ((Map<String, Object>)node.get("data")).get("name");
                return new Container(activity, rootViewCreator, id, name);

            case "ContainerStack":
                ContainerStack containerStack = new ContainerStack(activity);
                List<Map<String, Object>> children = (List<Map<String, Object>>) node.get("children");
                Map<String, Object> inner = children.get(0);
                containerStack.addView(create(inner));
                return containerStack;

        }

        return null;
//        return rootViewCreator.createRootView(id, name);
    }
}
