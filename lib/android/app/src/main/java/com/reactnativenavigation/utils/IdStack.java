package com.reactnativenavigation.utils;

import java.util.ArrayDeque;
import java.util.HashMap;

public class IdStack<E> {

	private final ArrayDeque<String> deque = new ArrayDeque<>();
	private final HashMap<String, E> map = new HashMap<>();

	public void push(String id, E item) {
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
		String popped = deque.pop();
		E removed = map.remove(popped);
		return removed;
	}

	public boolean isEmpty() {
		return deque.isEmpty();
	}

	public int size() {
		return deque.size();
	}

	public String peekId() {
		return deque.peek();
	}

	public void clear() {
		deque.clear();
		map.clear();
	}

	public E get(final String id) {
		return map.get(id);
	}

	public boolean contains(final String id) {
		return deque.contains(id);
	}
}
