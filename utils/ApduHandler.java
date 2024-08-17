package com.eastcompeace.lpa.sdk.utils;

import com.eastcompeace.lpa.sdk.bean.es10.TLVObject;
import java.util.ArrayList;
import java.util.List;

public class ApduHandler {
    private static final String TAG = "ApduHandler";

    public static TLVObject apduTransfTlv(String str, String str2) {
        if (!str2.contains(str)) {
            return null;
        }
        String[] split = str2.split(str);
        if (split.length <= 1) {
            return null;
        }
        String str3 = split[1];
        String substring = str3.substring(0, 2);
        return new TLVObject(str, substring, str3.substring(2, Integer.valueOf(substring, 16).intValue() + 2));
    }

    public static List<TLVObject> apduTransfTlvsByParentTag(String str, String str2) {
        return getTLVByTag(str, str2, true);
    }

    public static List<TLVObject> getTLVByTag(String str, String str2, boolean z) {
        List<TLVObject> tLVObjects = getTLVObjects(str2);
        ArrayList arrayList = new ArrayList();
        getTLVByTag(str, tLVObjects, arrayList, z);
        return arrayList;
    }

    private static void getTLVByTag(String str, List<TLVObject> list, List<TLVObject> list2, boolean z) {
        if (list != null) {
            for (TLVObject next : list) {
                if (str.equals(next.getStag())) {
                    list2.add(next);
                    if (z) {
                        ArrayList arrayList = new ArrayList();
                        arrayList.add(next);
                        getChildTLVObjects(arrayList);
                    }
                }
            }
            if (list2.size() == 0) {
                for (TLVObject svalue : list) {
                    getTLVByTag(str, getTLVObjects(svalue.getSvalue()), list2, z);
                }
            }
        }
    }

    public static void getChildTLVObjects(List<TLVObject> list) {
        for (TLVObject next : list) {
            next.setChildrenTLV(getTLVObjects(next.getSvalue()));
            try {
                getChildTLVObjects(next.getChildrenTLV());
            } catch (Exception unused) {
            }
        }
    }

    public static List<TLVObject> getTLVObjects(String str) {
        int i;
        int i2;
        ArrayList arrayList = new ArrayList();
        if (str.endsWith("9000")) {
            str = str.substring(0, str.length() - 4);
        }
        while (str.length() > 0) {
            try {
                int i3 = (Integer.parseInt(str.substring(0, 2), 16) & 31) == 31 ? 4 : 2;
                int intValue = Integer.valueOf(str.substring(i3, i3 + 2), 16).intValue();
                if (intValue > 128) {
                    i2 = (intValue & 15) * 2;
                    i = 2;
                } else {
                    i2 = 2;
                    i = 0;
                }
                String substring = str.substring(0, i3);
                String str2 = "";
                if (i != 0) {
                    str2 = str.substring(i3, i3 + i);
                }
                int i4 = i3 + i;
                int i5 = i2 + i4;
                String substring2 = str.substring(i4, i5);
                int intValue2 = (Integer.valueOf(substring2, 16).intValue() * 2) + i5;
                String substring3 = str.substring(i5, intValue2);
                str = str.substring(intValue2);
                arrayList.add(new TLVObject(substring, str2, substring2, substring3));
            } catch (Exception | StringIndexOutOfBoundsException unused) {
            }
        }
        return arrayList;
    }

    public static String getLen(String str) {
        int length = str.length() / 2;
        if (length > 255) {
            return String.format("%s%04X", new Object[]{82, Integer.valueOf(length)});
        } else if (length > 128) {
            return String.format("%s%02X", new Object[]{81, Integer.valueOf(length)});
        } else {
            return String.format("%02X", new Object[]{Integer.valueOf(length)});
        }
    }

    public static String getLen(StringBuffer stringBuffer) {
        int length = stringBuffer.length() / 2;
        if (length > 255) {
            return String.format("%s%04X", new Object[]{82, Integer.valueOf(length)});
        } else if (length > 128) {
            return String.format("%s%02X", new Object[]{81, Integer.valueOf(length)});
        } else {
            return String.format("%02X", new Object[]{Integer.valueOf(length)});
        }
    }

    public static String getLenAndApdu(String str) {
        int length = str.length() / 2;
        if (length > 255) {
            return String.format("%s%04X%s", new Object[]{82, Integer.valueOf(length), str});
        } else if (length > 128) {
            return String.format("%s%02X%s", new Object[]{81, Integer.valueOf(length), str});
        } else {
            return String.format("%02X%s", new Object[]{Integer.valueOf(length), str});
        }
    }

    public static String getLenAndApdu(StringBuffer stringBuffer) {
        int length = stringBuffer.length() / 2;
        if (length > 255) {
            return String.format("%s%04X%s", new Object[]{82, Integer.valueOf(length), stringBuffer});
        } else if (length > 128) {
            return String.format("%s%02X%s", new Object[]{81, Integer.valueOf(length), stringBuffer});
        } else {
            return String.format("%02X%s", new Object[]{Integer.valueOf(length), stringBuffer});
        }
    }

    public static String getInverseCode(String str) {
        if (StringUtils.isEmpty(str)) {
            return "";
        }
        char[] charArray = str.toCharArray();
        int length = charArray.length;
        int i = length % 2;
        int i2 = i == 0 ? length : length + 1;
        char[] cArr = new char[i2];
        for (int i3 = 0; i3 < length; i3++) {
            cArr[i3 % 2 == 0 ? i3 + 1 : i3 - 1] = charArray[i3];
        }
        if (i != 0) {
            cArr[i2 - 2] = 'F';
        }
        return String.valueOf(cArr);
    }
}
