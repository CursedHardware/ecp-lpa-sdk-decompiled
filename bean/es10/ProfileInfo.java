package com.eastcompeace.lpa.sdk.bean.es10;

import com.eastcompeace.lpa.sdk.bean.BaseSerializableBean;
import com.eastcompeace.lpa.sdk.utils.HexUtil;
import com.eastcompeace.lpa.sdk.utils.StringUtils;
import java.util.List;
import org.android.agoo.message.MessageService;

public class ProfileInfo extends BaseSerializableBean {
    private String dpProprietaryData;
    private String eid;
    private String iccid;
    private String icon;
    private String iconType;
    private String imsi;
    private boolean isApduIccid;
    private String isdpAid;
    private String matchingId;
    private String msisdn;
    private String policyRules;
    private String profileClass;
    private String profileName;
    private String profileNickname;
    private Operator profileOwner;
    private int profileState;
    private String profileType;
    private String serviceProviderName;
    private String templateId;

    public ProfileInfo() {
        this.isApduIccid = false;
    }

    public ProfileInfo(List<TLVObject> list) {
        for (TLVObject next : list) {
            String stag = next.getStag();
            char c = 65535;
            int hashCode = stag.hashCode();
            if (hashCode != 1682) {
                if (hashCode != 1708) {
                    if (hashCode != 1824) {
                        if (hashCode != 1767110) {
                            switch (hashCode) {
                                case 1815:
                                    if (stag.equals("90")) {
                                        c = 3;
                                        break;
                                    }
                                    break;
                                case 1816:
                                    if (stag.equals("91")) {
                                        c = 4;
                                        break;
                                    }
                                    break;
                                case 1817:
                                    if (stag.equals("92")) {
                                        c = 5;
                                        break;
                                    }
                                    break;
                                case 1818:
                                    if (stag.equals("93")) {
                                        c = 6;
                                        break;
                                    }
                                    break;
                                case 1819:
                                    if (stag.equals("94")) {
                                        c = 7;
                                        break;
                                    }
                                    break;
                                case 1820:
                                    if (stag.equals("95")) {
                                        c = 8;
                                        break;
                                    }
                                    break;
                                default:
                                    switch (hashCode) {
                                        case 2100:
                                            if (stag.equals("B6")) {
                                                c = 9;
                                                break;
                                            }
                                            break;
                                        case 2101:
                                            if (stag.equals("B7")) {
                                                c = 10;
                                                break;
                                            }
                                            break;
                                        case 2102:
                                            if (stag.equals("B8")) {
                                                c = 11;
                                                break;
                                            }
                                            break;
                                    }
                            }
                        } else if (stag.equals("9F70")) {
                            c = 2;
                        }
                    } else if (stag.equals("99")) {
                        c = 12;
                    }
                } else if (stag.equals("5A")) {
                    c = 0;
                }
            } else if (stag.equals("4F")) {
                c = 1;
            }
            switch (c) {
                case 0:
                    this.isApduIccid = true;
                    this.iccid = getShowIccid(next.getSvalue());
                    break;
                case 1:
                    this.isdpAid = next.getSvalue();
                    break;
                case 2:
                    if (!"00".equals(next.getSvalue())) {
                        this.profileState = 1;
                        break;
                    } else {
                        this.profileState = 0;
                        break;
                    }
                case 3:
                    this.profileNickname = new String(HexUtil.decodeHex(next.getSvalue()));
                    break;
                case 4:
                    this.serviceProviderName = new String(HexUtil.decodeHex(next.getSvalue()));
                    break;
                case 5:
                    this.profileName = new String(HexUtil.decodeHex(next.getSvalue()));
                    break;
                case 6:
                    if (!MessageService.MSG_DB_READY_REPORT.equals(next.getSvalue())) {
                        this.iconType = "png";
                        break;
                    } else {
                        this.iconType = "jpg";
                        break;
                    }
                case 7:
                    this.icon = next.getSvalue();
                    break;
                case 8:
                    this.profileClass = next.getSvalue();
                    break;
            }
        }
    }

    public static String getShowIccid(String str) {
        if (StringUtils.isEmpty(str)) {
            return "";
        }
        char[] charArray = str.toCharArray();
        int length = charArray.length;
        int i = charArray[length + -2] == 'F' ? length - 1 : length;
        char[] cArr = new char[i];
        for (int i2 = 0; i2 < length; i2++) {
            int i3 = i2 % 2 == 0 ? i2 + 1 : i2 - 1;
            if (i3 < i) {
                cArr[i3] = charArray[i2];
            }
        }
        return String.valueOf(cArr);
    }

    public static String getApduIccid(String str) {
        if (StringUtils.isEmpty(str)) {
            return "";
        }
        char[] charArray = str.toCharArray();
        int length = charArray.length;
        int i = length % 2;
        int i2 = i == 0 ? length : length + 1;
        char[] cArr = new char[i2];
        for (int i3 = 0; i3 < length; i3++) {
            cArr[i3 % 2 == 0 ? i3 + 1 : i3 - 1] = charArray[i3];
        }
        if (i != 0) {
            cArr[i2 - 2] = 'F';
        }
        return String.valueOf(cArr);
    }

    public String getEid() {
        return this.eid;
    }

    public void setEid(String str) {
        this.eid = str;
    }

    public String getIccid() {
        return this.iccid;
    }

    public void setIccid(String str) {
        this.iccid = str;
    }

    public String getImsi() {
        return this.imsi;
    }

    public void setImsi(String str) {
        this.imsi = str;
    }

    public String getMsisdn() {
        return this.msisdn;
    }

    public void setMsisdn(String str) {
        this.msisdn = str;
    }

    public String getMatchingId() {
        return this.matchingId;
    }

    public void setMatchingId(String str) {
        this.matchingId = str;
    }

    public String getIsdpAid() {
        return this.isdpAid;
    }

    public void setIsdpAid(String str) {
        this.isdpAid = str;
    }

    public int getProfileState() {
        return this.profileState;
    }

    public void setProfileState(int i) {
        this.profileState = i;
    }

    public String getProfileNickname() {
        return this.profileNickname;
    }

    public void setProfileNickname(String str) {
        this.profileNickname = str;
    }

    public String getServiceProviderName() {
        return this.serviceProviderName;
    }

    public void setServiceProviderName(String str) {
        this.serviceProviderName = str;
    }

    public String getProfileName() {
        return this.profileName;
    }

    public void setProfileName(String str) {
        this.profileName = str;
    }

    public String getIconType() {
        return this.iconType;
    }

    public void setIconType(String str) {
        this.iconType = str;
    }

    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String str) {
        this.icon = str;
    }

    public String getProfileClass() {
        return this.profileClass;
    }

    public void setProfileClass(String str) {
        this.profileClass = str;
    }

    public Operator getProfileOwner() {
        return this.profileOwner;
    }

    public void setProfileOwner(Operator operator) {
        this.profileOwner = operator;
    }

    public String getPolicyRules() {
        return this.policyRules;
    }

    public void setPolicyRules(String str) {
        this.policyRules = str;
    }

    public String getDpProprietaryData() {
        return this.dpProprietaryData;
    }

    public void setDpProprietaryData(String str) {
        this.dpProprietaryData = str;
    }

    public String getTemplateId() {
        return this.templateId;
    }

    public void setTemplateId(String str) {
        this.templateId = str;
    }

    public String getProfileType() {
        return this.profileType;
    }

    public void setProfileType(String str) {
        this.profileType = str;
    }

    public String toString() {
        return "ProfileInfo{eid='" + this.eid + '\'' + ", iccid='" + this.iccid + '\'' + ", imsi='" + this.imsi + '\'' + ", msisdn='" + this.msisdn + '\'' + ", matchingId='" + this.matchingId + '\'' + ", isdpAid='" + this.isdpAid + '\'' + ", profileState='" + this.profileState + '\'' + ", profileNickname='" + this.profileNickname + '\'' + ", serviceProviderName='" + this.serviceProviderName + '\'' + ", profileName='" + this.profileName + '\'' + ", iconType=" + this.iconType + ", icon='" + this.icon + '\'' + ", profileClass=" + this.profileClass + ", profileOwner=" + this.profileOwner + ", policyRules='" + this.policyRules + '\'' + ", dpProprietaryData='" + this.dpProprietaryData + '\'' + ", templateId='" + this.templateId + '\'' + ", profileType='" + this.profileType + '\'' + '}';
    }
}
