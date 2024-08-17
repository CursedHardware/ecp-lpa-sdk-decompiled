package com.eastcompeace.lpa.sdk.fup;

public class FotaUtil {
    public static int hex2Int(String str) {
        return Integer.parseInt(str, 16);
    }

    public static boolean isResetFlag(String str) {
        return (Integer.parseInt(str, 16) >> 7) == 1;
    }

    public static boolean hasExpAtr(String str) {
        return (Integer.parseInt(str, 16) >> 6) == 3;
    }

    public static int getAtrLen(String str) {
        int parseInt = Integer.parseInt(str, 16) & 63;
        System.out.println(parseInt);
        return parseInt;
    }

    public static void main(String[] strArr) {
        getAtrLen("8a");
    }

    public static String getSeidFromScid(String str) {
        Scid scid = new Scid(str, false);
        scid.parser();
        return scid.getUniquelyIdentify();
    }

    public static void compareScid(String str, String str2, StringBuffer stringBuffer) {
        Scid scid = new Scid(str, false);
        scid.parser();
        Scid scid2 = new Scid(str2, false);
        scid2.parser();
        compareScid(scid, scid2, stringBuffer);
    }

    private static void compareScid(Scid scid, Scid scid2, StringBuffer stringBuffer) {
        stringBuffer.append("比较结果:" + ShellUtils.COMMAND_LINE_END);
        if (!scid.getInternalSerialNumber().equals(scid2.getInternalSerialNumber())) {
            stringBuffer.append("内 部序列号 不相等" + ShellUtils.COMMAND_LINE_END);
        }
        if (!scid.getStatusIdentifier().equals(scid2.getStatusIdentifier())) {
            stringBuffer.append("getStatusIdentifier 不相等" + ShellUtils.COMMAND_LINE_END);
        }
        if (!scid.getVersionDifferenceInformation().equals(scid2.getVersionDifferenceInformation())) {
            stringBuffer.append("getVersionDifferenceInformation 不相等" + ShellUtils.COMMAND_LINE_END);
        }
        if (!scid.getBootloaderVersion().equals(scid2.getBootloaderVersion())) {
            stringBuffer.append("getBootloaderVersion 不相等" + ShellUtils.COMMAND_LINE_END);
        }
        if (!scid.getCosVersion().equals(scid2.getCosVersion())) {
            stringBuffer.append("getCosVersion 不相等" + ShellUtils.COMMAND_LINE_END);
        }
        if (!scid.getCosPatchVersion().equals(scid2.getCosPatchVersion())) {
            stringBuffer.append("getCosPatchVersion 不相等" + ShellUtils.COMMAND_LINE_END);
        }
        if (!scid.getAlgorithmLibraryPatchVersion().equals(scid2.getAlgorithmLibraryPatchVersion())) {
            stringBuffer.append("getAlgorithmLibraryPatchVersion 不相等" + ShellUtils.COMMAND_LINE_END);
        }
        if (!scid.getPatchAddress().equals(scid2.getPatchAddress())) {
            stringBuffer.append("getPatchAddress 不相等" + ShellUtils.COMMAND_LINE_END);
        }
        if (!scid.getPatchStatus().equals(scid2.getPatchStatus())) {
            stringBuffer.append("getPatchStatus 不相等" + ShellUtils.COMMAND_LINE_END);
        }
        if (!scid.getPatchLength().equals(scid2.getPatchLength())) {
            stringBuffer.append("getPatchLength 不相等" + ShellUtils.COMMAND_LINE_END);
        }
        if (!scid.getManageApplicationVersion().equals(scid2.getManageApplicationVersion())) {
            stringBuffer.append("getManageApplicationVersion 不相等" + ShellUtils.COMMAND_LINE_END);
        }
        if (!scid.getStateGridApplicationVersion().equals(scid2.getStateGridApplicationVersion())) {
            stringBuffer.append("getStateGridApplicationVersion 不相等" + ShellUtils.COMMAND_LINE_END);
        }
        if (!scid.getPersonalizedStatus().equals(scid2.getPersonalizedStatus())) {
            stringBuffer.append("getPersonalizedStatus 不相等" + ShellUtils.COMMAND_LINE_END);
        }
        if (!scid.getUniquelyIdentify().equals(scid2.getUniquelyIdentify())) {
            stringBuffer.append("getUniquelyIdentify 不相等" + ShellUtils.COMMAND_LINE_END);
        }
    }
}
