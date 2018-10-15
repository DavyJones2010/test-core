package edu.xmu.test.work.account;

import org.junit.Assert;
import org.junit.Test;

import java.util.regex.Pattern;

/**
 * Created by davywalker on 17/1/4.
 * 密码校验规则
 */
public class PwdValidationTest {

    // 密码校验规则: 正则匹配, 非双字节字符
    @Test
    public void nonChineseTest() throws Exception {
        // 白名单方式
        Pattern LEGAL_PATTERN = Pattern.compile("^[-+=|,0-9a-zA-Z!@#$%^&*?_.~+/()\\[\\]{}<>]*$");
        Assert.assertFalse(LEGAL_PATTERN.matcher("abc我是def").matches());
        Assert.assertFalse(LEGAL_PATTERN.matcher("abcかわいいdef").matches());
        Assert.assertTrue(LEGAL_PATTERN.matcher("abc%?)([]def").matches());
        // 白名单方式
        LEGAL_PATTERN = Pattern.compile("^[\\x21-\\x7f]*$");
        Assert.assertFalse(LEGAL_PATTERN.matcher("abc我是def").matches());
        Assert.assertFalse(LEGAL_PATTERN.matcher("abcかわいいdef").matches());
        Assert.assertTrue(LEGAL_PATTERN.matcher("abc%?)([]def").matches());
    }
}
