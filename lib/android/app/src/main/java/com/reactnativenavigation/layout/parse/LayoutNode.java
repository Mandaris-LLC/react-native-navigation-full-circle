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
}
