package com.eastcompeace.lpa.sdk.utils;

import anet.channel.request.Request;
import com.umeng.analytics.pro.cc;
import java.nio.charset.Charset;
import java.util.Locale;
import java.util.regex.Pattern;

public final class TextUtil {
    private static final Pattern HEX_STRING_PATTERN = Pattern.compile("^(?:[0-9a-fA-F]{2})*$");

    private TextUtil() {
    }

    public static String intToHexString(int i) {
        StringBuilder sb = new StringBuilder();
        sb.append(Integer.toHexString(i).toUpperCase(Locale.ENGLISH));
        if (sb.length() % 2 != 0) {
            sb.insert(0, '0');
        }
        return sb.toString();
    }

    public static String longToHexString(long j) {
        return Long.toHexString(j).toUpperCase(Locale.ENGLISH);
    }

    public static String bytesToHexString(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        char[] charArray = "0123456789ABCDEF".toCharArray();
        StringBuilder sb = new StringBuilder(bArr.length * 2);
        for (byte b : bArr) {
            sb.append(charArray[(b & 240) >>> 4]);
            sb.append(charArray[b & cc.m]);
        }
        return sb.toString();
    }

    public static String hexToString(String str) {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (i < str.length()) {
            int i2 = i + 2;
            sb.append((char) Integer.parseInt(str.substring(i, i2), 16));
            i = i2;
        }
        return sb.toString().trim();
    }

    public static String bytesToString(byte[] bArr) {
        return hexToString(bytesToHexString(bArr));
    }

    public static byte[] hexStringToBytes(String str) {
        if (str == null) {
            return null;
        }
        checkHexStringOrThrow(str);
        int length = str.length() / 2;
        byte[] bArr = new byte[length];
        for (int i = 0; i < length; i++) {
            int i2 = i * 2;
            bArr[i] = (byte) Integer.parseInt(str.substring(i2, i2 + 2), 16);
        }
        return bArr;
    }

    public static void checkHexStringOrThrow(String str) throws IllegalArgumentException {
        if (!HEX_STRING_PATTERN.matcher(str).matches()) {
            throw new IllegalArgumentException("Hex String is Not Well Formatted " + str);
        }
    }

    public static void checkHexStringOrThrow(String str, int i) throws IllegalArgumentException {
        checkHexStringOrThrow(str);
        if (i != str.length() / 2) {
            throw new IllegalArgumentException("Invalid Hex String length");
        }
    }

    public static byte[] stringToUtf8Bytes(String str) {
        return str.getBytes(Charset.forName(Request.DEFAULT_CHARSET));
    }

    public static String escapeLineFeed(String str) {
        return (str == null || str.isEmpty()) ? "" : str.replace(ShellUtils.COMMAND_LINE_END, "\\n");
    }
}
