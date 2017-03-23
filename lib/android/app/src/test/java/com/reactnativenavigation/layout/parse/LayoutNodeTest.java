package com.reactnativenavigation.layout.parse;

import com.reactnativenavigation.BaseTest;

import org.junit.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;

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
}
