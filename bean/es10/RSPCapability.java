package com.eastcompeace.lpa.sdk.bean.es10;

import com.eastcompeace.lpa.sdk.bean.BaseSerializableBean;
import com.eastcompeace.lpa.sdk.utils.HexUtil;

public class RSPCapability extends BaseSerializableBean {
    private int additionalProfile;
    private int crlSupport;
    private int deviceInfoExtensibilitySupport;
    private int rpmSupport;
    private int testProfileSupport;

    private int chartToint(char c) {
        return c - '0';
    }

    public RSPCapability(String str) {
        char[] charArray = HexUtil.hexString2binaryString(str.substring(2, str.length())).toCharArray();
        int length = charArray.length - 1;
        int i = 0;
        this.additionalProfile = length > 0 ? chartToint(charArray[0]) : 0;
        this.crlSupport = 1 < length ? chartToint(charArray[1]) : 0;
        this.rpmSupport = 2 < length ? chartToint(charArray[2]) : 0;
        this.testProfileSupport = 3 < length ? chartToint(charArray[3]) : 0;
        this.deviceInfoExtensibilitySupport = 4 < length ? chartToint(charArray[4]) : i;
    }

    public int getAdditionalProfile() {
        return this.additionalProfile;
    }

    public void setAdditionalProfile(int i) {
        this.additionalProfile = i;
    }

    public int getCrlSupport() {
        return this.crlSupport;
    }

    public void setCrlSupport(int i) {
        this.crlSupport = i;
    }

    public int getRpmSupport() {
        return this.rpmSupport;
    }

    public void setRpmSupport(int i) {
        this.rpmSupport = i;
    }

    public int getTestProfileSupport() {
        return this.testProfileSupport;
    }

    public void setTestProfileSupport(int i) {
        this.testProfileSupport = i;
    }

    public int getDeviceInfoExtensibilitySupport() {
        return this.deviceInfoExtensibilitySupport;
    }

    public void setDeviceInfoExtensibilitySupport(int i) {
        this.deviceInfoExtensibilitySupport = i;
    }
}
