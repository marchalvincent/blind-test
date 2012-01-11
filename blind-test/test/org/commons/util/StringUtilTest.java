package org.commons.util;

import static org.junit.Assert.*;

import org.junit.Test;

public class StringUtilTest {

	@Test
	public final void testIsEmpty() {
		assertEquals(true, StringUtil.isEmpty(null));
		assertEquals(true, StringUtil.isEmpty(""));
		assertEquals(false, StringUtil.isEmpty("re"));
		assertEquals(false, StringUtil.isEmpty("TEST"));
	}

	@Test
	public final void testIsNotEmpty() {
		assertEquals(false, StringUtil.isNotEmpty(null));
		assertEquals(false, StringUtil.isNotEmpty(""));
		assertEquals(true, StringUtil.isNotEmpty("re"));
		assertEquals(true, StringUtil.isNotEmpty("TEST"));
	}

	@Test
	public final void testEquals() {
		assertEquals(true, StringUtil.equals("", ""));
		assertEquals(true, StringUtil.equals(null, null));
		assertEquals(false, StringUtil.equals("", null));
		assertEquals(true, StringUtil.equals("test", "test"));
		assertEquals(false, StringUtil.equals("Test", "test"));
		assertEquals(false, StringUtil.equals("test", "TEST"));
	}

	@Test
	public final void testEqualsIgnoreCase() {
		assertEquals(true, StringUtil.equalsIgnoreCase("", ""));
		assertEquals(true, StringUtil.equalsIgnoreCase(null, null));
		assertEquals(false, StringUtil.equalsIgnoreCase("", null));
		assertEquals(true, StringUtil.equalsIgnoreCase("test", "test"));
		assertEquals(true, StringUtil.equalsIgnoreCase("Test", "test"));
		assertEquals(true, StringUtil.equalsIgnoreCase("test", "TEST"));
	}

	@Test
	public final void testCapitalize() {
		assertEquals("Test", StringUtil.capitalize("test"));
		assertEquals("TEST", StringUtil.capitalize("TEST"));
		assertEquals("TeST", StringUtil.capitalize("teST"));
		assertEquals("R", StringUtil.capitalize("r"));
		assertEquals("", StringUtil.capitalize(""));
		assertEquals(null, StringUtil.capitalize(null));
	}

	@Test
	public final void testCompareTo() {
		assertEquals(0, StringUtil.compareTo("", ""));
		assertEquals(1, StringUtil.compareTo("test", null));
		assertEquals(-1, StringUtil.compareTo(null, "test"));
		assertEquals(0, StringUtil.compareTo(null, null));
		assertEquals(32, StringUtil.compareTo("a", "A"));
		assertEquals(-32, StringUtil.compareTo("A", "a"));
		assertEquals(-1, StringUtil.compareTo("A", "B"));
		assertEquals(0, StringUtil.compareTo("test", "test"));
		assertEquals(0, StringUtil.compareTo("TEST", "TEST"));
		assertEquals(-32, StringUtil.compareTo("TEST", "Test"));
	}

	@Test
	public final void testCompareToIgnoreCase() {
		assertEquals(0, StringUtil.compareToIgnoreCase("", ""));
		assertEquals(1, StringUtil.compareToIgnoreCase("test", null));
		assertEquals(-1, StringUtil.compareToIgnoreCase(null, "test"));
		assertEquals(0, StringUtil.compareToIgnoreCase(null, null));
		assertEquals(0, StringUtil.compareToIgnoreCase("a", "A"));
		assertEquals(0, StringUtil.compareToIgnoreCase("A", "a"));
		assertEquals(-1, StringUtil.compareToIgnoreCase("A", "B"));
		assertEquals(0, StringUtil.compareToIgnoreCase("test", "test"));
		assertEquals(0, StringUtil.compareToIgnoreCase("TEST", "TEST"));
		assertEquals(0, StringUtil.compareToIgnoreCase("TEST", "Test"));
	}

	@Test
	public final void testIsNumeric() {
		assertEquals(true, StringUtil.isNumeric("123"));
		assertEquals(false, StringUtil.isNumeric(null));
		assertEquals(false, StringUtil.isNumeric("123.34"));
		assertEquals(true, StringUtil.isNumeric("43543543534"));
		assertEquals(false, StringUtil.isNumeric("test"));
	}

	@Test
	public final void testToInteger() {
		assertEquals(Integer.valueOf(123), StringUtil.toInteger("123"));
		assertEquals(null, StringUtil.toInteger(null));
		assertEquals(null, StringUtil.toInteger("123.34"));
		assertEquals(Integer.valueOf(43543543), StringUtil.toInteger("43543543"));
		assertEquals(null, StringUtil.toInteger("test"));
	}

}
