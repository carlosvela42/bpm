package co.jp.nej.earth.util;

import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.model.constant.Constant;
import co.jp.nej.earth.model.constant.Constant.EnCryption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class CryptUtil {
    private static final Logger LOG = LoggerFactory.getLogger(CryptUtil.class);

    public static String encryptData(String plainText) throws Exception {
        try {
            Cipher cipher = Cipher.getInstance(EnCryption.PKCS5PADDING);
            SecretKeySpec secretKey = new SecretKeySpec(EnCryption.KEY, EnCryption.AES);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] cipherText = cipher.doFinal(plainText.getBytes(Constant.UTF_8));
            return new String(Base64.getEncoder().encode(cipherText), Constant.UTF_8);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new EarthException(e);
        }
    }

    public static String decryptData(String encryptedText) throws EarthException {
        try {
            Cipher cipher = Cipher.getInstance(EnCryption.PKCS5PADDING);
            SecretKeySpec secretKey = new SecretKeySpec(EnCryption.KEY, EnCryption.AES);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] cipherText = Base64.getDecoder().decode(encryptedText.getBytes(Constant.UTF_8));
            return new String(cipher.doFinal(cipherText), Constant.UTF_8);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new EarthException(e);
        }
    }
}
