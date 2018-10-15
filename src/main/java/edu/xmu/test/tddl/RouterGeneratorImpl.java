package edu.xmu.test.tddl;

import java.util.List;

import com.google.common.base.Joiner;

public class RouterGeneratorImpl implements RouterGenerator {

	@Override
	public String generateTableIndex(List<String> keys) {
		return String.format("%04d", Math.abs(Joiner.on("").join(keys).toLowerCase().hashCode()) % 1024);
	}

	@Override
	public String generateDBIndex(List<String> keys) {
		return String.format("%04d", Math.abs(Joiner.on("").join(keys).toLowerCase().hashCode()) % 1024 / 64);
	}

}
