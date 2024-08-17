package com.eastcompeace.lpa.sdk.bean.es11;

import com.eastcompeace.lpa.sdk.bean.BaseSerializableBean;
import java.util.List;

public class Es11AuthenticateClientBean extends BaseSerializableBean {
    private List<EventEntry> eventEntries;
    private String transactionId;

    public Es11AuthenticateClientBean() {
    }

    public Es11AuthenticateClientBean(String str, List<EventEntry> list) {
        this.transactionId = str;
        this.eventEntries = list;
    }

    public String getTransactionId() {
        return this.transactionId;
    }

    public void setTransactionId(String str) {
        this.transactionId = str;
    }

    public List<EventEntry> getEventEntries() {
        return this.eventEntries;
    }

    public void setEventEntries(List<EventEntry> list) {
        this.eventEntries = list;
    }

    public String toString() {
        return "AuthenticateClientBean{transactionId='" + this.transactionId + '\'' + ", eventEntries=" + this.eventEntries + '}';
    }
}
