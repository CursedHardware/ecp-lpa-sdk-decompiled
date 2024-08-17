package com.eastcompeace.lpa.sdk.bean.es10;

import com.eastcompeace.lpa.sdk.bean.BaseSerializableBean;
import com.eastcompeace.lpa.sdk.utils.ApduHandler;
import com.eastcompeace.lpa.sdk.utils.HexUtil;
import com.eastcompeace.lpa.sdk.utils.StringUtils;
import java.util.List;

public class EuiccInfo2 extends BaseSerializableBean {
    private String FreeNonVolatileMemory;
    private String FreeVolatileMemory;
    private String NumberOfInstalledApplication;
    private String discoveryBaseURL;
    private String euiccCategory;
    private String euiccCiPKIdForSign;
    private String euiccCiPKIdForVerify;
    private String euiccFirmwareVer;
    private PprIds forbiddenProfilePolicyRules;
    private String globalplatformVersion;
    private String platformLabel;
    private String ppVersion;
    private String profileVersion;
    private RSPCapability rspCapability;
    private String sasAcreditationNumber;
    private String svn;
    private String treProductReference;
    private String treProperties;
    private String ts102241Version;
    private UiccCapability uiccCapability;

    public EuiccInfo2(String str) {
        List<TLVObject> childrenTLV;
        if (!StringUtils.isEmpty(str)) {
            List<TLVObject> tLVByTag = ApduHandler.getTLVByTag("BF22", str, true);
            if ((tLVByTag == null || tLVByTag.size() == 1) && (childrenTLV = tLVByTag.get(0).getChildrenTLV()) != null) {
                for (TLVObject next : childrenTLV) {
                    String stag = next.getStag();
                    stag.hashCode();
                    char c = 65535;
                    switch (stag.hashCode()) {
                        case 1540:
                            if (stag.equals("04")) {
                                c = 0;
                                break;
                            }
                            break;
                        case 1555:
                            if (stag.equals("0C")) {
                                c = 1;
                                break;
                            }
                            break;
                        case 1785:
                            if (stag.equals("81")) {
                                c = 2;
                                break;
                            }
                            break;
                        case 1786:
                            if (stag.equals("82")) {
                                c = 3;
                                break;
                            }
                            break;
                        case 1787:
                            if (stag.equals("83")) {
                                c = 4;
                                break;
                            }
                            break;
                        case 1789:
                            if (stag.equals("85")) {
                                c = 5;
                                break;
                            }
                            break;
                        case 1790:
                            if (stag.equals("86")) {
                                c = 6;
                                break;
                            }
                            break;
                        case 1791:
                            if (stag.equals("87")) {
                                c = 7;
                                break;
                            }
                            break;
                        case 1792:
                            if (stag.equals("88")) {
                                c = 8;
                                break;
                            }
                            break;
                        case 1802:
                            if (stag.equals("8B")) {
                                c = 9;
                                break;
                            }
                            break;
                        case 1824:
                            if (stag.equals("99")) {
                                c = 10;
                                break;
                            }
                            break;
                        case 2067:
                            if (stag.equals("A4")) {
                                c = 11;
                                break;
                            }
                            break;
                        case 2072:
                            if (stag.equals("A9")) {
                                c = 12;
                                break;
                            }
                            break;
                        case 2080:
                            if (stag.equals("AA")) {
                                c = 13;
                                break;
                            }
                            break;
                        case 2082:
                            if (stag.equals("AC")) {
                                c = 14;
                                break;
                            }
                            break;
                        case 2083:
                            if (stag.equals("AD")) {
                                c = 15;
                                break;
                            }
                            break;
                        case 2084:
                            if (stag.equals("AE")) {
                                c = 16;
                                break;
                            }
                            break;
                    }
                    switch (c) {
                        case 0:
                            this.ppVersion = next.getSvalue();
                            break;
                        case 1:
                            this.sasAcreditationNumber = new String(HexUtil.decodeHex(next.getSvalue()));
                            break;
                        case 2:
                            this.profileVersion = next.getSvalue();
                            break;
                        case 3:
                            this.svn = next.getSvalue();
                            break;
                        case 4:
                            this.euiccFirmwareVer = next.getSvalue();
                            break;
                        case 5:
                            this.uiccCapability = new UiccCapability(next.getSvalue());
                            break;
                        case 6:
                            this.ts102241Version = next.getSvalue();
                            break;
                        case 7:
                            this.globalplatformVersion = next.getSvalue();
                            break;
                        case 8:
                            this.rspCapability = new RSPCapability(next.getSvalue());
                            break;
                        case 9:
                            this.euiccCategory = next.getSvalue();
                            break;
                        case 10:
                            this.forbiddenProfilePolicyRules = new PprIds(next.getSvalue());
                            break;
                        case 11:
                            for (TLVObject next2 : next.getChildrenTLV()) {
                                if ("81".equals(next2.getStag())) {
                                    this.NumberOfInstalledApplication = next2.getSvalue();
                                }
                                if ("82".equals(next2.getStag())) {
                                    this.FreeNonVolatileMemory = next2.getSvalue();
                                }
                                if ("83".equals(next2.getStag())) {
                                    this.FreeVolatileMemory = next2.getSvalue();
                                }
                            }
                            break;
                        case 12:
                            for (TLVObject next3 : next.getChildrenTLV()) {
                                if ("04".equals(next3.getStag())) {
                                    this.euiccCiPKIdForVerify = next3.getSvalue();
                                }
                            }
                            break;
                        case 13:
                            for (TLVObject next4 : next.getChildrenTLV()) {
                                if ("04".equals(next4.getStag())) {
                                    this.euiccCiPKIdForSign = next4.getSvalue();
                                }
                            }
                            break;
                        case 14:
                            for (TLVObject next5 : next.getChildrenTLV()) {
                                if ("80".equals(next5.getStag())) {
                                    this.platformLabel = new String(HexUtil.decodeHex(next5.getSvalue()));
                                }
                                if ("81".equals(next5.getStag())) {
                                    this.discoveryBaseURL = new String(HexUtil.decodeHex(next5.getSvalue()));
                                }
                            }
                            break;
                        case 15:
                            this.treProperties = next.getSvalue();
                            break;
                        case 16:
                            this.treProductReference = next.getSvalue();
                            break;
                    }
                }
            }
        }
    }

    public String getProfileVersion() {
        return this.profileVersion;
    }

    public void setProfileVersion(String str) {
        this.profileVersion = str;
    }

    public String getSvn() {
        return this.svn;
    }

    public void setSvn(String str) {
        this.svn = str;
    }

    public String getEuiccFirmwareVer() {
        return this.euiccFirmwareVer;
    }

    public void setEuiccFirmwareVer(String str) {
        this.euiccFirmwareVer = str;
    }

    public String getNumberOfInstalledApplication() {
        return this.NumberOfInstalledApplication;
    }

    public void setNumberOfInstalledApplication(String str) {
        this.NumberOfInstalledApplication = str;
    }

    public String getFreeNonVolatileMemory() {
        return this.FreeNonVolatileMemory;
    }

    public void setFreeNonVolatileMemory(String str) {
        this.FreeNonVolatileMemory = str;
    }

    public String getFreeVolatileMemory() {
        return this.FreeVolatileMemory;
    }

    public void setFreeVolatileMemory(String str) {
        this.FreeVolatileMemory = str;
    }

    public UiccCapability getUiccCapability() {
        return this.uiccCapability;
    }

    public void setUiccCapability(UiccCapability uiccCapability2) {
        this.uiccCapability = uiccCapability2;
    }

    public String getTs102241Version() {
        return this.ts102241Version;
    }

    public void setTs102241Version(String str) {
        this.ts102241Version = str;
    }

    public String getGlobalplatformVersion() {
        return this.globalplatformVersion;
    }

    public void setGlobalplatformVersion(String str) {
        this.globalplatformVersion = str;
    }

    public RSPCapability getRspCapability() {
        return this.rspCapability;
    }

    public void setRspCapability(RSPCapability rSPCapability) {
        this.rspCapability = rSPCapability;
    }

    public String getEuiccCiPKIdForVerify() {
        return this.euiccCiPKIdForVerify;
    }

    public void setEuiccCiPKIdForVerify(String str) {
        this.euiccCiPKIdForVerify = str;
    }

    public String getEuiccCiPKIdForSign() {
        return this.euiccCiPKIdForSign;
    }

    public void setEuiccCiPKIdForSign(String str) {
        this.euiccCiPKIdForSign = str;
    }

    public String getEuiccCategory() {
        return this.euiccCategory;
    }

    public void setEuiccCategory(String str) {
        this.euiccCategory = str;
    }

    public PprIds getForbiddenProfilePolicyRules() {
        return this.forbiddenProfilePolicyRules;
    }

    public void setForbiddenProfilePolicyRules(PprIds pprIds) {
        this.forbiddenProfilePolicyRules = pprIds;
    }

    public String getPpVersion() {
        return this.ppVersion;
    }

    public void setPpVersion(String str) {
        this.ppVersion = str;
    }

    public String getSasAcreditationNumber() {
        return this.sasAcreditationNumber;
    }

    public void setSasAcreditationNumber(String str) {
        this.sasAcreditationNumber = str;
    }

    public String getPlatformLabel() {
        return this.platformLabel;
    }

    public void setPlatformLabel(String str) {
        this.platformLabel = str;
    }

    public String getDiscoveryBaseURL() {
        return this.discoveryBaseURL;
    }

    public void setDiscoveryBaseURL(String str) {
        this.discoveryBaseURL = str;
    }

    public String getTreProperties() {
        return this.treProperties;
    }

    public void setTreProperties(String str) {
        this.treProperties = str;
    }

    public String getTreProductReference() {
        return this.treProductReference;
    }

    public void setTreProductReference(String str) {
        this.treProductReference = str;
    }
}
