package edu.xmu.test.effectivejava.sample.method;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.ForwardingSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

public class Chapter3ImmutableObjectTest {

	@Test
	public void inherentenceTest() {
		LoggedHashSet<String> set = new LoggedHashSet<>();
		set.add("A");
		set.addAll(Lists.newArrayList("B", "C"));

		Assert.assertNotEquals(3, set.getAddCount());
		Assert.assertEquals(5, set.getAddCount());
	}

	@Test
	public void compositionTest() {
		LoggedHashSetCompositionImpl<String> set = new LoggedHashSetCompositionImpl<>(Sets.newHashSet());
		set.add("A");
		set.addAll(Lists.newArrayList("B", "C"));

		Assert.assertEquals(3, set.getAddCount());
	}

	private static class LoggedHashSetCompositionImpl<E> extends ForwardingSet<E> {
		final Set<E> set;
		int addCount = 0;

		public LoggedHashSetCompositionImpl(Set<E> set) {
			super();
			this.set = set;
		}

		@Override
		public boolean add(E element) {
			addCount++;
			return super.add(element);
		}

		@Override
		public boolean addAll(Collection<? extends E> collection) {
			addCount += collection.size();
			return super.addAll(collection);
		}

		public int getAddCount() {
			return addCount;
		}

		@Override
		protected Set<E> delegate() {
			return set;
		}
	}

	private static class LoggedHashSet<E> extends HashSet<E> {
		private int addCount = 0;

		public LoggedHashSet() {
			super();
		}

		@Override
		public boolean add(E e) {
			addCount++;
			return super.add(e);
		}

		@Override
		public boolean addAll(Collection<? extends E> c) {
			addCount += c.size();
			return super.addAll(c);
		}

		public int getAddCount() {
			return addCount;
		}

	}
}
