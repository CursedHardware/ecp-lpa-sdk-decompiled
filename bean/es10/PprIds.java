package com.eastcompeace.lpa.sdk.bean.es10;

import com.eastcompeace.lpa.sdk.bean.BaseSerializableBean;
import com.eastcompeace.lpa.sdk.utils.HexUtil;

public class PprIds extends BaseSerializableBean {
    private int ppr1;
    private int ppr2;
    private int pprUpdateControl;

    private int chartToint(char c) {
        return c - '0';
    }

    public PprIds(String str) {
        char[] charArray = HexUtil.hexString2binaryString(str.substring(2, str.length())).toCharArray();
        int length = charArray.length - 1;
        int i = 0;
        this.pprUpdateControl = length > 0 ? chartToint(charArray[0]) : 0;
        this.ppr1 = 1 < length ? chartToint(charArray[1]) : 0;
        this.ppr2 = 2 < length ? chartToint(charArray[2]) : i;
    }

    public int getPprUpdateControl() {
        return this.pprUpdateControl;
    }

    public void setPprUpdateControl(int i) {
        this.pprUpdateControl = i;
    }

    public int getPpr1() {
        return this.ppr1;
    }

    public void setPpr1(int i) {
        this.ppr1 = i;
    }

    public int getPpr2() {
        return this.ppr2;
    }

    public void setPpr2(int i) {
        this.ppr2 = i;
    }
}
