package com.eastcompeace.lpa.sdk.fup;

public class PCSCResponse {
    private String bytes;
    private String data;
    private int dataLen;
    private String sw;
    private String sw1;
    private String sw2;

    public PCSCResponse(String str) {
    }

    public int getDataLen() {
        return this.dataLen;
    }

    public void setDataLen(int i) {
        this.dataLen = i;
    }

    public String getSw() {
        return this.sw;
    }

    public void setSw(String str) {
        this.sw = str;
    }

    public String getSw1() {
        return this.sw1;
    }

    public void setSw1(String str) {
        this.sw1 = str;
    }

    public String getSw2() {
        return this.sw2;
    }

    public void setSw2(String str) {
        this.sw2 = str;
    }

    public String getData() {
        return this.data;
    }

    public void setData(String str) {
        this.data = str;
    }

    public String getBytes() {
        return this.bytes;
    }

    public void setBytes(String str) {
        this.bytes = str;
    }
}
