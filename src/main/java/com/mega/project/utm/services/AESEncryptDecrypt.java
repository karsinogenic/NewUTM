package com.mega.project.utm.services;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESEncryptDecrypt {
    private static final String AES_CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding";
    // private static final String STATIC_IV = "Q2FyZFN5c0FzY28z"; // example static
    // IV

    public static String encrypt(String plaintext, String key, String iv) throws Exception {
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes(StandardCharsets.UTF_8));

        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");

        Cipher cipher = Cipher.getInstance(AES_CIPHER_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);

        byte[] cipherText = cipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));

        return Base64.getEncoder().encodeToString(cipherText);
    }

    public static String decrypt(String ciphertext, String key, String iv) throws Exception {
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes(StandardCharsets.UTF_8));

        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");

        Cipher cipher = Cipher.getInstance(AES_CIPHER_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);

        byte[] cipherText = null;
        try {
            cipherText = Base64.getDecoder().decode(ciphertext);

        } catch (Exception e) {
            cipherText = Base64.getUrlDecoder().decode(ciphertext);
            // TODO: handle exception
        }
        byte[] plainText = cipher.doFinal(cipherText);

        return new String(plainText, StandardCharsets.UTF_8);
    }
}
