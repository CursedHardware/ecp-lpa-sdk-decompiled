package com.eastcompeace.lpa.sdk;

public interface IChannel {
    String transmitAPDU(int i, int i2, int i3, int i4, int i5, String str) throws Exception;
}
