package com.eastcompeace.lpa.sdk.bean.cos;

import com.eastcompeace.lpa.sdk.bean.BaseSerializableBean;
import java.util.List;

public class RunResponse extends BaseSerializableBean {
    private List<Apdu> apdus;
    private String code;
    private String msg;
    private String scriptName;
    private String seid;
    private String status;

    public String getCode() {
        return this.code;
    }

    public void setCode(String str) {
        this.code = str;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String str) {
        this.msg = str;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String str) {
        this.status = str;
    }

    public String getSeid() {
        return this.seid;
    }

    public void setSeid(String str) {
        this.seid = str;
    }

    public String getScriptName() {
        return this.scriptName;
    }

    public void setScriptName(String str) {
        this.scriptName = str;
    }

    public List<Apdu> getApdus() {
        return this.apdus;
    }

    public void setApdus(List<Apdu> list) {
        this.apdus = list;
    }

    public static class Apdu extends BaseSerializableBean {
        private String apdu;
        private String code;
        private String returnCode;
        private String returnRes;

        public String getCode() {
            return this.code;
        }

        public void setCode(String str) {
            this.code = str;
        }

        public String getReturnCode() {
            return this.returnCode;
        }

        public void setReturnCode(String str) {
            this.returnCode = str;
        }

        public String getReturnRes() {
            return this.returnRes;
        }

        public void setReturnRes(String str) {
            this.returnRes = str;
        }

        public String getApdu() {
            return this.apdu;
        }

        public void setApdu(String str) {
            this.apdu = str;
        }
    }
}
