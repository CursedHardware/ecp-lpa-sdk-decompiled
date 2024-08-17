package com.eastcompeace.lpa.sdk.manage;

import android.os.Handler;
import com.eastcompeace.lpa.sdk.Es10xApduImpl;
import com.eastcompeace.lpa.sdk.EuiccController;
import com.eastcompeace.lpa.sdk.IChannel;
import com.eastcompeace.lpa.sdk.IEs10xFunction;
import com.eastcompeace.lpa.sdk.bean.ActivationCode;
import com.eastcompeace.lpa.sdk.bean.es10.EuiccInfo1;
import com.eastcompeace.lpa.sdk.bean.es10.EuiccInfo2;
import com.eastcompeace.lpa.sdk.bean.es10.Notification;
import com.eastcompeace.lpa.sdk.bean.es10.ProfileInfo;
import com.eastcompeace.lpa.sdk.bean.es11.Es11AuthenticateClientBean;
import com.eastcompeace.lpa.sdk.bean.es9.AuthenticateClientBean;
import com.eastcompeace.lpa.sdk.log.ELog;
import com.eastcompeace.lpa.sdk.log.Ilog;
import com.eastcompeace.lpa.sdk.utils.StringUtils;
import com.ecp.lpa.common.ThreadManager;
import java.util.List;
import java.util.Objects;

public class OMAManager {
    public static final String AID = "A0000005591010FFFFFFFF8900000100";
    private static final String TAG = "OMAManager";
    private static volatile OMAManager instance;
    /* access modifiers changed from: private */
    public EuiccController euiccController;
    /* access modifiers changed from: private */
    public Handler handler = new Handler();

    private OMAManager() {
    }

    public static OMAManager getInstance() {
        OMAManager oMAManager;
        synchronized (OMAManager.class) {
            if (instance == null) {
                instance = new OMAManager();
            }
            oMAManager = instance;
        }
        return oMAManager;
    }

    public OMAManager init(IChannel iChannel, String str) {
        Objects.requireNonNull(iChannel, "Channel cannot be null");
        if (!StringUtils.isEmpty(str)) {
            this.euiccController = new EuiccController(new Es10xApduImpl(iChannel, str));
            return this;
        }
        throw new NullPointerException("IMEI cannot be null");
    }

    public OMAManager setLog(Ilog ilog) {
        ELog.setLog(ilog);
        return this;
    }

    public EuiccController getEuiccController() {
        return this.euiccController;
    }

    public void getProfilesInfos(final IEs10xFunction.CallBack<List<ProfileInfo>> callBack) {
        if (checkEuiccController(callBack)) {
            ThreadManager.initExceutor().execute(new Runnable() {
                public void run() {
                    OMAManager.this.euiccController.getProfilesInfos(new IEs10xFunction.CallBack<List<ProfileInfo>>() {
                        public void onComplete(final int i, final List<ProfileInfo> list, final String str) {
                            OMAManager.this.handler.post(new Runnable() {
                                public void run() {
                                    callBack.onComplete(i, list, str);
                                }
                            });
                        }
                    });
                }
            });
        }
    }

    public void enableProfile(final String str, final boolean z, final IEs10xFunction.CallBack<Void> callBack) {
        if (checkEuiccController(callBack)) {
            ThreadManager.initExceutor().execute(new Runnable() {
                public void run() {
                    OMAManager.this.euiccController.enableProfile(str, z, new IEs10xFunction.CallBack<Void>() {
                        public void onComplete(final int i, final Void voidR, final String str) {
                            OMAManager.this.handler.post(new Runnable() {
                                public void run() {
                                    callBack.onComplete(i, voidR, str);
                                }
                            });
                        }
                    });
                }
            });
        }
    }

    public void disableProfile(final String str, final boolean z, final IEs10xFunction.CallBack<Void> callBack) {
        if (checkEuiccController(callBack)) {
            ThreadManager.initExceutor().execute(new Runnable() {
                public void run() {
                    OMAManager.this.euiccController.disableProfile(str, z, new IEs10xFunction.CallBack<Void>() {
                        public void onComplete(final int i, final Void voidR, final String str) {
                            OMAManager.this.handler.post(new Runnable() {
                                public void run() {
                                    callBack.onComplete(i, voidR, str);
                                }
                            });
                        }
                    });
                }
            });
        }
    }

    public void deleteProfile(final String str, final IEs10xFunction.CallBack<Void> callBack) {
        if (checkEuiccController(callBack)) {
            ThreadManager.initExceutor().execute(new Runnable() {
                public void run() {
                    OMAManager.this.euiccController.deleteProfile(str, new IEs10xFunction.CallBack<Void>() {
                        public void onComplete(final int i, final Void voidR, final String str) {
                            OMAManager.this.handler.post(new Runnable() {
                                public void run() {
                                    callBack.onComplete(i, voidR, str);
                                }
                            });
                        }
                    });
                }
            });
        }
    }

    public void setNickName(final String str, final String str2, final IEs10xFunction.CallBack<Void> callBack) {
        if (checkEuiccController(callBack)) {
            ThreadManager.initExceutor().execute(new Runnable() {
                public void run() {
                    OMAManager.this.euiccController.setNickName(str, str2, new IEs10xFunction.CallBack<Void>() {
                        public void onComplete(final int i, final Void voidR, final String str) {
                            OMAManager.this.handler.post(new Runnable() {
                                public void run() {
                                    callBack.onComplete(i, voidR, str);
                                }
                            });
                        }
                    });
                }
            });
        }
    }

    public void downLoadStepOne(final String str, final String str2, final IEs10xFunction.CallBack<AuthenticateClientBean> callBack) {
        if (checkEuiccController(callBack)) {
            ThreadManager.initExceutor().execute(new Runnable() {
                public void run() {
                    OMAManager.this.euiccController.auth(str, str2, new IEs10xFunction.CallBack<AuthenticateClientBean>() {
                        public void onComplete(final int i, final AuthenticateClientBean authenticateClientBean, final String str) {
                            OMAManager.this.handler.post(new Runnable() {
                                public void run() {
                                    callBack.onComplete(i, authenticateClientBean, str);
                                }
                            });
                        }
                    });
                }
            });
        }
    }

    public void downLoadStepOne(final ActivationCode activationCode, final IEs10xFunction.CallBack<AuthenticateClientBean> callBack) {
        if (checkEuiccController(callBack)) {
            ThreadManager.initExceutor().execute(new Runnable() {
                public void run() {
                    OMAManager.this.euiccController.auth(activationCode, new IEs10xFunction.CallBack<AuthenticateClientBean>() {
                        public void onComplete(final int i, final AuthenticateClientBean authenticateClientBean, final String str) {
                            OMAManager.this.handler.post(new Runnable() {
                                public void run() {
                                    callBack.onComplete(i, authenticateClientBean, str);
                                }
                            });
                        }
                    });
                }
            });
        }
    }

    public void downLoadStepTwo(final AuthenticateClientBean authenticateClientBean, final String str, final IEs10xFunction.CallBack<String> callBack) {
        if (checkEuiccController(callBack)) {
            ThreadManager.initExceutor().execute(new Runnable() {
                public void run() {
                    OMAManager.this.euiccController.downLoadProfile(authenticateClientBean, str, new IEs10xFunction.CallBack<String>() {
                        public void onComplete(final int i, final String str, final String str2) {
                            OMAManager.this.handler.post(new Runnable() {
                                public void run() {
                                    callBack.onComplete(i, str, str2);
                                }
                            });
                        }
                    });
                }
            });
        }
    }

    private boolean checkEuiccController(IEs10xFunction.CallBack callBack) {
        if (this.euiccController != null) {
            return true;
        }
        if (callBack == null) {
            return false;
        }
        callBack.onComplete(-2, null, (String) null);
        return false;
    }

    public void notificationOpera(IEs10xFunction.CallBack<Void> callBack) {
        notificationOpera(15, (String) null, callBack);
    }

    public void notificationOpera(int i, IEs10xFunction.CallBack<Void> callBack) {
        notificationOpera(i, (String) null, callBack);
    }

    public void notificationOpera(final int i, final String str, final IEs10xFunction.CallBack<Void> callBack) {
        if (checkEuiccController(callBack)) {
            ThreadManager.initExceutor().execute(new Runnable() {
                public void run() {
                    OMAManager.this.euiccController.notificationOpera(i, str, callBack);
                }
            });
        }
    }

    public void removeNotificationFromList(final String str, final IEs10xFunction.CallBack<Void> callBack) {
        if (checkEuiccController(callBack)) {
            ThreadManager.initExceutor().execute(new Runnable() {
                public void run() {
                    OMAManager.this.euiccController.removeNotificationFromList(str, new IEs10xFunction.CallBack<Void>() {
                        public void onComplete(final int i, final Void voidR, final String str) {
                            OMAManager.this.handler.post(new Runnable() {
                                public void run() {
                                    callBack.onComplete(i, voidR, str);
                                }
                            });
                        }
                    });
                }
            });
        }
    }

    public void listNotification(final int i, final IEs10xFunction.CallBack<List<Notification>> callBack) {
        if (checkEuiccController(callBack)) {
            ThreadManager.initExceutor().execute(new Runnable() {
                public void run() {
                    OMAManager.this.euiccController.listNotification(i, new IEs10xFunction.CallBack<List<Notification>>() {
                        public void onComplete(int i, List<Notification> list, String str) {
                            callBack.onComplete(i, list, str);
                        }
                    });
                }
            });
        }
    }

    public EuiccInfo1 getEuiccInfo1() throws Exception {
        return this.euiccController.getEUICCInfo1Obj();
    }

    public EuiccInfo2 getEuiccInfo2() throws Exception {
        return this.euiccController.getEUICCInfo2Obj();
    }

    public void getDefaultSmdpAddress(final IEs10xFunction.CallBack<String> callBack) {
        if (checkEuiccController(callBack)) {
            ThreadManager.initExceutor().execute(new Runnable() {
                public void run() {
                    OMAManager.this.euiccController.getDefaultSmdpAddress(new IEs10xFunction.CallBack<String>() {
                        public void onComplete(final int i, final String str, final String str2) {
                            OMAManager.this.handler.post(new Runnable() {
                                public void run() {
                                    callBack.onComplete(i, str, str2);
                                }
                            });
                        }
                    });
                }
            });
        }
    }

    public void getSmdsAddress(final IEs10xFunction.CallBack<String> callBack) {
        if (checkEuiccController(callBack)) {
            ThreadManager.initExceutor().execute(new Runnable() {
                public void run() {
                    OMAManager.this.euiccController.getSmdsAddress(new IEs10xFunction.CallBack<String>() {
                        public void onComplete(final int i, final String str, final String str2) {
                            OMAManager.this.handler.post(new Runnable() {
                                public void run() {
                                    callBack.onComplete(i, str, str2);
                                }
                            });
                        }
                    });
                }
            });
        }
    }

    public void getSmdpFromSmds(final String str, final IEs10xFunction.CallBack<Es11AuthenticateClientBean> callBack) {
        if (checkEuiccController(callBack)) {
            ThreadManager.initExceutor().execute(new Runnable() {
                public void run() {
                    OMAManager.this.euiccController.getSmdpFromSmdsAddress(str, new IEs10xFunction.CallBack<Es11AuthenticateClientBean>() {
                        public void onComplete(final int i, final Es11AuthenticateClientBean es11AuthenticateClientBean, final String str) {
                            OMAManager.this.handler.post(new Runnable() {
                                public void run() {
                                    callBack.onComplete(i, es11AuthenticateClientBean, str);
                                }
                            });
                        }
                    });
                }
            });
        }
    }

    public String getEid() {
        if (!checkEuiccController((IEs10xFunction.CallBack) null)) {
            return "";
        }
        try {
            return this.euiccController.getEid();
        } catch (Exception e) {
            ELog.e(TAG, "getEid", e);
            return "";
        }
    }

    public void cancelSession(final String str, int i) {
        if (checkEuiccController((IEs10xFunction.CallBack) null)) {
            ThreadManager.initExceutor().execute(new Runnable() {
                public void run() {
                    OMAManager.this.euiccController.cancelSession(str, 0);
                }
            });
        }
    }

    public void setDefaultDpAddress(final String str, final IEs10xFunction.CallBack<Void> callBack) {
        if (checkEuiccController(callBack)) {
            ThreadManager.initExceutor().execute(new Runnable() {
                public void run() {
                    OMAManager.this.euiccController.setDefaultDpAddress(str, new IEs10xFunction.CallBack<Void>() {
                        public void onComplete(final int i, final Void voidR, final String str) {
                            OMAManager.this.handler.post(new Runnable() {
                                public void run() {
                                    callBack.onComplete(i, voidR, str);
                                }
                            });
                        }
                    });
                }
            });
        }
    }

    public void resetMemory(final int i, final IEs10xFunction.CallBack<Void> callBack) {
        if (checkEuiccController(callBack)) {
            ThreadManager.initExceutor().execute(new Runnable() {
                public void run() {
                    OMAManager.this.euiccController.resetMemory(i, new IEs10xFunction.CallBack<Void>() {
                        public void onComplete(final int i, final Void voidR, final String str) {
                            OMAManager.this.handler.post(new Runnable() {
                                public void run() {
                                    callBack.onComplete(i, voidR, str);
                                }
                            });
                        }
                    });
                }
            });
        }
    }

    public void profileClass(String str) {
        if (checkEuiccController((IEs10xFunction.CallBack) null)) {
            this.euiccController.profileClass(str);
        }
    }
}
