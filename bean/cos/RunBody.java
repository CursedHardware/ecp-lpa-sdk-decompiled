package com.eastcompeace.lpa.sdk.bean.cos;

import com.eastcompeace.lpa.sdk.bean.BaseSerializableBean;
import com.taobao.accs.common.Constants;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RunBody extends BaseSerializableBean {
    private List<Data> datas;
    private String rid;
    private String seid;
    private String sessionId;
    private String tid;
    private String transactionId;

    public RunBody(String str, String str2, String str3, String str4, String str5, List<Data> list) {
        this.sessionId = str;
        this.transactionId = str2;
        this.tid = str3;
        this.rid = str4;
        this.seid = str5;
        this.datas = list;
    }

    public String getSessionId() {
        return this.sessionId;
    }

    public void setSessionId(String str) {
        this.sessionId = str;
    }

    public String getTransactionId() {
        return this.transactionId;
    }

    public void setTransactionId(String str) {
        this.transactionId = str;
    }

    public String getTid() {
        return this.tid;
    }

    public void setTid(String str) {
        this.tid = str;
    }

    public String getRid() {
        return this.rid;
    }

    public void setRid(String str) {
        this.rid = str;
    }

    public String getSeid() {
        return this.seid;
    }

    public void setSeid(String str) {
        this.seid = str;
    }

    public List<Data> getDatas() {
        return this.datas;
    }

    public void setDatas(List<Data> list) {
        this.datas = list;
    }

    public static class Data extends BaseSerializableBean {
        private String code;
        private String data;
        private String returnCode;
        private String returnRes;
        private String sw;

        public Data(String str, String str2, String str3, String str4, String str5) {
            this.code = str;
            this.returnCode = str2;
            this.returnRes = str3;
            this.data = str4;
            this.sw = str5;
        }

        public String getCode() {
            return this.code;
        }

        public void setCode(String str) {
            this.code = str;
        }

        public String getReturnCode() {
            return this.returnCode;
        }

        public void setReturnCode(String str) {
            this.returnCode = str;
        }

        public String getReturnRes() {
            return this.returnRes;
        }

        public void setReturnRes(String str) {
            this.returnRes = str;
        }

        public String getData() {
            return this.data;
        }

        public void setData(String str) {
            this.data = str;
        }

        public String getSw() {
            return this.sw;
        }

        public void setSw(String str) {
            this.sw = str;
        }

        public JSONObject getJSONObject() {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("code", this.code);
                jSONObject.put("returnCode", this.returnCode);
                jSONObject.put("returnRes", this.returnRes);
                jSONObject.put(Constants.KEY_DATA, this.data);
                jSONObject.put("sw", this.sw);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return jSONObject;
        }
    }

    public JSONObject getJSONObject() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("sessionId", this.sessionId);
            jSONObject.put("transactionId", this.transactionId);
            jSONObject.put("tid", this.tid);
            jSONObject.put("rid", this.rid);
            jSONObject.put("seid", this.seid);
            JSONArray jSONArray = new JSONArray();
            for (Data jSONObject2 : this.datas) {
                jSONArray.put(jSONObject2.getJSONObject());
            }
            jSONObject.put("datas", jSONArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject;
    }

    public String toJsonString() {
        return getJSONObject().toString();
    }
}
