package com.keshav.TOTP_Demo.Encryption;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.spec.KeySpec;
import java.util.Base64;

public class AESUtil {
    public static SecretKey getKeyFromPassword(String password, String salt) {
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 65536, 256);
            SecretKey secret = new SecretKeySpec(factory.generateSecret(spec)
                    .getEncoded(), "AES");
            return secret;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public static String encrypt(String algorithm, String secret, SecretKey key, IvParameterSpec iv) {
        try {
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.ENCRYPT_MODE, key, iv);
            byte[] cipherText = cipher.doFinal(secret.getBytes());
            return Base64.getEncoder()
                    .encodeToString(cipherText);
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public static String decrypt(String algorithm, String secret, SecretKey key, IvParameterSpec ivParameterSpec) {
    try {
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE,key,ivParameterSpec);
        byte[] plainText = cipher.doFinal(Base64.getDecoder().decode(secret));
        return new String(plainText);
    }catch (Exception e){
        System.out.println(e);
    }
    return null;
    }

}
