package com.eastcompeace.lpa.sdk.fup;

import java.util.ArrayList;
import java.util.List;

public class A3Util {
    public static List<String> splitA3(String str) throws Exception {
        try {
            ArrayList arrayList = new ArrayList();
            int i = 0;
            while (i < str.length()) {
                int i2 = i + 2;
                if (str.substring(i, i2).equals("A3")) {
                    if (str.substring(i2, i2 + 2).equals("08")) {
                        i2 += 18;
                    }
                    int i3 = i2 + 6;
                    int parseInt = Integer.parseInt(str.substring(i2, i3), 16) + i3;
                    arrayList.add(str.substring(i, parseInt));
                    i = parseInt;
                } else {
                    throw new Exception("找不到A3tag，请检查TLV格式是否正确");
                }
            }
            return arrayList;
        } catch (ArrayIndexOutOfBoundsException unused) {
            throw new Exception("格式有误，解析失败");
        }
    }

    public static List<String> A3_resolver(String str) throws Exception {
        try {
            ArrayList arrayList = new ArrayList();
            String substring = str.substring(0, str.length() - 4);
            if (substring.substring(0, 2).equals("A3")) {
                int i = (substring.substring(2, 4).equals("08") ? 20 : 4) + 6 + 4;
                while (true) {
                    int i2 = i + 4;
                    if (i2 >= substring.length()) {
                        return arrayList;
                    }
                    int i3 = i + 2;
                    if (substring.substring(i, i3).equals("80")) {
                        arrayList.add(Constant.RESETTAG);
                        i = i3;
                    } else {
                        int parseInt = (Integer.parseInt(substring.substring(i, i2), 16) * 2) + i2;
                        int i4 = parseInt - 4;
                        arrayList.add(substring.substring(i2, i4) + "(" + substring.substring(i4, parseInt) + ")");
                        i = parseInt + 2;
                    }
                }
            } else {
                throw new Exception("找不到A3tag，请检查TLV格式是否正确");
            }
        } catch (ArrayIndexOutOfBoundsException unused) {
            throw new Exception("格式有误，解析失败");
        }
    }
}
