package com.berry.utils.encrypt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 安全散列算法SHA-256
 */
public class SHA256 {

    public static String encrypt(String strSrc) {
        MessageDigest md;
        String strDes;
        String encName = "SHA-256";

        byte[] bt = strSrc.getBytes();
        try {
            md = MessageDigest.getInstance(encName);
            md.update(bt);
            strDes = ConvertUtil.bytesToHexString(md.digest());
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
        return strDes;
    }

}
