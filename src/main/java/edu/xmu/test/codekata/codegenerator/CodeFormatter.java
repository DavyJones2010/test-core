package edu.xmu.test.codekata.codegenerator;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

public class CodeFormatter {
	private static final String INDENT = "\t";

	public static String formatCode(String source) {
		int indentCount = 0;
		String codeWithNewLine = StringUtils.replace(StringUtils.replace(StringUtils.replace(source, "{", "{\n"), "}", "}\n"), ";", ";\n");
		List<String> lines = Splitter.on('\n').splitToList(codeWithNewLine);
		List<String> indentedLines = Lists.newArrayList();
		for (String line : lines) {
			if (line.endsWith("{")) {
				indentedLines.add(StringUtils.repeat(INDENT, indentCount).concat(line));
				indentCount++;
			} else if (line.endsWith("}")) {
				indentCount--;
				indentedLines.add(StringUtils.repeat(INDENT, indentCount).concat(line));
			} else if (line.endsWith(";")) {
				indentedLines.add(StringUtils.repeat(INDENT, indentCount).concat(line));
			}
		}

		return Joiner.on('\n').join(indentedLines);
	}
}
