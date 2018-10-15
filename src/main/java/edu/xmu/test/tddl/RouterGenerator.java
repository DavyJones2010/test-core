package edu.xmu.test.tddl;

import java.util.List;

public interface RouterGenerator {
	public String generateTableIndex(List<String> keys);

	public String generateDBIndex(List<String> keys);
}
