package com.eastcompeace.lpa.sdk.bean.es10;

import com.eastcompeace.lpa.sdk.bean.BaseSerializableBean;
import com.eastcompeace.lpa.sdk.utils.HexUtil;

public class UiccCapability extends BaseSerializableBean {
    private int akaCave;
    private int akaMilenage;
    private int akaTuak128;
    private int akaTuak256;
    private int berTlvFileSupport;
    private int contactlessSupport;
    private int csimSupport;
    private int dfLinkSupport;
    private int eapClient;
    private int gbaAuthenISim;
    private int gbaAuthenUcatTpsim;
    private int gbaAuthenUsim;
    private int getIdentity;
    private int isimSupport;
    private int javacard;
    private int mbmsAuthenUsim;
    private int multipleCsimSupport;
    private int multipleIsimSupport;
    private int multipleUsimSupport;
    private int multos;
    private int profile_a_x25519;
    private int profile_b_p256;
    private int rfu1;
    private int rfu2;
    private int suciCalculatorApi;
    private int usimSupport;

    private int chartToint(char c) {
        return c - '0';
    }

    public UiccCapability(String str) {
        char[] charArray = HexUtil.hexString2binaryString(str.substring(2, str.length())).toCharArray();
        int length = charArray.length - 1;
        int i = 0;
        this.contactlessSupport = length > 0 ? chartToint(charArray[0]) : 0;
        this.usimSupport = 1 < length ? chartToint(charArray[1]) : 0;
        this.isimSupport = 2 < length ? chartToint(charArray[2]) : 0;
        this.csimSupport = 3 < length ? chartToint(charArray[3]) : 0;
        this.akaMilenage = 4 < length ? chartToint(charArray[4]) : 0;
        this.akaCave = 5 < length ? chartToint(charArray[5]) : 0;
        this.akaTuak128 = 6 < length ? chartToint(charArray[6]) : 0;
        this.akaTuak256 = 7 < length ? chartToint(charArray[7]) : 0;
        this.rfu1 = 8 < length ? chartToint(charArray[8]) : 0;
        this.rfu2 = 9 < length ? chartToint(charArray[9]) : 0;
        this.gbaAuthenUsim = 10 < length ? chartToint(charArray[10]) : 0;
        this.gbaAuthenISim = 11 < length ? chartToint(charArray[11]) : 0;
        this.mbmsAuthenUsim = 12 < length ? chartToint(charArray[12]) : 0;
        this.eapClient = 13 < length ? chartToint(charArray[13]) : 0;
        this.javacard = 14 < length ? chartToint(charArray[14]) : 0;
        this.multos = 15 < length ? chartToint(charArray[15]) : 0;
        this.multipleUsimSupport = 16 < length ? chartToint(charArray[16]) : 0;
        this.multipleIsimSupport = 17 < length ? chartToint(charArray[17]) : 0;
        this.multipleCsimSupport = 18 < length ? chartToint(charArray[18]) : 0;
        this.berTlvFileSupport = 19 < length ? chartToint(charArray[19]) : 0;
        this.dfLinkSupport = 20 < length ? chartToint(charArray[20]) : 0;
        this.gbaAuthenUcatTpsim = 21 < length ? chartToint(charArray[21]) : 0;
        this.getIdentity = 22 < length ? chartToint(charArray[22]) : 0;
        this.profile_a_x25519 = 23 < length ? chartToint(charArray[23]) : 0;
        this.profile_b_p256 = 24 < length ? chartToint(charArray[24]) : 0;
        this.suciCalculatorApi = 25 < length ? chartToint(charArray[25]) : i;
    }

    public int getContactlessSupport() {
        return this.contactlessSupport;
    }

    public void setContactlessSupport(int i) {
        this.contactlessSupport = i;
    }

    public int getUsimSupport() {
        return this.usimSupport;
    }

    public void setUsimSupport(int i) {
        this.usimSupport = i;
    }

    public int getIsimSupport() {
        return this.isimSupport;
    }

    public void setIsimSupport(int i) {
        this.isimSupport = i;
    }

    public int getCsimSupport() {
        return this.csimSupport;
    }

    public void setCsimSupport(int i) {
        this.csimSupport = i;
    }

    public int getAkaMilenage() {
        return this.akaMilenage;
    }

    public void setAkaMilenage(int i) {
        this.akaMilenage = i;
    }

    public int getAkaCave() {
        return this.akaCave;
    }

    public void setAkaCave(int i) {
        this.akaCave = i;
    }

    public int getAkaTuak128() {
        return this.akaTuak128;
    }

    public void setAkaTuak128(int i) {
        this.akaTuak128 = i;
    }

    public int getAkaTuak256() {
        return this.akaTuak256;
    }

    public void setAkaTuak256(int i) {
        this.akaTuak256 = i;
    }

    public int getRfu1() {
        return this.rfu1;
    }

    public void setRfu1(int i) {
        this.rfu1 = i;
    }

    public int getRfu2() {
        return this.rfu2;
    }

    public void setRfu2(int i) {
        this.rfu2 = i;
    }

    public int getGbaAuthenUsim() {
        return this.gbaAuthenUsim;
    }

    public void setGbaAuthenUsim(int i) {
        this.gbaAuthenUsim = i;
    }

    public int getGbaAuthenISim() {
        return this.gbaAuthenISim;
    }

    public void setGbaAuthenISim(int i) {
        this.gbaAuthenISim = i;
    }

    public int getMbmsAuthenUsim() {
        return this.mbmsAuthenUsim;
    }

    public void setMbmsAuthenUsim(int i) {
        this.mbmsAuthenUsim = i;
    }

    public int getEapClient() {
        return this.eapClient;
    }

    public void setEapClient(int i) {
        this.eapClient = i;
    }

    public int getJavacard() {
        return this.javacard;
    }

    public void setJavacard(int i) {
        this.javacard = i;
    }

    public int getMultos() {
        return this.multos;
    }

    public void setMultos(int i) {
        this.multos = i;
    }

    public int getMultipleUsimSupport() {
        return this.multipleUsimSupport;
    }

    public void setMultipleUsimSupport(int i) {
        this.multipleUsimSupport = i;
    }

    public int getMultipleIsimSupport() {
        return this.multipleIsimSupport;
    }

    public void setMultipleIsimSupport(int i) {
        this.multipleIsimSupport = i;
    }

    public int getMultipleCsimSupport() {
        return this.multipleCsimSupport;
    }

    public void setMultipleCsimSupport(int i) {
        this.multipleCsimSupport = i;
    }

    public int getBerTlvFileSupport() {
        return this.berTlvFileSupport;
    }

    public void setBerTlvFileSupport(int i) {
        this.berTlvFileSupport = i;
    }

    public int getDfLinkSupport() {
        return this.dfLinkSupport;
    }

    public void setDfLinkSupport(int i) {
        this.dfLinkSupport = i;
    }

    public int getGbaAuthenUcatTpsim() {
        return this.gbaAuthenUcatTpsim;
    }

    public void setGbaAuthenUcatTpsim(int i) {
        this.gbaAuthenUcatTpsim = i;
    }

    public int getGetIdentity() {
        return this.getIdentity;
    }

    public void setGetIdentity(int i) {
        this.getIdentity = i;
    }

    public int getProfile_a_x25519() {
        return this.profile_a_x25519;
    }

    public void setProfile_a_x25519(int i) {
        this.profile_a_x25519 = i;
    }

    public int getProfile_b_p256() {
        return this.profile_b_p256;
    }

    public void setProfile_b_p256(int i) {
        this.profile_b_p256 = i;
    }

    public int getSuciCalculatorApi() {
        return this.suciCalculatorApi;
    }

    public void setSuciCalculatorApi(int i) {
        this.suciCalculatorApi = i;
    }

    public String toString() {
        return "UiccCapability{contactlessSupport=" + this.contactlessSupport + ", usimSupport=" + this.usimSupport + ", isimSupport=" + this.isimSupport + ", csimSupport=" + this.csimSupport + ", akaMilenage=" + this.akaMilenage + ", akaCave=" + this.akaCave + ", akaTuak128=" + this.akaTuak128 + ", akaTuak256=" + this.akaTuak256 + ", rfu1=" + this.rfu1 + ", rfu2=" + this.rfu2 + ", gbaAuthenUsim=" + this.gbaAuthenUsim + ", gbaAuthenISim=" + this.gbaAuthenISim + ", mbmsAuthenUsim=" + this.mbmsAuthenUsim + ", eapClient=" + this.eapClient + ", javacard=" + this.javacard + ", multos=" + this.multos + ", multipleUsimSupport=" + this.multipleUsimSupport + ", multipleIsimSupport=" + this.multipleIsimSupport + ", multipleCsimSupport=" + this.multipleCsimSupport + ", berTlvFileSupport=" + this.berTlvFileSupport + ", dfLinkSupport=" + this.dfLinkSupport + ", gbaAuthenUcatTpsim=" + this.gbaAuthenUcatTpsim + ", getIdentity=" + this.getIdentity + ", profile_a_x25519=" + this.profile_a_x25519 + ", profile_b_p256=" + this.profile_b_p256 + ", suciCalculatorApi=" + this.suciCalculatorApi + '}';
    }
}
