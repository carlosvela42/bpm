package co.jp.nej.earth.util;

import co.jp.nej.earth.BaseTest;
import org.junit.Assert;
import org.junit.Test;

public class CryptUtilTest extends BaseTest {
    @Test
    public void testEncryptAndDecryptTwoWay() throws Exception {
        String plainText = "This is message test";
        String ecryptedText = CryptUtil.encryptData(plainText);
        System.out.println(Math.ceil(9.1));
        System.out.println(Math.round(9.4));
        System.out.println(Math.round(9.6));
        Long a =10L/3;
        System.out.println(a);
        System.out.println(Math.ceil(10%3));
        Assert.assertEquals(plainText, CryptUtil.decryptData(ecryptedText));
    }
}
