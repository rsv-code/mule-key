package com.lehman.muleKey;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.*;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;

public class Crypto {
    public static ObservableList<String> getAlgorithms() {
        String[] algorithms = {
            "AES",
            "Blowfish",
            "DES",
            "DESede",
            "RC2"
        };
        return FXCollections.observableList(Arrays.asList(algorithms));
    }

    public static ObservableList<String> getModes() {
        String[] modes = {
                "CBC",
                "CFB",
                "ECB",
                "OFB"
        };
        return FXCollections.observableList(Arrays.asList(modes));
    }

    public static String encrypt(String Algorithm, String Mode, String Key, String Input) throws NoSuchPaddingException, NoSuchAlgorithmException, UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        // NoPadding is other option.
        String cipherStr = Algorithm + "/" + Mode + "/PKCS5Padding";
        IvParameterSpec iv = getIvParam(Algorithm, Key);
        SecretKeySpec skeySpec = new SecretKeySpec(Key.getBytes("UTF-8"), Algorithm);
        Cipher c = Cipher.getInstance(cipherStr);
        c.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

        byte[] encrypted = c.doFinal(Input.getBytes());
        return Base64.getEncoder().encodeToString(encrypted);
    }

    public static String generateKey(String Algorithm) {
        int length = 16;

        switch(Algorithm) {
            case "AES":
                length = 16;
                break;
            case "Blowfish":
                length = 8;
                break;
            case "DES":
                length = DESKeySpec.DES_KEY_LEN;
                break;
            case "DESede":
                length = DESedeKeySpec.DES_EDE_KEY_LEN;
                break;
            case "RC2":
                length = 8;
                break;
        }
        return Base64.getEncoder().encodeToString(getRandomBytes(length)).substring(0, length);
    }

    public static IvParameterSpec getIvParam(String Algorithm, String Key) {
        int length = 16;
        switch(Algorithm) {
            case "AES":
                length = 16;
                break;
            case "Blowfish":
                length = 8;
                break;
            case "DES":
                length = 8;
                break;
            case "DESede":
                length = 8;
                break;
            case "RC2":
                length = 8;
                break;
        }
        return new IvParameterSpec(getIvParamBytes(length, Key));
    }

    public static byte[] getIvParamBytes(int Length, String Key) {
        return Arrays.copyOf(Key.getBytes(), Length);
    }

    public static byte[] getRandomBytes(int Length) {
        Random rand = new Random();
        byte[] bytes = new byte[Length];
        rand.nextBytes(bytes);
        return bytes;
    }
}
