package com.eastcompeace.lpa.sdk.utils;

import com.alibaba.fastjson.JSONObject;
import com.eastcompeace.lpa.sdk.IChannel;
import com.eastcompeace.lpa.sdk.fup.A3Util;
import com.eastcompeace.lpa.sdk.fup.Constant;
import com.eastcompeace.lpa.sdk.http.CallBack;
import com.eastcompeace.lpa.sdk.http.HttpExcepsion;
import com.eastcompeace.lpa.sdk.http.HttpManager;
import com.eastcompeace.lpa.sdk.language.LanguageConfig;
import com.eastcompeace.lpa.sdk.log.ELog;
import com.taobao.accs.common.Constants;
import com.taobao.agoo.a.a.b;
import java.util.List;

public class UpdateHandler {
    private static final String TAG = "OldUpdateCosHandler";
    public static String api = "https://secsmsminiapp.eastcompeace.com:443/resolver-server";
    private static UpdateHandler eSimManager = new UpdateHandler();
    String appId;
    String appSecret;
    public IChannel channel;
    String serverUrl = api;

    public interface UpdateCallBack<T> {
        void onError(Exception exc);

        void onSuccess(T t);
    }

    public static UpdateHandler getInstance() {
        if (eSimManager == null) {
            eSimManager = new UpdateHandler();
        }
        return eSimManager;
    }

    public UpdateHandler init(String str, String str2, String str3, IChannel iChannel) {
        this.channel = iChannel;
        this.appId = str;
        this.appSecret = str2;
        if (!StringUtils.isEmpty(str3)) {
            this.serverUrl = str3;
        }
        return this;
    }

    public UpdateHandler init(String str, String str2, IChannel iChannel) {
        return init(str, str2, (String) null, iChannel);
    }

    public void update(final UpdateCallBack<String> updateCallBack) {
        try {
            authSe(getSCID(), new CallBack<String>() {
                public void onSuccess(String str) {
                    try {
                        String excute = UpdateHandler.this.excute(str);
                        if (StringUtils.isEmpty(excute)) {
                            updateCallBack.onError(new Exception("Update faild"));
                        } else {
                            updateCallBack.onSuccess(excute);
                        }
                    } catch (Exception e) {
                        updateCallBack.onError(e);
                    }
                }

                public void onError(Exception exc) {
                    ELog.e(UpdateHandler.TAG, exc.getMessage(), exc);
                    updateCallBack.onError(exc);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            updateCallBack.onError(e);
        }
    }

    public String excute(String str) throws Exception {
        List<String> A3_resolver = A3Util.A3_resolver(str);
        int size = A3_resolver.size();
        String str2 = "";
        for (int i = 0; i < size; i++) {
            String str3 = A3_resolver.get(i);
            if (!str3.equals(Constant.RESETTAG)) {
                String substring = str3.substring(str3.length() - 5, str3.length() - 1);
                String transmitAPDU = transmitAPDU(str3.substring(0, str3.length() - 6));
                if (!substring.equals(transmitAPDU.substring(transmitAPDU.length() - 4, transmitAPDU.length()))) {
                    throw new Exception("eUICC update return \nsendApdu: " + str3 + "\nresponse: " + transmitAPDU);
                } else if (i == size - 1) {
                    str2 = transmitAPDU;
                }
            }
        }
        return str2;
    }

    public void authSe(String str, final CallBack<String> callBack) {
        try {
            JSONObject jSONObject = new JSONObject();
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("SCID", (Object) str);
            jSONObject.put("seInfo", (Object) jSONObject2);
            String jSONObject3 = jSONObject.toString();
            new HttpManager.Builder().url(this.serverUrl + "/fota/es1/authSE").addHeader("appId", this.appId).addHeader("signature", HmacSHA256Util.sha256_HMAC(jSONObject3, this.appSecret)).addStringBody(jSONObject3).postBody().build().asyncExecute(new CallBack<JSONObject>() {
                public void onSuccess(JSONObject jSONObject) {
                    try {
                        JSONObject jSONObject2 = jSONObject.getJSONObject("resData");
                        if (jSONObject2 == null) {
                            callBack.onError(new HttpExcepsion(200, "No Response"));
                            return;
                        }
                        boolean booleanValue = jSONObject2.getBoolean(b.JSON_SUCCESS).booleanValue();
                        String string = jSONObject2.getString(Constants.SHARED_MESSAGE_ID_FILE);
                        int intValue = jSONObject2.getIntValue("code");
                        if (!booleanValue || intValue != 200) {
                            callBack.onError(new HttpExcepsion(intValue, string));
                            return;
                        }
                        String string2 = jSONObject2.getString("fup");
                        if (!StringUtils.isEmpty(string2)) {
                            callBack.onSuccess(string2);
                        } else {
                            callBack.onError(new HttpExcepsion(intValue, "fup is null"));
                        }
                    } catch (Exception e) {
                        ELog.e(UpdateHandler.TAG, "authSe", e);
                        callBack.onError(e);
                    }
                }

                public void onError(Exception exc) {
                    callBack.onError(exc);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getSCID() throws Exception {
        String transmitAPDU = transmitAPDU("80CA000050");
        if ("9000".equals(transmitAPDU.substring(transmitAPDU.length() - 4, transmitAPDU.length()))) {
            return transmitAPDU.substring(0, transmitAPDU.length() - 4);
        }
        throw new Exception(LanguageConfig.getInstance().euiccNotSupportUpdate());
    }

    public String transmitAPDU(String str) throws Exception {
        String transmitAPDU = this.channel.transmitAPDU(Integer.valueOf(str.substring(0, 2), 16).intValue(), Integer.valueOf(str.substring(2, 4), 16).intValue(), Integer.valueOf(str.substring(4, 6), 16).intValue(), Integer.valueOf(str.substring(6, 8), 16).intValue(), Integer.valueOf(str.substring(8, 10), 16).intValue(), str.substring(10, str.length()));
        ELog.i("data=" + str + "\nresponse=" + transmitAPDU);
        return transmitAPDU;
    }
}
