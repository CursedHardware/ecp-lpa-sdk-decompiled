package com.eastcompeace.lpa.sdk.bean.es10;

import com.eastcompeace.lpa.sdk.bean.BaseSerializableBean;
import com.eastcompeace.lpa.sdk.utils.TextUtil;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import kotlin.UByte;
import kotlin.jvm.internal.ByteCompanionObject;

public class BerTlv extends BaseSerializableBean {
    private static final byte CONSTRUCTED_TLV_MASK = 32;
    private static final int LONG_FORM_LENGTH_MASK = 128;
    private static final byte MULTIPLE_TAG_MASK = 31;
    private static final byte MULTIPLE_TAG_MORE_MASK = Byte.MIN_VALUE;
    private final List<BerTlv> children;
    private byte[] data;
    private int len;
    private int tag;
    private int valueOffset;

    public BerTlv(byte[] bArr) {
        this(bArr, 0, bArr.length);
    }

    public BerTlv(byte[] bArr, int i, int i2) {
        this.children = new ArrayList();
        this.data = bArr;
        int decodeTag = decodeTag(bArr, i);
        if (decodeTag < i + i2) {
            this.valueOffset = decodeLen(bArr, decodeTag);
            decodeChildren();
        }
    }

    public BerTlv(int i, byte[] bArr) {
        this.children = new ArrayList();
        this.tag = i;
        this.data = bArr;
        this.len = bArr.length;
        this.valueOffset = 0;
        decodeChildren();
    }

    public static BerTlv parseHexString(String str) {
        return new BerTlv(TextUtil.hexStringToBytes(str));
    }

    public int getTag() {
        return this.tag;
    }

    public int getLength() {
        return this.len;
    }

    public byte[] getValue() {
        byte[] bArr = new byte[this.len];
        copyValue(bArr, 0);
        return bArr;
    }

    public void copyValue(byte[] bArr, int i) {
        System.arraycopy(this.data, this.valueOffset, bArr, i, this.len);
    }

    public List<BerTlv> getChildren() {
        return this.children;
    }

    public BerTlv getChild(int i) {
        return this.children.get(i);
    }

    public BerTlv getFirstChild() {
        return this.children.get(0);
    }

    public void addChild(BerTlv berTlv) {
        if (berTlv != null) {
            this.children.add(berTlv);
            int tlvLength = berTlv.getTlvLength();
            byte[] bArr = this.data;
            this.len += tlvLength;
            byte[] bArr2 = new byte[(bArr.length + tlvLength)];
            this.data = bArr2;
            try {
                System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
                berTlv.encodeTo(this.data, bArr.length);
            } finally {
                Arrays.fill(bArr, (byte) 0);
            }
        }
    }

    public boolean replaceChild(BerTlv berTlv) {
        if (berTlv != null) {
            ListIterator<BerTlv> listIterator = this.children.listIterator();
            while (listIterator.hasNext()) {
                BerTlv next = listIterator.next();
                if (next.getTag() == berTlv.getTag()) {
                    listIterator.set(berTlv);
                    int i = berTlv.len - next.len;
                    byte[] bArr = this.data;
                    byte[] copyOf = Arrays.copyOf(bArr, bArr.length + i);
                    int i2 = this.valueOffset;
                    for (BerTlv next2 : this.children) {
                        next2.encodeTo(copyOf, i2);
                        i2 += next2.getTlvLength();
                    }
                    this.len += i;
                    this.data = copyOf;
                    return true;
                }
            }
            addChild(berTlv);
            return false;
        }
        throw new IllegalArgumentException();
    }

    public BerTlv find(int i) {
        return find(i, false);
    }

    public BerTlv find(int i, boolean z) {
        return z ? findBfs(i) : findDfs(i, this);
    }

    public byte[] encode() {
        byte[] bArr = new byte[getTlvLength()];
        encodeTo(bArr, 0);
        return bArr;
    }

    private BerTlv findBfs(int i) {
        LinkedList linkedList = new LinkedList();
        linkedList.add(this);
        while (linkedList.peek() != null) {
            BerTlv berTlv = (BerTlv) linkedList.poll();
            if (berTlv.getTag() == i) {
                return berTlv;
            }
            linkedList.addAll(berTlv.getChildren());
        }
        return null;
    }

    public void encodeTo(byte[] bArr, int i) {
        ByteBuffer allocate = ByteBuffer.allocate(getTlvLength());
        putTrimmedInt(allocate, this.tag);
        putTrimmedInt(allocate, getEncodedLength());
        allocate.put(this.data, this.valueOffset, this.len);
        allocate.clear();
        allocate.get(bArr, i, getTlvLength());
    }

    public int getTlvLength() {
        return getIntSize(this.tag) + getIntSize(getEncodedLength()) + this.len;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        sb.append(TextUtil.intToHexString(this.tag));
        sb.append('|');
        sb.append(TextUtil.intToHexString(getEncodedLength()));
        sb.append('|');
        if (isConstructed()) {
            for (BerTlv berTlv : getChildren()) {
                sb.append(berTlv.toString());
            }
        } else {
            sb.append(TextUtil.bytesToHexString(getValue()));
        }
        sb.append(']');
        return sb.toString();
    }

    private int decodeTag(byte[] bArr, int i) {
        int i2 = i + 1;
        byte b = bArr[i] & UByte.MAX_VALUE;
        this.tag = b;
        if ((b & MULTIPLE_TAG_MASK) != 31) {
            return i2;
        }
        while (true) {
            int i3 = this.tag << 8;
            this.tag = i3;
            this.tag = i3 + (bArr[i2] & UByte.MAX_VALUE);
            int i4 = i2 + 1;
            if ((bArr[i2] & Byte.MIN_VALUE) != Byte.MIN_VALUE) {
                return i4;
            }
            i2 = i4;
        }
    }

    private int decodeLen(byte[] bArr, int i) {
        int i2 = i + 1;
        byte b = bArr[i] & UByte.MAX_VALUE;
        this.len = b;
        if ((b & 128) == 128) {
            this.len = 0;
            for (int i3 = b & ByteCompanionObject.MAX_VALUE; i3 > 0; i3--) {
                int i4 = this.len << 8;
                this.len = i4;
                i2++;
                this.len = i4 + (bArr[i2] & UByte.MAX_VALUE);
            }
        }
        return i2;
    }

    private void decodeChildren() {
        if (isConstructed()) {
            int i = this.valueOffset;
            while (true) {
                int i2 = this.valueOffset + this.len;
                if (i < i2) {
                    BerTlv berTlv = new BerTlv(this.data, i, i2 - i);
                    this.children.add(berTlv);
                    i += berTlv.getTlvLength();
                } else {
                    return;
                }
            }
        }
    }

    private boolean isConstructed() {
        int numberOfLeadingZeros = 32 << ((3 - (Integer.numberOfLeadingZeros(this.tag) / 8)) * 8);
        return (this.tag & numberOfLeadingZeros) == numberOfLeadingZeros;
    }

    private BerTlv findDfs(int i, BerTlv berTlv) {
        if (berTlv.getTag() == i) {
            return berTlv;
        }
        for (BerTlv findDfs : berTlv.getChildren()) {
            BerTlv findDfs2 = findDfs(i, findDfs);
            if (findDfs2 != null) {
                return findDfs2;
            }
        }
        return null;
    }

    private void putTrimmedInt(ByteBuffer byteBuffer, int i) {
        if (i == 0) {
            byteBuffer.put((byte) 0);
        }
        for (int numberOfLeadingZeros = 3 - (Integer.numberOfLeadingZeros(i) / 8); numberOfLeadingZeros >= 0; numberOfLeadingZeros--) {
            byteBuffer.put((byte) ((i >> (numberOfLeadingZeros * 8)) & 255));
        }
    }

    public int getEncodedLength() {
        int i;
        int i2 = this.len;
        if (i2 < 128) {
            return i2;
        }
        if (i2 < 256) {
            i = 33024;
        } else if (i2 >= 65536) {
            return i2 - 2097152000;
        } else {
            i = 8519680;
        }
        return i2 + i;
    }

    private int getIntSize(int i) {
        if (i == 0) {
            return 1;
        }
        return 4 - (Integer.numberOfLeadingZeros(i) / 8);
    }

    public String toHexString() {
        return TextUtil.intToHexString(this.tag) + TextUtil.intToHexString(getEncodedLength()) + TextUtil.bytesToHexString(getValue());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            BerTlv berTlv = (BerTlv) obj;
            if (this.tag == berTlv.tag && this.len == berTlv.len) {
                return Arrays.equals(getValue(), berTlv.getValue());
            }
        }
        return false;
    }

    public int hashCode() {
        return (((this.tag * 31) + this.len) * 31) + Arrays.hashCode(getValue());
    }
}
