package edu.xmu.test.javase.junit;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.MethodRule;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

/**
 * Testcase inspired by <a href="http://cwd.dhemery.com/2010/12/junit-rules/">How To Use JUnit Rule</a> and <a
 * href="http://cwd.dhemery.com/2011/01/what-junit-rules-are-good-for/">What JUnit Rules Are Good For</a><br/>
 */
public class JunitRuleTest {
	@Retention(RetentionPolicy.RUNTIME)
	static @interface NoScreenshot {
	}

	@Retention(RetentionPolicy.RUNTIME)
	static @interface CreateTmpFolder {
	}

	@Rule
	public MethodRule screenshot = new ScreenshotOnFailureRule();

	@Rule
	public TestRule temporaryFolder = new TestRule() {
		@Override
		public Statement apply(Statement base, Description description) {
			if (null != description.getAnnotation(CreateTmpFolder.class)) {
				return new Statement() {
					@Override
					public void evaluate() throws Throwable {
						// create tmp folder
						System.out.println("Creating tmp folder for testcase: " + description.getClassName() + "." + description.getMethodName());
						base.evaluate();
						// cleanup tmp folder
						System.out.println("Removing tmp folder for testcase: " + description.getClassName() + "." + description.getMethodName());
					}
				};
			} else {
				return base;
			}
		}
	};

	@Rule
	public TestRule createTestRule() {
		return new TestRule() {
			@Override
			public Statement apply(Statement base, Description description) {
				if (null != description.getAnnotation(CreateTmpFolder.class)) {
					return new Statement() {
						@Override
						public void evaluate() throws Throwable {
							// create tmp folder
							System.out.println("Creating tmp folder2 for testcase: " + description.getClassName() + "." + description.getMethodName());
							base.evaluate();
							// cleanup tmp folder
							System.out.println("Removing tmp folder2 for testcase: " + description.getClassName() + "." + description.getMethodName());
						}
					};
				} else {
					return base;
				}
			}
		};
	}

	@Test
	@NoScreenshot
	public void myTest() {
		throw new RuntimeException("WTF");
	}

	@Test
	public void myTest2() {
		throw new RuntimeException("WTF");
	}

	@Test
	@CreateTmpFolder
	public void myTest3() {
		throw new RuntimeException("WTF");
	}

	static class ScreenshotOnFailureStatement extends Statement {
		Statement base;
		String className;
		String methodName;

		public ScreenshotOnFailureStatement(Statement base, String className, String methodName) {
			super();
			this.base = base;
			this.className = className;
			this.methodName = methodName;
		}

		@Override
		public void evaluate() throws Throwable {
			try {
				base.evaluate();
			} catch (Throwable e) {
				System.out.println("Error executing " + className + "." + methodName);
			}
		}
	}

	static class ScreenshotOnFailureRule implements MethodRule {
		@Override
		public Statement apply(Statement base, FrameworkMethod method, Object target) {
			if (allowsScreenshot(method)) {
				String className = method.getMethod().getDeclaringClass().getSimpleName();
				String methodName = method.getName();
				return new ScreenshotOnFailureStatement(base, className, methodName);
			}
			return base;
		}

		private boolean allowsScreenshot(FrameworkMethod method) {
			return method.getMethod().getAnnotation(NoScreenshot.class) == null;
		}
	}
}
