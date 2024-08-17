package com.eastcompeace.lpa.sdk.utils;

import com.umeng.analytics.pro.cc;
import org.android.agoo.message.MessageService;

public class HexUtil {
    private static final char[] DIGITS_LOWER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private static final char[] DIGITS_UPPER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    public static char[] encodeHex(byte[] bArr) {
        return encodeHex(bArr, false);
    }

    public static char[] encodeHex(byte[] bArr, boolean z) {
        return encodeHex(bArr, z ? DIGITS_LOWER : DIGITS_UPPER);
    }

    protected static char[] encodeHex(byte[] bArr, char[] cArr) {
        if (bArr == null) {
            return null;
        }
        int length = bArr.length;
        char[] cArr2 = new char[(length << 1)];
        int i = 0;
        for (int i2 = 0; i2 < length; i2++) {
            int i3 = i + 1;
            cArr2[i] = cArr[(bArr[i2] & 240) >>> 4];
            i = i3 + 1;
            cArr2[i3] = cArr[bArr[i2] & cc.m];
        }
        return cArr2;
    }

    public static String encodeHexStr(byte[] bArr) {
        return encodeHexStr(bArr, false);
    }

    public static String encodeHexStr(byte[] bArr, boolean z) {
        return encodeHexStr(bArr, z ? DIGITS_LOWER : DIGITS_UPPER);
    }

    protected static String encodeHexStr(byte[] bArr, char[] cArr) {
        return bArr == null ? "" : new String(encodeHex(bArr, cArr));
    }

    public static String decodeHexStr(String str) {
        return new String(decodeHex(str.toCharArray()));
    }

    public static byte[] decodeHex(String str) {
        return decodeHex(str.toCharArray());
    }

    public static byte[] decodeHex(char[] cArr) {
        int length = cArr.length;
        if ((length & 1) == 0) {
            byte[] bArr = new byte[(length >> 1)];
            int i = 0;
            int i2 = 0;
            while (i < length) {
                int i3 = i + 1;
                i = i3 + 1;
                bArr[i2] = (byte) (((toDigit(cArr[i], i) << 4) | toDigit(cArr[i3], i3)) & 255);
                i2++;
            }
            return bArr;
        }
        throw new RuntimeException("Odd number of characters.");
    }

    protected static int toDigit(char c, int i) {
        int digit = Character.digit(c, 16);
        if (digit != -1) {
            return digit;
        }
        throw new RuntimeException("Illegal hexadecimal character " + c + " at index " + i);
    }

    public static String intToHex(int i, int i2) {
        String hexString = Integer.toHexString(i);
        if (hexString == null) {
            hexString = "";
        }
        int length = i2 - hexString.length();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i3 = 0; i3 < length; i3++) {
            stringBuffer.append(MessageService.MSG_DB_READY_REPORT);
        }
        return stringBuffer.append(hexString).toString();
    }

    public static void main(String[] strArr) throws Exception {
        String encodeHexStr = encodeHexStr("gasm".getBytes());
        String str = new String(decodeHex(encodeHexStr));
        System.out.println("转换前：" + "gasm");
        System.out.println("转换后：" + encodeHexStr);
        System.out.println("还原后：" + str);
    }

    public static String int2HexSeqNumber(int i) {
        if (i < 0) {
            throw new RuntimeException("int2Hex: iValue < 0");
        } else if (i < 16) {
            return MessageService.MSG_DB_READY_REPORT + Integer.toUnsignedString(i, 16);
        } else if (i >= 16 && i < 128) {
            return Integer.toUnsignedString(i, 16);
        } else {
            if (i >= 128 && i < 256) {
                return "00" + Integer.toUnsignedString(i, 16);
            } else if (i >= 256 && i < 4096) {
                return MessageService.MSG_DB_READY_REPORT + Integer.toUnsignedString(i, 16);
            } else if (i >= 4096 && i < 65536) {
                return Integer.toUnsignedString(i, 16);
            } else {
                throw new RuntimeException("int2Hex :iValue=" + i);
            }
        }
    }

    public static String hexString2binaryString(String str) {
        if (str == null || str.length() % 2 != 0) {
            return null;
        }
        int i = 0;
        String str2 = "";
        while (i < str.length()) {
            int i2 = i + 1;
            String str3 = "0000" + Integer.toBinaryString(Integer.parseInt(str.substring(i, i2), 16));
            str2 = str2 + str3.substring(str3.length() - 4);
            i = i2;
        }
        return str2;
    }

    public static boolean isValidApdu(String str) {
        if (str == null || str.trim().equals("") || str.length() < 10 || str.length() % 2 != 0) {
            throw new RuntimeException("无效的APDU:apdu=" + str);
        }
        int length = str.length();
        if (length == 10) {
            if (Integer.parseInt(str.substring(length - 2, length)) == 0) {
                return true;
            }
            throw new RuntimeException("无效的APDU:apdu=" + str);
        } else if (Integer.parseInt(str.substring(8, 10), 16) == str.substring(10).length() / 2) {
            return true;
        } else {
            throw new RuntimeException("无效的APDU:apdu=" + str);
        }
    }

    public static String int2Hex4Len(int i) {
        if (i < 0) {
            throw new RuntimeException("int2Hex: iValue < 0");
        } else if (i < 16) {
            return "000" + Integer.toUnsignedString(i, 16);
        } else if (i >= 16 && i < 256) {
            return "00" + Integer.toUnsignedString(i, 16);
        } else if (i >= 256 && i < 4096) {
            return MessageService.MSG_DB_READY_REPORT + Integer.toUnsignedString(i, 16);
        } else if (i >= 4096 && i < 65536) {
            return Integer.toUnsignedString(i, 16);
        } else {
            throw new RuntimeException("int2Hex4Len : iValue >= 65536");
        }
    }
}
