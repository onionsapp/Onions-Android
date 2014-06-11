package com.bennyguitar.onions_android.Security;

import android.util.Base64;
import android.util.Log;

import org.cryptonode.jncryptor.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by BenG on 5/31/14.
 */
public class OCSecurity {
    // Constants
    public static int defaultShaIterations = 15000;
    public static int defaultPBKDFRounds = 10000;

    public static String stretchedSha256String(String input, int iterations) {
        byte[] hashedInput = stretchedStringData(input, iterations);
        return Base64.encodeToString(hashedInput, Base64.NO_WRAP);
    }

    private static byte[] stretchedStringData(String input, int iterations) {
        byte[] inputBytes = input.getBytes();
        for (int i = 0; i < iterations; i++) {
            inputBytes = sha256Hash(inputBytes);
        }

        return inputBytes;
    }

    private static byte[] sha256Hash(byte[] inputData) {
        MessageDigest digest;
        byte[] outputData;
        try {
            digest = MessageDigest.getInstance("SHA-256");
            digest.update(inputData);
            outputData = digest.digest();
        } catch (NoSuchAlgorithmException e1) {
            outputData = new byte[0];
        }

        return outputData;
    }

    public static String encryptedText(String text, String password) {
        // Set up
        AES256JNCryptor cryptor = ocCryptor(defaultPBKDFRounds);
        String encryptedText = null;

        // Encrypt
        try {
            byte[] ciphertext = cryptor.encryptData(text.getBytes(), password.toCharArray());
            encryptedText = Base64.encodeToString(ciphertext, Base64.NO_WRAP);
        } catch (CryptorException e) {
            e.printStackTrace();
        }

        // Return it
        return encryptedText;
    }

    public static String decryptedText(String base64text, String password, int iterations) {
        // Set Up
        AES256JNCryptor cryptor = ocCryptor(iterations);
        byte[] base64Bytes = Base64.decode(base64text, Base64.NO_WRAP);
        String decryptedText = null;

        // Decrypt
        try {
            byte[] ciphertext = cryptor.decryptData(base64Bytes, password.toCharArray());
            decryptedText = new String(ciphertext);
        } catch (CryptorException e) {
            e.printStackTrace();
        }

        // Return it
        return decryptedText;
    }

    private static AES256JNCryptor ocCryptor(int iterations) {
        AES256JNCryptor cryptor = new AES256JNCryptor();
        cryptor.setPBKDFIterations(iterations);
        return cryptor;
    }
}
