package com.keshav.TOTP_Demo.Encryption;

import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.util.Base64;

@Component
public class Encrypt {
    public String generateSecret(String username, String pass, String deviceId){
        int n = 16;
        String con = username.concat(pass).concat(deviceId).concat("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        StringBuilder sb = new StringBuilder(n);
        for (int i=0;i<n;i++){
            int index = (int) (con.length()* Math.random());
            sb.append(con.charAt(index));
        }
        return sb.toString().toUpperCase();
    }

    public String getEncodedSecret(String secret, String password) {
        SecretKey key = AESUtil.getKeyFromPassword(password,"123454321");
        byte [] iv = new byte[]{-114, 18,57,-100,7,114,111,90,-114,18,57,-100,7,114,111,90};
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        String algorithm = "AES/CBC/PKCS5Padding";
        return AESUtil.encrypt(algorithm, secret, key, ivParameterSpec);
    }

    public String getDecodedSecret(String secret, String password) {
        byte[] decodedBytes = Base64.getDecoder().decode(password);
        String pass = new String(decodedBytes);
        SecretKey key = AESUtil.getKeyFromPassword(pass,"123454321");
        byte [] iv = new byte[]{-114, 18,57,-100,7,114,111,90,-114,18,57,-100,7,114,111,90};
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        String algorithm = "AES/CBC/PKCS5Padding";
        return AESUtil.decrypt(algorithm, secret, key, ivParameterSpec);

    }
}
