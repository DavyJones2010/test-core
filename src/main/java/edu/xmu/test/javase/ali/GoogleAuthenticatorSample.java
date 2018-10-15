package edu.xmu.test.javase.ali;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;

import com.google.zxing.WriterException;
import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Hex;

/**
 * <pre>
 * 实现参照
 * 1. <a href="https://tools.ietf.org/html/rfc6238">rfc-6238</a>
 * 2.
 * <a href="https://github.com/ghthou/Google-Authenticator/blob/master/src/main/java/z/study/googleAuthenticator/util/GoogleAuthenticatorUtils.java">github</a>
 * </pre>
 * Created by davywalker on 17/7/27.
 */
public class GoogleAuthenticatorSample {
    public static String SK = "iwdfxbvil2x4crfgzlyif6pu4bsqz4jz";
    /**
     * 时间前后偏移量
     * 用于防止客户端时间不精确导致生成的TOTP与服务器端的TOTP一直不一致
     * 如果为0,当前时间为 10:10:15
     * 则表明在 10:10:00-10:10:30 之间生成的TOTP 能校验通过
     * 如果为1,则表明在
     * 10:09:30-10:10:00
     * 10:10:00-10:10:30
     * 10:10:30-10:11:00 之间生成的TOTP 能校验通过
     * 以此类推
     */
    private static final int timeExcursion = 1;

    public static void main(String[] args) throws IOException, WriterException {
        String googleAuthQRCodeData = createGoogleAuthQRCodeData(SK, "davywalker2010", "alibaba");
        System.out.println(googleAuthQRCodeData);
        QRCodeUtil.createQRCode(googleAuthQRCodeData,
            "/Users/davywalker/Softwares/totp.png");
    }

    public static String createGoogleAuthQRCodeData(String secret, String account, String issuer)
        throws UnsupportedEncodingException {
        String qrCodeData = "otpauth://totp/" + issuer + ":" + account + "?secret=" + secret
            + "&issuer=" + issuer + "&algorithm=SHA1&digits=6&period=30";
        return qrCodeData;
    }

    /**
     * 校验方法
     *
     * @param secretKey 密钥
     * @param code      用户输入的TOTP
     */
    public static boolean verify(String code) {
        long time = System.currentTimeMillis() / 1000 / 30;
        for (int i = -timeExcursion; i <= timeExcursion; i++) {
            String totp = getTOTP(time + i);
            if (code.equals(totp)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 根据密钥获取验证码
     * 返回字符串是因为数值有可能以0开头
     *
     * @param secretKey 密钥
     * @param time      第几个30秒 System.currentTimeMillis() / 1000 / 30
     */
    public static String getTOTP(long time) {
        Base32 base32 = new Base32();
        byte[] bytes = base32.decode(SK.toUpperCase());
        String hexKey = String.valueOf(Hex.encodeHex(bytes));
        String hexTime = Long.toHexString(time);
        return TOTP.generateTOTP(hexKey, hexTime, "6");
    }

    /**
     * 随机生成一个密钥, 通常是跟用户绑定的
     */
    public static String createSecretKey() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[20];
        random.nextBytes(bytes);
        Base32 base32 = new Base32();
        String secretKey = base32.encodeToString(bytes);
        return secretKey.toLowerCase();
    }

}
