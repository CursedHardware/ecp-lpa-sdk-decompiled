package com.eastcompeace.lpa.sdk.http;

import com.eastcompeace.lpa.sdk.utils.StringUtils;
import com.king.zxing.util.LogUtils;

public class HttpExcepsion extends Exception {
    private int code;

    public HttpExcepsion(int i) {
        this.code = i;
    }

    public HttpExcepsion(int i, String str) {
        super(str);
        this.code = i;
    }

    public HttpExcepsion(int i, String str, Throwable th) {
        super(str, th);
        this.code = i;
    }

    public HttpExcepsion(String str) {
        super(str);
    }

    public HttpExcepsion(String str, Throwable th) {
        super(str, th);
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int i) {
        this.code = i;
    }

    public String getMessage() {
        String message = super.getMessage();
        if (StringUtils.isEmpty(message)) {
            return "";
        }
        String[] split = message.split(LogUtils.COLON);
        return split[split.length - 1];
    }
}
