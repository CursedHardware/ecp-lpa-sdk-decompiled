package com.eastcompeace.lpa.sdk.bean.es10;

import com.eastcompeace.lpa.sdk.bean.BaseSerializableBean;
import java.util.List;

public class TLVObject extends BaseSerializableBean {
    private List<TLVObject> childrenTLV;
    private String slength;
    private String slengthTag;
    private String stag;
    private String svalue;

    public TLVObject(String str) {
        this.stag = str;
    }

    public TLVObject(String str, String str2) {
        this.stag = str;
        this.slength = str2;
    }

    public TLVObject(String str, String str2, String str3) {
        this.stag = str;
        this.slength = str2;
        this.svalue = str3;
    }

    public TLVObject(String str, String str2, String str3, String str4) {
        this.stag = str;
        this.slengthTag = str2;
        this.slength = str3;
        this.svalue = str4;
    }

    public String getSlengthTag() {
        return this.slengthTag;
    }

    public void setSlengthTag(String str) {
        this.slengthTag = str;
    }

    public String getStag() {
        return this.stag;
    }

    public void setStag(String str) {
        this.stag = str;
    }

    public String getSlength() {
        return this.slength;
    }

    public void setSlength(String str) {
        this.slength = str;
    }

    public String getSvalue() {
        return this.svalue;
    }

    public void setSvalue(String str) {
        this.svalue = str;
    }

    public List<TLVObject> getChildrenTLV() {
        return this.childrenTLV;
    }

    public void setChildrenTLV(List<TLVObject> list) {
        this.childrenTLV = list;
    }

    public String getApdu() {
        return this.stag + this.slengthTag + this.slength + this.svalue;
    }

    public String toString() {
        return "TLVObject{stag='" + this.stag + '\'' + ", slengthTag='" + this.slengthTag + '\'' + ", slength='" + this.slength + '\'' + ", svalue='" + this.svalue + '\'' + ", childrenTLV=" + this.childrenTLV + '}';
    }
}
