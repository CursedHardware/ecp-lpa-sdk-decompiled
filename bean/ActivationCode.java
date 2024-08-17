package com.eastcompeace.lpa.sdk.bean;

public class ActivationCode extends BaseSerializableBean {
    private String ACFormat;
    private String ACToken;
    private String SMDPAddress;
    private String SMDPOID;
    private String confirmationCodeRequiredFlag;

    public ActivationCode() {
        this.ACToken = "";
    }

    public ActivationCode(String str, String str2, String str3, String str4, String str5) {
        this.ACFormat = str;
        this.SMDPAddress = getstandardSMDPAddress(str2);
        this.ACToken = str3;
        this.SMDPOID = str4;
        this.confirmationCodeRequiredFlag = str5;
    }

    public String getstandardSMDPAddress(String str) {
        if (str.toLowerCase().startsWith("https://")) {
            return str.substring(8, str.length());
        }
        return str.toLowerCase().startsWith("http://") ? str.substring(7, str.length()) : str;
    }

    public ActivationCode(String str) {
        String[] split = str.trim().split("\\$");
        for (int i = 0; i < split.length; i++) {
            if (i == 0) {
                this.ACFormat = split[0];
            } else if (i == 1) {
                this.SMDPAddress = split[1];
            } else if (i == 2) {
                this.ACToken = split[2];
            } else if (i == 3) {
                this.SMDPOID = split[3];
            } else if (i == 4) {
                this.confirmationCodeRequiredFlag = split[4];
            }
        }
    }

    public String getACFormat() {
        return this.ACFormat;
    }

    public void setACFormat(String str) {
        this.ACFormat = str;
    }

    public String getSMDPAddress() {
        return this.SMDPAddress;
    }

    public void setSMDPAddress(String str) {
        this.SMDPAddress = getstandardSMDPAddress(str);
    }

    public String getACToken() {
        return this.ACToken;
    }

    public void setACToken(String str) {
        this.ACToken = str;
    }

    public String getSMDPOID() {
        return this.SMDPOID;
    }

    public void setSMDPOID(String str) {
        this.SMDPOID = str;
    }

    public String getConfirmationCodeRequiredFlag() {
        return this.confirmationCodeRequiredFlag;
    }

    public void setConfirmationCodeRequiredFlag(String str) {
        this.confirmationCodeRequiredFlag = str;
    }

    public String toString() {
        return "ActivationCode{ACFormat='" + this.ACFormat + '\'' + ", SMDPAddress='" + this.SMDPAddress + '\'' + ", ACToken='" + this.ACToken + '\'' + ", SMDPOID='" + this.SMDPOID + '\'' + ", confirmationCodeRequiredFlag='" + this.confirmationCodeRequiredFlag + '\'' + '}';
    }
}
