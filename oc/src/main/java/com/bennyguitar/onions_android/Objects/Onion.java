package com.bennyguitar.onions_android.Objects;

import com.bennyguitar.onions_android.Security.OCSecurity;
import com.bennyguitar.onions_android.Session.OCSession;
import com.parse.DeleteCallback;
import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by BenG on 5/31/14.
 */
@ParseClassName("Onion")
public class Onion extends ParseObject {
    public final static String onionTitleKey = "onionTitle";
    public final static String onionInfoKey = "onionInfo";
    public final static String iterationsKey = "iterations";
    public final static String userIdKey = "userId";
    public final static String versionKey = "onionVersion";
    public final static Double currentVersion = 2.0;
    public String plainTextTitle;
    public String plainTextInfo;

    // New Onion
    public static Onion newOnion(String title, String info) {
        Onion onion = new Onion();
        onion.plainTextTitle = title != null ? title : "";
        onion.plainTextInfo = info != null ? info : "";
        onion.put(userIdKey, OCSession.mainSession.UserId);
        onion.encryptOnion();
        return onion;
    }

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
        setVersion(currentVersion);
    }

    // Iterations
    public int getIterations() {
        return getInt(iterationsKey);
    }

    public void setIterations(int iterations) {
        put(iterationsKey, iterations);
    }

    // Version
    public double getVersion() {
        return getDouble(versionKey);
    }

    public void setVersion(double v) {
        put(versionKey, v);
    }
}
