package edu.xmu.test.algorithm.populate;

import java.security.MessageDigest;
import java.security.Provider;
import java.security.Security;

import org.apache.commons.lang.ArrayUtils;
import org.junit.Test;

public class PwdTest {
    @Test
    public void test() {
        String oldPwd = "qwe123";
        String oldSalt = "e18a9e83-5025-4a2d-bd8a-60f0ee3a810a";
        String oldEnPwd = Algorithm.md5Encrypt(oldPwd + oldSalt);
        System.out.println(oldEnPwd);

        String newPwd = "qwe123";
        String newSalt = "5fb49486-09a7-4fe5-8fdb-346b11cdb732";
        String newEnPwd = Algorithm.md5Encrypt(newPwd + newSalt);
        System.out.println(newEnPwd);
    }

    @Test
    public void sha1Test() throws Exception {
        String str = "whoru";
        String s = Algorithm.sha1Encrypt(str);
        System.out.println(s);

        str = "whoruhahahhahahaha";
        s = Algorithm.sha1Encrypt(str);
        System.out.println(s);
    }
}
