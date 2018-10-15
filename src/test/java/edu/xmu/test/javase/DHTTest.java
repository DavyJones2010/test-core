package edu.xmu.test.javase;

import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.commons.collections.MapUtils;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 一致性哈希算法(Distributed Hash Table)简单实现
 * 
 * @author davyjones
 *
 */
public class DHTTest {
	@Test
	public void test() {
		// 假设哈希空间为2^5 = 32;
		List<String> a = Lists.newArrayList();
		int indexA = 3;
		List<String> b = Lists.newArrayList();
		int indexB = 11;
		List<String> c = Lists.newArrayList();
		int indexC = 17;
		System.out.println("indexA=" + indexA + " indexB=" + indexB + " indexC=" + indexC);
		TreeMap<Integer, List<String>> ring = Maps.newTreeMap();
		ring.put(indexA, a);
		ring.put(indexB, b);
		ring.put(indexC, c);

		List<String> datas = Lists.newArrayList("ab", "ac", "ad", "ae", "af", "ag", "ah", "ai", "aj", "k", "m", "n",
				"o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z");
		for (String data : datas) {
			putData(ring, data);
		}
		MapUtils.debugPrint(System.out, "", ring);

		addNode(ring, 20);
		MapUtils.debugPrint(System.out, "", ring);

		removeNode(ring, 20);
		MapUtils.debugPrint(System.out, "", ring);
	}

	/**
	 * 模拟简单的增加服务器节点
	 * 
	 * @param ring
	 * @param newIndex
	 */
	void addNode(TreeMap<Integer, List<String>> ring, int newIndex) {
		if (ring.containsKey(newIndex)) {
			return;
		}
		List<String> newValues = Lists.newArrayList();

		if (ring.lastKey() < newIndex) {
			int oldIndex = ring.firstKey();
			List<String> oldValues = ring.firstEntry().getValue();
			transformValues(oldIndex, newIndex, oldValues, newValues);
			ring.put(newIndex, newValues);
			return;
		}
		for (Entry<Integer, List<String>> ringEntry : ring.entrySet()) {
			if (ringEntry.getKey() > newIndex) {
				int oldIndex = ringEntry.getKey();
				List<String> oldValues = ringEntry.getValue();
				transformValues(oldIndex, newIndex, oldValues, newValues);
			}
		}
		ring.put(newIndex, newValues);
	}

	/**
	 * 模拟删除服务器节点
	 * 
	 * @param ring
	 * @param newIndex
	 */
	void removeNode(TreeMap<Integer, List<String>> ring, int newIndex) {
		if (!ring.containsKey(newIndex)) {
			return;
		}
		int nodeIndex = ring.firstKey();
		// 普通节点删除
		for (Entry<Integer, List<String>> ringEntry : ring.entrySet()) {
			if (ringEntry.getKey() > newIndex) {
				nodeIndex = ringEntry.getKey();
			}
		}
		ring.get(nodeIndex).addAll(ring.get(newIndex));
		ring.remove(newIndex);
		return;
	}

	/**
	 * 增加节点
	 * 
	 * @param oldIndex
	 * @param newIndex
	 * @param oldValues
	 * @param newValues
	 */
	void transformValues(int oldIndex, int newIndex, List<String> oldValues, List<String> newValues) {
		if (oldIndex == newIndex) {
			return;
		}
		if (oldIndex > newIndex) {
			for (String value : oldValues) {
				int hashCode = value.hashCode() % 32;
				if (hashCode <= newIndex) {
					newValues.add(value);
				}
			}
		}
		if (oldIndex < newIndex) {
			for (String value : oldValues) {
				int hashCode = value.hashCode() % 32;
				if (hashCode >= newIndex) {
					newValues.add(value);
				}
			}
		}
		oldValues.removeAll(newValues);
	}

	/**
	 * 实现简单的一致性哈希算法
	 * 
	 * @param ring
	 * @param value
	 */
	void putData(TreeMap<Integer, List<String>> ring, String value) {
		int valueIndex = value.hashCode() % 32;
		if (ring.lastEntry().getKey() < valueIndex) {
			ring.firstEntry().getValue().add(value);
		}
		for (Entry<Integer, List<String>> ringEntry : ring.entrySet()) {
			if (ringEntry.getKey() >= valueIndex) {
				System.out.println(
						"data=" + value + " dataHashCode=" + valueIndex + " ringHashCode=" + ringEntry.getKey());
				ringEntry.getValue().add(value);
				return;
			}
		}
	}
}
