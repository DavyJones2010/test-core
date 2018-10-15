package edu.xmu.test.codekata.codegenerator;

public class SimpleCodeGeneratorImpl implements CodeGenerator {

	@Override
	public String generateCode(Class<?> clazz, int num) {
		StringBuilder sb = new StringBuilder();
		if (clazz.isAssignableFrom(int.class)) {
			sb.append("public class SimpleCompare {");
			sb.append("	public int compare(int a1, int a2) {");
			sb.append("		if (a1>a2) {");
			sb.append("			return a1;");
			sb.append("		} else {");
			sb.append("			return a2;");
			sb.append("		}");
			sb.append("	}");
			sb.append("}");
		} else if (clazz.isAssignableFrom(long.class)) {
			sb.append("public class SimpleCompare {");
			sb.append("	public long compare(long a1, long a2, long a3, long a4, long a5) {");
			sb.append("		if (a1>a2) {");
			sb.append("			return a1;");
			sb.append("		} else {");
			sb.append("			return a2;");
			sb.append("		}");
			sb.append("	}");
			sb.append("}");
		}
		return sb.toString();
	}

}
