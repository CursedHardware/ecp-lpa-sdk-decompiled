package com.eastcompeace.lpa.sdk.bean.es10;

import com.eastcompeace.lpa.sdk.bean.BaseSerializableBean;
import com.eastcompeace.lpa.sdk.utils.StrHandleUtil;

public class Operator extends BaseSerializableBean {
    private String gid1;
    private String gid2;
    private String mcc;
    private String mnc;

    public Operator() {
    }

    public Operator(String str) {
        for (TLVObject next : StrHandleUtil.analyseBerTLVStringOnlyFirstLevel(str)) {
            String stag = next.getStag();
            stag.hashCode();
            if (stag.equals("80")) {
                this.mcc = next.getSvalue().substring(0, 3);
                this.mnc = next.getSvalue().substring(3, 6);
            }
        }
    }

    public String getMcc() {
        return this.mcc;
    }

    public void setMcc(String str) {
        this.mcc = str;
    }

    public String getMnc() {
        return this.mnc;
    }

    public void setMnc(String str) {
        this.mnc = str;
    }

    public String getGid1() {
        return this.gid1;
    }

    public void setGid1(String str) {
        this.gid1 = str;
    }

    public String getGid2() {
        return this.gid2;
    }

    public void setGid2(String str) {
        this.gid2 = str;
    }

    public String toString() {
        return "Operator{mcc='" + this.mcc + '\'' + ", mnc='" + this.mnc + '\'' + ", gid1='" + this.gid1 + '\'' + ", gid2='" + this.gid2 + '\'' + '}';
    }
}
