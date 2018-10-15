package edu.xmu.test.codekata.codegenerator;

public class CodeGeneratorImpl implements CodeGenerator {

	@Override
	public String generateCode(Class<?> clazz, int num) {
		CodeBuilder builder = new CodeBuilder();
		String codeStr = builder.startClass("SimpleCompare", clazz).startMethod("compare", clazz, num).endMethod().endClass().getCodeStr();
		return CodeFormatter.formatCode(codeStr);
	}

}
