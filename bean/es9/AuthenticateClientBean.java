package com.eastcompeace.lpa.sdk.bean.es9;

import com.eastcompeace.lpa.sdk.bean.BaseSerializableBean;
import com.eastcompeace.lpa.sdk.bean.es10.StoreMetadataRequest;
import com.eastcompeace.lpa.sdk.utils.HexUtil;

public class AuthenticateClientBean extends BaseSerializableBean {
    private byte[] profileMetadata;
    private byte[] smdpCertificate;
    private byte[] smdpSignature2;
    private byte[] smdpSigned2;
    private SmdpSigned2 smdpSignedTwo;
    private String transactionId;

    public AuthenticateClientBean() {
    }

    public AuthenticateClientBean(String str, byte[] bArr, byte[] bArr2, byte[] bArr3, byte[] bArr4) {
        this.transactionId = str;
        this.profileMetadata = bArr;
        this.smdpSigned2 = bArr2;
        this.smdpSignature2 = bArr3;
        this.smdpCertificate = bArr4;
    }

    public StoreMetadataRequest getStoreMetadata() {
        return new StoreMetadataRequest(HexUtil.encodeHexStr(this.profileMetadata));
    }

    public String getTransactionId() {
        return this.transactionId;
    }

    public void setTransactionId(String str) {
        this.transactionId = str;
    }

    public byte[] getProfileMetadata() {
        return this.profileMetadata;
    }

    public void setProfileMetadata(byte[] bArr) {
        this.profileMetadata = bArr;
    }

    public byte[] getSmdpSigned2() {
        return this.smdpSigned2;
    }

    public void setSmdpSigned2(byte[] bArr) {
        this.smdpSigned2 = bArr;
    }

    public byte[] getSmdpSignature2() {
        return this.smdpSignature2;
    }

    public void setSmdpSignature2(byte[] bArr) {
        this.smdpSignature2 = bArr;
    }

    public byte[] getSmdpCertificate() {
        return this.smdpCertificate;
    }

    public void setSmdpCertificate(byte[] bArr) {
        this.smdpCertificate = bArr;
    }

    public SmdpSigned2 getSmdpSigned2Obj() {
        if (this.smdpSignedTwo == null) {
            this.smdpSignedTwo = new SmdpSigned2(this.smdpSigned2);
        }
        return this.smdpSignedTwo;
    }

    public void getApdu() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(this.smdpSigned2);
        stringBuffer.append(this.smdpSigned2);
        stringBuffer.append(this.smdpSigned2);
    }

    public String toString() {
        return "AuthenticateClientBean{transactionId='" + this.transactionId + '\'' + ", profileMetadata=" + getStoreMetadata().toString() + ", smdpSigned2=" + HexUtil.encodeHexStr(this.smdpSigned2) + '\'' + getSmdpSigned2Obj().toString() + ", smdpSignature2=" + HexUtil.encodeHexStr(this.smdpSignature2) + ", smdpCertificate=" + HexUtil.encodeHexStr(this.smdpCertificate) + '}';
    }
}
