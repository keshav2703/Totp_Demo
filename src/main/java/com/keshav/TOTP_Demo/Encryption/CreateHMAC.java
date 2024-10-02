package com.keshav.TOTP_Demo.Encryption;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class CreateHMAC {

    private static final String HMAC_ALGO = "HmacSHA1";

    public static byte[] hmacHash(String key, long timeCounter) {
        try {
            SecretKeySpec mackey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), HMAC_ALGO);
            Mac mac = Mac.getInstance(HMAC_ALGO);
            mac.init(mackey);

            byte[] timeBytes = ByteBuffer.allocate(8).putLong(timeCounter).array();
            return mac.doFinal(timeBytes);
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public static int truncateHash(byte[] hash) {
        int offset = hash[hash.length - 1] & 0xF;
        return ((hash[offset] & 0x7F) << 24)
                | ((hash[offset + 1] & 0xFF) << 16)
                | ((hash[offset + 2] & 0xFF) << 8)
                | (hash[offset + 3] & 0xFF);
    }
}
