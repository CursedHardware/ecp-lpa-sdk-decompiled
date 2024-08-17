package com.eastcompeace.lpa.sdk;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.eastcompeace.lpa.sdk.bean.es11.Es11AuthenticateClientBean;
import com.eastcompeace.lpa.sdk.bean.es11.EventEntry;
import com.eastcompeace.lpa.sdk.bean.es9.InitiateAuthenticationBean;
import com.eastcompeace.lpa.sdk.http.CallBack;
import com.eastcompeace.lpa.sdk.http.HttpManager;
import com.eastcompeace.lpa.sdk.http.HttpResponseUtils;
import com.eastcompeace.lpa.sdk.log.ELog;
import com.eastcompeace.lpa.sdk.utils.AppConfig;
import com.eastcompeace.lpa.sdk.utils.Base64Android;
import com.eastcompeace.lpa.sdk.utils.StringUtils;
import java.util.ArrayList;

public class Es11FunctionImpl {
    private static final String TAG = "es11FunctionImpl";
    private static final String USER_AGENT = "gsma-rsp-lpad";
    private static final String X_ADMIN_PROTOCOL = "gsma/rsp/v2.2.2";
    private static volatile Es11FunctionImpl es11FunctionImpl;
    private String smdsAddress;
    private String smdsAddressHttp;

    public static Es11FunctionImpl getInstance() {
        if (es11FunctionImpl == null) {
            synchronized (Es11FunctionImpl.class) {
                if (es11FunctionImpl == null) {
                    es11FunctionImpl = new Es11FunctionImpl();
                }
            }
        }
        return es11FunctionImpl;
    }

    public Es11FunctionImpl smdsAddress(String str) {
        this.smdsAddressHttp = AppConfig.filterUrl(str);
        this.smdsAddress = str;
        return this;
    }

    public void initiateAuthentication(String str, String str2, final CallBack callBack) {
        new HttpManager.Builder().url(this.smdsAddressHttp + "/gsma/rsp2/es11/initiateAuthentication").postBody().addHeader("user-agent", USER_AGENT).addHeader("x-admin-protocol", X_ADMIN_PROTOCOL).addParams("euiccChallenge", str).addParams("euiccInfo1", str2).addParams("smdsAddress", this.smdsAddress).build().asyncExecute(new CallBack<JSONObject>() {
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
                    ELog.e(Es11FunctionImpl.TAG, "initiateAuthentication onSuccess: ", e);
                    callBack.onError(e);
                }
            }

            public void onError(Exception exc) {
                callBack.onError(exc);
            }
        });
    }

    public void authenticateClient(String str, String str2, final CallBack callBack) {
        new HttpManager.Builder().url(this.smdsAddressHttp + "/gsma/rsp2/es11/authenticateClient").postBody().addHeader("user-agent", USER_AGENT).addHeader("x-admin-protocol", X_ADMIN_PROTOCOL).addParams("transactionId", str).addParams("authenticateServerResponse", str2).build().asyncExecute(new CallBack<JSONObject>() {
            public void onSuccess(JSONObject jSONObject) {
                int size;
                try {
                    if (HttpResponseUtils.isExecutedSucess(jSONObject)) {
                        Es11AuthenticateClientBean es11AuthenticateClientBean = new Es11AuthenticateClientBean();
                        es11AuthenticateClientBean.setTransactionId(jSONObject.getString("transactionId"));
                        JSONArray jSONArray = jSONObject.getJSONArray("eventEntries");
                        if (jSONArray != null && (size = jSONArray.size()) > 0) {
                            ArrayList arrayList = new ArrayList();
                            for (int i = 0; i < size; i++) {
                                JSONObject jSONObject2 = jSONArray.getJSONObject(i);
                                EventEntry eventEntry = new EventEntry();
                                eventEntry.setEventId(jSONObject2.getString("eventId"));
                                eventEntry.setRspServerAddress(jSONObject2.getString("rspServerAddress"));
                                arrayList.add(eventEntry);
                            }
                            es11AuthenticateClientBean.setEventEntries(arrayList);
                        }
                        callBack.onSuccess(es11AuthenticateClientBean);
                        return;
                    }
                    callBack.onError(new Exception(""));
                } catch (Exception e) {
                    ELog.e(Es11FunctionImpl.TAG, "authenticateClient onSuccess: ", e);
                    callBack.onError(e);
                }
            }

            public void onError(Exception exc) {
                callBack.onError(exc);
            }
        });
    }
}
