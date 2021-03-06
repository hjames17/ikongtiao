package com.wetrack.base.utils.encrypt;

/**
 * Created by zhangsong on 15/8/14.
 */
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SHA1 {
    private static Log log = LogFactory.getLog(SHA1.class);
    private static final String ALGORITHM = "SHA-1";

    public SHA1() {
    }

    public static String encryptHex(String strInput) {
        return encryptHex(strInput, "UTF-8");
    }

    public static String encryptHex(String strInput, String charset) {
        Object b = null;

        byte[] b1;
        try {
            b1 = strInput.getBytes(charset);
        } catch (UnsupportedEncodingException var4) {
            b1 = strInput.getBytes();
        }

        return RadixUtil.binToHex(encrypt(b1));
    }

    public static String encryptHex(byte[] byteInput) {
        return RadixUtil.binToHex(encrypt(byteInput));
    }

    public static byte[] encrypt(byte[] byteInput) {
        try {
            MessageDigest nsae = MessageDigest.getInstance("SHA-1");
            nsae.update(byteInput);
            return nsae.digest();
        } catch (NoSuchAlgorithmException var2) {
            log.error("No such Algorithm in digest");
            return new byte[0];
        }
    }
}

