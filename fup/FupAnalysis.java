package com.eastcompeace.lpa.sdk.fup;

import com.eastcompeace.lpa.sdk.IChannel;
import com.eastcompeace.lpa.sdk.fup.FupInstruction;
import com.eastcompeace.lpa.sdk.log.ELog;
import com.eastcompeace.lpa.sdk.utils.HexUtil;
import java.util.ArrayList;
import java.util.List;

public class FupAnalysis {
    public static final int CRC_LEN = 2;
    public static final int DATA_TOTAL_LEN = 3;
    public static final int ScidLength = 67;
    public static final int ScidNoNeedLength = 11;

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0115, code lost:
        r1 = r6;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String analysis1(java.lang.String r13, com.eastcompeace.lpa.sdk.IChannel r14) throws java.lang.Exception {
        /*
            java.util.List r13 = getFupInstructions(r13)
            java.lang.String r0 = "*****************开始发送APDU指令*****************"
            com.eastcompeace.lpa.sdk.log.ELog.i(r0)
            r0 = 0
            java.lang.String r1 = "0000"
            r2 = r0
        L_0x000d:
            int r3 = r13.size()
            java.lang.String r4 = "01"
            java.lang.String r5 = ""
            if (r2 >= r3) goto L_0x017a
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r3 = "++++++++开始发送第 "
            java.lang.StringBuilder r1 = r1.append(r3)
            int r3 = r2 + 1
            java.lang.StringBuilder r1 = r1.append(r3)
            java.lang.String r6 = " 条APDU++++++++"
            java.lang.StringBuilder r1 = r1.append(r6)
            java.lang.String r1 = r1.toString()
            com.eastcompeace.lpa.sdk.log.ELog.i(r1)
            java.lang.Object r1 = r13.get(r2)
            com.eastcompeace.lpa.sdk.fup.FupInstruction r1 = (com.eastcompeace.lpa.sdk.fup.FupInstruction) r1
            r1.isResetInstruction()
            r1.getReset()
            int r2 = r1.getCurrentApduOrder()
            java.lang.String r6 = com.eastcompeace.lpa.sdk.utils.HexUtil.int2Hex4Len(r2)
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.String r8 = "apduOrder:"
            java.lang.StringBuilder r7 = r7.append(r8)
            java.lang.StringBuilder r2 = r7.append(r2)
            java.lang.String r2 = r2.toString()
            com.eastcompeace.lpa.sdk.log.ELog.i(r2)
            java.lang.String r2 = r1.getSeApdu()
            int r7 = r1.getExpApduRspDataLen()
            java.lang.String r8 = r1.getExpApduRspData()
            java.lang.String r1 = r1.getExpApduRspSW()
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            java.lang.String r10 = "expApduRspDataLen:"
            java.lang.StringBuilder r9 = r9.append(r10)
            java.lang.StringBuilder r9 = r9.append(r7)
            java.lang.String r9 = r9.toString()
            com.eastcompeace.lpa.sdk.log.ELog.i(r9)
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            java.lang.String r10 = "expApduRspData:"
            java.lang.StringBuilder r9 = r9.append(r10)
            java.lang.StringBuilder r9 = r9.append(r8)
            java.lang.String r9 = r9.toString()
            com.eastcompeace.lpa.sdk.log.ELog.i(r9)
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            java.lang.String r10 = "expApduRspSW:"
            java.lang.StringBuilder r9 = r9.append(r10)
            java.lang.StringBuilder r9 = r9.append(r1)
            java.lang.String r9 = r9.toString()
            com.eastcompeace.lpa.sdk.log.ELog.i(r9)
            boolean r9 = com.eastcompeace.lpa.sdk.fup.Constant.innerTest
            if (r9 != 0) goto L_0x0176
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            java.lang.String r10 = "apdu:"
            java.lang.StringBuilder r9 = r9.append(r10)
            java.lang.StringBuilder r9 = r9.append(r2)
            java.lang.String r9 = r9.toString()
            com.eastcompeace.lpa.sdk.log.ELog.i(r9)
            java.lang.String r2 = excut(r2, r14)
            com.eastcompeace.lpa.sdk.fup.PCSCResponse r9 = new com.eastcompeace.lpa.sdk.fup.PCSCResponse
            r9.<init>(r2)
            java.lang.String r2 = r9.getSw()
            java.lang.String r10 = "61"
            boolean r10 = r1.startsWith(r10)
            if (r10 == 0) goto L_0x00ee
            java.io.PrintStream r1 = java.lang.System.out
            java.lang.String r10 = "----------61 0---------"
            r1.println(r10)
            r1 = 1
            java.lang.String r10 = "9000"
            r12 = r10
            r10 = r1
            r1 = r12
            goto L_0x00ef
        L_0x00ee:
            r10 = r0
        L_0x00ef:
            boolean r11 = r1.equalsIgnoreCase(r2)
            if (r11 != 0) goto L_0x0117
            java.lang.StringBuilder r13 = new java.lang.StringBuilder
            r13.<init>()
            java.lang.String r14 = "期望的响应状态字和实际的响应状态字不一致,expApduRspSW:"
            java.lang.StringBuilder r13 = r13.append(r14)
            java.lang.StringBuilder r13 = r13.append(r1)
            java.lang.String r14 = " realApduRspSW:"
            java.lang.StringBuilder r13 = r13.append(r14)
            java.lang.StringBuilder r13 = r13.append(r2)
            java.lang.String r13 = r13.toString()
            com.eastcompeace.lpa.sdk.log.ELog.i(r13)
        L_0x0115:
            r1 = r6
            goto L_0x017c
        L_0x0117:
            int r1 = r9.getDataLen()
            if (r10 == 0) goto L_0x0126
            java.io.PrintStream r1 = java.lang.System.out
            java.lang.String r2 = "----------61---------"
            r1.println(r2)
            r1 = r0
            r7 = r1
        L_0x0126:
            if (r7 == r1) goto L_0x0149
            java.lang.StringBuilder r13 = new java.lang.StringBuilder
            r13.<init>()
            java.lang.String r14 = "期望的响应数据长度和实际的响应数据长度不一致,expApduRspDataLen:"
            java.lang.StringBuilder r13 = r13.append(r14)
            java.lang.StringBuilder r13 = r13.append(r7)
            java.lang.String r14 = " realApduRspDataLen:"
            java.lang.StringBuilder r13 = r13.append(r14)
            java.lang.StringBuilder r13 = r13.append(r1)
            java.lang.String r13 = r13.toString()
            com.eastcompeace.lpa.sdk.log.ELog.i(r13)
            goto L_0x0115
        L_0x0149:
            if (r1 <= 0) goto L_0x0176
            java.lang.String r5 = r9.getData()
            boolean r1 = r5.equalsIgnoreCase(r8)
            if (r1 != 0) goto L_0x0176
            java.lang.StringBuilder r13 = new java.lang.StringBuilder
            r13.<init>()
            java.lang.String r14 = "期望的响应数据和实际的响应数据不一致,expApduRspData:"
            java.lang.StringBuilder r13 = r13.append(r14)
            java.lang.StringBuilder r13 = r13.append(r8)
            java.lang.String r14 = " realApduRspData:"
            java.lang.StringBuilder r13 = r13.append(r14)
            java.lang.StringBuilder r13 = r13.append(r5)
            java.lang.String r13 = r13.toString()
            com.eastcompeace.lpa.sdk.log.ELog.i(r13)
            goto L_0x0115
        L_0x0176:
            r2 = r3
            r1 = r6
            goto L_0x000d
        L_0x017a:
            java.lang.String r4 = "00"
        L_0x017c:
            java.lang.StringBuffer r13 = new java.lang.StringBuffer
            r13.<init>()
            r13.append(r1)
            r13.append(r4)
            r13.append(r5)
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            java.lang.String r0 = "before calc crc,crcSrcData:"
            java.lang.StringBuilder r14 = r14.append(r0)
            java.lang.String r0 = r13.toString()
            java.lang.StringBuilder r14 = r14.append(r0)
            java.lang.String r14 = r14.toString()
            com.eastcompeace.lpa.sdk.log.ELog.i(r14)
            java.lang.String r14 = r13.toString()
            java.lang.String r14 = com.eastcompeace.lpa.sdk.fup.CRCUtils.CalcCRC16XModem(r14)
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "计算response中的CRC16:"
            java.lang.StringBuilder r0 = r0.append(r1)
            java.lang.StringBuilder r0 = r0.append(r14)
            java.lang.String r0 = r0.toString()
            com.eastcompeace.lpa.sdk.log.ELog.i(r0)
            r13.append(r14)
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            java.lang.String r0 = "返回给服务器的数据:"
            java.lang.StringBuilder r14 = r14.append(r0)
            java.lang.String r0 = r13.toString()
            java.lang.StringBuilder r14 = r14.append(r0)
            java.lang.String r14 = r14.toString()
            com.eastcompeace.lpa.sdk.log.ELog.i(r14)
            java.lang.String r13 = r13.toString()
            return r13
        */
        throw new UnsupportedOperationException("Method not decompiled: com.eastcompeace.lpa.sdk.fup.FupAnalysis.analysis1(java.lang.String, com.eastcompeace.lpa.sdk.IChannel):java.lang.String");
    }

    public static List<FupInstruction> getFupInstructions(String str) throws FwUpdateException {
        ArrayList arrayList = new ArrayList();
        ELog.i("CRC校验成功");
        String substring = str.substring(0, 6);
        ELog.i("数据总长度(字符串个数):" + substring + " -- " + Integer.parseInt(substring, 16));
        String substring2 = str.substring(6, 10);
        int parseInt = Integer.parseInt(substring2, 16);
        ELog.i("命令条数:" + substring2 + " -- " + parseInt);
        String substring3 = str.substring(10);
        ELog.i("==============================开始解析指令:");
        int i = 0;
        while (i < parseInt) {
            try {
                i++;
                ELog.i("第 " + i + " 条命令开始---------------------");
                FupInstruction fupInstruction = new FupInstruction();
                arrayList.add(fupInstruction);
                fupInstruction.setCurrentApduOrder(i);
                int i2 = 2;
                ELog.i("forBeginIndex:" + 0 + " forEndIndex:" + 2);
                String substring4 = substring3.substring(0, 2);
                if (FotaUtil.isResetFlag(substring4)) {
                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append("flag:" + substring4 + " ");
                    FupInstruction.ResetEntity resetEntity = new FupInstruction.ResetEntity();
                    resetEntity.setResetFlag(substring4);
                    if (FotaUtil.hasExpAtr(substring4)) {
                        resetEntity.setHasExpAtr(true);
                        int atrLen = FotaUtil.getAtrLen(substring4);
                        int i3 = (atrLen * 2) + 2;
                        String substring5 = substring3.substring(2, i3);
                        resetEntity.setExpectAtr(substring5);
                        resetEntity.setResetInstrLen(atrLen);
                        stringBuffer.append("atrLength:" + atrLen + " ");
                        stringBuffer.append("expAtr:" + substring5);
                        i2 = i3;
                    } else {
                        resetEntity.setHasExpAtr(false);
                    }
                    fupInstruction.setReset(resetEntity);
                    fupInstruction.setResetInstruction(true);
                    ELog.i("reset 指令：" + stringBuffer.toString());
                    substring3 = substring3.substring(i2);
                } else {
                    int hex2Int = FotaUtil.hex2Int(substring3.substring(0, 4)) - 2;
                    fupInstruction.setApduLen(hex2Int);
                    ELog.i("apdu头 和数据 的长度:" + hex2Int + "(字节)");
                    int i4 = (hex2Int * 2) + 4;
                    String substring6 = substring3.substring(4, i4);
                    fupInstruction.setSeApdu(substring6);
                    if (!HexUtil.isValidApdu(substring6)) {
                        ELog.i("apduHeaderAndData:无效的APDU");
                    }
                    int i5 = i4 + 4;
                    fupInstruction.setExpApduRspSW(substring3.substring(i4, i5));
                    int i6 = i5 + 2;
                    String substring7 = substring3.substring(i5, i6);
                    int hex2Int2 = FotaUtil.hex2Int(substring7);
                    fupInstruction.setExpStrApduRspDataLen(substring7);
                    fupInstruction.setExpApduRspDataLen(hex2Int2);
                    fupInstruction.setResetInstruction(false);
                    if (hex2Int2 != 0) {
                        if (hex2Int2 > 0) {
                            int i7 = (hex2Int2 * 2) + i6;
                            fupInstruction.setExpApduRspData(substring3.substring(i6, i7));
                            i6 = i7;
                        } else {
                            throw new FwUpdateException("期望响应数据长度 apduResponseDataLen 错误");
                        }
                    }
                    substring3 = substring3.substring(i6);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } catch (Throwable unused) {
            }
        }
        return arrayList;
    }

    private static String excut(String str, IChannel iChannel) {
        String str2 = null;
        try {
            System.out.println("apdu:" + str);
            System.out.println("sw:" + str.substring(str.length() - 5, str.length() - 1));
            String substring = str.substring(0, str.length() - 6);
            int intValue = Integer.valueOf(substring.substring(0, 2), 16).intValue();
            int intValue2 = Integer.valueOf(substring.substring(2, 4), 16).intValue();
            int intValue3 = Integer.valueOf(substring.substring(4, 6), 16).intValue();
            int intValue4 = Integer.valueOf(substring.substring(6, 8), 16).intValue();
            int intValue5 = Integer.valueOf(substring.substring(8, 10), 16).intValue();
            String substring2 = substring.substring(10, substring.length());
            System.out.println("data:" + substring2);
            str2 = iChannel.transmitAPDU(intValue, intValue2, intValue3, intValue4, intValue5, substring2);
            System.out.println("response" + str2);
            return str2;
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Throwable unused) {
        }
        return str2;
    }
}
