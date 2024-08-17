package com.eastcompeace.lpa.sdk;

import androidx.core.app.NotificationCompat;
import anet.channel.util.HttpConstant;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.eastcompeace.lpa.sdk.bean.cos.IssueTask;
import com.eastcompeace.lpa.sdk.bean.cos.Rule;
import com.eastcompeace.lpa.sdk.bean.cos.RunBody;
import com.eastcompeace.lpa.sdk.bean.cos.RunResponse;
import com.eastcompeace.lpa.sdk.http.CallBack;
import com.eastcompeace.lpa.sdk.http.HttpManager;
import com.eastcompeace.lpa.sdk.log.ELog;
import com.taobao.accs.common.Constants;
import com.taobao.agoo.a.a.b;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import okhttp3.Response;

public class UpdateCosHandler {
    private static final String TAG = "UpdateCosHandler";
    public static String api = "http://192.168.10.182/disp-server";

    public static void login(String str, String str2, final CallBack<String> callBack) {
        new HttpManager.Builder().url(api + "/api/gateway/v1.0.0/auth/login").postBody().addParams("accessId", str).addParams("accessSecret", str2).build().asyncExecuteForResponse(new CallBack<Response>() {
            public void onSuccess(Response response) {
                if (!response.isSuccessful() || 200 != response.code()) {
                    CallBack.this.onError(new Exception(String.valueOf(response.code())));
                    return;
                }
                try {
                    JSONObject parseObject = JSON.parseObject(response.body().string());
                    if (200 == parseObject.getInteger("code").intValue()) {
                        CallBack.this.onSuccess(response.header(HttpConstant.AUTHORIZATION));
                        return;
                    }
                    CallBack.this.onError(new Exception(parseObject.getString(Constants.SHARED_MESSAGE_ID_FILE)));
                } catch (IOException e) {
                    ELog.d(UpdateCosHandler.TAG, "login: ", e);
                    CallBack.this.onError(e);
                }
            }

            public void onError(Exception exc) {
                ELog.e(UpdateCosHandler.TAG, "login", exc);
                CallBack.this.onError(exc);
            }
        });
    }

    public static void getTaskList(String str, String str2, final CallBack<List<IssueTask>> callBack) {
        new HttpManager.Builder().url(api + "/api/disp-issue/issue/task/list").postBody().addHeader("authorization", str).addParams("productName", str2).build().asyncExecute(new CallBack<JSONObject>() {
            public void onSuccess(JSONObject jSONObject) {
                try {
                    if (200 == jSONObject.getIntValue("code")) {
                        ArrayList arrayList = new ArrayList();
                        JSONArray jSONArray = jSONObject.getJSONArray(Constants.KEY_DATA);
                        int size = jSONArray.size();
                        for (int i = 0; i < size; i++) {
                            JSONObject jSONObject2 = jSONArray.getJSONObject(i);
                            if (jSONObject2 != null) {
                                IssueTask issueTask = new IssueTask();
                                issueTask.setTid(jSONObject2.getString("tid"));
                                issueTask.setStatus(jSONObject2.getString(NotificationCompat.CATEGORY_STATUS));
                                issueTask.setLastDate(jSONObject2.getString("lastDate"));
                                JSONObject jSONObject3 = jSONObject2.getJSONObject("productInfo");
                                if (jSONObject3 != null) {
                                    IssueTask.ProductInfo productInfo = new IssueTask.ProductInfo();
                                    productInfo.setPid(jSONObject3.getString("pid"));
                                    productInfo.setName(jSONObject3.getString("name"));
                                    productInfo.setStatus(jSONObject3.getString(NotificationCompat.CATEGORY_STATUS));
                                    issueTask.setProductInfo(productInfo);
                                }
                                arrayList.add(issueTask);
                            }
                        }
                        CallBack.this.onSuccess(arrayList);
                        return;
                    }
                    CallBack.this.onError(new Exception(jSONObject.getString(Constants.SHARED_MESSAGE_ID_FILE)));
                } catch (Exception e) {
                    ELog.e(UpdateCosHandler.TAG, "login", e);
                    CallBack.this.onError(e);
                }
            }

            public void onError(Exception exc) {
                ELog.e(UpdateCosHandler.TAG, "login", exc);
                CallBack.this.onError(exc);
            }
        });
    }

    public static void getRuleList(String str, String str2, final CallBack<List<Rule>> callBack) {
        new HttpManager.Builder().url(api + "/api/disp-issue/issue/rule/list").postBody().addHeader("authorization", str).addParams("tid", str2).build().asyncExecute(new CallBack<JSONObject>() {
            public void onSuccess(JSONObject jSONObject) {
                try {
                    if (200 == jSONObject.getInteger("code").intValue()) {
                        ArrayList arrayList = new ArrayList();
                        JSONArray jSONArray = jSONObject.getJSONArray(Constants.KEY_DATA);
                        if (jSONArray != null) {
                            int size = jSONArray.size();
                            for (int i = 0; i < size; i++) {
                                JSONObject jSONObject2 = jSONArray.getJSONObject(i);
                                Rule rule = new Rule();
                                rule.setName(jSONObject2.getString("name"));
                                rule.setRid(jSONObject2.getString("rid"));
                                rule.setType(jSONObject2.getString("type"));
                                rule.setStatus(jSONObject2.getString(NotificationCompat.CATEGORY_STATUS));
                                arrayList.add(rule);
                            }
                        }
                        CallBack.this.onSuccess(arrayList);
                        return;
                    }
                    CallBack.this.onError(new Exception(jSONObject.getString(Constants.SHARED_MESSAGE_ID_FILE)));
                } catch (Exception e) {
                    ELog.e(UpdateCosHandler.TAG, "login", e);
                    CallBack.this.onError(e);
                }
            }

            public void onError(Exception exc) {
                ELog.e(UpdateCosHandler.TAG, "login", exc);
                CallBack.this.onError(exc);
            }
        });
    }

    public static void applyTransactionId(String str, String str2, final CallBack<String> callBack) {
        new HttpManager.Builder().url(api + "/api/dicp-issue/issue/applyTransactionId").postBody().addHeader("authorization", str).addParams("tid", str2).build().asyncExecute(new CallBack<JSONObject>() {
            public void onSuccess(JSONObject jSONObject) {
                try {
                    if (200 == jSONObject.getIntValue("code")) {
                        String str = "";
                        JSONObject jSONObject2 = jSONObject.getJSONObject(Constants.KEY_DATA);
                        if (jSONObject2 != null) {
                            str = jSONObject2.getString("transactionId");
                        }
                        CallBack.this.onSuccess(str);
                        return;
                    }
                    CallBack.this.onError(new Exception(jSONObject.getString(Constants.SHARED_MESSAGE_ID_FILE)));
                } catch (Exception e) {
                    ELog.e(UpdateCosHandler.TAG, "login", e);
                    CallBack.this.onError(e);
                }
            }

            public void onError(Exception exc) {
                ELog.e(UpdateCosHandler.TAG, "login", exc);
                CallBack.this.onError(exc);
            }
        });
    }

    public static void run(String str, RunBody runBody, final CallBack<RunResponse> callBack) {
        new HttpManager.Builder().url(api + "/api/dicp-issue/issue/run").postBody().addHeader("authorization", str).addStringBody(runBody.toJsonString()).build().asyncExecute(new CallBack<JSONObject>() {
            public void onSuccess(JSONObject jSONObject) {
                try {
                    if (200 == jSONObject.getIntValue("code")) {
                        RunResponse runResponse = new RunResponse();
                        JSONObject jSONObject2 = jSONObject.getJSONObject(Constants.KEY_DATA);
                        if (jSONObject2 != null) {
                            runResponse.setCode(jSONObject2.getString("code"));
                            runResponse.setMsg(jSONObject2.getString(NotificationCompat.CATEGORY_MESSAGE));
                            runResponse.setStatus(jSONObject2.getString(NotificationCompat.CATEGORY_STATUS));
                            runResponse.setSeid(jSONObject2.getString("seid"));
                            runResponse.setScriptName(jSONObject2.getString("scriptName"));
                            JSONArray jSONArray = jSONObject.getJSONArray("apdus");
                            if (jSONArray != null) {
                                int size = jSONObject2.size();
                                ArrayList arrayList = new ArrayList();
                                for (int i = 0; i < size; i++) {
                                    JSONObject jSONObject3 = jSONArray.getJSONObject(i);
                                    if (jSONObject3 != null) {
                                        RunResponse.Apdu apdu = new RunResponse.Apdu();
                                        apdu.setCode(jSONObject3.getString("code"));
                                        apdu.setReturnCode(jSONObject3.getString("returnCode"));
                                        apdu.setReturnRes(jSONObject3.getString("returnRes"));
                                        apdu.setApdu(jSONObject3.getString("apdu"));
                                        arrayList.add(apdu);
                                        runResponse.setApdus(arrayList);
                                    }
                                }
                            }
                        }
                        CallBack.this.onSuccess(runResponse);
                        return;
                    }
                    CallBack.this.onError(new Exception(jSONObject.getString(Constants.SHARED_MESSAGE_ID_FILE)));
                } catch (Exception e) {
                    ELog.e(UpdateCosHandler.TAG, "login", e);
                    CallBack.this.onError(e);
                }
            }

            public void onError(Exception exc) {
                ELog.e(UpdateCosHandler.TAG, "login", exc);
                CallBack.this.onError(exc);
            }
        });
    }

    public static void destroyTransactionId(String str, String str2, String str3, final CallBack<String> callBack) {
        new HttpManager.Builder().url(api + "/api/dicp-issue/issue/applyTransactionId").postBody().addHeader("authorization", str).addParams("tid", str2).addParams("transactionId", str3).build().asyncExecute(new CallBack<JSONObject>() {
            public void onSuccess(JSONObject jSONObject) {
                try {
                    if (200 == jSONObject.getIntValue("code")) {
                        String str = "";
                        JSONObject jSONObject2 = jSONObject.getJSONObject(Constants.KEY_DATA);
                        if (jSONObject2 != null) {
                            str = jSONObject2.getString("result");
                        }
                        if (str.toUpperCase().contains(HttpConstant.SUCCESS)) {
                            CallBack.this.onSuccess(b.JSON_SUCCESS);
                        } else {
                            CallBack.this.onError(new Exception(b.JSON_SUCCESS));
                        }
                    } else {
                        CallBack.this.onError(new Exception(jSONObject.getString(Constants.SHARED_MESSAGE_ID_FILE)));
                    }
                } catch (Exception e) {
                    ELog.e(UpdateCosHandler.TAG, "login", e);
                    CallBack.this.onError(e);
                }
            }

            public void onError(Exception exc) {
                ELog.e(UpdateCosHandler.TAG, "login", exc);
                CallBack.this.onError(exc);
            }
        });
    }
}
