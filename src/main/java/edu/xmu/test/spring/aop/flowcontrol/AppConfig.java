package edu.xmu.test.spring.aop.flowcontrol;

import java.util.Map;
import java.util.Properties;
import java.util.Set;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

public final class AppConfig implements ConfigUpdateListener {
	Map<String, Long> methodFlowConfig = Maps.newConcurrentMap();
	Set<String> blacklist = Sets.newCopyOnWriteArraySet();

	public void init() {
		methodFlowConfig.put("aService", 1000L);
	}

	@Override
	public void updateConfig(Properties configs) {
		// TODO: update methodFlowConfig&blacklist
	}

}
