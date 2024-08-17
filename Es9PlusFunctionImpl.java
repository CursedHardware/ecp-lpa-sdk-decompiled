package com.eastcompeace.lpa.sdk;

import com.alibaba.fastjson.JSONObject;
import com.eastcompeace.lpa.sdk.bean.es9.AuthenticateClientBean;
import com.eastcompeace.lpa.sdk.bean.es9.GetBoundProfilePackageBean;
import com.eastcompeace.lpa.sdk.bean.es9.InitiateAuthenticationBean;
import com.eastcompeace.lpa.sdk.http.CallBack;
import com.eastcompeace.lpa.sdk.http.HttpExcepsion;
import com.eastcompeace.lpa.sdk.http.HttpManager;
import com.eastcompeace.lpa.sdk.http.HttpManagerS;
import com.eastcompeace.lpa.sdk.http.HttpResponseUtils;
import com.eastcompeace.lpa.sdk.http.RspResponseExcepsion;
import com.eastcompeace.lpa.sdk.log.ELog;
import com.eastcompeace.lpa.sdk.utils.AppConfig;
import com.eastcompeace.lpa.sdk.utils.Base64Android;
import com.eastcompeace.lpa.sdk.utils.StringUtils;
import java.io.IOException;

public class Es9PlusFunctionImpl {
    private static final String TAG = "Es9PlusFunctionImpl";
    private static final String USER_AGENT = "gsma-rsp-lpad";
    private static final String X_ADMIN_PROTOCOL = "gsma/rsp/v2.2.2";
    private static volatile Es9PlusFunctionImpl es9PlusFunctionImpl;
    private String smdpAddress;
    private String smdpAddressHttp;

    public static Es9PlusFunctionImpl getInstance() {
        if (es9PlusFunctionImpl == null) {
            synchronized (Es9PlusFunctionImpl.class) {
                if (es9PlusFunctionImpl == null) {
                    es9PlusFunctionImpl = new Es9PlusFunctionImpl();
                }
            }
        }
        return es9PlusFunctionImpl;
    }

    public Es9PlusFunctionImpl smdpAddress(String str) {
        this.smdpAddress = str;
        this.smdpAddressHttp = AppConfig.filterUrl(str);
        return this;
    }

    public void initiateAuthentication(String str, String str2, final CallBack callBack) {
        ELog.d(TAG, "--------------Es9+ initiateAuthentication-----------------");
        new HttpManager.Builder().url(this.smdpAddressHttp + "/gsma/rsp2/es9plus/initiateAuthentication").postBody().addHeader("user-agent", USER_AGENT).addHeader("x-admin-protocol", X_ADMIN_PROTOCOL).addParams("euiccChallenge", str).addParams("euiccInfo1", str2).addParams("smdpAddress", this.smdpAddress).build().asyncExecute(new CallBack<JSONObject>() {
            public void onSuccess(JSONObject jSONObject) {
                try {
                    if (HttpResponseUtils.isExecutedSucess(jSONObject)) {
                        InitiateAuthenticationBean initiateAuthenticationBean = new InitiateAuthenticationBean();
                        initiateAuthenticationBean.setTransactionId(jSONObject.getString("transactionId"));
                        String string = jSONObject.getString("serverSigned1");
                        if (!StringUtils.isEmpty(string)) {
                            initiateAuthenticationBean.setServerSigned1(Base64Android.decode(string, 0));
                        }
                        String string2 = jSONObject.getString("serverSignature1");
                        if (!StringUtils.isEmpty(string2)) {
                            initiateAuthenticationBean.setServerSignature1(Base64Android.decode(string2, 0));
                        }
                        String string3 = jSONObject.getString("euiccCiPKIdToBeUsed");
                        if (!StringUtils.isEmpty(string3)) {
                            initiateAuthenticationBean.setEuiccCiPKIdTobeUsed(Base64Android.decode(string3, 0));
                        }
                        String string4 = jSONObject.getString("serverCertificate");
                        if (!StringUtils.isEmpty(string4)) {
                            initiateAuthenticationBean.setServerCertificate(Base64Android.decode(string4, 0));
                        }
                        callBack.onSuccess(initiateAuthenticationBean);
                        return;
                    }
                    callBack.onError(new Exception(""));
                } catch (Exception e) {
                    ELog.e(Es9PlusFunctionImpl.TAG, "initiateAuthentication onSuccess: ", e);
                    callBack.onError(e);
                }
            }

            public void onError(Exception exc) {
                callBack.onError(exc);
            }
        });
    }

    public void authenticateClient(String str, String str2, final CallBack callBack) {
        ELog.d(TAG, "--------------Es9+ authenticateClient-----------------");
        new HttpManager.Builder().url(this.smdpAddressHttp + "/gsma/rsp2/es9plus/authenticateClient").postBody().addHeader("user-agent", USER_AGENT).addHeader("x-admin-protocol", X_ADMIN_PROTOCOL).addParams("transactionId", str).addParams("authenticateServerResponse", str2).build().asyncExecute(new CallBack<JSONObject>() {
            public void onSuccess(JSONObject jSONObject) {
                try {
                    if (HttpResponseUtils.isExecutedSucess(jSONObject)) {
                        AuthenticateClientBean authenticateClientBean = new AuthenticateClientBean();
                        authenticateClientBean.setTransactionId(jSONObject.getString("transactionId"));
                        String string = jSONObject.getString("profileMetadata");
                        if (!StringUtils.isEmpty(string)) {
                            authenticateClientBean.setProfileMetadata(Base64Android.decode(string, 0));
                        }
                        String string2 = jSONObject.getString("smdpSigned2");
                        if (!StringUtils.isEmpty(string2)) {
                            authenticateClientBean.setSmdpSigned2(Base64Android.decode(string2, 0));
                        }
                        String string3 = jSONObject.getString("smdpSignature2");
                        if (!StringUtils.isEmpty(string3)) {
                            authenticateClientBean.setSmdpSignature2(Base64Android.decode(string3, 0));
                        }
                        String string4 = jSONObject.getString("smdpCertificate");
                        if (!StringUtils.isEmpty(string4)) {
                            authenticateClientBean.setSmdpCertificate(Base64Android.decode(string4, 0));
                        }
                        callBack.onSuccess(authenticateClientBean);
                        return;
                    }
                    callBack.onError(new Exception(""));
                } catch (RspResponseExcepsion e) {
                    ELog.e(Es9PlusFunctionImpl.TAG, "authenticateClient onSuccess: ", e);
                    callBack.onError(e);
                }
            }

            public void onError(Exception exc) {
                callBack.onError(exc);
            }
        });
    }

    public void getBoundProfilePackage(String str, String str2, final CallBack callBack) {
        ELog.d(TAG, "--------------Es9+ getBoundProfilePackage-----------------");
        new HttpManager.Builder().url(this.smdpAddressHttp + "/gsma/rsp2/es9plus/getBoundProfilePackage").postBody().addHeader("user-agent", USER_AGENT).addHeader("x-admin-protocol", X_ADMIN_PROTOCOL).addParams("transactionId", str).addParams("prepareDownloadResponse", str2).build().asyncExecute(new CallBack<JSONObject>() {
            public void onSuccess(JSONObject jSONObject) {
                try {
                    if (HttpResponseUtils.isExecutedSucess(jSONObject)) {
                        GetBoundProfilePackageBean getBoundProfilePackageBean = new GetBoundProfilePackageBean();
                        getBoundProfilePackageBean.setTransactionId(jSONObject.getString("transactionId"));
                        String string = jSONObject.getString("boundProfilePackage");
                        if (!StringUtils.isEmpty(string)) {
                            getBoundProfilePackageBean.setBoundProfilePackage(Base64Android.decode(string, 0));
                        }
                        callBack.onSuccess(getBoundProfilePackageBean);
                        return;
                    }
                    callBack.onError(new Exception(""));
                } catch (Exception e) {
                    ELog.e(Es9PlusFunctionImpl.TAG, "getBoundProfilePackage onSuccess: ", e);
                    callBack.onError(e);
                }
            }

            public void onError(Exception exc) {
                callBack.onError(exc);
            }
        });
    }

    public void handleNotification(String str, final CallBack callBack) {
        ELog.d(TAG, "--------------Es9+ handleNotification-----------------");
        new HttpManagerS.Builder().url(this.smdpAddressHttp + "/gsma/rsp2/es9plus/handleNotification").postBody().addHeader("user-agent", USER_AGENT).addHeader("x-admin-protocol", X_ADMIN_PROTOCOL).addParams("pendingNotification", str).build().asyncExecute(new CallBack<JSONObject>() {
            public void onSuccess(JSONObject jSONObject) {
                callBack.onSuccess(jSONObject);
            }

            public void onError(Exception exc) {
                callBack.onError(exc);
            }
        });
    }

    public JSONObject handleNotification(String str) throws HttpExcepsion, IOException {
        ELog.d(TAG, "--------------Es9+ handleNotification-----------------");
        return new HttpManagerS.Builder().url(this.smdpAddressHttp + "/gsma/rsp2/es9plus/handleNotification").postBody().addHeader("user-agent", USER_AGENT).addHeader("x-admin-protocol", X_ADMIN_PROTOCOL).addParams("pendingNotification", str).build().syncExecute();
    }

    public void cancelSession(String str, String str2, final CallBack callBack) {
        ELog.d(TAG, "--------------Es9+ cancelSession-----------------");
        new HttpManager.Builder().url(this.smdpAddressHttp + "/gsma/rsp2/es9plus/cancelSession").postBody().addHeader("user-agent", USER_AGENT).addHeader("x-admin-protocol", X_ADMIN_PROTOCOL).addParams("transactionId", str).addParams("cancelSessionResponse", str2).build().asyncExecute(new CallBack<JSONObject>() {
            public void onSuccess(JSONObject jSONObject) {
                try {
                    if (HttpResponseUtils.isExecutedSucess(jSONObject)) {
                        callBack.onSuccess(null);
                    }
                } catch (Exception e) {
                    ELog.e(Es9PlusFunctionImpl.TAG, "cancelSession onSuccess error: ", e);
                    callBack.onError(e);
                }
            }

            public void onError(Exception exc) {
                callBack.onError(exc);
            }
        });
    }
}
