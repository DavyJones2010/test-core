package edu.xmu.test.javase;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;

import edu.xmu.test.excel.util.CellReferenceUtil;

public class ExcelRegionTest {
	@Test
	public void getRegionTest() {
		List<Pair<Integer, Integer>> col = getPositionListFromRegion("A1:B2");
		System.out.println(col);
	}

	/**
	 * @param region
	 *            A1:B2
	 * @return [(0,0), (0,1), (1,0), (1,1)]
	 */
	@SuppressWarnings("unchecked")
	public List<Pair<Integer, Integer>> getPositionListFromRegion(String region) {
		String[] splits = StringUtils.split(region, ':');
		Preconditions.checkArgument(2 == splits.length, "splits.length = " + splits.length);

		Set<Integer> rowNums = IntStream.rangeClosed(CellReferenceUtil.getStartRowIndex(region), CellReferenceUtil.getEndRowIndex(region)).boxed().collect(Collectors.toSet());
		Set<Integer> colNums = IntStream.rangeClosed(CellReferenceUtil.getStartColIndex(region), CellReferenceUtil.getEndColIndex(region)).boxed().collect(Collectors.toSet());

		return Sets.cartesianProduct(rowNums, colNums).stream().map((List<Integer> t) -> {
			return Pair.of(t.get(0), t.get(1));
		}).collect(Collectors.toList());
	}
}
