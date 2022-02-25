package com.webservice.foetmobile.utilitaire;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utilitaire {

    public static String encryptPassword(String password) {
        String returnValue = null;
        byte[] buf = password.getBytes();
        MessageDigest algorithm = null;
        try {
            algorithm = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
        }
        algorithm.reset();
        algorithm.update(buf);
        byte[] digest = algorithm.digest();
        returnValue = "";
        for (int byteIdx = 0; byteIdx < digest.length; byteIdx++) {
            returnValue += Integer.toHexString(digest[byteIdx] + 256);
        }
        return returnValue;

    }
}
