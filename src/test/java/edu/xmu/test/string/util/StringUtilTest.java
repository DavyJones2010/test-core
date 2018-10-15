package edu.xmu.test.string.util;

import static org.junit.Assert.assertEquals;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

public class StringUtilTest {
    @Test
    public void splitTest() {
        String[] strs = StringUtils.splitPreserveAllTokens("a||", "|");
        assertEquals(3, strs.length);
        strs = StringUtils.split("a||", "|");
        assertEquals(1, strs.length);
    }
}
