package com.eastcompeace.lpa.sdk.bean.es10;

import com.eastcompeace.lpa.sdk.bean.BaseSerializableBean;
import com.eastcompeace.lpa.sdk.utils.ApduHandler;
import com.eastcompeace.lpa.sdk.utils.HexUtil;
import com.eastcompeace.lpa.sdk.utils.StringUtils;
import java.util.List;

public class EuiccInfo1 extends BaseSerializableBean {
    private String euiccCiPKIdForSign;
    private String euiccCiPKIdForVerify;
    private String svn;

    public EuiccInfo1(String str) {
        List<TLVObject> tLVByTag;
        if (!StringUtils.isEmpty(str) && (tLVByTag = ApduHandler.getTLVByTag("BF20", str, true)) != null && tLVByTag.size() == 1) {
            for (TLVObject next : tLVByTag.get(0).getChildrenTLV()) {
                String stag = next.getStag();
                stag.hashCode();
                char c = 65535;
                switch (stag.hashCode()) {
                    case 1786:
                        if (stag.equals("82")) {
                            c = 0;
                            break;
                        }
                        break;
                    case 2072:
                        if (stag.equals("A9")) {
                            c = 1;
                            break;
                        }
                        break;
                    case 2080:
                        if (stag.equals("AA")) {
                            c = 2;
                            break;
                        }
                        break;
                }
                switch (c) {
                    case 0:
                        this.svn = new String(HexUtil.decodeHex(next.getSvalue()));
                        break;
                    case 1:
                        for (TLVObject next2 : next.getChildrenTLV()) {
                            if ("04".equals(next2.getStag())) {
                                this.euiccCiPKIdForVerify = next2.getSvalue();
                            }
                        }
                        break;
                    case 2:
                        for (TLVObject next3 : next.getChildrenTLV()) {
                            if ("04".equals(next3.getStag())) {
                                this.euiccCiPKIdForSign = next3.getSvalue();
                            }
                        }
                        break;
                }
            }
        }
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

    public String getSvn() {
        return this.svn;
    }

    public void setSvn(String str) {
        this.svn = str;
    }

    public String toString() {
        return "EuiccInfo1{euiccCiPKIdForVerify='" + this.euiccCiPKIdForVerify + '\'' + ", euiccCiPKIdForSign='" + this.euiccCiPKIdForSign + '\'' + ", svn='" + this.svn + '\'' + '}';
    }
}
