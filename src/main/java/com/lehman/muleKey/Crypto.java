package com.lehman.muleKey;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

public class Crypto {
    public static ObservableList<String> getAlgorithms() {
        String[] algorithms = {
            "AES",
            "Blowfish",
            "DES",
            "DESede",
            "RC2",
            "RCA",
            // Can only be used with JCE.
            "Camellia",
            "CAST5",
            "CAST6",
            "Noekeon",
            "Rijndael",
            "SEED",
            "Serpent",
            "Skipjack",
            "TEA",
            "Twofish",
            "XTEA",
            "RC5",
            "RC6"
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
        String cipherStr = Algorithm + "/" + Mode + "/PKCS5Padding";
        IvParameterSpec iv = new IvParameterSpec("encryptionIntVec".getBytes("UTF-8"));
        SecretKeySpec skeySpec = new SecretKeySpec(Key.getBytes("UTF-8"), Algorithm);
        Cipher c = Cipher.getInstance(cipherStr);
        c.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

        byte[] encrypted = c.doFinal(Input.getBytes());
        return Base64.getEncoder().encodeToString(encrypted);
    }
}
