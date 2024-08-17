package com.eastcompeace.lpa.sdk.utils;

import com.king.zxing.util.LogUtils;

public class ChannelExcuteException extends Exception {
    private int resultCode;

    public ChannelExcuteException(int i) {
        this.resultCode = i;
    }

    public ChannelExcuteException(int i, String str) {
        super(str);
        this.resultCode = i;
    }

    public ChannelExcuteException(int i, String str, Throwable th) {
        super(str, th);
        this.resultCode = i;
    }

    public ChannelExcuteException(String str) {
        super(str);
    }

    public ChannelExcuteException(String str, Throwable th) {
        super(str, th);
    }

    public int getResultCode() {
        return this.resultCode;
    }

    public String getMessage() {
        if (StringUtils.isEmpty(super.getMessage())) {
            return "";
        }
        String[] split = super.getMessage().split(LogUtils.COLON);
        return split[split.length - 1];
    }
}
