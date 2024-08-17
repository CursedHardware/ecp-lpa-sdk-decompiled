package com.eastcompeace.lpa.sdk.fup;

public class FupInstruction {
    private String SeApdu;
    private int apduLen;
    private int currentApduOrder;
    private String expApduRspData;
    private int expApduRspDataLen;
    private String expApduRspSW;
    private String expStrApduRspDataLen;
    private boolean isResetInstruction;
    private ResetEntity reset;
    private String strApduLen;

    public boolean isResetInstruction() {
        return this.isResetInstruction;
    }

    public void setResetInstruction(boolean z) {
        this.isResetInstruction = z;
    }

    public int getCurrentApduOrder() {
        return this.currentApduOrder;
    }

    public void setCurrentApduOrder(int i) {
        this.currentApduOrder = i;
    }

    public String getExpApduRspData() {
        return this.expApduRspData;
    }

    public void setExpApduRspData(String str) {
        this.expApduRspData = str;
    }

    public String getExpApduRspSW() {
        return this.expApduRspSW;
    }

    public void setExpApduRspSW(String str) {
        this.expApduRspSW = str;
    }

    public String getSeApdu() {
        return this.SeApdu;
    }

    public void setSeApdu(String str) {
        this.SeApdu = str;
    }

    public ResetEntity getReset() {
        return this.reset;
    }

    public String getStrApduLen() {
        return this.strApduLen;
    }

    public void setStrApduLen(String str) {
        this.strApduLen = str;
    }

    public int getApduLen() {
        return this.apduLen;
    }

    public void setApduLen(int i) {
        this.apduLen = i;
    }

    public String getExpStrApduRspDataLen() {
        return this.expStrApduRspDataLen;
    }

    public void setExpStrApduRspDataLen(String str) {
        this.expStrApduRspDataLen = str;
    }

    public int getExpApduRspDataLen() {
        return this.expApduRspDataLen;
    }

    public void setExpApduRspDataLen(int i) {
        this.expApduRspDataLen = i;
    }

    public void setReset(ResetEntity resetEntity) {
        this.reset = resetEntity;
    }

    public static class ResetEntity {
        private String expectAtr;
        private boolean hasExpAtr;
        private String resetFlag;
        private int resetInstrLen;

        public boolean isHasExpAtr() {
            return this.hasExpAtr;
        }

        public void setHasExpAtr(boolean z) {
            this.hasExpAtr = z;
        }

        public String getResetFlag() {
            return this.resetFlag;
        }

        public void setResetFlag(String str) {
            this.resetFlag = str;
        }

        public int getResetInstrLen() {
            return this.resetInstrLen;
        }

        public void setResetInstrLen(int i) {
            this.resetInstrLen = i;
        }

        public String getExpectAtr() {
            return this.expectAtr;
        }

        public void setExpectAtr(String str) {
            this.expectAtr = str;
        }
    }
}
