package edu.xmu.test.guava.collect;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ListFactory {
	@SuppressWarnings("unchecked")
	public static <E> List<E> newArrayList(E... elements) {
		List<E> list = new ArrayList<E>(elements.length);
		Collections.addAll(list, elements);
		return list;
	}
}
