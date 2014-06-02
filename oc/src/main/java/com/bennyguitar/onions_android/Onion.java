package com.bennyguitar.onions_android;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by BenG on 5/31/14.
 */
@ParseClassName("Onion")
public class Onion extends ParseObject {
    public static String onionTitleKey = "onionTitle";
    public static String onionInfoKey = "onionInfo";
    public static String iterationsKey = "iterations";
    public String plainTextTitle;
    public String plainTextInfo;

    // Decrypt/Encrypt
    public void decryptOnion() {
        plainTextTitle = OCSecurity.decryptedText(getString(onionTitleKey), OCSession.mainSession.Password, getIterations());
        plainTextInfo = OCSecurity.decryptedText(getString(onionInfoKey), OCSession.mainSession.Password, getIterations());
    }

    public void encryptOnion() {
        String encryptedTitle = OCSecurity.encryptedText(plainTextTitle, OCSession.mainSession.Password);
        String encryptedInfo = OCSecurity.encryptedText(plainTextInfo, OCSession.mainSession.Password);
        put(onionTitleKey, encryptedTitle);
        put(onionInfoKey, encryptedInfo);
        setIterations(OCSecurity.defaultPBKDFRounds);
    }

    // Iterations
    public int getIterations() {
        return getInt(iterationsKey);
    }

    public void setIterations(int iterations) {
        put(iterationsKey, iterations);
    }

    // Save


    // Delete

}
