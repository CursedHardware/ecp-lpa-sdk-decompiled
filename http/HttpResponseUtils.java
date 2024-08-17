package com.eastcompeace.lpa.sdk.http;

import androidx.core.app.NotificationCompat;
import com.alibaba.fastjson.JSONObject;
import com.eastcompeace.lpa.sdk.utils.AppConfig;
import com.eastcompeace.lpa.sdk.utils.StringUtils;
import com.taobao.accs.common.Constants;

public class HttpResponseUtils {
    public static boolean isExecutedSucess(JSONObject jSONObject) throws RspResponseExcepsion {
        JSONObject jSONObject2;
        JSONObject jSONObject3 = jSONObject.getJSONObject("header");
        if (jSONObject3 == null || (jSONObject2 = jSONObject3.getJSONObject("functionExecutionStatus")) == null) {
            return false;
        }
        String string = jSONObject2.getString(NotificationCompat.CATEGORY_STATUS);
        if (StringUtils.isEmpty(string)) {
            return false;
        }
        if (AppConfig.STATUS_SUCCESS.equals(string)) {
            return true;
        }
        JSONObject jSONObject4 = jSONObject2.getJSONObject("statusCodeData");
        throw new RspResponseExcepsion(jSONObject4.getString("reasonCode"), jSONObject4.getString("subjectCode"), jSONObject4.getString(Constants.SHARED_MESSAGE_ID_FILE));
    }
}
