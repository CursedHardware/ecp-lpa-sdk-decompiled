package com.eastcompeace.lpa.sdk.procedures;

import com.eastcompeace.lpa.sdk.Es10xApduImpl;
import com.eastcompeace.lpa.sdk.Es9PlusFunctionImpl;
import com.eastcompeace.lpa.sdk.IEs10xFunction;
import com.eastcompeace.lpa.sdk.http.CallBack;
import com.eastcompeace.lpa.sdk.log.ELog;
import com.eastcompeace.lpa.sdk.utils.Base64;

public class CancelSessionProcedurs {
    private static final String TAG = "CancelSessionProcedurs";
    private static volatile CancelSessionProcedurs instance;
    private IEs10xFunction es10xFunction;

    public CancelSessionProcedurs(IEs10xFunction iEs10xFunction) {
        this.es10xFunction = iEs10xFunction;
    }

    public static CancelSessionProcedurs getInstance(IEs10xFunction iEs10xFunction) {
        if (instance == null) {
            synchronized (Es10xApduImpl.class) {
                instance = new CancelSessionProcedurs(iEs10xFunction);
            }
        } else {
            instance.setEs10xFunction(iEs10xFunction);
        }
        return instance;
    }

    private void setEs10xFunction(IEs10xFunction iEs10xFunction) {
        this.es10xFunction = iEs10xFunction;
    }

    public void cancelSession(String str, int i) {
        cancelSession(str, i, (IEs10xFunction.CallBack<Void>) null);
    }

    public void cancelSession(final String str, int i, final IEs10xFunction.CallBack<Void> callBack) {
        this.es10xFunction.cancelSession(str, i, new IEs10xFunction.CallBack<String>() {
            public void onComplete(int i, String str, String str2) {
                if (i != 0) {
                    CancelSessionProcedurs.this.disposeCallBack(callBack, i, (Object) null, str2);
                } else {
                    Es9PlusFunctionImpl.getInstance().cancelSession(str, Base64.encodeHex(str), new CallBack() {
                        public void onSuccess(Object obj) {
                            ELog.d(CancelSessionProcedurs.TAG, "cancelSession success");
                            CancelSessionProcedurs.this.disposeCallBack(callBack, 0, (Object) null, (String) null);
                        }

                        public void onError(Exception exc) {
                            ELog.e(CancelSessionProcedurs.TAG, "cancelSession", exc);
                            CancelSessionProcedurs.this.disposeCallBack(callBack, -6, (Object) null, exc.getMessage());
                        }
                    });
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void disposeCallBack(IEs10xFunction.CallBack callBack, int i, Object obj, String str) {
        if (callBack != null) {
            callBack.onComplete(i, obj, str);
        }
    }
}
