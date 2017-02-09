package com.reactnativenavigation.layout;

import java.util.List;
import java.util.Map;

public class LayoutNode {
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
