package com.eastcompeace.lpa.sdk.bean.es9;

import com.eastcompeace.lpa.sdk.bean.BaseSerializableBean;
import java.util.Arrays;

public class GetBoundProfilePackageBean extends BaseSerializableBean {
    private byte[] boundProfilePackage;
    private String transactionId;

    public String getTransactionId() {
        return this.transactionId;
    }

    public void setTransactionId(String str) {
        this.transactionId = str;
    }

    public byte[] getBoundProfilePackage() {
        return this.boundProfilePackage;
    }

    public void setBoundProfilePackage(byte[] bArr) {
        this.boundProfilePackage = bArr;
    }

    public String toString() {
        return "GetBoundProfilePackageBean{transactionId='" + this.transactionId + '\'' + ", boundProfilePackage=" + Arrays.toString(this.boundProfilePackage) + '}';
    }
}
