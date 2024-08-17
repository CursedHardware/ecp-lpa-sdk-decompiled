package com.eastcompeace.lpa.sdk.utils;

import androidx.core.view.InputDeviceCompat;
import androidx.core.view.ViewCompat;
import com.eastcompeace.lpa.sdk.log.ELog;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import org.android.agoo.message.MessageService;
import org.json.JSONException;
import org.json.JSONObject;

public class EuiccErrorMapper {
    public static final int ERROR_ADDRESS_MISSING = 10011;
    public static final int ERROR_CALLER_NOT_ALLOWED = 13000;
    public static final int ERROR_CARRIER_LOCKED = 10000;
    public static final int ERROR_CERTIFICATE_ERROR = 10012;
    public static final int ERROR_CONNECTION_ERROR = 10014;
    public static final int ERROR_DISALLOWED_BY_PPR = 10010;
    public static final int ERROR_ENCODE_CONFIRMCODE = 11001;
    public static final int ERROR_EUICC_INSUFFICIENT_MEMORY = 10004;
    public static final int ERROR_EUICC_MISSING = 10006;
    public static final int ERROR_INCOMPATIBLE_CARRIER = 10003;
    public static final int ERROR_INSTALL_PROFILE = 10009;
    public static final int ERROR_INVALID_ACTIVATION_CODE = 10001;
    public static final int ERROR_INVALID_CONFIRMATION_CODE = 10002;
    public static final int ERROR_INVALID_PORT = 10017;
    public static final int ERROR_INVALID_RESPONSE = 10015;
    public static final int ERROR_NO_PROFILES_AVAILABLE = 10013;
    public static final int ERROR_OPERATION_BUSY = 10016;
    public static final int ERROR_SIM_MISSING = 10008;
    public static final int ERROR_TIME_OUT = 10005;
    public static final int ERROR_UNKONW_ERROR = 11000;
    public static final int ERROR_UNSUPPORTED_VERSION = 10007;
    public static final int OPERATION_APDU = 8;
    public static final int OPERATION_AUTH_GETEUICC = 12;
    public static final int OPERATION_AUTH_SERVER = 13;
    public static final int OPERATION_DOWNLOAD = 5;
    public static final int OPERATION_EUICC_CARD = 3;
    public static final int OPERATION_EUICC_GSMA = 7;
    public static final int OPERATION_HTTP = 11;
    public static final int OPERATION_LOAD_BPP = 15;
    public static final int OPERATION_METADATA = 6;
    public static final int OPERATION_PREPAREDOWLOAD = 14;
    public static final int OPERATION_SIM_SLOT = 2;
    public static final int OPERATION_SMDX = 9;
    public static final int OPERATION_SMDX_SUBJECT_REASON_CODE = 10;
    public static final int OPERATION_SWITCH = 4;
    public static final int OPERATION_SYSTEM = 1;
    public static final int RESULT_EUICC_NOT_FOUND = -2;
    private static final int RESULT_FIRST_USER = 2;
    public static final int RESULT_UNKNOWN_ERROR = -1;
    private static final String TAG = "EuiccErrorMapper";
    private static TreeMap errorCategoryTable = errorCategoryTable();
    private static Map errorTable = errorTable();

    private static int getEuiccCardErrorCode(int i) {
        switch (i) {
            case 525577:
            case 525579:
            case 525580:
            case 525581:
                return ERROR_INSTALL_PROFILE;
            case 525578:
                return ERROR_EUICC_INSUFFICIENT_MEMORY;
            default:
                switch (i) {
                    case 525583:
                    case 526851:
                    case 527107:
                    case 527363:
                        return ERROR_DISALLOWED_BY_PPR;
                    default:
                        return 0;
                }
        }
    }

    private static int getSmdxErrorCode(int i) {
        switch (i) {
            case 655361:
            case 655362:
            case 655363:
            case 655367:
            case 655368:
            case 655381:
                return ERROR_CONNECTION_ERROR;
            case 655365:
            case 655366:
            case 655382:
                return ERROR_CERTIFICATE_ERROR;
            case 655369:
            case 655370:
            case 655371:
            case 655372:
            case 655373:
            case 655374:
            case 655375:
            case 655376:
            case 655377:
            case 655378:
            case 655379:
                return ERROR_INVALID_RESPONSE;
            case 655380:
                return ERROR_INVALID_CONFIRMATION_CODE;
            default:
                return 0;
        }
    }

    public static boolean isEuiccCardError(int i) {
        return i >= 524288 && i <= 589823;
    }

    public static boolean isSGPSubjectCodeReasonCode(int i) {
        return (i >>> 24) == 10;
    }

    public static boolean isSmdxError(int i) {
        return i >= 655360 && i <= 720895;
    }

    public static boolean isSmdxHttpError(int i) {
        return i >= 655460 && i <= 656359;
    }

    public static boolean isUiccChannelError(int i) {
        return i >= 589824 && i <= 655359;
    }

    private static TreeMap errorCategoryTable() {
        TreeMap treeMap = new TreeMap();
        treeMap.put(65536, new ErrorResult(1));
        treeMap.put(131072, new ErrorResult(2));
        treeMap.put(196608, new ErrorResult(3));
        treeMap.put(262144, new ErrorResult(9));
        treeMap.put(327680, new ErrorResult(4));
        treeMap.put(393216, new ErrorResult(5));
        treeMap.put(458752, new ErrorResult(6));
        treeMap.put(524288, new ErrorResult(7));
        treeMap.put(589824, new ErrorResult(8));
        treeMap.put(655360, new ErrorResult(9));
        return treeMap;
    }

    private static Map errorTable() {
        HashMap hashMap = new HashMap();
        hashMap.put(65537, new ErrorResult(1));
        hashMap.put(65538, new ErrorResult(1));
        hashMap.put(65539, new ErrorResult(1));
        hashMap.put(Integer.valueOf(InputDeviceCompat.SOURCE_TRACKBALL), new ErrorResult(1));
        hashMap.put(65541, new ErrorResult(1));
        hashMap.put(65542, new ErrorResult(1));
        hashMap.put(65543, new ErrorResult(1));
        hashMap.put(131073, new ErrorResult(2));
        hashMap.put(131074, new ErrorResult(2));
        hashMap.put(131075, new ErrorResult(2, ERROR_TIME_OUT));
        hashMap.put(131076, new ErrorResult(2, ERROR_TIME_OUT));
        hashMap.put(131077, new ErrorResult(2, ERROR_TIME_OUT));
        hashMap.put(131078, new ErrorResult(2, ERROR_TIME_OUT));
        hashMap.put(131079, new ErrorResult(2));
        hashMap.put(131080, new ErrorResult(2, ERROR_TIME_OUT));
        hashMap.put(131081, new ErrorResult(2));
        hashMap.put(131082, new ErrorResult(2, ERROR_EUICC_MISSING));
        hashMap.put(131083, new ErrorResult(2));
        hashMap.put(196609, new ErrorResult(3, ERROR_UNSUPPORTED_VERSION));
        hashMap.put(196610, new ErrorResult(3));
        hashMap.put(196611, new ErrorResult(3, ERROR_EUICC_MISSING));
        hashMap.put(196612, new ErrorResult(3));
        hashMap.put(196613, new ErrorResult(3));
        hashMap.put(196614, new ErrorResult(3));
        hashMap.put(262145, new ErrorResult(9, ERROR_ADDRESS_MISSING));
        hashMap.put(262146, new ErrorResult(9, ERROR_INVALID_CONFIRMATION_CODE));
        hashMap.put(262147, new ErrorResult(9, ERROR_ADDRESS_MISSING));
        hashMap.put(262148, new ErrorResult(9, ERROR_CERTIFICATE_ERROR));
        hashMap.put(262149, new ErrorResult(9));
        hashMap.put(262150, new ErrorResult(9, ERROR_CERTIFICATE_ERROR));
        hashMap.put(262151, new ErrorResult(9, ERROR_CERTIFICATE_ERROR));
        hashMap.put(262152, new ErrorResult(9, ERROR_NO_PROFILES_AVAILABLE));
        hashMap.put(327681, new ErrorResult(4, ERROR_CARRIER_LOCKED));
        hashMap.put(327682, new ErrorResult(4));
        hashMap.put(393217, new ErrorResult(5, ERROR_DISALLOWED_BY_PPR));
        hashMap.put(393218, new ErrorResult(5, ERROR_INVALID_ACTIVATION_CODE));
        hashMap.put(393219, new ErrorResult(5, ERROR_CARRIER_LOCKED));
        hashMap.put(393220, new ErrorResult(5));
        hashMap.put(393221, new ErrorResult(5, ERROR_INCOMPATIBLE_CARRIER));
        hashMap.put(393222, new ErrorResult(5, ERROR_OPERATION_BUSY));
        hashMap.put(458753, new ErrorResult(6, ERROR_INVALID_ACTIVATION_CODE));
        hashMap.put(458754, new ErrorResult(6, ERROR_INVALID_ACTIVATION_CODE));
        hashMap.put(458755, new ErrorResult(6, ERROR_INVALID_ACTIVATION_CODE));
        hashMap.put(458757, new ErrorResult(6, ERROR_INCOMPATIBLE_CARRIER));
        return hashMap;
    }

    public static class ErrorResult {
        public final int errorCode;
        public final int operationCode;

        public ErrorResult(int i) {
            this(i, 0);
        }

        public int toErrorCode() {
            return (this.operationCode << 24) + this.errorCode;
        }

        public ErrorResult(int i, int i2) {
            this.operationCode = i;
            this.errorCode = i2;
        }
    }

    public static int getOperationAndErrorCode(int i, int i2) {
        return new ErrorResult(i, i2).toErrorCode();
    }

    public static ErrorResult getErrorResult(int i) {
        return new ErrorResult(i >>> 24, i & ViewCompat.MEASURED_SIZE_MASK);
    }

    public static int getOperationSmdxSubjectReasonCode(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            String str2 = "";
            String string = jSONObject.has("reasonCode") ? jSONObject.getString("reasonCode") : str2;
            if (jSONObject.has("subjectCode")) {
                str2 = jSONObject.getString("subjectCode");
            }
            return getOperationSmdxSubjectReasonCode(string, str2);
        } catch (JSONException e) {
            e.printStackTrace();
            return 2;
        }
    }

    public static int getOperationSmdxSubjectReasonCode(String str, String str2) {
        try {
            String[] split = str.split("\\.");
            String[] split2 = str2.split("\\.");
            StringBuffer stringBuffer = new StringBuffer();
            if (split.length > 3) {
                return 2;
            }
            if (split2.length > 3) {
                return 2;
            }
            int length = split2.length;
            for (int i = 0; i < 3; i++) {
                if (i > length - 1) {
                    stringBuffer.insert(0, MessageService.MSG_DB_READY_REPORT);
                } else {
                    stringBuffer.append(Integer.toHexString(Integer.valueOf(split2[i]).intValue()));
                }
            }
            int length2 = split.length;
            for (int i2 = 0; i2 < 3; i2++) {
                if (i2 > length2 - 1) {
                    stringBuffer.insert((i2 + 3) - length2, MessageService.MSG_DB_READY_REPORT);
                } else {
                    stringBuffer.append(Integer.toHexString(Integer.valueOf(split[i2]).intValue()));
                }
            }
            stringBuffer.insert(0, "A");
            ELog.i(TAG, stringBuffer.toString());
            ELog.i(TAG, Integer.parseInt(stringBuffer.toString(), 16) + "");
            System.out.println(stringBuffer.toString());
            System.out.println(Integer.parseInt(stringBuffer.toString(), 16));
            return Integer.parseInt(stringBuffer.toString(), 16);
        } catch (Exception e) {
            ELog.e(TAG, "getOperationSmdxSubjectReasonCode", e);
            return -1;
        }
    }
}
