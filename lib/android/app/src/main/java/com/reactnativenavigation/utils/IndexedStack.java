package com.reactnativenavigation.utils;

import java.util.ArrayDeque;
import java.util.HashMap;

public class IndexedStack<K, E> {

	private final ArrayDeque<K> deque = new ArrayDeque<>();
	private final HashMap<K, E> map = new HashMap<>();

	public void push(K id, E item) {
		deque.push(id);
		map.put(id, item);
	}

	public E peek() {
		return map.get(deque.peek());
	}

	public E pop() {
		if (deque.isEmpty()) {
			return null;
		}
		K popped = deque.pop();
		E removed = map.remove(popped);
		return removed;
	}

	public boolean isEmpty() {
		return deque.isEmpty();
	}

	public int size() {
		return deque.size();
	}
}
