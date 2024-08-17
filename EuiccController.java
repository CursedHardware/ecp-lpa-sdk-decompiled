package com.eastcompeace.lpa.sdk;

import com.eastcompeace.lpa.sdk.IEs10xFunction;
import com.eastcompeace.lpa.sdk.bean.ActivationCode;
import com.eastcompeace.lpa.sdk.bean.es10.EuiccInfo1;
import com.eastcompeace.lpa.sdk.bean.es10.EuiccInfo2;
import com.eastcompeace.lpa.sdk.bean.es10.Notification;
import com.eastcompeace.lpa.sdk.bean.es10.ProfileInfo;
import com.eastcompeace.lpa.sdk.bean.es11.Es11AuthenticateClientBean;
import com.eastcompeace.lpa.sdk.bean.es9.AuthenticateClientBean;
import com.eastcompeace.lpa.sdk.log.ELog;
import com.eastcompeace.lpa.sdk.procedures.AuthProcedurs;
import com.eastcompeace.lpa.sdk.procedures.CancelSessionProcedurs;
import com.eastcompeace.lpa.sdk.procedures.Es11Procedures;
import com.eastcompeace.lpa.sdk.procedures.NotificationProcedurs;
import com.eastcompeace.lpa.sdk.procedures.ProfileDownloadAndInstallProcedures;
import com.eastcompeace.lpa.sdk.utils.StringUtils;
import com.xuexiang.xupdate.entity.UpdateError;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class EuiccController {
    private static final String TAG = "EuiccController";
    private static volatile EuiccController instance;
    private int count = 1;
    protected IEs10xFunction es10xFunction;
    private TimeUnit timeUnit = TimeUnit.MILLISECONDS;
    private int timeout = UpdateError.ERROR.INSTALL_FAILED;

    public EuiccController(IEs10xFunction iEs10xFunction) {
        this.es10xFunction = iEs10xFunction;
    }

    public static EuiccController getInstance(IEs10xFunction iEs10xFunction) {
        if (instance == null) {
            synchronized (Es10xApduImpl.class) {
                instance = new EuiccController(iEs10xFunction);
            }
        } else {
            instance.setEs10xFunction(iEs10xFunction);
        }
        return instance;
    }

    public EuiccController setImei(String str) {
        IEs10xFunction iEs10xFunction = this.es10xFunction;
        if (iEs10xFunction instanceof Es10xApduImpl) {
            ((Es10xApduImpl) iEs10xFunction).setImei(str);
        }
        return this;
    }

    public void setEs10xFunction(IEs10xFunction iEs10xFunction) {
        this.es10xFunction = iEs10xFunction;
    }

    public EuiccController slotId(int i) {
        IEs10xFunction iEs10xFunction = this.es10xFunction;
        if (iEs10xFunction instanceof IEs10xFunction) {
            iEs10xFunction.slotId(i);
        }
        return this;
    }

    public EuiccController isdpAid(String str) {
        IEs10xFunction iEs10xFunction = this.es10xFunction;
        if (iEs10xFunction instanceof Es10xApduImpl) {
            iEs10xFunction.isdpAid(str);
        }
        return this;
    }

    public EuiccController profileClass(String str) {
        IEs10xFunction iEs10xFunction = this.es10xFunction;
        if (iEs10xFunction instanceof Es10xApduImpl) {
            iEs10xFunction.profileClass(str);
        }
        return this;
    }

    public String getEid() throws Exception {
        return this.es10xFunction.getEid();
    }

    public void setDefaultDpAddress(String str, IEs10xFunction.CallBack callBack) {
        this.es10xFunction.setDefaultDpAddress(str, callBack);
    }

    public void getDefaultSmdpAddress(IEs10xFunction.CallBack<String> callBack) {
        this.es10xFunction.requestDefaultSmdpAddress(callBack);
    }

    public void getSmdsAddress(IEs10xFunction.CallBack<String> callBack) {
        this.es10xFunction.requestSmdsAddress(callBack);
    }

    public void getSmdpFromSmdsAddress(String str, IEs10xFunction.CallBack<Es11AuthenticateClientBean> callBack) {
        Es11Procedures.getInstance(this.es10xFunction).getSmdpFromSmdsAddress(str, callBack);
    }

    public void auth(ActivationCode activationCode, IEs10xFunction.CallBack<AuthenticateClientBean> callBack) {
        AuthProcedurs.getInstance(this.es10xFunction).auth(activationCode.getSMDPAddress(), activationCode.getACToken(), callBack);
    }

    public void auth(String str, String str2, IEs10xFunction.CallBack<AuthenticateClientBean> callBack) {
        AuthProcedurs.getInstance(this.es10xFunction).auth(str, str2, callBack);
    }

    public void downLoadProfile(AuthenticateClientBean authenticateClientBean, IEs10xFunction.CallBack callBack) {
        downLoadProfile(authenticateClientBean, (String) null, callBack);
    }

    public void downLoadProfile(AuthenticateClientBean authenticateClientBean, String str, IEs10xFunction.CallBack callBack) {
        ProfileDownloadAndInstallProcedures.getInstance(this.es10xFunction).downLoadProfile(authenticateClientBean, str, callBack);
    }

    public void cancelSession(String str, int i) {
        CancelSessionProcedurs.getInstance(this.es10xFunction).cancelSession(str, i, (IEs10xFunction.CallBack<Void>) null);
    }

    public void getProfileInfo(String str, IEs10xFunction.CallBack<ProfileInfo> callBack) {
        this.es10xFunction.getProfileInfo(str, callBack);
    }

    public void notificationOpera(int i) {
        notificationOpera(i, new IEs10xFunction.CallBack<Void>() {
            public void onComplete(int i, Void voidR, String str) {
                ELog.e(EuiccController.TAG, "notificationOpera:resultCode=" + i + " errorMsg=" + str);
            }
        });
    }

    public void notificationOpera(int i, IEs10xFunction.CallBack<Void> callBack) {
        notificationOpera(i, (String) null, callBack);
    }

    public void notificationOpera(int i, String str, IEs10xFunction.CallBack<Void> callBack) {
        NotificationProcedurs.getInstance(this.es10xFunction).sendNotification(i, str, callBack);
    }

    public void listNotification(int i, IEs10xFunction.CallBack<List<Notification>> callBack) {
        this.es10xFunction.listNotification(i, callBack);
    }

    public void removeNotificationFromList(String str, IEs10xFunction.CallBack<Void> callBack) {
        this.es10xFunction.removeNotificationFromList(str, callBack);
    }

    public void setNickName(String str, String str2, IEs10xFunction.CallBack<Void> callBack) {
        this.es10xFunction.setNickName(str, str2, callBack);
    }

    public void getProfilesInfos(IEs10xFunction.CallBack callBack) {
        this.es10xFunction.getProfileInfos(callBack);
    }

    public void disableProfile(String str, boolean z, IEs10xFunction.CallBack<Void> callBack) {
        this.es10xFunction.disableProfile(str, z, callBack);
    }

    public void enableProfile(String str, boolean z, IEs10xFunction.CallBack<Void> callBack) {
        this.es10xFunction.enableProfile(str, z, callBack);
    }

    public void deleteProfile(String str, IEs10xFunction.CallBack<Void> callBack) {
        this.es10xFunction.deleteProfile(str, callBack);
    }

    public void resetMemory(int i, IEs10xFunction.CallBack<Void> callBack) {
        this.es10xFunction.resetMemory(i, callBack);
    }

    public String getEUICCInfo1() throws Exception {
        try {
            return this.es10xFunction.getEUICCInfo1();
        } catch (Exception e) {
            ELog.e(TAG, "getEUICCInfo", e);
            throw e;
        }
    }

    public EuiccInfo1 getEUICCInfo1Obj() throws Exception {
        try {
            String eUICCInfo1 = this.es10xFunction.getEUICCInfo1();
            if (StringUtils.isEmpty(eUICCInfo1)) {
                return null;
            }
            return new EuiccInfo1(eUICCInfo1);
        } catch (Exception e) {
            ELog.e(TAG, "getEUICCInfo", e);
            throw e;
        }
    }

    public String getEUICCInfo2() throws Exception {
        try {
            return this.es10xFunction.getEUICCInfo2();
        } catch (Exception e) {
            ELog.e(TAG, "getEUICCInfo", e);
            throw e;
        }
    }

    public EuiccInfo2 getEUICCInfo2Obj() throws Exception {
        try {
            String eUICCInfo2 = this.es10xFunction.getEUICCInfo2();
            if (StringUtils.isEmpty(eUICCInfo2)) {
                return null;
            }
            return new EuiccInfo2(eUICCInfo2);
        } catch (Exception e) {
            ELog.e(TAG, "getEUICCInfo", e);
            throw e;
        }
    }
}
