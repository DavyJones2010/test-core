package edu.xmu.test.javase.ali;

import org.junit.Test;

/**
 * Created by davywalker on 17/7/27.
 */
public class GoogleAuthenticatorTest {

    @Test
    public void validateTest() throws Exception {
        String totp = GoogleAuthenticatorSample.getTOTP(System.currentTimeMillis() / 30000);
        System.out.printf(totp);
    }

}
