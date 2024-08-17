package com.eastcompeace.lpa.sdk.utils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import kotlin.UByte;

public class HmacSHA256Util {
    public static byte[] hmacSHA256(byte[] bArr, byte[] bArr2) throws Exception {
        Mac instance = Mac.getInstance("HmacSHA256");
        instance.init(new SecretKeySpec(bArr, 0, bArr.length, "HmacSHA256"));
        return instance.doFinal(bArr2);
    }

    public static String byteArrayToHexString(byte[] bArr) {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (bArr != null && i < bArr.length) {
            String hexString = Integer.toHexString(bArr[i] & UByte.MAX_VALUE);
            if (hexString.length() == 1) {
                sb.append('0');
            }
            sb.append(hexString);
            i++;
        }
        return sb.toString().toLowerCase();
    }

    public static String hmacSHA256(String str, String str2) throws Exception {
        Mac instance = Mac.getInstance("HmacSHA256");
        instance.init(new SecretKeySpec(str.getBytes(), "HmacSHA256"));
        return byteArrayToHexString(instance.doFinal(str2.getBytes()));
    }

    public static String sha256_HMAC(String str, String str2) {
        try {
            Mac instance = Mac.getInstance("HmacSHA256");
            instance.init(new SecretKeySpec(str2.getBytes(), "HmacSHA256"));
            return byteArrayToHexString(instance.doFinal(str.getBytes()));
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static void main(String[] strArr) {
        String str = "10000001" + "1529853639000" + "<req><orderId>91303183862452</orderId><notifyUrl></notifyUrl><refundTime>2013-03-22 16:25:26</refundTime><refundFee>24.5</refundFee><refundNo>14252414245</refundNo></req>";
        System.out.println("加密前 =========>" + str);
        try {
            System.out.println("加密后 =========>" + hmacSHA256("b06c75b58d1701ff470119a4114f8b45", str));
        } catch (Exception e) {
            System.out.println("Error HmacSHA256 ===========>" + e.getMessage());
        }
    }
}
