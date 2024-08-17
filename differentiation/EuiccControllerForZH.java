package com.eastcompeace.lpa.sdk.differentiation;

import com.eastcompeace.lpa.sdk.Es10xApduImpl;
import com.eastcompeace.lpa.sdk.Es9PlusFunctionImpl;
import com.eastcompeace.lpa.sdk.GetBppHandler;
import com.eastcompeace.lpa.sdk.IEs10xFunction;
import com.eastcompeace.lpa.sdk.UpdateCosHandler;
import com.eastcompeace.lpa.sdk.bean.ActivationCode;
import com.eastcompeace.lpa.sdk.bean.cos.IssueTask;
import com.eastcompeace.lpa.sdk.bean.cos.Rule;
import com.eastcompeace.lpa.sdk.bean.cos.RunBody;
import com.eastcompeace.lpa.sdk.bean.cos.RunResponse;
import com.eastcompeace.lpa.sdk.bean.es10.EuiccInfo1;
import com.eastcompeace.lpa.sdk.bean.es10.EuiccInfo2;
import com.eastcompeace.lpa.sdk.bean.es10.Notification;
import com.eastcompeace.lpa.sdk.bean.es10.ProfileInfo;
import com.eastcompeace.lpa.sdk.bean.es10.TLVObject;
import com.eastcompeace.lpa.sdk.bean.es9.AuthenticateClientBean;
import com.eastcompeace.lpa.sdk.bean.es9.GetBoundProfilePackageBean;
import com.eastcompeace.lpa.sdk.http.CallBack;
import com.eastcompeace.lpa.sdk.http.HttpExcepsion;
import com.eastcompeace.lpa.sdk.http.RspResponseExcepsion;
import com.eastcompeace.lpa.sdk.language.LanguageConfig;
import com.eastcompeace.lpa.sdk.log.ELog;
import com.eastcompeace.lpa.sdk.procedures.AuthProcedurs;
import com.eastcompeace.lpa.sdk.utils.ApduHandler;
import com.eastcompeace.lpa.sdk.utils.Base64;
import com.eastcompeace.lpa.sdk.utils.ChannelExcuteException;
import com.eastcompeace.lpa.sdk.utils.EuiccErrorMapper;
import com.eastcompeace.lpa.sdk.utils.HexUtil;
import com.eastcompeace.lpa.sdk.utils.MyDigest;
import com.eastcompeace.lpa.sdk.utils.StringUtils;
import com.xuexiang.xupdate.entity.UpdateError;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class EuiccControllerForZH {
    private static final String TAG = "EuiccController";
    private static volatile EuiccControllerForZH instance;
    private int count = 1;
    IEs10xFunction es10xFunction;
    private TimeUnit timeUnit = TimeUnit.MILLISECONDS;
    private int timeout = UpdateError.ERROR.INSTALL_FAILED;

    public EuiccControllerForZH(IEs10xFunction iEs10xFunction) {
        this.es10xFunction = iEs10xFunction;
    }

    public static EuiccControllerForZH getInstance(IEs10xFunction iEs10xFunction) {
        if (instance == null) {
            synchronized (Es10xApduImpl.class) {
                instance = new EuiccControllerForZH(iEs10xFunction);
            }
        } else {
            instance.setEs10xFunction(iEs10xFunction);
        }
        return instance;
    }

    public EuiccControllerForZH setImei(String str) {
        IEs10xFunction iEs10xFunction = this.es10xFunction;
        if (iEs10xFunction instanceof Es10xApduImpl) {
            ((Es10xApduImpl) iEs10xFunction).setImei(str);
        }
        return this;
    }

    public void setEs10xFunction(IEs10xFunction iEs10xFunction) {
        this.es10xFunction = iEs10xFunction;
    }

    public EuiccControllerForZH slotId(int i) {
        IEs10xFunction iEs10xFunction = this.es10xFunction;
        if (iEs10xFunction instanceof IEs10xFunction) {
            iEs10xFunction.slotId(i);
        }
        return this;
    }

    public EuiccControllerForZH isdpAid(String str) {
        IEs10xFunction iEs10xFunction = this.es10xFunction;
        if (iEs10xFunction instanceof Es10xApduImpl) {
            iEs10xFunction.isdpAid(str);
        }
        return this;
    }

    public EuiccControllerForZH profileClass(String str) {
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

    public void Auth(ActivationCode activationCode, IEs10xFunction.CallBack<AuthenticateClientBean> callBack) {
        AuthProcedurs.getInstance(this.es10xFunction).auth(activationCode.getSMDPAddress(), activationCode.getACToken(), callBack);
    }

    /* access modifiers changed from: private */
    public void Es10ExcuteError(IEs10xFunction.CallBack callBack, Exception exc) {
        int i;
        if (exc instanceof ChannelExcuteException) {
            int resultCode = ((ChannelExcuteException) exc).getResultCode();
            if (resultCode == -2) {
                i = EuiccErrorMapper.getOperationAndErrorCode(3, EuiccErrorMapper.ERROR_EUICC_MISSING);
            } else if (resultCode == -3) {
                i = EuiccErrorMapper.getOperationAndErrorCode(3, 0);
            } else if (resultCode == -1) {
                i = EuiccErrorMapper.getOperationAndErrorCode(3, 0);
            } else {
                i = EuiccErrorMapper.getOperationAndErrorCode(1, 0);
            }
        } else {
            i = EuiccErrorMapper.getOperationAndErrorCode(1, 0);
        }
        callBack.onComplete(i, null, exc.getMessage());
    }

    /* access modifiers changed from: private */
    public void Es9HttpError(IEs10xFunction.CallBack callBack, Exception exc) {
        if (exc instanceof RspResponseExcepsion) {
            RspResponseExcepsion rspResponseExcepsion = (RspResponseExcepsion) exc;
            callBack.onComplete(EuiccErrorMapper.getOperationSmdxSubjectReasonCode(rspResponseExcepsion.getReasonCode(), rspResponseExcepsion.getSubjectCode()), null, rspResponseExcepsion.getErrorS());
        } else if (exc instanceof HttpExcepsion) {
            HttpExcepsion httpExcepsion = (HttpExcepsion) exc;
            callBack.onComplete(EuiccErrorMapper.getOperationAndErrorCode(11, httpExcepsion.getCode()), null, httpExcepsion.getCode() + "");
        } else {
            callBack.onComplete(EuiccErrorMapper.getOperationAndErrorCode(1, 0), null, exc.getMessage());
        }
    }

    public void downLoadProfile(AuthenticateClientBean authenticateClientBean, IEs10xFunction.CallBack callBack) {
        downLoadProfile(authenticateClientBean, (String) null, callBack);
    }

    public void downLoadProfileAndReturnBpp(final AuthenticateClientBean authenticateClientBean, String str, final IEs10xFunction.CallBack callBack, final GetBppHandler.BppCallBack bppCallBack) {
        byte[] bArr;
        byte[] bArr2 = null;
        if (getProfileInfo(authenticateClientBean.getStoreMetadata().getIccid()) != null) {
            callBack.onComplete(EuiccErrorMapper.getOperationAndErrorCode(7, EuiccErrorMapper.ERROR_INSTALL_PROFILE), null, LanguageConfig.getInstance().profileExists());
            cancelSession(authenticateClientBean.getTransactionId(), 3);
            return;
        }
        try {
            if (!StringUtils.isEmpty(str)) {
                str = HexUtil.encodeHexStr(MyDigest.sha256(str.getBytes()));
                bArr = MyDigest.sha256Byte(str + authenticateClientBean.getTransactionId());
            } else {
                bArr = null;
            }
            ELog.d(TAG, "confirmationCode:" + str);
            try {
                ELog.d(TAG, "--------------Es10 prepareDownload-----------------");
                IEs10xFunction iEs10xFunction = this.es10xFunction;
                if (bArr != null) {
                    bArr2 = bArr;
                }
                String prepareDownload = iEs10xFunction.prepareDownload(bArr2, authenticateClientBean.getSmdpSigned2(), authenticateClientBean.getSmdpSignature2(), authenticateClientBean.getSmdpCertificate());
                ELog.d(TAG, "Es10 prepareDownload response: " + prepareDownload);
                ELog.d(TAG, "--------------Es9 getBoundProfilePackage-----------------");
                Es9PlusFunctionImpl.getInstance().getBoundProfilePackage(authenticateClientBean.getTransactionId(), Base64.encodeHex(prepareDownload), new CallBack<GetBoundProfilePackageBean>() {
                    public void onSuccess(GetBoundProfilePackageBean getBoundProfilePackageBean) {
                        ELog.d(EuiccControllerForZH.TAG, "Es9+ authenticateClient success response: " + getBoundProfilePackageBean.toString());
                        bppCallBack.getBppAndProfileMetadata(HexUtil.encodeHexStr(authenticateClientBean.getProfileMetadata()), HexUtil.encodeHexStr(getBoundProfilePackageBean.getBoundProfilePackage()));
                        EuiccControllerForZH.this.loadBoundProfilePackage(getBoundProfilePackageBean, callBack);
                    }

                    public void onError(Exception exc) {
                        ELog.e(EuiccControllerForZH.TAG, "getBoundProfilePackage error", exc);
                        EuiccControllerForZH.this.cancelSession(authenticateClientBean.getTransactionId(), 3);
                        EuiccControllerForZH.this.Es9HttpError(callBack, exc);
                    }
                });
            } catch (Exception e) {
                ELog.e(TAG, "prepareDownload error", e);
                cancelSession(authenticateClientBean.getTransactionId(), 127);
                Es10ExcuteError(callBack, e);
            }
        } catch (NoSuchAlgorithmException e2) {
            ELog.e(TAG, "downLoadProfile", e2);
            cancelSession(authenticateClientBean.getTransactionId(), 0);
            callBack.onComplete(EuiccErrorMapper.getOperationAndErrorCode(1, 0), null, e2.getMessage());
        }
    }

    public void downLoadProfile(final AuthenticateClientBean authenticateClientBean, String str, final IEs10xFunction.CallBack callBack) {
        byte[] bArr;
        byte[] bArr2 = null;
        if (getProfileInfo(authenticateClientBean.getStoreMetadata().getIccid()) != null) {
            callBack.onComplete(EuiccErrorMapper.getOperationAndErrorCode(7, EuiccErrorMapper.ERROR_INSTALL_PROFILE), null, LanguageConfig.getInstance().profileExists());
            cancelSession(authenticateClientBean.getTransactionId(), 3);
            return;
        }
        try {
            if (!StringUtils.isEmpty(str)) {
                str = HexUtil.encodeHexStr(MyDigest.sha256(str.getBytes()));
                bArr = MyDigest.sha256Byte(str + authenticateClientBean.getTransactionId());
            } else {
                bArr = null;
            }
            ELog.d(TAG, "confirmationCode:" + str);
            try {
                ELog.d(TAG, "--------------Es10 prepareDownload-----------------");
                IEs10xFunction iEs10xFunction = this.es10xFunction;
                if (bArr != null) {
                    bArr2 = bArr;
                }
                String prepareDownload = iEs10xFunction.prepareDownload(bArr2, authenticateClientBean.getSmdpSigned2(), authenticateClientBean.getSmdpSignature2(), authenticateClientBean.getSmdpCertificate());
                ELog.d(TAG, "Es10 prepareDownload response: " + prepareDownload);
                ELog.d(TAG, "--------------Es9 getBoundProfilePackage-----------------");
                Es9PlusFunctionImpl.getInstance().getBoundProfilePackage(authenticateClientBean.getTransactionId(), Base64.encodeHex(prepareDownload), new CallBack<GetBoundProfilePackageBean>() {
                    public void onSuccess(GetBoundProfilePackageBean getBoundProfilePackageBean) {
                        ELog.d(EuiccControllerForZH.TAG, "Es9+ authenticateClient success response: " + getBoundProfilePackageBean.toString());
                        EuiccControllerForZH.this.loadBoundProfilePackage(getBoundProfilePackageBean, callBack);
                    }

                    public void onError(Exception exc) {
                        ELog.e(EuiccControllerForZH.TAG, "getBoundProfilePackage error", exc);
                        EuiccControllerForZH.this.cancelSession(authenticateClientBean.getTransactionId(), 3);
                        EuiccControllerForZH.this.Es9HttpError(callBack, exc);
                    }
                });
            } catch (Exception e) {
                ELog.e(TAG, "prepareDownload error", e);
                cancelSession(authenticateClientBean.getTransactionId(), 127);
                Es10ExcuteError(callBack, e);
            }
        } catch (NoSuchAlgorithmException e2) {
            ELog.e(TAG, "downLoadProfile", e2);
            cancelSession(authenticateClientBean.getTransactionId(), 0);
            callBack.onComplete(EuiccErrorMapper.getOperationAndErrorCode(1, 0), null, e2.getMessage());
        }
    }

    /* access modifiers changed from: private */
    public void loadBoundProfilePackage(final GetBoundProfilePackageBean getBoundProfilePackageBean, final IEs10xFunction.CallBack callBack) {
        ELog.d(TAG, "--------------Es10 loadBoundProfilePackage-----------------");
        this.es10xFunction.loadBoundProfilePackage(getBoundProfilePackageBean.getBoundProfilePackage(), new IEs10xFunction.CallBack<String>() {
            public void onComplete(int i, String str, String str2) {
                ELog.d(EuiccControllerForZH.TAG, "Es10 loadBoundProfilePackage code:" + i + "   response:" + str);
                if (i == 0) {
                    List<TLVObject> tLVByTag = ApduHandler.getTLVByTag("A0", str, true);
                    if (tLVByTag == null || tLVByTag.size() <= 0 || tLVByTag.get(0).getChildrenTLV() == null) {
                        EuiccControllerForZH.this.cancelSession(getBoundProfilePackageBean.getTransactionId(), 5);
                        callBack.onComplete(EuiccErrorMapper.getOperationAndErrorCode(8, 0), null, LanguageConfig.getInstance().profileInstallFailedWithApdu() + str);
                        return;
                    }
                    EuiccControllerForZH.this.downloadNotification(str, (IEs10xFunction.CallBack) null);
                    callBack.onComplete(0, str, str2);
                    return;
                }
                EuiccControllerForZH.this.cancelSession(getBoundProfilePackageBean.getTransactionId(), 5);
                EuiccControllerForZH.this.Es10ExcuteError(callBack, new ChannelExcuteException(i, str2));
            }
        });
    }

    public void downloadNotification(String str, final IEs10xFunction.CallBack callBack) {
        System.out.println("downloadNotification" + str);
        List<TLVObject> tLVByTag = ApduHandler.getTLVByTag("BF2F", str, true);
        if (tLVByTag != null && tLVByTag.size() == 1) {
            final Notification notification = new Notification(tLVByTag.get(0).getChildrenTLV());
            Es9PlusFunctionImpl.getInstance().smdpAddress(notification.getNotificationAddress()).handleNotification(Base64.encodeHex(str), new CallBack() {
                public void onSuccess(Object obj) {
                    ELog.d(EuiccControllerForZH.TAG, obj.toString());
                    IEs10xFunction.CallBack callBack = callBack;
                    if (callBack != null) {
                        callBack.onComplete(0, null, "");
                    }
                }

                public void onError(Exception exc) {
                    if ("204".equals(exc.getMessage())) {
                        ELog.d(EuiccControllerForZH.TAG, "downloadNotification：Send notification success");
                        IEs10xFunction.CallBack callBack = callBack;
                        if (callBack != null) {
                            callBack.onComplete(0, null, "");
                        }
                        EuiccControllerForZH.this.removeNotificationFromList(notification.getSeqNumber(), new IEs10xFunction.CallBack() {
                            public void onComplete(int i, Object obj, String str) {
                                ELog.d(EuiccControllerForZH.TAG, "downloadNotification：Remove notification resultCode=" + i);
                            }
                        });
                        return;
                    }
                    IEs10xFunction.CallBack callBack2 = callBack;
                    if (callBack2 != null) {
                        callBack2.onComplete(-6, null, exc.getMessage());
                    }
                    ELog.e(EuiccControllerForZH.TAG, "downloadNotification：Send notification failed", exc);
                }
            });
        }
    }

    public void cancelSession(final String str, int i) {
        this.es10xFunction.cancelSession(str, i, new IEs10xFunction.CallBack<String>() {
            public void onComplete(int i, String str, String str2) {
                Es9PlusFunctionImpl.getInstance().cancelSession(str, Base64.encodeHex(str), new CallBack() {
                    public void onSuccess(Object obj) {
                        ELog.d(EuiccControllerForZH.TAG, "cancelSession success");
                    }

                    public void onError(Exception exc) {
                        ELog.e(EuiccControllerForZH.TAG, "cancelSession", exc);
                    }
                });
            }
        });
    }

    private ProfileInfo getProfileInfo(String str) {
        final CountDownLatch countDownLatch = new CountDownLatch(this.count);
        final ProfileInfo[] profileInfoArr = new ProfileInfo[1];
        this.es10xFunction.getProfileInfo(str, new IEs10xFunction.CallBack<ProfileInfo>() {
            public void onComplete(int i, ProfileInfo profileInfo, String str) {
                if (i == 0) {
                    profileInfoArr[0] = profileInfo;
                }
                countDownLatch.countDown();
            }
        });
        try {
            countDownLatch.await((long) this.timeout, this.timeUnit);
            return profileInfoArr[0];
        } catch (InterruptedException e) {
            ELog.e(TAG, "getProfileInfo", e);
            return profileInfoArr[0];
        } catch (Throwable unused) {
            return profileInfoArr[0];
        }
    }

    public void notificationOpera(int i) {
        notificationOpera(i, new IEs10xFunction.CallBack<Void>() {
            public void onComplete(int i, Void voidR, String str) {
                ELog.e(EuiccControllerForZH.TAG, "notificationOpera:resultCode=" + i + " errorMsg=" + str);
            }
        });
    }

    public void notificationOpera(final int i, final IEs10xFunction.CallBack<Void> callBack) {
        listNotification(i, new IEs10xFunction.CallBack<List<Notification>>() {
            public void onComplete(int i, List<Notification> list, String str) {
                ELog.d(EuiccControllerForZH.TAG, "notificationOpera: resultCode=" + i + " errorMsg=" + str);
                if (i != 0) {
                    callBack.onComplete(i, null, str);
                } else if (list == null || list.size() <= 0) {
                    ELog.d(EuiccControllerForZH.TAG, "notificationOpera_retrieveNotifications: resultCode=" + i + " errorMsg=No notification");
                    callBack.onComplete(i, null, "No notification");
                } else {
                    EuiccControllerForZH.this.operaRetrieveNotificationsList(i, callBack);
                }
            }
        });
    }

    public void notificationOpera(int i, final String str, final IEs10xFunction.CallBack<Void> callBack) {
        listNotification(i, new IEs10xFunction.CallBack<List<Notification>>() {
            public void onComplete(int i, List<Notification> list, String str) {
                if (i != 0) {
                    callBack.onComplete(i, null, str);
                } else if (list == null || list.size() <= 0) {
                    ELog.d(EuiccControllerForZH.TAG, "notificationOpera_retrieveNotifications: resultCode=" + i + " errorMsg=No notification");
                    callBack.onComplete(i, null, "No notification");
                } else {
                    for (Notification next : list) {
                        if (next.getIccid().equals(ProfileInfo.getApduIccid(str))) {
                            EuiccControllerForZH.this.operaRetrieveNotificationsList(next.getSeqNumberHexStr(), callBack);
                            return;
                        }
                    }
                    callBack.onComplete(-1, null, "No notification");
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void operaRetrieveNotificationsList(String str, final IEs10xFunction.CallBack callBack) {
        retrieveNotificationsList(str, (IEs10xFunction.CallBack<Notification>) new IEs10xFunction.CallBack<Notification>() {
            public void onComplete(int i, Notification notification, String str) {
                if (i == 0) {
                    EuiccControllerForZH.this.sendNotificationsListToDp(notification, callBack);
                } else {
                    callBack.onComplete(i, null, str);
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void operaRetrieveNotificationsList(int i, final IEs10xFunction.CallBack callBack) {
        retrieveNotificationsList(i, (IEs10xFunction.CallBack<List<Notification>>) new IEs10xFunction.CallBack<List<Notification>>() {
            public void onComplete(int i, List<Notification> list, String str) {
                if (i != 0) {
                    callBack.onComplete(i, null, str);
                } else if (list == null || list.size() <= 0) {
                    ELog.d(EuiccControllerForZH.TAG, "notificationOpera_retrieveNotifications: resultCode=" + i + " errorMsg=No retrieveNotifications found");
                    callBack.onComplete(i, null, "No retrieveNotifications found");
                } else {
                    for (Notification access$500 : list) {
                        EuiccControllerForZH.this.sendNotificationsListToDp(access$500, callBack);
                    }
                }
            }
        });
    }

    public void listNotification(int i, IEs10xFunction.CallBack<List<Notification>> callBack) {
        this.es10xFunction.listNotification(i, callBack);
    }

    public void retrieveNotificationsList(int i, IEs10xFunction.CallBack<List<Notification>> callBack) {
        this.es10xFunction.retrieveNotificationsList(i, callBack);
    }

    public void retrieveNotificationsList(String str, IEs10xFunction.CallBack<Notification> callBack) {
        this.es10xFunction.retrieveNotification(str, callBack);
    }

    /* access modifiers changed from: private */
    public void sendNotificationsListToDp(final Notification notification, final IEs10xFunction.CallBack callBack) {
        Es9PlusFunctionImpl.getInstance().smdpAddress(notification.getNotificationAddress()).handleNotification(Base64.encodeHex(notification.getData()), new CallBack() {
            public void onSuccess(Object obj) {
                EuiccControllerForZH.this.removeNotificationFromList(notification.getSeqNumber(), callBack);
            }

            public void onError(Exception exc) {
                if ("204".equals(exc.getMessage())) {
                    EuiccControllerForZH.this.removeNotificationFromList(notification.getSeqNumber(), callBack);
                } else {
                    callBack.onComplete(-6, null, exc.getMessage());
                }
            }
        });
    }

    public void sendNotificationToDp(String str, String str2, final IEs10xFunction.CallBack callBack) {
        Es9PlusFunctionImpl.getInstance().smdpAddress(str).handleNotification(Base64.encodeHex(str2), new CallBack() {
            public void onSuccess(Object obj) {
                callBack.onComplete(0, obj, (String) null);
            }

            public void onError(Exception exc) {
                if ("204".equals(exc.getMessage())) {
                    callBack.onComplete(0, null, (String) null);
                } else {
                    callBack.onComplete(-6, null, exc.getMessage());
                }
            }
        });
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

    public EuiccInfo2 getEUICCInfo2Obj() {
        try {
            String eUICCInfo2 = this.es10xFunction.getEUICCInfo2();
            if (StringUtils.isEmpty(eUICCInfo2)) {
                return null;
            }
            return new EuiccInfo2(eUICCInfo2);
        } catch (Exception e) {
            ELog.e(TAG, "getEUICCInfo", e);
            return null;
        }
    }

    public void updateCosLogin(String str, String str2, CallBack<String> callBack) {
        UpdateCosHandler.login(str, str2, callBack);
    }

    public void updateCosGetTaskList(String str, String str2, CallBack<List<IssueTask>> callBack) {
        UpdateCosHandler.getTaskList(str, str2, callBack);
    }

    public void updateCosGetRuleList(String str, String str2, CallBack<List<Rule>> callBack) {
        UpdateCosHandler.getRuleList(str, str2, callBack);
    }

    public void updateCosApplyTransactionId(String str, String str2, CallBack<String> callBack) {
        UpdateCosHandler.applyTransactionId(str, str2, callBack);
    }

    public void updateCosRun(String str, RunBody runBody, CallBack<RunResponse> callBack) {
        UpdateCosHandler.run(str, runBody, callBack);
    }

    public void updateCosDestroyTransactionId(String str, String str2, String str3, CallBack<String> callBack) {
        UpdateCosHandler.destroyTransactionId(str, str2, str3, callBack);
    }
}
