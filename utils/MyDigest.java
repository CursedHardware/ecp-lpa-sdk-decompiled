package com.eastcompeace.lpa.sdk.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MyDigest {
    public static String md5(String str) throws NoSuchAlgorithmException {
        return HexUtil.encodeHexStr(md5(HexUtil.decodeHex(str)));
    }

    public static byte[] md5(byte[] bArr) throws NoSuchAlgorithmException {
        return algorithm(bArr, "MD5");
    }

    public static String sha1(String str) throws NoSuchAlgorithmException {
        return HexUtil.encodeHexStr(sha1(HexUtil.decodeHex(str)));
    }

    public static byte[] sha1(byte[] bArr) throws NoSuchAlgorithmException {
        return algorithm(bArr, "SHA-1");
    }

    public static String sha256(String str) throws NoSuchAlgorithmException {
        return HexUtil.encodeHexStr(sha256(HexUtil.decodeHex(str)));
    }

    public static byte[] sha256Byte(String str) throws NoSuchAlgorithmException {
        return sha256(HexUtil.decodeHex(str));
    }

    public static byte[] sha256(byte[] bArr) throws NoSuchAlgorithmException {
        System.out.println("cccode encodeHexStr:" + HexUtil.encodeHexStr(bArr));
        return algorithm(bArr, "SHA-256");
    }

    public static byte[] algorithm(byte[] bArr, String str) throws NoSuchAlgorithmException {
        MessageDigest instance = MessageDigest.getInstance(str);
        instance.update(bArr);
        return instance.digest();
    }
}
