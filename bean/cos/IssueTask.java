package com.eastcompeace.lpa.sdk.bean.cos;

import com.eastcompeace.lpa.sdk.bean.BaseSerializableBean;
import java.util.List;

public class IssueTask extends BaseSerializableBean {
    private String lastDate;
    private ProductInfo productInfo;
    private List<Rule> rules;
    private String status;
    private String tid;

    public String getTid() {
        return this.tid;
    }

    public void setTid(String str) {
        this.tid = str;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String str) {
        this.status = str;
    }

    public ProductInfo getProductInfo() {
        return this.productInfo;
    }

    public void setProductInfo(ProductInfo productInfo2) {
        this.productInfo = productInfo2;
    }

    public String getLastDate() {
        return this.lastDate;
    }

    public void setLastDate(String str) {
        this.lastDate = str;
    }

    public static class ProductInfo extends BaseSerializableBean {
        private String name;
        private String pid;
        private String status;

        public String getPid() {
            return this.pid;
        }

        public void setPid(String str) {
            this.pid = str;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String str) {
            this.name = str;
        }

        public String getStatus() {
            return this.status;
        }

        public void setStatus(String str) {
            this.status = str;
        }
    }

    public List<Rule> getRules() {
        return this.rules;
    }

    public void setRules(List<Rule> list) {
        this.rules = list;
    }
}
