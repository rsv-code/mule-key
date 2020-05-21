/*
 * This file is part of the Mule-Key (https://github.com/rsv-code/mule-key).
 * Copyright (c) 2020 Roseville Code Inc
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

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

/**
 * Crypto class provides encryption support functions.
 */
public class Crypto {
    /**
     * Gets a list of algorithms.
     * @return A ObservableList of String objects with the available algorithm names.
     */
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

    /**
     * Gets a list of modes.
     * @return A ObservableList of String objects with the available mode names.
     */
    public static ObservableList<String> getModes() {
        String[] modes = {
                "CBC",
                "CFB",
                "ECB",
                "OFB"
        };
        return FXCollections.observableList(Arrays.asList(modes));
    }

    /**
     * Encrypts the provided input string with the provided algorithm, mode,
     * and key. It returns the encrypted String.
     * @param Algorithm is a String with the algorithm to use.
     * @param Mode is a String with the mode to use.
     * @param Key is a String with the key to use.
     * @param Input is a String to encrypt.
     * @return A String with the encrypted data.
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     * @throws InvalidAlgorithmParameterException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    public static String encrypt(String Algorithm, String Mode, String Key, String Input) throws NoSuchPaddingException, NoSuchAlgorithmException, UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        // NoPadding is other option.
        String cipherStr = Algorithm + "/" + Mode + "/PKCS5Padding";
        IvParameterSpec iv = getIvParam(Algorithm, Key);
        SecretKeySpec skeySpec = new SecretKeySpec(Key.getBytes("UTF-8"), Algorithm);
        Cipher c = Cipher.getInstance(cipherStr);

        if (Mode.equals("ECB")) {
            c.init(Cipher.ENCRYPT_MODE, skeySpec);
        } else {
            c.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
        }

        byte[] encrypted = c.doFinal(Input.getBytes());
        return Base64.getEncoder().encodeToString(encrypted);
    }

    /**
     * Generates a random key with the provided algorithm.
     * @param Algorithm is a String with the algorithm to generate for.
     * @return A String with the generated key.
     */
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

    /**
     * Gets the IvParameterSpec object from the provided algorithm and key.
     * @param Algorithm is a String with the algorithm to use.
     * @param Key is a String with the key to use.
     * @return A IvParameterSpec object to use in the encryption process.
     */
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

    /**
     * Gets a byte array with the key bytes of the length provided
     * for use in the IV parameter. The key is often used by default
     * as the IV parameter.
     * @param Length is an int with the length in bytes.
     * @param Key is a String with the key to use.
     * @return A byte array with the IV parameter.
     */
    public static byte[] getIvParamBytes(int Length, String Key) {
        return Arrays.copyOf(Key.getBytes(), Length);
    }

    /**
     * Gets a byte array of random bytes.
     * @param Length is an int with the size of the random byte array.
     * @return A byte array with the random bytes.
     */
    public static byte[] getRandomBytes(int Length) {
        Random rand = new Random();
        byte[] bytes = new byte[Length];
        rand.nextBytes(bytes);
        return bytes;
    }
}
