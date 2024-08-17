package com.eastcompeace.lpa.sdk.http;

import com.taobao.accs.common.Constants;
import org.json.JSONException;
import org.json.JSONObject;

public class RspResponseExcepsion extends Exception {
    private String message;
    private String reasonCode;
    private String subjectCode;

    public RspResponseExcepsion(String str, String str2, String str3) {
        this.reasonCode = str;
        this.subjectCode = str2;
        this.message = str3;
    }

    public RspResponseExcepsion(String str, String str2, String str3, String str4) {
        super(str);
        this.reasonCode = str2;
        this.subjectCode = str3;
        this.message = str4;
    }

    public RspResponseExcepsion(String str, Throwable th, String str2, String str3, String str4) {
        super(str, th);
        this.reasonCode = str2;
        this.subjectCode = str3;
        this.message = str4;
    }

    public RspResponseExcepsion(Throwable th, String str, String str2, String str3) {
        super(th);
        this.reasonCode = str;
        this.subjectCode = str2;
        this.message = str3;
    }

    public RspResponseExcepsion(String str, Throwable th, boolean z, boolean z2, String str2, String str3, String str4) {
        super(str, th, z, z2);
        this.reasonCode = str2;
        this.subjectCode = str3;
        this.message = str4;
    }

    public String getReasonCode() {
        return this.reasonCode;
    }

    public void setReasonCode(String str) {
        this.reasonCode = str;
    }

    public String getSubjectCode() {
        return this.subjectCode;
    }

    public String getErrorS() {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("reasonCode", this.reasonCode);
            jSONObject.put("subjectCode", this.subjectCode);
            jSONObject.put(Constants.SHARED_MESSAGE_ID_FILE, this.message);
            return jSONObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }

    public void setSubjectCode(String str) {
        this.subjectCode = str;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String str) {
        this.message = str;
    }
}
