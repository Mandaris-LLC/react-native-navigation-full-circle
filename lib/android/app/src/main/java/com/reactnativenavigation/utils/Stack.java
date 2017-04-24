package com.reactnativenavigation.utils;

public interface Stack<E> extends Iterable<E> {

	void push(E element);

	E pop();

	E peek();

	boolean remove(E element);

	int size();

	boolean isEmpty();

}
