package com.eastcompeace.lpa.sdk.utils;

import com.umeng.analytics.pro.cc;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import kotlin.UByte;
import okio.Utf8;

public class Base64 {
    private static final char[] legalChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();

    public static String encodeHex(String str) {
        return (str == null || str == "") ? "" : encode(HexUtil.decodeHex(str));
    }

    public static String encode(byte[] bArr) {
        int length = bArr.length;
        StringBuffer stringBuffer = new StringBuffer((bArr.length * 3) / 2);
        int i = length - 3;
        int i2 = 0;
        int i3 = 0;
        while (i2 <= i) {
            byte b = ((bArr[i2] & UByte.MAX_VALUE) << cc.n) | ((bArr[i2 + 1] & UByte.MAX_VALUE) << 8) | (bArr[i2 + 2] & UByte.MAX_VALUE);
            char[] cArr = legalChars;
            stringBuffer.append(cArr[(b >> 18) & 63]);
            stringBuffer.append(cArr[(b >> 12) & 63]);
            stringBuffer.append(cArr[(b >> 6) & 63]);
            stringBuffer.append(cArr[b & Utf8.REPLACEMENT_BYTE]);
            i2 += 3;
            int i4 = i3 + 1;
            if (i3 >= 14) {
                stringBuffer.append(" ");
                i3 = 0;
            } else {
                i3 = i4;
            }
        }
        int i5 = 0 + length;
        if (i2 == i5 - 2) {
            int i6 = ((bArr[i2 + 1] & UByte.MAX_VALUE) << 8) | ((bArr[i2] & UByte.MAX_VALUE) << cc.n);
            char[] cArr2 = legalChars;
            stringBuffer.append(cArr2[(i6 >> 18) & 63]);
            stringBuffer.append(cArr2[(i6 >> 12) & 63]);
            stringBuffer.append(cArr2[(i6 >> 6) & 63]);
            stringBuffer.append("=");
        } else if (i2 == i5 - 1) {
            int i7 = (bArr[i2] & UByte.MAX_VALUE) << cc.n;
            char[] cArr3 = legalChars;
            stringBuffer.append(cArr3[(i7 >> 18) & 63]);
            stringBuffer.append(cArr3[(i7 >> 12) & 63]);
            stringBuffer.append("==");
        }
        return stringBuffer.toString().replace(" ", "");
    }

    public static String decodeHex(String str) {
        return HexUtil.encodeHexStr(decode(str));
    }

    private static int decode(char c) {
        int i;
        if (c >= 'A' && c <= 'Z') {
            return c - 'A';
        }
        if (c >= 'a' && c <= 'z') {
            i = c - 'a';
        } else if (c >= '0' && c <= '9') {
            i = (c - '0') + 26;
        } else if (c == '+') {
            return 62;
        } else {
            if (c == '/') {
                return 63;
            }
            if (c == '=') {
                return 0;
            }
            throw new RuntimeException("unexpected code: " + c);
        }
        return i + 26;
    }

    public static byte[] decode(String str) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            decode(str, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            try {
                byteArrayOutputStream.close();
            } catch (IOException e) {
                System.err.println("Error while decoding BASE64: " + e.toString());
            }
            return byteArray;
        } catch (IOException unused) {
            throw new RuntimeException();
        }
    }

    private static void decode(String str, OutputStream outputStream) throws IOException {
        int length = str.length();
        int i = 0;
        while (true) {
            if (i < length && str.charAt(i) <= ' ') {
                i++;
            } else if (i != length) {
                int i2 = i + 2;
                int i3 = i + 3;
                int decode = (decode(str.charAt(i)) << 18) + (decode(str.charAt(i + 1)) << 12) + (decode(str.charAt(i2)) << 6) + decode(str.charAt(i3));
                outputStream.write((decode >> 16) & 255);
                if (str.charAt(i2) != '=') {
                    outputStream.write((decode >> 8) & 255);
                    if (str.charAt(i3) != '=') {
                        outputStream.write(decode & 255);
                        i += 4;
                    } else {
                        return;
                    }
                } else {
                    return;
                }
            } else {
                return;
            }
        }
    }
}
