package edu.xmu.test.javase;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public final class MapUtils {

	private MapUtils() {
	}

	/**
	 * 根据传入的key0与map现有的key1进行比较,如果相与结果不为0,则证明命中.
	 * 
	 * @param map
	 * @param key
	 * @return
	 */
	public static Map<Long, List<String>> getByKey(Map<Long, List<String>> map, long key) {
		Map<Long, List<String>> res = Maps.newHashMap();
		for (Entry<Long, List<String>> entry : map.entrySet()) {
			if ((key & entry.getKey().longValue()) != 0) {
				if (res.containsKey(entry.getKey().longValue())) {
					res.get(entry.getKey().longValue()).addAll(entry.getValue());
				} else {
					List<String> list = Lists.newArrayList();
					list.addAll(entry.getValue());
					res.put(entry.getKey().longValue(), list);
				}
			}
		}

		return res;
	}
}
