package com.eastcompeace.lpa.sdk.fup;

public class FwUpdateException extends Exception {
    private String reasonCode;
    private String subjectCode;

    public FwUpdateException() {
    }

    public FwUpdateException(Exception exc) {
        super(exc);
    }

    public FwUpdateException(String str) {
        super(str);
    }

    public FwUpdateException(String str, String str2, String str3) {
        super(str3);
        this.subjectCode = str;
        this.reasonCode = str2;
    }

    public FwUpdateException(String str, String str2, String str3, Throwable th) {
        super(str3, th);
        this.subjectCode = str;
        this.reasonCode = str2;
    }

    public FwUpdateException(String str, String str2, Throwable th) {
        super(th);
        this.subjectCode = str;
        this.reasonCode = str2;
    }

    public FwUpdateException(String str, String str2, String str3, Throwable th, boolean z, boolean z2) {
        super(str3, th, z, z2);
        this.subjectCode = str;
        this.reasonCode = str2;
    }

    public String getSubjectCode() {
        return this.subjectCode;
    }

    public String getReasonCode() {
        return this.reasonCode;
    }

    public void setSubjectCode(String str) {
        this.subjectCode = str;
    }

    public void setReasonCode(String str) {
        this.reasonCode = str;
    }
}
