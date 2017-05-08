package com.reactnativenavigation.utils;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class IdStack<E> implements Iterable<String> {

	private final ArrayDeque<String> deque = new ArrayDeque<>();
	private final HashMap<String, E> map = new HashMap<>();

	public void push(String id, E item) {
		deque.push(id);
		map.put(id, item);
	}

	public E peek() {
		if (isEmpty()) {
			return null;
		}
		return map.get(deque.peek());
	}

	public E pop() {
		if (isEmpty()) {
			return null;
		}
		return map.remove(deque.pop());
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

	public boolean containsId(final String id) {
		return deque.contains(id);
	}

	public E remove(final String id) {
		if (!containsId(id)) {
			return null;
		}
		deque.remove(id);
		return map.remove(id);
	}

	@Override
	public Iterator<String> iterator() {
		return deque.iterator();
	}

	public Deque<String> getIds() {
		return deque;
	}

	public Map<String, E> getMap() {
		return map;
	}
}
