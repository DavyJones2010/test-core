package edu.xmu.test.javase.math;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import org.junit.Assert;
import org.junit.Test;

public class BigDecimalTest {

	/**
	 * <p>
	 * The safest way to construct BigDecimal is new BigDecimal(String) or
	 * BigDecimal.valueOf(double). The new BigDecimal(double) is deprecated and
	 * will cause precision loss
	 * </p>
	 */
	@Test
	public void precisionTest() {
		// The data stored in val is "0.002233"
		BigDecimal val = new BigDecimal("0.002233");
		assertNotEquals(0, val.compareTo(new BigDecimal(0.002233)));
		assertEquals(0, val.compareTo(new BigDecimal(0.002233, MathContext.DECIMAL32)));
		assertEquals(0, val.compareTo(new BigDecimal(0.002233, MathContext.DECIMAL64)));
		assertEquals("0.002233", val.toPlainString());
		assertEquals("0.002233", val.toString());

		// The data stored in val is not "0.002233"
		val = new BigDecimal(0.002233);
		assertEquals(0, val.compareTo(new BigDecimal(0.002233)));
		assertEquals(0, val.compareTo(new BigDecimal(0.002233, MathContext.UNLIMITED)));
		assertNotEquals(0, val.compareTo(new BigDecimal(0.002233, MathContext.DECIMAL32)));
		assertNotEquals(0, val.compareTo(new BigDecimal(0.002233, MathContext.DECIMAL64)));
		assertNotEquals("0.002233", val.toPlainString());
		assertNotEquals("0.002233", val.toString());

		// The data stored in val is "0.002233"
		val = BigDecimal.valueOf(0.002233);
		assertNotEquals(0, val.compareTo(new BigDecimal(0.002233)));
		assertEquals(0, val.compareTo(new BigDecimal(0.002233, MathContext.DECIMAL32)));
		assertEquals(0, val.compareTo(new BigDecimal(0.002233, MathContext.DECIMAL64)));
		assertEquals("0.002233", val.toPlainString());
		assertEquals("0.002233", val.toString());
	}

	/**
	 * Precision: How many non-zero digit we can have after the dot<br/>
	 * Scale: How many digit we can have after the dot<br/>
	 */
	@Test
	public void precisionTest2() {
		BigDecimal val = new BigDecimal(0.002233);
		// Scale is the smallest number to keey (10^scale * num) is an int
		// But here the val is not 0.002233 actually
		assertNotEquals(6, val.scale());
		Assert.assertNotEquals("0.002233", val.toString());
		Assert.assertNotEquals("0.002233", val.toPlainString());
		Assert.assertEquals(0.002233, val.doubleValue(), 0);

		val = val.setScale(2, RoundingMode.HALF_UP);
		System.out.println("Scaled: " + val.toPlainString());

		MathContext currencyMathContext = new MathContext(2, RoundingMode.HALF_UP);
		val = new BigDecimal(0.002233, currencyMathContext);
		Assert.assertEquals("0.0022", val.toString());
		Assert.assertEquals("0.0022", val.toPlainString());
		Assert.assertEquals(0.0022, val.doubleValue(), 0);

		val = BigDecimal.valueOf(0.002233443322323242);
		assertEquals("0.002233443322323242", val.toString());

		// The default precision for BigDecimal is 16
		val = BigDecimal.valueOf(0.0022334433223232424);
		assertNotEquals("0.0022334433223232424", val.toString());

		val = BigDecimal.valueOf(0.0022334433223232425);
		assertNotEquals("0.0022334433223232425", val.toString());

		val = new BigDecimal(111.002233);
		Assert.assertNotEquals("111.002233", val.toString());
		Assert.assertNotEquals("111.002233", val.toPlainString());
		Assert.assertEquals(111.002233, val.doubleValue(), 0);

		val = BigDecimal.valueOf(111.002233);
		Assert.assertEquals("111.002233", val.toString());
		Assert.assertEquals("111.002233", val.toPlainString());
		Assert.assertEquals(111.002233, val.doubleValue(), 0);
	}

	@Test
	public void scaleTest() {
		BigDecimal val = new BigDecimal("0.002233");
		assertEquals(6, val.scale());
		val = val.setScale(4, RoundingMode.HALF_UP);
		assertEquals("0.0022", val.toPlainString());

		val = BigDecimal.valueOf(1.002233);
		assertEquals(6, val.scale());
		val = val.setScale(4, RoundingMode.HALF_UP);
		assertEquals("1.0022", val.toPlainString());
	}

	@Test
	public void equalsTest() {
		MathContext currencyMathContext = new MathContext(2, RoundingMode.HALF_UP);

		BigDecimal val = new BigDecimal("0.002233");
		assertEquals(0, val.compareTo(new BigDecimal("0.002233")));
		assertEquals(0, new BigDecimal("0.0022").compareTo(new BigDecimal("0.002233", currencyMathContext)));
		assertEquals(1, val.compareTo(new BigDecimal("0.002233", currencyMathContext)));

		val = new BigDecimal("0.002233").setScale(2, RoundingMode.HALF_UP);
		assertEquals("0.00", val.toPlainString());
		assertEquals(0, val.compareTo(BigDecimal.ZERO));
		// equals only return true when both face value and scale value equal
		assertFalse(val.equals(BigDecimal.ZERO));

		val = new BigDecimal("0.002233").setScale(1, RoundingMode.HALF_UP);
		assertEquals("0.0", val.toPlainString());
		assertEquals(0, val.compareTo(BigDecimal.ZERO));
	}
}
