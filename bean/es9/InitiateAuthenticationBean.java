package com.eastcompeace.lpa.sdk.bean.es9;

import com.eastcompeace.lpa.sdk.bean.BaseBean;
import com.eastcompeace.lpa.sdk.utils.HexUtil;

public class InitiateAuthenticationBean extends BaseBean {
    private byte[] euiccCiPKIdTobeUsed;
    private byte[] serverCertificate;
    private byte[] serverSignature1;
    private byte[] serverSigned1;
    private String transactionId;

    public String getTransactionId() {
        return this.transactionId;
    }

    public void setTransactionId(String str) {
        this.transactionId = str;
    }

    public byte[] getServerSigned1() {
        return this.serverSigned1;
    }

    public void setServerSigned1(byte[] bArr) {
        this.serverSigned1 = bArr;
    }

    public byte[] getServerSignature1() {
        return this.serverSignature1;
    }

    public void setServerSignature1(byte[] bArr) {
        this.serverSignature1 = bArr;
    }

    public byte[] getEuiccCiPKIdTobeUsed() {
        return this.euiccCiPKIdTobeUsed;
    }

    public void setEuiccCiPKIdTobeUsed(byte[] bArr) {
        this.euiccCiPKIdTobeUsed = bArr;
    }

    public byte[] getServerCertificate() {
        return this.serverCertificate;
    }

    public void setServerCertificate(byte[] bArr) {
        this.serverCertificate = bArr;
    }

    public String getApdu() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(HexUtil.encodeHexStr(this.serverSigned1));
        stringBuffer.append(HexUtil.encodeHexStr(this.serverSignature1));
        stringBuffer.append(HexUtil.encodeHexStr(this.euiccCiPKIdTobeUsed));
        stringBuffer.append(HexUtil.encodeHexStr(this.serverCertificate));
        return stringBuffer.toString();
    }

    public String toString() {
        return "InitiateAuthenticationBean{transactionId='" + this.transactionId + '\'' + ", serverSigned1=" + HexUtil.encodeHexStr(this.serverSigned1) + ", serverSignature1=" + HexUtil.encodeHexStr(this.serverSignature1) + ", euiccCiPKIdTobeUsed=" + HexUtil.encodeHexStr(this.euiccCiPKIdTobeUsed) + ", serverCertificate=" + HexUtil.encodeHexStr(this.serverCertificate) + '}';
    }
}
