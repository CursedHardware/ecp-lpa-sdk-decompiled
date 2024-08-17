package com.eastcompeace.lpa.sdk.bean.es9;

import com.eastcompeace.lpa.sdk.bean.BaseSerializableBean;
import com.eastcompeace.lpa.sdk.bean.es10.TLVObject;
import com.eastcompeace.lpa.sdk.utils.ApduHandler;
import com.eastcompeace.lpa.sdk.utils.HexUtil;
import java.util.List;

public class SmdpSigned2 extends BaseSerializableBean {
    private String bppEuiccOtpk;
    private boolean ccRequiredFlag;
    private String transactionId;

    public SmdpSigned2(byte[] bArr) {
        List<TLVObject> tLVObjects;
        List<TLVObject> tLVObjects2 = ApduHandler.getTLVObjects(HexUtil.encodeHexStr(bArr));
        if (tLVObjects2 != null && tLVObjects2.size() == 1 && (tLVObjects = ApduHandler.getTLVObjects(tLVObjects2.get(0).getSvalue())) != null && tLVObjects.size() > 0) {
            for (TLVObject next : tLVObjects) {
                String stag = next.getStag();
                stag.hashCode();
                char c = 65535;
                switch (stag.hashCode()) {
                    case 1537:
                        if (stag.equals("01")) {
                            c = 0;
                            break;
                        }
                        break;
                    case 1784:
                        if (stag.equals("80")) {
                            c = 1;
                            break;
                        }
                        break;
                    case 1817:
                        if (stag.equals("92")) {
                            c = 2;
                            break;
                        }
                        break;
                }
                switch (c) {
                    case 0:
                        this.ccRequiredFlag = !"00".equals(next.getSvalue());
                        break;
                    case 1:
                        this.transactionId = next.getSvalue();
                        break;
                    case 2:
                        this.bppEuiccOtpk = new String(HexUtil.decodeHex(next.getSvalue()));
                        break;
                }
            }
        }
    }

    public String getTransactionId() {
        return this.transactionId;
    }

    public void setTransactionId(String str) {
        this.transactionId = str;
    }

    public boolean isCcRequiredFlag() {
        return this.ccRequiredFlag;
    }

    public void setCcRequiredFlag(boolean z) {
        this.ccRequiredFlag = z;
    }

    public String getBppEuiccOtpk() {
        return this.bppEuiccOtpk;
    }

    public void setBppEuiccOtpk(String str) {
        this.bppEuiccOtpk = str;
    }

    public String toString() {
        return "SmdpSigned2{transactionId='" + this.transactionId + '\'' + ", ccRequiredFlag=" + this.ccRequiredFlag + ", bppEuiccOtpk='" + this.bppEuiccOtpk + '\'' + '}';
    }
}
