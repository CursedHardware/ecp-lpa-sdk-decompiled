package com.eastcompeace.lpa.sdk;

import com.eastcompeace.lpa.sdk.IEs10xFunction;
import com.eastcompeace.lpa.sdk.bean.ActivationCode;
import com.eastcompeace.lpa.sdk.bean.es10.ProfileInfo;
import com.eastcompeace.lpa.sdk.bean.es9.AuthenticateClientBean;
import com.eastcompeace.lpa.sdk.differentiation.EuiccControllerForZH;
import com.eastcompeace.lpa.sdk.log.ELog;
import com.eastcompeace.lpa.sdk.utils.ApduHandler;
import com.eastcompeace.lpa.sdk.utils.StringUtils;

public class GetBppHandler {
    private static final String TAG = "GetBppHandler";
    private static volatile GetBppHandler instance;
    private IChannel channel;
    private Es10xApduImpl es10xApdu;
    private EuiccControllerForZH euiccController = new EuiccControllerForZH(this.es10xApdu);

    public interface BppCallBack {
        void getBppAndProfileMetadata(String str, String str2);
    }

    public interface CallBack<T> {
        void onComplete(int i, T t, String str);
    }

    public static GetBppHandler getInstance(IChannel iChannel, String str) {
        if (instance == null) {
            synchronized (GetBppHandler.class) {
                instance = new GetBppHandler(iChannel, str);
            }
        } else {
            instance.imei(str);
            instance.setChannel(iChannel);
        }
        return instance;
    }

    public GetBppHandler imei(String str) {
        this.euiccController.setImei(str);
        return instance;
    }

    public void setChannel(IChannel iChannel) {
        this.es10xApdu.setChannel(iChannel);
        this.channel = iChannel;
    }

    public GetBppHandler(IChannel iChannel, String str) {
        this.channel = iChannel;
        this.es10xApdu = new Es10xApduImpl(iChannel, str);
    }

    public void getBppAndProfileMetadata(String str, final String str2, final IEs10xFunction.CallBack<String> callBack, final BppCallBack bppCallBack) {
        this.euiccController.Auth(new ActivationCode(str), new IEs10xFunction.CallBack<AuthenticateClientBean>() {
            public void onComplete(int i, AuthenticateClientBean authenticateClientBean, String str) {
                if (i == 0) {
                    GetBppHandler.this.download(str2, authenticateClientBean, callBack, bppCallBack);
                    return;
                }
                IEs10xFunction.CallBack callBack = callBack;
                if (callBack != null) {
                    callBack.onComplete(i, null, str);
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void download(String str, AuthenticateClientBean authenticateClientBean, final IEs10xFunction.CallBack callBack, final BppCallBack bppCallBack) {
        this.euiccController.downLoadProfileAndReturnBpp(authenticateClientBean, str, new IEs10xFunction.CallBack<String>() {
            public void onComplete(int i, String str, String str2) {
                IEs10xFunction.CallBack callBack = callBack;
                if (callBack != null) {
                    callBack.onComplete(i, str, str2);
                }
            }
        }, new BppCallBack() {
            public void getBppAndProfileMetadata(String str, String str2) {
                BppCallBack bppCallBack = bppCallBack;
                if (bppCallBack != null) {
                    bppCallBack.getBppAndProfileMetadata(str, str2);
                }
            }
        });
    }

    public void downLoadNotify(String str, IEs10xFunction.CallBack callBack) {
        this.euiccController.downloadNotification(str, callBack);
    }

    public void enable(String str, boolean z, final CallBack callBack) {
        this.euiccController.enableProfile(str, z, new IEs10xFunction.CallBack<Void>() {
            public void onComplete(int i, Void voidR, String str) {
                callBack.onComplete(i, voidR, str);
            }
        });
    }

    public void notificationOpera(int i, String str, final CallBack callBack) {
        this.euiccController.notificationOpera(i, str, new IEs10xFunction.CallBack<Void>() {
            public void onComplete(int i, Void voidR, String str) {
                callBack.onComplete(i, voidR, str);
            }
        });
    }

    public void disable(String str, boolean z, final CallBack callBack) {
        this.euiccController.disableProfile(str, z, new IEs10xFunction.CallBack<Void>() {
            public void onComplete(int i, Void voidR, String str) {
                callBack.onComplete(i, voidR, str);
            }
        });
    }

    public void delete(String str, final CallBack callBack) {
        this.euiccController.deleteProfile(str, new IEs10xFunction.CallBack<Void>() {
            public void onComplete(int i, Void voidR, String str) {
                callBack.onComplete(i, voidR, str);
            }
        });
    }

    public void sendNOtifyData(String str, String str2, final CallBack callBack) {
        this.euiccController.sendNotificationToDp(str, str2, new IEs10xFunction.CallBack<String>() {
            public void onComplete(int i, String str, String str2) {
                callBack.onComplete(i, str, str2);
            }
        });
    }

    private void enableProfile(String str, String str2, boolean z) {
        StringBuffer stringBuffer = new StringBuffer();
        if (!StringUtils.isEmpty(str)) {
            stringBuffer.append("4F" + ApduHandler.getLenAndApdu(str));
        }
        if (!StringUtils.isEmpty(str2)) {
            stringBuffer.append("5A" + ApduHandler.getLenAndApdu(ProfileInfo.getApduIccid(str2)));
        }
        stringBuffer.insert(0, "A0" + ApduHandler.getLen(stringBuffer));
        stringBuffer.append(z ? "810101" : "810100");
        try {
            transmitAPDU("BF31" + ApduHandler.getLenAndApdu(stringBuffer));
        } catch (Exception e) {
            ELog.e(TAG, "enableProfile: ", e);
            "excute apdu error" + e.getMessage();
        }
    }

    private void disableProfile(String str, String str2, boolean z, CallBack callBack) {
        StringBuffer stringBuffer = new StringBuffer();
        if (!StringUtils.isEmpty(str)) {
            stringBuffer.append("4F" + ApduHandler.getLenAndApdu(str));
        }
        if (!StringUtils.isEmpty(str2)) {
            stringBuffer.append("5A" + ApduHandler.getLenAndApdu(ProfileInfo.getApduIccid(str2)));
        }
        stringBuffer.insert(0, "A0" + ApduHandler.getLen(stringBuffer));
        stringBuffer.append(z ? "810101" : "810100");
        try {
            transmitAPDU("BF32" + ApduHandler.getLenAndApdu(stringBuffer));
        } catch (Exception e) {
            ELog.e(TAG, "disableProfile: ", e);
            "excute apdu error" + e.getMessage();
        }
    }

    private void deleteProfile(String str, String str2, CallBack callBack) {
        StringBuffer stringBuffer = new StringBuffer();
        if (!StringUtils.isEmpty(str)) {
            stringBuffer.append("4F" + ApduHandler.getLenAndApdu(str));
        }
        if (!StringUtils.isEmpty(str2)) {
            stringBuffer.append("5A" + ApduHandler.getLenAndApdu(ProfileInfo.getApduIccid(str2)));
        }
        try {
            transmitAPDU("BF33" + ApduHandler.getLenAndApdu(stringBuffer));
        } catch (Exception e) {
            ELog.e(TAG, "deleteProfile: ", e);
            "excute apdu error：" + e.getMessage();
        }
    }

    private String transmitAPDU(String str) throws Exception {
        int i;
        char c;
        String str2 = str;
        int i2 = 2;
        int parseInt = Integer.parseInt("FF", 16) * 2;
        if (str.length() <= parseInt) {
            int length = str.length() / 2;
            String transmitAPDU = this.channel.transmitAPDU(128, 226, 145, 0, length, str);
            ELog.i("head=" + String.format("%02X%02X%02X%02X%02X", new Object[]{128, 226, 145, 0, Integer.valueOf(length)}));
            ELog.i("data=" + str2 + "\nresponse=" + transmitAPDU);
            return transmitAPDU;
        }
        String str3 = "head=";
        String str4 = "%02X%02X%02X%02X%02X";
        int length2 = (str.length() / parseInt) + 1;
        if (str.length() % parseInt == 0) {
            length2--;
        }
        String str5 = null;
        int i3 = 0;
        while (i3 < length2) {
            if (i3 == length2 - 1) {
                String substring = str2.substring(parseInt * i3, str.length());
                int length3 = substring.length() / i2;
                String transmitAPDU2 = this.channel.transmitAPDU(128, 226, 145, i3, length3, substring);
                ELog.i(str3 + String.format(str4, new Object[]{128, 226, 145, Integer.valueOf(i3), Integer.valueOf(length3)}));
                ELog.i("data=" + substring + "\nresponse=" + transmitAPDU2);
                str5 = transmitAPDU2;
                c = 3;
                i = 2;
            } else {
                String substring2 = str2.substring(parseInt * i3, (i3 + 1) * parseInt);
                String transmitAPDU3 = this.channel.transmitAPDU(128, 226, 17, i3, 255, substring2);
                i = 2;
                c = 3;
                ELog.i(str3 + String.format(str4, new Object[]{128, 226, 17, Integer.valueOf(i3), 255}));
                ELog.i("data=" + substring2 + "\nchildResponse=" + transmitAPDU3);
            }
            i3++;
            char c2 = c;
            i2 = i;
        }
        return str5;
    }
}
