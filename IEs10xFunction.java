package com.eastcompeace.lpa.sdk;

import com.eastcompeace.lpa.sdk.bean.es10.Notification;
import com.eastcompeace.lpa.sdk.bean.es10.ProfileInfo;
import com.eastcompeace.lpa.sdk.bean.es10.RulesAuthorisationTable;

public interface IEs10xFunction {
    public static final int CANCEL_REASON_END_USER_REJECTED = 0;
    public static final int CANCEL_REASON_LOAD_BPP_EXECUTION_ERROR = 5;
    public static final int CANCEL_REASON_METADATA_MISMATCH = 4;
    public static final int CANCEL_REASON_POSTPONED = 1;
    public static final int CANCEL_REASON_PPR_NOT_ALLOWED = 3;
    public static final int CANCEL_REASON_TIMEOUT = 2;
    public static final int CANCEL_REASON_UNDEFINED_REASON = 127;
    public static final int RESET_OPTION_DELETE_FIELD_LOADED_TEST_PROFILES = 2;
    public static final int RESET_OPTION_DELETE_OPERATIONAL_PROFILES = 1;
    public static final int RESET_OPTION_RESET_DEFAULT_SMDP_ADDRESS = 4;

    public interface CallBack<T> {
        void onComplete(int i, T t, String str);
    }

    public @interface CancelReason {
    }

    public @interface ResetOption {
    }

    public static class ResultCode {
        public static final int RESULT_APDU_RESPONSE_ERROR = -4;
        public static final int RESULT_CALLER_NOT_ALLOWED = -3;
        public static final int RESULT_EUICC_NOT_FOUND = -2;
        public static final int RESULT_EXCUTE_ERROR = -3;
        public static final int RESULT_FIRST_USER = 1;
        public static final int RESULT_HTTP_ERROR = -6;
        public static final int RESULT_OK = 0;
        public static final int RESULT_PARAMS_ERROR = -5;
        public static final int RESULT_RSP_SERVER_ERROR = -7;
        public static final int RESULT_UNKNOWN_ERROR = -1;
    }

    String authenticateServer(String str, byte[] bArr, byte[] bArr2, byte[] bArr3, byte[] bArr4) throws Exception;

    void cancelSession(String str, int i, CallBack<String> callBack);

    void deleteProfile(String str, CallBack callBack);

    void disableProfile(String str, boolean z, CallBack callBack);

    void enableProfile(String str, boolean z, CallBack callBack);

    String getDownloadAndInstallProfileSessionKey();

    String getEUICCChallenge() throws Exception;

    String getEUICCInfo1() throws Exception;

    String getEUICCInfo2() throws Exception;

    String getEid() throws Exception;

    void getProfileInfo(String str, CallBack<ProfileInfo> callBack);

    void getProfileInfos(CallBack callBack);

    void getRAT(CallBack<RulesAuthorisationTable> callBack);

    IEs10xFunction isdpAid(String str);

    void listNotification(int i, CallBack callBack);

    void loadBoundProfilePackage(byte[] bArr, CallBack callBack);

    String prepareDownload(byte[] bArr, byte[] bArr2, byte[] bArr3, byte[] bArr4) throws Exception;

    IEs10xFunction profileClass(String str);

    void removeNotificationFromList(String str, CallBack<Void> callBack);

    void requestDefaultSmdpAddress(CallBack<String> callBack);

    void requestSmdsAddress(CallBack<String> callBack);

    void resetMemory(int i, CallBack<Void> callBack);

    void retrieveNotification(String str, CallBack<Notification> callBack);

    void retrieveNotificationsList(int i, CallBack callBack);

    void setChannel(IChannel iChannel);

    void setDefaultDpAddress(String str, CallBack<Void> callBack);

    void setNickName(String str, String str2, CallBack callBack);

    IEs10xFunction slotId(int i);
}
