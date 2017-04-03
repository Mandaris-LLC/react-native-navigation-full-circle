package com.reactnativenavigation.layout;

import android.support.annotation.NonNull;

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
		SideMenuRight
	}

	@SuppressWarnings("unchecked")
	public static LayoutNode parse(JSONObject layoutTree) {
		String id = layoutTree.optString("id");
		LayoutNode.Type type = LayoutNode.Type.valueOf(layoutTree.optString("type"));
		JSONObject data = parseData(layoutTree);
		List<LayoutNode> children = parseChildren(layoutTree);
		return new LayoutNode(id, type, data, children);
	}

	@NonNull
	private static List<LayoutNode> parseChildren(JSONObject layoutTree) {
		List<LayoutNode> children = new ArrayList<>();
		if (layoutTree.has("children")) {
			JSONArray rawChildren = layoutTree.optJSONArray("children");
			for (int i = 0; i < rawChildren.length(); i++) {
				children.add(LayoutNode.parse(rawChildren.optJSONObject(i)));
			}
		}
		return children;
	}

	private static JSONObject parseData(JSONObject layoutTree) {
		return layoutTree.has("data") ? layoutTree.optJSONObject("data") : new JSONObject();
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
}
