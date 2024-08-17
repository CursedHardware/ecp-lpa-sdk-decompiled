package com.eastcompeace.lpa.sdk.procedures;

import com.eastcompeace.lpa.sdk.Es10xApduImpl;
import com.eastcompeace.lpa.sdk.Es11FunctionImpl;
import com.eastcompeace.lpa.sdk.IEs10xFunction;
import com.eastcompeace.lpa.sdk.bean.es11.Es11AuthenticateClientBean;
import com.eastcompeace.lpa.sdk.bean.es9.InitiateAuthenticationBean;
import com.eastcompeace.lpa.sdk.http.CallBack;
import com.eastcompeace.lpa.sdk.log.ELog;
import com.eastcompeace.lpa.sdk.procedures.exception.ErrorHandler;
import com.eastcompeace.lpa.sdk.utils.Base64;
import com.eastcompeace.lpa.sdk.utils.EuiccErrorMapper;
import com.eastcompeace.lpa.sdk.utils.StringUtils;
import com.xuexiang.xupdate.entity.UpdateError;
import java.util.concurrent.TimeUnit;

public class Es11Procedures {
    private static final String TAG = "Es11Procedures";
    private static volatile Es11Procedures instance;
    private int count = 1;
    IEs10xFunction es10xFunction;
    private TimeUnit timeUnit = TimeUnit.MILLISECONDS;
    private int timeout = UpdateError.ERROR.INSTALL_FAILED;

    public Es11Procedures(IEs10xFunction iEs10xFunction) {
        this.es10xFunction = iEs10xFunction;
    }

    public static Es11Procedures getInstance(IEs10xFunction iEs10xFunction) {
        if (instance == null) {
            synchronized (Es10xApduImpl.class) {
                instance = new Es11Procedures(iEs10xFunction);
            }
        } else {
            instance.setEs10xFunction(iEs10xFunction);
        }
        return instance;
    }

    public Es11Procedures setImei(String str) {
        IEs10xFunction iEs10xFunction = this.es10xFunction;
        if (iEs10xFunction instanceof Es10xApduImpl) {
            ((Es10xApduImpl) iEs10xFunction).setImei(str);
        }
        return this;
    }

    public void setEs10xFunction(IEs10xFunction iEs10xFunction) {
        this.es10xFunction = iEs10xFunction;
    }

    public void getSmdpFromSmdsAddress(final String str, final IEs10xFunction.CallBack<Es11AuthenticateClientBean> callBack) {
        try {
            String eUICCInfo1 = this.es10xFunction.getEUICCInfo1();
            String eUICCChallenge = this.es10xFunction.getEUICCChallenge();
            ELog.d(TAG, "--------------Es11 initiateAuthentication-----------------");
            ELog.d(TAG, "smdsAddress: " + str);
            ELog.d(TAG, "eUICCChallenge: " + eUICCChallenge);
            ELog.d(TAG, "euiccInfo: " + eUICCInfo1);
            if (StringUtils.isEmpty(str)) {
                callBack.onComplete(EuiccErrorMapper.getOperationAndErrorCode(9, EuiccErrorMapper.ERROR_ADDRESS_MISSING), null, (String) null);
            }
            Es11FunctionImpl.getInstance().smdsAddress(str).initiateAuthentication(Base64.encodeHex(eUICCChallenge), Base64.encodeHex(eUICCInfo1), new CallBack<InitiateAuthenticationBean>() {
                public void onSuccess(InitiateAuthenticationBean initiateAuthenticationBean) {
                    ELog.d(Es11Procedures.TAG, "Es9+ initiateAuthentication success response: " + initiateAuthenticationBean.toString());
                    Es11Procedures.this.authService(str, initiateAuthenticationBean, callBack);
                }

                public void onError(Exception exc) {
                    ELog.e(Es11Procedures.TAG, "Es9+ initiateAuthentication error:", exc);
                    ErrorHandler.es9HttpError(callBack, exc);
                }
            });
        } catch (Exception e) {
            ELog.e(TAG, "Es10 getEUICCInfo1/getEUICCChallenge error:", e);
            ErrorHandler.es10Error(12, callBack, e);
        }
    }

    /* access modifiers changed from: private */
    public void authService(String str, InitiateAuthenticationBean initiateAuthenticationBean, final IEs10xFunction.CallBack<Es11AuthenticateClientBean> callBack) {
        try {
            String authenticateServer = this.es10xFunction.authenticateServer("", initiateAuthenticationBean.getServerSigned1(), initiateAuthenticationBean.getServerSignature1(), initiateAuthenticationBean.getEuiccCiPKIdTobeUsed(), initiateAuthenticationBean.getServerCertificate());
            ELog.d(TAG, "Es10 authenticateServer response: " + authenticateServer);
            Es11FunctionImpl.getInstance().smdsAddress(str).authenticateClient(initiateAuthenticationBean.getTransactionId(), Base64.encodeHex(authenticateServer), new CallBack<Es11AuthenticateClientBean>() {
                public void onSuccess(Es11AuthenticateClientBean es11AuthenticateClientBean) {
                    ELog.d(Es11Procedures.TAG, "Es9+ authenticateClient success response: " + es11AuthenticateClientBean.toString());
                    callBack.onComplete(0, es11AuthenticateClientBean, (String) null);
                }

                public void onError(Exception exc) {
                    ELog.e(Es11Procedures.TAG, "authenticateClient error", exc);
                    ErrorHandler.es9HttpError(callBack, exc);
                }
            });
        } catch (Exception e) {
            ELog.e(TAG, "Es10 authenticateServer error", e);
            ErrorHandler.es10Error(13, callBack, e);
        }
    }
}
