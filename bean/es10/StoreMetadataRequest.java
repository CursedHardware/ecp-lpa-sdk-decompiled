package com.eastcompeace.lpa.sdk.bean.es10;

import com.eastcompeace.lpa.sdk.bean.BaseSerializableBean;
import com.eastcompeace.lpa.sdk.utils.ApduHandler;
import com.eastcompeace.lpa.sdk.utils.HexUtil;
import java.util.List;

public class StoreMetadataRequest extends BaseSerializableBean {
    private String iccid;
    private String icon;
    private String iconType;
    private String notificationConfigurationInfo;
    private String profileClass;
    private String profileName;
    private Operator profileOwner;
    private String profilePolicyRules;
    private String serviceProviderName;

    public StoreMetadataRequest() {
    }

    public StoreMetadataRequest(String str) {
        List<TLVObject> apduTransfTlvsByParentTag = ApduHandler.apduTransfTlvsByParentTag("BF25", str);
        if (apduTransfTlvsByParentTag.size() > 0) {
            for (TLVObject next : apduTransfTlvsByParentTag.get(0).getChildrenTLV()) {
                String stag = next.getStag();
                stag.hashCode();
                char c = 65535;
                switch (stag.hashCode()) {
                    case 1708:
                        if (stag.equals("5A")) {
                            c = 0;
                            break;
                        }
                        break;
                    case 1816:
                        if (stag.equals("91")) {
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
                    case 1818:
                        if (stag.equals("93")) {
                            c = 3;
                            break;
                        }
                        break;
                    case 1819:
                        if (stag.equals("94")) {
                            c = 4;
                            break;
                        }
                        break;
                    case 1820:
                        if (stag.equals("95")) {
                            c = 5;
                            break;
                        }
                        break;
                    case 1824:
                        if (stag.equals("99")) {
                            c = 6;
                            break;
                        }
                        break;
                    case 2101:
                        if (stag.equals("B7")) {
                            c = 7;
                            break;
                        }
                        break;
                }
                switch (c) {
                    case 0:
                        this.iccid = ProfileInfo.getShowIccid(next.getSvalue());
                        break;
                    case 1:
                        this.serviceProviderName = new String(HexUtil.decodeHex(next.getSvalue()));
                        break;
                    case 2:
                        this.profileName = new String(HexUtil.decodeHex(next.getSvalue()));
                        break;
                    case 3:
                        this.iconType = next.getSvalue();
                        break;
                    case 4:
                        this.icon = next.getSvalue();
                        break;
                    case 5:
                        this.profileClass = next.getSvalue();
                        break;
                    case 6:
                        this.profilePolicyRules = next.getSvalue();
                        break;
                    case 7:
                        this.profileOwner = new Operator(next.getSvalue());
                        break;
                }
            }
        }
    }

    public String getIccid() {
        return this.iccid;
    }

    public void setIccid(String str) {
        this.iccid = str;
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

    public String getNotificationConfigurationInfo() {
        return this.notificationConfigurationInfo;
    }

    public void setNotificationConfigurationInfo(String str) {
        this.notificationConfigurationInfo = str;
    }

    public Operator getProfileOwner() {
        return this.profileOwner;
    }

    public void setProfileOwner(Operator operator) {
        this.profileOwner = operator;
    }

    public String getProfilePolicyRules() {
        return this.profilePolicyRules;
    }

    public void setProfilePolicyRules(String str) {
        this.profilePolicyRules = str;
    }

    public String toString() {
        return "StoreMetadata{iccid='" + this.iccid + '\'' + ", serviceProviderName='" + this.serviceProviderName + '\'' + ", profileName='" + this.profileName + '\'' + ", iconType='" + this.iconType + '\'' + ", icon='" + this.icon + '\'' + ", profileClass='" + this.profileClass + '\'' + ", notificationConfigurationInfo='" + this.notificationConfigurationInfo + '\'' + ", profileOwner=" + this.profileOwner + ", profilePolicyRules='" + this.profilePolicyRules + '\'' + '}';
    }
}
