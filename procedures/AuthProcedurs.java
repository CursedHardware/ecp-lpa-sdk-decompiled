package com.eastcompeace.lpa.sdk.procedures;

import com.eastcompeace.lpa.sdk.Es10xApduImpl;
import com.eastcompeace.lpa.sdk.Es9PlusFunctionImpl;
import com.eastcompeace.lpa.sdk.IEs10xFunction;
import com.eastcompeace.lpa.sdk.bean.es9.AuthenticateClientBean;
import com.eastcompeace.lpa.sdk.bean.es9.InitiateAuthenticationBean;
import com.eastcompeace.lpa.sdk.http.CallBack;
import com.eastcompeace.lpa.sdk.log.ELog;
import com.eastcompeace.lpa.sdk.procedures.exception.ErrorHandler;
import com.eastcompeace.lpa.sdk.utils.Base64;
import com.eastcompeace.lpa.sdk.utils.EuiccErrorMapper;
import com.eastcompeace.lpa.sdk.utils.StringUtils;

public class AuthProcedurs {
    private static final String TAG = "AuthProcedurs";
    private static volatile AuthProcedurs instance;
    /* access modifiers changed from: private */
    public IEs10xFunction es10xFunction;

    public AuthProcedurs(IEs10xFunction iEs10xFunction) {
        this.es10xFunction = iEs10xFunction;
    }

    public static AuthProcedurs getInstance(IEs10xFunction iEs10xFunction) {
        if (instance == null) {
            synchronized (Es10xApduImpl.class) {
                instance = new AuthProcedurs(iEs10xFunction);
            }
        } else {
            instance.setEs10xFunction(iEs10xFunction);
        }
        return instance;
    }

    private void setEs10xFunction(IEs10xFunction iEs10xFunction) {
        this.es10xFunction = iEs10xFunction;
    }

    public void auth(String str, String str2, IEs10xFunction.CallBack callBack) {
        try {
            String eUICCInfo1 = this.es10xFunction.getEUICCInfo1();
            String eUICCChallenge = this.es10xFunction.getEUICCChallenge();
            ELog.d(TAG, "--------------profile download auth-----------------");
            ELog.d(TAG, "activationCode address: " + str);
            ELog.d(TAG, "eUICCChallenge: " + eUICCChallenge);
            ELog.d(TAG, "euiccInfo: " + eUICCInfo1);
            if (StringUtils.isEmpty(str)) {
                disposeCallBack(callBack, EuiccErrorMapper.getOperationAndErrorCode(9, EuiccErrorMapper.ERROR_ADDRESS_MISSING), (Object) null, (String) null);
            }
            final String[] strArr = new String[1];
            final String str3 = str2;
            final String str4 = str;
            final IEs10xFunction.CallBack callBack2 = callBack;
            Es9PlusFunctionImpl.getInstance().smdpAddress(str).initiateAuthentication(Base64.encodeHex(eUICCChallenge), Base64.encodeHex(eUICCInfo1), new CallBack<InitiateAuthenticationBean>() {
                public void onSuccess(InitiateAuthenticationBean initiateAuthenticationBean) {
                    ELog.d(AuthProcedurs.TAG, "Es9+ initiateAuthentication success response: " + initiateAuthenticationBean.toString());
                    try {
                        String authenticateServer = AuthProcedurs.this.es10xFunction.authenticateServer(str3, initiateAuthenticationBean.getServerSigned1(), initiateAuthenticationBean.getServerSignature1(), initiateAuthenticationBean.getEuiccCiPKIdTobeUsed(), initiateAuthenticationBean.getServerCertificate());
                        strArr[0] = initiateAuthenticationBean.getTransactionId();
                        Es9PlusFunctionImpl.getInstance().smdpAddress(str4).authenticateClient(strArr[0], Base64.encodeHex(authenticateServer), new CallBack<AuthenticateClientBean>() {
                            public void onSuccess(AuthenticateClientBean authenticateClientBean) {
                                ELog.d(AuthProcedurs.TAG, "Es9+ authenticateClient success response: " + authenticateClientBean.toString());
                                AuthProcedurs.this.disposeCallBack(callBack2, 0, authenticateClientBean, (String) null);
                            }

                            public void onError(Exception exc) {
                                ELog.e(AuthProcedurs.TAG, "authenticateClient error", exc);
                                ErrorHandler.es9HttpError(callBack2, exc);
                            }
                        });
                    } catch (Exception e) {
                        ELog.e(AuthProcedurs.TAG, "Es10 authenticateServer error", e);
                        ErrorHandler.es10Error(13, callBack2, e);
                    }
                }

                public void onError(Exception exc) {
                    ELog.e(AuthProcedurs.TAG, "Es9+ initiateAuthentication error:", exc);
                    ErrorHandler.es9HttpError(callBack2, exc);
                }
            });
        } catch (Exception e) {
            ELog.e(TAG, "Es10 getEUICCInfo1/getEUICCChallenge error:", e);
            ErrorHandler.es10Error(12, callBack, e);
        }
    }

    /* access modifiers changed from: private */
    public void disposeCallBack(IEs10xFunction.CallBack callBack, int i, Object obj, String str) {
        if (callBack != null) {
            callBack.onComplete(i, obj, str);
        }
    }
}
