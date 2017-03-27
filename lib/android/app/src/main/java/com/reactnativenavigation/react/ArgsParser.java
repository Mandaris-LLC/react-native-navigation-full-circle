package com.reactnativenavigation.react;

import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArgsParser {
	public static Map<String, Object> parse(ReadableMap map) {
		Map<String, Object> result = new HashMap<>();
		ReadableMapKeySetIterator it = map.keySetIterator();
		while (it.hasNextKey()) {
			String key = it.nextKey();
			switch (map.getType(key)) {
				case String:
					result.put(key, map.getString(key));
					break;
				case Number:
					result.put(key, parseNumber(map, key));
					break;
				case Boolean:
					result.put(key, map.getBoolean(key));
					break;
				case Array:
					result.put(key, parseArray(map.getArray(key)));
					break;
				case Map:
					result.put(key, parse(map.getMap(key)));
					break;
				default:
					break;
			}
		}
		return result;
	}

	public static List<Object> parseArray(ReadableArray arr) {
		List<Object> result = new ArrayList<>();
		for (int i = 0; i < arr.size(); i++) {
			switch (arr.getType(i)) {
				case String:
					result.add(arr.getString(i));
					break;
				case Number:
					result.add(parseNumber(arr, i));
					break;
				case Boolean:
					result.add(arr.getBoolean(i));
					break;
				case Array:
					result.add(parseArray(arr.getArray(i)));
					break;
				case Map:
					result.add(parse(arr.getMap(i)));
					break;
				default:
					break;
			}
		}
		return result;
	}

	private static Object parseNumber(ReadableMap map, String key) {
		try {
			return map.getInt(key);
		} catch (Exception e) {
			return map.getDouble(key);
		}
	}

	private static Object parseNumber(ReadableArray arr, int index) {
		try {
			return arr.getInt(index);
		} catch (Exception e) {
			return arr.getDouble(index);
		}
	}
}
