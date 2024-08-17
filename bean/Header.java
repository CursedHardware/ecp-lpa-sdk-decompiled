package com.eastcompeace.lpa.sdk.bean;

public class Header extends BaseSerializableBean {
    private FunctionExecutionStatus functionExecutionStatus;

    public FunctionExecutionStatus getFunctionExecutionStatus() {
        return this.functionExecutionStatus;
    }

    public void setFunctionExecutionStatus(FunctionExecutionStatus functionExecutionStatus2) {
        this.functionExecutionStatus = functionExecutionStatus2;
    }

    public static class FunctionExecutionStatus {
        private String status;

        public String getStatus() {
            return this.status;
        }

        public void setStatus(String str) {
            this.status = str;
        }
    }
}
