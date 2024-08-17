package com.eastcompeace.lpa.sdk;

import com.eastcompeace.lpa.sdk.Exception.Es10ExcuteException;
import com.eastcompeace.lpa.sdk.IEs10xFunction;
import com.eastcompeace.lpa.sdk.bean.Tag;
import com.eastcompeace.lpa.sdk.bean.es10.Notification;
import com.eastcompeace.lpa.sdk.bean.es10.ProfileInfo;
import com.eastcompeace.lpa.sdk.bean.es10.RulesAuthorisationTable;
import com.eastcompeace.lpa.sdk.bean.es10.TLVObject;
import com.eastcompeace.lpa.sdk.language.LanguageConfig;
import com.eastcompeace.lpa.sdk.log.ELog;
import com.eastcompeace.lpa.sdk.utils.ApduHandler;
import com.eastcompeace.lpa.sdk.utils.HexUtil;
import com.eastcompeace.lpa.sdk.utils.StringUtils;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Es10xApduImpl implements IEs10xFunction {
    private static final String TAG = "Es10xDefaultImpl";
    private static volatile Es10xApduImpl instance;
    private String AC_TOKEN;
    private IChannel channel;
    private String imei;
    private String isdp_Aid;
    private String profileClass;

    public IEs10xFunction slotId(int i) {
        return this;
    }

    public Es10xApduImpl(IChannel iChannel, String str) {
        this.channel = iChannel;
        this.imei = str;
    }

    public void setImei(String str) {
        this.imei = str;
    }

    public static Es10xApduImpl getInstance(IChannel iChannel, String str) {
        if (instance == null) {
            synchronized (Es10xApduImpl.class) {
                instance = new Es10xApduImpl(iChannel, str);
            }
        } else {
            instance.channel = iChannel;
            instance.imei = str;
        }
        return instance;
    }

    public String getEid() throws Exception {
        List<TLVObject> tLVByTag;
        ELog.d("--------------Es10 getEid--------------");
        String transmitAPDU = transmitAPDU("BF3E035C015A");
        if (transmitAPDU != null && transmitAPDU.endsWith("9000") && (tLVByTag = ApduHandler.getTLVByTag("5A", transmitAPDU, false)) != null && tLVByTag.size() == 1) {
            return tLVByTag.get(0).getSvalue();
        }
        throw new Es10ExcuteException("Eid is null");
    }

    public IEs10xFunction isdpAid(String str) {
        this.isdp_Aid = str;
        return this;
    }

    public IEs10xFunction profileClass(String str) {
        this.profileClass = str;
        return this;
    }

    public String getEUICCInfo1() throws Exception {
        ELog.d("--------------Es10 getEUICCInfo1--------------");
        String transmitAPDU = transmitAPDU("BF2000");
        if (transmitAPDU != null && transmitAPDU.endsWith("9000")) {
            return transmitAPDU.substring(0, transmitAPDU.length() - 4);
        }
        throw new Es10ExcuteException("EUICCInfo1 is null");
    }

    public String getEUICCInfo2() throws Exception {
        ELog.d("--------------Es10 getEUICCInfo2--------------");
        String transmitAPDU = transmitAPDU("BF2200");
        if (transmitAPDU != null && transmitAPDU.endsWith("9000")) {
            return transmitAPDU.substring(0, transmitAPDU.length() - 4);
        }
        throw new Es10ExcuteException("EUICCInfo1 is null");
    }

    public void setChannel(IChannel iChannel) {
        this.channel = iChannel;
    }

    public String getEUICCChallenge() throws Exception {
        ELog.d("--------------Es10 getEUICCChallenge--------------");
        String transmitAPDU = transmitAPDU("BF2E00");
        if (transmitAPDU != null && transmitAPDU.endsWith("9000")) {
            return transmitAPDU.substring(10, transmitAPDU.length() - 4);
        }
        throw new Es10ExcuteException("EUICCChallenge is null");
    }

    public String authenticateServer(String str, byte[] bArr, byte[] bArr2, byte[] bArr3, byte[] bArr4) throws Exception {
        ELog.d("--------------Es10 authenticateServer--------------");
        if (StringUtils.isEmpty(this.imei)) {
            throw new Exception(LanguageConfig.getInstance().imeiLengthErrorTip());
        } else if (this.imei.length() >= 8) {
            String encodeHexStr = HexUtil.encodeHexStr(str.getBytes());
            String inverseCode = ApduHandler.getInverseCode(this.imei);
            String str2 = inverseCode.length() > 8 ? "82" + ApduHandler.getLenAndApdu(inverseCode) : "";
            String str3 = "A0" + ApduHandler.getLenAndApdu((StringUtils.isEmpty(encodeHexStr) ? "8000" : "80" + ApduHandler.getLenAndApdu(encodeHexStr)) + ("A1" + ApduHandler.getLenAndApdu(("80" + ApduHandler.getLenAndApdu(inverseCode.substring(0, 8))) + ("A1" + ApduHandler.getLenAndApdu("800363000081030800008503080000")) + str2)));
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(HexUtil.encodeHexStr(bArr));
            stringBuffer.append(HexUtil.encodeHexStr(bArr2));
            stringBuffer.append(HexUtil.encodeHexStr(bArr3));
            stringBuffer.append(HexUtil.encodeHexStr(bArr4));
            stringBuffer.append(str3);
            String transmitAPDU = transmitAPDU("BF38" + ApduHandler.getLenAndApdu(stringBuffer));
            if (transmitAPDU != null && transmitAPDU.endsWith("9000")) {
                return transmitAPDU.substring(0, transmitAPDU.length() - 4);
            }
            throw new Es10ExcuteException("authenticateServer response is null");
        } else {
            throw new Exception(LanguageConfig.getInstance().imeiLengthErrorTip());
        }
    }

    public String prepareDownload(byte[] bArr, byte[] bArr2, byte[] bArr3, byte[] bArr4) throws Exception {
        ELog.d("--------------Es10 prepareDownload--------------");
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(HexUtil.encodeHexStr(bArr2));
        stringBuffer.append(HexUtil.encodeHexStr(bArr3));
        if (bArr != null) {
            stringBuffer.append("04" + ApduHandler.getLenAndApdu(HexUtil.encodeHexStr(bArr)));
        }
        stringBuffer.append(HexUtil.encodeHexStr(bArr4));
        stringBuffer.insert(0, "BF21" + ApduHandler.getLen(stringBuffer));
        String transmitAPDU = transmitAPDU(stringBuffer.toString());
        if (transmitAPDU != null && transmitAPDU.endsWith("9000")) {
            return transmitAPDU.substring(0, transmitAPDU.length() - 4);
        }
        throw new Es10ExcuteException("prepareDownload response is null");
    }

    public void loadBoundProfilePackage(byte[] bArr, IEs10xFunction.CallBack callBack) {
        ELog.d("--------------Es10 loadBoundProfilePackage--------------");
        String encodeHexStr = HexUtil.encodeHexStr(bArr);
        ELog.i(TAG, encodeHexStr);
        if (StringUtils.isEmpty(encodeHexStr)) {
            callBack.onComplete(-5, null, "BoundProfilePackage is null");
        } else if (!encodeHexStr.startsWith("BF36")) {
            callBack.onComplete(-5, null, LanguageConfig.getInstance().getString("boundprofilepackage_tag_is_not_bf36"));
        } else {
            List<TLVObject> tLVObjects = ApduHandler.getTLVObjects(encodeHexStr);
            if (tLVObjects == null || tLVObjects.size() == 0) {
                callBack.onComplete(-5, null, LanguageConfig.getInstance().getString("boundprofilepackage_tag_is_not_bf36"));
            } else if (tLVObjects.size() != 1) {
                callBack.onComplete(-5, null, LanguageConfig.getInstance().getString("incorrect_bpp_format"));
            } else {
                TLVObject tLVObject = tLVObjects.get(0);
                List<TLVObject> tLVObjects2 = ApduHandler.getTLVObjects(tLVObject.getSvalue());
                int size = tLVObjects2.size();
                if (size < 4) {
                    callBack.onComplete(-5, null, LanguageConfig.getInstance().getString("incorrect_bpp_format"));
                    return;
                }
                try {
                    ELog.d(TAG, "===InitialiseSecureChannel===");
                    String transmitAPDU = transmitAPDU(tLVObject.getStag() + tLVObject.getSlengthTag() + tLVObject.getSlength() + tLVObjects2.get(0).getApdu());
                    if (!StringUtils.isEmpty(transmitAPDU)) {
                        if (transmitAPDU.endsWith("9000")) {
                            ELog.d(TAG, "===ConfigureISDP===");
                            String transmitAPDU2 = transmitAPDU(tLVObjects2.get(1).getApdu());
                            if (!StringUtils.isEmpty(transmitAPDU2)) {
                                if (transmitAPDU2.endsWith("9000")) {
                                    ELog.d(TAG, "===Storeletadata 1===");
                                    TLVObject tLVObject2 = tLVObjects2.get(2);
                                    String transmitAPDU3 = transmitAPDU(tLVObject2.getStag() + tLVObject2.getSlengthTag() + tLVObject2.getSlength());
                                    if (!StringUtils.isEmpty(transmitAPDU3)) {
                                        if (transmitAPDU3.endsWith("9000")) {
                                            ELog.d(TAG, "===Storeletadata 2===");
                                            String transmitAPDU4 = transmitAPDU(tLVObject2.getSvalue());
                                            if (!StringUtils.isEmpty(transmitAPDU4)) {
                                                if (transmitAPDU4.endsWith("9000")) {
                                                    ELog.d(TAG, "===ppk===");
                                                    TLVObject tLVObject3 = tLVObjects2.get(3);
                                                    if (tLVObject3.getStag().toUpperCase().equals("A2")) {
                                                        String transmitAPDU5 = transmitAPDU(tLVObject3.getApdu());
                                                        if (StringUtils.isEmpty(transmitAPDU5) || !transmitAPDU5.endsWith("9000")) {
                                                            callBack.onComplete(-4, null, "excute ReplaceSessionKeys(A2 87) apdu error");
                                                            return;
                                                        }
                                                    }
                                                    ELog.d(TAG, "===ppk1===");
                                                    TLVObject tLVObject4 = tLVObjects2.get(size - 1);
                                                    String transmitAPDU6 = transmitAPDU(tLVObject4.getStag() + tLVObject4.getSlengthTag() + tLVObject4.getSlength());
                                                    if (!StringUtils.isEmpty(transmitAPDU6)) {
                                                        if (transmitAPDU6.endsWith("9000")) {
                                                            ELog.d(TAG, "===ppk2===");
                                                            List<TLVObject> tLVObjects3 = ApduHandler.getTLVObjects(tLVObject4.getSvalue());
                                                            int i = 0;
                                                            while (i < tLVObjects3.size()) {
                                                                String apdu = tLVObjects3.get(i).getApdu();
                                                                ELog.d(TAG, "===ppk2 第" + i + "条apdu===");
                                                                String transmitAPDU7 = transmitAPDU(apdu);
                                                                if (!StringUtils.isEmpty(transmitAPDU7)) {
                                                                    if (transmitAPDU7.endsWith("9000")) {
                                                                        if (i == tLVObjects3.size() - 1) {
                                                                            callBack.onComplete(0, transmitAPDU7.substring(0, transmitAPDU7.length() - 4), (String) null);
                                                                        }
                                                                        i++;
                                                                    }
                                                                }
                                                                callBack.onComplete(-4, null, LanguageConfig.getInstance().getString("excute_loadprofileelements(a3_86)_apdu_error"));
                                                                return;
                                                            }
                                                            return;
                                                        }
                                                    }
                                                    callBack.onComplete(-4, null, LanguageConfig.getInstance().getString("excute_loadprofileelements(a3)_apdu_error"));
                                                    return;
                                                }
                                            }
                                            callBack.onComplete(-4, null, LanguageConfig.getInstance().getString("excute_storeletadata(a1_88)_apdu_error"));
                                            return;
                                        }
                                    }
                                    callBack.onComplete(-4, null, LanguageConfig.getInstance().getString("excute_storeletadata(a1)_apdu_error"));
                                    return;
                                }
                            }
                            callBack.onComplete(-4, null, LanguageConfig.getInstance().getString("excute_configureisdp_(a0_87)_apdu_error"));
                            return;
                        }
                    }
                    callBack.onComplete(-4, null, LanguageConfig.getInstance().getString("excute_initialisesecurechannel_(bf23)_apdu_error"));
                } catch (Exception e) {
                    ELog.e(TAG, "loadBoundProfilePackage", e);
                    callBack.onComplete(-3, null, e.getMessage());
                }
            }
        }
    }

    public void getRAT(IEs10xFunction.CallBack<RulesAuthorisationTable> callBack) {
        ELog.d("--------------Es10 getRAT--------------");
        try {
            if (isSuccess(transmitAPDU("BF44"), callBack)) {
            }
        } catch (Exception e) {
            ELog.e(TAG, "listNotification", e);
            callBack.onComplete(-3, null, e.getMessage());
        }
    }

    public void getProfileInfos(IEs10xFunction.CallBack callBack) {
        getProfileInfos((String) null, (String) null, this.profileClass, (String) null, callBack);
    }

    public void getProfileInfo(String str, IEs10xFunction.CallBack callBack) {
        getProfileInfos((String) null, str, this.profileClass, (String) null, callBack);
    }

    private void getProfileInfos(String str, String str2, String str3, String str4, IEs10xFunction.CallBack callBack) {
        ELog.d("--------------Es10 getProfileInfos--------------");
        StringBuffer stringBuffer = new StringBuffer();
        if (!StringUtils.isEmpty(str)) {
            stringBuffer.append("4F" + ApduHandler.getLenAndApdu(str));
        }
        if (!StringUtils.isEmpty(str2)) {
            str2 = ProfileInfo.getApduIccid(str2);
            stringBuffer.append("5A" + ApduHandler.getLenAndApdu(str2));
        }
        if (!StringUtils.isEmpty(str3)) {
            stringBuffer.append("95" + ApduHandler.getLenAndApdu(str3));
        }
        if (stringBuffer.length() > 0) {
            stringBuffer.insert(0, "A0" + ApduHandler.getLen(stringBuffer));
        }
        if (!StringUtils.isEmpty(str4)) {
            stringBuffer.append("5C" + ApduHandler.getLenAndApdu(str4));
        }
        try {
            String transmitAPDU = transmitAPDU("BF2D" + ApduHandler.getLenAndApdu(stringBuffer));
            if (isSuccess(transmitAPDU, callBack)) {
                ArrayList arrayList = new ArrayList();
                if (transmitAPDU.contains(Tag.PROFILE_INFO)) {
                    for (TLVObject childrenTLV : ApduHandler.apduTransfTlvsByParentTag("E3", transmitAPDU)) {
                        arrayList.add(new ProfileInfo(childrenTLV.getChildrenTLV()));
                    }
                    if (StringUtils.isEmpty(str2)) {
                        callBack.onComplete(0, arrayList, (String) null);
                    } else {
                        callBack.onComplete(0, arrayList.get(0), (String) null);
                    }
                } else {
                    if (!StringUtils.isEmpty(str2)) {
                        arrayList = null;
                    }
                    callBack.onComplete(0, arrayList, (String) null);
                }
            }
        } catch (Exception e) {
            ELog.e(TAG, "getProfilesInfo: ", e);
            callBack.onComplete(-3, null, e.getMessage());
        }
    }

    public void setNickName(String str, String str2, IEs10xFunction.CallBack callBack) {
        ELog.d("--------------Es10 setNickName--------------");
        StringBuffer stringBuffer = new StringBuffer();
        if (!StringUtils.isEmpty(str)) {
            stringBuffer.append("5A" + ApduHandler.getLenAndApdu(ProfileInfo.getApduIccid(str)));
        }
        if (!StringUtils.isEmpty(str2)) {
            stringBuffer.append("90" + ApduHandler.getLenAndApdu(HexUtil.encodeHexStr(str2.getBytes())));
        }
        try {
            String transmitAPDU = transmitAPDU("BF29" + ApduHandler.getLenAndApdu(stringBuffer));
            if (isSuccess(transmitAPDU, callBack)) {
                String substring = transmitAPDU.substring(6, transmitAPDU.length() - 4);
                if (substring.startsWith("800100")) {
                    callBack.onComplete(0, null, (String) null);
                } else if (substring.startsWith("800101")) {
                    callBack.onComplete(-4, null, LanguageConfig.getInstance().getString("iccid_not_found"));
                } else {
                    callBack.onComplete(-4, null, LanguageConfig.getInstance().getString("undefined_error"));
                }
            }
        } catch (Exception e) {
            ELog.e(TAG, "setNickNamesetNickName:", e);
            callBack.onComplete(-3, null, e.getMessage());
        }
    }

    public void enableProfile(String str, boolean z, IEs10xFunction.CallBack callBack) {
        enableProfile((String) null, str, z, callBack);
    }

    private void enableProfile(String str, String str2, boolean z, IEs10xFunction.CallBack callBack) {
        ELog.d("--------------Es10 enableProfile--------------");
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
            String transmitAPDU = transmitAPDU("BF31" + ApduHandler.getLenAndApdu(stringBuffer));
            if (isSuccess(transmitAPDU, callBack)) {
                String substring = transmitAPDU.substring(10);
                if (substring.startsWith("00")) {
                    callBack.onComplete(0, null, (String) null);
                } else if (substring.startsWith("01")) {
                    callBack.onComplete(1, null, LanguageConfig.getInstance().getString("iccid_or_aid_not_found"));
                } else if (substring.startsWith("02")) {
                    callBack.onComplete(2, null, LanguageConfig.getInstance().getString("profile_not_in_disabled_state"));
                } else if (substring.startsWith("03")) {
                    callBack.onComplete(3, null, LanguageConfig.getInstance().getString("disallowed_by_policy"));
                } else if (substring.startsWith("04")) {
                    callBack.onComplete(4, null, LanguageConfig.getInstance().getString("wrong_profile_reenabling"));
                } else if (substring.startsWith("05")) {
                    callBack.onComplete(5, null, LanguageConfig.getInstance().getString("cat_busy"));
                } else {
                    callBack.onComplete(-1, null, LanguageConfig.getInstance().getString("undefined_error"));
                }
            }
        } catch (Exception e) {
            ELog.e(TAG, "enableProfile: ", e);
            callBack.onComplete(-3, null, LanguageConfig.getInstance().getString("excute_apdu_error"));
        }
    }

    public void disableProfile(String str, boolean z, IEs10xFunction.CallBack callBack) {
        disableProfile((String) null, str, z, callBack);
    }

    private void disableProfile(String str, String str2, boolean z, IEs10xFunction.CallBack callBack) {
        ELog.d("--------------Es10 disableProfile--------------");
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
            String transmitAPDU = transmitAPDU("BF32" + ApduHandler.getLenAndApdu(stringBuffer));
            if (isSuccess(transmitAPDU, callBack)) {
                String substring = transmitAPDU.substring(10);
                if (substring.startsWith("00")) {
                    callBack.onComplete(0, null, (String) null);
                } else if (substring.startsWith("01")) {
                    callBack.onComplete(1, null, LanguageConfig.getInstance().getString("iccid_or_aid_not_found"));
                } else if (substring.startsWith("02")) {
                    callBack.onComplete(2, null, LanguageConfig.getInstance().getString("profile_not_in_enabled_state"));
                } else if (substring.startsWith("03")) {
                    callBack.onComplete(3, null, LanguageConfig.getInstance().getString("disallowed_by_policy"));
                } else if (substring.startsWith("05")) {
                    callBack.onComplete(5, null, LanguageConfig.getInstance().getString("cat_busy"));
                } else {
                    callBack.onComplete(-1, null, LanguageConfig.getInstance().getString("undefined_error"));
                }
            }
        } catch (Exception e) {
            ELog.e(TAG, "disableProfile: ", e);
            callBack.onComplete(-3, null, LanguageConfig.getInstance().getString("excute_apdu_error"));
        }
    }

    public void deleteProfile(String str, IEs10xFunction.CallBack callBack) {
        if (!StringUtils.isEmpty(str)) {
            deleteProfile((String) null, str, callBack);
        } else if (!StringUtils.isEmpty(this.isdp_Aid)) {
            deleteProfile(this.isdp_Aid, (String) null, callBack);
        } else {
            callBack.onComplete(-5, null, LanguageConfig.getInstance().getString("isdp_aid_or_iccid_cannot_be_null"));
        }
    }

    private void deleteProfile(String str, String str2, IEs10xFunction.CallBack callBack) {
        ELog.d("--------------Es10 deleteProfile--------------");
        StringBuffer stringBuffer = new StringBuffer();
        if (!StringUtils.isEmpty(str)) {
            stringBuffer.append("4F" + ApduHandler.getLenAndApdu(str));
        }
        if (!StringUtils.isEmpty(str2)) {
            stringBuffer.append("5A" + ApduHandler.getLenAndApdu(ProfileInfo.getApduIccid(str2)));
        }
        try {
            String transmitAPDU = transmitAPDU("BF33" + ApduHandler.getLenAndApdu(stringBuffer));
            if (isSuccess(transmitAPDU, callBack)) {
                String substring = transmitAPDU.substring(10);
                if (substring.startsWith("00")) {
                    callBack.onComplete(0, null, (String) null);
                } else if (substring.startsWith("01")) {
                    callBack.onComplete(-4, null, LanguageConfig.getInstance().getString("iccid_or_aid_not_found"));
                } else if (substring.startsWith("02")) {
                    callBack.onComplete(-4, null, LanguageConfig.getInstance().getString("profile_not_in_disabled_state"));
                } else if (substring.startsWith("03")) {
                    callBack.onComplete(-4, null, LanguageConfig.getInstance().getString("disallowed_by_policy"));
                } else if (substring.startsWith("05")) {
                    callBack.onComplete(-4, null, LanguageConfig.getInstance().getString("cat_busy"));
                } else {
                    callBack.onComplete(-4, null, LanguageConfig.getInstance().getString("undefined_error"));
                }
            }
        } catch (Exception e) {
            ELog.e(TAG, "deleteProfile: ", e);
            callBack.onComplete(-3, null, LanguageConfig.getInstance().getString("excute_apdu_error"));
        }
    }

    public void listNotification(int i, IEs10xFunction.CallBack callBack) {
        ELog.d("--------------Es10 listNotification--------------");
        new ArrayList();
        StringBuffer stringBuffer = new StringBuffer();
        if (i == 8) {
            stringBuffer.append("BF280481020410");
        } else if (i == 4) {
            stringBuffer.append("BF280481020520");
        } else if (i == 2) {
            stringBuffer.append("BF280481020640");
        } else if (i == 1) {
            stringBuffer.append("BF280481020780");
        } else {
            stringBuffer.append("BF2800");
        }
        try {
            String transmitAPDU = transmitAPDU(stringBuffer.toString());
            if (isSuccess(transmitAPDU, callBack)) {
                if (!transmitAPDU.contains("BF2F")) {
                    callBack.onComplete(0, null, LanguageConfig.getInstance().getString("no_notification_response"));
                    return;
                }
                List<TLVObject> apduTransfTlvsByParentTag = ApduHandler.apduTransfTlvsByParentTag("BF2F", transmitAPDU);
                ArrayList arrayList = new ArrayList();
                for (TLVObject childrenTLV : apduTransfTlvsByParentTag) {
                    arrayList.add(new Notification(childrenTLV.getChildrenTLV()));
                }
                callBack.onComplete(0, arrayList, (String) null);
            }
        } catch (Exception e) {
            ELog.e(TAG, "listNotification", e);
            callBack.onComplete(-3, null, e.getMessage());
        }
    }

    public void retrieveNotificationsList(int i, IEs10xFunction.CallBack callBack) {
        ELog.d("--------------Es10 retrieveNotificationsList--------------");
        StringBuffer stringBuffer = new StringBuffer();
        if (i == 8) {
            stringBuffer.append("81020410");
            stringBuffer.insert(0, "A0" + ApduHandler.getLen(stringBuffer));
        } else if (i == 4) {
            stringBuffer.append("81020520");
            stringBuffer.insert(0, "A0" + ApduHandler.getLen(stringBuffer));
        } else if (i == 2) {
            stringBuffer.append("81020640");
            stringBuffer.insert(0, "A0" + ApduHandler.getLen(stringBuffer));
        } else if (i == 1) {
            stringBuffer.append("81020780");
            stringBuffer.insert(0, "A0" + ApduHandler.getLen(stringBuffer));
        }
        stringBuffer.insert(0, "BF2B" + ApduHandler.getLen(stringBuffer));
        try {
            String transmitAPDU = transmitAPDU(stringBuffer.toString());
            if (isSuccess(transmitAPDU, callBack)) {
                if (!transmitAPDU.contains("BF2B")) {
                    callBack.onComplete(0, null, LanguageConfig.getInstance().getString("no_notification_response"));
                    return;
                }
                List<TLVObject> tLVByTag = ApduHandler.getTLVByTag("A0", transmitAPDU, true);
                if (tLVByTag != null && tLVByTag.size() == 1) {
                    ArrayList arrayList = new ArrayList();
                    for (TLVObject next : tLVByTag.get(0).getChildrenTLV()) {
                        List<TLVObject> tLVByTag2 = ApduHandler.getTLVByTag("BF2F", next.getApdu(), true);
                        if (tLVByTag2 != null && tLVByTag2.size() == 1) {
                            Notification notification = new Notification(tLVByTag2.get(0).getChildrenTLV());
                            notification.setData(next.getApdu());
                            arrayList.add(notification);
                        }
                    }
                    callBack.onComplete(0, arrayList, (String) null);
                }
            }
        } catch (Exception e) {
            ELog.e(TAG, "retrieveNotification", e);
            callBack.onComplete(-3, null, e.getMessage());
        }
    }

    public void retrieveNotification(String str, IEs10xFunction.CallBack<Notification> callBack) {
        ELog.d("--------------Es10 retrieveNotification--------------");
        StringBuffer stringBuffer = new StringBuffer();
        if (!StringUtils.isEmpty(str)) {
            stringBuffer.append("80" + ApduHandler.getLenAndApdu(str));
        }
        stringBuffer.insert(0, "A0" + ApduHandler.getLen(stringBuffer));
        stringBuffer.insert(0, "BF2B" + ApduHandler.getLen(stringBuffer));
        try {
            String transmitAPDU = transmitAPDU(stringBuffer.toString());
            if (isSuccess(transmitAPDU, callBack)) {
                if (!transmitAPDU.contains("BF2B")) {
                    callBack.onComplete(-4, null, LanguageConfig.getInstance().getString("no_notification_response"));
                    return;
                }
                List<TLVObject> tLVByTag = ApduHandler.getTLVByTag("A0", transmitAPDU, true);
                if (tLVByTag != null && tLVByTag.size() == 1) {
                    List<TLVObject> childrenTLV = tLVByTag.get(0).getChildrenTLV();
                    if (childrenTLV != null && childrenTLV.size() == 1) {
                        TLVObject tLVObject = childrenTLV.get(0);
                        List<TLVObject> tLVByTag2 = ApduHandler.getTLVByTag("BF2F", tLVObject.getApdu(), true);
                        if (tLVByTag2 != null && tLVByTag2.size() == 1) {
                            Notification notification = new Notification(tLVByTag2.get(0).getChildrenTLV());
                            notification.setData(tLVObject.getApdu());
                            callBack.onComplete(0, notification, (String) null);
                            return;
                        }
                    }
                    callBack.onComplete(-1, null, LanguageConfig.getInstance().getString("notification_content_is_null"));
                }
            }
        } catch (Exception e) {
            ELog.e(TAG, "retrieveNotification", e);
            callBack.onComplete(-3, null, e.getMessage());
        }
    }

    public void removeNotificationFromList(String str, IEs10xFunction.CallBack callBack) {
        ELog.d("--------------Es10 removeNotificationFromList--------------");
        if (StringUtils.isEmpty(str)) {
            callBack.onComplete(-5, null, LanguageConfig.getInstance().getString("seqnumber_cannot_be_null"));
            return;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("80" + ApduHandler.getLenAndApdu(HexUtil.int2HexSeqNumber(Integer.valueOf(str).intValue())));
        stringBuffer.insert(0, "BF30" + ApduHandler.getLen(stringBuffer));
        try {
            String transmitAPDU = transmitAPDU(stringBuffer.toString());
            if (isSuccess(transmitAPDU, callBack)) {
                List<TLVObject> apduTransfTlvsByParentTag = ApduHandler.apduTransfTlvsByParentTag("80", transmitAPDU);
                if (apduTransfTlvsByParentTag == null) {
                    callBack.onComplete(-4, null, transmitAPDU);
                    return;
                }
                int intValue = Integer.valueOf(apduTransfTlvsByParentTag.get(0).getSvalue(), 16).intValue();
                if (intValue == 0) {
                    callBack.onComplete(0, null, (String) null);
                } else if (intValue == 1) {
                    callBack.onComplete(-4, null, LanguageConfig.getInstance().getString("nothing_to_delete"));
                } else {
                    callBack.onComplete(-4, null, LanguageConfig.getInstance().getString("undefined_error"));
                }
            }
        } catch (Exception e) {
            ELog.e(TAG, "removeNotificationFromList", e);
            callBack.onComplete(-3, null, e.getMessage());
        }
    }

    public void cancelSession(String str, int i, IEs10xFunction.CallBack<String> callBack) {
        ELog.d("--------------Es10 cancelSession--------------");
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("80" + ApduHandler.getLenAndApdu(str.toUpperCase()));
        stringBuffer.append("8101" + String.format("%02X", new Object[]{Integer.valueOf(i)}));
        stringBuffer.insert(0, "BF41" + ApduHandler.getLen(stringBuffer));
        try {
            String transmitAPDU = transmitAPDU(stringBuffer.toString());
            if (!isSuccess(transmitAPDU, callBack)) {
                callBack.onComplete(-4, null, transmitAPDU);
            } else {
                callBack.onComplete(0, transmitAPDU.substring(0, transmitAPDU.length() - 4), (String) null);
            }
        } catch (Exception e) {
            ELog.e(TAG, "removeNotificationFromList", e);
            callBack.onComplete(-3, null, e.getMessage());
        }
    }

    public void setDefaultDpAddress(String str, IEs10xFunction.CallBack<Void> callBack) {
        ELog.d("--------------Es10 setDefaultDpAddress--------------");
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("80" + ApduHandler.getLenAndApdu(HexUtil.encodeHexStr(str.getBytes(StandardCharsets.UTF_8))));
        stringBuffer.insert(0, "BF3F" + ApduHandler.getLen(stringBuffer));
        try {
            String transmitAPDU = transmitAPDU(stringBuffer.toString());
            if (isSuccess(transmitAPDU, callBack)) {
                List<TLVObject> tLVObjects = ApduHandler.getTLVObjects(transmitAPDU);
                if (tLVObjects != null) {
                    if (tLVObjects.size() == 1) {
                        if ("800100".equals(tLVObjects.get(0).getSvalue())) {
                            callBack.onComplete(0, null, (String) null);
                            return;
                        } else {
                            callBack.onComplete(-4, null, LanguageConfig.getInstance().getString("apdu_response_error") + ": " + transmitAPDU);
                            return;
                        }
                    }
                }
                callBack.onComplete(-4, null, LanguageConfig.getInstance().getString("apdu_response_error") + ": " + transmitAPDU);
            }
        } catch (Exception e) {
            ELog.e(TAG, "setDefaultDpAddress", e);
            callBack.onComplete(-3, null, e.getMessage());
        }
    }

    public void requestDefaultSmdpAddress(IEs10xFunction.CallBack<String> callBack) {
        ELog.d("--------------Es10 requestDefaultSmdpAddress--------------");
        try {
            String transmitAPDU = transmitAPDU("BF3C00");
            if (isSuccess(transmitAPDU, callBack)) {
                if (transmitAPDU.contains("BF3C")) {
                    List<TLVObject> tLVByTag = ApduHandler.getTLVByTag("BF3C", transmitAPDU, true);
                    if (tLVByTag != null && tLVByTag.size() == 1) {
                        List<TLVObject> childrenTLV = tLVByTag.get(0).getChildrenTLV();
                        if (childrenTLV != null) {
                            if (childrenTLV.size() != 0) {
                                for (TLVObject next : childrenTLV) {
                                    if (next.getStag().equals("80")) {
                                        callBack.onComplete(0, HexUtil.decodeHexStr(next.getSvalue()), (String) null);
                                        return;
                                    }
                                }
                            }
                        }
                        callBack.onComplete(0, null, (String) null);
                        return;
                    }
                    callBack.onComplete(0, "", (String) null);
                    return;
                }
                callBack.onComplete(0, "", (String) null);
            }
        } catch (Exception e) {
            ELog.e(TAG, "requestDefaultSmdpAddress", e);
            callBack.onComplete(-3, null, e.getMessage());
        }
    }

    public void requestSmdsAddress(IEs10xFunction.CallBack<String> callBack) {
        ELog.d("--------------Es10 requestSmdsAddress--------------");
        try {
            String transmitAPDU = transmitAPDU("BF3C00");
            if (isSuccess(transmitAPDU, callBack)) {
                if (transmitAPDU.contains("BF3C")) {
                    List<TLVObject> tLVByTag = ApduHandler.getTLVByTag("BF3C", transmitAPDU, true);
                    if (tLVByTag != null && tLVByTag.size() == 1) {
                        List<TLVObject> childrenTLV = tLVByTag.get(0).getChildrenTLV();
                        if (childrenTLV != null) {
                            if (childrenTLV.size() != 0) {
                                for (TLVObject next : childrenTLV) {
                                    if (next.getStag().equals("81")) {
                                        callBack.onComplete(0, HexUtil.decodeHexStr(next.getSvalue()), (String) null);
                                        return;
                                    }
                                }
                            }
                        }
                        callBack.onComplete(0, null, (String) null);
                        return;
                    }
                    callBack.onComplete(0, "", (String) null);
                    return;
                }
                callBack.onComplete(0, "", (String) null);
            }
        } catch (Exception e) {
            ELog.e(TAG, "requestDefaultSmdpAddress", e);
            callBack.onComplete(-3, null, e.getMessage());
        }
    }

    public void resetMemory(int i, IEs10xFunction.CallBack<Void> callBack) {
        ELog.d("--------------Es10 resetMemory--------------");
        StringBuffer stringBuffer = new StringBuffer();
        if (i == 1) {
            stringBuffer.append("82" + ApduHandler.getLenAndApdu("0780"));
        } else if (i == 2) {
            stringBuffer.append("82" + ApduHandler.getLenAndApdu("0640"));
        } else if (i == 4) {
            stringBuffer.append("82" + ApduHandler.getLenAndApdu("0520"));
        }
        stringBuffer.insert(0, "BF34" + ApduHandler.getLen(stringBuffer));
        try {
            String transmitAPDU = transmitAPDU(stringBuffer.toString());
            if (isSuccess(transmitAPDU, callBack)) {
                List<TLVObject> tLVObjects = ApduHandler.getTLVObjects(transmitAPDU);
                if (tLVObjects != null) {
                    if (tLVObjects.size() == 1) {
                        TLVObject tLVObject = tLVObjects.get(0);
                        if ("800100".equals(tLVObject.getSvalue())) {
                            callBack.onComplete(0, null, (String) null);
                            return;
                        } else if ("800101".equals(tLVObject.getSvalue())) {
                            callBack.onComplete(-4, null, LanguageConfig.getInstance().getString("nothing_to_delete"));
                            return;
                        } else if ("800105".equals(tLVObject.getSvalue())) {
                            callBack.onComplete(-4, null, LanguageConfig.getInstance().getString("cat_busy"));
                            return;
                        } else {
                            callBack.onComplete(-4, null, LanguageConfig.getInstance().getString("undefined_error"));
                            return;
                        }
                    }
                }
                callBack.onComplete(-4, null, LanguageConfig.getInstance().getString("apdu_response_error") + ": " + transmitAPDU);
            }
        } catch (Exception e) {
            ELog.e(TAG, "resetMemory", e);
            callBack.onComplete(-3, null, e.getMessage());
        }
    }

    public String getDownloadAndInstallProfileSessionKey() {
        try {
            String transmitAPDU = transmitAPDU("DF7E00");
            ELog.i(TAG, "DownloadAndInstallProfileSessionKey:" + transmitAPDU);
            return transmitAPDU;
        } catch (Exception e) {
            ELog.e(TAG, "getDownloadAndInstallProfileSessionKey", e);
            return "";
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
            return transmitAPDU.toUpperCase();
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
        return str5.toUpperCase();
    }

    private boolean isSuccess(String str, IEs10xFunction.CallBack callBack) {
        if (StringUtils.isEmpty(str)) {
            callBack.onComplete(-4, null, LanguageConfig.getInstance().getString("response_is_null"));
            return false;
        } else if (str.endsWith("9000") || str.endsWith("910B")) {
            return true;
        } else {
            callBack.onComplete(-4, null, str);
            return false;
        }
    }
}
