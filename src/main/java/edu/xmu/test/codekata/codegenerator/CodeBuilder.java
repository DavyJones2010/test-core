package edu.xmu.test.codekata.codegenerator;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

public class CodeBuilder {
	private StringBuilder codeStr = new StringBuilder();

	public CodeBuilder startClass(String className, Class<?> clazz) {
		if (!clazz.isPrimitive()) {
			codeStr.append("import ").append(clazz.getName()).append(";");
		}
		codeStr.append("public class ").append(className).append(" {");
		return this;
	}

	public CodeBuilder startMethod(String methodName, Class<?> paramClazz, int paramCount) {
		String paramClassName = paramClazz.getSimpleName();
		List<String> paramsWithType = Lists.newArrayList();
		List<String> paramsWithoutType = Lists.newArrayList();
		for (int i = 1; i <= paramCount; i++) {
			paramsWithType.add(paramClassName + " a" + i);
			paramsWithoutType.add(" a" + i);
		}
		String methodDeclare = String.format("public %s %s(%s) {", paramClassName, methodName, Joiner.on(',').join(paramsWithType));
		codeStr.append(methodDeclare);

		// below code generates method body with if-else statement
		String currentPivot = paramsWithoutType.get(0);
		List<String> excludeCurrentPivot = Lists.newArrayList(paramsWithoutType);
		excludeCurrentPivot.remove(currentPivot);
		codeStr.append(CodeBuilder.buildMethodBodyWithIfAndElse(currentPivot, excludeCurrentPivot, !paramClazz.isPrimitive()));

		// below code generates method body with if statement
		// buildMethodBodyOnlyWithIf(paramsWithoutType);
		return this;
	}

	public static String buildMethodBodyWithIfAndElse(String currentPivot, List<String> params, boolean isObject) {
		String basicElem = "";
		String wrapElem = "";
		if (!isObject) {
			basicElem = "if(%s > %s) {return %s;}else{return %s;}";
			wrapElem = "if(%s > %s){%s}else{%s}";
		} else {
			basicElem = "if(%s.compareTo(%s) > 0) {return %s;}else{return %s;}";
			wrapElem = "if(%s.compareTo(%s) > 0){%s}else{%s}";
		}
		if (1 == params.size()) {
			return String.format(basicElem, currentPivot, params.get(0), currentPivot, params.get(0));
		} else {
			List<String> whenCurrentPivotWins = Lists.newArrayList(params);
			whenCurrentPivotWins.remove(params.get(0));
			return String.format(wrapElem, currentPivot, params.get(0), buildMethodBodyWithIfAndElse(currentPivot, whenCurrentPivotWins, isObject),
					buildMethodBodyWithIfAndElse(params.get(0), whenCurrentPivotWins, isObject));
		}
	}

	CodeBuilder buildMethodBodyOnlyWithIf(List<String> params) {
		String basicElem = "if(%s > %s) {return %s;";
		String wrapElem = "if(%s > %s){";
		for (String currParam : params) {
			List<String> excludeCurrParam = Lists.newArrayList(params);
			excludeCurrParam.remove(currParam);
			List<String> excludeCurrAndNextParam = Lists.newArrayList(excludeCurrParam);
			for (String nextParam : excludeCurrParam) {
				excludeCurrAndNextParam.remove(nextParam);
				if (excludeCurrAndNextParam.isEmpty()) {
					codeStr.append(String.format(basicElem, currParam, nextParam, currParam)).append(StringUtils.repeat("}", excludeCurrParam.size()));
				} else {
					codeStr.append(String.format(wrapElem, currParam, nextParam));
				}
			}
		}

		return this;
	}

	public CodeBuilder endMethod() {
		codeStr.append("}");
		return this;
	}

	public CodeBuilder endClass() {
		codeStr.append("}");
		return this;
	}

	public String getCodeStr() {
		return codeStr.toString();
	}

}
