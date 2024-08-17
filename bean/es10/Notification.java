package com.eastcompeace.lpa.sdk.bean.es10;

import com.eastcompeace.lpa.sdk.bean.BaseSerializableBean;
import com.eastcompeace.lpa.sdk.utils.HexUtil;
import com.eastcompeace.lpa.sdk.utils.StringUtils;

public class Notification extends BaseSerializableBean {
    public static final int ALL_EVENTS = 15;
    public static final int EVENT_DELETE = 8;
    public static final int EVENT_DISABLE = 4;
    public static final int EVENT_ENABLE = 2;
    public static final int EVENT_INSTALL = 1;
    String data;
    String iccid;
    String notificationAddress;
    int profileManagementOperation;
    String seqNumber;
    private String seqNumberHexStr;

    public @interface Event {
    }

    public String getData() {
        return this.data;
    }

    public void setData(String str) {
        this.data = str;
    }

    public String getSeqNumberHexStr() {
        return StringUtils.isEmpty(this.seqNumberHexStr) ? HexUtil.int2HexSeqNumber(Integer.valueOf(this.seqNumber).intValue()) : this.seqNumberHexStr;
    }

    public String getSeqNumber() {
        return this.seqNumber;
    }

    public void setSeqNumber(String str) {
        this.seqNumber = str;
    }

    public int getProfileManagementOperation() {
        return this.profileManagementOperation;
    }

    public void setProfileManagementOperation(int i) {
        this.profileManagementOperation = i;
    }

    public String getNotificationAddress() {
        return this.notificationAddress;
    }

    public void setNotificationAddress(String str) {
        this.notificationAddress = str;
    }

    public String getIccid() {
        return this.iccid;
    }

    public void setIccid(String str) {
        this.iccid = str;
    }

    public Notification(String str, int i, String str2, byte[] bArr) {
        this.seqNumber = str;
        this.notificationAddress = str2;
        this.profileManagementOperation = i;
        if (bArr != null && bArr.length > 0) {
            this.data = HexUtil.encodeHexStr(bArr);
        }
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x006d, code lost:
        if (r0.equals("0780") == false) goto L_0x0065;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public Notification(java.util.List<com.eastcompeace.lpa.sdk.bean.es10.TLVObject> r9) {
        /*
            r8 = this;
            r8.<init>()
            java.util.Iterator r9 = r9.iterator()
        L_0x0007:
            boolean r0 = r9.hasNext()
            if (r0 == 0) goto L_0x00ec
            java.lang.Object r0 = r9.next()
            com.eastcompeace.lpa.sdk.bean.es10.TLVObject r0 = (com.eastcompeace.lpa.sdk.bean.es10.TLVObject) r0
            java.lang.String r1 = r0.getStag()
            r1.hashCode()
            int r2 = r1.hashCode()
            r3 = 3
            r4 = 0
            r5 = -1
            r6 = 2
            r7 = 1
            switch(r2) {
                case 1555: goto L_0x0049;
                case 1708: goto L_0x003e;
                case 1784: goto L_0x0033;
                case 1785: goto L_0x0028;
                default: goto L_0x0026;
            }
        L_0x0026:
            r1 = r5
            goto L_0x0053
        L_0x0028:
            java.lang.String r2 = "81"
            boolean r1 = r1.equals(r2)
            if (r1 != 0) goto L_0x0031
            goto L_0x0026
        L_0x0031:
            r1 = r3
            goto L_0x0053
        L_0x0033:
            java.lang.String r2 = "80"
            boolean r1 = r1.equals(r2)
            if (r1 != 0) goto L_0x003c
            goto L_0x0026
        L_0x003c:
            r1 = r6
            goto L_0x0053
        L_0x003e:
            java.lang.String r2 = "5A"
            boolean r1 = r1.equals(r2)
            if (r1 != 0) goto L_0x0047
            goto L_0x0026
        L_0x0047:
            r1 = r7
            goto L_0x0053
        L_0x0049:
            java.lang.String r2 = "0C"
            boolean r1 = r1.equals(r2)
            if (r1 != 0) goto L_0x0052
            goto L_0x0026
        L_0x0052:
            r1 = r4
        L_0x0053:
            switch(r1) {
                case 0: goto L_0x00db;
                case 1: goto L_0x00cf;
                case 2: goto L_0x00a8;
                case 3: goto L_0x0057;
                default: goto L_0x0056;
            }
        L_0x0056:
            goto L_0x0007
        L_0x0057:
            java.lang.String r0 = r0.getSvalue()
            r0.hashCode()
            int r1 = r0.hashCode()
            switch(r1) {
                case 1481507: goto L_0x0086;
                case 1482499: goto L_0x007b;
                case 1483522: goto L_0x0070;
                case 1484607: goto L_0x0067;
                default: goto L_0x0065;
            }
        L_0x0065:
            r3 = r5
            goto L_0x0090
        L_0x0067:
            java.lang.String r1 = "0780"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x0090
            goto L_0x0065
        L_0x0070:
            java.lang.String r1 = "0640"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x0079
            goto L_0x0065
        L_0x0079:
            r3 = r6
            goto L_0x0090
        L_0x007b:
            java.lang.String r1 = "0520"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x0084
            goto L_0x0065
        L_0x0084:
            r3 = r7
            goto L_0x0090
        L_0x0086:
            java.lang.String r1 = "0410"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x008f
            goto L_0x0065
        L_0x008f:
            r3 = r4
        L_0x0090:
            switch(r3) {
                case 0: goto L_0x00a2;
                case 1: goto L_0x009d;
                case 2: goto L_0x0099;
                case 3: goto L_0x0095;
                default: goto L_0x0093;
            }
        L_0x0093:
            goto L_0x0007
        L_0x0095:
            r8.profileManagementOperation = r7
            goto L_0x0007
        L_0x0099:
            r8.profileManagementOperation = r6
            goto L_0x0007
        L_0x009d:
            r0 = 4
            r8.profileManagementOperation = r0
            goto L_0x0007
        L_0x00a2:
            r0 = 8
            r8.profileManagementOperation = r0
            goto L_0x0007
        L_0x00a8:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = r0.getSvalue()
            r3 = 16
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2, r3)
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r2 = ""
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r1 = r1.toString()
            r8.seqNumber = r1
            java.lang.String r0 = r0.getSvalue()
            r8.seqNumberHexStr = r0
            goto L_0x0007
        L_0x00cf:
            java.lang.String r0 = r0.getSvalue()
            java.lang.String r0 = r8.getShowIccid(r0)
            r8.iccid = r0
            goto L_0x0007
        L_0x00db:
            java.lang.String r1 = new java.lang.String
            java.lang.String r0 = r0.getSvalue()
            byte[] r0 = com.eastcompeace.lpa.sdk.utils.HexUtil.decodeHex((java.lang.String) r0)
            r1.<init>(r0)
            r8.notificationAddress = r1
            goto L_0x0007
        L_0x00ec:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.eastcompeace.lpa.sdk.bean.es10.Notification.<init>(java.util.List):void");
    }

    public String getShowIccid(String str) {
        if (StringUtils.isEmpty(str)) {
            return "";
        }
        char[] charArray = str.toCharArray();
        int length = charArray.length;
        int i = charArray[length + -2] == 'F' ? length - 1 : length;
        char[] cArr = new char[i];
        for (int i2 = 0; i2 < length; i2++) {
            int i3 = i2 % 2 == 0 ? i2 + 1 : i2 - 1;
            if (i3 < i) {
                cArr[i3] = charArray[i2];
            }
        }
        return String.valueOf(cArr);
    }

    public String getApduIccid(String str) {
        if (StringUtils.isEmpty(str)) {
            return "";
        }
        char[] charArray = str.toCharArray();
        int length = charArray.length;
        int i = length % 2;
        int i2 = i == 0 ? length : length + 1;
        char[] cArr = new char[i2];
        for (int i3 = 0; i3 < length; i3++) {
            cArr[i3 % 2 == 0 ? i3 + 1 : i3 - 1] = charArray[i3];
        }
        if (i != 0) {
            cArr[i2 - 2] = 'F';
        }
        return String.valueOf(cArr);
    }
}
