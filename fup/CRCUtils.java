package com.eastcompeace.lpa.sdk.fup;

import com.eastcompeace.lpa.sdk.utils.HexUtil;
import com.umeng.analytics.pro.cc;
import kotlin.UByte;
import kotlin.jvm.internal.ByteCompanionObject;
import okio.Utf8;

public class CRCUtils {
    static byte[] crc16_tab_h = {0, -63, -127, 64, 1, -64, ByteCompanionObject.MIN_VALUE, 65, 1, -64, ByteCompanionObject.MIN_VALUE, 65, 0, -63, -127, 64, 1, -64, ByteCompanionObject.MIN_VALUE, 65, 0, -63, -127, 64, 0, -63, -127, 64, 1, -64, ByteCompanionObject.MIN_VALUE, 65, 1, -64, ByteCompanionObject.MIN_VALUE, 65, 0, -63, -127, 64, 0, -63, -127, 64, 1, -64, ByteCompanionObject.MIN_VALUE, 65, 0, -63, -127, 64, 1, -64, ByteCompanionObject.MIN_VALUE, 65, 1, -64, ByteCompanionObject.MIN_VALUE, 65, 0, -63, -127, 64, 1, -64, ByteCompanionObject.MIN_VALUE, 65, 0, -63, -127, 64, 0, -63, -127, 64, 1, -64, ByteCompanionObject.MIN_VALUE, 65, 0, -63, -127, 64, 1, -64, ByteCompanionObject.MIN_VALUE, 65, 1, -64, ByteCompanionObject.MIN_VALUE, 65, 0, -63, -127, 64, 0, -63, -127, 64, 1, -64, ByteCompanionObject.MIN_VALUE, 65, 1, -64, ByteCompanionObject.MIN_VALUE, 65, 0, -63, -127, 64, 1, -64, ByteCompanionObject.MIN_VALUE, 65, 0, -63, -127, 64, 0, -63, -127, 64, 1, -64, ByteCompanionObject.MIN_VALUE, 65, 1, -64, ByteCompanionObject.MIN_VALUE, 65, 0, -63, -127, 64, 0, -63, -127, 64, 1, -64, ByteCompanionObject.MIN_VALUE, 65, 0, -63, -127, 64, 1, -64, ByteCompanionObject.MIN_VALUE, 65, 1, -64, ByteCompanionObject.MIN_VALUE, 65, 0, -63, -127, 64, 0, -63, -127, 64, 1, -64, ByteCompanionObject.MIN_VALUE, 65, 1, -64, ByteCompanionObject.MIN_VALUE, 65, 0, -63, -127, 64, 1, -64, ByteCompanionObject.MIN_VALUE, 65, 0, -63, -127, 64, 0, -63, -127, 64, 1, -64, ByteCompanionObject.MIN_VALUE, 65, 0, -63, -127, 64, 1, -64, ByteCompanionObject.MIN_VALUE, 65, 1, -64, ByteCompanionObject.MIN_VALUE, 65, 0, -63, -127, 64, 1, -64, ByteCompanionObject.MIN_VALUE, 65, 0, -63, -127, 64, 0, -63, -127, 64, 1, -64, ByteCompanionObject.MIN_VALUE, 65, 1, -64, ByteCompanionObject.MIN_VALUE, 65, 0, -63, -127, 64, 0, -63, -127, 64, 1, -64, ByteCompanionObject.MIN_VALUE, 65, 0, -63, -127, 64, 1, -64, ByteCompanionObject.MIN_VALUE, 65, 1, -64, ByteCompanionObject.MIN_VALUE, 65, 0, -63, -127, 64};
    static byte[] crc16_tab_l = {0, -64, -63, 1, -61, 3, 2, -62, -58, 6, 7, -57, 5, -59, -60, 4, -52, 12, cc.k, -51, cc.m, -49, -50, cc.l, 10, -54, -53, 11, -55, 9, 8, -56, -40, 24, 25, -39, 27, -37, -38, 26, 30, -34, -33, 31, -35, 29, 28, -36, 20, -44, -43, 21, -41, 23, 22, -42, -46, 18, 19, -45, 17, -47, -48, cc.n, -16, 48, 49, -15, 51, -13, -14, 50, 54, -10, -9, 55, -11, 53, 52, -12, 60, -4, -3, 61, -1, Utf8.REPLACEMENT_BYTE, 62, -2, -6, 58, 59, -5, 57, -7, -8, 56, 40, -24, -23, 41, -21, 43, 42, -22, -18, 46, 47, -17, 45, -19, -20, 44, -28, 36, 37, -27, 39, -25, -26, 38, 34, -30, -29, 35, -31, 33, 32, -32, -96, 96, 97, -95, 99, -93, -94, 98, 102, -90, -89, 103, -91, 101, 100, -92, 108, -84, -83, 109, -81, 111, 110, -82, -86, 106, 107, -85, 105, -87, -88, 104, 120, -72, -71, 121, -69, 123, 122, -70, -66, 126, ByteCompanionObject.MAX_VALUE, -65, 125, -67, -68, 124, -76, 116, 117, -75, 119, -73, -74, 118, 114, -78, -77, 115, -79, 113, 112, -80, 80, -112, -111, 81, -109, 83, 82, -110, -106, 86, 87, -105, 85, -107, -108, 84, -100, 92, 93, -99, 95, -97, -98, 94, 90, -102, -101, 91, -103, 89, 88, -104, -120, 72, 73, -119, 75, -117, -118, 74, 78, -114, -113, 79, -115, 77, 76, -116, 68, -124, -123, 69, -121, 71, 70, -122, -126, 66, 67, -125, 65, -127, ByteCompanionObject.MIN_VALUE, 64};

    public static int calcCrc16(byte[] bArr) {
        return calcCrc16(bArr, 0, bArr.length);
    }

    public static String calcCrc16(String str) {
        byte[] decodeHex = HexUtil.decodeHex(str);
        return String.format("%04x", new Object[]{Integer.valueOf(calcCrc16(decodeHex, 0, decodeHex.length))});
    }

    public static String calcCrc16InByte(byte[] bArr) {
        return String.format("%04x", new Object[]{Integer.valueOf(calcCrc16(bArr, 0, bArr.length))});
    }

    public static boolean verifyCRC16(String str, String str2) {
        if (String.format("%04x", new Object[]{Integer.valueOf(CRC_XModem(str.getBytes()))}).equalsIgnoreCase(str2)) {
            return true;
        }
        return false;
    }

    public static int calcCrc16(byte[] bArr, int i, int i2) {
        return calcCrc16(bArr, i, i2, 65535);
    }

    public static int calcCrc16(byte[] bArr, int i, int i2, int i3) {
        int i4 = (65280 & i3) >> 8;
        byte b = i3 & UByte.MAX_VALUE;
        int i5 = 0;
        while (i5 < i2) {
            byte b2 = (b ^ bArr[i + i5]) & UByte.MAX_VALUE;
            i5++;
            byte b3 = i4 ^ crc16_tab_h[b2];
            i4 = crc16_tab_l[b2];
            b = b3;
        }
        return ((i4 & 255) << 8) | (b & UByte.MAX_VALUE & UByte.MAX_VALUE);
    }

    public static String CalcCRC16XModem(String str) {
        return String.format("%04x", new Object[]{Integer.valueOf(CRC_XModem(str.getBytes()))});
    }

    public static int CRC_XModem(byte[] bArr) {
        int i = 0;
        for (byte b : bArr) {
            for (int i2 = 0; i2 < 8; i2++) {
                boolean z = true;
                boolean z2 = ((b >> (7 - i2)) & 1) == 1;
                if (((i >> 15) & 1) != 1) {
                    z = false;
                }
                i <<= 1;
                if (z2 ^ z) {
                    i ^= 4129;
                }
            }
        }
        return 65535 & i;
    }

    public static void main(String[] strArr) {
        long currentTimeMillis = System.currentTimeMillis();
        String CalcCRC16XModem = CalcCRC16XModem("0008F8000A80001500A404000EA000000533C000FF860000000427900000000F8050000008DD18E282D35CA2CD611C000017848201001049CB922F089EBD8F66F12D9DEB094970900000010680AB0000FFD8EE5FA1D42B888DC6B1EC25F96F9387775BD20874FEC619AC3E3DE58A12C306AC3844D91E93DD291FAEAB05EFD81A902191458B89A5A5CF998742F7BC2D7005A3411BE140558EADDC40FEEFF6FC2832C587CF0EEE51EB7E197F72B71DA08DE7B086714C4C3A6728AE4FE0ED876EE36C32AC1B225DB595EF2846462F61CEC37D8B0C47CE2E225D8840EE0A9AC97B80813DF4673DD39FCD7C27D2964D5E3CF6FAD528B99616734AB755731BD3AA5DAA4796BBAECB3F3164C1998742F7BC2D7005998742F7BC2D7005998742F7BC2D7005998742F7BC2D7005998742F7BC2D70050990E9BF3722F902135CC8D36C03C7223CB7C1128E6E4DFBC5C4BC5A17D611900000009880AB8001919A26915E9205A8DC10B74772A43C727DB62921D4C7DD792B0D8C8B1BC559A1CFBD82E13665B4624DF582E13665B4624DF582E13665B4624DF554A3286AC9A75F9A82E13665B4624DF5BB660944F2C9662482E13665B4624DF5320DAA309EC06B3382E13665B4624DF5B339C664C0D39236B2CE741EB79506093CB7C1128E6E4DFBC5C4BC5A17D6119A5D3B3B7EAEE65E9E900000010680AB0000FF82CF01D3A18E9DC0B279FEF628DC8571D16D03A4ADEBF2CC63766A6E9C3D016D221A63A3F71B683F2DA4AA1A703206F665270146290DCBA6B7A9C983EBB9C03727CBA08D488DD90C29C9A5631A7190EF740DFC08DFDC53EE786AA39DCDD157869FBAF0283FBE89DC64B946C17685E7B0F102B02973A777D68A02100AB10C173F0D5C7ED85EEA910DA3286A05F25CCA44B583B23718298270917B0BF434EF982002B40852D5DDF9812FE72CA3C7D92DCE998742F7BC2D70056084E7DABB2F2E54786B9D2E24DBBE7500FF86B9ADC58C18527D6245CAF6743EB0C9EA0F688CF264A75F9C565783252D7C05E974DD102D9554048605AB0762EB3AF33A7C9BF948900000010680AB0001FF1EACC635BE31A27535BB889F962BD75BE8D60326F49AD222FA998742F7BC2D70051893C4CD459F6C2171701A643DD961639271023932AC911D97032BA779DDBCCB3E18E7E90274F00806DDE9EF87266582E3F6C6654888F3EFD332C284F13EC399694DEE60565E9ECBB945E5356AE36B0F894DDCBC95AEE16A9D0450EA4EB387B6CB961988D061C27F8F5A6B4B97F1CC2B02563FEFEE8CB702FC9EAB426A56FC8E9F163E9B3E917397A0B1870E9948CC8EFA1FF91F4EB7445A998742F7BC2D7005830259C4D33BE878399956C4021DB7BDB73ACEE842A9058A5A8F9DCECC10B7FE998742F7BC2D7005998742F7BC2D7005998742F7BC2D7005998742F7BC2D900000007180AB80026A700551868D2AEDEEDA697ACBBCBF115358B5998742F7BC2D7005998742F7BC2D7005998742F7BC2D7005998742F7BC2D700551868D2AEDEEDA699403872680BC565EC4D3834A0DBC49E8668901625EFAB1AF2E0D23AADA29AD7680E789F55EB8FA6073DCE20C876648E5900000000880D6001F0101900000");
        long currentTimeMillis2 = System.currentTimeMillis();
        System.out.println("crcXmodern:" + CalcCRC16XModem);
        System.out.println("total:" + (currentTimeMillis2 - currentTimeMillis));
    }
}
