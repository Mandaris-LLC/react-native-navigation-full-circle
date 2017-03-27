package com.reactnativenavigation.layout.parse;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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
	public final JSONObject data;
	public final List<LayoutNode> children;

	public LayoutNode(String id, Type type) {
		this(id, type, new JSONObject(), new ArrayList<LayoutNode>());
	}

	public LayoutNode(String id, Type type, JSONObject data, List<LayoutNode> children) {
		this.id = id;
		this.type = type;
		this.data = data;
		this.children = children;
	}

	@SuppressWarnings("unchecked")
	public static LayoutNode fromTree(JSONObject layoutTree) {
		String id = layoutTree.optString("id");
		LayoutNode.Type type = LayoutNode.Type.fromString(layoutTree.optString("type"));

		JSONObject data;
		if (layoutTree.has("data")) {
			data = layoutTree.optJSONObject("data");
		} else {
			data = new JSONObject();
		}

		List<LayoutNode> children = new ArrayList<>();
		if (layoutTree.has("children")) {
			JSONArray rawChildren = layoutTree.optJSONArray("children");
			for (int i = 0; i < rawChildren.length(); i++) {
				children.add(LayoutNode.fromTree(rawChildren.optJSONObject(i)));
			}
		}

		return new LayoutNode(id, type, data, children);
	}
}
