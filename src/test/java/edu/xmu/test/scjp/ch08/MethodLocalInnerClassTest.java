package edu.xmu.test.scjp.ch08;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Map;

import org.junit.Test;

import com.google.common.collect.Maps;

public class MethodLocalInnerClassTest {
	final String outerOuterValue = "WhoAreU?";
	String outerOuterValue2 = "IAmDavy";

	@SuppressWarnings("unused")
	@Test
	public void createMethodLocalInnerClassTest() {
		final String outerValue = "Yes";
		String outerValue2 = "Fantastic";
		/*
		 * "public/private/protected/static/transient class InnerClass" <br/>
		 * All will generate compilation error! MethodLocalInnerClass can not
		 * have any access identifiers except "abstract/final"
		 */
		class InnerClass {
			String value;

			public String getValue() {
				return value;
			}

			public String getOuterOuterValue() {
				// Access enclosing class private final field
				return outerOuterValue;
			}

			public String getOuterOuterValue2() {
				// Access enclosing class private non-final field
				return outerOuterValue2;
			}

			public String getOuterValue() {
				// outerValue2 = "Amazing"; // MethodLocalInnerClass cannot
				// access non-final variable of its wrapping method
				/*
				 * What if InnerClass can access non-final variable of its
				 * wrapping method? <br/> When the method ends, the stack frame
				 * is blown away and the variable is history. But even the
				 * method completes, the InnerClass instance created within it
				 * might still be alive on the heap. Because the method local
				 * variables aren't guaranteed to be alive as long as the
				 * method-local inner class object, the inner class instance
				 * can't use them. Unless the local variable are marked as
				 * final!
				 */
				return outerValue;
			}

			public MethodLocalInnerClassTest getOuterInstance() {
				// MethodLocalInnerClassTest.this
				// .createMethodLocalInnerClassTest(); // StackOverflowError!
				return MethodLocalInnerClassTest.this;
			}
		}
		InnerClass inner = new InnerClass();
		inner.value = "Hallo";
		assertEquals("Hallo", inner.getValue());
		assertEquals(this, inner.getOuterInstance());
		assertEquals("Yes", inner.getOuterValue());
		assertEquals("WhoAreU?", inner.getOuterOuterValue());
		assertEquals("IAmDavy", inner.getOuterOuterValue2());
	}

	private static String classValue;
	private static final String staticClassValue = "StaticClassValue";

	public static void staticMethodInnerClassTest() {
		class InnerClass {
			public String getStaticValue() {
				return staticClassValue;
			}

			public String getNonStaticValue() {
				return classValue;
			}
		} // Don't need comma here for MethodInnerClass declaration

		InnerClass innerClass = new InnerClass();
		assertNull(null, innerClass.getNonStaticValue());
		assertEquals("StaticClassValue", innerClass.getStaticValue());
	}

	@Test
	public void methodInnerClassTest() {
		staticMethodInnerClassTest();
	}

	Map<String, Object> instanceMap = Maps.newHashMap();

	@Test
	public void methodInnerClassPassedToOtherInstances() {
		enrichInstanceMap();
		/*
		 * The final variable "finalMethodVariable" is still alive here even if
		 * the method stack "enrichInstanceMethod" is discarded. <br/> And the
		 * non-final "nonFinalMethodVariable" is doomed to be discared now <br/>
		 * We have to notice that method local "final" variable may not be gc-ed
		 * even if the method stack is blown away <br/>
		 */
		assertEquals(2, instanceMap.size());
	}

	@SuppressWarnings("unused")
	private void enrichInstanceMap() {
		final String finalMethodVariable = "LongLiveTheKing";
		String nonFinalMethodVariable = "ShortLiveTheMan";
		class InnerClass {
			public String getMethodVariable() {
				return finalMethodVariable;
			}
		}
		instanceMap.put("instance1", new InnerClass());
		instanceMap.put("instance2", new InnerClass());
	}

}