package com.eastcompeace.lpa.sdk.procedures;

import com.alibaba.fastjson.JSONObject;
import com.eastcompeace.lpa.sdk.Es10xApduImpl;
import com.eastcompeace.lpa.sdk.Es9PlusFunctionImpl;
import com.eastcompeace.lpa.sdk.IEs10xFunction;
import com.eastcompeace.lpa.sdk.bean.es10.Notification;
import com.eastcompeace.lpa.sdk.http.HttpExcepsion;
import com.eastcompeace.lpa.sdk.log.ELog;
import com.eastcompeace.lpa.sdk.utils.Base64;
import com.eastcompeace.lpa.sdk.utils.StringUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NotificationProcedurs {
    private static final String TAG = "NotificationProcedurs";
    private static volatile NotificationProcedurs instance;
    /* access modifiers changed from: private */
    public IEs10xFunction es10xFunction;

    public NotificationProcedurs(IEs10xFunction iEs10xFunction) {
        this.es10xFunction = iEs10xFunction;
    }

    public static NotificationProcedurs getInstance(IEs10xFunction iEs10xFunction) {
        if (instance == null) {
            synchronized (Es10xApduImpl.class) {
                instance = new NotificationProcedurs(iEs10xFunction);
            }
        } else {
            instance.setEs10xFunction(iEs10xFunction);
        }
        return instance;
    }

    private void setEs10xFunction(IEs10xFunction iEs10xFunction) {
        this.es10xFunction = iEs10xFunction;
    }

    public void sendNotification(int i) {
        sendNotification(i, (String) null, (IEs10xFunction.CallBack) null);
    }

    public synchronized void sendNotification(int i, final String str, final IEs10xFunction.CallBack callBack) {
        this.es10xFunction.retrieveNotificationsList(i, new IEs10xFunction.CallBack<List<Notification>>() {
            public void onComplete(int i, List<Notification> list, String str) {
                int i2 = 0;
                if (i != 0) {
                    NotificationProcedurs notificationProcedurs = NotificationProcedurs.this;
                    notificationProcedurs.disposeCallBack(callBack, i, (Object) null, notificationProcedurs.getMsgJson("retrieveNotificationsList", 0, 0, str));
                } else if (list == null || list.size() <= 0) {
                    NotificationProcedurs notificationProcedurs2 = NotificationProcedurs.this;
                    notificationProcedurs2.disposeCallBack(callBack, i, (Object) null, notificationProcedurs2.getMsgJson("retrieveNotificationsList", 0, 0, str));
                } else {
                    int size = list.size();
                    ArrayList arrayList = new ArrayList();
                    if (!StringUtils.isEmpty(str)) {
                        for (int i3 = 0; i3 < size; i3++) {
                            Notification notification = list.get(i3);
                            if (str.equals(notification.getIccid())) {
                                arrayList.add(notification);
                            }
                        }
                        list = arrayList;
                    }
                    final int size2 = list.size();
                    if (size2 <= 0) {
                        NotificationProcedurs notificationProcedurs3 = NotificationProcedurs.this;
                        notificationProcedurs3.disposeCallBack(callBack, i, (Object) null, notificationProcedurs3.getMsgJson("retrieveNotificationsList", 0, 0, str));
                        return;
                    }
                    while (i2 < size2) {
                        final int i4 = i2 + 1;
                        ELog.d("finalI=" + i4);
                        Notification notification2 = list.get(i2);
                        String seqNumber = notification2.getSeqNumber();
                        ELog.i(NotificationProcedurs.TAG, "sendNotification seqNumber:" + seqNumber);
                        if (StringUtils.isEmpty(str) || str.equals(notification2.getIccid())) {
                            try {
                                Es9PlusFunctionImpl.getInstance().smdpAddress(notification2.getNotificationAddress()).handleNotification(Base64.encodeHex(notification2.getData()));
                                NotificationProcedurs.this.es10xFunction.removeNotificationFromList(seqNumber, new IEs10xFunction.CallBack<Void>() {
                                    public void onComplete(int i, Void voidR, String str) {
                                        NotificationProcedurs.this.disposeCallBack(callBack, i, (Object) null, NotificationProcedurs.this.getMsgJson("handleNotification", i4, size2, str));
                                    }
                                });
                            } catch (HttpExcepsion e) {
                                ELog.e(NotificationProcedurs.TAG, "sendNotificationsListToDp", e);
                                NotificationProcedurs notificationProcedurs4 = NotificationProcedurs.this;
                                notificationProcedurs4.disposeCallBack(callBack, -6, (Object) null, notificationProcedurs4.getMsgJson("handleNotification:", i4, size2, e.getMessage()));
                            } catch (IOException e2) {
                                ELog.e(NotificationProcedurs.TAG, "sendNotificationsListToDp", e2);
                                NotificationProcedurs notificationProcedurs5 = NotificationProcedurs.this;
                                notificationProcedurs5.disposeCallBack(callBack, -6, (Object) null, notificationProcedurs5.getMsgJson("handleNotification:", i4, size2, e2.getMessage()));
                            }
                        }
                        i2 = i4;
                    }
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

    /* access modifiers changed from: private */
    public String getMsgJson(String str, int i, int i2, String str2) {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("count", (Object) Integer.valueOf(i));
        jSONObject.put("total", (Object) Integer.valueOf(i2));
        jSONObject.put("errorMsg", (Object) str2);
        String jSONString = jSONObject.toJSONString();
        ELog.d(str + ": " + jSONString);
        return jSONString;
    }
}
