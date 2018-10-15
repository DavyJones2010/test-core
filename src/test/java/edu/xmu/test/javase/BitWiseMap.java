package edu.xmu.test.javase;

import java.util.List;
import java.util.Map;

import com.google.common.collect.ForwardingMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class BitWiseMap<K extends Long, V> extends ForwardingMap<K, V> {

	Map<K, V> map = Maps.newConcurrentMap();

	@Override
	protected Map<K, V> delegate() {
		return map;
	}

	public List<V> getByKey(long key) {
		List<V> res = Lists.newArrayList();
		for (Entry<K, V> entry : map.entrySet()) {
			if ((key & entry.getKey().longValue()) != 0) {
				res.add(entry.getValue());
			}
		}
		return res;
	}
}
