package com.reactnativenavigation.layout.parse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LayoutNode {
	public enum Type {
		Container,
		ContainerStack,
		BottomTabs,
		SideMenuRoot,
		SideMenuCenter,
		SideMenuLeft,
		SideMenuRight;

		public static Type fromString(String str) {
			return valueOf(str);
		}
	}

	public final String id;
	public final Type type;
	public final Map<String, Object> data;

	public final List<LayoutNode> children;

	public LayoutNode(String id, Type type) {
		this(id, type, new HashMap<String, Object>(), new ArrayList<LayoutNode>());
	}

	public LayoutNode(String id, Type type, Map<String, Object> data, List<LayoutNode> children) {
		this.id = id;
		this.type = type;
		this.data = data;
		this.children = children;
	}

	@SuppressWarnings("unchecked")
	public static LayoutNode fromTree(Map<String, Object> layoutTree) {
		String id = (String) layoutTree.get("id");
		LayoutNode.Type type = LayoutNode.Type.fromString((String) layoutTree.get("type"));

		Map<String, Object> data;
		if (layoutTree.containsKey("data")) {
			data = (Map<String, Object>) layoutTree.get("data");
		} else {
			data = new HashMap<>();
		}

		List<LayoutNode> children = new ArrayList<>();
		if (layoutTree.containsKey("children")) {
			List<Object> rawChildren = (List<Object>) layoutTree.get("children");
			for (Object rawChild : rawChildren) {
				children.add(LayoutNode.fromTree((Map<String, Object>) rawChild));
			}
		}

		return new LayoutNode(id, type, data, children);
	}
}
