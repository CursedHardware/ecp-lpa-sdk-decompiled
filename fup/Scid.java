package com.eastcompeace.lpa.sdk.fup;

import com.eastcompeace.lpa.sdk.log.ELog;

public class Scid {
    private static final int algorithmLibraryPatchVersionLen = 2;
    private static final int bootloaderVersionLen = 2;
    private static final int cosPatchVersionLen = 2;
    private static final int cosVersionLen = 2;
    private static final int industryCodeLen = 2;
    private static final int internalSerialNumberLen = 10;
    private static final int manageApplicationVersionLen = 2;
    private static final int orderIndexLen = 5;
    private static final int patchAddressLen = 4;
    private static final int patchLengthLen = 2;
    private static final int patchStatusLen = 1;
    private static final int personalizedStatusLen = 1;
    private static final int productSerialNumberLen = 4;
    private static final int stateGridApplicationVersionLen = 2;
    private static final int statusIdentifierLen = 1;
    private static final int uniquelyIdentifyLen = 24;
    private static final int versionDifferenceInformationLen = 1;
    private String algorithmLibraryPatchVersion;
    private String bootloaderVersion;
    private String cosPatchVersion;
    private String cosVersion;
    private boolean has11Bytes;
    private String industryCode;
    private String internalSerialNumber;
    private String manageApplicationVersion;
    private String orderIndex;
    private String patchAddress;
    private String patchLength;
    private String patchStatus;
    private String personalizedStatus;
    private String productSerialNumber;
    private String scid;
    private String stateGridApplicationVersion;
    private String statusIdentifier;
    private String uniquelyIdentify;
    private String versionDifferenceInformation;

    public Scid(String str, boolean z) {
        this.scid = str;
        this.has11Bytes = z;
    }

    public void parser() {
        ELog.i("--------scid parser begin---------------");
        this.internalSerialNumber = this.scid.substring(0, 20);
        ELog.i("内部序列号:" + this.internalSerialNumber);
        this.statusIdentifier = this.scid.substring(20, 22);
        ELog.i("状态标识:" + this.statusIdentifier);
        this.versionDifferenceInformation = this.scid.substring(22, 24);
        ELog.i("版本差异信息:" + this.versionDifferenceInformation);
        this.bootloaderVersion = this.scid.substring(24, 28);
        ELog.i("Bootload 版本号:" + this.bootloaderVersion);
        this.cosVersion = this.scid.substring(28, 32);
        ELog.i("COS 版本号:" + this.cosVersion);
        this.cosPatchVersion = this.scid.substring(32, 36);
        ELog.i("COS 补丁版本号:" + this.cosPatchVersion);
        this.algorithmLibraryPatchVersion = this.scid.substring(36, 40);
        ELog.i("算法库补丁版本号:" + this.algorithmLibraryPatchVersion);
        this.patchAddress = this.scid.substring(40, 48);
        ELog.i("补丁地址:" + this.patchAddress);
        this.patchStatus = this.scid.substring(48, 50);
        ELog.i("补丁状态（ COS 内部维护）:" + this.patchStatus);
        this.patchLength = this.scid.substring(50, 54);
        ELog.i("补丁长度（ COS 内部维护):" + this.patchLength);
        this.manageApplicationVersion = this.scid.substring(54, 58);
        ELog.i("当前管理应用版本号:" + this.manageApplicationVersion);
        this.stateGridApplicationVersion = this.scid.substring(58, 62);
        ELog.i("国网应用版本号:" + this.stateGridApplicationVersion);
        this.personalizedStatus = this.scid.substring(62, 64);
        ELog.i(" 个人化状态:" + this.personalizedStatus);
        this.uniquelyIdentify = this.scid.substring(64, 112);
        ELog.i("唯一标识:" + this.uniquelyIdentify);
        try {
            if (this.has11Bytes) {
                this.orderIndex = this.scid.substring(112, 122);
                ELog.i("订单索引:" + this.orderIndex);
                this.productSerialNumber = this.scid.substring(122, 130);
                ELog.i("产品序列号:" + this.productSerialNumber);
                this.industryCode = this.scid.substring(130, 134);
                ELog.i("行业代码:" + this.industryCode);
            }
        } catch (Exception unused) {
            ELog.i("警告:scid中可能不存在/订单索引/产品序列号/行业代码");
        }
        ELog.i("--------scid parser end---------------");
    }

    public String format() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(";" + "scid:" + this.scid + ShellUtils.COMMAND_LINE_END);
        stringBuffer.append(";" + "内部序列号:" + this.internalSerialNumber + ShellUtils.COMMAND_LINE_END);
        stringBuffer.append(";" + "状态标识:" + this.statusIdentifier + ShellUtils.COMMAND_LINE_END);
        stringBuffer.append(";" + "版本差异信息:" + this.versionDifferenceInformation + ShellUtils.COMMAND_LINE_END);
        stringBuffer.append(";" + "Bootload 版本号:" + this.bootloaderVersion + ShellUtils.COMMAND_LINE_END);
        stringBuffer.append(";" + "COS 版本号:" + this.cosVersion + ShellUtils.COMMAND_LINE_END);
        stringBuffer.append(";" + "COS 补丁版本号:" + this.cosPatchVersion + ShellUtils.COMMAND_LINE_END);
        stringBuffer.append(";" + "算法库补丁版本号:" + this.algorithmLibraryPatchVersion + ShellUtils.COMMAND_LINE_END);
        stringBuffer.append(";" + "补丁地址:" + this.patchAddress + ShellUtils.COMMAND_LINE_END);
        stringBuffer.append(";" + "补丁状态（ COS 内部维护）:" + this.patchStatus + ShellUtils.COMMAND_LINE_END);
        stringBuffer.append(";" + "补丁长度（ COS 内部维护):" + this.patchLength + ShellUtils.COMMAND_LINE_END);
        stringBuffer.append(";" + "当前管理应用版本号:" + this.manageApplicationVersion + ShellUtils.COMMAND_LINE_END);
        stringBuffer.append(";" + "国网应用版本号:" + this.stateGridApplicationVersion + ShellUtils.COMMAND_LINE_END);
        stringBuffer.append(";" + "个人化状态:" + this.personalizedStatus + ShellUtils.COMMAND_LINE_END);
        stringBuffer.append(";" + "唯一标识:" + this.uniquelyIdentify + ShellUtils.COMMAND_LINE_END);
        return stringBuffer.toString();
    }

    public Scid() {
    }

    public String getInternalSerialNumber() {
        return this.internalSerialNumber;
    }

    public void setInternalSerialNumber(String str) {
        this.internalSerialNumber = str;
    }

    public String getStatusIdentifier() {
        return this.statusIdentifier;
    }

    public void setStatusIdentifier(String str) {
        this.statusIdentifier = str;
    }

    public String getVersionDifferenceInformation() {
        return this.versionDifferenceInformation;
    }

    public void setVersionDifferenceInformation(String str) {
        this.versionDifferenceInformation = str;
    }

    public String getBootloaderVersion() {
        return this.bootloaderVersion;
    }

    public void setBootloaderVersion(String str) {
        this.bootloaderVersion = str;
    }

    public String getCosVersion() {
        return this.cosVersion;
    }

    public void setCosVersion(String str) {
        this.cosVersion = str;
    }

    public String getCosPatchVersion() {
        return this.cosPatchVersion;
    }

    public void setCosPatchVersion(String str) {
        this.cosPatchVersion = str;
    }

    public String getAlgorithmLibraryPatchVersion() {
        return this.algorithmLibraryPatchVersion;
    }

    public void setAlgorithmLibraryPatchVersion(String str) {
        this.algorithmLibraryPatchVersion = str;
    }

    public String getPatchAddress() {
        return this.patchAddress;
    }

    public void setPatchAddress(String str) {
        this.patchAddress = str;
    }

    public String getPatchStatus() {
        return this.patchStatus;
    }

    public void setPatchStatus(String str) {
        this.patchStatus = str;
    }

    public String getPatchLength() {
        return this.patchLength;
    }

    public void setPatchLength(String str) {
        this.patchLength = str;
    }

    public String getManageApplicationVersion() {
        return this.manageApplicationVersion;
    }

    public void setManageApplicationVersion(String str) {
        this.manageApplicationVersion = str;
    }

    public String getStateGridApplicationVersion() {
        return this.stateGridApplicationVersion;
    }

    public void setStateGridApplicationVersion(String str) {
        this.stateGridApplicationVersion = str;
    }

    public String getPersonalizedStatus() {
        return this.personalizedStatus;
    }

    public void setPersonalizedStatus(String str) {
        this.personalizedStatus = str;
    }

    public String getUniquelyIdentify() {
        return this.uniquelyIdentify;
    }

    public void setUniquelyIdentify(String str) {
        this.uniquelyIdentify = str;
    }

    public String getOrderIndex() {
        return this.orderIndex;
    }

    public void setOrderIndex(String str) {
        this.orderIndex = str;
    }

    public String getProductSerialNumber() {
        return this.productSerialNumber;
    }

    public void setProductSerialNumber(String str) {
        this.productSerialNumber = str;
    }

    public String getIndustryCode() {
        return this.industryCode;
    }

    public void setIndustryCode(String str) {
        this.industryCode = str;
    }

    public static void main(String[] strArr) {
        new Scid("FFFF4E9EC6B2AFDC690A00000002000200000000000000000000000000000102FFFFFFF70000000000000000000000000000000000494930810000C010190DAF010005", true).parser();
    }
}
