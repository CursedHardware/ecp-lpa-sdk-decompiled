package com.eastcompeace.lpa.sdk.bean.cos;

import com.eastcompeace.lpa.sdk.bean.BaseSerializableBean;

public class Rule extends BaseSerializableBean {
    private String name;
    private String rid;
    private String status;
    private String type;

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public String getRid() {
        return this.rid;
    }

    public void setRid(String str) {
        this.rid = str;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String str) {
        this.type = str;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String str) {
        this.status = str;
    }
}
