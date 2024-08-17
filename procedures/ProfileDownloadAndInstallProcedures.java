package com.eastcompeace.lpa.sdk.procedures;

import com.eastcompeace.lpa.sdk.Es10xApduImpl;
import com.eastcompeace.lpa.sdk.Es9PlusFunctionImpl;
import com.eastcompeace.lpa.sdk.IEs10xFunction;
import com.eastcompeace.lpa.sdk.bean.es10.ProfileInfo;
import com.eastcompeace.lpa.sdk.bean.es10.StoreMetadataRequest;
import com.eastcompeace.lpa.sdk.bean.es10.TLVObject;
import com.eastcompeace.lpa.sdk.bean.es9.AuthenticateClientBean;
import com.eastcompeace.lpa.sdk.bean.es9.GetBoundProfilePackageBean;
import com.eastcompeace.lpa.sdk.http.CallBack;
import com.eastcompeace.lpa.sdk.language.LanguageConfig;
import com.eastcompeace.lpa.sdk.log.ELog;
import com.eastcompeace.lpa.sdk.procedures.exception.ErrorHandler;
import com.eastcompeace.lpa.sdk.utils.ApduHandler;
import com.eastcompeace.lpa.sdk.utils.AppConfig;
import com.eastcompeace.lpa.sdk.utils.Base64;
import com.eastcompeace.lpa.sdk.utils.ChannelExcuteException;
import com.eastcompeace.lpa.sdk.utils.EuiccErrorMapper;
import com.eastcompeace.lpa.sdk.utils.HexUtil;
import com.eastcompeace.lpa.sdk.utils.MyDigest;
import com.eastcompeace.lpa.sdk.utils.StringUtils;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class ProfileDownloadAndInstallProcedures {
    private static final String TAG = "ProfileDownloadAndInstallProcedures";
    private static volatile ProfileDownloadAndInstallProcedures instance;
    /* access modifiers changed from: private */
    public IEs10xFunction es10xFunction;

    public ProfileDownloadAndInstallProcedures(IEs10xFunction iEs10xFunction) {
        this.es10xFunction = iEs10xFunction;
    }

    public static ProfileDownloadAndInstallProcedures getInstance(IEs10xFunction iEs10xFunction) {
        if (instance == null) {
            synchronized (Es10xApduImpl.class) {
                instance = new ProfileDownloadAndInstallProcedures(iEs10xFunction);
            }
        } else {
            instance.setEs10xFunction(iEs10xFunction);
        }
        return instance;
    }

    private void setEs10xFunction(IEs10xFunction iEs10xFunction) {
        this.es10xFunction = iEs10xFunction;
    }

    public synchronized void downLoadProfile(final AuthenticateClientBean authenticateClientBean, final String str, final IEs10xFunction.CallBack callBack) {
        if (authenticateClientBean == null) {
            disposeCallBack(callBack, -5, (Object) null, "AuthenticateClientBean is null");
            return;
        }
        StoreMetadataRequest storeMetadata = authenticateClientBean.getStoreMetadata();
        if (storeMetadata == null) {
            disposeCallBack(callBack, -5, (Object) null, "StoreMetadata is null");
        } else {
            this.es10xFunction.getProfileInfo(storeMetadata.getIccid(), new IEs10xFunction.CallBack<ProfileInfo>() {
                public void onComplete(int i, ProfileInfo profileInfo, String str) {
                    if (profileInfo != null) {
                        ProfileDownloadAndInstallProcedures.this.disposeCallBack(callBack, EuiccErrorMapper.getOperationAndErrorCode(7, EuiccErrorMapper.ERROR_INSTALL_PROFILE), (Object) null, LanguageConfig.getInstance().profileExists());
                        CancelSessionProcedurs.getInstance(ProfileDownloadAndInstallProcedures.this.es10xFunction).cancelSession(authenticateClientBean.getTransactionId(), 3, (IEs10xFunction.CallBack<Void>) null);
                        return;
                    }
                    ProfileDownloadAndInstallProcedures.this.downLoad(authenticateClientBean, str, callBack);
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void downLoad(final AuthenticateClientBean authenticateClientBean, String str, final IEs10xFunction.CallBack callBack) {
        byte[] bArr;
        byte[] bArr2 = null;
        try {
            if (!StringUtils.isEmpty(str)) {
                str = HexUtil.encodeHexStr(MyDigest.sha256(str.getBytes()));
                ELog.d("confirmationCode:" + str);
                bArr = MyDigest.sha256Byte(str + authenticateClientBean.getTransactionId());
                ELog.d("TransactionId:" + authenticateClientBean.getTransactionId());
                ELog.d("confirmCodeB:" + bArr);
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
                        ELog.d(ProfileDownloadAndInstallProcedures.TAG, "Es9+ authenticateClient success response: " + getBoundProfilePackageBean.toString());
                        ProfileDownloadAndInstallProcedures.this.loadBoundProfilePackage(getBoundProfilePackageBean, callBack);
                    }

                    public void onError(Exception exc) {
                        ELog.e(ProfileDownloadAndInstallProcedures.TAG, "getBoundProfilePackage error", exc);
                        CancelSessionProcedurs.getInstance(ProfileDownloadAndInstallProcedures.this.es10xFunction).cancelSession(authenticateClientBean.getTransactionId(), 3);
                        ErrorHandler.es9HttpError(callBack, exc);
                    }
                });
            } catch (Exception e) {
                ELog.e(TAG, "prepareDownload error", e);
                CancelSessionProcedurs.getInstance(this.es10xFunction).cancelSession(authenticateClientBean.getTransactionId(), 127);
                ErrorHandler.es10Error(14, callBack, e);
            }
        } catch (NoSuchAlgorithmException e2) {
            ELog.e(TAG, "downLoadProfile", e2);
            CancelSessionProcedurs.getInstance(this.es10xFunction).cancelSession(authenticateClientBean.getTransactionId(), 0, (IEs10xFunction.CallBack<Void>) null);
            disposeCallBack(callBack, EuiccErrorMapper.getOperationAndErrorCode(1, EuiccErrorMapper.ERROR_ENCODE_CONFIRMCODE), (Object) null, e2.getMessage());
        }
    }

    /* access modifiers changed from: protected */
    public void loadBoundProfilePackage(final GetBoundProfilePackageBean getBoundProfilePackageBean, final IEs10xFunction.CallBack callBack) {
        ELog.d(TAG, "--------------Es10 loadBoundProfilePackage-----------------");
        this.es10xFunction.loadBoundProfilePackage(getBoundProfilePackageBean.getBoundProfilePackage(), new IEs10xFunction.CallBack<String>() {
            public void onComplete(int i, String str, String str2) {
                System.out.println("loadBoundProfilePackage result" + i);
                if (AppConfig.isGetInstallProfileSessionKey) {
                    ProfileDownloadAndInstallProcedures.this.es10xFunction.getDownloadAndInstallProfileSessionKey();
                }
                ELog.d(ProfileDownloadAndInstallProcedures.TAG, "Es10 loadBoundProfilePackage code:" + i + "   response:" + str);
                if (i == 0) {
                    List<TLVObject> tLVByTag = ApduHandler.getTLVByTag("A0", str, true);
                    if (tLVByTag == null || tLVByTag.size() <= 0 || tLVByTag.get(0).getChildrenTLV() == null) {
                        ProfileDownloadAndInstallProcedures.this.disposeCallBack(callBack, EuiccErrorMapper.getOperationAndErrorCode(8, 0), (Object) null, LanguageConfig.getInstance().profileInstallFailedWithApdu() + str);
                        return;
                    }
                    ProfileDownloadAndInstallProcedures.this.disposeCallBack(callBack, 0, str, str2);
                    return;
                }
                CancelSessionProcedurs.getInstance(ProfileDownloadAndInstallProcedures.this.es10xFunction).cancelSession(getBoundProfilePackageBean.getTransactionId(), 5);
                ErrorHandler.es10Error(15, callBack, new ChannelExcuteException(i, str2));
                NotificationProcedurs.getInstance(ProfileDownloadAndInstallProcedures.this.es10xFunction).sendNotification(15);
            }
        });
    }

    /* access modifiers changed from: private */
    public void disposeCallBack(IEs10xFunction.CallBack callBack, int i, Object obj, String str) {
        if (callBack != null) {
            callBack.onComplete(i, obj, str);
        }
    }
}
