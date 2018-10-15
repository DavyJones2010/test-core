package edu.xmu.test.javase.jvm;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * Inspired by local cache memory leak: 频繁引发fullgc
 * 
 * -XX:MaxTenuringThreshold=3
 * 
 * @author davyjones
 *
 */
public class MemoryLeakSample {
	static Map<String, Character[]> cacheReport = Maps.newHashMap();
	static List<Character[]> object = Lists.newArrayList();

	public static void main(String[] args) throws InterruptedException {
		// 新启动一个线程, 循环创建无用对象, 诱导引发younggc
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					object.add(new Character[1024 * 1024 * 10]);
					object.clear();
					try {
						TimeUnit.SECONDS.sleep(1L);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();

		// 主线程放到map里的元素躲过多次younggc都被promote到oldgen
		while (true) {
			for (int i = 0; i < 100_000; i++) {
				cacheReport.put(i + "", new Character[1024 * 1]);
			}
			cacheReport.clear();
		}
		// 解决方案:
		// 1. 采用对象池, 避免每次都new Character[]
		// 2. 增大-XX:MaxTenuringThreshold参数
	}

}
