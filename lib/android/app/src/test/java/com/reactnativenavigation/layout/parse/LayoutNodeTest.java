package com.reactnativenavigation.layout.parse;

import com.reactnativenavigation.BaseTest;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.assertj.core.api.Java6Assertions.entry;

public class LayoutNodeTest extends BaseTest {
	@Test
	public void dto() throws Exception {
		LayoutNode node = new LayoutNode("the id", LayoutNode.Type.Container);
		assertThat(node.id).isEqualTo("the id");
		assertThat(node.type).isEqualTo(LayoutNode.Type.Container);
		assertThat(node.data).isEmpty();
		assertThat(node.children).isEmpty();
	}

	@Test
	public void parseType() throws Exception {
		assertThat(LayoutNode.Type.fromString("Container")).isEqualTo(LayoutNode.Type.Container);
	}

	@Test(expected = RuntimeException.class)
	public void invalidType() throws Exception {
		LayoutNode.Type.fromString("unknown");
	}

	@Test
	public void parseFromTree() throws Exception {
		Map<String, Object> tree = new HashMap<>();
		tree.put("id", "node1");
		tree.put("type", "ContainerStack");
		Map<String, Object> rawData = new HashMap<>();
		rawData.put("dataKey", "dataValue");
		tree.put("data", rawData);
		List<Object> rawChildren = new ArrayList<>();
		Map<String, Object> rawChild = new HashMap<>();
		rawChild.put("id", "childId1");
		rawChild.put("type", "Container");
		rawChildren.add(rawChild);
		tree.put("children", rawChildren);

		LayoutNode result = LayoutNode.fromTree(tree);

		assertThat(result).isNotNull();
		assertThat(result.id).isEqualTo("node1");
		assertThat(result.type).isEqualTo(LayoutNode.Type.ContainerStack);
		assertThat(result.data).containsOnly(entry("dataKey", "dataValue"));
		assertThat(result.children).hasSize(1);
		assertThat(result.children.get(0).id).isEqualTo("childId1");
		assertThat(result.children.get(0).type).isEqualTo(LayoutNode.Type.Container);
		assertThat(result.children.get(0).data).isEmpty();
		assertThat(result.children.get(0).children).isEmpty();
	}
}
