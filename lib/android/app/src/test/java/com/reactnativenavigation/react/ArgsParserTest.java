package com.reactnativenavigation.react;

import com.facebook.react.bridge.JavaOnlyArray;
import com.facebook.react.bridge.JavaOnlyMap;
import com.reactnativenavigation.BaseTest;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.assertj.core.api.Java6Assertions.entry;

public class ArgsParserTest extends BaseTest {
	@Test
	public void parsesToMap() throws Exception {
		JavaOnlyMap input = new JavaOnlyMap();
		input.putString("keyString", "stringValue");
		input.putInt("keyInt", 123);
		input.putDouble("keyDouble", 123.456);
		input.putBoolean("keyBoolean", true);
		input.putArray("keyArray", new JavaOnlyArray());
		input.putNull("bla");

		Map<String, Object> result = ArgsParser.parse(input);

		assertThat(result).containsOnly(
				entry("keyString", "stringValue"),
				entry("keyInt", 123),
				entry("keyDouble", 123.456),
				entry("keyBoolean", true),
				entry("keyArray", new ArrayList<>())
		);
	}

	@Test
	public void parsesArrays() throws Exception {
		JavaOnlyArray input = new JavaOnlyArray();
		input.pushString("Hello");
		input.pushInt(123);
		input.pushDouble(123.456);
		input.pushBoolean(true);
		input.pushArray(new JavaOnlyArray());
		input.pushMap(new JavaOnlyMap());
		input.pushNull();

		List<Object> result = ArgsParser.parseArray(input);
		assertThat(result).containsExactly("Hello", 123, 123.456, true, new ArrayList<>(), new HashMap<>());
	}
}
