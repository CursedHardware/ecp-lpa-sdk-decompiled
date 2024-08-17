package com.eastcompeace.lpa.sdk.utils;

import anet.channel.util.HttpConstant;
import com.eastcompeace.lpa.sdk.config.LibConfig;

public class AppConfig {
    public static final boolean HTTP_DEBUG = true;
    public static final String STATUS_SUCCESS = "Executed-Success";
    public static String[] httpUrl = {"192.168.12.140", "192.168.10.79"};
    public static boolean isDebug = true;
    public static boolean isGetInstallProfileSessionKey = false;
    public static final boolean isJavaSdCardForZh = false;
    public static boolean isOutPutLog = false;

    public static String filterUrl(String str) {
        if (LibConfig.isUseHttpRequest) {
            return "http://" + str;
        }
        if (str.contains(HttpConstant.HTTP)) {
            return "http://" + str;
        }
        for (String contains : httpUrl) {
            if (str.contains(contains)) {
                return "http://" + str;
            }
        }
        return "https://" + str;
    }
}
