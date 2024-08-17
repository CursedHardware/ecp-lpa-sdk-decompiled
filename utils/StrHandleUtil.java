package com.eastcompeace.lpa.sdk.utils;

import com.eastcompeace.lpa.sdk.bean.es10.TLVObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StrHandleUtil {
    public static boolean isEmpty(Object[] objArr) {
        return objArr == null || objArr.length == 0;
    }

    public static boolean isBlank(CharSequence charSequence) {
        int length;
        if (!(charSequence == null || (length = charSequence.length()) == 0)) {
            for (int i = 0; i < length; i++) {
                if (!Character.isWhitespace(charSequence.charAt(i))) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isNotBlank(CharSequence charSequence) {
        return !isBlank(charSequence);
    }

    public static boolean isNoneBlank(CharSequence... charSequenceArr) {
        return !isAnyBlank(charSequenceArr);
    }

    public static boolean isAnyBlank(CharSequence... charSequenceArr) {
        if (isEmpty(charSequenceArr)) {
            return true;
        }
        for (CharSequence isBlank : charSequenceArr) {
            if (isBlank(isBlank)) {
                return true;
            }
        }
        return false;
    }

    public static String getRandomData(int i) {
        Random random = new Random((long) i);
        StringBuffer stringBuffer = new StringBuffer();
        for (int i2 = 0; i2 < i; i2++) {
            stringBuffer.append(HexUtil.intToHex(random.nextInt(255), 2));
        }
        return stringBuffer.toString();
    }

    public static String add_80(String str) {
        return add_80(str, 0, 8);
    }

    public static String add_80(String str, int i, int i2) {
        int length = (str.length() / 2) % i2;
        if (i == 1 && length == 0) {
            return str;
        }
        return str + "8000000000000000000000000000000000000000000000000000000000000000".substring(0, (i2 - length) * 2);
    }

    public static String del_80(String str) {
        return del_80(str, 8);
    }

    public static String del_80(String str, int i) {
        String substring;
        do {
            substring = str.substring(str.length() - 2);
            if ("80".equals(substring) || "00".equals(substring)) {
                str = str.substring(0, str.length() - 2);
            } else {
                throw new RuntimeException("数据补位出错.");
            }
        } while (!"80".equals(substring));
        if ((str.length() / 2) % i <= i) {
            return str;
        }
        throw new RuntimeException("数据补位出错，补位长度超过最大值.");
    }

    public static String genLV(String str) {
        return HexUtil.intToHex(str.length() / 2, 2) + str;
    }

    public static String genTLV(String str, String str2) {
        return str + genLV(str2);
    }

    public static String genBER_Len(int i) {
        String intToHex = i <= 127 ? HexUtil.intToHex(i, 2) : "";
        if (i > 127 && i <= 255) {
            intToHex = "81" + HexUtil.intToHex(i, 2);
        }
        return (i <= 255 || i > 65535) ? intToHex : "82" + HexUtil.intToHex(i, 4);
    }

    public static String genBER_TLV(String str, String str2) {
        return genBER_TLV(str, str2, 0);
    }

    public static String genBER_TLV(String str, String str2, int i) {
        if (i == 1 && isBlank(str2)) {
            return "";
        }
        int length = str2.length() / 2;
        if (str2.length() % 2 != 0) {
            System.out.println("tag: " + str);
            System.out.println("sValue: " + str2);
        }
        return str + genBER_Len(length) + str2;
    }

    public static String genBER_LV(String str) {
        return genBER_LV(str, 0);
    }

    public static String genBER_LV(String str, int i) {
        if (str == "" && i == 1) {
            return "";
        }
        return genBER_Len(str.length() / 2) + str;
    }

    public static String genBER_DGI(String str, String str2) {
        return genBER_DGI(str, str2, 0);
    }

    public static String genBER_DGI(String str, String str2, int i) {
        String str3 = "";
        if (!isNotBlank(str2)) {
            return str3;
        }
        if (i == 1 && str2 == str3) {
            return str3;
        }
        int length = str2.length() / 2;
        if (length < 255) {
            str3 = HexUtil.intToHex(length, 2) + str2;
        }
        if (length >= 255 && length <= 65535) {
            str3 = "FF" + HexUtil.intToHex(length, 4) + str2;
        }
        return str + str3;
    }

    public static String xOR(String str, String str2, int i) {
        byte[] decodeHex = HexUtil.decodeHex(str);
        byte[] decodeHex2 = HexUtil.decodeHex(str2);
        int i2 = i / 2;
        byte[] bArr = new byte[i2];
        if (str.length() == str2.length()) {
            for (int i3 = 0; i3 < i2; i3++) {
                if (i3 >= decodeHex.length) {
                    bArr[i3] = 0;
                }
                bArr[i3] = (byte) (decodeHex[i3] ^ decodeHex2[i3]);
            }
            return HexUtil.encodeHexStr(bArr);
        }
        throw new RuntimeException("不能进行模二加.");
    }

    public static String genASN1HexData(String str, String str2) {
        return (!isNoneBlank(str2) || (Integer.parseInt(str.substring(0, 2), 16) & 32) != 0) ? "" : genASN1DerData(str, str2);
    }

    public static String genASN1DerData(String str, String str2) {
        return isNoneBlank(str2) ? genBER_TLV(str, str2, 0) : "";
    }

    public static String getTLVvalueBER(String str, int i) {
        if (i == 0) {
            i = 2;
        }
        int i2 = i + 2;
        String substring = str.substring(i, i2);
        String substring2 = str.substring(i2);
        if ("81".equals(substring)) {
            return substring2.substring(2);
        }
        return "82".equals(substring) ? substring2.substring(4) : substring2;
    }

    public static boolean checkData(String str, String str2, String str3) {
        return (str == null || str2 == null || !str.equals(str2)) ? false : true;
    }

    public static List<TLVObject> analyseBerTLVStringOnlyFirstLevel(String str) {
        ArrayList arrayList = new ArrayList();
        while (true) {
            if (!isNoneBlank(str)) {
                return arrayList;
            }
            String substring = str.substring(0, 2);
            int parseInt = Integer.parseInt(substring, 16);
            String substring2 = str.substring(2);
            if ((parseInt & 31) == 31) {
                substring = substring + substring2.substring(0, 2);
                substring2 = substring2.substring(2);
                if (Integer.parseInt(substring2.substring(0, 2), 16) > 128) {
                    substring = substring + substring2.substring(0, 2);
                }
            }
            String substring3 = substring2.substring(0, 2);
            int parseInt2 = Integer.parseInt(substring3, 16);
            String substring4 = substring2.substring(2);
            if (parseInt2 > 128) {
                int i = (parseInt2 & 15) * 2;
                int parseInt3 = Integer.parseInt(substring4.substring(0, i), 16);
                substring3 = substring3 + substring4.substring(0, i);
                substring4 = substring4.substring(i);
                parseInt2 = parseInt3;
            }
            if (substring4.length() / 2 < parseInt2) {
                System.out.println("Length值大于后续长度");
            }
            int i2 = parseInt2 * 2;
            String substring5 = substring4.substring(0, i2);
            str = substring4.substring(i2);
            arrayList.add(new TLVObject(substring, substring3, substring5));
        }
    }

    public static String[] getSequenceOfElement(String str, String str2, String str3) {
        List<TLVObject> analyseBerTLVStringOnlyFirstLevel = analyseBerTLVStringOnlyFirstLevel(str3);
        String[] strArr = new String[analyseBerTLVStringOnlyFirstLevel.size()];
        for (int i = 0; i < analyseBerTLVStringOnlyFirstLevel.size(); i++) {
            checkData(str, analyseBerTLVStringOnlyFirstLevel.get(i).getStag(), str2);
            strArr[i] = analyseBerTLVStringOnlyFirstLevel.get(i).getSvalue();
        }
        return strArr;
    }

    public static String getSignatureValue(String str) {
        List<TLVObject> analyseBerTLVStringOnlyFirstLevel = analyseBerTLVStringOnlyFirstLevel(str);
        String str2 = "";
        if (analyseBerTLVStringOnlyFirstLevel.size() != 1) {
            return str2;
        }
        for (TLVObject svalue : analyseBerTLVStringOnlyFirstLevel(analyseBerTLVStringOnlyFirstLevel.get(0).getSvalue())) {
            String svalue2 = svalue.getSvalue();
            if ("00".equals(svalue2.substring(0, 2))) {
                svalue2 = svalue2.substring(2);
            }
            str2 = str2 + svalue2;
        }
        return str2;
    }
}
