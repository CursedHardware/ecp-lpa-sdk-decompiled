package com.eastcompeace.lpa.sdk.procedures.exception;

import com.eastcompeace.lpa.sdk.IEs10xFunction;
import com.eastcompeace.lpa.sdk.http.HttpExcepsion;
import com.eastcompeace.lpa.sdk.http.RspResponseExcepsion;
import com.eastcompeace.lpa.sdk.utils.ChannelExcuteException;
import com.eastcompeace.lpa.sdk.utils.EuiccErrorMapper;

public class ErrorHandler {
    public static void es10Error(int i, IEs10xFunction.CallBack callBack, Exception exc) {
        int i2;
        if (callBack != null) {
            if (exc instanceof ChannelExcuteException) {
                int resultCode = ((ChannelExcuteException) exc).getResultCode();
                if (resultCode == -2) {
                    i2 = EuiccErrorMapper.getOperationAndErrorCode(i, EuiccErrorMapper.ERROR_EUICC_MISSING);
                } else if (resultCode == -3) {
                    i2 = EuiccErrorMapper.getOperationAndErrorCode(i, EuiccErrorMapper.ERROR_CALLER_NOT_ALLOWED);
                } else if (resultCode == -1) {
                    i2 = EuiccErrorMapper.getOperationAndErrorCode(i, EuiccErrorMapper.ERROR_UNKONW_ERROR);
                } else {
                    i2 = EuiccErrorMapper.getOperationAndErrorCode(i, EuiccErrorMapper.ERROR_UNKONW_ERROR);
                }
            } else {
                i2 = EuiccErrorMapper.getOperationAndErrorCode(i, -1);
            }
            callBack.onComplete(i2, null, exc.getMessage());
        }
    }

    public static void es9HttpError(IEs10xFunction.CallBack callBack, Exception exc) {
        if (callBack != null) {
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
    }
}
