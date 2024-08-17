package com.eastcompeace.lpa.sdk.log;

import com.eastcompeace.lpa.sdk.utils.AppConfig;

public class ELog {
    public static boolean DEBUG = AppConfig.isDebug;
    public static final String LOG_TAG = "LPA";
    public static Ilog mlog;

    public static void setLog(Ilog ilog) {
        mlog = ilog;
    }

    public static final void d(String str) {
        if (DEBUG && mlog != null) {
            d("LPA", str);
        }
    }

    public static final void d(String str, String str2) {
        Ilog ilog;
        if (DEBUG && (ilog = mlog) != null) {
            ilog.d(str, str2);
        }
    }

    public static final void d(String str, String str2, Throwable th) {
        Ilog ilog;
        if (DEBUG && (ilog = mlog) != null) {
            ilog.d(str, str2, th);
        }
    }

    public static final void e(String str) {
        if (DEBUG && mlog != null) {
            e("LPA", "" + str);
        }
    }

    public static final void e(String str, String str2) {
        Ilog ilog;
        if (DEBUG && (ilog = mlog) != null) {
            ilog.e(str, str2);
        }
    }

    public static final void e(String str, String str2, Throwable th) {
        Ilog ilog;
        if (DEBUG && (ilog = mlog) != null) {
            ilog.e(str, str2, th);
        }
    }

    public static final void i(String str) {
        if (DEBUG && mlog != null) {
            i("LPA", str);
        }
    }

    public static final void i(String str, String str2) {
        Ilog ilog;
        if (DEBUG && (ilog = mlog) != null) {
            ilog.i(str, str2, (Throwable) null);
        }
    }

    public static final void i(String str, String str2, Throwable th) {
        Ilog ilog;
        if (DEBUG && (ilog = mlog) != null) {
            ilog.i(str, str2, th);
        }
    }
}
